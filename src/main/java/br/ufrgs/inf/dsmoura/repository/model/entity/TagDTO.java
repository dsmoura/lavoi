package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;

import javax.persistence.*;

@Entity
public class TagDTO implements Serializable, Comparable<TagDTO> {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "tag_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="tag_generator")
	private Integer tagPk;
	
	@Column(nullable=false)
	private String name;
	
	@Transient
	private Long count;
	
	@Transient
	private Float totalProportion;

	public Integer getTagPk() {
		return tagPk;
	}

	public void setTagPk(Integer tagPk) {
		this.tagPk = tagPk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Float getTotalProportion() {
		return totalProportion;
	}

	public void setTotalProportion(Float totalProportion) {
		this.totalProportion = totalProportion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tagPk == null) ? 0 : tagPk.hashCode());
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
		TagDTO other = (TagDTO) obj;
		if (tagPk == null) {
			if (other.tagPk != null)
				return false;
		} else if (!tagPk.equals(other.tagPk))
			return false;
		return true;
	}

	@Override
	public int compareTo(TagDTO o) {
		return this.getName().compareTo(o.getName());
	}

}
