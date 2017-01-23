package com.algorithms.dijkstra.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.cyrille.tools.Tools;


	public class Test {

	  private static List<Vertex> nodes = new LinkedList<Vertex>();
	  private static Map edges = new LinkedHashMap<String, Edge>();
	  private String cur1 ="";
	  private String cur2 ="";

	  public  void testExcute() {
	    //nodes = new ArrayList<Vertex>();
	    //nodes = getEdge();
/*	    Vertex location = new Vertex("0", "EUR" );
	      nodes.add(location);
	      location = new Vertex("1", "USD" );
	      nodes.add(location);	      
	      location = new Vertex("2", "GBP" );
	      nodes.add(location);
	      location = new Vertex("3", "NOK" );
	      nodes.add(location);
	      location = new Vertex("4", "CHF" );
	      nodes.add(location);
	      location = new Vertex("5", "SEK" );
	      nodes.add(location);
	      location = new Vertex("6", "JPY" );
	      nodes.add(location);
	      location = new Vertex("7", "MXN" );
	      nodes.add(location);	      
	      location = new Vertex("8", "BRL" );
	      nodes.add(location);	 	      
	      location = new Vertex("9", "CAD" );
	      nodes.add(location);*/
	      
	    //}

/*	    EURUSD,1.0621
	    GBPUSD,1.4928
	    NOKCHF,1.3701
	    SEKCHF,1.3373
	    USDJPY,121.19
	    USDMXN,15.376
	    BRLMXN,4.9344
	    JPYCAD,0.010461
	    NOKSEK,1.0281*/
	    
	    
/*	    addLane("EURUSD", 0, 1, 1.0621);
	    // backward
	    addLane("USDEUR", 1, 0, 1/1.0621);
	    
	    addLane("GBPUSD", 2, 1, 1.4928);
	    // backward
	    addLane("USDGBP", 1, 2, 1/1.4928);
	    
	    addLane("NOKCHF", 3, 4, 1.3701);
	    // backward
	    addLane("CHFNOK", 4, 3, 1/1.3701);
	    
	    addLane("SEKCHF", 5, 4, 1.3373);
	    // backward
	    addLane("CHFSEK", 4, 5, 1/1.3373);
	    
	    addLane("USDJPY", 1, 6, 121.19);
	    // backward
	    addLane("JPYUSD", 6, 1, 1/121.19);
	    
	    addLane("USDMXN", 1, 7, 15.376);
	    // backward
	    addLane("MXNUSD", 7, 1, 1/15.376);
	    
	    addLane("BRLMXN", 8, 7, 4.9344);
	    // backward
	    addLane("BRLMXN", 7, 8, 1/4.9344);
	    
	    addLane("JPYCAD", 6, 9, 0.010461);
	    // backward
	    addLane("CADJPY", 9, 6, 1/0.010461);
	    
	    addLane("NOKSEK", 3, 5, 1.0281);
	    // backward
	    addLane("SEKNOK", 5, 3, 1/1.0281);*/
		  
		  
		  buidGraphFromFile();

	    // Lets check from location Loc_1 to Loc_10
	    Graph graph = new Graph(nodes, new ArrayList<Edge>(edges.values()));
	    DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
	    dijkstra.execute(nodes.get(6));
	    LinkedList<Vertex> path = dijkstra.getPath(nodes.get(1));
	    
	    String predecessor;
	    double allWeight=1;
	    
	    for (Vertex vertex : path) {
	      System.out.println("Node:"+vertex);
	    }
	    
	    
	    for( int j=0; j< path.size(); j++){
	    	
	    	if(j+1<path.size()){
	    		allWeight *= getLane(path.get(j).getName(), path.get(j+1).getName()).getRate();
	    		 System.out.println("weight:"+getLane(path.get(j).getName(), path.get(j+1).getName()).getRate());
	    	}
	    }
	    
	    BigDecimal bd = new BigDecimal(allWeight).setScale(4, RoundingMode.HALF_EVEN);
	    System.out.println("All weight:"+bd);
	    
	  }
	  
/*	  1. Trader asks for EURUSD, return 1.0621
			  2. Trader asks for USDAUD, return “Not available”
			  3. Trader asks for JPYUSD, return 0.0083
			  4. Trader asks for EURJPY, return 128.7159
			  5. Trader asks for EURCAD, return 1.3465
			  6. Trader asks for BRLUSD, return 0.3209
			  7. Trader asks for NOKSEK, return 1.0281
			  8. Trader asks for SEKNOK, return 0.9761*/

	  private static void addLane(String laneId, int sourceLocNo, int destLocNo,
	      double duration) {
	    Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration);
	    edges.put(laneId, lane);
	  }
	  
	  private Edge getLane(String source, String destination){
		 return (Edge) edges.get(source+destination) ; 
	  }
	  
	  public static void main(String []args){
		  Test t = new Test();
		  t.testExcute();
	  }
	  
	  public List<Vertex> getEdge(){
		  TreeSet<String> ts = new TreeSet<String>();
		  List<Vertex> nodes = new ArrayList<Vertex>();
		  
		  Map<String, Double> fileNodes =  Tools.readFile("input.csv");
		  String e;
		  String  CUR1 ;
		  String CUR2;
		  int i =0;
		   for( String key: fileNodes.keySet()){
			   e = key;
			   System.out.println(e);
			   CUR1 = e.substring(0,3);
			   CUR2 = e.substring(3,6);
			   if(!ts.contains(CUR1)){
				   ts.add(CUR1);
				    Vertex location = new Vertex(""+(i++), CUR1 );
				     nodes.add(location);
				     System.out.println(location);
			   }
			   
			   if(!ts.contains(CUR2)){
				   ts.add(CUR2);
				    Vertex location = new Vertex(""+(i++), CUR2 );
				     nodes.add(location);
				     System.out.println(location);
			   }			   
		   }
		   
		  return nodes;
	  }
	  
	  public static void   buidGraphFromFile(){
		  TreeSet<String> ts = new TreeSet<String>();
		  Map<String, Integer> edgeMapping = new LinkedHashMap<String, Integer>();
		  
		  Map<String, Double> fileNodes =  Tools.readFile("input.csv");
		  String e;
		  String  CUR1 ;
		  String CUR2;
		  int i =0;
		  int j=0;
		   for( String key: fileNodes.keySet()){
			   e = key;
			   System.out.println(e);
			   
			   
			   CUR1 = e.substring(0,3);
			   CUR2 = e.substring(3,6);
			   if(!ts.contains(CUR1)){
				   ts.add(CUR1);
				   j=i++;
				    Vertex location = new Vertex(""+j, CUR1 );
				    edgeMapping.put(CUR1,j);
				     nodes.add(location);
				     System.out.println(location);
			   }
			   
			   if(!ts.contains(CUR2)){
				   ts.add(CUR2);
				   j=i++;
				    Vertex location = new Vertex(""+j, CUR2 );
				    edgeMapping.put(CUR2,j);
				     nodes.add(location);
				     System.out.println(location);
			   }	
			   
			   addLane(CUR1+CUR2, edgeMapping.get(CUR1),  edgeMapping.get(CUR2), fileNodes.get(e));
			   addLane(CUR2+CUR1, edgeMapping.get(CUR2),  edgeMapping.get(CUR1), 1/fileNodes.get(e));
		   }
		   
	  }
} 