package day12;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Day12 {
	
	public static void main(String [] args) {
		Scanner s = new Scanner(Day12.class.getResourceAsStream("input_day12.txt"));
		String initial = s.nextLine().replace("initial state: ", "");
		s.nextLine();
		// Rules match: string -> char, so a map is perfect
		Map<String, Character> transformation = new TreeMap<String, Character>();
		while(s.hasNextLine()) {
			String trans = s.nextLine();
			String[] t = trans.split(" ");
			transformation.put(t[0], t[2].charAt(0));
		}
		s.close();
		
		// Extending the pots to the left
		String left_pots = "..........";
		initial = left_pots + initial;
		
		// the left offset
		int index_offset = left_pots.length();
		
		// keeping track of the number of rounds
		int rounds = 0;
		// and the previous generation
		String prev = "-1";
		
		// Running until stable generation is found
		while(true) {
			// Putting extra pots on the right if needed
			if(initial.substring(initial.length()-5).contains("#"))
				initial += ".....";
						
			// Growing the plants one generation
			String result = "..";
			for(int i = 2; i < initial.length()-2; i++) {
				String gen = initial.substring(i-2, i+3);
				result += transformation.get(gen);
			}
			result += "..";
			initial = result;
			
			// Increasing round number
			rounds++;
			//System.out.println("round " + rounds + ", " + initial );
			
			// Finding the sum of plants that generation
			int sum = sum(initial, index_offset);
			
			// Print out part 1
			if(rounds == 20) {
				System.out.println("Part 1: " + sum);
			}
			
			// Checking if previous generation is in this generation
			if(initial.contains(prev))
				break;
			prev = initial;
			
		}
		
		// The difference between these two stable generation
		int diff = sum(initial, index_offset) - sum(prev, index_offset);
		
		// The sum after five billion years is the current stabilized sum plus the years until then times the difference between the two stable generations
		long result = (50000000000L - rounds) * diff + sum(initial, index_offset);
		System.out.println("Part 2: " + result);
		
	}
	
	// Finding the sum of all the plants indexes
	public static int sum(String plants, int index_offset) {
		int result = 0;
		for(int i = 0 ; i < plants.length(); i++) {
			if(plants.charAt(i) == '#')
				result += (i - index_offset);
		}
		return result;
	}
}
