/**
 * Created by Paul-HP on 3/9/2017.
 */
public class Utils {


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
