package core;

import org.jgrapht.Graph;
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


        ShowGraph.printGraphEdges(simpleDirectedWeightedGraph);
        ShowGraph.printGraph(simpleDirectedWeightedGraph);
        return simpleDirectedWeightedGraph;
    }
}
