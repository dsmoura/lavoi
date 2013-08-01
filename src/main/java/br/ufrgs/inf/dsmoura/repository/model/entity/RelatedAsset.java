package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.String;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
public class RelatedAsset implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@SequenceGenerator(name = "relatedasset_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="relatedasset_generator")
	private Integer relatedAssetPk;
	
	@Column(nullable=false)
	private String id;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false)
	private String version;
	
	@Column(nullable=false)
	private String reference;
	
	@ManyToOne
	@JoinColumn(name="relatedassettypePk")
	private RelatedAssetTypeDTO relatedAssetTypeDTO = new RelatedAssetTypeDTO();
	
	@Transient
	private Long randomID = new Random().nextLong();
	
	public Integer getRelatedAssetPk() {
		return relatedAssetPk;
	}

	public void setRelatedAssetPk(Integer relatedAssetPk) {
		this.relatedAssetPk = relatedAssetPk;
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
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public RelatedAssetTypeDTO getRelatedAssetTypeDTO() {
		return relatedAssetTypeDTO;
	}

	public void setRelatedAssetTypeDTO(RelatedAssetTypeDTO relatedAssetTypeDTO) {
		this.relatedAssetTypeDTO = relatedAssetTypeDTO;
	}

	public Long getRandomID() {
		return randomID;
	}

	public void setRandomID(Long randomID) {
		this.randomID = randomID;
	}
	
}
