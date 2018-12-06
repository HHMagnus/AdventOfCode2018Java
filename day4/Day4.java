package day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Day4 {
	public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
		new Day4();
	}
	
	public class Event{
		LocalDateTime time;
		String eventInfo;
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		public Event(String event) {
			String time = event.split("\\] ")[0].replaceAll("\\[", "");
			this.time = LocalDateTime.parse(time, formatter);
			this.eventInfo = event.split("\\] ")[1];
		}
	}
	
	public class Sleep{
		LocalDateTime start, end;
		public Sleep(LocalDateTime start, LocalDateTime end) {
			this.start = start;
			this.end = end;
		}
		public int time_between() {
			return (int) start.until(end, ChronoUnit.MINUTES);
		}
	}
	
	public Day4() throws FileNotFoundException, URISyntaxException{
		Scanner s = new Scanner(new File(Day4.class.getResource("input_day4.txt").toURI()));
		List<Event> events = new ArrayList<Event>();
		while(s.hasNextLine())
			events.add(new Event(s.nextLine()));
		
		s.close();
		
		//Sort by date
		events.sort((e1, e2) -> {return e1.time.compareTo(e2.time);});
		
		// Put the guards sleeping schedule into a list
		Map<String, List<Sleep>> guard_timings = new HashMap<String, List<Sleep>>();
		
		String current_guard = "";
		for(int i = 0; i < events.size(); i++) {
			Event e = events.get(i);
			if(e.eventInfo.endsWith("shift")) {
				current_guard = e.eventInfo.split(" ")[1];
			}else {
				Event e2 = events.get(++i);
				
				if(!guard_timings.containsKey(current_guard))
					guard_timings.put(current_guard, new ArrayList<Sleep>());
				
				guard_timings.get(current_guard).add(new Sleep(e.time, e2.time));
			}
		}
		
		// Part 1 / Strategy 1
		
		// Find the guard that sleeps the most
		Map.Entry<String, List<Sleep>> max = null;
		for(Map.Entry<String, List<Sleep>> entry : guard_timings.entrySet()) {
			if(max == null || 
					entry.getValue().stream().mapToInt(Sleep::time_between).sum() 
						> max.getValue().stream().mapToInt(Sleep::time_between).sum())
				max = entry;
		}
		
		// Check minute he sleeps the most
		int occurances = -1;
		int x = -1;
		for(int i = 0; i < 60; i++) {
			final int ii = i;
			int occ = max.getValue().stream()
					.mapToInt((Sleep sl) -> sl.start.getMinute() <= ii && ii < sl.end.getMinute() ? 1 : 0).sum();
			if(occ > occurances) {
				occurances = occ;
				x = i;
			}
		}
		System.out.println("Part 1: " + max.getKey() + ", most prevent minute " + x);
		
		// Part 2 / Strategy 2
		
		occurances = -1;
		x = -1;
		String guard = "INVALID";
		for(Map.Entry<String, List<Sleep>> entry : guard_timings.entrySet()) {
			for(int i = 0; i < 60; i++) {
				final int ii = i;
				int occ = entry.getValue().stream()
						.mapToInt((sl) -> sl.start.getMinute() <= ii && ii < sl.end.getMinute() ? 1 : 0)
						.sum();
				if(occ > occurances) {
					occurances = occ;
					x = i;
					guard = entry.getKey();
				}
			}
		}
		
		System.out.println("Part 2, guard: " + guard + ", minute: " + x +", occurances: " + occurances);
	}
}
