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
public class ConditionDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "condition_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="condition_generator")
	private Integer conditionPk;
	
	@Column(nullable=false)
	private String type;

	@Column(nullable=false)
	private String description;

	public Integer getConditionPk() {
		return conditionPk;
	}

	public void setConditionPk(Integer conditionPk) {
		this.conditionPk = conditionPk;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}