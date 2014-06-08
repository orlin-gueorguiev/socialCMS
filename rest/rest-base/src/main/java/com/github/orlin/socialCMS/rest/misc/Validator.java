package com.github.orlin.socialCMS.rest.misc;

import org.apache.commons.lang3.StringUtils;

import com.github.orlin.socialCMS.rest.RestInputConstraintViolationException;

public class Validator {
	/**
	 * Validates an input and checks if it is integer between <b>min</b> and <b>max</b><br />
	 * if <b>true</b> returns the parsed integer<br />
	 * <b>else</b> throws an exception
	 * @param fieldName The name of the form field where the paramether originates from (for the error message)
	 * @param max if &lt 1, don't check for max constraint
	 */
	public static Integer validateIntegerInput(String input, String fieldName, int min, int max,boolean optional) throws RestInputConstraintViolationException {
		if(optional && StringUtils.isEmpty(input)) {
			return null;
		}
		
		if(!StringUtils.isNumeric(input) || Integer.parseInt(input)< min || (max > 0 && Integer.parseInt(input) > max)) {
			throw RestInputConstraintViolationException.formInputInteger(fieldName, min, max, optional);
		}
		
		return Integer.parseInt(input);
		
	}
	
	
	/**
	 * Validates an input and checks if it is long between <b>min</b> and <b>max</b><br />
	 * if <b>true</b> returns the parsed long<br />
	 * <b>else</b> throws an exception
	 * @param fieldName The name of the form field where the paramether originates from (for the error message)
	 * @param max if &lt 1, don't check for max constraint
	 */
	public static Long validateLongInput(String input, String fieldName, int min, int max,boolean optional) throws RestInputConstraintViolationException {
		if(optional && StringUtils.isEmpty(input)) {
			return null;
		}
		
		if(!StringUtils.isNumeric(input) || Long.parseLong(input)< min || (max > 0 && Long.parseLong(input) > max)) {
			throw RestInputConstraintViolationException.formInputLong(fieldName, min, max, optional);
		}
		
		return Long.parseLong(input);
		
	}
	
	/**
	 * Validates an input and checks if it is string with length between <b>minLen</b> and <b>maxLen</b><br />
	 * if <b>optional and empty</b> return <i>null</i><br />
	 * otherwise if <b>true</b> returns the trimmed String<br />
	 * <b>else</b> throws an exception
	 * @param fieldName The name of the form field where the parameter originates from (for the error message)
	 * @param optiona is the parameter optional
	 * @param maxLen if &lt 1, don't check for max constraint
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
		if(length < minLen || (maxLen > 0 && length > maxLen)) {
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
	 * @param maxLen if &lt 1, don't check for max constraint
	 */
	public static String validateStringInput (String input, String fieldName, boolean optional, String regexp, int maxLen) throws RestInputConstraintViolationException {
		if(optional && StringUtils.isEmpty(input)) {
			return null;
		}
		
		String trimmed = StringUtils.trim(input);
		int length = trimmed.length();
		if((maxLen > 0 && length > maxLen) || !trimmed.matches(regexp)) {
			throw RestInputConstraintViolationException.formInputStringRegexp(fieldName, regexp, maxLen, optional);
		}
		
		return trimmed;
	}
}
