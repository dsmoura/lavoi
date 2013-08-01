package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.*;

import br.ufrgs.inf.dsmoura.repository.controller.util.FieldsUtil;


@Entity
@NamedQueries({
	@NamedQuery(name="FeedbackDTO.listConsumptionFeedbacksByUser",
                	query="SELECT f" +
                   			" FROM FeedbackDTO f" +
                   			" WHERE f.feedbackUserDTO.username = :username" +
                   			"     AND f.isForCertification = FALSE" +
                   			"     AND f.asset.isExcluded = FALSE")
})
public class FeedbackDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "feedback_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="feedback_generator")
	private Integer feedbackPk;
	
	@ManyToOne
	@JoinColumn(name="userpk", nullable=false)
	private UserDTO feedbackUserDTO;
	
	@ManyToOne
	@JoinColumn(name="assetpk", nullable=false)
	private Asset asset;
	
	@Column(nullable=false)
	private Boolean isForCertification;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="qualityinusePk", nullable=false)
	private QualityInUseDTO qualityInUseDTO = new QualityInUseDTO();
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="softwareproductqualityPk", nullable=false)
	private SoftwareProductQualityDTO softwareProductQualityDTO = new SoftwareProductQualityDTO();
	
	private Integer generalScore;
	
	private String comment;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;
	
	@Column(nullable=false)
	private Boolean hasFeedback;

	public Integer getFeedbackPk() {
		return feedbackPk;
	}

	public void setFeedbackPk(Integer feedbackPk) {
		this.feedbackPk = feedbackPk;
	}

	public UserDTO getFeedbackUserDTO() {
		return feedbackUserDTO;
	}

	public void setFeedbackUserDTO(UserDTO feedbackUserDTO) {
		this.feedbackUserDTO = feedbackUserDTO;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	
	public Boolean getIsForCertification() {
		return isForCertification;
	}

	public void setIsForCertification(Boolean isForCertification) {
		this.isForCertification = isForCertification;
	}

	public QualityInUseDTO getQualityInUseDTO() {
		return qualityInUseDTO;
	}

	public void setQualityInUseDTO(QualityInUseDTO qualityInUseDTO) {
		this.qualityInUseDTO = qualityInUseDTO;
	}

	public SoftwareProductQualityDTO getSoftwareProductQualityDTO() {
		return softwareProductQualityDTO;
	}

	public void setSoftwareProductQualityDTO(
			SoftwareProductQualityDTO softwareProductQualityDTO) {
		this.softwareProductQualityDTO = softwareProductQualityDTO;
	}

	public Integer getGeneralScore() {
		return generalScore;
	}

	public void setGeneralScore(Integer generalScore) {
		this.generalScore = generalScore;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
	
	public Date getDateTime() {
		if (date != null) {
			return date.getTime();
		}
		return null;
	}
	
	public String getStrDate() {
		return FieldsUtil.getStrDate(date);
	}

	public Boolean getHasFeedback() {
		return hasFeedback;
	}

	public void setHasFeedback(Boolean hasFeedback) {
		this.hasFeedback = hasFeedback;
	}
	
}
