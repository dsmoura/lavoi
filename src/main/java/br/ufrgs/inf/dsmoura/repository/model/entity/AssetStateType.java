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
public class AssetStateType implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "assetstatetype_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="assetstatetype_generator")
	private Integer assetstatetypePk;
	
	@Column(unique=true, nullable=false)
	private String name;

	public Integer getAssetstatetypePk() {
		return assetstatetypePk;
	}
	public void setAssetstatetypePk(Integer assetstatetypePk) {
		this.assetstatetypePk = assetstatetypePk;
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
				+ ((assetstatetypePk == null) ? 0 : assetstatetypePk.hashCode());
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
		AssetStateType other = (AssetStateType) obj;
		if (assetstatetypePk == null) {
			if (other.assetstatetypePk != null)
				return false;
		} else if (!assetstatetypePk.equals(other.assetstatetypePk))
			return false;
		return true;
	}
}