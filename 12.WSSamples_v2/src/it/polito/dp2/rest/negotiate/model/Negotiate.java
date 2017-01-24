package it.polito.dp2.rest.negotiate.model;

import javax.xml.bind.annotation.XmlRootElement;

/*
 * A value class that represents the result of a negotiation
 * Includes Min and Max and an id associated with the negotiation
 */
@XmlRootElement
public class Negotiate {
	
	private float Max;
	private float Min;
	private long id;
	
	public Negotiate() {
		
	}
	
	public Negotiate(float max, float min) {
		Max = max;
		Min = min;
	}

	public Negotiate(float max, float min, int id) {
		Max = max;
		Min = min;
		this.id=id;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public float getMax() {
		return Max;
	}
	
	public void setMax(float max) {
		Max = max;
	}
	
	public float getMin() {
		return Min;
	}
	
	public void setMin(float min) {
		Min = min;
	}
	
}
