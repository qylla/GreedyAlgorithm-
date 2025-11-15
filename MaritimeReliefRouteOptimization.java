package GreedyDivide;

import java.util.*;

public class MaritimeReliefRouteOptimization {

	static int[][] distanceMatrix = {
	        {0, 15, 25, 35},
	        {15, 0, 30, 28},
	        {25, 30, 0, 20},
	        {35, 28, 20, 0}
	    };
	    static String[] locations = {"Port A", "Port B", "Relief Center C", "Relief Center D"};

	    public static String greedyTSP(int[][] dist) {
	        int n = dist.length;
	        boolean[] visited = new boolean[n];
	        int current = 0;
	        visited[current] = true;
	        int total = 0;
	        StringBuilder route = new StringBuilder("Greedy TSP Route: ").append(locations[current]);
	        for (int step = 0; step < n - 1; step++) {
	            int next = -1, best = Integer.MAX_VALUE;
	            for (int j = 0; j < n; j++) {
	                if (!visited[j] && dist[current][j] < best) {
	                    best = dist[current][j];
	                    next = j;
	                }
	            }
	            visited[next] = true;
	            total += best;
	            route.append(" -> ").append(locations[next]);
	            current = next;
	        }
	        total += dist[current][0];
	        route.append(" -> ").append(locations[0])
	             .append(" | Total Distance: ").append(total).append(" nm");
	        return route.toString();
	    }
	    private static class DCResult {
	        int cost; List<Integer> path;
	        DCResult(int c, List<Integer> p) { cost = c; path = p; }
	    }

	    public static void main(String[] args) {
	        System.out.println(greedyTSP(distanceMatrix));
	    }
	}