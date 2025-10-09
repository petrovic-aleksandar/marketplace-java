package me.aco.Util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

@ApplicationScoped
public class Producer {
	
	@PersistenceUnit(unitName = "marketplace")
	private EntityManagerFactory emf;
	
	@Produces
	public EntityManager getEm() {
		return emf.createEntityManager();
	}

}
