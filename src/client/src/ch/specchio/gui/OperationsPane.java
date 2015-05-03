package ch.specchio.gui;


import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

// uses the Singleton Pattern
public class OperationsPane extends JScrollPane {

	private static final long serialVersionUID = 1L;
	private static OperationsPane instance = null;
	int rep_cnt;
	GridBagConstraints constraints;
	ScrollPaneLayout spl;	
	ReportContainer frame;
	
	//public JFrame application_frame;

	   protected OperationsPane() {
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
	   public static OperationsPane getInstance() 
	   {
	      if(instance == null) {
	         instance = new OperationsPane();
	      }
	      return instance;
	   }
	   
	   public void add_report(ProgressReportPanel rep)
	   {
		   constraints.gridy = rep_cnt;
		   frame.l.insertComponent(rep, constraints);
		   rep_cnt++;
		   this.validate(); // force the redraw on screen
	   }
	   
	   public void remove_report(ProgressReportPanel rep)
	   {		   
//		   ReportRemover rr = new ReportRemover(this, frame, rep);		   
//		   rr.start();		   
	   }
	}
