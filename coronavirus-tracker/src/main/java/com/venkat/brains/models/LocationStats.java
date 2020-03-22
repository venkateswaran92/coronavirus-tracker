package com.venkat.brains.models;

public class LocationStats {

	private String state;
	private String country;
	private int latesTotalCases;
	private int diffromPrevDay;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getLatesTotalCases() {
		return latesTotalCases;
	}

	public void setLatesTotalCases(int latesTotalCases) {
		this.latesTotalCases = latesTotalCases;
	}

	public int getDiffromPrevDay() {
		return diffromPrevDay;
	}

	public void setDiffromPrevDay(int diffromPrevDay) {
		this.diffromPrevDay = diffromPrevDay;
	}

}
