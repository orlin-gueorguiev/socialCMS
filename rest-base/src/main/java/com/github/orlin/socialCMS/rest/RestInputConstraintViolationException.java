package com.github.orlin.socialCMS.rest;

/**
 * @author orlin
 *
 */
public class RestInputConstraintViolationException extends Exception {
	private static final long serialVersionUID = 1L;

	public enum Type {
		FORM_INPUT_MISSING("Please supply a '%s' in the form"),
		FORM_INPUT_NOT_INTEGER(""),
		ENTITY_NOT_FOUND("Entity of type '%s' with id '%s' was not found"),
		;
		String message;
		Type(String message) {
			this.message = message;
		}
	}
	
	private Object[] args;
	private Type type;
	
	private RestInputConstraintViolationException(Type type, Object... arguments) {
		this.type = type;
		this.args = arguments;
	}
	
	public static RestInputConstraintViolationException formInputMissing(String inputId) {
		return new RestInputConstraintViolationException(Type.FORM_INPUT_MISSING, inputId);
	}
	
	public static RestInputConstraintViolationException entityNotFound(String entityType, String entityId) {
		return new RestInputConstraintViolationException(Type.ENTITY_NOT_FOUND, entityType, entityId);
	}
	
	/**
	 * @return Returns a human readable message
	 */
	public String getMessage() {
		return String.format(type.message, args);
	}
}
