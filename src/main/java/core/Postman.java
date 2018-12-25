package core;

import javafx.util.Pair;
import org.jgrapht.Graph;
import org.jgrapht.alg.flow.mincost.CapacityScalingMinimumCostFlow;
import org.jgrapht.alg.flow.mincost.MinimumCostFlowProblem;
import org.jgrapht.alg.interfaces.MinimumCostFlowAlgorithm;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Postman {
    private Graph<String, CustomEdge> graph;
//    private Map<Integer, Pair<Double, Double>> weightsMap;


    public Postman() {
    }

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
        for (String vertex : this.graph.vertexSet()) {
            graphD_G.addVertex(vertex);
        }

        //populate edges
        for (CustomEdge edge : this.graph.edgeSet()) {
            if (edge.checkWhichWeightIsBigger()) {
                DefaultWeightedEdge edgeNew = graphD_G.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge));
                graphD_G.setEdgeWeight(edgeNew, edge.getWeight1());
            } else {
                DefaultWeightedEdge edgeNew = graphD_G.addEdge(graph.getEdgeTarget(edge), graph.getEdgeSource(edge));
                graphD_G.setEdgeWeight(edgeNew, edge.getWeight2());

            }
            //TODO: Trzeba by rozkminić  tą funkcję do przepływów bo bez nie jani rusz :)
        }

    }

    public void func()  {
        final int qwerty = 0;
        CustomGraph customGraph = new CustomGraph();
        Graph graph = customGraph.createDirectedGraph();
        Function<String, Integer> nodesFunction =x->{
            if(x.equals("v1")) {
                System.out.println(x);
                System.out.println("aaaaaaaaaa");
                return -5;
            }
            else if(x.equals("v2")) {
                System.out.println(x);
                System.out.println("bbbbbbbbbb");
                return 5;
            }
            else {
                System.out.println(x);
                System.out.println("ccccccccccc");
                return 0;
            }

        };
        nodesFunction.apply("(v1,v3)");
        Function<DefaultWeightedEdge, Integer> edgesFunction = x -> 10;
//        MinimumCostFlowProblem.MinimumCostFlowProblemImpl problem =
        MinimumCostFlowProblem problem =
                new MinimumCostFlowProblem.MinimumCostFlowProblemImpl(graph, nodesFunction, edgesFunction);
        System.out.println("**************************");
        System.out.println(problem.getGraph());
        System.out.println(problem.getNodeSupply());
        System.out.println(problem.getArcCapacityLowerBounds());
        System.out.println(problem.getArcCapacityUpperBounds());

//        MinimumCostFlowAlgorithm.MinimumCostFlowImpl minimumCostFlowAlgorithm = new MinimumCostFlowAlgorithm.MinimumCostFlowImpl()
//        CapacityScalingMinimumCostFlow()
        Map<DefaultWeightedEdge,Double> flowMap = new HashMap<>();
        //todo: tutaj jest coś bradzo nie tak, może by użyć tej drugiej funckji CapacityScalingMinimumCostFlow
//        MinimumCostFlowAlgorithm rrrr= new MinimumCostFlowAlgorithm.MinimumCostFlowImpl(5.0,flowMap);
//        rrrr.getMinimumCostFlow()


//        MinimumCostFlowAlgorithm.MinimumCostFlow

//        CapacityScalingMinimumCostFlow.
        //to jest dużo lepsze podejście bo coś dizała
        CapacityScalingMinimumCostFlow costFlow = new CapacityScalingMinimumCostFlow();
//        costFlow.getFlowMap();
        MinimumCostFlowAlgorithm.MinimumCostFlow<DefaultWeightedEdge> qeqwe = costFlow.getMinimumCostFlow(problem);

        System.out.println("**************************");

    }


    @Override
    public String toString() {
        return "Postman{" +
                "graph=" + graph +
//                ", weightsMap=" + weightsMap +
                '}';
    }
}
