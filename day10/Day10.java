package day10;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Day10 {
	
	public class Point{
		int x, y, vx, vy;
		public Point(int x, int y, int vx, int vy) {
			this.x = x;
			this.y = y;
			this.vx = vx;
			this.vy = vy;
		}
	}
	
	public static void main(String[] args) {
		new Day10();
	}
	
	public Day10() {
		Scanner s = new Scanner(Day10.class.getResourceAsStream("input_day10.txt"));
		List<Point> points = new ArrayList<Point>();
		while(s.hasNextLine()) {
			String point = s.nextLine()
					.replaceAll(" ", "")
					.replaceAll("position=<", "")
					.replaceAll(">velocity=<", ";")
					.replaceAll(">", "");
			String p = point.split(";")[0];
			String v = point.split(";")[1];
			int x = Integer.parseInt(p.split(",")[0]);
			int y = Integer.parseInt(p.split(",")[1]);
			int vx = Integer.parseInt(v.split(",")[0]);
			int vy = Integer.parseInt(v.split(",")[1]);
			
			points.add(new Point(x,y,vx,vy));
		}
		s.close();
		
		JFrame frame = new JFrame("Day10");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SolutionPanel panel = new SolutionPanel(points);
		panel.setFocusable(true);
		panel.addKeyListener(panel);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
	
	class SolutionPanel extends JPanel implements KeyListener{
		private static final long serialVersionUID = 1L;
		int rounds = 0;
		List<Point> points;
		boolean forward = true;
		public SolutionPanel(List<Point> points) {
			this.points = points;
		}
		
		public Dimension getPreferredSize() {
			return new Dimension(750,750);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			System.out.println(forward ? rounds++ : rounds--);
			int disX, disY;
			disX = points.get(0).x;
			disY = points.get(0).y;
			for(Point p : points) {
				p.x += forward ? p.vx : - p.vx;
				p.y += forward ? p.vy : - p.vy;
				g.drawLine(750/2 + disX - p.x, 750/2 + disY - p.y, 750/2 + disX - p.x, 750/2 + disY - p.y);
			}
			
			// Happened on this input at 10931
			if(rounds < 10800)
				repaint();
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == 'W') {
				repaint();
			}
			
			//System.out.println("P: " + e.getKeyChar());
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyChar() == 'A') {
				forward = true;
				System.out.println("forward");
			}else if(e.getKeyChar() == 'D') {
				System.out.println("backward");
				forward = false;
			}
			//System.out.println("R: " + e.getKeyChar());
		}

		@Override
		public void keyTyped(KeyEvent e) {
			//System.out.println("T: " + e.getKeyChar());
			
		}
	}
}
