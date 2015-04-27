package ch.specchio.proc_modules;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
	
	public void AddSpecralPlot(SPECCHIOClient specchioClient, ArrayList<Space> spaces,ProgressReportDialog pr, JPanel spectralPlotPanel) throws SPECCHIOClientException {
		this.specchioClient = specchioClient;
		this.pr = pr;
		pr.setVisible(true);
		pr.set_progress(0);
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
		if (spectrumEnum.size() > 0) {
			setDisplayedIndex(1);
		}
		pr.set_progress(50);
		spectralPlotPanel.validate();
		spectralPlotPanel.repaint();
		spectralPlotPanel.add(rootPanel);
	
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
			
			if (!spectralPlots.containsKey(ss)) {
				// need to build the plot object for this space
				spectralPlots.put(ss, new SpectralLinePlot(ss, PLOT_WIDTH, PLOT_HEIGHT, null));
			}
			SpectralPlot sp = spectralPlots.get(ss);
			sp.plot(spectrumId);
			rootPanel.add(sp);
			// change the selected spectra
			ArrayList<Integer> spectrumIdList = new ArrayList<Integer>();
			spectrumIdList.add(spectrumId);
			
			pr.set_progress(100);
			pr.setVisible(false);
			
		}
		catch (SPECCHIOClientException ex) {
			// error contacting the server
		}
		
	}
	
	public void draw_plot(){
		
	}

}
