package com.github.orlin.socialCMS.rest.misc;

import org.apache.commons.lang3.StringUtils;

import com.github.orlin.socialCMS.rest.RestInputConstraintViolationException;

public class Validator {
	/**
	 * Validates an input and checks if it is integer between <b>min</b> and <b>max</b><br />
	 * if <b>true</b> returns the parsed integer<br />
	 * <b>else</b> throws an exception
	 * @param fieldName The name of the form field where the paramether originates from (for the error message)
	 */
	public static Integer validateIntegerInput(String input, String fieldName, int min, int max) throws RestInputConstraintViolationException {
		if(!StringUtils.isNumeric(input) || Integer.parseInt(input)< min || Integer.parseInt(input) > max) {
			throw RestInputConstraintViolationException.formInputInteger(fieldName, min, max);
		}
		
		return Integer.parseInt(input);
		
	}
	/**
	 * Validates an input and checks if it is string with length between <b>minLen</b> and <b>maxLen</b><br />
	 * if <b>optional and empty</b> return <i>null</i><br />
	 * otherwise if <b>true</b> returns the trimmed String<br />
	 * <b>else</b> throws an exception
	 * @param fieldName The name of the form field where the parameter originates from (for the error message)
	 * @param optiona is the parameter optional
	 */
	public static String validateStringInput (String input, String fieldName, boolean optional, int minLen, int maxLen) throws RestInputConstraintViolationException {
		if(optional && StringUtils.isEmpty(input)) {
			return null;
		}
		
		if(!optional && StringUtils.isEmpty(input)) {
			throw RestInputConstraintViolationException.formInputString(fieldName, minLen, maxLen, optional);
		}
		
		String trimmed = StringUtils.trim(input);
		int length = trimmed.length();
		if(length < minLen || length > maxLen) {
			throw RestInputConstraintViolationException.formInputString(fieldName, minLen, maxLen, optional);
		}
		
		return trimmed;
	}
	
	/**
	 * Validates an input and checks if it is string with length smaller then<b>maxLen</b> and complies with <b>regexp</b><br />
	 * if <b>optional and empty</b> return <i>null</i><br />
	 * otherwise if <b>true</b> returns the trimmed String<br />
	 * <b>else</b> throws an exception
	 * @param fieldName The name of the form field where the parameter originates from (for the error message)
	 * @param optiona is the parameter optional
	 */
	public static String validateStringInput (String input, String fieldName, boolean optional, String regexp, int maxLen) throws RestInputConstraintViolationException {
		if(optional && StringUtils.isEmpty(input)) {
			return null;
		}
		
		String trimmed = StringUtils.trim(input);
		int length = trimmed.length();
		if(length > maxLen || !trimmed.matches(regexp)) {
			throw RestInputConstraintViolationException.formInputStringRegexp(fieldName, regexp, maxLen, optional);
		}
		
		return trimmed;
	}
}
