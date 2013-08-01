package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class ParameterDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "parameter_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="parameter_generator")
	private Integer parameterPk;
	
	@Column(nullable=false)
	private String name;

	@Column
	private String type;
	
	@Column
	private String direction;

	public Integer getParameterPk() {
		return parameterPk;
	}

	public void setParameterPk(Integer parameterPk) {
		this.parameterPk = parameterPk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
}