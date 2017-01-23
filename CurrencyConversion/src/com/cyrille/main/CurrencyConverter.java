package com.cyrille.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.algorithms.dijkstra.model.DijkstraAlgorithm;
import com.algorithms.dijkstra.model.Edge;
import com.algorithms.dijkstra.model.Graph;
import com.algorithms.dijkstra.model.Test;
import com.algorithms.dijkstra.model.Vertex;
import com.cyrille.tools.Tools;


//The code below is just a version beta and need substantial cleaning and optimization.
public class CurrencyConverter {
	  private static List<Vertex> nodes = new LinkedList<Vertex>();
	  private static Map edges = new LinkedHashMap<String, Edge>();
	  private static int  cur1 =-1 ;
	  private static int  cur2 =-1;
	  
	public static void main(String[] args) throws IOException {
		System.out.println("Please enter CUR1CUR2<return>  and receive a response to 4DP of precision :");
		
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		String cur1cur2 = bufferRead.readLine();

			CurrencyConverter c = new CurrencyConverter();
			c.run("input.csv",cur1cur2);
		
	}
	
	
	
	  public  void run(String fileName, String cur1cur2) {
		  	if(null == cur1cur2) return;
			buidGraphFromFile(fileName, cur1cur2.toUpperCase().trim());
			if(cur1 ==-1 || cur2 == -1) return;
			
		    // Lets check from node1 to node2
		    Graph graph = new Graph(nodes, new ArrayList<Edge>(edges.values()));
		    DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
		    dijkstra.execute(nodes.get(cur1));
		    LinkedList<Vertex> path = dijkstra.getPath(nodes.get(cur2));
		    
		    double allWeight=1;
		    
		    if(path != null){ //We will check that there is a path between the node
			    for( int j=0; j< path.size(); j++){
			    	
			    	if(j+1<path.size()){
			    		if(null != path.get(j) && null != path.get(j+1))
			    		allWeight *= getLane(path.get(j).getName(), path.get(j+1).getName()).getRate();
			    		
			    	}
			    }
			    
			    BigDecimal bd = new BigDecimal(allWeight).setScale(4, RoundingMode.HALF_EVEN);
			    System.out.println("Trader asks for "+cur1cur2+", return "+bd);
		    }else 
		    	System.out.println("Trader asks for "+cur1cur2+", return Not available");
		  }
	  
	  
	  
	  public static void   buidGraphFromFile(String fileName, String cur1cur2){
		  TreeSet<String> ts = new TreeSet<String>();
		  Map<String, Integer> edgeMapping = new LinkedHashMap<String, Integer>();
		  
		  Map<String, Double> fileNodes =  Tools.readFile(fileName);
		  String e;
		  String  CUR1 ;
		  String CUR2;
		  int i =0;
		  int j=0;
		   for( String key: fileNodes.keySet()){
			   e = key;
			   
			   CUR1 = e.substring(0,3);
			   CUR2 = e.substring(3,6);
			   if(!ts.contains(CUR1)){
				   ts.add(CUR1);
				   j=i++;
				    Vertex location = new Vertex(""+j, CUR1 );
				    edgeMapping.put(CUR1,j);
				     nodes.add(location);
			   }
			   
			   if(!ts.contains(CUR2)){
				   ts.add(CUR2);
				   j=i++;
				    Vertex location = new Vertex(""+j, CUR2 );
				    edgeMapping.put(CUR2,j);
				     nodes.add(location);
			   }	
			   
			   addLane(CUR1+CUR2, edgeMapping.get(CUR1),  edgeMapping.get(CUR2), fileNodes.get(e));
			   addLane(CUR2+CUR1, edgeMapping.get(CUR2),  edgeMapping.get(CUR1), 1/fileNodes.get(e));
		   }
		   
		   String cmdCUR1 = cur1cur2.substring(0,3);
		   String cmdCUR2 = cur1cur2.substring(3,6);
		   
		   if(null == edgeMapping.get(cmdCUR1)  || null == edgeMapping.get(cmdCUR2)) {
			   System.out.println("Trader asks for "+cur1cur2+", return Not available");
			   return;
		   }
		   
		   cur1 =edgeMapping.get(cmdCUR1);
		   cur2 =edgeMapping.get(cmdCUR2);
	  }
	  
	  private static void addLane(String laneId, int sourceLocNo, int destLocNo,
		      double rate) {
		    Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), rate);
		    edges.put(laneId, lane);
		  }
	  
	  private Edge getLane(String source, String rate){
		 return (Edge) edges.get(source+rate) ; 
	  }
	  
	  
}
