package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.Random;

import javax.persistence.*;

@Entity
public class FreeFormDescriptorDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "freeformdescriptor_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="freeformdescriptor_generator")
	private Integer freeformdescriptorPk;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false)
	private String freeFormValue;
	
	@Transient
	private Long randomID = new Random().nextLong();

	public Integer getFreeformdescriptorPk() {
		return freeformdescriptorPk;
	}

	public void setFreeformdescriptorPk(Integer freeformdescriptorPk) {
		this.freeformdescriptorPk = freeformdescriptorPk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFreeFormValue() {
		return freeFormValue;
	}

	public void setFreeFormValue(String freeFormValue) {
		this.freeFormValue = freeFormValue;
	}

	public Long getRandomID() {
		return randomID;
	}

	public void setRandomID(Long randomID) {
		this.randomID = randomID;
	}
	
}
