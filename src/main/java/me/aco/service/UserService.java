package me.aco.service;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import me.aco.dto.UserReq;
import me.aco.enums.UserRole;
import me.aco.model.User;

@Stateless
public class UserService {
	
	@Inject
	private EntityManager em;
	
	public List<User> getAll() {
		return em.createNamedQuery(User.getAll, User.class).getResultList();
	}
	
	public boolean checkIfUsernameExists(String username) {
		if (em.createNamedQuery(User.getByUsername, User.class).setParameter("username", username).getResultList().size() > 0)
			return true;
		else
			return false;
	}
	
	public void persistUser(User user) {
		em.persist(user);
	}
	
	public User saveUser(User user) {
		return em.merge(user);
	}
	
	public void updateUser(long id, UserReq request) {
		User loadedUser = em.createNamedQuery(User.getById, User.class).setParameter("id", id).getSingleResult();
		loadedUser.setUsername(request.getUsername());
		loadedUser.setName(request.getName());
		loadedUser.setEmail(request.getEmail());
		loadedUser.setPhone(request.getPhone());
		loadedUser.setRole(UserRole.valueOf(request.getRole()));
	}
	
	public void deactivateUser(long id) {
		User loadedUser = em.createNamedQuery(User.getById, User.class).setParameter("id", id).getSingleResult();
		loadedUser.setActive(false);
		em.merge(loadedUser);
	}

}
