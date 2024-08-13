package com.cathaybk.practice.nt50341.b;

public class practice1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 乘數 i 1-9
		for (int i = 1; i < 10; i++) {
			// 被乘數 m 2-9
			for (int m = 2; m < 10; m++) {
				System.out.printf("%d*%d=%2d ", m, i, m * i);

			}
			System.out.println();
		}
	}

}
