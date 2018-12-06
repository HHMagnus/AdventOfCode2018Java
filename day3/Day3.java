package day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Day3 {
	
	public class Rect{
		String id;
		int X,Y,W,H;
		public Rect(String id, int X, int Y, int W, int H) {
			this.id = id;
			this.X = X;
			this.Y = Y;
			this.W = W;
			this.H = H;
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
		new Day3();
	}
	
	public Day3() throws FileNotFoundException, URISyntaxException {
		Scanner s = new Scanner(new File(Day3.class.getResource("input_day3.txt").toURI()));

		int[][] claims = new int[1001][1001];
		
		List<Rect> rects = new LinkedList<Rect>();

		for(int i = 0; i < 1365; i++) {
			String rect = s.nextLine();
			rect = rect.replaceAll(" ", "");
			String id = rect.replaceAll("#", "").split("@")[0];
			rect = rect.split("@")[1];
			String cords = rect.split(":")[0];
			String size = rect.split(":")[1];
			
			int X = Integer.parseInt(cords.split(",")[0]);
			int Y = Integer.parseInt(cords.split(",")[1]);
			
			int W = Integer.parseInt(size.split("x")[0]);
			int H = Integer.parseInt(size.split("x")[1]);
			
			for(int x = X; x < X+W;x++) {
				for(int y = Y; y < Y+H; y++) {
					claims[x][y]++;
				}
			}
			
			rects.add(new Day3.Rect(id, X, Y, W, H));
			
			
		}
		
		s.close();
		
		
		// Part 1
		int squareClaims = 0;
		for(int i = 0; i < 1000; i++) {
			for(int j = 0; j < 1000; j++) {
				if(claims[i][j] > 1)
					squareClaims++;
			}
		}
		System.out.println("Part 1, square inch claimed: " + squareClaims);
		
		// Part 2
		rectloop:
		for(Rect r : rects) {
			for(int x =r.X; x < r.X+r.W;x++) {
				for(int y = r.Y; y < r.Y+r.H;y++) {
					if(claims[x][y] > 1)
						continue rectloop;
				}
			}
			System.out.println("Part 2, id: " + r.id);
		}
		
	}
}
