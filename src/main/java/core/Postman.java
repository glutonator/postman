package core;

import javafx.util.Pair;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Postman {
    private Graph<String, CustomEdge> graph;
//    private Map<Integer, Pair<Double, Double>> weightsMap;


    public Postman(int size) throws IOException {
        CustomGraph customGraph = new CustomGraph();
        this.graph = customGraph.createComleteGraphUndireted(size);
//        this.weightsMap = new HashMap<>(size);
//        populateWeightsMap(size);
        System.out.println(toString());
        customGraph.givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(graph);
    }


//    public void populateWeightsMap(int size) {
//        for (int i = 0; i < size; i++) {
//            this.weightsMap.put(i, new Pair<>(i + 0.55, i + 0.88));
//        }
//    }
//
//    // [c_ij , c_ji]
//    public Pair<Double, Double> getPairOfWeighsFromEdge(int edgeIndex) {
//        return this.weightsMap.get(edgeIndex);
//    }
//
//    // c_ij
//    public Double getFirstWeighFromPair(Pair<Double, Double> pair) {
//        return pair.getKey();
//    }
//
//    // c_ji
//    public Double getSecondWeighFromPair(Pair<Double, Double> pair) {
//        return pair.getValue();
//    }
//
//    public boolean checkWhichWeightIsBigger(int edgeIndex) {
//        Pair<Double, Double> pair = getPairOfWeighsFromEdge(edgeIndex);
//        if (getFirstWeighFromPair(pair) <= getSecondWeighFromPair(pair)) {
//            // c_ij <= c_ji
//            return true;
//        } else {
//            // c_ij > c_ji
//            return false;
//        }
//    }

    public void alg() {
//        Graph<String, DefaultWeightedEdge> graphD_G;
//        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> simpleDirectedWeightedGraph =
//                new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

        Graph<String, DefaultWeightedEdge> graphD_G =
                new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

        //populate nodes
        for(String vertex : this.graph.vertexSet()) {
            graphD_G.addVertex(vertex);
        }

        //populate edges
        for (CustomEdge edge : this.graph.edgeSet()) {
           if (edge.checkWhichWeightIsBigger()) {
               DefaultWeightedEdge edgeNew = graphD_G.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge));
               graphD_G.setEdgeWeight(edgeNew,edge.getWeight1());
           }
           else {
               DefaultWeightedEdge edgeNew = graphD_G.addEdge(graph.getEdgeTarget(edge),graph.getEdgeSource(edge));
               graphD_G.setEdgeWeight(edgeNew,edge.getWeight2());

           }
           //TODO: Trzeba by rozkminić  tą funkcję do przepływów bo bez nie jani rusz :)
        }

    }


    @Override
    public String toString() {
        return "Postman{" +
                "graph=" + graph +
//                ", weightsMap=" + weightsMap +
                '}';
    }
}
