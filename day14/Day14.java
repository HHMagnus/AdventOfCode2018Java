package day14;

import java.util.ArrayList;
import java.util.List;

public class Day14 {
	
	// Solution is using input 990941 (hardcoded)
	public static void main(String [] args) {
		List<Integer> recipes = new ArrayList<Integer>();
		// Starting recipes
		recipes.add(3);
		recipes.add(7);
		
		//Starting elf positions
		int elf1 = 0, elf2 = 1;
		
		// continue while input hasn't been found
		while(true){
			// Getting elfs current recipe
			int c_elf1 = recipes.get(elf1),
					c_elf2 = recipes.get(elf2);
			
			// Finding the sum and adding it to the recipe string
			int sum = c_elf1 + c_elf2;
			if(sum > 10) {
				recipes.add(sum / 10);
				recipes.add(sum % 10);
			}else if(sum == 10) {
				recipes.add(1);
				recipes.add(0);
			}else {
				recipes.add(sum);
			}
			
			// Calculating elfs next position
			elf1 += c_elf1+1;
			elf1 %= recipes.size();
			elf2 += c_elf2+1;
			elf2 %= recipes.size();
			
			// Stop when input found, since two digits can be added from the sum we need to check both
			int size = recipes.size();
			if((recipes.get(size-1) == 1 &&
					recipes.get(size-2) == 4 &&
					recipes.get(size-3) == 9 &&
					recipes.get(size-4) == 0 &&
					recipes.get(size-5) == 9 &&
					recipes.get(size-6) == 9) 
				||
					(recipes.get(size-2) == 1 &&
					recipes.get(size-3) == 4 &&
					recipes.get(size-4) == 9 &&
					recipes.get(size-5) == 0 &&
					recipes.get(size-6) == 9 &&
					recipes.get(size-7) == 9)){
				break;
			}
		}
		
		// part 1
		System.out.print("Part 1: ");
		for(int i = 0; i < 10; i++) {
			System.out.print(recipes.get(990941 + i));
		}
		System.out.println("");
		
		// part 2
		int size = recipes.size();
		// there's two cases, if the sum was with 1 or 2 digits
		System.out.println("Part 2: " + (recipes.get(size - 5) == 9 ? size-6 : size-7));
	}

}
