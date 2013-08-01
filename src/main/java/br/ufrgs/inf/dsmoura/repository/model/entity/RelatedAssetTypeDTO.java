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
public class RelatedAssetTypeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "relatedassettype_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="relatedassettype_generator")
	private Integer relatedassettypePk;
	
	@Column(unique=true, nullable=false)
	private String name;
	
	public Integer getRelatedassettypePk() {
		return relatedassettypePk;
	}

	public void setRelatedassettypePk(Integer relatedassettypePk) {
		this.relatedassettypePk = relatedassettypePk;
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
		result = prime
				* result
				+ ((relatedassettypePk == null) ? 0 : relatedassettypePk.hashCode());
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
		RelatedAssetTypeDTO other = (RelatedAssetTypeDTO) obj;
		if (relatedassettypePk == null) {
			if (other.relatedassettypePk != null)
				return false;
		} else if (!relatedassettypePk.equals(other.relatedassettypePk))
			return false;
		return true;
	}
}