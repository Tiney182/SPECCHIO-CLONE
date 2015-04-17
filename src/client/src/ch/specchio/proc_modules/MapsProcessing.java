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









import ch.specchio.client.SPECCHIOClient;
import ch.specchio.gui.ProgressReportDialog;
import ch.specchio.gui.SpectrumMetadataCategoryList;
import ch.specchio.interfaces.ProgressReportInterface;
import ch.specchio.metadata.MDE_Controller;
import ch.specchio.plots.swing.SpectralPlot;
import ch.specchio.spaces.Space;
import ch.specchio.spaces.SpectralSpace;


public class MapsProcessing {
	 String url = null;
	 
	 public MapsProcessing(){
	 }
	 
		 
	
        public void open_maps(String latitude, String longitude){
        	
        	url = "https://www.google.co.uk/maps/place/" + latitude + "," + longitude;
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

	 