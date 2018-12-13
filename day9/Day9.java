package day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;


public class Day9 {
	
	public static void main (String[] args) throws FileNotFoundException, URISyntaxException {
		Scanner s = new Scanner(new File(Day9.class.getResource("input_day9.txt").toURI()));
		String[] input = s.nextLine().split(" ");
		s.close();
		int players = Integer.parseInt(input[0]);
		int last_marble = Integer.parseInt(input[6]);
		
		long part1 = game(players, last_marble);
		System.out.println("Part 1: " +  part1);
		
		long part2 = game(players, last_marble*100);
		System.out.println("Part 2: " + part2);
	}
	
	public static long game(int players, int last_marble) {
		Deque<Integer> circle = new ArrayDeque<Integer>();
		circle.addFirst(0);
		
		long scores[] = new long[players];
		
		for(int i = 1; i <= last_marble; i++) {
			if (i % 23 == 0) {
				// add score
				for(int j = 0; j < 6; j++) { int f = circle.remove(); circle.addLast(f);}
				
				scores[i % players] += i + circle.pop();
			}else {
				for(int j = 0; j < 2; j++) { int l = circle.removeLast(); circle.addFirst(l);}
				circle.addLast(i);
			}
		}
		
		return Arrays.stream(scores).max().getAsLong();
	}
}
