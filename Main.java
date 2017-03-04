import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul-HP on 3/4/2017.
 */
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

	}
}
