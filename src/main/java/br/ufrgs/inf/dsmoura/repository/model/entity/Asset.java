package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.Index;

import br.ufrgs.inf.dsmoura.repository.controller.util.FieldsUtil;


@NamedQueries({
	@NamedQuery(name="Asset.findUniqueByPk",
                  query="SELECT a" +
                  			" FROM Asset a" +
                  			" WHERE a.assetPk = :assetPk"),
   @NamedQuery(name="Asset.findAssetPk",
					   query="SELECT a.assetPk" +
					   			" FROM Asset a" +
					   			" WHERE UPPER(a.id) = UPPER(:id)" +
					   			"	  AND UPPER(a.version) = UPPER(:version)"),
   @NamedQuery(name="Asset.findAssetByIDVersion",
                  query="SELECT a" +
                  			" FROM Asset a" +
                  			" WHERE UPPER(a.id) = UPPER(:id)" +
                  			"	  AND UPPER(a.version) = UPPER(:version)")
})
@Entity
public class Asset implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@SequenceGenerator(name = "asset_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="asset_generator")
	private Integer assetPk;
	
	@Index
	@Column(nullable=false)
	private String id;
	
	@Column(nullable=false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name="assettypepk", nullable=false)
	private AssetType type = new AssetType();
	
	@Column(nullable=true)
	private String otherType;
	
	@ManyToOne
	@JoinColumn(name="assetstatetypepk", nullable=false)
	private AssetStateType state = new AssetStateType();
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar creationDate;
	
	@Column(nullable=false)
	private String version;
	
	@ManyToOne
	@JoinColumn(name="softwarelicensePk", nullable=true)
	private SoftwareLicenseDTO softwareLicenseDTO;
	
	@Column(nullable=true)
	private String otherSoftwareLicense;
	
	@Column(nullable=false)
	private String accessRights;
	
	@Column(length=1024, nullable=false)
	private String shortDescription;
	
	@Column(length=16384, nullable=false)
	private String description;
	
	@Column(nullable=false)
	private Boolean isExcluded;

//	Order by RAS:
//	1. profile
//	2. description
//	3. classification
//	4. solution
//	5. usage
//	6. relatedAsset
	
	@OneToOne(cascade=CascadeType.ALL)
	private Classification classification = new Classification();
	
	@OneToOne(cascade=CascadeType.ALL)
	private Solution solution = new Solution();
	
	@OneToOne(cascade=CascadeType.ALL)
	private Usage usage = new Usage();
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="assetpk")
	private List<RelatedAsset> relatedAssets = new ArrayList<RelatedAsset>();
	
	@OneToOne(cascade=CascadeType.ALL)
	private EffortDTO effortDTO = new EffortDTO();
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="assetpk")
	private List<FeedbackDTO> feedbackDTOs = new ArrayList<FeedbackDTO>();

	public Integer getAssetPk() {
		return assetPk;
	}

	public void setAssetPk(Integer assetPk) {
		this.assetPk = assetPk;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
	
	public String getStrDate() {
		return FieldsUtil.getStrDate(this.date);
	}
	
	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}
	
	public String getStrCreationDate() {
		return FieldsUtil.getStrDate(this.creationDate);
	}

	public AssetStateType getState() {
		return state;
	}

	public void setState(AssetStateType state) {
		this.state = state;
	}
	
	public AssetType getType() {
		return type;
	}

	public void setType(AssetType type) {
		this.type = type;
	}
	
	public String getOtherType() {
		return otherType;
	}

	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public SoftwareLicenseDTO getSoftwareLicenseDTO() {
		return softwareLicenseDTO;
	}

	public void setSoftwareLicenseDTO(SoftwareLicenseDTO softwareLicenseDTO) {
		this.softwareLicenseDTO = softwareLicenseDTO;
	}
	
	public String getOtherSoftwareLicense() {
		return otherSoftwareLicense;
	}

	public void setOtherSoftwareLicense(String otherSoftwareLicense) {
		this.otherSoftwareLicense = otherSoftwareLicense;
	}

	public String getAccessRights() {
		return accessRights;
	}

	public void setAccessRights(String accessRights) {
		this.accessRights = accessRights;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Boolean getIsExcluded() {
		return isExcluded;
	}

	public void setIsExcluded(Boolean isExcluded) {
		this.isExcluded = isExcluded;
	}

	public Classification getClassification() {
		return classification;
	}

	public void setClassification(Classification classification) {
		this.classification = classification;
	}
	
	public List<RelatedAsset> getRelatedAssets() {
		return relatedAssets;
	}

	public void setRelatedAssets(List<RelatedAsset> relatedAssets) {
		this.relatedAssets = relatedAssets;
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}

	public Usage getUsage() {
		return usage;
	}

	public void setUsage(Usage usage) {
		this.usage = usage;
	}
	
	public EffortDTO getEffortDTO() {
		return effortDTO;
	}

	public void setEffortDTO(EffortDTO effortDTO) {
		this.effortDTO = effortDTO;
	}

	public List<FeedbackDTO> getFeedbackDTOs() {
		return feedbackDTOs;
	}

	public void setFeedbackDTOs(List<FeedbackDTO> feedbackDTOs) {
		this.feedbackDTOs = feedbackDTOs;
	}
	
	public List<FeedbackDTO> getFeedbackList() {
		if (feedbackDTOs != null) {
			List<FeedbackDTO> list = new ArrayList<FeedbackDTO>();
			for (FeedbackDTO f : feedbackDTOs) {
				if (f.getHasFeedback()) {
					list.add(f);
				}
			}
			return list;
		}
		return feedbackDTOs;
	}
	
}