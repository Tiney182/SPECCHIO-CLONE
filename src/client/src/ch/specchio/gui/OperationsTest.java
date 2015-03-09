package ch.specchio.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

import ch.specchio.client.SPECCHIODatabaseDescriptor;
import ch.specchio.client.SPECCHIOServerDescriptor;
import ch.specchio.client.SPECCHIOWebAppDescriptor;
import ch.specchio.client.SPECCHIOClient;
import ch.specchio.client.SPECCHIOClientException;
import ch.specchio.client.SPECCHIOClientFactory;

class OperationsTestPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
		
		GridbagLayouter l;
		public void ReportContainter()
		{
			l = new GridbagLayouter(this);
		}
}
public class OperationsTest extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private static OperationsTest instance = null;
	int rep_cnt;
	GridBagConstraints constraints;
	ScrollPaneLayout spl;	
	ReportContainer frame;
	
	//public JFrame application_frame;

	   protected OperationsTest() {
	      // Exists only to defeat instantiation.
		  setPreferredSize(new Dimension(200, 0));
		  spl = new ScrollPaneLayout();
		  setLayout(spl);
		  
		  
		  rep_cnt = 0;
		  frame = new ReportContainer();
		  
//		  JLabel jlb = new JLabel("Hello");
//		  frame.add(jlb);
		  
		  constraints = new GridBagConstraints();
		  constraints.gridwidth = 1;
		  constraints.gridheight = 1;	
		  constraints.anchor = GridBagConstraints.NORTHWEST;
		  constraints.gridx = 0;  
		  		  
		  getViewport().add(frame);		
		  
	   }
	   public static OperationsTest getInstance() 
	   {
	      if(instance == null) {
	         instance = new OperationsTest();
	      }
	      return instance;
	   }
	   public void add_label(){
		 JLabel jl = new JLabel("HELLO");
		   frame.add(jl);
		   getViewport().add(frame);
	   }

}
