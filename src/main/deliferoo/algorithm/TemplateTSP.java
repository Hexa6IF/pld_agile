package algorithm;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class TemplateTSP implements TSP {
	
	private Integer[] bestSolution;
	private int bestSolutionCost = 0;
	private Boolean timeLimitReached;
	
	public Boolean getTimeLimitReached(){
		return timeLimitReached;
	}
	
	public void searchSolution(int timeLimit, int nbNodes, int[][] cost, int[] duration){
		timeLimitReached = false;
		bestSolutionCost = Integer.MAX_VALUE;
		bestSolution = new Integer[nbNodes];
		ArrayList<Integer> undiscovered = new ArrayList<Integer>();
		for (int i=1; i<nbNodes; i++) undiscovered.add(i);
		ArrayList<Integer> discovered = new ArrayList<Integer>(nbNodes);
		discovered.add(0); // le premier sommet visite est 0
		branchAndBound(0, undiscovered, discovered, 0, cost, duration, System.currentTimeMillis(), timeLimit);
	}
	
	public Integer getBestSolution(int i){
		if ((bestSolution == null) || (i<0) || (i>=bestSolution.length))
			return null;
		return bestSolution[i];
	}
	
	public int getBestSolutionCost(){
		return bestSolutionCost;
	}
	
	/**
	 * Methode devant etre redefinie par les sous-classes de TemplateTSP
	 * @param currentNode
	 * @param undiscovered : tableau des sommets restant a visiter
	 * @param cost : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duration : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 * @return une borne inferieure du cout des permutations commencant par sommetCourant, 
	 * contenant chaque sommet de nonVus exactement une fois et terminant par le sommet 0
	 */
	protected abstract int bound(Integer currentNode, ArrayList<Integer> undiscovered, int[][] cost, int[] duration);
	
	/**
	 * Methode devant etre redefinie par les sous-classes de TemplateTSP
	 * @param currentNode
	 * @param undiscovered : tableau des sommets restant a visiter
	 * @param cost : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duration : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 * @return un iterateur permettant d'iterer sur tous les sommets de nonVus
	 */
	protected abstract Iterator<Integer> iterator(Integer currentNode, ArrayList<Integer> undiscovered, int[][] cost, int[] duration);
	
	/**
	 * Methode definissant le patron (template) d'une resolution par separation et evaluation (branch and bound) du TSP
	 * @param currentNode le dernier sommet visite
	 * @param undiscovered la liste des sommets qui n'ont pas encore ete visites
	 * @param discovered la liste des sommets visites (y compris sommetCrt)
	 * @param discoveredCost la somme des couts des arcs du chemin passant par tous les sommets de vus + la somme des duree des sommets de vus
	 * @param cost : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duration : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 * @param startTime : moment ou la resolution a commence
	 * @param timeLimit : limite de temps pour la resolution
	 */	
	 void branchAndBound(int currentNode, ArrayList<Integer> undiscovered, ArrayList<Integer> discovered, int discoveredCost, int[][] cost, int[] duration, long startTime, int timeLimit){
		 if (System.currentTimeMillis() - startTime > timeLimit){
			 timeLimitReached = true;
			 return;
		 }
	    if (undiscovered.size() == 0){ // tous les sommets ont ete visites
	    	discoveredCost += cost[currentNode][0];
	    	if (discoveredCost < bestSolutionCost){ // on a trouve une solution meilleure que bestSolution
	    		discovered.toArray(bestSolution);
	    		bestSolutionCost = discoveredCost;
	    	}
	    } else if (discoveredCost + bound(currentNode, undiscovered, cost, duration) < bestSolutionCost){
	        Iterator<Integer> it = iterator(currentNode, undiscovered, cost, duration);
	        while (it.hasNext()){
	        	Integer prochainSommet = it.next();
	        	discovered.add(prochainSommet);
	        	undiscovered.remove(prochainSommet);
	        	branchAndBound(prochainSommet, undiscovered, discovered, discoveredCost + cost[currentNode][prochainSommet] + duration[prochainSommet], cost, duration, startTime, timeLimit);
	        	discovered.remove(prochainSommet);
	        	undiscovered.add(prochainSommet);
	        }	    
	    }
	}
}

