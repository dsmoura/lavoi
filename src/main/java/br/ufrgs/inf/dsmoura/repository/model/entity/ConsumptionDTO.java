package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
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
public class ConsumptionDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "consumption_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="consumption_generator")
	private Integer consumptionPk;
	
	@OneToOne
	@JoinColumn(name="userpk", nullable=false)
	private UserDTO consumerUserDTO;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;

	public Integer getConsumptionPk() {
		return consumptionPk;
	}

	public void setConsumptionPk(Integer consumptionPk) {
		this.consumptionPk = consumptionPk;
	}

	public UserDTO getConsumerUserDTO() {
		return consumerUserDTO;
	}

	public void setConsumerUserDTO(UserDTO consumerUserDTO) {
		this.consumerUserDTO = consumerUserDTO;
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
		return FieldsUtil.getStrDate(date);
	}
}