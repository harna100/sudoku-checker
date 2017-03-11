import java.io.IOException;

public class Main {
	public static void main(String[] args){
		if(args.length != 1){
			System.out.println("Wrong number of arguments. Aborting.");
			System.exit(1);
		}
		FileParser fp = new FileParser(args[0]);
		try {
			fp.parseFile();
		} catch (IOException e) {
			System.out.println("Error reading file. Aborting.");
			System.exit(1);
		}
		System.out.println("Inputted board:");
		Utils.PrintBoard(fp.getGrid());

		int[][] grid = fp.getGrid();

		Checker checker = new Checker(grid);
		boolean noErrors = checker.check();
		if(noErrors){
			System.out.println("No errors found");
		}
		else{
			checker.fix();
		}

	}
}
