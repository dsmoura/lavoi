package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.*;

@Entity
public class OperationDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "operation_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="operation_generator")
	private Integer operationPk;
	
	@Column(nullable=false)
	private String name;
	
	@Column
	private Boolean initiatesTransaction;
	
	@Column
	private String description;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="operationPk")
	private List<ConditionDTO> conditionDTOs = new ArrayList<ConditionDTO>();
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="operationPk")
	private List<ParameterDTO> operationDTOs = new ArrayList<ParameterDTO>();
	
	@Transient
	private Long randomID = new Random().nextLong();

	public Integer getOperationPk() {
		return operationPk;
	}

	public void setOperationPk(Integer operationPk) {
		this.operationPk = operationPk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getInitiatesTransaction() {
		return initiatesTransaction;
	}

	public void setInitiatesTransaction(Boolean initiatesTransaction) {
		this.initiatesTransaction = initiatesTransaction;
	}
	
	public String getInitiatesTransactionStr() {
		if (initiatesTransaction != null) {
			return initiatesTransaction ? "Yes" : "No";
		}
		return "-";
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ConditionDTO> getConditionDTOs() {
		return conditionDTOs;
	}

	public void setConditionDTOs(List<ConditionDTO> conditionDTOs) {
		this.conditionDTOs = conditionDTOs;
	}

	public List<ParameterDTO> getOperationDTOs() {
		return operationDTOs;
	}

	public void setOperationDTOs(List<ParameterDTO> operationDTOs) {
		this.operationDTOs = operationDTOs;
	}

	public Long getRandomID() {
		return randomID;
	}

	public void setRandomID(Long randomID) {
		this.randomID = randomID;
	}
	
}
