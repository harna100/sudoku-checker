import java.util.*;

public class Checker{
	public static final Set<Integer> numbers = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));

	List<Integer> colErrors = new ArrayList<>();
	List<Integer> rowErrors = new ArrayList<>();
	List<Integer> subErrors = new ArrayList<>();

	Map<Integer, Set<Integer>> colMissing = new HashMap<>();
	Map<Integer, Set<Integer>> rowMissing = new HashMap<>();
	Map<Integer, Set<Integer>> subMissing = new HashMap<>();



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

	public static Set<Integer> rowMissing(int[][] grid, int row){
		Set<Integer> elements = new HashSet<>();
		for (int i = 0; i < 9; ++i) {
			elements.add(grid[row][i]);
		}

		return symmetricDifference(elements, numbers);
	}

	public static Set<Integer> colMissing(int[][] grid, int col){
		Set<Integer> elements = new HashSet<>();
		for (int i = 0; i < 9; ++i) {
			elements.add(grid[i][col]);
		}
		return symmetricDifference(elements, numbers);
	}

	public static Set<Integer> subgridMissing(int[][] grid, int subgrid){
		Set<Integer> elements = new HashSet<>();
		int subCenterX = (subgrid%3)*3 + 1;
		int subCenterY = (subgrid/3)*3 + 1;

		for (int i = -1; i < 2; ++i) {
			for (int j = -1; j < 2; ++j) {
				elements.add(grid[subCenterY+i][subCenterX+j]);
			}
		}

		return symmetricDifference(elements, numbers);
	}

	// taken from https://codereview.stackexchange.com/questions/32297/is-there-a-better-way-to-find-the-uncommon-elements-from-two-sets
	private static Set<Integer> symmetricDifference(Set<Integer> a, Set<Integer> b) {
		Set<Integer> result = new HashSet<>(a);
		for (Integer element : b) {
			// .add() returns false if element already exists
			if (!result.add(element)) {
				result.remove(element);
			}
		}
		return result;
	}

}