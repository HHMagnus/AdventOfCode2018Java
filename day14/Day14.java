package day14;

public class Day14 {
	
	public static void main(String [] args) {
		String recipes = "37";
		
		int elf1 = 0, elf2 = 1;
		String last_ten = "6666666666";
		int rounds = 0;
		while(last_ten.indexOf("990941") == -1) {
			
			// Getting elfs current recipe
			int c_elf1 = Integer.parseInt(""+recipes.charAt(elf1)),
					c_elf2 = Integer.parseInt(""+recipes.charAt(elf2));
			
			// Finding the sum and adding it to the recipe string
			int sum = c_elf1 + c_elf2;
			recipes += sum;
			last_ten += sum;
			if(sum >= 10)
				last_ten.substring(2);
			else
				last_ten.substring(1);
			
			// Calculating elfs next position
			elf1 += c_elf1+1;
			elf1 %= recipes.length();
			elf2 += c_elf2+1;
			elf2 %= recipes.length();
			
			System.out.println(rounds++);
		}
		
		// part 1
		System.out.println(recipes.substring(990941, 990951));
		
		// part 2
		System.out.println(recipes.toString().indexOf("990941"));
	}

}
