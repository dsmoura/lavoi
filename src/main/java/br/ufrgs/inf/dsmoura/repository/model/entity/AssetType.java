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
public class AssetType implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "assettype_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="assettype_generator")
	private Integer assettypePk;
	
	@Column(unique=true, nullable=false)
	private String name;
	
	public Integer getAssettypePk() {
		return assettypePk;
	}
	public void setAssettypePk(Integer assettypePk) {
		this.assettypePk = assettypePk;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString() {
		return this.getName();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((assettypePk == null) ? 0 : assettypePk.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssetType other = (AssetType) obj;
		if (assettypePk == null) {
			if (other.assettypePk != null)
				return false;
		} else if (!assettypePk.equals(other.assettypePk))
			return false;
		return true;
	}
}