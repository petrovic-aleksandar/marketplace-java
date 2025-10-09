package me.aco.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "appuser")
@NamedQueries(value = {
		@NamedQuery(name = User.getAll, query = "select u from User u"),
		@NamedQuery(name = User.getById, query = "select u from User u where u.id = :id"),
		@NamedQuery(name = User.getByUsername, query = "select u from User u where u.username = :username")
		})
public class User {
	
	public static final String getAll = "GetAllUsers";
	public static final String getById = "GetUserById";
	public static final String getByUsername = "GetUserByUsername";
	
	@Id
	private long id;
	private String username;
	private String password;
	private String name;
	private String email;
	private String phone;
	private double balance;
	@Enumerated(EnumType.STRING)
	private UserRole role;
	private boolean active;
	
	private String refreshToken;
	private LocalDateTime refreshTokenExpiry;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
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
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public UserRole getRole() {
		return role;
	}
	
	public void setRole(UserRole role) {
		this.role = role;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getRefreshToken() {
		return refreshToken;
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public LocalDateTime getRefreshTokenExpiry() {
		return refreshTokenExpiry;
	}
	
	public void setRefreshTokenExpiry(LocalDateTime refreshTokenExpiry) {
		this.refreshTokenExpiry = refreshTokenExpiry;
	}

}
