package ru.job4j.calculator;

/**
* Class Calculator
* @author Alexander Belov (whiterabbit.nsk@gmail.com)
* @version 0.1
* @since 24.05.2018
*/

public class Calculator {
	private double result;
	
	public void add(double first, double second) {
		this.result = first + second;
	}
	
	public void subtract(double first, double second) {
		this.result = first - second;
	}
	
	public void multiple(double first, double second) {
		this.result = first * second;
	}
	
	public void div(double first, double second) {
		this.result = first / second;
	}
	
	public double getResult() {
		return this.result;
	}
	
	
	

