package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;

import javax.persistence.*;

@Entity
public class QualityInUseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "qualityinuse_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="qualityinuse_generator")
	private Integer qualityinusePk;
	
	private Integer effectivenessScore;
	
	private Integer efficiencyScore;
	
	private Integer satisfactionScore;
	
	private Integer safetyScore;
	
	private Integer contextCoverageScore;

	public Integer getQualityinusePk() {
		return qualityinusePk;
	}

	public void setQualityinusePk(Integer qualityinusePk) {
		this.qualityinusePk = qualityinusePk;
	}

	public Integer getEffectivenessScore() {
		return effectivenessScore;
	}

	public void setEffectivenessScore(Integer effectivenessScore) {
		this.effectivenessScore = effectivenessScore;
	}

	public Integer getEfficiencyScore() {
		return efficiencyScore;
	}

	public void setEfficiencyScore(Integer efficiencyScore) {
		this.efficiencyScore = efficiencyScore;
	}

	public Integer getSatisfactionScore() {
		return satisfactionScore;
	}

	public void setSatisfactionScore(Integer satisfactionScore) {
		this.satisfactionScore = satisfactionScore;
	}

	public Integer getSafetyScore() {
		return safetyScore;
	}

	public void setSafetyScore(Integer safetyScore) {
		this.safetyScore = safetyScore;
	}

	public Integer getContextCoverageScore() {
		return contextCoverageScore;
	}

	public void setContextCoverageScore(Integer contextCoverageScore) {
		this.contextCoverageScore = contextCoverageScore;
	}
	
}
