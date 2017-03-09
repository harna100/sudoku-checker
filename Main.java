import java.util.*;

public class Main {
	public static void main(String[] args){
		FileParser fp = new FileParser(args[0]);
		fp.parseFile();
		fp.printBoard();

		List<Integer> colErrors = new ArrayList<>();
		List<Integer> rowErrors = new ArrayList<>();
		List<Integer> subErrors = new ArrayList<>();

		int[][] grid = fp.getGrid();
		for (int i = 0; i < 9; i++) {
			if(!Checker.colCheck(grid,i)){
				colErrors.add(i);
			}
			if(!Checker.rowCheck(grid,i)){
				rowErrors.add(i);
			}
			int centerX = (i%3)*3 + 1;
			int centerY = (i/3)*3 + 1;
			if(!Checker.subgridCheck(grid,centerX,centerY)){
				subErrors.add(i);
			}
		}
		System.out.println("subErrors = " + subErrors);
		System.out.println("rowErrors = " + rowErrors);
		System.out.println("colErrors = " + colErrors);

		Map<Integer, Set<Integer>> colMissing = new HashMap<>();
		Map<Integer, Set<Integer>> rowMissing = new HashMap<>();
		Map<Integer, Set<Integer>> subMissing = new HashMap<>();

		for (Integer colError : colErrors) {
			colMissing.put(colError, Checker.colMissing(grid, colError));
		}

		for (Integer rowError : rowErrors) {
			rowMissing.put(rowError, Checker.rowMissing(grid, rowError));
		}

		for (Integer subError : subErrors) {
			subMissing.put(subError, Checker.subgridMissing(grid,subError));
		}

		System.out.println("colMissing = " + colMissing);
		System.out.println("rowMissing = " + rowMissing);
		System.out.println("subMissing = " + subMissing);

		int[][] toModify = grid.clone();

		int x = rowMissing.keySet().iterator().next();
		int y = colMissing.keySet().iterator().next();

		System.out.println("Error at Column " + y+1 + " Row " + x+1 + ".");
		System.out.println("Current Value: " + grid[y][x] + " Recommended: " +  colMissing.get(y).iterator().next());

		toModify[y][x] = colMissing.get(y).iterator().next();
		System.out.println("New Board: ");
		PrintBoard(toModify);

	}
	public static void PrintBoard(int[][] grid){
		for (int[] ints : grid) {
			StringBuilder sb = new StringBuilder();
			for (int anInt : ints) {
				sb.append(anInt);
				sb.append(",");
			}
			sb.setLength(sb.length()-1);
			System.out.println(sb.toString());
		}
	}
}
