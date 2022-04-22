package com.portal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class Response - JSON Object
 * @author santhoshkumardurairaj
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
	/**
	 * available
	 */
	boolean available;
	
	/**
	 * boolean
	 * @return
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * 
	 * @param available
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}
}
