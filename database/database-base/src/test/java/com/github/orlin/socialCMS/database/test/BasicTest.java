package com.github.orlin.socialCMS.database.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * A Basic test class, which initiates all teh general stuff, such as the entity manager, etc.
 * @author orlin
 *
 */
public class BasicTest {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("socialCMSPersistence");
	public static EntityManager em = emf.createEntityManager();
	
}
