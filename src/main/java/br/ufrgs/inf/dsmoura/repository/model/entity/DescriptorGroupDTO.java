package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.*;

@Entity
public class DescriptorGroupDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "descriptorgroup_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="descriptorgroup_generator")
	private Integer descriptorgroupPk;
	
	@Column(nullable=false)
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="descriptorgroupPk")
	private List<FreeFormDescriptorDTO> freeFormDescriptorDTOs = new ArrayList<FreeFormDescriptorDTO>();
	
	@Transient
	private Long randomID = new Random().nextLong();

	public Integer getDescriptorgroupPk() {
		return descriptorgroupPk;
	}

	public void setDescriptorgroupPk(Integer descriptorgroupPk) {
		this.descriptorgroupPk = descriptorgroupPk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FreeFormDescriptorDTO> getFreeFormDescriptorDTOs() {
		return freeFormDescriptorDTOs;
	}

	public void setFreeFormDescriptorDTOs(
			List<FreeFormDescriptorDTO> freeFormDescriptorDTOs) {
		this.freeFormDescriptorDTOs = freeFormDescriptorDTOs;
	}

	public Long getRandomID() {
		return randomID;
	}

	public void setRandomID(Long randomID) {
		this.randomID = randomID;
	}
	
}
