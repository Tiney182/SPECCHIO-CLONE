package ch.specchio.proc_modules;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
//Basis of beginning of code taken from
//http://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java 






import ch.specchio.gui.SpectrumMetadataCategoryList;
import ch.specchio.interfaces.ProgressReportInterface;
import ch.specchio.metadata.MDE_Controller;
import ch.specchio.plots.swing.SpectralPlot;
import ch.specchio.spaces.Space;
import ch.specchio.spaces.SpectralSpace;


public class MapsProcessing {
	 String url = "http://www.google.com";
	 private List<Integer> spectrumEnum;
	 private List<Space> spectrumEnumSpaces;
	 private Hashtable<Space, SpectralSpace> loadedSpaces;
	 private Hashtable<SpectralSpace, SpectralPlot> spectralPlots;
	 private ProgressReportInterface pr;
	 private SpectrumMetadataCategoryList categoryList;
	 private MDE_Controller mdec;
	 
	 public MapsProcessing(){	 	
		 
	 	}
	 
	 public void get_location(){
		 ProgressReportInterface pr;
		 ArrayList<Space> spaces = new ArrayList<Space>();  
		 int latitude ;
		 int longitude;
		 
//		if(pr != null){
//			pr.set_operation("Oh my god he just went in");
//			pr.set_progress(0);
//		}
		
		spectrumEnum = new ArrayList<Integer>();
		spectrumEnumSpaces = new ArrayList<Space>();
		loadedSpaces = new Hashtable<Space, SpectralSpace>();
		spectralPlots = new Hashtable<SpectralSpace, SpectralPlot>();
		for (Space space : spaces) {
			for (Integer id : space.getSpectrumIds()) {
				spectrumEnum.add(id);
				spectrumEnumSpaces.add(space);
				System.out.print(space);
				System.out.print(id);
			}
		}
		
//		if(pr != null){
//			pr.set_operation("God damnit leeroy stick the the plan");
//			pr.set_progress(100);
//		}
			
//		categoryList = new SpectrumMetadataCategoryList(mdec.getFormFactory(), 3);
	 }
       

        public void open_window(){
        	
	        if(Desktop.isDesktopSupported()){
	            Desktop desktop = Desktop.getDesktop();
	            try {
	                desktop.browse(new URI(url));
	            } catch (IOException | URISyntaxException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        }else{
	            Runtime runtime = Runtime.getRuntime();
	            try {
	                runtime.exec("xdg-open " + url);
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	        
        }
    }
}