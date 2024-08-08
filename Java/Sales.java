package com.cathaybk.practice.nt50341.b;

public class Sales extends Employee{

	private int bonus;
	private int payment = getSalary() + bonus;

	public int getBonus() {
		return bonus;
	}

	public void setBnus(int bonus) {
		this.bonus = bonus;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	public Sales(String name,String department, int salary, int bonus) {
		super( name,department,salary);
		this.bonus = (int) (bonus * 0.05);
		this.payment = salary+this.bonus;
	}
	@Override
	public void printInfo() {
		// TODO Auto-generated method stub
		super.printInfo();
		System.out.println("");
		System.out.printf("業績獎金: %d",bonus);
		System.out.println("");
		System.out.printf("總計: %d",payment);
		System.out.println("");
		
		
		
	}
	

}
