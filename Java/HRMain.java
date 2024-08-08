package com.cathaybk.practice.nt50341.b;

import java.util.ArrayList;
import java.util.List;

public class HRMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(new Sales("張志城","信用卡部",35000,6000));
		employeeList.add(new Sales("林大鈞","保代部",38000,4000));
		employeeList.add(new Supervisor("李中白","資訊部",65000));
		employeeList.add(new Supervisor("林小中","理財部",80000));
		for(Employee employee : employeeList) {
			employee.printInfo();
			
		}
	}

}
