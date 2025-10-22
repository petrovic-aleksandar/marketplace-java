package me.aco.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import me.aco.dto.UserReq;
import me.aco.enums.UserRole;
import me.aco.model.User;
import me.aco.util.SecurityUtil;

@Stateless
public class UserService {
	
	@Inject
	private EntityManager em;
	
	public List<User> getAll() {
		return em.createNamedQuery(User.getAll, User.class).getResultList();
	}
	
	public User getById(long id) {
		List<User> users = em.createNamedQuery(User.getById, User.class).setParameter("id", id).getResultList();
		if (users.size() > 0)
			return users.get(0);
		else
			return null;
	}
	
	public User getByUsername(String username) {
		List<User> users = em.createNamedQuery(User.getByUsername, User.class).setParameter("username", username).getResultList();
		if (users.size() > 0)
			return users.get(0);
		else
			return null;
	}
	
	public void persistUser(User user) {
		em.persist(user);
	}
	
	public User saveUser(User user) {
		return em.merge(user);
	}
	
	public User updateUser(long id, UserReq request) throws NoSuchAlgorithmException {
		User loadedUser = em.createNamedQuery(User.getById, User.class).setParameter("id", id).getSingleResult();
		loadedUser.setUsername(request.getUsername());
		if (request.isUpdatePassword()) {
			loadedUser.setSalt(SecurityUtil.getSalt());
			loadedUser.setPassword(SecurityUtil.get_SHA_512_SecurePassword(request.getPassword(), loadedUser.getSalt()));
		}
		loadedUser.setName(request.getName());
		loadedUser.setEmail(request.getEmail());
		loadedUser.setPhone(request.getPhone());
		loadedUser.setRole(UserRole.valueOf(request.getRole()));
		return saveUser(loadedUser);
	}
	
	public User deactivateUser(long id) {
		User loadedUser = em.createNamedQuery(User.getById, User.class).setParameter("id", id).getSingleResult();
		loadedUser.setActive(false);
		return saveUser(loadedUser);
	}
	
	public User activateUser(long id) {
		User loadedUser = em.createNamedQuery(User.getById, User.class).setParameter("id", id).getSingleResult();
		loadedUser.setActive(true);
		return saveUser(loadedUser);
	}

}
