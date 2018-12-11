package day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.Scanner;


public class Day8 {
	public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
		Scanner s = new Scanner(new File(Day8.class.getResource("input_day8.txt").toURI()));
		LinkedList<Integer> input = new LinkedList<Integer>();
		while(s.hasNext()) input.add(s.nextInt());
		s.close();
		
		// Part 1
		System.out.println("Part 1: " + getMetaSum(new LinkedList<Integer>(input)));
		
		// Part 2
		System.out.println("Part 2: " + getMetaIndexValue(new LinkedList<Integer>(input)));
	}
	
	// Part 1, finding the sum of all meta entries
	public static int getMetaSum(LinkedList<Integer> input) {
		int x = input.removeFirst();
		int y = input.removeFirst();
		int sum = 0;
		for(int i = 0; i < x; i++) {
			sum += getMetaSum(input);
		}
		for(int i = 0; i < y; i++) {
			sum += input.removeFirst();
		}
		return sum;
	}
	
	public static int getMetaIndexValue(LinkedList<Integer> input) {
		int x = input.removeFirst();
		int y = input.removeFirst();
		int sums[] = new int[x];
		for(int i = 0; i < x; i++)
			sums[i] = getMetaIndexValue(input);
		
		int sum = 0;
		for(int i = 0; i < y; i++) {
			int z = input.removeFirst();
			// 0 children
			if(x == 0)
				sum += z;
			// child exists
			if(x > 0 && z <= x)
				sum += sums[z-1];
		}
		return sum;
	}
}
