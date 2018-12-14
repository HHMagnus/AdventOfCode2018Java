package day11;

public class Day11 {

	public static void main(String[] args) {
		int input = 7165;

		int[][] powerArea = new int[300][300];

		for (int x = 0; x < 300; x++) {
			for (int y = 0; y < 300; y++) {
				int rackId = 10 + x;
				int powerLevel = rackId * y;
				powerLevel += input;
				powerLevel *= rackId;
				// Keep hundred digit
				powerLevel %= 1000;
				powerLevel /= 100;

				powerLevel -= 5;
				powerArea[x][y] = powerLevel;
			}
		}
		
		//int size = 3;
		
		int max = -999999999;
		int topLeftX = -1;
		int topLeftY = -1;
		int sizeM = -1;
		for(int size = 1; size <= 300; size++) {
			// Printing since solution can take some time
			System.out.println("size: " + size);
			for (int x = 0; x < 301-size; x++) {
				for (int y = 1; y < 301-size; y++) {
					int area = 0;
					for(int i = 0; i < size; i++) {
						for(int j = 0; j < size; j++) {
							area += powerArea[x+i][y+j];
						}
					}
					if(area > max) {
						max = area;
						topLeftX = x;
						topLeftY = y;
						sizeM = size;
					}
				}
			}
		}
		
		System.out.println("Part 1: " + topLeftX + "," + topLeftY + "," + sizeM);
	}
}
