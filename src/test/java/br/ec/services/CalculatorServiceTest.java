package br.ec.services;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalculatorServiceTest {

	private CalculatorService service;

	private Double firstNumber;

	private Double secondNumber;

	public CalculatorServiceTest() {
		this.service = new CalculatorService();
		this.firstNumber = 10d;
		this.secondNumber = 5d;
	}

	@Test
	public void sumOperation() {
		assertEquals(this.service.sum(this.firstNumber, this.secondNumber), 15d, 0.001);
	}

	@Test
	public void diffOperation() {
		assertEquals(this.service.diff(this.firstNumber, this.secondNumber), 5d, 0.001);
	}

	@Test
	public void timesOperation() {
		assertEquals(this.service.times(this.firstNumber, this.secondNumber), 50d, 0.001);
	}

	@Test
	public void divisionOperation() {
		assertEquals(this.service.division(this.firstNumber, this.secondNumber), 2d, 0.001);
	}
}