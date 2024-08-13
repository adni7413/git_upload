package com.cathaybk.practice.nt50341.b;


public class Employee implements IWork {

	private String name;
	private String department;
	private int salary;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Employee(String name,String department, int salary) {
		this.name = name;
		this.department = department;
		this.salary = salary;
		
	}


	@Override
	public void printInfo() {
		System.out.println("薪資單");
		System.out.printf("姓名: %s 工作部門: %s",name,department);
		System.out.println("");
		System.out.printf("月薪: %d",salary);
		
		
	}

}
