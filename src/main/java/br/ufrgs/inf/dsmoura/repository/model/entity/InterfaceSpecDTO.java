package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.*;

@Entity
public class InterfaceSpecDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "interfacespec_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="interfacespec_generator")
	private Integer interfacespecPk;
	
	@Column(nullable=false)
	private String name;
	
	@Column
	private String description;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="interfacespecPk")
	private List<OperationDTO> operationDTOs = new ArrayList<OperationDTO>();
	
	@Transient
	private Long randomID = new Random().nextLong();

	public Integer getInterfacespecPk() {
		return interfacespecPk;
	}

	public void setInterfacespecPk(Integer interfacespecPk) {
		this.interfacespecPk = interfacespecPk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<OperationDTO> getOperationDTOs() {
		return operationDTOs;
	}

	public void setOperationDTOs(List<OperationDTO> operationDTOs) {
		this.operationDTOs = operationDTOs;
	}

	public Long getRandomID() {
		return randomID;
	}

	public void setRandomID(Long randomID) {
		this.randomID = randomID;
	}
	
}
