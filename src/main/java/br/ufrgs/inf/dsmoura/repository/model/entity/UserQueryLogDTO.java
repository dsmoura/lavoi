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
public class UserQueryLogDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "userquerylog_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="userquerylog_generator")
	private Integer userquerylogPk;
	
	@Column(length=16384, nullable=false)
	private String queryText;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;
	
	@Column(nullable=false)
	private String ip;
	
	@Column
	private String username;

	public Integer getUserquerylogPk() {
		return userquerylogPk;
	}
	public void setUserquerylogPk(Integer userquerylogPk) {
		this.userquerylogPk = userquerylogPk;
	}
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}