package com.github.orlin.socialCMS.database.general;

/**
 * @author orlin
 * 
 */
public abstract class Filter {
	private Long id;
	private Integer firstResult;
	private Integer count;

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public Integer getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(Integer firstResult) {
		this.firstResult = firstResult;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}