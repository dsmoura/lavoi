package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;

import javax.persistence.*;

@Entity
public class SoftwareProductQualityDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "softwareproductquality_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="softwareproductquality_generator")
	private Integer softwareproductqualityPk;
	
	private Integer functionalSuitabilityScore;
	
	private Integer performanceEfficiencyScore;
	
	private Integer compatibilityScore;
	
	private Integer usabilityScore;
	
	private Integer reliabilityScore;
	
	private Integer securityScore;
	
	private Integer maintainabilityScore;
	
	private Integer portabilityScore;

	public Integer getSoftwareproductqualityPk() {
		return softwareproductqualityPk;
	}

	public void setSoftwareproductqualityPk(Integer softwareproductqualityPk) {
		this.softwareproductqualityPk = softwareproductqualityPk;
	}

	public Integer getFunctionalSuitabilityScore() {
		return functionalSuitabilityScore;
	}

	public void setFunctionalSuitabilityScore(Integer functionalSuitabilityScore) {
		this.functionalSuitabilityScore = functionalSuitabilityScore;
	}

	public Integer getPerformanceEfficiencyScore() {
		return performanceEfficiencyScore;
	}

	public void setPerformanceEfficiencyScore(Integer performanceEfficiencyScore) {
		this.performanceEfficiencyScore = performanceEfficiencyScore;
	}

	public Integer getCompatibilityScore() {
		return compatibilityScore;
	}

	public void setCompatibilityScore(Integer compatibilityScore) {
		this.compatibilityScore = compatibilityScore;
	}

	public Integer getUsabilityScore() {
		return usabilityScore;
	}

	public void setUsabilityScore(Integer usabilityScore) {
		this.usabilityScore = usabilityScore;
	}

	public Integer getReliabilityScore() {
		return reliabilityScore;
	}

	public void setReliabilityScore(Integer reliabilityScore) {
		this.reliabilityScore = reliabilityScore;
	}

	public Integer getSecurityScore() {
		return securityScore;
	}

	public void setSecurityScore(Integer securityScore) {
		this.securityScore = securityScore;
	}

	public Integer getMaintainabilityScore() {
		return maintainabilityScore;
	}

	public void setMaintainabilityScore(Integer maintainabilityScore) {
		this.maintainabilityScore = maintainabilityScore;
	}

	public Integer getPortabilityScore() {
		return portabilityScore;
	}

	public void setPortabilityScore(Integer portabilityScore) {
		this.portabilityScore = portabilityScore;
	}

}
