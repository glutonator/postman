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
    public static void main(String... args) throws IOException {
//        CustomGraphOld customGraph = new CustomGraphOld();
////        customGraph.createGraphSimpleGraph();
////        customGraph.createDirectedGraph();
//        customGraph.createDirectedGraphComplete();
//        Postman postman = new Postman();
//        postman.func222();
        Postman postman = new Postman(4);
        postman.auxiliaryAlg();
        postman.alg();

    }
}
