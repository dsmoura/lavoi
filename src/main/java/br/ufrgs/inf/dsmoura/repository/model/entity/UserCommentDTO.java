package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.Calendar;

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
public class UserCommentDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "usercomment_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="usercomment_generator")
	private Integer usercommentPk;
	
	@Column(nullable=false)
	private String comment;
	
	@OneToOne
	@JoinColumn(name="userpk", nullable=false)
	private UserDTO userDTO;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;
	
	public Integer getUsercommentPk() {
		return usercommentPk;
	}
	public void setUsercommentPk(Integer usercommentPk) {
		this.usercommentPk = usercommentPk;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public UserDTO getUserDTO() {
		return userDTO;
	}
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	public Calendar getDate() {
		return date;
	}
	public void setDate(Calendar date) {
		this.date = date;
	}
	public String getStrDate() {
		return FieldsUtil.getStrDate(this.date);
	}
}