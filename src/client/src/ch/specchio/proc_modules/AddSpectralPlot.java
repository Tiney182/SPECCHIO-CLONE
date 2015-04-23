package ch.specchio.proc_modules;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import ch.specchio.client.SPECCHIOClient;
import ch.specchio.client.SPECCHIOClientException;
import ch.specchio.gui.ErrorDialog;
import ch.specchio.interfaces.ProgressReportInterface;
import ch.specchio.plots.swing.SpectralLinePlot;
import ch.specchio.plots.swing.SpectralPlot;
import ch.specchio.spaces.Space;
import ch.specchio.spaces.SpectralSpace;

public class AddSpectralPlot {

	/** the unloaded spaces corresponding to the indices of the spectrum enumeration */
	private List<Space> spectrumEnumSpaces;	
	private Hashtable<Space, SpectralSpace> loadedSpaces;
	private Hashtable<SpectralSpace, SpectralPlot> spectralPlots;
	private SPECCHIOClient specchioClient;
	private List<Integer> spectrumEnum;
	private int PLOT_HEIGHT = 200;
	private int PLOT_WIDTH = 300;
	
	public void AddSpecralPlot(SPECCHIOClient specchioClient, ArrayList<Space> spaces, ProgressReportInterface pr) throws SPECCHIOClientException {
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
	}
	
	private void setDisplayedIndex(int index) {
		
		
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
			
			// change the selected spectra
			ArrayList<Integer> spectrumIdList = new ArrayList<Integer>();
			spectrumIdList.add(spectrumId);
			
			// tell the metadata panel to display the new spectrum
			
			
			
			
			
		}
		catch (SPECCHIOClientException ex) {
			// error contacting the server
		}
		
	}

}
