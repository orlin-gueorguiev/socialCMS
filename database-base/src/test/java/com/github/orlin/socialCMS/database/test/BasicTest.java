package com.github.orlin.socialCMS.database.test;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.github.orlin.socialCMS.database.entities.AddressDao;
import com.github.orlin.socialCMS.database.filters.AddressFilter;
import com.github.orlin.socialCMS.database.general.GenericDBService;

public class BasicTest {
	public static EntityManager em = TestDBService.getEntityManager();
	
	// just to get the EntityManager
	public static class TestDBService extends GenericDBService<AddressDao, AddressFilter> {

		public static EntityManager getEntityManager() {
			return em;
		}
		
		@Override
		public AddressDao create() {
			return null;
		}

		@Override
		public Class<AddressDao> getObjectClass() {
			return null;
		}

		@Override
		public CriteriaQuery<AddressDao> generateCriteria(AddressFilter filter, CriteriaQuery<AddressDao> cq, CriteriaBuilder cb) {
			return null;
		}
		
	}
}
