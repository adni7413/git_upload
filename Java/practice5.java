package com.cathaybk.practice.nt50341.b;

import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Pattern;

public class practice5 {

	public static void printCalender() {

		Scanner scanner = new Scanner(System.in);
		System.out.println("輸入介於 1-12 間的整數 m:");
		String inmonth = scanner.nextLine();
		

		// 判斷輸入的月份是否符合規範
		while (!Pattern.matches("^[1-9]\\d*", inmonth)
				|| (Integer.parseInt(inmonth) < 1 | Integer.parseInt(inmonth) > 12)) {
			System.out.println("請重新輸入介於 1-12 間的整數 m:");
			inmonth = scanner.nextLine();

		}
		//儲存輸入的月份
		int month = Integer.parseInt(inmonth);
		scanner.close();

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);//用系統判斷年分
		calendar.set(year, month - 1, 1); //設置年月日，因為月份從0開始，故減1
		int startDay = calendar.get(Calendar.DAY_OF_WEEK); // 求本週第一天是星期幾
		int dayCount = startDay - 1; // 第一週的初始計數
		int maxDay = maxMonthDay(year, month);  //每月最大天數
		System.out.printf("          %d年%d月        ", year, month);
		System.out.println();
		System.out.println("---------------------------");
		System.out.println("日   一   二   三  四   五   六");
		System.out.println("===========================");
		fillInSpace(startDay);
		for (int i = 1; i <= maxDay; i++) {
			System.out.printf("%3d%s", i, " ");  //使用printf對齊
			dayCount++;  //從每個月第一天開始，紀錄目前加到星期幾
			if (dayCount >= 7) {  //每輸出七天換行一次，並把紀錄星期的dayCount歸零
				dayCount = 0;
				System.out.print('\n');
			}
		}

	}

	//每月份第一天前面補空格 eg.若是星期三，則前面補兩次4個字元的空格
	public static void fillInSpace(int startDay) {
		for (int i = 1; i < startDay; i++) {
			System.out.printf("%4s", " ");
		}
	}

	//判斷每月份最大天數以及閏年二月份天數
	public static int maxMonthDay(int year, int month) {
		int max = 30;
		if (month == 1 | month == 3 | month == 5 | month == 7 | month == 8 | month == 10 | month == 12)
			max = 31;
		else if (month == 2 & ((int) year % 4 == 0))
			max = 29;
		else if (month == 2 & ((int) year % 4 != 0))
			max = 28;
		return max;
	}

	public static void main(String[] args) {

		printCalender();

	}
}
