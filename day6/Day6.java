package day6;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Day6 {
	public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
		Scanner s = new Scanner(new File(Day6.class.getResource("input_day6.txt").toURI()));
		int X = -1,Y = -1,W = -1,H = -1;
		Point[] points = new Point[50];
		for(int i = 0; i < 50; i++) {
			String cords = s.nextLine().replaceAll(" ", "");
			String[] cs = cords.split(",");
			points[i] = new Point(Integer.parseInt(cs[0]),Integer.parseInt(cs[1]));
			
			// Determine bounds
			if(X == -1 || points[i].x < X)
				X = points[i].x;
			if(W == -1 || points[i].x > W)
				W = points[i].x;
			if(Y == -1 || points[i].y < Y)
				Y = points[i].y;
			if(H == -1 || points[i].y > H)
				H = points[i].y;
		}
		s.close();
		
		// Part 1
		System.out.println("Dimensions: " + X + ", " + Y + "; " + W + ", " + H);
		
		int[] sizes = new int[50];
		Set<Integer> outer = new TreeSet<Integer>();
		
		// Part 2 variables
		int region = 0;
		
		for(int i = 0-X; i <= W+X*2; i++) {
			for(int j = 0-Y; j <= H+Y*2; j++) {
				// part 1 point
				int point = -1;
				int distance = -1;
				
				// part 2 region
				int total = 0;
				boolean invalid = false;
				for(int pi = 0; pi < 50; pi++) {
					Point p = points[pi];
					int d = Math.abs(i - p.x) + Math.abs(j - p.y);
					
					//part 2 total
					total += d;
					
					//part 1 comparison
					if(d == distance) {
						invalid = true;
					}else if(distance == -1 || d < distance) {
						distance = d;
						point = pi;
						invalid = false;
					}
				}
				// part 2
				if(total < 10000)
					region++;
				
				// part 1 skip if more then 2 smallest distance
				if(invalid)
					continue;
				
				sizes[point]++;
				
				if(i == 0-X || i == W+X*2 || j == 0-Y || j == H+Y*2)
					outer.add(point);
			}
		}
		for(int i : outer) {
			sizes[i] = 0;
		}
		
		// Part 1
		System.out.println("Part 1, biggest area: " + Arrays.stream(sizes).max().getAsInt());
		
		// Part 2
		System.out.println("Part 2, region: " + region);
		
	}
}
