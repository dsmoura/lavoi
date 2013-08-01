package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.ufrgs.inf.dsmoura.repository.controller.util.FieldsUtil;


@Entity

public class AdjustmentDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "adjustment_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="adjustment_generator")
	private Integer adjustmentPk;
	
	@OneToOne
	@JoinColumn(name="userpk", nullable=false)
	private UserDTO adjusterUserDTO;
	
	@Column(nullable=false)
	private String description;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;

	public Integer getAdjustmentPk() {
		return adjustmentPk;
	}

	public void setAdjustmentPk(Integer adjustmentPk) {
		this.adjustmentPk = adjustmentPk;
	}

	public UserDTO getAdjusterUserDTO() {
		return adjusterUserDTO;
	}

	public void setAdjusterUserDTO(UserDTO adjusterUserDTO) {
		this.adjusterUserDTO = adjusterUserDTO;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
	
	public Date getDateTime() {
		if (date != null) {
			return date.getTime();
		}
		return null;
	}
	
	public String getStrDate() {
		return FieldsUtil.getStrDate(this.date);
	}
}