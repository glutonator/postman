package core;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import com.sun.javafx.geom.Edge;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.util.SupplierUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Supplier;

public class CustomGraphOld {

    private Integer TMP = 0;

    public void createGraphSimpleGraph() throws IOException {
//        System.out.println("ppp");
//
//        Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
//        g.addVertex("v1");
//        g.addVertex("v2");
//        g.addVertex("v3");

//        g.addEdge("v1", "v2");
//        g.addEdge("v2", "v3");

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
                new SimpleGraph<>(vSupplier, SupplierUtil.createDefaultEdgeSupplier(), false);
//        Graph<String, DefaultEdge> completeGraph =
//                new SimpleWeightedGraph<>(vSupplier, SupplierUtil.createDefaultEdgeSupplier());
        // Create the CompleteGraphGenerator object
        CompleteGraphGenerator<String, DefaultEdge> completeGenerator =
                new CompleteGraphGenerator<>(SIZE);

        // Use the CompleteGraphGenerator object to make completeGraph a
        // complete graph with [size] number of vertices
        completeGenerator.generateGraph(completeGraph);


//        CustomGraphOld customGraph = new CustomGraphOld();
//        customGraph.givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(g);
//        givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(completeGraph);

        ShowGraph.printGraph(completeGraph);

        DefaultEdge edge = completeGraph.getEdge("v0", "v1");
        System.out.println("sdadsa");
        ShowGraph.printGraphEdges(completeGraph);

    }

//    public Graph createComleteGraphUndireted(int size) {
////        int SIZE = 4;
//        int SIZE = size;
//
//        Supplier<String> vSupplier = new Supplier<String>() {
//            private int id = 0;
//
//            @Override
//            public String get() {
//                return "v" + id++;
//            }
//        };
//
//        Supplier<CustomEdge> edgeSupplier = new Supplier<CustomEdge>() {
//            private int id = TMP;
//            //TODO: zrobić randa dla wartości wag, trzeba też uwzględnić stosuenk liczby dróg jednokiernkowych
//            @Override
//            public CustomEdge get() {
//                return new CustomEdge("e"+id++,10.0,100.0);
//            }
//        };
//
////        Graph<String, DefaultWeightedEdge> completeGraph =
////                new SimpleWeightedGraph<>(vSupplier, SupplierUtil.createDefaultWeightedEdgeSupplier());
////        Graph<String, CustomEdge> completeGraph =
////                new SimpleWeightedGraph<>(vSupplier, edgeSupplier);
//        Graph<String, CustomEdge> completeGraph =
//                new WeightedMultigraph<>(vSupplier, edgeSupplier);
//        // Create the CompleteGraphGenerator object
////        CompleteGraphGenerator<String, DefaultWeightedEdge> completeGenerator =
////                new CompleteGraphGenerator<>(SIZE);
//        CompleteGraphGenerator<String, CustomEdge> completeGenerator =
//                new CompleteGraphGenerator<>(SIZE);
//
//        // Use the CompleteGraphGenerator object to make completeGraph a
//        // complete graph with [size] number of vertices
//        completeGenerator.generateGraph(completeGraph);
//
//
//        ShowGraph.printGraph(completeGraph);
//        ShowGraph.printGraphEdges(completeGraph);
//        return completeGraph;
//    }

    public Graph createDirectedGraph() {
        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> simpleDirectedWeightedGraph =
                new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        simpleDirectedWeightedGraph.addVertex("v1");
        simpleDirectedWeightedGraph.addVertex("v2");
        simpleDirectedWeightedGraph.addVertex("v3");
        DefaultWeightedEdge edge = simpleDirectedWeightedGraph.addEdge("v1", "v2");
        DefaultWeightedEdge edge2 = simpleDirectedWeightedGraph.addEdge("v2", "v1");
        simpleDirectedWeightedGraph.addEdge("v1", "v3");
        simpleDirectedWeightedGraph.addEdge("v3", "v1");
        simpleDirectedWeightedGraph.addEdge("v2", "v3");
        simpleDirectedWeightedGraph.addEdge("v3", "v2");

        simpleDirectedWeightedGraph.setEdgeWeight(edge, 45);
//        System.out.println(simpleDirectedWeightedGraph.toString());
//        System.out.println(simpleDirectedWeightedGraph.getEdgeWeight(edge));
//        System.out.println(simpleDirectedWeightedGraph.getEdgeWeight(edge2));

        ShowGraph.printGraphEdges(simpleDirectedWeightedGraph);
//        givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(simpleDirectedWeightedGraph);
        ShowGraph.printGraph(simpleDirectedWeightedGraph);
        return simpleDirectedWeightedGraph;
    }

    public Graph createDirectedGraphFromBook() {
        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> simpleDirectedWeightedGraph =
                new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        simpleDirectedWeightedGraph.addVertex("v1");
        simpleDirectedWeightedGraph.addVertex("v2");
        simpleDirectedWeightedGraph.addVertex("v3");
        simpleDirectedWeightedGraph.addVertex("v4");
        simpleDirectedWeightedGraph.addVertex("s");
        simpleDirectedWeightedGraph.addVertex("t");

        DefaultWeightedEdge edge = simpleDirectedWeightedGraph.addEdge("s", "v1");
        DefaultWeightedEdge edge2 = simpleDirectedWeightedGraph.addEdge("s", "v2");
        DefaultWeightedEdge edge3 = simpleDirectedWeightedGraph.addEdge("v1", "v3");
        DefaultWeightedEdge edge4 = simpleDirectedWeightedGraph.addEdge("v2", "v4");
        DefaultWeightedEdge edge5 = simpleDirectedWeightedGraph.addEdge("v1", "v2");
        DefaultWeightedEdge edge6 = simpleDirectedWeightedGraph.addEdge("v4", "v3");
        DefaultWeightedEdge edge7 = simpleDirectedWeightedGraph.addEdge("v3", "t");
        DefaultWeightedEdge edge8 = simpleDirectedWeightedGraph.addEdge("v4", "t");

        simpleDirectedWeightedGraph.setEdgeWeight(edge, 1);
        simpleDirectedWeightedGraph.setEdgeWeight(edge2, 9);
        simpleDirectedWeightedGraph.setEdgeWeight(edge3, 9);
        simpleDirectedWeightedGraph.setEdgeWeight(edge4, 1);
        simpleDirectedWeightedGraph.setEdgeWeight(edge5, 1);
        simpleDirectedWeightedGraph.setEdgeWeight(edge6, 1);
        simpleDirectedWeightedGraph.setEdgeWeight(edge7, 1);
        simpleDirectedWeightedGraph.setEdgeWeight(edge8, 9);

        ShowGraph.printGraphEdges(simpleDirectedWeightedGraph);
//        givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(simpleDirectedWeightedGraph);
        ShowGraph.printGraph(simpleDirectedWeightedGraph);
        return simpleDirectedWeightedGraph;
    }

    public Graph createDirectedGraphFromBookMultigraph() {
        DirectedWeightedMultigraph<String, DefaultWeightedEdge> simpleDirectedWeightedGraph =
                new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        simpleDirectedWeightedGraph.addVertex("v1");
        simpleDirectedWeightedGraph.addVertex("v2");
        simpleDirectedWeightedGraph.addVertex("v3");
        simpleDirectedWeightedGraph.addVertex("v4");
        simpleDirectedWeightedGraph.addVertex("s");
        simpleDirectedWeightedGraph.addVertex("t");

        DefaultWeightedEdge edge = simpleDirectedWeightedGraph.addEdge("s", "v1");
        DefaultWeightedEdge edge2 = simpleDirectedWeightedGraph.addEdge("s", "v2");
        DefaultWeightedEdge edge3 = simpleDirectedWeightedGraph.addEdge("v1", "v3");
        DefaultWeightedEdge edge4 = simpleDirectedWeightedGraph.addEdge("v2", "v4");
        DefaultWeightedEdge edge5 = simpleDirectedWeightedGraph.addEdge("v1", "v2");
        DefaultWeightedEdge edge6 = simpleDirectedWeightedGraph.addEdge("v4", "v3");
        DefaultWeightedEdge edge7 = simpleDirectedWeightedGraph.addEdge("v3", "t");
        DefaultWeightedEdge edge8 = simpleDirectedWeightedGraph.addEdge("v4", "t");

        DefaultWeightedEdge edge888 = simpleDirectedWeightedGraph.addEdge("v4", "t");
        DefaultWeightedEdge edge777 = simpleDirectedWeightedGraph.addEdge("v3", "t");


        simpleDirectedWeightedGraph.setEdgeWeight(edge, 1);
        simpleDirectedWeightedGraph.setEdgeWeight(edge2, 9);
        simpleDirectedWeightedGraph.setEdgeWeight(edge3, 9);
        simpleDirectedWeightedGraph.setEdgeWeight(edge4, 1);
        simpleDirectedWeightedGraph.setEdgeWeight(edge5, 1);
        simpleDirectedWeightedGraph.setEdgeWeight(edge6, 1);
        simpleDirectedWeightedGraph.setEdgeWeight(edge7, 1);
        simpleDirectedWeightedGraph.setEdgeWeight(edge8, 9);



        simpleDirectedWeightedGraph.setEdgeWeight(edge888, 888);
        simpleDirectedWeightedGraph.setEdgeWeight(edge777, 1);


        ShowGraph.printGraphEdges(simpleDirectedWeightedGraph);
//        givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(simpleDirectedWeightedGraph);
        ShowGraph.printGraph(simpleDirectedWeightedGraph);
        return simpleDirectedWeightedGraph;
    }

    public Graph createDirectedGraphFromBookMultigraphCustomEdge() {
        DirectedWeightedMultigraph<String, CustomEdge> simpleDirectedWeightedGraph =
                new DirectedWeightedMultigraph<String, CustomEdge>(CustomEdge.class);
        simpleDirectedWeightedGraph.addVertex("v1");
        simpleDirectedWeightedGraph.addVertex("v2");
        simpleDirectedWeightedGraph.addVertex("v3");
        simpleDirectedWeightedGraph.addVertex("v4");
        simpleDirectedWeightedGraph.addVertex("s");
        simpleDirectedWeightedGraph.addVertex("t");

        CustomEdge customEdge = new CustomEdge("qqqq",12.0,59.0);

        boolean edge = simpleDirectedWeightedGraph.addEdge("s", "v1",new CustomEdge());
        boolean edge2 = simpleDirectedWeightedGraph.addEdge("s", "v2",new CustomEdge());
        boolean edge3 = simpleDirectedWeightedGraph.addEdge("v1", "v3",new CustomEdge());
        boolean edge4 = simpleDirectedWeightedGraph.addEdge("v2", "v4",new CustomEdge());
        boolean edge5 = simpleDirectedWeightedGraph.addEdge("v1", "v2",new CustomEdge());
        boolean edge6 = simpleDirectedWeightedGraph.addEdge("v4", "v3",new CustomEdge());
        boolean edge7 = simpleDirectedWeightedGraph.addEdge("v3", "t",new CustomEdge());
        boolean edge8 = simpleDirectedWeightedGraph.addEdge("v4", "t",new CustomEdge());

//        CustomEdge edge888 = simpleDirectedWeightedGraph.addEdge("v4", "t");
//        CustomEdge edge777 = simpleDirectedWeightedGraph.addEdge("v3", "t");


        simpleDirectedWeightedGraph.setEdgeWeight("s", "v1",1);
        simpleDirectedWeightedGraph.setEdgeWeight("s", "v2",9);
        simpleDirectedWeightedGraph.setEdgeWeight("v1", "v3",9);
        simpleDirectedWeightedGraph.setEdgeWeight("v2", "v4",1);
        simpleDirectedWeightedGraph.setEdgeWeight("v1", "v2",1);
        simpleDirectedWeightedGraph.setEdgeWeight("v4", "v3",1);
        simpleDirectedWeightedGraph.setEdgeWeight("v3", "t",1);
        simpleDirectedWeightedGraph.setEdgeWeight("v4", "t",9);


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

    public void createDirectedGraphComplete() throws IOException {
        int SIZE = 4;
        Supplier<String> vSupplier = new Supplier<String>() {
            private int id = 0;

            @Override
            public String get() {
                return "v" + id++;
            }
        };
//        Graph<String, DefaultWeightedEdge> completeGraph =
//                new SimpleGraph<>(vSupplier, SupplierUtil.createDefaultWeightedEdgeSupplier(), false);
        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> completeGraph =
                new SimpleDirectedWeightedGraph<>(vSupplier,SupplierUtil.createDefaultWeightedEdgeSupplier());
        // Create the CompleteGraphGenerator object
        CompleteGraphGenerator<String, DefaultWeightedEdge> completeGenerator =
                new CompleteGraphGenerator<>(SIZE);

        // Use the CompleteGraphGenerator object to make completeGraph a
        // complete graph with [size] number of vertices
        completeGenerator.generateGraph(completeGraph);

        ShowGraph.printGraphEdges(completeGraph);
        ShowGraph.givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(completeGraph);
        ShowGraph.printGraph(completeGraph);
    }

}
