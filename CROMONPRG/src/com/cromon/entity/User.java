package com.cromon.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;



/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private int id;
	private String active;
	private java.sql.Timestamp dateLastPassword;
	private String emailAddress;
	private String fullName;
	private String password;
	private String phoneNumber;
	private String userName;
	private String userType;
	private String ipAddress;
	private  int contador;
	private String tipoUsuario;

	public User() {
	}

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "active", length=1)
	public String getActive() {
		return this.active;
	}
	public void setActive(String active) {
		this.active = active;
	}

	@Column(name ="dateLastPassword")
	public java.sql.Timestamp getDateLastPassword() {
		return this.dateLastPassword;
	}
	public void setDateLastPassword(java.sql.Timestamp dateLastPassword) {
		this.dateLastPassword = dateLastPassword;
	}

	@Column(name = "emailAddress", length = 75)
	public String getEmailAddress() {
		return this.emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Column(name = "fullName", length = 60)
	public String getFullName() {
		return this.fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "password", length = 256)
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "phoneNumber", length = 10)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "userName", length = 8)
	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "userType", length = 12)
	public String getUserType() {
		return this.userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	@Column(name = "ipAddress", length = 15)
	public String getIpAddress() {
		return this.ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	@Column(name = "contador", length = 5)
	public int getContador() {
		return this.contador;
	}
	public void setContador(int contador) {
		this.contador = contador;
	}

	@Column(name="tipoUsuario", length=50)
	public String getTipoUsuario() {
		return tipoUsuario;
	}


	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

}