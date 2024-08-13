package com.cathaybk.practice.nt50341.b;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class practice7 {

	public static final String UPDATE_CAR_SQL = "update STUDENT.CARS2 set  MIN_PRICE = ?, PRICE = ?  where MANUFACTURER = ? and TYPE = ?";

	public static final String INSERT_CARS_SQL = "insert into STUDENT.CARS2 (MANUFACTURER, TYPE, MIN_PRICE, PRICE) values (?, ?, ?, ?)";

	public static final String CONN_URL = "jdbc:oracle:thin:@//localhost:1521/XE";

	public static final String DELETE_CAR_SQL = "delete from STUDENT.CARS2 where MANUFACTURER = ? and TYPE = ?";

	public static final String SEARCH_CAR_SQL = "select * from STUDENT.CARS2 where MANUFACTURER = ? and TYPE = ?";

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("請選擇以下指令輸入: select、insert、update、delete");
		String commandOption = scanner.nextLine();
		
		//指令集
		String[] commandArray = { "select", "insert", "update", "delete" };

		//若指令輸入不符規範，提示重新輸入到符合為止
		while (!Arrays.asList(commandArray).contains(commandOption)) {
			System.out.println("請重新輸入:");
			commandOption = scanner.nextLine();
		}

		//根據輸入的指令執行對應的方法
		switch (commandOption) {

		case "select":
			doQuery();
			break;

		case "insert":
			doInsert();
			break;

		case "update":
			doUpdate();
			break;

		case "delete":
			doDelete();
			break;

		}
		scanner.close();
	}

	//insert方法
	public static void doInsert() {

		//等待使用者輸入四種屬性的資料
		Scanner scanner = new Scanner(System.in);
		System.out.println("請輸入製造商:");
		String inManufacturer = scanner.nextLine();
		System.out.println("請輸入類型:");
		String inType = scanner.nextLine();
		System.out.println("請輸入底價:");
		String inputPriceMin = scanner.nextLine();
		System.out.println("請輸入售價:");
		String inputPrice = scanner.nextLine();

		try (Connection conn = DriverManager.getConnection(CONN_URL, "student", "student123456");) {

			try {
				conn.setAutoCommit(false);

				PreparedStatement insertCheck = conn.prepareStatement(SEARCH_CAR_SQL);

				insertCheck.setString(1, inManufacturer);
				insertCheck.setString(2, inType);

				// ResultSet物件儲存查詢結果
				ResultSet rs = insertCheck.executeQuery();

				// 若查詢結果製造商以及類型為空值，代表不重複，可以輸入此筆資料
				if (!rs.isBeforeFirst()) {

					// 判斷輸入的底價以及售價是否符合規範，若錯誤則提示直到輸入正確為止
					while (!Pattern.matches("^\\d*\\.?\\d*", inputPriceMin)
							|| !Pattern.matches("^\\d*\\.?\\d*", inputPrice)
							|| (Double.parseDouble(inputPriceMin) > Double.parseDouble(inputPrice))) {
						System.out.println("請重新輸入底價與售價(最多到小數點二位)，且售價需大於底價");
						System.out.println("請輸入底價:");
						inputPriceMin = scanner.nextLine();
						System.out.println("請輸入售價:");
						inputPrice = scanner.nextLine();

					}

					//若查詢結果不重複且價格符合規範，則輸入資料到資料庫並commit
					PreparedStatement insertValue = conn.prepareStatement(INSERT_CARS_SQL);
					insertValue.setString(1, inManufacturer);
					insertValue.setString(2, inType);
					insertValue.setDouble(3, Double.parseDouble(inputPriceMin));
					insertValue.setDouble(4, Double.parseDouble(inputPrice));
					insertValue.executeUpdate();

					conn.commit();

					System.out.println("新增成功");
				} else { // 若查詢結果已經有此製造商以及類型，則提示利用update指令
					throw new Exception("已有製造商、類型資料。請利用update");

				}
				
			} catch (Exception e) {
				System.out.println("新增失敗，原因：" + e.getMessage());
				try {
					conn.rollback();
				} catch (SQLException sqle) {
					System.out.println("rollback 失敗，原因：" + sqle.getMessage());
				}
			}

		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}finally {
			scanner.close();
		}
		
	}

	//select方法
	public static void doQuery() {

		Scanner scanner = new Scanner(System.in);

		//等待使用者輸入四種屬性的資料
		System.out.println("請輸入製造商:");
		String inManufacturer = scanner.nextLine();
		System.out.println("請輸入類型:");
		String inType = scanner.nextLine();

		try (Connection conn = DriverManager.getConnection(CONN_URL, "student", "student123456");) {

			PreparedStatement dosearch = conn.prepareStatement(SEARCH_CAR_SQL);
			dosearch.setString(1, inManufacturer);
			dosearch.setString(2, inType);

			// ResultSet物件儲存查詢結果
			ResultSet rs = dosearch.executeQuery();

			// 使用StringBuilder做字串串接
			StringBuilder sb = new StringBuilder();
			sb.append("查詢結果： \n");

			// 若查詢結果不為空值，代表有查詢到資料
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					sb.append("製造商： ").append(rs.getString("MANUFACTURER")).append("，型號：").append(rs.getString("TYPE"))
							.append("，售價：").append(rs.getString("PRICE")).append("，底價：")
							.append(rs.getString("MIN_PRICE"));
				}
				System.out.println(sb.toString());
			} else {
				// 若查詢結果為空值，代表沒有查詢到資料
				throw new Exception("查無此筆資料");
			}

		} catch (Exception e) {
			System.out.println("查詢失敗，原因：" + e.getMessage());
			e.printStackTrace();

		}finally {
			scanner.close();
		}
	}

	//update方法
	public static void doUpdate() {
		
		//等待使用者輸入四種屬性的資料
		Scanner scanner = new Scanner(System.in);
		System.out.println("請輸入製造商:");
		String inManufacturer = scanner.nextLine();
		System.out.println("請輸入類型:");
		String inType = scanner.nextLine();
		System.out.println("請輸入底價:");
		String inputPriceMin = scanner.nextLine();
		System.out.println("請輸入售價:");
		String inputPrice = scanner.nextLine();

		try (Connection conn = DriverManager.getConnection(CONN_URL, "student", "student123456");) {
			try {
				conn.setAutoCommit(false);
				PreparedStatement selecinsert = conn.prepareStatement(UPDATE_CAR_SQL);

				// 判斷輸入的底價以及售價是否符合規範，若錯誤則提示直到輸入正確為止
				while (!Pattern.matches("^\\d*\\.?\\d*", inputPriceMin) || !Pattern.matches("^\\d*\\.?\\d*", inputPrice)
						|| (Double.parseDouble(inputPriceMin) > Double.parseDouble(inputPrice))) {
					System.out.println("請重新輸入底價與售價(最多到小數點二位)，且售價需大於底價");
					System.out.println("請輸入底價:");
					inputPriceMin = scanner.nextLine();
					System.out.println("請輸入售價:");
					inputPrice = scanner.nextLine();
				}

				//執行update
				selecinsert.setDouble(1, Double.parseDouble(inputPriceMin));
				selecinsert.setDouble(2, Double.parseDouble(inputPrice));
				selecinsert.setString(3, inManufacturer);
				selecinsert.setString(4, inType);
				selecinsert.executeUpdate();

				// 判斷更新數量updateCount
				int updateCount = selecinsert.getUpdateCount();

				// 判斷更新數量updateCount，若不為0代表成功更新到資料，並commit
				if (updateCount != 0) {
					System.out.println("更新成功");
					conn.commit();

				} else { // updateCount若為0代表沒更新到資料，此筆資料應該要新增，因此提示需使用insert指令
					throw new Exception("無此製造商以及類別。請利用insert新增資料");
				}

			} catch (Exception e) {
				System.out.println("更新失敗，原因：" + e.getMessage());
				try {
					conn.rollback();
				} catch (SQLException sqle) {
//                    sqle.printStackTrace();
					System.out.println("rollback 失敗，原因：" + sqle.getMessage());
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}finally {
			scanner.close();
		}
		
	}

	//delete方法
	public static void doDelete() {

		Scanner scanner = new Scanner(System.in);

		System.out.println("請輸入製造商:");
		String inManufacturer = scanner.nextLine();
		System.out.println("請輸入類型:");
		String inType = scanner.nextLine();

		try (Connection conn = DriverManager.getConnection(CONN_URL, "student", "student123456");) {
			try {
				conn.setAutoCommit(false);
				PreparedStatement dodelete = conn.prepareStatement(DELETE_CAR_SQL);
				dodelete.setString(1, inManufacturer);
				dodelete.setString(2, inType);

				dodelete.executeUpdate();
				int deleteCount = dodelete.getUpdateCount();

				// 判斷刪除的資料數量deleteCount，若不為0代表成功刪除到資料，並commit
				if (deleteCount != 0) {
					System.out.println("刪除成功");
					conn.commit();
				} else { // deleteCount若為0代表沒刪除到資料，應無此筆資料
					throw new Exception("無此筆資料");
				}

			} catch (Exception e) {
				System.out.println("刪除失敗，原因：" + e.getMessage());
				try {
					conn.rollback();
				} catch (SQLException sqle) {
					System.out.println("rollback 失敗，原因：" + sqle.getMessage());
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}finally {
			scanner.close();
		}

	}
}
