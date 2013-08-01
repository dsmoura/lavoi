package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQueries({
	   @NamedQuery(name="SystemPropertyDTO.getByKey",
	       query="SELECT s" +
	       			" FROM SystemPropertyDTO s" +
	       			"  WHERE s.key = :key")
		})
public class SystemPropertyDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "systemproperty_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="systemproperty_generator")
	private Integer systempropertyPk;
	
	@Column(unique=true, nullable=false)
	private String key;
	
	@Column(nullable=false)
	private String value;

	public Integer getSystempropertyPk() {
		return systempropertyPk;
	}

	public void setSystempropertyPk(Integer systempropertyPk) {
		this.systempropertyPk = systempropertyPk;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}