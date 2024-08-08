package com.cathaybk.practice.nt50341.b;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class practice2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Integer> result = new ArrayList<>();
		
		
		while ( result.size() < 6 ) {
			Random rand = new Random();
			int randNum = rand.nextInt(49)+1;
	
			if ( result.contains(randNum))  {
				continue;
			}
			else {
				result.add(randNum);
			}
			
		}
		
		System.out.print("排序前:");
		for(int randNum: result) {
			System.out.print(randNum);
			System.out.print(" ");
		}
		
		Collections.sort(result);
		
		System.out.println("");
		System.out.print("排序後:");
		
		for(int randNum: result) {
			System.out.print(randNum);
			System.out.print(" ");
		}

}
	}
