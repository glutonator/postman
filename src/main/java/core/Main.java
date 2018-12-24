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
        CustomGraph customGraph = new CustomGraph();
//        customGraph.createGraphSimpleGraph();
//        customGraph.createDirectedGraph();
        customGraph.createDirectedGraphComplete();
    }
}
