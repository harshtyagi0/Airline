package org.pack.airline.model;

import javax.persistence.*;

@Entity
@Table
public class Admin {

	@Id
	@Column
	private String userName;
	private int password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

}
