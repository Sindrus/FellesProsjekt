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
public class ConnectionImpl extends AbstractConnection{
	
    /** Keeps track of the used ports for each server port. */
    private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());
    private static final int[] TRIES = {500,1000,2000,4000};
    private static final int TIME_WAIT_DURATION = 8000;
    /**
     * Initialise initial sequence number and setup state machine.
     * 
     * @param myPort
     * - the local port to associate with this connection
     */
    public ConnectionImpl(int Port){
    	super();
    	this.usedPorts.put(Port, true);
    	this.myPort = Port;
    	this.myAddress = getIPv4Address();
    }
    
    private String getIPv4Address(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
            }
        catch (UnknownHostException e){
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
    public void connect(InetAddress remoteAddress, int remotePort) throws IOException, SocketTimeoutException {
    	if (this.state != State.CLOSED){
    		throw new IllegalStateException("Need to be in closed state before trying to connect.");
    	}
    	//Set state information
    	this.remoteAddress = remoteAddress.getHostAddress();
    	this.remotePort = remotePort;
    	//Send SYN, receive SYN_ACK and  ACK on SYN_ACK
    	//Make a syn to see if the other end returns a SYN_ACK, which is then "made to be" synAck
    	KtnDatagram synAck = null, syn = constructInternalPacket(Flag.SYN); 
    	synAck = safelySendPacket(syn, State.CLOSED, State.SYN_SENT);
    	if (checksumCheck(synAck) && synAck.getFlag() == Flag.SYN_ACK){
    		lastValidPacketReceived = synAck;
    		this.remotePort = synAck.getSrc_port();
    		this.remoteAddress = synAck.getSrc_addr();
    		safelySendAck(synAck);
    	} else{
    		throw new IOException("Could not connect; did not receive valid SYN_ACK.");
    	}
    	this.state = State.ESTABLISHED;
    }

    /**
     * Listen for, and accept, incoming connections.
     * 
     * @return A new ConnectionImpl-object representing the new connection.
     * @see Connection#accept()
     */
    public Connection accept() throws IOException, SocketTimeoutException {
    	if (this.state != State.CLOSED){
        	throw new IllegalStateException("Need to be in closed state to accept connections.");
    	}
    	this.state = State.LISTEN;
    	KtnDatagram syn = null; //Make a SYN packet
    	 while (!checksumCheck(syn))
    		 try {
             syn = receivePacket(true);
		} catch (Exception e) {}
    	//Create new connection
    	ConnectionImpl connection = new ConnectionImpl(newPortNumber()); //Make Received state packet
    	connection.remoteAddress = syn.getSrc_addr();
    	connection.remotePort = syn.getSrc_port();
    	connection.state = State.SYN_RCVD;
    	KtnDatagram ack = null; //Make ACK packet
 	   	int timer = 4;
 	   	long start = System.currentTimeMillis();
 	   	try {
	 	   	do {
	 	   		try {
	 	   			timer--;
		 	   		connection.sendAck(syn, true);
	    			ack = connection.receiveAck();
	    			break;
	 	   		} catch (ConnectException e) {
	 	   		} catch (IOException e) {
	 	   		}
	 	   	}while(timer >= 0 && (TRIES[3-timer] + start) <= System.currentTimeMillis());
 	   	} catch (Exception e){
 	   		throw new IOException("Unable to connect.");
 	   	}
    	this.state = State.CLOSED;
    	//Finalize connection
    	if (connection.checksumCheck(ack)) {
    		connection.state = State.ESTABLISHED;
    		connection.lastValidPacketReceived = ack;
    		return connection;
    	}
    	else{
    		throw new IOException("Unable to connect; did not receive valid ACK on SYN_ACK");
    	}
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
    	if (this.state != State.ESTABLISHED){
    		throw new IllegalStateException("Data packets can only be sent in established state");
    	}
    	if (lastValidPacketReceived.getFlag() == Flag.SYN_ACK){
    		// We got here.
    		boolean sent = false;
    		while (sent == false){
    		try {
    			//we got here too.
    			sendAck(lastValidPacketReceived, false);
    			sent = true;
    		}catch (SocketException e) {
    		}
    		}
       }
	   KtnDatagram datapacket = constructDataPacket(msg), ack = null;
	   int tries = 5;
	   while(tries-- >= 0 && !checksumCheck(ack)){ //This is not a fail. fail occurs at recieve().
		   System.out.println("do we stop here with a fail?" + tries);
		   ack = sendDataPacketWithRetransmit(datapacket);
	   }
	   if (!checksumCheck(ack) || ack.getFlag() != Flag.ACK){
		   state = State.CLOSED;
		   throw new IOException("Failed to send packet");
	   }
	   lastValidPacketReceived = ack;
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
    	if (this.state != State.ESTABLISHED){
    		throw new IllegalStateException("Data packets can only be received in established state");
    	}
    	KtnDatagram ktnd = null;
    	try{
    		ktnd = receivePacket(false);
    	} catch (EOFException e){
    		throw e;
    	} catch (IOException e) {}
    	//Send ACK and deliver content to application
    	if(checksumCheck(ktnd) && ktnd.getFlag() == Flag.NONE && ktnd.getSeq_nr() <= lastValidPacketReceived.getSeq_nr() +2){
    			System.out.println("we should be here now.");
    			lastValidPacketReceived = ktnd;
    			safelySendAck(ktnd);
    			return (String) ktnd.getPayload();
    		}
		//Receive duplicate, try again
		safelySendAck(lastValidPacketReceived);
		return receive();
    }
    
    /*
     * Sending an ACK and handling errors
     * @param ktnd
     * @throws IOException
     */
   private void safelySendAck(KtnDatagram ktnd) throws IOException{
	   if (ktnd.getFlag() != Flag.NONE && ktnd.getFlag() != Flag.SYN && ktnd.getFlag() != null 
		   && ktnd.getFlag() != Flag.FIN && ktnd.getFlag() != Flag.SYN_ACK){
		   throw new IllegalArgumentException("Cannot ACK "+ktnd.getFlag().toString()+" packet.");
	   }
	   int timer = 4;
	   long start = System.currentTimeMillis();
	   do {
		   try {
			   timer--;
			   sendAck(ktnd, ktnd.getFlag() == Flag.SYN);
			   return;
		   } catch (IOException e) {
		   }
	   }while(timer >= 0 && (TRIES[3-timer] + start) <= System.currentTimeMillis() && checksumCheck(ktnd));
	   throw new IOException("Could not send ACK.");
   }
   
    /**
     * Send a packet and wait for aCK. If no ACK is received, null is returned.
     * @param ktnd
     * @return KtnDatagram
     * @throw EOFException
     */
   private KtnDatagram safelySendPacket(KtnDatagram ktnd, State before, State after) 
		   throws EOFException{
	   KtnDatagram ack = null;
	   int timer = 4;
	   long start = System.currentTimeMillis();
	   do {
		   try {
			   timer--;
			   this.state = before;
			   simplySendPacket(ktnd);
			   this.state = after;
			   ack = receiveAck();
			   break;
		   } catch (ClException e) {
		   } catch (ConnectException e){
		   } catch (SocketException e) {
		   } catch (IOException e) {
		   }
	   }while(timer >= 0 && (TRIES[3-timer] + start) <= System.currentTimeMillis() && checksumCheck(ktnd));
	   return ack;
   }
   
    /**
     * Close the connection.
     * 
     * @see Connection#close()
     */
    public void close() throws IOException {
    	if (state != State.ESTABLISHED){
    		throw new IllegalStateException("Cannot close unless connected.");
    	}
    	if (disconnectRequest != null){
    		KtnDatagram resend = null;
     	   	int timer = 4;
     	   	long start = System.currentTimeMillis();
     	   	do {
     	   		safelySendAck(disconnectRequest);
     	   		state = State.CLOSE_WAIT;
    	 		try {
    	 			timer--;
    	 			Thread.sleep(2000);
    	 	 		break;
    	 	   	} catch (InterruptedException e) {
    	 	   	}
    	 		try {
    	 			resend = receivePacket(true);
    	 		} catch (SocketException e){
    	 		}
    	   	}while(timer >= 0 && (TRIES[3-timer] + start) <= System.currentTimeMillis());
	    	if (checksumCheck(resend) && resend.getFlag() != Flag.FIN){
	    		throw new IOException("Could not close connection; first FIN_ACK never received.");
	    	}
	    	//Send FIN and receive FIN_ACK
	    	KtnDatagram fin = constructInternalPacket(Flag.FIN), fin_ack = null;
	    	fin_ack = safelySendPacket(fin, State.CLOSE_WAIT, State.LAST_ACK);
	    	if (checksumCheck(fin_ack)){
	    		state = State.CLOSED;
	    	}
    	} else {
    		//Send FIN and receive FIN_ACK
    		KtnDatagram fin = constructInternalPacket(Flag.FIN), fin_ack = null;
    		fin_ack = safelySendPacket(fin, State.ESTABLISHED, State.FIN_WAIT_1);
    		if (!checksumCheck(fin_ack)){
    			throw new IOException("Could not close connection; did not receive FIN_ACK");
    		}
    		state = State.FIN_WAIT_2;
    		fin = null;
    		long start1 = System.currentTimeMillis();
    		do {
    			fin = receivePacket(true);
    			break;
    	   	}while(TIME_WAIT_DURATION + start1 <= System.currentTimeMillis());
    		System.out.println(checksumCheck(fin));
    		if (!checksumCheck(fin) || fin.getFlag() != Flag.FIN){
    			throw new IOException("Failed to close connection; never received final FIN");
    		}
    		long start2 = System.currentTimeMillis();
    		do {
    			if (checksumCheck(fin)){
    				safelySendAck(fin);
    			}
    			fin = receivePacket(true);
    		} while (TIME_WAIT_DURATION + start2 <= System.currentTimeMillis());
    		state = State.CLOSED;
    	}
    }

    /**
     * Test a packet for transmission errors. This function should only called
     * with data or ACK packets in the ESTABLISHED state.
     * 
     * @param packet
     *            Packet to test.
     * @return true if packet is free of errors, false otherwise.
     */
    @Override
    protected boolean checksumCheck(KtnDatagram packet) {
    	return (packet != null && packet.getChecksum() == packet.calculateChecksum());
    }
}