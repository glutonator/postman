package core;

import org.jgrapht.Graph;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.util.SupplierUtil;

import java.io.IOException;
import java.util.Iterator;
import java.util.function.Supplier;

public class Main {
//todo: coś jest nie tak jak mzianiam się na graf z wagami
    public static void main(String... args) throws IOException {
        System.out.println("ppp");

        Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        g.addVertex("v1");
        g.addVertex("v2");
        g.addVertex("v3");

        g.addEdge("v1", "v2");
        g.addEdge("v2", "v3");

//        SimpleDirectedGraph simpleDirectedGraph = new SimpleDirectedGraph(DefaultEdge.class);
        int SIZE = 4;
        Supplier<String> vSupplier = new Supplier<String>() {
            private int id = 0;
            @Override
            public String get() {
                return "v" + id++;
            }
        };
        Graph<String, DefaultEdge> completeGraph =
                new SimpleGraph<>(vSupplier, SupplierUtil.createDefaultEdgeSupplier(),false);
//        Graph<String, DefaultEdge> completeGraph =
//                new SimpleWeightedGraph<>(vSupplier, SupplierUtil.createDefaultEdgeSupplier());
        // Create the CompleteGraphGenerator object
        CompleteGraphGenerator<String, DefaultEdge> completeGenerator =
                new CompleteGraphGenerator<>(SIZE);

        // Use the CompleteGraphGenerator object to make completeGraph a
        // complete graph with [size] number of vertices
        completeGenerator.generateGraph(completeGraph);


        CustomGraph customGraph = new CustomGraph();
//        customGraph.givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(g);
        customGraph.givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(completeGraph);


        // Print out the graph to be sure it's really complete
        Iterator<String> iter = new DepthFirstIterator<>(completeGraph);
        while (iter.hasNext()) {
            String vertex = iter.next();
            System.out.println(
                    "Vertex " + vertex + " is connected to: "
                            + completeGraph.edgesOf(vertex).toString());
        }

        DefaultEdge edge = completeGraph.getEdge("v0", "v1");
        System.out.println("sdadsa");
    }
}
