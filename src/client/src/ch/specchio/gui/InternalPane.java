package ch.specchio.gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class InternalPane extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private static InternalPane instance = null;
	JPanel viewPanel = new JPanel();
	
	protected InternalPane(){
		setViewportView(viewPanel);
		}
	
	public static InternalPane getInstance() 
	   {
	      if(instance == null) {
	         instance = new InternalPane();
	      }
	      return instance;
	   }
	
	public void test_window(){
		   int i;
		   JPanel jp = new JPanel();
		   viewPanel.add(jp);
		   
		   for(i = 0;i < 1000; i++){
			   String num = Integer.toString(i);
			   JButton number = new JButton(num);
			   jp.add(number);
		   }
	   }
}
