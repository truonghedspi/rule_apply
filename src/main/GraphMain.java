package main;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import rule.Rule;
import dgraph.Graph;
import dgraph.Vertex;



public class GraphMain {
	
	static String pos[] = {"V","N"};
	static String pos1[] = {"N", "V"};
	static String align[] = {"1", "0"};
	public static void main(String[] args) {
			Graph g = new Graph();
			
			g.initGraph();
			
			g.createMonoTone("Toi/N thich/V ban/N");
			Rule rule = new Rule(pos,align);
			Rule rule2 =  new Rule(pos1,align);
			g.applyRule(rule);
			g.applyRule(rule2);
			g.postProcess();
			
			System.out.println(g.toPlf());
			
	}
	
}
