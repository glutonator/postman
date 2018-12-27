package core;

import javafx.geometry.Pos;
import org.jgrapht.Graph;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class CustomGraph {
    static private Integer TMP = 0;


    public static Graph createComleteGraphUndireted(int size) {
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

            @Override
            public CustomEdge get() {
                return new CustomEdge("e" + id++);
            }
        };


        Graph<String, CustomEdge> completeGraph =
                new WeightedMultigraph<>(vSupplier, edgeSupplier);
        CompleteGraphGenerator<String, CustomEdge> completeGenerator =
                new CompleteGraphGenerator<>(SIZE);

        // Use the CompleteGraphGenerator object to make completeGraph a
        // complete graph with [size] number of vertices
        completeGenerator.generateGraph(completeGraph);

        ShowGraph.printGraph(completeGraph);
        ShowGraph.printGraphEdges(completeGraph);
        return completeGraph;
    }

//    public static Graph<String, CustomEdge> createOneDirectionRoads(Graph<String, CustomEdge> graph, int percent) throws Exception {
//        if(percent<0 || percent >100) {
//            throw new Exception("Zmienna percent jest albo mniejsza od 0, albo większa od 100");
//        }
//        int numberOfOneDirectionRoads = Double.valueOf(graph.edgeSet().size() * percent / 100).intValue();
//        System.out.println("numberOfOneDirectionRoads: " + numberOfOneDirectionRoads);
//        if (numberOfOneDirectionRoads > 0) {
//            int arrayOfInfEdges[] = genereteRandCombiantion(graph.edgeSet().size(), numberOfOneDirectionRoads);
//
//            int i = 0;
//            int count = 0;
//            for (CustomEdge edge : graph.edgeSet()) {
//                if (arrayOfInfEdges[i] == count) {
//                    edge.setWeight2(Postman.INF_WEIGHT);
//                    i++;
//                }
//                count++;
//                if (arrayOfInfEdges.length <= i) {
//                    break;
//                }
//            }
//        }
//        return graph;
//    }
//
//    public static int[] genereteRandCombiantion(int numeberOfNumbers, int limitNumbers) {
//        Integer[] indices = new Integer[numeberOfNumbers];
//        Arrays.setAll(indices, i -> i);
//        Collections.shuffle(Arrays.asList(indices));
//        return Arrays.stream(indices).mapToInt(Integer::intValue).limit(limitNumbers).sorted().toArray();
//    }
}
