package core;

import org.jgrapht.Graph;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.function.Supplier;

public class CustomGraph {
    private Integer TMP = 0;


    public Graph createComleteGraphUndireted(int size) {
//        int SIZE = 4;
        int SIZE = size;

        Supplier<String> vSupplier = new Supplier<String>() {
            private int id = 0;

            @Override
            public String get() {
                return "v" + id++;
            }
        };

        Supplier<CustomEdge> edgeSupplier = new Supplier<CustomEdge>() {
            private int id = TMP;

            //TODO: zrobić randa dla wartości wag, trzeba też uwzględnić stosuenk liczby dróg jednokiernkowych
            @Override
            public CustomEdge get() {
                return new CustomEdge("e" + id++, 10.0, 100.0);
            }
        };

//        Graph<String, DefaultWeightedEdge> completeGraph =
//                new SimpleWeightedGraph<>(vSupplier, SupplierUtil.createDefaultWeightedEdgeSupplier());
//        Graph<String, CustomEdge> completeGraph =
//                new SimpleWeightedGraph<>(vSupplier, edgeSupplier);
        Graph<String, CustomEdge> completeGraph =
                new WeightedMultigraph<>(vSupplier, edgeSupplier);
        // Create the CompleteGraphGenerator object
//        CompleteGraphGenerator<String, DefaultWeightedEdge> completeGenerator =
//                new CompleteGraphGenerator<>(SIZE);
        CompleteGraphGenerator<String, CustomEdge> completeGenerator =
                new CompleteGraphGenerator<>(SIZE);

        // Use the CompleteGraphGenerator object to make completeGraph a
        // complete graph with [size] number of vertices
        completeGenerator.generateGraph(completeGraph);


        ShowGraph.printGraph(completeGraph);
        ShowGraph.printGraphEdges(completeGraph);
        return completeGraph;
    }
}
