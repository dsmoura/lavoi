package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.util.Calendar;

import br.ufrgs.inf.dsmoura.repository.controller.util.FieldsUtil;


public class AssetSolrDTO implements Comparable<AssetSolrDTO> {
	private Float score;
	private Integer stars;
	private Float averageScore;
	private Integer reuseCounter;
	private String assetPk;
	private String id;
	private String name;
	private String type;
	private String state;
	private String softwareLicense;
	private Calendar date;
	private String version;
	private String shortDescription;
	private String description;
	private Calendar certificationDate;
	
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public Integer getStars() {
		return stars;
	}
	public void setStars(Integer stars) {
		this.stars = stars;
	}
	public Float getAverageScore() {
		return averageScore;
	}
	public void setAverageScore(Float averageScore) {
		this.averageScore = averageScore;
	}
	public String getAverageScoreNorm() {
		if (averageScore != null) {
			return FieldsUtil.normalizeAverageScore(averageScore);
		}
		return null;
	}
	public Integer getReuseCounter() {
		return reuseCounter;
	}
	public void setReuseCounter(Integer reuseCounter) {
		this.reuseCounter = reuseCounter;
	}
	public String getAssetPk() {
		return assetPk;
	}
	public void setAssetPk(String assetPk) {
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
	public String getNameNorm() {
		return FieldsUtil.normalize(name);
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
	public Integer getQualityStars() {
		if (this.averageScore != null) {
			if (this.averageScore <= 0) {
				return this.averageScore.intValue();
			} else {
				return (int) (averageScore*2);
			}
		}
		return null;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getState() {
		return state;
	}
	public Boolean getIsCertifiedState() {
		if (state != null) {
			return state.equalsIgnoreCase("Certified");
		}
		return false;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSoftwareLicense() {
		return softwareLicense;
	}
	public void setSoftwareLicense(String softwareLicense) {
		this.softwareLicense = softwareLicense;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
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
	public Calendar getCertificationDate() {
		return certificationDate;
	}
	public void setCertificationDate(Calendar certificationDate) {
		this.certificationDate = certificationDate;
	}
	public String getStrCertificationDate() {
		return FieldsUtil.getStrDate(this.certificationDate);
	}
	@Override
	public int compareTo(AssetSolrDTO o) {
		return this.getVersion().compareTo(o.getVersion());
	}
}
