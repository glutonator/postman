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
        //todo: dla przystej liczby wierchołków od 8 w zwyż jest jakiś błað i czasem generuje się nie graf eulerowski
        //todo: dla nieprzystej liczby jest okey chyba zawsze
        Postman postman = new Postman(CustomGraph.createComleteGraphUndireted(10),100);
//        Postman postman = new Postman(CustomGraph.createGraphForCyclicity(111,77),100);
//        Postman postman = new Postman(CustomGraph.createGraphForDensity(17,0.5),100);
//        Postman postman = new Postman(SimpleTests.createDirectedGraphFromBookMultigraphCustomEdge());
        postman.auxiliaryAlg();
        postman.alg();

    }
}
