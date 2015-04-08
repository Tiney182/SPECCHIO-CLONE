package ch.specchio.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class InternalPane extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private static InternalPane instance = null;
	JPanel viewPanel = new JPanel();
	
	protected InternalPane(){
		getViewport().add(viewPanel);
		}
	
	public static InternalPane getInstance() 
	   {
	      if(instance == null) {
	         instance = new InternalPane();
	      }
	      return instance;
	   }
}
