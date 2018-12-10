package day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Day7 {
	public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
		new Day7();
	}
	
	class Node implements Comparable<Node>{
		String name;
		List<Edge> edgesIn;
		List<Edge> edgeOut;
		int time;
		
		public Node(String name, int time) {
			this.name = name;
			this.edgesIn = new LinkedList<Edge>();
			this.edgeOut = new LinkedList<Edge>();
			this.time = time;
		}
		
		public int compareTo(Node n) {
			return name.charAt(0) - n.name.charAt(0);
		}
	}
	
	class Edge{
		Node to, from;
		public Edge(Node to, Node from) {
			this.to = to;
			this.from = from;
		}
	}
	
	public Day7() throws FileNotFoundException, URISyntaxException {
		
		//Part 1
		Scanner s = new Scanner(new File(Day7.class.getResource("input_day7.txt").toURI()));
		Map<String, Node> nodes = new TreeMap<String, Node>();
		while(s.hasNext()) {
			String[] input = s.nextLine().split(" ");
			String to = input[7], from = input[1];
			Node t = nodes.get(to);
			if(t == null) {
				t = new Node(to, 60 + to.charAt(0));
				nodes.put(to, t);
			}
			Node f = nodes.get(from);
			if(f == null) {
				f = new Node(from, 60 + from.charAt(0));
				nodes.put(from, f);
			}
			Edge e = new Edge(t, f);
			f.edgeOut.add(e);
			t.edgesIn.add(e);
		}
		s.close();
		
		Set<Node> zeroIn = new TreeSet<Node>();
		for(Node n : nodes.values()) {
			if(n.edgesIn.size() == 0)
				zeroIn.add(n);
		}
		
		String result = "";
		while(!zeroIn.isEmpty()) {
			Node n = zeroIn.iterator().next();
			zeroIn.remove(n);
			
			result += n.name;
			
			for(Edge e : n.edgeOut) {
				Node t = e.to;
				t.edgesIn.remove(e);
				
				if(t.edgesIn.size() == 0)
					zeroIn.add(t);
			}
			
		}
		System.out.println("Part 1: " + result);
		
		// Part 2
		s = new Scanner(new File(Day7.class.getResource("input_day7.txt").toURI()));
		nodes = new TreeMap<String, Node>();
		while(s.hasNext()) {
			String[] input = s.nextLine().split(" ");
			String to = input[7], from = input[1];
			Node t = nodes.get(to);
			if(t == null) {
				t = new Node(to, 61 - 'A'+to.charAt(0));
				nodes.put(to, t);
			}
			Node f = nodes.get(from);
			if(f == null) {
				f = new Node(from, 61 - 'A'+from.charAt(0));
				nodes.put(from, f);
			}
			Edge e = new Edge(t, f);
			f.edgeOut.add(e);
			t.edgesIn.add(e);
		}
		s.close();
		
		zeroIn = new TreeSet<Node>();
		for(Node n : nodes.values()) {
			if(n.edgesIn.size() == 0)
				zeroIn.add(n);
		}
		
		result = "";
		Node[] works = new Node[6];
		for(int i = 0; i < 6; i++) {
			if(zeroIn.isEmpty()) break;
			
			works[i] = zeroIn.iterator().next();
			zeroIn.remove(works[i]);
		}
		int time = 0;
		while(result.length() < nodes.size()) {
			time++;
			for(int i = 0; i < 6; i++) {
				Node n = works[i];
				if(n == null) continue;
				n.time--;
				System.out.println(n.time);
				if(n.time == 0) {
					result += n.name;
					works[i] = null;
					for(Edge e : n.edgeOut) {
						Node o = e.to;
						o.edgesIn.remove(e);
						
						if(o.edgesIn.size() == 0)
							zeroIn.add(o);
					}
				}
			}
			
			for(int i = 0; i < 6; i++) {
				if(zeroIn.isEmpty()) break;
				
				if(works[i] != null) continue;
				
				works[i] = zeroIn.iterator().next();
				zeroIn.remove(works[i]);
			}
			
		}
		
		System.out.println("Part 2: " + time);
		
	}
}
