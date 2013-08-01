package br.ufrgs.inf.dsmoura.repository.model.entity;

import java.io.Serializable;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@NamedQueries({
   @NamedQuery(name="UserDTO.findByEmail",
       query="SELECT u" +
       			" FROM UserDTO u" +
       			" WHERE u.email = :email"),
   @NamedQuery(name="UserDTO.findByUsername",
       query="SELECT u" +
       			" FROM UserDTO u" +
       			" WHERE u.username = :username")
})
@Entity
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "user_generator", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.AUTO, generator="user_generator")
	private Integer userPk;
	
	@Column(unique=true, nullable=false)
	private String username;
	
	@Column(nullable=false)
	private String name;
	
	@Column(unique=true, nullable=false)
	private String email;
	
	@Column(nullable=false)
	private String password;
	
	@Column(nullable=false)
	private Boolean isCertifier;
	
	@Column(nullable=false)
	private Boolean isManager;
	
	@Column(nullable=false)
	private Boolean isFirstLogin;
	
	@Column(nullable=false)
	private String firstLoginCode;
	
	@Column(nullable=false)
	private Boolean isChangingPassword;
	
	@Column
	private String ip;
	
	@ManyToMany
	@JoinColumn(name="projectpk")
	private List<ProjectDTO> projectDTOs = new ArrayList<ProjectDTO>();

	@Transient
	private String confirmPassword;
	
	@Transient
	private String newPassword;
	
	public Integer getUserPk() {
		return userPk;
	}

	public void setUserPk(Integer userPk) {
		this.userPk = userPk;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Boolean getIsCertifier() {
		return isCertifier;
	}

	public void setIsCertifier(Boolean isCertifier) {
		this.isCertifier = isCertifier;
	}

	public Boolean getIsManager() {
		return isManager;
	}

	public void setIsManager(Boolean isManager) {
		this.isManager = isManager;
	}

	public Boolean getIsFirstLogin() {
		return isFirstLogin;
	}

	public void setIsFirstLogin(Boolean isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	public String getFirstLoginCode() {
		return firstLoginCode;
	}

	public void setFirstLoginCode(String firstLoginCode) {
		this.firstLoginCode = firstLoginCode;
	}
	
	public Boolean getIsChangingPassword() {
		return isChangingPassword;
	}

	public void setIsChangingPassword(Boolean isChangingPassword) {
		this.isChangingPassword = isChangingPassword;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public List<ProjectDTO> getProjectDTOs() {
		return projectDTOs;
	}

	public void setProjectDTOs(List<ProjectDTO> projectDTOs) {
		this.projectDTOs = projectDTOs;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String toString() {
		return this.username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userPk == null) ? 0 : userPk.hashCode());
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
		UserDTO other = (UserDTO) obj;
		if (userPk == null) {
			if (other.userPk != null)
				return false;
		} else if (!userPk.equals(other.userPk))
			return false;
		return true;
	}
}
