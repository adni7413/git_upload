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
import java.util.TreeSet;

public class practice6 {

	public static void main(String[] args) {

		try {
			InputStreamReader isr = new InputStreamReader(
					new FileInputStream("C:\\Users\\Admin\\Downloads\\Java評量_第6題cars.csv"));// 檔案讀取路徑
			BufferedReader reader = new BufferedReader(isr);
			String line = null;

			//建立list儲存車輛資料
			List<Map<String, String>> carList = new ArrayList<>();

			//讀取csv各行資料，並將四種屬性資料以Map傳入list
			while ((line = reader.readLine()) != null) {

				Map<String, String> map = new HashMap<>();

				String item[] = line.split(",");
				/** 讀取 **/
				String data1 = item[0].trim();
				String data2 = item[1].trim();
				String data3 = item[2].trim();
				String data4 = item[3].trim();
				map.put("MANUFACTURER", data1);
				map.put("TYPE", data2);
				map.put("MIN.PRICE", data3.toString());
				map.put("PRICE", data4.toString());

				carList.add(map);
				
			}

			//刪掉首列標題資料，方便後續比較價格順序
			carList.remove(0);
			//建立比較方法，先在compare方法內將字串轉成BigDecimal由大排到小比較價格
			Collections.sort(carList, new Comparator<Map<String, String>>() {

				@Override
				public int compare(Map<String, String> o1, Map<String, String> o2) {
					BigDecimal price1 = new BigDecimal(o1.get("PRICE"));
					BigDecimal price2 = new BigDecimal(o2.get("PRICE"));
					return price2.compareTo(price1);
				}
			});

			//新增csv檔到桌面
			BufferedWriter bufferedwrite = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream("C:\\Users\\Admin\\Desktop\\cars2.csv", true), "UTF-8"));

			bufferedwrite.write(new String(new byte[] { (byte) 0xef, (byte) 0xbb, (byte) 0xbf }));
			bufferedwrite.append((CharSequence) "MANUFACTURER" + "," + "TYPE" + "," + "MIN.PRICE" + "," + "PRICE");
			bufferedwrite.newLine();
			
			//將排序後的資料輸出至csv檔
			for (Map carInfo : carList) {

				bufferedwrite.append((CharSequence) carInfo.get("MANUFACTURER") + ",");
				bufferedwrite.append((CharSequence) carInfo.get("TYPE") + ",");
				bufferedwrite.append((CharSequence) carInfo.get("MIN.PRICE") + ",");
				bufferedwrite.append((CharSequence) carInfo.get("PRICE"));
				bufferedwrite.newLine();
			}
			bufferedwrite.flush();

			// 建立一組set儲存廠商資料
			Set<String> manufactureSet = new TreeSet<String>();
			manufactureSet.add("Audi");  //用迴圈加進去
			manufactureSet.add("Acura");
			manufactureSet.add("Buick");
			manufactureSet.add("BMW");
			manufactureSet.add("Chevrolet");
			manufactureSet.add("Cadillac");

			//首列輸出四屬性資料的標題
			System.out.printf("%-13s%-8s%-13s%-8s", "MANUFACTURER", "TYPE", "MIN.PRICE", "PRICE");
			System.out.println();
			//所有廠商底價以及售價的合計數
			BigDecimal TotalPrice = new BigDecimal("0.0");
			BigDecimal TotalMinPrice = new BigDecimal("0.0");

			// 建立大迴圈個別搜尋6個廠商brand
			for (String brand : manufactureSet) {
				
				//各別6個廠商底價以及售價的小計數
				BigDecimal priceTotal = new BigDecimal("0.0");
				BigDecimal priceMinTotal = new BigDecimal("0.0");

				// 建立小迴圈搜尋list裡面每輛車的資料。若list裡的map車商對應到大迴圈的廠商brand，則輸出四個屬性的資料，並加總底價以及售價(小計以及合計分別加總)
				for (Map<String, String> carInfo : carList) {

					if (carInfo.get("MANUFACTURER").equals(brand)) {
						System.out.printf("%-13s%-8s%11s%7s", carInfo.get("MANUFACTURER"), carInfo.get("TYPE"),
								carInfo.get("MIN.PRICE"), carInfo.get("PRICE"));
						System.out.println();
						priceTotal = priceTotal.add(new BigDecimal(carInfo.get("PRICE")));
						priceMinTotal = priceMinTotal.add(new BigDecimal(carInfo.get("MIN.PRICE")));
						TotalPrice = TotalPrice.add(new BigDecimal(carInfo.get("PRICE")));
						TotalMinPrice = TotalMinPrice.add(new BigDecimal(carInfo.get("MIN.PRICE")));
					}

				}
				System.out.printf("%-13s%18s%7s", "小計", priceMinTotal, priceTotal);
				System.out.println();
				//搜尋完一家廠商brand的資料後，印出底價以及售價的小計
			}
			System.out.printf("%-13s%18s%7s", "合計", TotalMinPrice, TotalPrice);
			//搜尋完所有廠商brand的資料後，印出底價以及售價的合計數

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
}
