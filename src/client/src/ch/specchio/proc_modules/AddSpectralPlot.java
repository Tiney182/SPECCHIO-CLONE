package ch.specchio.proc_modules;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ch.specchio.client.SPECCHIOClient;
import ch.specchio.client.SPECCHIOClientException;
import ch.specchio.gui.ProgressReportDialog;
import ch.specchio.plots.swing.SpectralLinePlot;
import ch.specchio.plots.swing.SpectralPlot;
import ch.specchio.spaces.Space;
import ch.specchio.spaces.SpectralSpace;

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
	private SpinnerModel sm;
	private JSpinner spin;
	private int current = 1;
	
	
	public void AddSpecralPlot(SPECCHIOClient specchioClient, ArrayList<Space> spaces, JPanel spectralPlotPanel) throws SPECCHIOClientException {
		this.specchioClient = specchioClient;		
		
		/** 
		 * Creation of Hash Tables and ArrayLists for spectrum read in from QueryBuilder
		 */
		
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
		
		/** 
		 * Creating of spinner and number model
		 * The number model is limited to the amount of spectrum in the spectrumEnum array list as not to throw 
		 * an out of bounds exception
		 * Spinner number model taken from with own values set
		 * http://stackoverflow.com/questions/15880844/how-to-limit-jspinner 
		 */
		sm = new SpinnerNumberModel(1, 1, spectrumEnum.size(),1 ); 
	    spin = new JSpinner(sm);
				
		/** 
		 * State Change listener used for the spinner
		 * When the state is changed will update and repaint the rootPanel with the current displayed index
		 */
	    
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
		 
		 /**
		  * If there is only one spectra to be shown remove the spinner and set the displayed index to one
		  */
		 
		if (spectrumEnum.size() == 1) {
			rootPanel.remove(spin);
			setDisplayedIndex(1);
		}
		
		/** 
		 * Creates the spectrum when the Enum list is greater than one
		 * Adds the spinner that will be used during the state change 
		 */
		if (spectrumEnum.size() > 1){		
			setDisplayedIndex(1);
			rootPanel.add(spin);
			rootPanel.revalidate();
			rootPanel.repaint();			
			int maxSpectra = spectrumEnum.size();
			JLabel jl = new JLabel("of " + maxSpectra + " Spectra");
			rootPanel.add(jl);
		}
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
				loadedSpaces.put(space, (SpectralSpace)specchioClient.loadSpace(space));
			}
			SpectralSpace ss = loadedSpaces.get(space);
			
			// plot the spectrum
			rootPanel.removeAll();
			if (!spectralPlots.containsKey(ss)) {
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
