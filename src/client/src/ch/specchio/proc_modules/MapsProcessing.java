package ch.specchio.proc_modules;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class MapsProcessing {	 
	
	
		//Basis of beginning of code taken from
		//http://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java 
        public void open_maps(ArrayList<Object> nameArray, ArrayList<Object> latitudeArray, ArrayList<Object> longitudeArray){
        	
        	String url;
        	JTextArea locationtext = new JTextArea();
        	locationtext.setEditable(false);
        	String latitude;
        	String longitude;
        	String file_name;
        	
        	/** 
        	 * Change to list iterator for each instance of lat and long in the array
        	 */
        	if (latitudeArray.size() == 1 && longitudeArray.size() == 1){
        	 url = "https://www.google.co.uk/maps/place/" + latitudeArray.get(0) + "," + longitudeArray.get(0);
        	 if(Desktop.isDesktopSupported()){
 	            Desktop desktop = Desktop.getDesktop();
 	            try {
 	                desktop.browse(new URI(url));
 	            } catch (IOException | URISyntaxException e) {
 	                e.printStackTrace();
 	            }
 	        }else{
 	            Runtime runtime = Runtime.getRuntime();
 	            try {
 	                runtime.exec("xdg-open " + url);
 	            } catch (IOException e) {
 	                e.printStackTrace();
 	            }		        
 	        }
        	}
        	//Added check to ensure arrays are the same size otherwise would give false location data
        	if(latitudeArray.size() > 1 && longitudeArray.size() > 1){
	        	if (latitudeArray.size() == longitudeArray.size()){
		        	for (int i = 0; i<latitudeArray.size(); i++){		        		
		        		file_name = nameArray.get(i).toString();
		        		latitude = latitudeArray.get(i).toString();
		        		longitude = longitudeArray.get(i).toString();
		        		locationtext.append(latitude + "," + longitude + "{"+ file_name + "} " + "\n");
//		        		
		        		//If latitude array is the same size as i it has reached the end then run a new window
		        		if (i == latitudeArray.size() -1){
		        			locationtext.selectAll();		        			
		        			StringSelection stringSelection = new StringSelection (locationtext.getSelectedText());
		        			Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
		        			clpbrd.setContents (stringSelection, null);		        			
		        			
		        			/** 
		        			 * Create new frame informing user that data has been copied to clipboard.
		        			 */
		        			JFrame clipboardFrame = new JFrame();
			    			JOptionPane.showMessageDialog(clipboardFrame, "Location Data Copied To Clipboard, Paste into bulk input text box", "Clipboard Copied", JOptionPane.PLAIN_MESSAGE);
		        			
		        			url = "http://www.mapcustomizer.com/";
			               	 if(Desktop.isDesktopSupported()){
			        	            Desktop desktop = Desktop.getDesktop();
			        	            try {
			        	                desktop.browse(new URI(url));
			        	            } catch (IOException | URISyntaxException e) {
			        	                e.printStackTrace();
			        	            }
			        	        }else{
			        	            Runtime runtime = Runtime.getRuntime();
			        	            try {
			        	                runtime.exec("xdg-open " + url);
			        	            } catch (IOException e) {
			        	                e.printStackTrace();
			        	            }		        
			        	        }
			        		}		        		
			        	}
	        	} else {
	        		//Errorframe for arrays not having equal data
	        		JFrame errorFrame = new JFrame();
	    			JOptionPane.showMessageDialog(errorFrame, "Arrays do not have equal amounts of data, spectra location data invalid", "Location Error", JOptionPane.ERROR_MESSAGE);
	        	}
	        }
        	if (latitudeArray.size() == 0 || longitudeArray.size() == 0){
        		//Errorframe for no location data existing
        		JFrame errorFrame = new JFrame();
    			JOptionPane.showMessageDialog(errorFrame, "No location data exists for given spectra", "Location Error", JOptionPane.ERROR_MESSAGE);
        	}
	    }
}

	 