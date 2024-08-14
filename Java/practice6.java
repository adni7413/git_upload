package com.cathaybk.practice.nt50341.b;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class practice6 {

	public static void main(String[] args) {

		// 第 6-1 題
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream("C:\\Users\\Admin\\Downloads\\Java評量_第6題cars.csv")));) {
			// 檔案讀取路徑

			String line;

			// 讀取csv首列資料，並將四種屬性資料以Array儲存
			String[] titleArray = reader.readLine().split(",");

			// 建立set儲存車輛製造商資料
			Set<String> manufactureSet = new TreeSet<String>();

			// 建立list儲存車輛資料
			List<Map<String, String>> carList = new ArrayList<>();

			// 讀取csv各行資料，並將四種屬性資料以Map傳入list，同時將不重複的製造商名稱傳入set
			while ((line = reader.readLine()) != null) {

				Map<String, String> map = new HashMap<>();

				String item[] = line.split(",");
				/** 讀取 **/

				map.put(titleArray[0], item[0].trim()); // trim()代表去除字串的前後空格
				map.put(titleArray[1], item[1].trim());
				map.put(titleArray[2], item[2].trim());
				map.put(titleArray[3], item[3].trim());

				manufactureSet.add(item[0].trim());
				carList.add(map);

			}

			// 建立比較方法，先在compare方法內將字串轉成BigDecimal由大排到小比較價格
			Collections.sort(carList, new Comparator<Map<String, String>>() {

				@Override
				public int compare(Map<String, String> o1, Map<String, String> o2) {
					BigDecimal price1 = new BigDecimal(o1.get(titleArray[3])); // 根據"Price"大到小排序
					BigDecimal price2 = new BigDecimal(o2.get(titleArray[3]));
					return price2.compareTo(price1);
				}
			});

			try (// 新增csv檔到桌面
					BufferedWriter bufferedwrite = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream("C:\\Users\\Admin\\Desktop\\cars2.csv", true), "UTF-8"))) {

				// 根據儲存資料屬性名稱的titleArray依序存入資料名稱
				for (int i = 0; i < titleArray.length; i++) {
					bufferedwrite.append(titleArray[i] + ",");
				}
				bufferedwrite.newLine();

				// 將排序後的資料輸出至csv檔
				for (Map<String, String> carInfo : carList) {

					// 根據資料種類數量依序將資料輸出至csv檔
					for (int m = 0; m < titleArray.length; m++) {
						bufferedwrite.append(carInfo.get(titleArray[m]) + ",");
					}
					bufferedwrite.newLine();
				}

			}

			// 第 6-2 題

	
			System.out.printf("%-14s%-9s%-13s%-7s", titleArray[0], titleArray[1], titleArray[2], titleArray[3]);
			System.out.println();

			// 所有廠商底價以及售價的合計數

			ArrayList<BigDecimal> TotalPrice = new ArrayList<>();
			ArrayList<BigDecimal> TotalMinPrice = new ArrayList<>();
			

			// 各別6個廠商底價以及售價的小計數

			Map<String, List<Map<String, String>>> groupedCarsMap = carList.stream()
					.collect(Collectors.groupingBy(car -> car.get(titleArray[0]), TreeMap::new, Collectors.toList()));

			groupedCarsMap.forEach((manufacturer, cars) -> {

				BigDecimal priceTotal = BigDecimal.ZERO;
				BigDecimal priceMinTotal = BigDecimal.ZERO;

				for (Map<String, String> map : cars) {
					System.out.printf("%-13s%-8s%11s%7s\n", manufacturer, map.get("Type"), map.get("Min.Price"),
							map.get("Price"));
					priceTotal = priceTotal.add(new BigDecimal(map.get("Price")));
					priceMinTotal = priceMinTotal.add(new BigDecimal(map.get("Min.Price")));

				}
				TotalPrice.add(priceTotal);
				TotalMinPrice.add(priceMinTotal);
				System.out.printf("%-13s%18s%7s\n", "小計", priceMinTotal, priceTotal);

			});

			BigDecimal totalminprice = BigDecimal.ZERO;
			for (BigDecimal minprice : TotalMinPrice)
				totalminprice = totalminprice.add(minprice);

			BigDecimal totalprice = BigDecimal.ZERO;
			for (BigDecimal price : TotalPrice)
				totalprice = totalprice.add(price);

			System.out.printf("%-13s%18s%7s", "合計", totalminprice, totalprice);
			// 搜尋完所有廠商brand的資料後，印出底價以及售價的合計數

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}

