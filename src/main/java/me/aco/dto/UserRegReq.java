package me.aco.dto;

import java.security.NoSuchAlgorithmException;

import me.aco.enums.UserRole;
import me.aco.model.User;
import me.aco.util.SecurityUtil;

public class UserRegReq {

	private String username;
	private String password;
	private String name;
	private String email;
	private String phone;

	public User toUser() throws NoSuchAlgorithmException {
		User user = new User();
		user.setUsername(username);
		user.setSalt(SecurityUtil.getSalt());
		user.setPassword(SecurityUtil.get_SHA_512_SecurePassword(password, user.getSalt()));
		user.setName(name);
		user.setEmail(email);
		user.setPhone(phone);
		user.setRole(UserRole.User);
		user.setActive(true);
		return user;
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

}
