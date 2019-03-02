package core;

import org.jgrapht.Graph;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.Iterator;

public class ShowGraph {
    public static void printGraph(Graph graph) {
        // Print out the graph to be sure it's really complete
        System.out.println("****************************************************");
        Iterator<String> iter = new DepthFirstIterator<>(graph);
        while (iter.hasNext()) {
            String vertex = iter.next();
            System.out.println(
                    "Wierzchołek " + vertex + " jest połączony do: "
                            + graph.edgesOf(vertex).toString());
        }
    }

    public static void printGraphEdges(Graph graph) {
        System.out.println("****************************************************");
        System.out.println("---Liczba wierzchołków: " + graph.vertexSet().size());
        System.out.println("Wierzchołki: "+String.join(",", graph.vertexSet()));
        System.out.println("---Liczba krawędzi: " + graph.edgeSet().size());
        System.out.println("Krawędzie:");
        for (Object e : graph.edgeSet()) {
            System.out.println(e.toString() + " --- " + graph.getEdgeWeight(e));
        }
    }
}
