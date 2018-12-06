package day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Day2 {
	public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
		Scanner s = new Scanner(new File(Day2.class.getResource("input_day2.txt").toURI()));
		String[] boxes = new String[250];
		for(int i = 0; i < 250; i++) {
			boxes[i] = s.nextLine();
		}
		s.close();
		
		// Part 1
		int two = 0;
		int three = 0;
		
		for(int i = 0; i < 250; i++) {
			boolean added_two = false, added_three = false;
			for(char c : boxes[i].toCharArray()) {
				int count = boxes[i].length() - boxes[i].replaceAll(""+c, "").length();
				if(count == 2 && !added_two) {
					two++; 
					added_two = true;
				}
				else if(count == 3 && !added_three) {
					three++;
					added_three = true;
				}
			}
		}
		
		System.out.println("Part 1, Two: " + two + ", Three: " + three + "\nChecksum: " + two*three);
		
		// Part 2
		for(int i = 0; i < 250; i++) {
			for(int j = i+1; j < 250; j++) {
				int diffs = 0;
				for(int c = 0; c < boxes[i].length(); c++) {
					if(boxes[i].charAt(c) != boxes[j].charAt(c))
						diffs++;
				}
				if(diffs == 1) {
					String result = "";
					for(int c = 0; c < boxes[i].length(); c++) {
						if(boxes[i].charAt(c) == boxes[j].charAt(c))
							result += boxes[i].charAt(c);
					}
					System.out.println("Part 2, common: " + result);
					System.exit(0);
				}
			}
		}
		
	}
}
