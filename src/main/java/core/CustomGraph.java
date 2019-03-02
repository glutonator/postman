package core;

import org.jgrapht.Graph;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.HashSet;
import java.util.Random;
import java.util.function.Supplier;

public class CustomGraph {
    private static final Random random = new Random();

    public static Graph createComleteGraphUndireted(int size) {
        Graph<String, CustomEdge> completeGraph = new WeightedMultigraph(new VertexSupplier(), new EdgeSupplier());
        CompleteGraphGenerator<String, CustomEdge> completeGenerator = new CompleteGraphGenerator<>(size);

        completeGenerator.generateGraph(completeGraph);
        System.out.println("Graf wejściowy bez dróg jednokierunkowych");
        ShowGraph.printGraph(completeGraph);
        ShowGraph.printGraphEdges(completeGraph);
        System.out.println("Graf wejściowy bez dróg jednokierunkowych -- KONIEC");
        return completeGraph;
    }

    //generowanie grafu spojnego o zadanym rozmiarze i gestosci (nie mniejszej od minimalnej mozliwej)
    public static Graph createGraphForDensity(int size, double density) {
        Graph<String, CustomEdge> graph = new WeightedMultigraph(new VertexSupplier(), new EdgeSupplier());
        graph.addVertex();
        HashSet<VPair> usedEdges = new HashSet<>();
        for(int i=1; i<size; ++i){
            String src = VertexSupplier.get(random.nextInt(i)), target = graph.addVertex();
            CustomEdge e = graph.addEdge(src,target);
            usedEdges.add(new VPair(src,target));
        }
        int eMax = (int)(size*(size-1)/2*density-(size-1));
        for(int i=0;i<eMax;++i)
            while(true){
                int le = random.nextInt(size), ge = random.nextInt(size);
                if(le>ge){
                    int tmp=le;
                    le=ge;
                    ge=tmp;
                }
                String src = VertexSupplier.get(le), target = VertexSupplier.get(ge);
                if(le!=ge && !usedEdges.contains(new VPair(src,target))){
                    graph.addEdge(src, target);
                    usedEdges.add(new VPair(src, target));
                    break;
                }
            }
        System.out.println("Graf wejściowy bez dróg jednokierunkowych");
        ShowGraph.printGraph(graph);
        ShowGraph.printGraphEdges(graph);
        System.out.println("Graf wejściowy bez dróg jednokierunkowych -- KONIEC");
        return graph;
    }

    // generowanie pierscienia z mniejszymi cyklami wewnetrznymi o zadanej dlugosci
    public static Graph createGraphForCyclicity(int size, int cycleLength){
        Graph<String, CustomEdge> graph = new WeightedMultigraph(new VertexSupplier(), new EdgeSupplier());
        String firstVertex, currVertex, prevVertex, cycleVertex;
        firstVertex = currVertex = prevVertex = cycleVertex = graph.addVertex();
        for(int i=1; i<size; ++i){
            currVertex = graph.addVertex();
            graph.addEdge(prevVertex,currVertex);
            if(i%(cycleLength-1) == 0){
                graph.addEdge(cycleVertex, currVertex);
                cycleVertex = currVertex;
            }
            prevVertex = currVertex;
        }
        if(!currVertex.equals(firstVertex))
            graph.addEdge(currVertex, firstVertex);
        if(!currVertex.equals(cycleVertex))
            graph.addEdge(cycleVertex, firstVertex);
        System.out.println("Graf wejściowy bez dróg jednokierunkowych");
        ShowGraph.printGraph(graph);
        ShowGraph.printGraphEdges(graph);
        System.out.println("Graf wejściowy bez dróg jednokierunkowych -- KONIEC");
        return graph;
    }

}
class VertexSupplier implements Supplier<String>{
    private int id = 0;

    @Override
    public String get() {
        return get(id++);
    }

    public static String get(int id){
        return "v" + id;
    }
}
class EdgeSupplier implements Supplier<CustomEdge>{
    private int id = 0;

    @Override
    public CustomEdge get() {
        return get(id++);
    }

    public static CustomEdge get(int id){
        return new CustomEdge("e" + id);
    }
}
class VPair{
    String v1,v2;

    public VPair(String v1, String v2){
        this.v1=v1;
        this.v2 =v2;
    }

    @Override
    public boolean equals(Object obj){
        return hashCode()==obj.hashCode();
    }

    @Override
    public int hashCode() {
        return (v1+v2).hashCode();
    }
}