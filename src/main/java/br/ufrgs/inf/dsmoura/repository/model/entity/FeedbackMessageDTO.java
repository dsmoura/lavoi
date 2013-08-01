package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.Calendar;

import javax.persistence.*;

@Entity
public class FeedbackMessageDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "feedbackmessage_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="feedbackmessage_generator")
	private Integer feedbackmessagePk;
	
	@Column(nullable=false, length=16384)
	private String message;
	
	@Column(nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String email;
	
	@Column(nullable=false)
	private String currentIp;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;

	public Integer getFeedbackmessagePk() {
		return feedbackmessagePk;
	}

	public void setFeedbackmessagePk(Integer feedbackmessagePk) {
		this.feedbackmessagePk = feedbackmessagePk;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCurrentIp() {
		return currentIp;
	}

	public void setCurrentIp(String currentIp) {
		this.currentIp = currentIp;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
}
