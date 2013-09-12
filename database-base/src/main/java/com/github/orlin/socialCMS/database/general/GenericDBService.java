package com.github.orlin.socialCMS.database.general;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.github.orlin.socialCMS.database.exceptions.TooManyAnswersRuntimeException;

public abstract class GenericDBService<T extends DBEntity, F extends Filter> implements DBService<T, F> {

	public abstract Class<T> getObjectClass();

	public abstract CriteriaQuery<T> generateCriteria(F filter, CriteriaQuery<T> cq, CriteriaBuilder cb);

	protected static final EntityManager em;

	static {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.github.orlin.socialCMS.database.entities");
		em = emf.createEntityManager();
	};

	@Override
	public T load(final Long id) {
		return em.find(getObjectClass(), id);
	}

	@Override
	public void save(T savable) {
		if(savable.getId() == null) {
			em.persist(savable);
		} else {
			em.merge(savable);
		}
	}

	@Override
	public void delete(T saved) {
		em.remove(saved);
	}

	@Override
	public final List<T> loadAllByFilter(final F filter) {
		TypedQuery<T> query = generateQuerry(filter);
		return query.getResultList();
	}

	private TypedQuery<T> generateQuerry(final F filter) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(getObjectClass());
		if (filter != null) {
			cq = generateCriteria(filter, cq, cb);
		}

		TypedQuery<T> query = em.createQuery(cq);

		if (filter != null) {
			if (filter.getCount() != null) {
				query.setMaxResults(filter.getCount());
			}

			if (filter.getFirstResult() != null) {
				query.setFirstResult(filter.getFirstResult());
			}
		}
		return query;
	}

	@Override
	public final List<T> loadAll() {
		return loadAllByFilter(null);
	}

	@Override
	public final int getSize() {
		return this.getSizeByFilter(null);
	}

	@Override
	public final int getSizeByFilter(final F filter) {
		return loadAllByFilter(filter).size();
	}

	@Override
	public final T loadByFilter(final F filter) {
		List<T> list = this.loadAllByFilter(filter);
		if (list.size() > 1) {
			throw new TooManyAnswersRuntimeException();
		}

		if (list.size() == 1) {
			return list.get(0);
		}

		return null;

	}
}
