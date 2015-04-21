package ch.specchio.test;


import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import ch.specchio.client.SPECCHIOClient;
import ch.specchio.proc_modules.MapsProcessing;

public class MapProcessingTest {
	
	String latitude = "12"; 
	String longitude = "11";
	MapsProcessing maps = new MapsProcessing();
	
	SPECCHIOClient specchio_client;
	@Test
	public void test_returned_spectra() {
		maps.correct_location_data(latitude, longitude);
	}

	
	public void test_spectra_id(ArrayList<Integer> ids){
		
	}
}
