package com.cs192.foodwatch;


public class CreateChartDataPasser {

	private double weight;
	private double days;
	private int target;
	
	public void createChartDataPasser(double inputweight, double inputdays, int inputtarget) {
		this.weight = inputweight;
		this.days = inputdays;
		this.target = inputtarget;
	}
	
	public double returnWeight() {
		return this.weight;
	}
	
	public double returnDays() {
		return this.days;
	}
	
	public int returnTarget() {
		return this.target;
	}
	
	
}
