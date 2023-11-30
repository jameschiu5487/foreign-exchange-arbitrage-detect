package finalproject;

import java.net.URL;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import org.json.JSONObject;

public class arbitrage {

    public static int nov;
    public static double dist[];
    public static int parent[];

    static //初始化最短距離表
    {
        nov = 5;
        dist = new double[nov];
        Arrays.fill(dist, Integer.MAX_VALUE);
        parent = new int[nov];
        Arrays.fill(parent, -1);
    }

    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<WeightVertex>> graph = new ArrayList<>();
        
        HashMap<Integer, String> currencyNameMap = new HashMap<>();
        currencyNameMap.put(0, "INR");
        currencyNameMap.put(1, "USD");
        currencyNameMap.put(2, "EUR");
        currencyNameMap.put(3, "GBP");
        currencyNameMap.put(4, "JPY");

        for (int i = 0;i<nov;i++){
            graph.add(i, new ArrayList<WeightVertex>());
        }
        
        double arr[][]= new double [nov][nov];
        arr=datasource.getdata(nov);
        for(int i=0;i<nov;i++)
        {
        	System.out.print(currencyNameMap.get(i)+" ");
        	for(int j=0;j<nov;j++)
        	{
        		System.out.print(arr[i][j]+" ");
        	}
        	System.out.println("");
        }

        for (int i = 0;i<nov;i++) //算出vertex各邊的權重並放入ＡrrayＬist
        {
            for (int j = 0;j<nov;j++)
            {
            	//因為匯兌是乘積的概念，所以取log變成加法
                double weight = -Math.log(arr[i][j]); 
                graph.get(i).add(new WeightVertex(j, weight));
            }
        }

        ArrayList<List<Integer>> cycles = bellmanFord(graph);

        if (cycles.size()==0)
        {
            System.out.println("No arbitrage exists...");
        }
        else
        {
        	int i=0;
        	for(i=0;i<cycles.size();i++)
        	{
        		List<Integer> cycle=cycles.get(i);
        		double profit = 1d;
                int source = cycle.get(0);
                int dest = 0;
                String path = currencyNameMap.get(source);
                for(int j=1;j<cycle.size();j++)
                {
                    dest = cycle.get(j);
                    profit *= arr[source][dest];
                    source = dest;
                    path += "->" + currencyNameMap.get(source);
                }
                System.out.println("Profit in the arbitrage(" + path + "): " + String.format("%.4f",(profit-1)*100) + "%.");
                
        	}
        }
        	
        }

    

    private static ArrayList<List<Integer>> bellmanFord(ArrayList<ArrayList<WeightVertex>> graph) 
    {
        dist[0] = 0;
        
        //完成最短距離表
        for (int i = 0;i<nov-1;i++)
        {
            for (int u = 0;u<nov;u++)
            {
                for (WeightVertex v: graph.get(u))
                { 
                    if (dist[u] + v.dist<dist[v.data]) //v.data為終點
                    {
                        dist[v.data] = dist[u] + v.dist;
                        parent[v.data] = u;
                    }
                }
            }
        }
        //找negative cycle
        HashSet<Integer> seenVertices = new HashSet<>();
        ArrayList<List<Integer>> cycles = new ArrayList<>();
        HashSet<Integer> output = new HashSet<>();
        for (int u = 0;u<nov;u++)
        {
            for (WeightVertex v: graph.get(u))
            {
                if(dist[u]+v.dist<dist[v.data] && !seenVertices.contains(v.data))
                {
                	ArrayList<Integer> new_cycle = new ArrayList<Integer>();
                	HashSet<Integer> visited = new HashSet<>();
                	int C=v.data;
                	int k=C;
                	do {
                		seenVertices.add(k);
                        visited.add(k);
                        new_cycle.add(k);
                        k = parent[k];
                    } while (k != C && !visited.contains(k));
                	int index = new_cycle.indexOf(k);
                	new_cycle.add(k);
                	if(!output.contains(k))
                	{
                		output.add(k);
                		List<Integer> final_cycle= new_cycle.subList(index, new_cycle.size());
                    	Collections.reverse(final_cycle);
                    	cycles.add(final_cycle);
                	}
                	
                } 
            }
        }
        return cycles;
    }

}

class WeightVertex{
    public int data;
    public double dist;

    public WeightVertex(int data, double dist){
        this.data = data;
        this.dist = dist;
    }
}
	
	
