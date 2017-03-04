import java.util.HashSet;
import java.util.Set;

public class Checker{

	public static boolean rowCheck(int[][] grid, int rowToCheck){
		Set<Integer> total = new HashSet<>();
		for (int i = 0; i < 9; ++i) {
			total.add(grid[rowToCheck][i]);
		}
		return total.size() == 9;
	}

	public static boolean colCheck(int[][] grid, int colToCheck){
		Set<Integer> total = new HashSet<>();
		for (int i = 0; i < 9; ++i) {
			total.add(grid[i][colToCheck]);
		}
		return total.size() == 9;
	}

	public static boolean subgridCheck(int[][] grid, int subCenterX, int subCenterY){
		Set<Integer> total = new HashSet<>();
		for (int i = -1; i < 2; ++i) {
			for (int j = -1; j < 2; ++j) {
				total.add(grid[subCenterY+i][subCenterX+j]);
			}
		}
		return total.size() == 9;
	}
	// idea for finding centers (i/3)*3 + 1 uses integer div
}