package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.*;

import br.ufrgs.inf.dsmoura.repository.controller.util.FieldsUtil;


@Entity
public class Usage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "usage_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="usage_generator")
	private Integer usagePk;
	
	@Column(length=16384, nullable=false)
	private String description;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="artifact", referencedColumnName="usagepk")
	private List<ArtifactDTO> artifactDTOs = new ArrayList<ArtifactDTO>();
	
	@Column(nullable=false)
	private String creatorUsername;
	
	@ManyToOne
	@JoinColumn(name="authoruserpk", referencedColumnName="userpk", nullable=false)
	private UserDTO authorUserDTO;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Calendar authorshipDate;
	
	@ManyToOne
	@JoinColumn(name="certifieruserpk", referencedColumnName="userpk")
	private UserDTO certifierUserDTO;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar certificationDate;
	
	@OneToMany
	@JoinColumn(name="usagepk")
	private List<AdjustmentDTO> adjustmentDTOs = new ArrayList<AdjustmentDTO>();
	
	@OneToMany
	@JoinColumn(name="usagepk")
	private List<ConsumptionDTO> consumptionDTOs = new ArrayList<ConsumptionDTO>();
	
	@OneToMany
	@JoinColumn(name="usagepk")
	private List<UserCommentDTO> userCommentDTOs = new ArrayList<UserCommentDTO>();

	public Integer getUsagePk() {
		return usagePk;
	}

	public void setUsagePk(Integer usagePk) {
		this.usagePk = usagePk;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<ArtifactDTO> getArtifactDTOs() {
		return artifactDTOs;
	}

	public void setArtifactDTOs(List<ArtifactDTO> artifactDTOs) {
		this.artifactDTOs = artifactDTOs;
	}
	
	public String getCreatorUsername() {
		return creatorUsername;
	}

	public void setCreatorUsername(String creatorUsername) {
		this.creatorUsername = creatorUsername;
	}

	public UserDTO getAuthorUserDTO() {
		return authorUserDTO;
	}

	public void setAuthorUserDTO(UserDTO authorUserDTO) {
		this.authorUserDTO = authorUserDTO;
	}

	public Calendar getAuthorshipDate() {
		return authorshipDate;
	}

	public void setAuthorshipDate(Calendar authorshipDate) {
		this.authorshipDate = authorshipDate;
	}
	
	public String getStrAuthorshipDate() {
		return FieldsUtil.getStrDate(authorshipDate);
	}

	public UserDTO getCertifierUserDTO() {
		return certifierUserDTO;
	}

	public void setCertifierUserDTO(UserDTO certifierUserDTO) {
		this.certifierUserDTO = certifierUserDTO;
	}

	public Calendar getCertificationDate() {
		return certificationDate;
	}

	public void setCertificationDate(Calendar certificationDate) {
		this.certificationDate = certificationDate;
	}
	
	public String getStrCertificationDate() {
		return FieldsUtil.getStrDate(certificationDate);
	}

	public List<AdjustmentDTO> getAdjustmentDTOs() {
		return adjustmentDTOs;
	}

	public void setAdjustmentDTOs(List<AdjustmentDTO> adjustmentDTOs) {
		this.adjustmentDTOs = adjustmentDTOs;
	}

	public List<ConsumptionDTO> getConsumptionDTOs() {
		return consumptionDTOs;
	}

	public void setConsumptionDTOs(List<ConsumptionDTO> consumptionDTOs) {
		this.consumptionDTOs = consumptionDTOs;
	}

	public List<UserCommentDTO> getUserCommentDTOs() {
		return userCommentDTOs;
	}

	public void setUserCommentDTOs(List<UserCommentDTO> userCommentDTOs) {
		this.userCommentDTOs = userCommentDTOs;
	}

}