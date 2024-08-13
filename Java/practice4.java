package com.cathaybk.practice.nt50341.b;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class practice4 {

	public static void main(String[] args) {
		
		 
		 List<Employee> employeeList1 = new ArrayList<>();
		 employeeList1.add(new Sales("張志城","信用卡部",35000,6000));
		 employeeList1.add(new Sales("林大鈞","保代部",38000,4000));
		 employeeList1.add(new Supervisor("李中白","資訊部",65000));
		 employeeList1.add(new Supervisor("林小中","理財部",80000));
		
		   
		 try (BufferedWriter bufferedwrite = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\Admin\\Desktop\\output.csv", true), "UTF-8"))){

			    bufferedwrite.write( new String(new byte[]{(byte)0xef,(byte)0xbb,(byte)0xbf}));
				
				for(Employee employee: employeeList1) {
					 
					 if(employee instanceof Supervisor) {
						 
						 Supervisor employee1 = (Supervisor) employee;
						 int payment = employee1.getPayment();
						 
						 bufferedwrite.write( employee.getName()+","+ payment );  
						bufferedwrite.newLine(); 
					 }
					 
					 else {
						 Sales employee1 = (Sales) employee;
						 int payment = employee1.getPayment();
						 
						 bufferedwrite.write( employee1.getName()+","+ payment);  
						bufferedwrite.newLine(); 
					 }
					 
				 }
				
				bufferedwrite.flush(); // 把記憶體資料寫進去
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			 
		 


}
}


