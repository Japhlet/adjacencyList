package adajacencyList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


public class adjacencylist {

	public static void main(String[] args) throws IOException {
		Scanner Keyboard= new Scanner(System.in);//Starts Keyboard for user input
		System.out.println("Enter a database filename:");//inputfile
		String fname=Keyboard.nextLine();
		Map<String,List<Path>> adj_list=readPaths(fname);// get adjacency list
		displayAdjacencyList(adj_list);//dispay it
		System.out.println("Which city do you choose?");
		String start=Keyboard.nextLine();
		while (!start.isEmpty()){
			if(adj_list.containsKey(start)){
		Map<String,Double> shortestdistances=findDistances(start,adj_list);//find distances
		displayShortest(start,shortestdistances);//print out distance map
		}
			else{
				System.out.println("Choose a valid City!");
				
			}
			System.out.println("Which city do you choose?");
			start=Keyboard.nextLine();
			}
	}
		
	public static Map< String, List<Path> > readPaths(String fname){
			Map<String,List<Path>> adj_list= new HashMap<String,List<Path>>();
			try {
				Scanner inFile= new Scanner (new File(fname));
				while (inFile.hasNextLine()){
					String input=inFile.nextLine();
					String[] tokens = input.split(",");
					String key=tokens[0];
					if(adj_list.containsKey(key)){
						Path path=new Path(tokens[1],Double.parseDouble(tokens[2]));
						List<Path> pathList=adj_list.get(key);
						pathList.add(path);
					}
				else
				{
					Path path=new Path(tokens[1],Double.parseDouble(tokens[2]));
					List<Path> pathList=new LinkedList<Path>();
					pathList.add(path);
					adj_list.put(key,pathList);
				}
		
			}
			Set<String> keys=adj_list.keySet();
			List<String> keysa=new LinkedList<String>();
			keysa.addAll(keys);
			
			

			int i=0;
			while(i<keysa.size()){
				if(adj_list.containsKey(keysa.get(i))){
	                List<Path> pathList=adj_list.get(keysa.get(i));
	              int j=0;
	                while(j<pathList.size()){
	            	   Path path =new Path(keysa.get(i),pathList.get(j).getCost());
	            	   String endpoint=(pathList.get(j).getEndpoint());
	            	   if (adj_list.containsKey(endpoint)){
	            		   List<Path> altpathList=adj_list.get(endpoint);
	            		   altpathList.add(path);
	            		    }
	            	   else{
	            		   List<Path> altpathList=new LinkedList<Path>();
	            		   altpathList.add(path);
	            		   adj_list.put(endpoint,altpathList);
	            		   
	            	   }
	            	   j++;}
	                i++;
	                
				}
				}
					}
			
			
			
		 catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adj_list;
		
	}
	 public static void displayAdjacencyList(Map< String,List<Path> > map){
		 Set<String> keys=map.keySet();
			List<String> keysa=new LinkedList<String>();
			keysa.addAll(keys);
			int i=0;
			
			while(i<map.size()){
		    String key=keysa.get(i);
			List <Path> pathList= map.get(key);
			int j=0;
			System.out.print(key);
				while (j<pathList.size()){
					Path path=pathList.get(j);
					System.out.print("("+path.getEndpoint()+" "+path.getCost()+"),");
					j++;
				}
				System.out.println("\n");
						
			i++;}
	 
	 }

	 public static Map<String, Double> findDistances(String start, Map<String,List<Path>> adj_list){
		 Map<String,Double> shortestdistances=new HashMap<String,Double>();
		 PriorityQueue<Path> queue=new PriorityQueue<Path>();
		 queue.add(new Path(start,0.0));
		 while (!queue.isEmpty()){
			 Path current=queue.poll();
			 if (!shortestdistances.containsKey(current.getEndpoint())){
				 double d=current.getCost();
				 String dest=current.getEndpoint();
				 shortestdistances.put(dest,d);
				 for(Path n:adj_list.get(dest)){
					 queue.add(new Path(n.getEndpoint(),d+n.getCost()));
	}
	}
}

		 	return shortestdistances;		 
		 
	 }
	 public static void displayShortest(String start, Map<String, Double> shortest){
		System.out.println("Distances from" + start +" to each city");
		System.out.println("Dest City	Distance");
		System.out.println("---------	----------");
		Set<String> cityList=shortest.keySet();
		List<String> shortestList=new ArrayList<String>();
		shortestList.addAll(cityList);
		int i=0;
		while(i<shortestList.size()){
			System.out.format("%-15.15s %-5.2f \n",shortestList.get(i),shortest.get(shortestList.get(i)));
			i++;}
		 
	 }
	 

}
