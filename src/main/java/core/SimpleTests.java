package core;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.WeightedMultigraph;

public class SimpleTests {

    public static Graph createDirectedGraphFromBookMultigraphCustomEdge() {
        WeightedMultigraph<String, CustomEdge> simpleDirectedWeightedGraph =
                new WeightedMultigraph<String, CustomEdge>(CustomEdge.class);
        simpleDirectedWeightedGraph.addVertex("v1");
        simpleDirectedWeightedGraph.addVertex("v2");
        simpleDirectedWeightedGraph.addVertex("v3");
        simpleDirectedWeightedGraph.addVertex("v4");
        simpleDirectedWeightedGraph.addVertex("s");
        simpleDirectedWeightedGraph.addVertex("t");

        String label = "temp";

        boolean edge = simpleDirectedWeightedGraph.addEdge("s", "v1",new CustomEdge(label,1.0));
        boolean edge2 = simpleDirectedWeightedGraph.addEdge("s", "v2",new CustomEdge(label,9.0));
        boolean edge3 = simpleDirectedWeightedGraph.addEdge("v1", "v3",new CustomEdge(label,9.0));
        boolean edge4 = simpleDirectedWeightedGraph.addEdge("v2", "v4",new CustomEdge(label,1.0));
        boolean edge5 = simpleDirectedWeightedGraph.addEdge("v1", "v2",new CustomEdge(label,1.0));
        boolean edge6 = simpleDirectedWeightedGraph.addEdge("v4", "v3",new CustomEdge(label,1.0));
        boolean edge7 = simpleDirectedWeightedGraph.addEdge("v3", "t",new CustomEdge(label,1.0));
        boolean edge8 = simpleDirectedWeightedGraph.addEdge("v4", "t",new CustomEdge(label,9.0));

//        CustomEdge edge888 = simpleDirectedWeightedGraph.addEdge("v4", "t");
//        CustomEdge edge777 = simpleDirectedWeightedGraph.addEdge("v3", "t");

//
//        simpleDirectedWeightedGraph.setEdgeWeight("s", "v1",1);
//        simpleDirectedWeightedGraph.setEdgeWeight("s", "v2",9);
//        simpleDirectedWeightedGraph.setEdgeWeight("v1", "v3",9);
//        simpleDirectedWeightedGraph.setEdgeWeight("v2", "v4",1);
//        simpleDirectedWeightedGraph.setEdgeWeight("v1", "v2",1);
//        simpleDirectedWeightedGraph.setEdgeWeight("v4", "v3",1);
//        simpleDirectedWeightedGraph.setEdgeWeight("v3", "t",1);
//        simpleDirectedWeightedGraph.setEdgeWeight("v4", "t",9);


//        simpleDirectedWeightedGraph.setEdgeWeight();
//        simpleDirectedWeightedGraph.setEdgeWeight(edge, 1);
//        simpleDirectedWeightedGraph.setEdgeWeight(edge2, 9);
//        simpleDirectedWeightedGraph.setEdgeWeight(edge3, 9);
//        simpleDirectedWeightedGraph.setEdgeWeight(edge4, 1);
//        simpleDirectedWeightedGraph.setEdgeWeight(edge5, 1);
//        simpleDirectedWeightedGraph.setEdgeWeight(edge6, 1);
//        simpleDirectedWeightedGraph.setEdgeWeight(edge7, 1);
//        simpleDirectedWeightedGraph.setEdgeWeight(edge8, 9);



//        simpleDirectedWeightedGraph.setEdgeWeight(edge888, 888);
//        simpleDirectedWeightedGraph.setEdgeWeight(edge777, 1);


        ShowGraph.printGraphEdges(simpleDirectedWeightedGraph);
//        givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(simpleDirectedWeightedGraph);
        ShowGraph.printGraph(simpleDirectedWeightedGraph);
        return simpleDirectedWeightedGraph;
    }
}
