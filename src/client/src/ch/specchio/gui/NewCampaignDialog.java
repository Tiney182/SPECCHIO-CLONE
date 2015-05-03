package ch.specchio.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

import ch.specchio.client.SPECCHIOClient;
import ch.specchio.client.SPECCHIOClientException;
import ch.specchio.file.reader.campaign.SpecchioCampaignDataLoader;
import ch.specchio.types.Campaign;


public class NewCampaignDialog extends JFrame implements ActionListener
{
	
	private static final long serialVersionUID = 1L;
	JTextField study_name;
	JTextArea study_description;
	JTextField main_directory;
	JComboBox file_format;
	JComboBox sensor;
	final JFileChooser fc;
	String path;
	GridbagLayouter l;
	Campaign campaign;
	JFrame app_frame;
	SPECCHIOClient specchio_client;
	
	//need be shown and hidden must instantiate now 
	protected JProgressBar progress_bar = new JProgressBar();
	protected JTextArea progress_text = new JTextArea();
	protected JSeparator seperate = new JSeparator();
	protected JScrollPane text_scroll = new JScrollPane(progress_text);
	
	
	public NewCampaignDialog(Campaign c, JFrame app_frame) throws SPECCHIOClientException
	{
		
		super("Create new campaign");
		
		// get a reference to the application's client object
		this.specchio_client = SPECCHIOApplication.getInstance().getClient();
		
		this.campaign = c;
		this.app_frame = app_frame;
		
		
		fc = new JFileChooser();
		l = new GridbagLayouter(this);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		constraints.gridwidth = 1;
		constraints.insets = new Insets(4, 4, 4, 4);
		constraints.gridheight = 1;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.gridx = 0;
		constraints.gridy = 0;
		l.insertComponent(new JLabel("Campaign name:"), constraints);
		
		
		constraints.gridwidth = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 1;
		constraints.gridy = 0;		
		study_name = new JTextField(20);
		l.insertComponent(study_name, constraints);
		
		//study_name.setBackground(Color.red);
				
		constraints.gridwidth = 1;		
		constraints.gridx = 0;
		constraints.gridy = 1;		
		l.insertComponent(new JLabel("Main directory:"), constraints);
		
		constraints.gridwidth = 2;
		main_directory = new JTextField(20);   
		constraints.gridx = 1;	
		l.insertComponent(main_directory, constraints);
		
		constraints.gridx = 3;	
		JButton browse = new JButton("Browse");
		l.insertComponent(browse, constraints);
		browse.setActionCommand("browse");
		browse.addActionListener(this);
		
		constraints.gridwidth = 1;
		constraints.gridx = 1;
		constraints.gridy = 2;	
		JButton create = new JButton("Create");
		l.insertComponent(create, constraints);
		create.setActionCommand("create");
		create.addActionListener(this);
		
		constraints.gridx = 2;
		JButton create_and_load = new JButton("Create & Load");
		l.insertComponent(create_and_load, constraints);
		create_and_load.setActionCommand("create_and_load");
		create_and_load.addActionListener(this);		
		
		constraints.gridx = 3;
		JButton cancel = new JButton("Cancel");
		l.insertComponent(cancel , constraints);
		cancel.setActionCommand("cancel");
		cancel.addActionListener(this);
		
		constraints.gridy = 3;
		constraints.gridx = 0;
		constraints.gridwidth = 4;
		l.insertComponent(seperate, constraints);
		seperate.setVisible(true);
		
		constraints.gridy = 4;
		constraints.gridx = 0;
		constraints.gridwidth = 4;
		l.insertComponent(progress_bar, constraints);
		progress_bar.setVisible(true);
		
		constraints.gridy = 5;
		constraints.gridx = 0;
		constraints.gridwidth = 4;
		constraints.ipady = 60;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		progress_text.setEditable(false);
		l.insertComponent(text_scroll, constraints);
		text_scroll.setVisible(true);
		text_scroll.setPreferredSize(new java.awt.Dimension(350,60));
		
		
		pack();
		setResizable(false);
				
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		
		if ("cancel".equals(e.getActionCommand())) {
			this.setVisible(false);
			
//			SpecchioCampaignDataLoader cdl = new SpecchioCampaignDataLoader(new LoadCampaignDataHandler(),specchio_client,progress_bar,progress_text);
//			cdl.update_progress_bar();
			
		} 
		if ("browse".equals(e.getActionCommand())) {
			
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = fc.showOpenDialog(this);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				path = file.getPath();
				main_directory.setText(path);
			}	    	
		} 	    
		if ("create".equals(e.getActionCommand()) || "create_and_load".equals(e.getActionCommand())) 
		{
			// should do some check here if all needed fields are filled ...
			
			try {
				// configure the campaign object
				campaign.setName(study_name.getText());
				campaign.setPath(main_directory.getText());
				
				// insert the campaign into the database and save its identifier
				int campaign_id = specchio_client.insertCampaign(campaign);
				campaign.setId(campaign_id);
				
				if ("create_and_load".equals(e.getActionCommand())) {
						
					// load the campaign data
					progress_bar.setIndeterminate(true);
					SpecchioCampaignDataLoader cdl = new SpecchioCampaignDataLoader(new LoadCampaignDataHandler(),specchio_client,progress_bar,progress_text);
					cdl.set_campaign(campaign);
					cdl.start();
					
					// close the dialog
//					this.setVisible(false);
				}
				
				// report success
//				JOptionPane.showMessageDialog(app_frame, "New campaign '" + study_name.getText() + "' created successfully.");
			}
			catch (SPECCHIOClientException ex) {
				JOptionPane.showMessageDialog(
		    			SPECCHIOApplication.getInstance().get_frame(),
		    			ex.getMessage(),
		    			"Error",
		    			JOptionPane.ERROR_MESSAGE
		    		);
		    }
			
		}
		
	} 
	
	
}

