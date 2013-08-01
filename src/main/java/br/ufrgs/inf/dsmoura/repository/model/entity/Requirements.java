package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Requirements implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "requirements_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="requirements_generator")
	private Integer requirementsPk;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="requirementspk")
	private List<FunctionalRequirementDTO> functionalRequirementDTOs = new ArrayList<FunctionalRequirementDTO>();
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="usecase", referencedColumnName="usecasepk")
	private List<UseCaseDTO> useCaseDTOs = new ArrayList<UseCaseDTO>();
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="userinterface", referencedColumnName="requirementspk")
	private List<UserInterfaceDTO> userInterfaceDTOs = new ArrayList<UserInterfaceDTO>();
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="requirementspk")
	private List<NonFunctionalRequirementDTO> nonFunctionalRequirementDTOs = new ArrayList<NonFunctionalRequirementDTO>();
	
	@ManyToMany
   @JoinColumn(name="nonfunctionalrequirementpk")
	private List<InternationalizationTypeDTO> internationalizationTypeDTOs = new ArrayList<InternationalizationTypeDTO>();
	
	@ManyToMany
   @JoinColumn(name="nonfunctionalrequirementpk")
   private List<OperationalSystemTypeDTO> operationalSystemTypeDTOs = new ArrayList<OperationalSystemTypeDTO>();
	
	public Integer getRequirementsPk() {
		return requirementsPk;
	}

	public void setRequirementsPk(Integer requirementsPk) {
		this.requirementsPk = requirementsPk;
	}

	public List<FunctionalRequirementDTO> getFunctionalRequirementDTOs() {
		return functionalRequirementDTOs;
	}

	public void setFunctionalRequirementDTOs(
			List<FunctionalRequirementDTO> functionalRequirementDTOs) {
		this.functionalRequirementDTOs = functionalRequirementDTOs;
	}
	
	public List<UseCaseDTO> getUseCaseDTOs() {
		return useCaseDTOs;
	}

	public void setUseCaseDTOs(List<UseCaseDTO> useCaseDTOs) {
		this.useCaseDTOs = useCaseDTOs;
	}

	public List<UserInterfaceDTO> getUserInterfaceDTOs() {
		return userInterfaceDTOs;
	}

	public void setUserInterfaceDTOs(List<UserInterfaceDTO> userInterfaceDTOs) {
		this.userInterfaceDTOs = userInterfaceDTOs;
	}

	public List<NonFunctionalRequirementDTO> getNonFunctionalRequirementDTOs() {
		return nonFunctionalRequirementDTOs;
	}

	public void setNonFunctionalRequirementDTOs(
			List<NonFunctionalRequirementDTO> nonFunctionalRequirementDTOs) {
		this.nonFunctionalRequirementDTOs = nonFunctionalRequirementDTOs;
	}

	public List<InternationalizationTypeDTO> getInternationalizationTypeDTOs() {
		return internationalizationTypeDTOs;
	}

	public void setInternationalizationTypeDTOs(
			List<InternationalizationTypeDTO> internationalizationTypeDTOs) {
		this.internationalizationTypeDTOs = internationalizationTypeDTOs;
	}

	public List<OperationalSystemTypeDTO> getOperationalSystemTypeDTOs() {
		return operationalSystemTypeDTOs;
	}

	public void setOperationalSystemTypeDTOs(
			List<OperationalSystemTypeDTO> operationalSystemTypeDTOs) {
		this.operationalSystemTypeDTOs = operationalSystemTypeDTOs;
	}
	
}
