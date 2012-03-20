package util;


import java.util.ArrayList;


public interface GuiListener {


	abstract void notifyGui(ChangeType ct, ArrayList<Object> list);


}