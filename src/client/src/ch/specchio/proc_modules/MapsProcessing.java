package ch.specchio.proc_modules;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
//Basis of beginning of code taken from
//http://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java 


public class MapsProcessing {
	 String url = "http://www.google.com";

     
       

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