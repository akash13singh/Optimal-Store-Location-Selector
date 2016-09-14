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
	
	//calculate Manhattan distance from all grid points to the center
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
	
	//finds median and then distance from each point to median.
	private int findMinimumCost(List<Integer> xPoints,List<Integer> yPoints){
		
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
	
	// find minimum cost using median. uses sorting
	public  void findOptimalDeliveryCostSlow() throws IOException{
		
		InputStreamReader inputStream = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(inputStream);
		
		String numCases = bufferedReader.readLine();
		if(numCases == null || "".equals(numCases.trim())){
			System.out.println("Wrong Input");
			System.exit(0);
		}
		
		int numGrids = Integer.parseInt(numCases.trim());
		
		int counterTestCases = 0;
		
		while(counterTestCases < numGrids){
			List<Integer> xPoints = new ArrayList<Integer>();
			List<Integer> yPoints = new ArrayList<Integer>();
			String strInput = bufferedReader.readLine();
			String[] dims = strInput.split(" ");
			
			int rows = Integer.parseInt(dims[1]);
	
				for(int i =0; i< rows; i++){
					String[] str = bufferedReader.readLine().split(" ");
					//map is pain in java
					int col = 0;
					for(String s:str){		
					if("0".equals(str)) ;
					else{
						int numDelivery = Integer.parseInt(s);
						for(int count =0;count<numDelivery;count++){
							xPoints.add(i);
							yPoints.add(col);
						}	
					}
					col++;
				}
			}
			int minCost = findMinimumCost(xPoints, yPoints);
			System.out.println(minCost+" blocks");
			counterTestCases++;
		}      
	}
	
	//find minimal delivery cost using weighted average
	private void calculateMinimalDeliveryCost(int[] costX,int[] costY){
		int numX = costX.length;
		int numY = costY.length;
		int centerX = 0;
		int centerY = 0;
		int totalWeightX = 0;
		int totalWeightY=0;
		int weightedCostX=0;
		int weightedCostY=0;
		int minCostX =0;
		int minCostY =0;
		
		for(int i=0;i<numX;i++){
			totalWeightX += costX[i];	
			weightedCostX += costX[i]*i;			
		}
		
		for(int i =0;i<costY.length;i++){
			totalWeightY += costY[i];
			weightedCostY += costY[i]*i;
		}
		
		centerX = weightedCostX/totalWeightX;
		centerY = weightedCostY/totalWeightY;
		
		int[] cX = {centerX,centerX+1};
		int[] cY = {centerY,centerY+1};
		
		int minCost = Integer.MAX_VALUE;
		for(int i = 0; i<2;i++){
			
			for(int j = 0;j<2;j++){
				int cost = 0;
				minCostX=0;
				minCostY=0;
				
				for(int k =0;k<numX; k++ ){
					minCostX += Math.abs(k-cX[i])*costX[k];			
				}	
				for(int k =0;k<numY; k++ ){
					minCostY += Math.abs(k-cY[j])*costY[k];		
				}
				cost = minCostX + minCostY;
				//System.out.println(cost);
				if (cost<minCost){
					minCost=cost;
				}
			}
		}
		
		System.out.println(minCost + " blocks");

	}
	
	//find minimum delviery cost
	public void findOptimalDeliveryCost() throws IOException{
		InputStreamReader inputStream = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(inputStream);
		
		String numCases = bufferedReader.readLine();
		if(numCases == null || "".equals(numCases.trim())){
			System.out.println("Wrong Input");
			System.exit(0);
		}
		
		int numGrids = Integer.parseInt(numCases.trim());
		
		int counterTestCases = 0;
		
		while(counterTestCases < numGrids){
			String strInput = bufferedReader.readLine();
			String[] dims = strInput.split(" ");
			int rows = Integer.parseInt(dims[1]);
			int cols = Integer.parseInt(dims[0]);
			int[] xCost = new int[rows];
			int[] yCost = new int[cols];
			
			for(int i =0; i< rows; i++){
				String[] str = bufferedReader.readLine().split(" ");
				
				//map is pain in java
				for(int j =0; j<str.length;j++){		
					int numDelivery = Integer.parseInt(str[j]);
	                xCost[i] =  xCost[i] + numDelivery;
	                yCost[j] =  yCost[j] + numDelivery;	
				}
			}		

			calculateMinimalDeliveryCost(xCost,yCost);
			counterTestCases++;
		}
		
		
	}
	
	
	public static void main(String[] args){
		long startTime = System.currentTimeMillis();
		String[] input =null;
		LocationSelector ls = new LocationSelector();
		
		try {
			//ls.findOptimalDeliveryCostSlow();
			ls.findOptimalDeliveryCost();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	    long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	}
}
