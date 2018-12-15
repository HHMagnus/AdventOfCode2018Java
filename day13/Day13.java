package day13;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Day13 {
	
	public class Cart{
		int originX, originY;
		int x, y, vx, vy;
		int hitIntersections = 0;
		boolean crashed = false;
		
		public Cart(int x, int y, int vx, int vy) {
			this.x = x;
			this.y = y;
			this.vx = vx;
			this.vy = vy;
			this.originX = x;
			this.originY = y;
		}
		
		public String toString() {
			return "pos: " + x + "," + y + ", v:" + vx + "," + vy + ", origin:" + originX +"," + originY;
		}
	}
	
	public static void main(String [] args) {
		new Day13();
	}
	
	public Day13() {
		Scanner s = new Scanner(Day13.class.getResourceAsStream("input_day13.txt"));
		char[][] track = new char[150][];
		for(int i = 0; i < 150; i ++) {
			track[i] = s.nextLine().toCharArray();
		}
		s.close();
		List<Cart> carts = new ArrayList<Cart>();
		for(int x = 0; x < 150; x++) {
			for(int y = 0; y < 150; y++) {
				char c = track[y][x];
				switch (c) {
				case '<':
					carts.add(new Cart(x,y,-1,0));
					track[y][x] = '-';
					break;
				case '>':
					carts.add(new Cart(x,y,1,0));
					track[y][x] = '-';
					break;
				case '^':
					carts.add(new Cart(x,y,0,-1));
					track[y][x] = '|';
					break;
				case 'v':
					carts.add(new Cart(x,y,0,1));
					track[y][x]= '|';
					break;
				}
			}
		}
		
		JFrame frame = new JFrame("Day13");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SolutionPanel panel = new SolutionPanel(track,carts);
		panel.setFocusable(true);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
	
	public class SolutionPanel extends JPanel{
		private static final long serialVersionUID = 1L;
		
		char[][] track;
		List<Cart> carts;
		
		boolean first_crash_happened = false;
		
		public SolutionPanel(char[][] track, List<Cart> carts) {
			this.carts = carts;
			this.track = track;
			
			ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
			executor.scheduleAtFixedRate(() ->{
				carts.sort((c1, c2) -> {return (c1.x == c2.x) ? c1.y - c2.y : c1.x - c2.x;});;
				for(Cart c : carts) {
					if(c.crashed) 
						continue;
					c.x += c.vx;
					c.y += c.vy;
					char t = track[c.y][c.x];
					// Changing direction
					if(t == '/') {
						int tempX = c.vx;
						c.vx = -1*c.vy;
						c.vy = -1*tempX;
					}else if(t == '\\') {
						int tempX = c.vx;
						c.vx = c.vy;
						c.vy = tempX;
						
					}
					// Intersection
					else if(t == '+') {
						if(c.hitIntersections == 0) {
							// turn left
							int tempX = c.vx;
							c.vx = c.vy;
							c.vy = -1*tempX;
						}else if(c.hitIntersections == 2) {
							// turn right
							int tempX = c.vx;
							c.vx = -1*c.vy;
							c.vy = tempX;
						}// else go straight
						c.hitIntersections = (c.hitIntersections+1) % 3;
					}
					if(carts.stream().filter(c2 -> !c2.crashed && c2.x == c.x && c2.y == c.y && c2 != c).count() >= 1) {
						c.crashed = true;
						carts.stream().filter(c2 -> !c2.crashed && c2.x == c.x && c2.y == c.y && c2 != c).findFirst().get().crashed = true;
						if(!first_crash_happened) {
							System.out.println("Part 1: " + c.x + "," +c.y);
							first_crash_happened = true;
						}
						
						if(carts.stream().filter(c2 -> !c2.crashed).count() == 1) {
							// might have to add 1 to position
							System.out.println("Part 2: " + carts.stream().filter(c2 -> !c2.crashed).findFirst().get());
						}
					}
				}
				repaint();
			}, 0, 25, TimeUnit.MILLISECONDS);
		}
		
		public Dimension getPreferredSize() {
			return new Dimension(750,750);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			int m = 5;
			for(int x = 0; x < 150; x++) {
				for(int y = 0; y < 150; y++) {
					if(track[y][x] == '|')
						g.drawLine(x*m+2, y*m, x*m+2, y*m+4);
					else if (track[y][x] == '-')
						g.drawLine(x*m, y*m+2, x*m+4, y*m+2);
					else if (track[y][x] == '+') {
						g.drawLine(x*m+2, y*m, x*m+2, y*m+4);
						g.drawLine(x*m, y*m+2, x*m+4, y*m+2);
					}else if(track[y][x] == '\\') {
						if(x != 149 && track[y][x+1] == '-')
							g.drawLine(x*m+2, y*m, x*m+4, y*m+2);
						else
							g.drawLine(x*m, y*m+2, x*m+2, y*m+4);
					}else if (track[y][x] == '/') {
						if(x != 149 && track[y][x+1] == '-')
							g.drawLine(x*m+2, y*m+5, x*m+5, y*m+2);
						else
							g.drawLine(x*m+2, y*m, x*m, y*m+2);
					}
				}
			}
			
			for(Cart c : carts)
				if(!c.crashed)
					g.drawOval(c.x*m, c.y*m, 4, 4);
			
		}
		
	}
}
