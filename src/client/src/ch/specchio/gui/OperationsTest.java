package ch.specchio.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
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
	DatabaseConnectionDialog dt;
	//public JFrame application_frame;

	   protected OperationsTest() {
	      // Exists only to defeat instantiation.
		  setPreferredSize(new Dimension(200, 0));
		  spl = new ScrollPaneLayout();
		  setLayout(spl);
		  
		  rep_cnt = 0;
		  frame = new ReportContainer();
		  		  		  
		  constraints = new GridBagConstraints();
		  constraints.gridwidth = 1;
		  constraints.gridheight = 1;	
		  constraints.anchor = GridBagConstraints.NORTHWEST;
		  constraints.gridx = 0;  
		  
//		  test_window();
//		  addTestButton();
		  
		  getViewport().add(frame);		
		  
	   }
	   public static OperationsTest getInstance() 
	   {
	      if(instance == null) {
	         instance = new OperationsTest();
	      }
	      return instance;
	   }
	   
	   public void addConnectionDialog(){
		   frame.add(dt.descriptor_panel);
	   }
	   
	  public void addTestButton(){
		  JButton button = new JButton("Sup");
		  frame.add(button);
		  button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("HEYYY BITCH");		
			}
		  });
	  }
	   
	  
	   
	   public void add_report(ProgressReportPanel rep)
	   {
		   constraints.gridy = rep_cnt;
		   frame.l.insertComponent(rep, constraints);
		   rep_cnt++;
		   this.validate(); // force the redraw on screen
	   }
	   
	   public void add_label(){
		   JLabel jbl = new JLabel("Hello");
		   frame.l.insertComponent(jbl, constraints);
		   this.validate(); //wish this would force update
	   }
	   
	   public void test_window(){
		   int i;
		   JPanel jp = new JPanel();
		   frame.add(jp);
		   
		   for(i = 0;i < 1000; i++){
			   String num = Integer.toString(i);
			   JButton number = new JButton(num);
			   jp.add(number);
		   }
	   }
	   
	   public void createExternalWindow(){
		   
	   }

}
