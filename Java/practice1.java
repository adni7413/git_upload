package com.cathaybk.practice.nt50341.b;

public class practice1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//被乘數 i 2-9
		for(int i=0;i<9;i++) {
			//乘數 m 1-9
			for(int m =1;m<9;m++) {
				System.out.printf("%d*%d=%2d ",m+1,i+1,(m+1)*(i+1));
				
			}
			System.out.print("\n");
		}
	}


}
