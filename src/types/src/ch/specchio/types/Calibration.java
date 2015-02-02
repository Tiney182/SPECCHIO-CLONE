package ch.specchio.types;

import java.util.Date;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ch.specchio.jaxb.XmlDateAdapter;


/**
 * This class represents an instrument calibration.
 */
@XmlRootElement(name="calibration")
public class Calibration {
	
	private boolean includes_uncertainty = true;
	private int reference_id; 
	private int instrument_id; 
	private SpectralFile spec_file = null; // used for loading calibration data from spectral files
	private int calibration_number;
	
	private Date calibration_date;
	protected Integer calibration_id;
	protected int cal_factors_id;
	protected int uncertainty_id;		
	private int calibration_no;
	private String comments;	
	private double[] factors;
	private double[] uncertainty;
	private int measurement_unit_id;
	
	public Calibration() {
		calibration_id = 0;
	}
	
	
	@XmlElement(name="includes_uncertainty")
	public boolean getIncludesUncertainty() { return this.includes_uncertainty; }
	public void setIncludesUncertainty(boolean includes_uncertainty) { this.includes_uncertainty = includes_uncertainty; }
	
	@XmlElement(name="reference_id")
	public int getReferenceId() { return this.reference_id; }
	public void setReferenceId(int reference_id) { this.reference_id = reference_id; }
	
	@XmlElement(name="spec_file")
	public SpectralFile getSpectralFile() { return this.spec_file; }
	public void setSpectralFile(SpectralFile spec_file) { this.spec_file = spec_file; }
	
	@XmlElement(name="instrument_id")
	public int getInstrumentId() { return this.instrument_id; }
	public void setInstrumentId(int instrument_id) { this.instrument_id = instrument_id; }

	@XmlElement(name="comments")
	public String getComments() {
		return comments; }
	public void setComments(String comments) {
		this.comments = comments; }

	@XmlElement(name="calibration_number")
	public int getCalibration_number() {
		return calibration_number; }
	public void setCalibration_number(int calibration_number) {
		this.calibration_number = calibration_number; }

	@XmlElement(name="calibration_id")
	public int getCalibration_id() {
		return calibration_id; }
	public void setCalibration_id(int calibration_id) {
		this.calibration_id = calibration_id; }
	
	@XmlElement(name="calibration_date")
	@XmlJavaTypeAdapter(XmlDateAdapter.class)
	public Date getCalibrationDate() { return this.calibration_date; }
	public void setCalibrationDate(Date calibration_date) { this.calibration_date = calibration_date; }
	
	@XmlElement(name="calibration_id")
	public Integer getCalibrationId() { return this.calibration_id; }
	public void setCalibrationId(Integer calibration_id) { this.calibration_id = calibration_id; }
	
	@XmlElement(name="calibration_no")
	public int getCalibrationNumber() { return this.calibration_no; }
	public void setCalibrationNumber(int calibration_no) { this.calibration_no = calibration_no; }
	
	@XmlElement(name="uncertainty_id")
	public int getUncertainty_id() {
		return uncertainty_id;
	}
	public void setUncertainty_id(int uncertainty_id) {
		this.uncertainty_id = uncertainty_id;
	}
	
	@XmlElement(name="cal_factors_id")
	public int getCalFactorsId() {
		return cal_factors_id;
	}
	public void setCalFactorsId(int cal_factors_id) {
		this.cal_factors_id = cal_factors_id;	
	}

	@XmlElement(name="factors")
	public double[] getFactors() {
		return factors;
	}
	public void setFactors(double[] factors) {
		this.factors = factors;
	}

	@XmlElement(name="uncertainty")
	public double[] getUncertainty() {
		return uncertainty;
	}
	public void setUncertainty(double[] uncertainty) {
		this.uncertainty = uncertainty;
	}

	@XmlElement(name="measurement_unit_id")
	public int getMeasurement_unit_id() {
		return measurement_unit_id;
	}
	public void setMeasurement_unit_id(int measurement_unit_id) {
		this.measurement_unit_id = measurement_unit_id;
	}

}
