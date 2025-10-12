package me.aco.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.RandomStringUtils;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import me.aco.dto.LoginReq;
import me.aco.dto.UserReq;
import me.aco.model.User;
import me.aco.util.SecurityUtil;

@Stateless
public class AuthService {
	
	@Inject
	private EntityManager em;
	
	public boolean checkPassword(LoginReq req, User u) {
		if (u.getPassword().equals(SecurityUtil.get_SHA_512_SecurePassword(req.getPassword(), u.getSalt())))
			return true;
		else
			return false;		
	}
	
	public String createAndSaveRefreshToken(LoginReq req) {
		String refreshToken = RandomStringUtils.secureStrong().nextAlphanumeric(32);
		User loadedUser = em.createNamedQuery(User.getByUsername, User.class).setParameter("username", req.getUsername()).getResultList().get(0);
		loadedUser.setRefreshToken(refreshToken);
		loadedUser.setRefreshTokenExpiry(LocalDateTime.now().plus(7, ChronoUnit.DAYS));
		em.merge(loadedUser);
		return refreshToken;
	}

}
