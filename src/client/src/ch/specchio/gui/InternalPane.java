package ch.specchio.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

import ch.specchio.gui.SPECCHIOApplication;

class ReportContainer extends JPanel{

	private static final long serialVersionUID = 1L;

	GridbagLayouter l;
	
	public ReportContainer ()
	{
		l = new GridbagLayouter(this);	
	}
}


class ReportRemover extends Thread
{
	ReportContainer frame; 
	ProgressReportPanel rep;
	InternalPane ip;
	
	public ReportRemover(InternalPane ip, ReportContainer frame, ProgressReportPanel rep)
	{
		this.frame = frame;
		this.rep = rep;		
		this.ip = ip;
	}
	
	public void run () 
	{				
		try {
			sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("remove component");
		frame.remove(rep);
		frame.repaint();
	}

}


public class InternalPane extends JScrollPane {

	private static InternalPane instance = null;
	ScrollPaneLayout spl;	
	ReportContainer frame;
	int rep_cnt;
	GridBagConstraints constraints;
	
	protected InternalPane(){
//		 setPreferredSize(new Dimension(200, 0));
		  spl = new ScrollPaneLayout();
		  setLayout(spl);
		  
		  rep_cnt = 0;
		  frame = new ReportContainer();
		  
		  constraints = new GridBagConstraints();
		  constraints.gridwidth = 1;
		  constraints.gridheight = 1;	
		  constraints.anchor = GridBagConstraints.NORTHWEST;
		  constraints.gridx = 0;  
		  		  
		  test_panel();
		  getViewport().add(frame);		
		  
	}
	
	private static final long serialVersionUID = 1L;

	public static InternalPane getInstance() 
	   {
	      if(instance == null) {
	         instance = new InternalPane();
	      }
	      return instance;
	   }
	
	public void test_panel(){
		
		int i;
		for(i = 0;i < 1000; i++){
			   String num = Integer.toString(i);
			   JButton number = new JButton(num);
			   frame.add(number); 
			}
		this.validate();
	   }
}
