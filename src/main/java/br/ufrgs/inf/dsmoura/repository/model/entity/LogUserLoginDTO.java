package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class LogUserLoginDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "loguserlogin_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="loguserlogin_generator")
	private Integer loguserloginPk;
	
	@Column(nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String fullname;
	
	@Column(nullable=false)
	private String email;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;
	
	@Column(nullable=false)
	private String ip;

	public Integer getLoguserloginPk() {
		return loguserloginPk;
	}

	public void setLoguserloginPk(Integer loguserloginPk) {
		this.loguserloginPk = loguserloginPk;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}