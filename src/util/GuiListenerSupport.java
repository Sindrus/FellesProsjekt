package util;

import java.util.ArrayList;

public class GUIListenerSupport {

	private ArrayList<GUIListener> listeners;

	public GUIListenerSupport(){
		listeners = new ArrayList<GUIListener>();
	}

	public void add(GUIListener l){
		listeners.add(l);
	}

	public void notifyListeners(ChangeType ct, ArrayList<Object> list){
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).notifyGui(ct, list);
		}
	}
}