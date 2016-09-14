import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *  @author akash
 * Code to find the cost of minimum cost  from optimal location
 */

public class LocationSelector {
	
	// number of test cases
	private int numTestCases = 0;
	
	//list of different grids
	private List<int[][]> grids = null;
	
	/**
	 * @return the numTestCases
	 */
	public int getNumTestCases() {
		return numTestCases;
	}
    

	/**
	 * @param numTestCases the numTestCases to set
	 */
	public void setNumTestCases(int numTestCases) {
		this.numTestCases = numTestCases;
	}


	public LocationSelector(){
		grids = new ArrayList<int[][]>();
	}
	
	// reads input into list grids
	private  void readInput() throws IOException{
		
		InputStreamReader inputStream = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(inputStream);
		
		String numCases = bufferedReader.readLine();
		if(numCases == null || "".equals(numCases.trim())){
			System.out.println("Wrong Input");
			System.exit(0);
		}
		
		setNumTestCases(Integer.parseInt(numCases.trim()));
		
		int counterTestCases = 0;
		
		while(counterTestCases < getNumTestCases()){
			
			String strInput = bufferedReader.readLine();
			String[] dims = strInput.split(" ");
			
			int rows = Integer.parseInt(dims[1]);
			int cols = Integer.parseInt(dims[0]);
			int[][] grid = new int[rows][cols];
			
			for(int i =0; i< rows; i++){
				String[] streamStorage = bufferedReader.readLine().split(" ");
				//map is pain in java
				grid[i] = Arrays.stream(streamStorage).mapToInt(str ->Integer.parseInt(str)).toArray();
			}
			
			grids.add(grid);	
			counterTestCases++;
		}      
	}
	
	//calculate manhattan distance from all grid points to the centre
	private int calculateManhattanDistance(List<Integer> xPoints,List<Integer> yPoints, int xCentre, int yCentre){
		
		System.out.println("median : ("+xCentre+","+yCentre+")");
		int xDistance = 0;
		int yDistance = 0;
		
		for(int i =0;i<xPoints.size();i++){
			xDistance = xDistance+  Math.abs(xPoints.get(i) - xCentre);
			yDistance = yDistance + Math.abs(yPoints.get(i) - yCentre);
		}
		
		return xDistance+yDistance;
	}
	
	
	// find minimum cost from 
	public int findMinimumCost(int[][] grid){
		
		//lists of x & y cordinates of the delivery points.
		List<Integer> xPoints = new ArrayList<Integer>();
		List<Integer> yPoints = new ArrayList<Integer>();
		
		int rows = grid.length;
		int cols = grid[0].length;
		
		for(int i = 0;i<rows;i++){
			for(int j =0;j<cols;j++){
				
				int numDelivery = grid[i][j];
				
				//add 1 grid point for each delivery to that grid point
				if(numDelivery>0){
					for(int k = 0;k<numDelivery; k++){
						xPoints.add(i);
						yPoints.add(j);
					}
				}
			}
		}
		
		Collections.sort(xPoints);
		Collections.sort(yPoints);
		boolean isTwoMedians = false;
		
		int xMedianIndex = xPoints.size()/2;
		int yMedianIndex = yPoints.size()/2;
		int xMedian = xPoints.get(xMedianIndex);
		int yMedian = yPoints.get(yMedianIndex);
		
		int cost = calculateManhattanDistance(xPoints,yPoints,xMedian,yMedian);
		
		//if the number of points are even, there can be two different medians.
		if(xPoints.size()%2 == 0 && (xMedian != xPoints.get(xMedianIndex-1) || yMedian != yPoints.get(yMedianIndex-1))){
			int cost2 = calculateManhattanDistance(xPoints,yPoints,xPoints.get(xMedianIndex-1),yPoints.get(yMedianIndex-1));
			if( cost2 < cost)
				cost = cost2;
		}
		
		return cost;
		
	}
	
	// verify if arrays are read correctly
	private void printGrids(){
		
		for(int[][] grid: grids){
			System.out.println(Arrays.deepToString(grid));
		}
	}
	
	
	public static void main(String[] args){
		String[] input =null;
		LocationSelector ls = new LocationSelector();
		
		try {
			ls.readInput();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int[][] grid: ls.grids){
			int cost = ls.findMinimumCost(grid);
			System.out.println(cost+" blocks");
		}
		
	}
}
