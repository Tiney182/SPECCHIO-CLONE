package ch.specchio.file.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ch.specchio.constants.FileTypes;
import ch.specchio.constants.HeaderBody;
import ch.specchio.constants.TimeFormats;
import ch.specchio.spaces.Space;
import ch.specchio.types.MetaDatatype;
import ch.specchio.types.MetaDate;
import ch.specchio.types.MetaParameter;
import ch.specchio.types.Spectrum;


/**
 * Class for writing spectral data as a comma-separated values.
 */
public abstract class CsvWriter extends SpectrumWriter {
	
	/** separator character */
	private static String separator = ",";
	
	/** buffered writer for output */
	private BufferedWriter bw;
	
	/**
	 * Constructor.
	 *
	 * @param os		the output stream upon which to write
	 * @param header	HeaderBody.Header or HeaderBody.Body
	 */
	protected CsvWriter(OutputStream os, int header) {
		
		super(os, FileTypes.CSV, header);
		
		// create a buffered writer for output
		bw = new BufferedWriter(new OutputStreamWriter(os));
		
	}
	
	
	/**
	 * Finish writing the current space.
	 * 
	 * @throws IOException	could not write to output
	 */
	public void endSpace() throws IOException {
		
		// flush the buffer
		bw.flush();
		
		super.endSpace();
		
	}
	
	
	/**
	 * Get a new instance of a CSV writer
	 * 
	 * @param os		the output stream upon which to write
	 * @param header	HeaderBody.Header or HeaderBody.Body
	 * 
	 * @throws IllegalArgumentException invalid value for header
	 */
	public static CsvWriter newInstance(OutputStream os, int header) {
		
		if (header == HeaderBody.Header) {
			return new CsvHdrWriter(os);
		} else if (header == HeaderBody.Body) {
			return new CsvBodyWriter(os);
		} else {
			throw new IllegalArgumentException("Unrecognised header-body value passed to CsvWriter.newInstance().");
		}
	}
	
	
	/**
	 * Write a field.
	 * 
	 * @param value	the value of the field
	 * 
	 * @throws IOException	could not write to the output
	 */
	protected void writeField(String value) throws IOException {
		
		// escape special characters
		boolean specialCharacters = false;
		String escapedValue = null;
		if (value != null) {
			specialCharacters = value.contains(separator) || value.contains("\n") || value.contains("\"");
			escapedValue = value.replaceAll("\"", "\"\"");
		}
		
		// output the value, with quotes if necessary
		if (specialCharacters) {
			bw.write("\"");
		}
		if (escapedValue != null) {
			bw.write(escapedValue);
		}
		if (specialCharacters) {
			bw.write("\"");
		}
		
	}
	
	
	/**
	 * Write a field separator.
	 * 
	 * @throws IOException	could not write to the output
	 */
	protected void writeFieldSeparator() throws IOException {
		
		bw.write(separator);
		
	}
	
	
	/**
	 * Write a record separator.
	 * 
	 * @throws IOException	could not write to the output
	 */
	protected void writeRecordSeparator() throws IOException {
		
		bw.newLine();
		
	}
	
}

		
/**
 * CSV header writer.
 */
class CsvHdrWriter extends CsvWriter {
	
	/** date formatter */
	private static final DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
	/** the list of spectra to be written out */
	private List<Spectrum> spectra;
	
	/** the set of metadata fields to be written out */
	private Set<String> metadataFields;
	
	/** the set of attribute names to be written out */
	private Set<String> attributeNames;
	
	/**
	 * Constructor.
	 * 
	 * @param os	the output stream being written to
	 */
	public CsvHdrWriter(OutputStream os) {
		
		super(os, HeaderBody.Header);
		
		// initialise member variables
		spectra = new LinkedList<Spectrum>();
		metadataFields = new TreeSet<String>();
		attributeNames = new TreeSet<String>();
		
	}
	
	
	/**
	 * Finish writing the current space.
	 * 
	 * @throws IOException	could not write to output
	 */
	public void endSpace() throws IOException {
		
		// write one row for every metadata field
		for (String metadataField : metadataFields) {
			
			try {
			
				// work out the name of this field
				String metadataName = null;
				Iterator<Spectrum> iter = spectra.iterator();
				while (metadataName == null && iter.hasNext()) {
					Spectrum s = iter.next();
					Object value = s.getMetadataValue(metadataField);
					if (value != null && value instanceof MetaDatatype) {
						// use the name supplied in this metadata value object
						metadataName = ((MetaDatatype<?>)value).name;
					}
				}
				if (metadataName == null) {
					// not a MetaDatatype; make up something from the field name
					metadataName = inferMetadataName(metadataField);
				}
				
				// write the name of the field
				writeField(metadataName);
				
				// write the field's value for each spectrum
				for (Spectrum s : spectra) {
				
					// write a field separator
					writeFieldSeparator();
					
					// write the fields's value
					Object value = s.getMetadataValue(metadataField);
					if (value != null) {
						writeField(value.toString());
					}
					
				}
			
			}
			catch (NoSuchMethodException ex) {
				// should never happen if Spectrum.METADATA_FIELDS is correct
				ex.printStackTrace();
			}
			
			// write end of the record
			writeRecordSeparator();
			
		}
		
		// write one row for every attribute
		for (String attributeName : attributeNames) {
			
			// write the name of the attribute
			writeField(attributeName);
			
			// write the attribute's value for each spectrum
			for (Spectrum s : spectra) {
				
				// write a field separator
				writeFieldSeparator();
				
				// write the attribute's value
				MetaParameter mp = s.getMetadata().get_first_entry(attributeName);
				if (mp != null && mp.getValue() != null) {
					
					if (mp instanceof MetaDate) {
						
						// output date according to the time format setting
						Date date = (Date)mp.getValue();
						if (getTimeFormat() == TimeFormats.Seconds) {
							writeField(Long.toString(date.getTime()));
						} else {
							writeField(df.format(date));
						}
					} else {
						
						// convert the value to its string form
						writeField(mp.getValue().toString());
					}
				}
				
			}
			
			// write the end of the record
			writeRecordSeparator();
			
		}
		
		super.endSpace();
		
	}
	
	
	/**
	 * Infer the name of a metadata field given its SQL field name.
	 * 
	 * @param field	a field name from Spectrum.METADATA_FIELDS
	 * 
	 * @return a name constructed from the input string
	 */
	private String inferMetadataName(String field) {
		
		StringBuffer sbuf = new StringBuffer();
		String parts[] = field.split("_");
		for (String part : parts) {
			if (!part.equals("id") && part.length() > 0) {
				
				if (sbuf.length() > 0) {
					// insert a space
					sbuf.append(' ');
				}
				
				// capitalise the first letter of the field name
				sbuf.append(part.substring(0, 1).toUpperCase());
				
				if (part.length() > 1) {
					// append the rest of the field name in its ordinary case
					sbuf.append(part.substring(1));
				}
			}
			
		}
		
		return sbuf.toString();
		
	}
	
	
	/**
	 * Start writing a new space.
	 * 
	 * @param space	the space
	 * 
	 * @throws IOException	could not write to output
	 */
	public void startSpace(Space spaceIn) throws IOException {
		
		super.startSpace(spaceIn);
		
		// reset lists
		spectra.clear();
		metadataFields.clear();
		attributeNames.clear();
		
	}
	
	
	/**
	 * Write a spectrum to the output stream.
	 * 
	 * @param sp	the spectrum to be written
	 * 
	 * @throws IOException	could not write to the output stream
	 */
	public void writeSpectrum(Spectrum s) throws IOException {
		
		// add the spectrum to the list to be written
		spectra.add(s);
		
		// make sure all of this spectrum's non-null metadata is included in the set to be output
		for (String field : Spectrum.METADATA_FIELDS) {
			try {
				if (s.getMetadataId(field) != 0) {
					metadataFields.add(field);
				}
			}
			catch (NoSuchMethodException ex) {
				// should never happen if Spectrum.METADATA_FIELDS is correct
				ex.printStackTrace();
			}
		}
		
		// make sure all of this spectrum's non-binary attributes are contained in the set to be output
		for (MetaParameter mp : s.getMetadata().getEntries()) {
			if (!"binary_val".equals(mp.getDefaultStorageField())) {
				attributeNames.add(mp.getAttributeName());
			}
		}
		
	}

}


/**
 * CSV body writer.
 */
class CsvBodyWriter extends CsvWriter {
	
	/** number formatter */
	private static final DecimalFormat df = new java.text.DecimalFormat("###.#########");
	
	/** the list of spectra to be written out */
	private List<Spectrum> spectra;

	/**
	 * Constructor.
	 * 
	 * @param os	the output stream being written to
	 */
	public CsvBodyWriter(OutputStream os) {
		
		super(os, HeaderBody.Body);
		
		// initialise member variables
		spectra = new LinkedList<Spectrum>();
		
	}
	
	
	/**
	 * Finish writing the current space.
	 * 
	 * @throws IOException	could not write to output
	 */
	public void endSpace() throws IOException {
		
		// write one record for each channel
		for (int channel = 0; channel < getCurrentSpace().getDimensionality() ; channel++) {
			
			// write the dimension number for this row
			writeField(df.format(getCurrentSpace().get_dimension_number(channel)));
			
			// write the vector elements for this dimension for each spectrum
			for (Spectrum s : spectra) {
				writeFieldSeparator();
				writeField(df.format(getCurrentSpace().get_vector_element(s.getSpectrumId(), channel)));
			}
			
			// end the row
			writeRecordSeparator();
			
		}
		
		super.endSpace();
		
	}
	
	
	/**
	 * Start writing a new space.
	 * 
	 * @param space	the space
	 * 
	 * @throws IOException	could not write to output
	 */
	public void startSpace(Space spaceIn) throws IOException {
		
		super.startSpace(spaceIn);
		
		// reset lists
		spectra.clear();
		
	}
	
	
	/**
	 * Write a spectrum to the output stream.
	 * 
	 * @param sp	the spectrum to be written
	 * 
	 * @throws IOException	could not write to the output stream
	 */
	public void writeSpectrum(Spectrum s) throws IOException {
		
		// add the spectrum to the list to be output
		spectra.add(s);
		
	}

}
