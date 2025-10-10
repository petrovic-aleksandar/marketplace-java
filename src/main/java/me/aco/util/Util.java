package me.aco.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class Util {
	
	@PersistenceContext
	private EntityManager em;

}
