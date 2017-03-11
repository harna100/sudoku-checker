import java.util.*;

public class Checker{
	public static final Set<Integer> numbers = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));

	private List<Integer> colErrors;
	private List<Integer> rowErrors;
	private List<Integer> subErrors;

	private Map<Integer, Set<Integer>> colMissing;
	private Map<Integer, Set<Integer>> rowMissing;
	private Map<Integer, Set<Integer>> subMissing;

	private int[][] grid;

	public Checker(int[][] gridToCheck) {
		colErrors = new ArrayList<>();
		rowErrors = new ArrayList<>();
		subErrors = new ArrayList<>();
		colMissing = new HashMap<>();
		rowMissing = new HashMap<>();
		subMissing = new HashMap<>();
		grid = gridToCheck;
	}

	public boolean check(){
		// spin off row check thread
		Thread rowCheckThread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 9; i++) {
					if(!rowCheck(i)){
						rowErrors.add(i);
					}
				}
			}
		});
		rowCheckThread.start();

		// spin off col check thread
		Thread colCheckThread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 9; i++) {
					if(!colCheck(i)){
						colErrors.add(i);
					}
				}
			}
		});
		colCheckThread.start();

		// spin off subgrid check thread
		Thread subgridCheckThread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 9; i++) {
					if(!subgridCheck(i)){
						subErrors.add(i);
					}
				}
			}
		});
		subgridCheckThread.start();

		// join the threads
		try {
			rowCheckThread.join();
			colCheckThread.join();
			subgridCheckThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		System.out.println("subErrors = " + subErrors);
		System.out.println("rowErrors = " + rowErrors);
		System.out.println("colErrors = " + colErrors);

		if(subErrors.isEmpty() && rowErrors.isEmpty() && colErrors.isEmpty())
			return true;

		// spin off row find missing thread
		Thread rowThread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (Integer rowError : rowErrors) {
					rowMissing.put(rowError, rowMissing(rowError));
				}
			}
		});
		rowThread.start();

		// spin off col find missing thread
		Thread colThread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (Integer colError : colErrors) {
					colMissing.put(colError, colMissing(colError));
				}
			}
		});
		colThread.start();

		// spin off subgrid find missing thread
		Thread subThread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (Integer subError : subErrors) {
					subMissing.put(subError, subgridMissing(subError));
				}
			}
		});
		subThread.start();

		// join the threads
		try {
			rowThread.join();
			colThread.join();
			subThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		System.out.println("colMissing = " + colMissing);
		System.out.println("rowMissing = " + rowMissing);
		System.out.println("subMissing = " + subMissing);
		return false;
	}

	public void fix(){
		int[][] toModify = new int[grid.length][];
		for (int i = 0; i < grid.length; i++) {
			toModify[i] = grid[i].clone();
		}

//		int row = rowMissing.keySet().iterator().next();
//		int col = colMissing.keySet().iterator().next();

		for (Integer row : rowMissing.keySet()) {
			for (Integer col : colMissing.keySet()) {
				if(checkCell(col,row) != 3){
					continue;
				}
				System.out.println("Error at Column " + (col+1) + " Row " + (row+1) + ".");
				Set<Integer> currColMissing = colMissing.get(col);
				Set<Integer> currRowMissing = rowMissing.get(row);
				Set<Integer> intersection = new HashSet<>(currRowMissing);
				intersection.retainAll(currColMissing);
				int newVal = intersection.iterator().next();
				System.out.println("Current Value: " + grid[row][col] + " Recommended: " +  newVal);
				toModify[row][col] = newVal;
			}
		}

		/*while(rowMissing.keySet().iterator().hasNext()){
			int row = rowMissing.keySet().iterator().next();
			while(colMissing.keySet().iterator().hasNext()){
				int col = colMissing.keySet().iterator().next();
				if(checkCell(col,row) != 3){
					continue;
				}
				System.out.println("Error at Column " + (col+1) + " Row " + (row+1) + ".");
				Set<Integer> currColMissing = colMissing.get(col);
				Set<Integer> currRowMissing = rowMissing.get(row);
				Set<Integer> intersection = new HashSet<>(currRowMissing);
				intersection.retainAll(currColMissing);
				int newVal = intersection.iterator().next();
				System.out.println("Current Value: " + grid[row][col] + " Recommended: " +  newVal);
				toModify[row][col] = newVal;
			}
		}*/
//		System.out.println("Error at Column " + (col+1) + " Row " + (row+1) + ".");
//		System.out.println("Current Value: " + grid[row][col] + " Recommended: " +  colMissing.get(col).iterator().next());

//		toModify[row][col] = colMissing.get(col).iterator().next();
		System.out.println("New Board: ");
		Utils.PrintBoard(toModify);
	}

	private boolean rowCheck(int rowToCheck){
		Set<Integer> total = new HashSet<>();
		for (int i = 0; i < 9; ++i) {
			total.add(grid[rowToCheck][i]);
		}
		return total.size() == 9;
	}

	private boolean colCheck(int colToCheck){
		Set<Integer> total = new HashSet<>();
		for (int i = 0; i < 9; ++i) {
			total.add(grid[i][colToCheck]);
		}
		return total.size() == 9;
	}

	private boolean subgridCheck(int subgrid){
		Set<Integer> total = new HashSet<>();
		int subCenterX = (subgrid%3)*3 + 1;
		int subCenterY = (subgrid/3)*3 + 1;
		for (int i = -1; i < 2; ++i) {
			for (int j = -1; j < 2; ++j) {
				total.add(grid[subCenterY+i][subCenterX+j]);
			}
		}
		return total.size() == 9;
	}

	private Set<Integer> rowMissing(int row){
		Set<Integer> elements = new HashSet<>();
		for (int i = 0; i < 9; ++i) {
			elements.add(grid[row][i]);
		}

		return symmetricDifference(elements, numbers);
	}

	private Set<Integer> colMissing(int col){
		Set<Integer> elements = new HashSet<>();
		for (int i = 0; i < 9; ++i) {
			elements.add(grid[i][col]);
		}
		return symmetricDifference(elements, numbers);
	}

	private Set<Integer> subgridMissing(int subgrid){
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

	private int checkCell(int col, int row){
		int toReturn = 0;
		//check row
		toReturn += rowCheck(row) ? 0:1;
		//check col
		toReturn += colCheck(col) ? 0:1;
		//check sub grid
		toReturn += subgridCheck((row/3*3 + col/3))? 0:1;
		return toReturn;
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