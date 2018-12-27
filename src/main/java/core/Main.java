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
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Supplier;

public class Main {
    public static void main(String... args) throws Exception {
        Postman postman = new Postman(CustomGraph.createComleteGraphUndireted(4),100);
//        Postman postman = new Postman(SimpleTests.createDirectedGraphFromBookMultigraphCustomEdge());
        postman.auxiliaryAlg();
        postman.alg();

    }
}
