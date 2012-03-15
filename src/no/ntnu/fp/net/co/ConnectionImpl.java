/*
 * Created on Oct 27, 2004
 */
package no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import no.ntnu.fp.net.admin.Log;
import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;

/**
 * Implementation of the Connection-interface. <br>
 * <br>
 * This class implements the behaviour in the methods specified in the interface
 * {@link Connection} over the unreliable, connectionless network realised in
 * {@link ClSocket}. The base class, {@link AbstractConnection} implements some
 * of the functionality, leaving message passing and error handling to this
 * implementation.
 * 
 * @author Sebjørn Birkeland and Stein Jakob Nordbø
 * @see no.ntnu.fp.net.co.Connection
 * @see no.ntnu.fp.net.cl.ClSocket
 */
public class ConnectionImpl extends AbstractConnection {

    /** Keeps track of the used ports for each server port. */
    private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());

    private static final int MAX_SEND_ATTEMPTS = 10;
    private static final int TIME_WAIT_DURATION = 5000;
    /**
     * Initialise initial sequence number and setup state machine.
     * 
     * @param myPort
     *            - the local port to associate with this connection
     */
    public ConnectionImpl(int myPort) {
    	super();
    	usedPorts.put(myPort, true);
    	this.myPort = myPort;
    	this.myAddress = getIPv4Address();
    }

    private String getIPv4Address() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    /**
     * Establish a connection to a remote location.
     * 
     * @param remoteAddress
     *            - the remote IP-address to connect to
     * @param remotePort
     *            - the remote portnumber to connect to
     * @throws IOException
     *             If there's an I/O error.
     * @throws java.net.SocketTimeoutException
     *             If timeout expires before connection is completed.
     * @see Connection#connect(InetAddress, int)
     */
    public void connect(InetAddress remoteAddress, int remotePort) throws IOException,
            SocketTimeoutException {
    	if (state != State.CLOSED){
    		throw new IllegalStateException("Need to be in closed state before trying to connect.");
    	}
    	//Set state information
    	this.remoteAddress = remoteAddress.getHostAddress();
    	this.remotePort = remotePort;
    	//Send SYN, receive SYN_ACK and send ACK on SYN_ACK
    	KtnDatagram synAck = null, syn = constructInternalPacket(Flag.SYN);
    	synAck = safelySendPacket(syn, State.CLOSED, State.SYN_SENT);
    	if (isReallyValid(synAck)){
    		lastValidPacketReceived = synAck;
    		this.remotePort = synAck.getSrc_port();
    		System.out.println("\nSENDING ACK on SYN_ACK.");
    		safelySendAck(synAck);
    	}else{
    		throw new IOException("Could not connect; did not receive valid SYN_ACK.");
    	}
    	System.out.println("\nESTABLISHED.\n");
    	state = State.ESTABLISHED;
//        throw new NotImplementedException();
    }

    /**
     * Listen for, and accept, incoming connections.
     * 
     * @return A new ConnectionImpl-object representing the new connection.
     * @see Connection#accept()
     */
    public Connection accept() throws IOException, SocketTimeoutException {
    	if (state != State.CLOSED){
        	throw new IllegalStateException("Need to be in closed state to accept connections.");
      }
    	//System.out.println("\nserver:LISTEN\n");
    	state = State.LISTEN;
    	//Receiving SYN
    	KtnDatagram syn = null;
    	while(!isReallyValid(syn)) try{
    		//System.out.println("\nRECEIVING SYN\n");
    		syn = receivePacket(true);
    	} catch (Exception e) {} //Ignore
    	//Create new connection
    	ConnectionImpl connection = new ConnectionImpl(newPortNumber());
    	connection.remoteAddress = syn.getSrc_addr();
    	connection.remotePort = syn.getSrc_port();
    	//System.out.println("\nSYN_RCVD\n");
    	connection.state = State.SYN_RCVD;
    	//Send SYN_ACK and receive ACK
    	KtnDatagram ack = null;
    	try{
    		int triesLeft = MAX_SEND_ATTEMPTS;
    		while (!connection.isReallyValid(ack) && triesLeft-- > 0) try{
    			//System.out.println("\sending SYN_ACK");
    			connection.sendAck(syn, true);
    			//System.out.println("\nRECEIVING ACK ON SYN_ACK");
    			ack = connection.receiveAck();
    		} catch (ConnectException e) {//Try again on A2 fail
    		} catch (IOException e) {}
    	} catch (Exception e) {
    		throw new IOException("Unable to connect");
    	}
    	//System.out.println("\nserver: ClOSED\n");
    	state = State.CLOSED;
    	//Finalize connection
    	if (connection.isReallyValid(ack)) {
    		System.out.println("\nEstablished\n");
    		connection.state = State.ESTABLISHED;
    		connection.lastValidPacketReceived = ack;
    		return connection;
    	}else{
    		throw new IOException("Unable to connect; did not receive valid ACK on SYN_ACK");
    	}
//    	throw new NotImplementedException();
    }
    
    /**
     * Find, reserve and return a new port number to be usd in a new connection.
     * @return int
     */
    public static int newPortNumber(){
    	int portnum = -1;
    	while (portnum == -1 || usedPorts.containsKey(portnum)){
    		portnum = (int) ((Math.random()*8999)+1001);
    	}
    	usedPorts.put(portnum, true);
    	return portnum;
    }

    /**
     * Send a message from the application.
     * 
     * @param msg
     *            - the String to be sent.
     * @throws ConnectException
     *             If no connection exists.
     * @throws IOException
     *             If no ACK was received.
     * @see AbstractConnection#sendDataPacketWithRetransmit(KtnDatagram)
     * @see no.ntnu.fp.net.co.Connection#send(String)
     */
    public void send(String msg) throws ConnectException, IOException {
    	if (state != State.ESTABLISHED){
    		throw new IllegalStateException("Data packets can only be sent in established state");
    	}
    	//if SYN_ACK is still the last valid packet received, send ACK on SYN_ACK again
    	//TODO: is this the solution to lost ACK on SYN_ACK?
    	if (lastValidPacketReceived.getFlag() == Flag.SYN_ACK){
    		boolean sent = false;
    		do try {
    			sendAck(lastValidPacketReceived, false);
    			sent = true;
    		}catch (SocketException e) {
    		}
    		while (sent == false);
    	}
	   KtnDatagram datapacket = constructDataPacket(msg), ack = null;
	   int triesLeft = MAX_SEND_ATTEMPTS;
	   while(!isReallyValid(ack) && triesleft-- > 0){
		   //System.out.println("\nSENDING DATA + RECEIVE ACK");
		   ack = sendDataPAcketWithRetransmit(datapacket);
	   }
	   if (!isReallyValid(ack)){
		   state = State.CLOSED;
		   throw new IOException("Failed to send packet");
	   }
	   //To prevent the fix above from running forever
	   lastValidPacketReceived = ack;
//        throw new NotImplementedException();
}
    /**
     * Wait for incoming data.
     * 
     * @return The received data's payload as a String.
     * @see Connection#receive()
     * @see AbstractConnection#receivePacket(boolean)
     * @see AbstractConnection#sendAck(KtnDatagram, boolean)
     */
    public String receive() throws ConnectException, IOException {
    	if (state != State.ESTABLISHED){
    		throw new IllegalStateException("Data packets can only be received in established state");
    	}
    	//System.out.println("\nRECEIVING DATA");
    	KtnDatagram ktnd = null;
    	try{
    		ktnd = receivePacket(false);
    	} catch (EOFException e){
    		//System.out.println("\nEOFException");
    		throw e;
    	} catch (IOException e) {} //Ignore
    	//Send ACK and deliver content to application
    	if(isReallyValid(ktnd) && ktnd.getFlag() == Flag.NONE)
    		&& ktnd.getSeq_nr() <= lastValidPacketReceived.getSeq_nr() +2){
    			//TODO:Sequence number check merged in validation method. And maybe bugs with "+2"?
    			lastValidPacketReceived = ktnd;
    			//System.out.println("\nSENDING ACK");
    			safelySendAck(ktnd);
    			return (String) ktnd.getPayload();
    		}
    		//Receive duplicate, try again
    		safelySendAck(lastValidPacketReceived);
    		return receive();
    	//        throw new NotImplementedException();
    }
    
    /*
     * Sending an ACK and handling errors
     * @param ktnd
     * @throws IOException
     */
   private void safelySendAck(KtnDatagram ktnd) throws IOException{
	   if (ktnd.getFlag() != Flag.NONE && ktnd.getFlag() != Flag.SYN && 
			ktnd.getFlag() != null && ktnd.getFlag() != Flag.FIN &&
			ktnd.getFlag() != Flag.SYN_ACK){
		   throw new IllegalArgumentException("Cannot ACK "+ktnd.getFlag().toString()+" packet.");
	   }
	   int triesLeft = MAX_SEND_ATTEMPTS;
	   do {
		   try {
			   sendAck(ktnd, ktnd.getFlag() == Flag.SYN);
			   return;
		   } catch (IOException e){} //Ignore
	   } while (triesLeft-- > 0);
	   throw new IOException("Could not send ACK");
   }
   
    
    /**
     * Close the connection.
     * 
     * @see Connection#close()
     */
    public void close() throws IOException {
        throw new NotImplementedException();
    }

    /**
     * Test a packet for transmission errors. This function should only called
     * with data or ACK packets in the ESTABLISHED state.
     * 
     * @param packet
     *            Packet to test.
     * @return true if packet is free of errors, false otherwise.
     */
    protected boolean isValid(KtnDatagram packet) {
        throw new NotImplementedException();
    }
}
