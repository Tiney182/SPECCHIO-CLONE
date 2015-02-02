package ch.specchio.types;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;


/**
 * Base class for all kinds of campaign.
 */
@XmlRootElement(name="campaign")
@XmlSeeAlso({SpecchioCampaign.class})
public abstract class Campaign {
	
	/** campaign id */
	private int campaign_id;
	
	/** campaign name */
	private String name;
	
	/** campaign description */
	private String description;
	
	/** campaign investigator */
	private String investigator;
	
	/** path to campaign data */
	private String path;
	
	/** all paths from which this campaign data has been loaded */
	private ArrayList<String> knownPaths;
	
	/** research group */
	private ResearchGroup research_group;
	
	
	/**
	 * Default constructor.
	 */
	public Campaign()
	{
		this.knownPaths = new ArrayList<String>();
	}
	
	
	/**
	 * Constructor.
	 */
	public Campaign(int campaign_id, String name, String path) {
		
		this.campaign_id = campaign_id;
		this.name = name;
		this.path = path;
		this.knownPaths = new ArrayList<String>();
		this.knownPaths.add(path);
		
	}
	
	
	/**
	 * Get the campaign description.
	 * 
	 * @return the description of the campaign
	 */
	@XmlElement(name="description")
	public String getDescription() {
		
		return description;
		
	}
	
	
	/**
	 * Set the campaign description.
	 * 
	 * @param description	 the description of the campaign
	 */
	public void setDescription(String description) {
		
		this.description = description;
		
	}
	
	
	/**
	 * Get the campaign identifier.
	 * 
	 * @return the campaign identifier
	 */
	@XmlElement(name="campaign_id")
	public int getId() {
		
		return campaign_id;
		
	}
	
	
	/**
	 * Set the campaign identifier.
	 * 
	 * @param id  campaign identifier
	 */
	public void setId(int campaign_id) {
		
		this.campaign_id = campaign_id;
		
	}
	
	
	/**
	 * Get the campaign investigator.
	 * 
	 * @return the investigator of the campaign
	 */
	@XmlElement(name="investigator")
	public String getInvestigator() {
		
		return investigator;
		
	}
	
	
	/**
	 * Set the campaign investigator.
	 * 
	 * @param investigator	 the investigator of the campaign
	 */
	public void setInvestigator(String investigator) {
		
		this.investigator = investigator;
		
	}
	
	
	/**
	 * Get the list of paths from which this campaign has been loaded.
	 * 
	 * @return the list of paths
	 */
	@XmlElement(name="known_paths")
	public ArrayList<String> getKnownPaths() {
		
		return this.knownPaths;
		
	}
	
	
	/**
	 * Set the list of paths from which this campaign has been loaded.
	 * 
	 * @param paths	the list of paths
	 */
	public void setKnownPaths(ArrayList<String> paths) {
		
		this.knownPaths = paths;
		
	}
	
	
	/**
	 * Add a path to the list from which this campaign has been loaded.
	 * 
	 * @param path	the new path
	 */
	public void addKnownPath(String path) {
		
		if (!knownPaths.contains(path)) {
			knownPaths.add(path);
		}
		
	}
	
	
	/**
	 * Get the campaign name.
	 * 
	 * @return the name of the campaign
	 */
	@XmlElement(name="name")
	public String getName() {
		
		return name;
		
	}
	
	
	/**
	 * Set the campaign name.
	 * 
	 * @param name	 the name of the campaign
	 */
	public void setName(String name) {
		
		this.name = name;
		
	}
	
	
	/**
	 * Get the path to the campaign's data.
	 * 
	 * @return the path to the campaign's data
	 */
	@XmlElement(name="path")
	public String getPath() {
		
		return path;
		
	}
	
	
	/**
	 * Set the path to the campaign data.
	 * 
	 * @param path	the path to the campaign data
	 */
	public void setPath(String path) {
		
		this.path = path;
		addKnownPath(path);
		
	}
	
	
	/**
	 * Get the research group.
	 * 
	 * @return a ResearchGroup object
	 */
	@XmlElement(name="research_group")
	public ResearchGroup getResearchGroup() {
		
		return research_group;
		
	}
	
	
	/** 
	 * Set the research group.
	 * 
	 * @param research_group	the research group
	 */
	public void setResearchGroup(ResearchGroup research_group) {
		
		this.research_group = research_group;
		
	}
	
	
	/**
	 * Get the campaign type.
	 * 
	 * @return a string identifying the type of the campaign
	 */
	@XmlElement(name="campaign_type")
	public abstract String getType();
	
	
	/**
	 * Get a string that represents the campaign.
	 * 
	 * @returns the name of the campaign
	 */
	public String toString() {
		
		return name;
		
	}

}
