package com.github.orlin.socialCMS.rest;


/**
 * @author orlin
 *
 */
public class RestInputConstraintViolationException extends Exception {
	private static final long serialVersionUID = 1L;

	public enum Type {
		FORM_INPUT_MISSING("Please supply a form paramether for the field '%s'"),
		FORM_INPUT_MUST_BE_INTEGER("The input on field '%s' should be an integer from '%d' to '%d'"),
		ENTITY_NOT_FOUND("Entity of type '%s' with id '%s' was not found"),
		FORM_INPUT_STRING_CONSTRAINT("Please supply a string between '%d' and '%d' chars for the %soptional paramether '%s'"),
		FORM_INPUT_STRING_REGEX("Please supply a string less then '%d' chars for the %soptional paramether '%s', which complies with the regexp: '%s'")
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
	
	public static RestInputConstraintViolationException formInputInteger(String inputId, int min, int max) {
		return new RestInputConstraintViolationException(Type.FORM_INPUT_MUST_BE_INTEGER, inputId, min, max);
	}
	
	public static RestInputConstraintViolationException formInputString(String inputId, int min, int max, boolean optional) {
		String optionalStr = optional ? "" : "non-";
		return new RestInputConstraintViolationException(Type.FORM_INPUT_STRING_CONSTRAINT, min, max, optionalStr, inputId);
	}
	
	public static RestInputConstraintViolationException formInputStringRegexp(String inputId, String regexp, int max, boolean optional) {
		String optionalStr = optional ? "" : "non-";
		return new RestInputConstraintViolationException(Type.FORM_INPUT_STRING_REGEX, max, optionalStr, inputId, regexp);
	}
	
	/**
	 * @return Returns a human readable message
	 */
	public String getMessage() {
		return String.format(type.message, args);
	}
}
