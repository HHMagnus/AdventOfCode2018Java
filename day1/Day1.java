package day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Day1 {

	public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
		Scanner s = new Scanner(new File(Day1.class.getResource("input_day1.txt").toURI()));
		int[] frequencies = new int[1000];
		for(int i = 0; i < 1000; i++) {
			frequencies[i] = s.nextInt();
		}
		s.close();
		
		//Part 1
		int total = 0;
		for(int i = 0; i < 1000; i++) {
			total += frequencies[i];
		}
		System.out.println("Part 1: " + total);
		
		//Part 2
		total = 0;
		Set<Integer> known = new TreeSet<Integer>();
		
		while(true) {
			for(int i = 0; i < 1000; i++) {
				total += frequencies[i];
				if(!known.add(total)) {
					System.out.println("Part 2: " + total);
					System.exit(0);
				}
			}
		}
	}
}
