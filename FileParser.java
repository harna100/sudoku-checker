import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Paul-HP on 3/4/2017.
 */
public class FileParser {
	private String fileName;
	private int[][] grid;

	public FileParser(String fileName) {
		this.fileName = fileName;
		grid = new int[9][9];
	}

	public void parseFile(){
		try {
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	public void printBoard(){
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

	public int[][] getGrid() {
		return grid;
	}
}
