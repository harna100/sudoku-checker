import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class FileParser {
	private String fileName;
	private int[][] grid;

	public FileParser(String fileName) {
		this.fileName = fileName;
		grid = new int[9][9];
	}

	public void parseFile() throws FileNotFoundException, IOException {
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		String currLine;
		int currPos = 0;
		while((currLine = br.readLine()) != null){
			String[] vals = currLine.trim().split(",");
			for (int i = 0; i < 9; ++i) {
				grid[currPos][i] = Integer.parseInt(vals[i]);
			}
			++currPos;
		}
	}



	public int[][] getGrid() {
		return grid;
	}
}
