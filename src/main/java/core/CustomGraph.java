package core;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import com.sun.javafx.geom.Edge;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleGraph;
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

public class CustomGraph {
    public void givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(Graph g) throws IOException {

        JGraphXAdapter<String, DefaultEdge> graphAdapter = new JGraphXAdapter<String, DefaultEdge>(g);
        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage image =
                mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        File imgFile = new File("graph.png");
        ImageIO.write(image, "PNG", imgFile);

//        assertTrue(imgFile.exists());
    }

    public void createGraphSimpleGraph() throws IOException {
        System.out.println("ppp");

        Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        g.addVertex("v1");
        g.addVertex("v2");
        g.addVertex("v3");

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


//        CustomGraph customGraph = new CustomGraph();
//        customGraph.givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(g);
        givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(completeGraph);

        printGraph(completeGraph);

        DefaultEdge edge = completeGraph.getEdge("v0", "v1");
        System.out.println("sdadsa");
        printGraphEdges(completeGraph);

    }

    public void createDirectedGraph() throws IOException {
        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> simpleDirectedWeightedGraph =
                new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        simpleDirectedWeightedGraph.addVertex("v1");
        simpleDirectedWeightedGraph.addVertex("v2");
        simpleDirectedWeightedGraph.addVertex("v3");
        DefaultWeightedEdge edge = simpleDirectedWeightedGraph.addEdge("v1", "v2");
        DefaultWeightedEdge edge2 = simpleDirectedWeightedGraph.addEdge("v2", "v1");

        simpleDirectedWeightedGraph.setEdgeWeight(edge, 45);
//        System.out.println(simpleDirectedWeightedGraph.toString());
//        System.out.println(simpleDirectedWeightedGraph.getEdgeWeight(edge));
//        System.out.println(simpleDirectedWeightedGraph.getEdgeWeight(edge2));

        printGraphEdges(simpleDirectedWeightedGraph);
//        givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(simpleDirectedWeightedGraph);
        printGraph(simpleDirectedWeightedGraph);
    }

    public void printGraph(Graph graph) {
        // Print out the graph to be sure it's really complete
        System.out.println("***************************************************8");
        Iterator<String> iter = new DepthFirstIterator<>(graph);
        while (iter.hasNext()) {
            String vertex = iter.next();
            System.out.println(
                    "Vertex " + vertex + " is connected to: "
                            + graph.edgesOf(vertex).toString());
        }
    }

    public void printGraphEdges(Graph graph) {
        System.out.print(String.join(",", graph.vertexSet()));
        System.out.println("---count: " + graph.vertexSet().size());
        for (Object e : graph.edgeSet()) {
            System.out.println(e.toString() + " --- " + graph.getEdgeWeight(e));
        }
    }
}
