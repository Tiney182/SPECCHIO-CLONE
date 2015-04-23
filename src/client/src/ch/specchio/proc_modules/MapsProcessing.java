package ch.specchio.proc_modules;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.script.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MapsProcessing {	 
	
		
	
		
	
		//Basis of beginning of code taken from
		//http://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java 
        public void open_maps(String latitude, String longitude){
        	
        	String url = "https://www.google.co.uk/maps/place/" + latitude + "," + longitude;
	        
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
        public void no_location(String latitude, String longitude){
        	
        if (latitude == "[]" || longitude == "[]"){
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "No Location Data Available for Given Spectra", "Location Error", JOptionPane.ERROR_MESSAGE);
			}
        }
        
        public void correct_location_data(String latitude, String longitude){
        
        if (latitude != "[]" || longitude != "[]"){
			latitude = latitude.substring(1);
			latitude = latitude.substring(0, latitude.length() - 1);
			longitude = longitude.substring(1);
			longitude = longitude.substring(0, longitude.length() - 1);
			open_maps(latitude, longitude);
			}
      }
}

	 