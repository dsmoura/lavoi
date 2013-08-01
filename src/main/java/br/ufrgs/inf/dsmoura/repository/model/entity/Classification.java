package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Classification implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "classification_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="classification_generator")
	private Integer classificationPk;
	
	@ManyToMany
	@JoinColumn(name="applicationsubdomainpk")
	private List<ApplicationSubdomain> applicationSubdomains = new ArrayList<ApplicationSubdomain>();
	
	@Column
	private String otherApplicationDomain;
	
	@Column
	private String otherApplicationSubdomain;
	
	@ManyToMany
	@JoinColumn(name="projectpk")
	private List<ProjectDTO> projectDTOs = new ArrayList<ProjectDTO>();
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="classificationPk")
	private List<TagDTO> tagDTOs = new ArrayList<TagDTO>();
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="classificationPk")
	private List<DescriptorGroupDTO> descriptorGroupDTOs = new ArrayList<DescriptorGroupDTO>();
	
	@Column
	private Float averageScore;
	
	@Column(nullable=false)
	private Integer reuseCounter;
	

	public Integer getClassificationPk() {
		return classificationPk;
	}

	public void setClassificationPk(Integer classificationPk) {
		this.classificationPk = classificationPk;
	}

	public List<ApplicationSubdomain> getApplicationSubdomains() {
		return applicationSubdomains;
	}

	public void setApplicationSubdomains(List<ApplicationSubdomain> applicationSubdomains) {
		this.applicationSubdomains = applicationSubdomains;
	}
	
	public String getOtherApplicationDomain() {
		return otherApplicationDomain;
	}

	public void setOtherApplicationDomain(String otherApplicationDomain) {
		this.otherApplicationDomain = otherApplicationDomain;
	}

	public String getOtherApplicationSubdomain() {
		return otherApplicationSubdomain;
	}

	public void setOtherApplicationSubdomain(String otherApplicationSubdomain) {
		this.otherApplicationSubdomain = otherApplicationSubdomain;
	}

	public List<ProjectDTO> getProjectDTOs() {
		return projectDTOs;
	}

	public void setProjectDTOs(List<ProjectDTO> projectDTOs) {
		this.projectDTOs = projectDTOs;
	}

	public List<TagDTO> getTagDTOs() {
		return tagDTOs;
	}

	public void setTagDTOs(List<TagDTO> tagDTOs) {
		this.tagDTOs = tagDTOs;
	}

	public List<DescriptorGroupDTO> getDescriptorGroupDTOs() {
		return descriptorGroupDTOs;
	}

	public void setDescriptorGroupDTOs(List<DescriptorGroupDTO> descriptorGroupDTOs) {
		this.descriptorGroupDTOs = descriptorGroupDTOs;
	}

	public Float getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(Float averageScore) {
		this.averageScore = averageScore;
	}

	public Integer getReuseCounter() {
		return reuseCounter;
	}

	public void setReuseCounter(Integer reuseCounter) {
		this.reuseCounter = reuseCounter;
	}
	
}