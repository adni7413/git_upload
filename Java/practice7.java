package com.cathaybk.practice.nt50341.b;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class practice7 {

	private static final String UPDATE_CAR_SQL = "update STUDENT.CARS2 set  MIN_PRICE = ?, PRICE = ?  where MANUFACTURER = ? and TYPE = ?";

	private static final String INSERT_CARS_SQL = "insert into STUDENT.CARS2 (MANUFACTURER, TYPE, MIN_PRICE, PRICE) values (?, ?, ?, ?)";

	private static final String CONN_URL = "jdbc:oracle:thin:@//localhost:1521/XE";

	private static final String DELETE_CAR_SQL = "delete from STUDENT.CARS2 where MANUFACTURER = ? and TYPE = ?";

	private static final String SEARCH_CAR_SQL = "select * from STUDENT.CARS2 where MANUFACTURER = ? and TYPE = ?";

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("請選擇以下指令輸入: select、insert、update、delete");
		String commandOption = scanner.nextLine();

		// 指令集
		List<String> commandList = new ArrayList<String>();
		commandList.add("select");
		commandList.add("insert");
		commandList.add("update");
		commandList.add("delete");

		// 若指令輸入不符規範，提示重新輸入到符合為止
		while (!commandList.contains(commandOption)) {
			System.out.println("請重新輸入:");
			commandOption = scanner.nextLine();
		}

		System.out.println("請輸入製造商:");
		String inManufacturer = scanner.nextLine();
		System.out.println("請輸入類型:");
		String inType = scanner.nextLine();

		// 根據輸入的指令執行對應的方法
		switch (commandOption) {

		case "select":
			doQuery(inManufacturer, inType);
			break;

		case "insert":
			System.out.println("請輸入底價:");
			String inputPriceMin = scanner.nextLine();
			System.out.println("請輸入售價:");
			String inputPrice = scanner.nextLine();

			doInsert(inManufacturer, inType, inputPriceMin, inputPrice);
			break;

		case "update":
			System.out.println("請輸入底價:");
			String updatePriceMin = scanner.nextLine();
			System.out.println("請輸入售價:");
			String updatePrice = scanner.nextLine();
			doUpdate(inManufacturer, inType, updatePriceMin, updatePrice);
			break;

		case "delete":
			doDelete(inManufacturer, inType);
			break;

		}
		scanner.close();
	}

	// insert方法
	public static void doInsert(String inManufacturer, String inType, String inputPriceMin, String inputPrice) {

		Scanner scanner = new Scanner(System.in);

		try (Connection conn = DriverManager.getConnection(CONN_URL, "student", "student123456");) {

			try (PreparedStatement insertValue = conn.prepareStatement(INSERT_CARS_SQL);) {

				conn.setAutoCommit(false);

				// 若WhetherExist為true，代表有此筆資料，無法輸入，提示錯誤原因
				if (WhetherExist(inManufacturer, inType)) {

					throw new Exception("已有製造商、類型資料。請利用update");

				} else { // 若WhetherExist為false，代表無此筆資料，繼續執行輸入步驟

					// 判斷輸入的底價以及售價是否符合規範，若錯誤則提示直到輸入正確為止
					while (!Pattern.matches("^[0-9]*\\.?\\d*", inputPriceMin)
							|| !Pattern.matches("^[0-9]*\\.?\\d*", inputPrice)
							|| (Double.parseDouble(inputPriceMin) > Double.parseDouble(inputPrice))) {
						System.out.println("請重新輸入底價與售價(最多到小數點二位)，且售價需大於底價");
						System.out.println("請輸入底價:");
						inputPriceMin = scanner.nextLine();
						System.out.println("請輸入售價:");
						inputPrice = scanner.nextLine();

					}

					// 若查詢結果不重複且價格符合規範，則輸入資料到資料庫並commit
					insertValue.setString(1, inManufacturer);
					insertValue.setString(2, inType);
					insertValue.setDouble(3, Double.parseDouble(inputPriceMin));
					insertValue.setDouble(4, Double.parseDouble(inputPrice));
					insertValue.executeUpdate();

					conn.commit();

					System.out.println("新增成功");

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
		} finally {
			scanner.close();
		}

	}

	// select方法
	public static void doQuery(String inManufacturer, String inType) {

		Scanner scanner = new Scanner(System.in);

		try (Connection conn = DriverManager.getConnection(CONN_URL, "student", "student123456");
				PreparedStatement dosearch = conn.prepareStatement(SEARCH_CAR_SQL);) {

			if (WhetherExist(inManufacturer, inType)) { // 若WhetherExist為true，代表有此筆資料，繼續執行步驟

				dosearch.setString(1, inManufacturer);
				dosearch.setString(2, inType);

				ResultSet rs = dosearch.executeQuery();

				// 使用StringBuilder做字串串接
				StringBuilder sb = new StringBuilder();
				sb.append("查詢結果： \n");
				while (rs.next()) {
					sb.append("製造商： ").append(rs.getString("MANUFACTURER")).append("，型號：").append(rs.getString("TYPE"))
							.append("，售價：").append(rs.getString("PRICE")).append("，底價：")
							.append(rs.getString("MIN_PRICE"));
				}
				System.out.println(sb.toString());

			} else {  // 若WhetherExist為false，代表無此筆資料，提示錯誤原因
				throw new Exception("查無此筆資料");
			}

		} catch (Exception e) {
			System.out.println("查詢失敗，原因：" + e.getMessage());
			e.printStackTrace();

		} finally {
			scanner.close();
		}
	}

	// update方法
	public static void doUpdate(String inManufacturer, String inType, String inputPriceMin, String inputPrice) {

		// 等待使用者輸入四種屬性的資料
		Scanner scanner = new Scanner(System.in);

		try (Connection conn = DriverManager.getConnection(CONN_URL, "student", "student123456");) {
			try (PreparedStatement selecinsert = conn.prepareStatement(UPDATE_CAR_SQL);) {
				conn.setAutoCommit(false);

				
				
				// 若WhetherExist為true，代表有此筆資料，繼續執行步驟
				if (WhetherExist(inManufacturer, inType)) {
					
					// 判斷輸入的底價以及售價是否符合規範，若錯誤則提示直到輸入正確為止
					while (!Pattern.matches("^[0-9]*\\.?\\d*", inputPriceMin)
							|| !Pattern.matches("^[0-9]*\\.?\\d*", inputPrice)
							|| (Double.parseDouble(inputPriceMin) > Double.parseDouble(inputPrice))) {

						System.out.println("請重新輸入底價與售價(最多到小數點二位)，且售價需大於底價");
						System.out.println("請輸入底價:");
						inputPriceMin = scanner.nextLine();
						System.out.println("請輸入售價:");
						inputPrice = scanner.nextLine();
					}

					// 執行update
					selecinsert.setDouble(1, Double.parseDouble(inputPriceMin));
					selecinsert.setDouble(2, Double.parseDouble(inputPrice));
					selecinsert.setString(3, inManufacturer);
					selecinsert.setString(4, inType);
					selecinsert.executeUpdate();

					
					System.out.println("更新成功");
					conn.commit();

				} else { // 若WhetherExist為false，代表無此筆資料，提示錯誤原因
					throw new Exception("無此製造商以及類別。請利用insert新增資料");
				}

			} catch (Exception e) {
				System.out.println("更新失敗，原因：" + e.getMessage());
				try {
					conn.rollback();
				} catch (SQLException sqle) {

					System.out.println("rollback 失敗，原因：" + sqle.getMessage());
				}
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			scanner.close();
		}

	}

	// delete方法
	public static void doDelete(String inManufacturer, String inType) {

		try (Connection conn = DriverManager.getConnection(CONN_URL, "student", "student123456");) {
			try (PreparedStatement dodelete = conn.prepareStatement(DELETE_CAR_SQL);) {
				conn.setAutoCommit(false);

				

				// 若WhetherExist為true，代表有此筆資料，繼續執行步驟
				if (WhetherExist(inManufacturer, inType)) {
					
					dodelete.setString(1, inManufacturer);
					dodelete.setString(2, inType);
					dodelete.executeUpdate();
					
					System.out.println("刪除成功");
					conn.commit();
					
				} else { // 若WhetherExist為false，代表無此筆資料，提示錯誤原因
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
		}

	}

	public static boolean WhetherExist(String checkmanufacturer, String checkType) {
		try (Connection conn = DriverManager.getConnection(CONN_URL, "student", "student123456");
				PreparedStatement docheck = conn.prepareStatement(SEARCH_CAR_SQL);) {

			docheck.setString(1, checkmanufacturer);
			docheck.setString(2, checkType);

			ResultSet check = docheck.executeQuery();

			// 若查詢結果不為空值，代表有查詢到資料
			if (check.isBeforeFirst()) {
				return true;

			} else {
				// 若查詢結果為空值，代表沒有查詢到資料
				throw new Exception();
			}
		} catch (Exception e) {
			return false;

		}

	}

}
