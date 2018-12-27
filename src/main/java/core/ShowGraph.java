package core;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class ShowGraph {
    public static void givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(Graph g) throws IOException {

        JGraphXAdapter<String, DefaultEdge> graphAdapter = new JGraphXAdapter<String, DefaultEdge>(g);
        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage image =
                mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        File imgFile = new File("graph.png");
        ImageIO.write(image, "PNG", imgFile);

    }
    public static void printGraph(Graph graph) {
        // Print out the graph to be sure it's really complete
        System.out.println("****************************************************");
        Iterator<String> iter = new DepthFirstIterator<>(graph);
        while (iter.hasNext()) {
            String vertex = iter.next();
            System.out.println(
                    "Vertex " + vertex + " is connected to: "
                            + graph.edgesOf(vertex).toString());
        }
    }

    public static void printGraphEdges(Graph graph) {
        System.out.println("****************************************************");
        System.out.print(String.join(",", graph.vertexSet()));
        System.out.println("---count: " + graph.vertexSet().size());
        for (Object e : graph.edgeSet()) {
            System.out.println(e.toString() + " --- " + graph.getEdgeWeight(e));
        }
    }
}
