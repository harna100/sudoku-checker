public class Main {
	public static void main(String[] args){
		FileParser fp = new FileParser(args[0]);
		fp.parseFile();
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
