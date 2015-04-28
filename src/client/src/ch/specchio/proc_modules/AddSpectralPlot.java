package ch.specchio.proc_modules;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.jersey.spi.StringReader.ValidateDefaultValue;

import ch.specchio.client.SPECCHIOClient;
import ch.specchio.client.SPECCHIOClientException;
import ch.specchio.gui.ProgressReportBarPanel;
import ch.specchio.gui.ProgressReportDialog;
import ch.specchio.gui.ProgressReportPanel;
import ch.specchio.gui.SpectrumMetadataPanel;
import ch.specchio.plots.swing.SpectralLinePlot;
import ch.specchio.plots.swing.SpectralPlot;
import ch.specchio.spaces.Space;
import ch.specchio.spaces.SpectralSpace;
import ch.specchio.gui.QueryBuilder;

public class AddSpectralPlot {

	/** the unloaded spaces corresponding to the indices of the spectrum enumeration */
	public List<Space> spectrumEnumSpaces;	
	private Hashtable<Space, SpectralSpace> loadedSpaces;
	public Hashtable<SpectralSpace, SpectralPlot> spectralPlots;
	public SPECCHIOClient specchioClient;
	public List<Integer> spectrumEnum;
	public int PLOT_HEIGHT = 200;
	public int PLOT_WIDTH = 300;
	public JPanel rootPanel = new JPanel();
	public JFrame testFrame = new JFrame();
	public ProgressReportDialog pr;
//	private JSpinner spin;
	private SpinnerModel sm;
	private JSpinner spin;
	private int current = 1;
	
	
	public void AddSpecralPlot(SPECCHIOClient specchioClient, ArrayList<Space> spaces,ProgressReportDialog pr, JPanel spectralPlotPanel) throws SPECCHIOClientException {
		this.specchioClient = specchioClient;
		this.pr = pr;		
		
		spectrumEnum = new ArrayList<Integer>();
		spectrumEnumSpaces = new ArrayList<Space>();
		loadedSpaces = new Hashtable<Space, SpectralSpace>();
		spectralPlots = new Hashtable<SpectralSpace, SpectralPlot>();
		for (Space space : spaces) {
			for (Integer id : space.getSpectrumIds()) {
				spectrumEnum.add(id);
				spectrumEnumSpaces.add(space);
			}
		}
		
		sm = new SpinnerNumberModel(1, 1, spectrumEnum.size(),1 ); 
	    spin = new JSpinner(sm);
		
	
		
		//Spinner number model
		//http://stackoverflow.com/questions/15880844/how-to-limit-jspinner 
		
		ChangeListener listener = new ChangeListener() {				
		      public void stateChanged(ChangeEvent e) {
		    	  current = (Integer)spin.getValue();
		    	  setDisplayedIndex(current);
					rootPanel.add(spin);
					rootPanel.revalidate();
					rootPanel.repaint();			
					int maxSpectra = spectrumEnum.size();
					JLabel jl = new JLabel("of " + maxSpectra + " Spectra");
					rootPanel.add(jl);
		    	  }	
		    };
		 spin.addChangeListener(listener);
		 
		if (spectrumEnum.size() == 1) {
			rootPanel.remove(spin);
			setDisplayedIndex(1);
		}
		
		if (spectrumEnum.size() > 1){		
			setDisplayedIndex(1);
			rootPanel.add(spin);
			rootPanel.revalidate();
			rootPanel.repaint();			
			int maxSpectra = spectrumEnum.size();
			JLabel jl = new JLabel("of " + maxSpectra + " Spectra");
			rootPanel.add(jl);
		}
		pr.set_progress(50);
		spectralPlotPanel.add(rootPanel);
		rootPanel.setVisible(true);
		spectralPlotPanel.validate();
		spectralPlotPanel.repaint();
	
	}
	
	
	
	public void setDisplayedIndex(int index) {
		
		try {
			
			// get the space and spectrum identifier to which this index refers
			Space space = spectrumEnumSpaces.get(index - 1);
			Integer spectrumId = spectrumEnum.get(index - 1);
			
			// get the loaded space object for this spectrum
			if (!loadedSpaces.containsKey(space)) {
				// need to load the space from the server
				
				loadedSpaces.put(space, (SpectralSpace)specchioClient.loadSpace(space));
			}
			SpectralSpace ss = loadedSpaces.get(space);
			
			// plot the spectrum
			rootPanel.removeAll();
			if (!spectralPlots.containsKey(ss)) {
				// need to build the plot object for this space
				spectralPlots.put(ss, new SpectralLinePlot(ss, PLOT_WIDTH, PLOT_HEIGHT, null));
			}
			SpectralPlot sp = spectralPlots.get(ss);
			sp.plot(spectrumId);
			rootPanel.add(sp);
			rootPanel.validate();
			rootPanel.repaint();
		}
		catch (SPECCHIOClientException ex) {
			// error contacting the server
		}
		
	}
	
	

}
