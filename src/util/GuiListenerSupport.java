package util;

import java.util.ArrayList;

public class GuiListenerSupport {

	private ArrayList<GuiListener> listeners;
	
	public GuiListenerSupport(){
		listeners = new ArrayList<GuiListener>();
	}
	
	public void add(GuiListener l){
		listeners.add(l);
	}
	
	public void notifyListeners(ChangeType ct, ArrayList<Object> list){
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).notifyGui(ct, list);
		}
	}
}