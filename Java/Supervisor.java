package com.cathaybk.practice.nt50341.b;

public class Supervisor extends Employee {

	private int payment = getSalary();

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	public Supervisor(String name, String department, int salary) {
		super(name, department, salary);
		this.payment = salary;
	}

	@Override
	public void printInfo() {
		super.printInfo();
		System.out.println("");
		System.out.printf("總計: %d", payment);
		System.out.println("");

	}
}
