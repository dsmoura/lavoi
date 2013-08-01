package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import javax.persistence.*;

@Entity
public class EffortDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "effort_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="effort_generator")
	
	private Integer effortPk;
	private Float estimatedTime;
	private Float realTime;
	private Float monetary;
	private Integer teamMembers;
	private Integer linesOfCode;

	public Integer getEffortPk() {
		return effortPk;
	}
	public void setEffortPk(Integer effortPk) {
		this.effortPk = effortPk;
	}
	public Float getEstimatedTime() {
		return estimatedTime;
	}
	public void setEstimatedTime(Float estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	public Float getRealTime() {
		return realTime;
	}
	public void setRealTime(Float realTime) {
		this.realTime = realTime;
	}
	public Float getMonetary() {
		return monetary;
	}
	public void setMonetary(Float monetary) {
		this.monetary = monetary;
	}
	public Integer getTeamMembers() {
		return teamMembers;
	}
	public void setTeamMembers(Integer teamMembers) {
		this.teamMembers = teamMembers;
	}
	public Integer getLinesOfCode() {
		return linesOfCode;
	}
	public void setLinesOfCode(Integer linesOfCode) {
		this.linesOfCode = linesOfCode;
	}
}
