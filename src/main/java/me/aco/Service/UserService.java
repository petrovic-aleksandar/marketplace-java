package me.aco.Service;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import me.aco.model.User;

@Stateless
public class UserService {
	
	@Inject
	private EntityManager em;
	
	public void saveUser(User user) {
		em.merge(user);
	}
	
	public List<User> getAll() {
		return em.createNamedQuery(User.getAll, User.class).getResultList();
	}

}
