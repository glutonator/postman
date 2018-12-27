package core;

import org.jgrapht.Graph;
import org.jgrapht.GraphTests;
import org.jgrapht.alg.flow.mincost.CapacityScalingMinimumCostFlow;
import org.jgrapht.alg.flow.mincost.MinimumCostFlowProblem;
import org.jgrapht.alg.interfaces.MatchingAlgorithm;
import org.jgrapht.alg.interfaces.MinimumCostFlowAlgorithm;
import org.jgrapht.alg.matching.blossom.v5.KolmogorovMinimumWeightPerfectMatching;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.DepthFirstIterator;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class Postman {
    private Graph<String, CustomEdge> graph;
    //    private Map<Integer, Pair<Double, Double>> weightsMap;
    private int size;

    public Postman() {
    }

    public Postman(int size) throws IOException {
        CustomGraph customGraph = new CustomGraph();
        //TODO: aktualne zalożenie zakłada, że dwa nody sa połączone ze sobą tylko jedną krawędzią o dwóch wagach
//        this.graph = customGraph.createComleteGraphUndireted(size);
        this.graph = SimpleTests.createDirectedGraphFromBookMultigraphCustomEdge();
        this.size = size;
        System.out.println(toString());
        ShowGraph.givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(graph);
    }

    public void auxiliaryAlg() {
        System.out.println("*************************************************");
        System.out.println("Pomocniczy algorytm początek");
        System.out.println("*************************************************");
        if (!GraphTests.isEulerian(this.graph)) {
            System.out.println("Graph is not Eulerian");
            System.out.println("Conversion to Eulerian");

            CustomGraphOld customGraphOld = new CustomGraphOld();
            //todo: w przypdaku gdy jest grafem eulera opuscić tą funckję
            //todo: trzeba wziac poprawkę, że na wyjsćiu tej funckji będzie multigraf

            //nadanie krawędziom grafu G wag - nie używane sa do niczego innego !!!
            for (CustomEdge edge : this.graph.edgeSet()) {
                this.graph.setEdgeWeight(edge, (edge.getWeight1() + edge.getWeight2()) / 2);
            }
            //znalezienie nodów o nieparzystym stopmniu
            Set<String> oddVertexes = new HashSet<>();
            Iterator<String> iter = new DepthFirstIterator<>(graph);
            while (iter.hasNext()) {
                String vertex = iter.next();
                int degree = this.graph.degreeOf(vertex);
                System.out.println(degree);
                if (degree % 2 != 0) {
                    oddVertexes.add(vertex);
                }
            }

            AsSubgraph<String, CustomEdge> asSubgraph = new AsSubgraph<>(this.graph, oddVertexes);
            ShowGraph.printGraphEdges(asSubgraph);
            ShowGraph.printGraph(asSubgraph);
            KolmogorovMinimumWeightPerfectMatching<String, CustomEdge> matching = new KolmogorovMinimumWeightPerfectMatching<>(asSubgraph);
            MatchingAlgorithm.Matching<String, CustomEdge> matching1 = matching.getMatching();
            for (CustomEdge edge : matching1.getEdges()) {
                boolean testIfEdgeAdded = this.graph.addEdge(this.graph.getEdgeSource(edge), this.graph.getEdgeTarget(edge),
                        new CustomEdge(edge.getLabel(), edge.getWeight1(), edge.getWeight2()));
                System.out.println(testIfEdgeAdded);
            }
            System.out.println(matching1);
            ShowGraph.printGraphEdges(this.graph);
            System.out.println("Graph is Eulerian: " + GraphTests.isEulerian(this.graph));

        } else {
            System.out.println("Graph is Eulerian");
        }
        System.out.println("*************************************************");
        System.out.println("Pomocniczy algorytm koniec");
        System.out.println("*************************************************");
    }

    public void alg() throws IOException {
        //todo: zmienna graf używana w algorytmie jest grafem G'-eulerowskim który jest podmienioną wersją grafu G
        Graph<String, DefaultWeightedEdge> graphD_G = createGraphD_G();

        Graph<String, LabelEdge> graphDApostrophe =
                new DirectedWeightedMultigraph<String, LabelEdge>(LabelEdge.class);

        //populate nodes
        for (String vertex : this.graph.vertexSet()) {
            graphDApostrophe.addVertex(vertex);
        }
        //populate edges
        for (CustomEdge edge : this.graph.edgeSet()) {
            String source = this.graph.getEdgeSource(edge);
            String target = this.graph.getEdgeTarget(edge);
            Double c_ij = edge.getWeight1();
            Double c_ji = edge.getWeight2();
            //(i,j)
            graphDApostrophe.addEdge(source, target, new LabelEdge("(" + source + "," + target + ")"));
            graphDApostrophe.setEdgeWeight(source, target, c_ij);
            //(j,i)
            graphDApostrophe.addEdge(target, source, new LabelEdge("(" + target + "," + source + ")"));
            graphDApostrophe.setEdgeWeight(target, source, c_ji);
            //(j,i)'
            graphDApostrophe.addEdge(target, source, new LabelEdge("(" + target + "," + source + ")'"));
            Set<LabelEdge> allEdges = graphDApostrophe.getAllEdges(target, source);
            for (LabelEdge edge1 : allEdges) {
                if (edge1.getLabel().equals("(" + target + "," + source + ")'")) {
                    graphDApostrophe.setEdgeWeight(edge1, (c_ji - c_ij) / 2);
                    break;
                }
            }
        }
        System.out.println("*************************************************");
        System.out.println("graphDApostrophe:");
        System.out.println(graphDApostrophe);
        System.out.println("size: " + graphDApostrophe.edgeSet().size());
        System.out.println("*************************************************");
        //definiowanie problemu przepływu o minimalnym koszcie
        Map<String, Integer> demand = createDemandMap(graphD_G);

        Function<String, Integer> nodesFunction = demand::get;
        Function<LabelEdge, Integer> edgesFunction = x -> {
            if (x.getLabel().contains("\'")) {
                return 2;
            }
            //todo zminić na nieskończoność
            return 10;
        };

        MinimumCostFlowProblem<String, LabelEdge> problem =
                new MinimumCostFlowProblem.MinimumCostFlowProblemImpl<>(graphDApostrophe, nodesFunction, edgesFunction);
        CapacityScalingMinimumCostFlow<String, LabelEdge> costFlow = new CapacityScalingMinimumCostFlow<String, LabelEdge>();
        MinimumCostFlowAlgorithm.MinimumCostFlow<LabelEdge> flows = costFlow.getMinimumCostFlow(problem);

        Map<LabelEdge, Double> optimalFlowMap = flows.getFlowMap();
        System.out.println("**************************");
        System.out.println("optimalFlowMap: " + optimalFlowMap);

        // Tworznie grafu D'' na podstawie optymalnych wartości przepływu
        Graph<String, LabelEdge> graphDApostrophe2 =
                new DirectedWeightedMultigraph<String, LabelEdge>(LabelEdge.class);
        //populate nodes
        for (String vertex : this.graph.vertexSet()) {
            graphDApostrophe2.addVertex(vertex);
        }
        //populate edges
        for (LabelEdge edge : optimalFlowMap.keySet()) {
            if (edge.getLabel().contains("\'")) {
                Double flow = optimalFlowMap.get(edge);

                //ekstrachowanie nazw wierchoków z etykiety
                //tmpLabel- konwersja - (v3,v0)' -> v3,v0
                String tmpLabel = edge.getLabel().substring(1, edge.getLabel().length() - 2);
                System.out.println(tmpLabel + "---" + flow.toString());
                //ekstrakcja początku i końca krawędzi
                String[] target_source = tmpLabel.split(",");
                //nadanie przejrzystych nazw zmiennych - target -> j && source -> i
                String target = target_source[0];
                String source = target_source[1];

                if (flow.equals(0.0)) {
                    System.out.println("if");
                    Double numberOfNewEdges = getNumberOfNewEdges(graphDApostrophe, flows, target, source) + 1;
                    for (int i = 0; i < numberOfNewEdges; i++) {
                        graphDApostrophe2.addEdge(source, target, new LabelEdge("qqqq"));
                        graphDApostrophe2.setEdgeWeight(source, target, this.graph.getEdge(source, target).getWeight1());
                        System.out.println(i);
                    }
                } else {
                    System.out.println("else");
                    Double numberOfNewEdges = getNumberOfNewEdges(graphDApostrophe, flows, source, target) + 1;
                    for (int i = 0; i < numberOfNewEdges; i++) {
                        graphDApostrophe2.addEdge(target, source, new LabelEdge("qqqq"));
                        graphDApostrophe2.setEdgeWeight(target, source, this.graph.getEdge(source, target).getWeight2());
                        System.out.println(i);
                    }
                }
            }
        }
        CustomGraphOld customGraphOld = new CustomGraphOld();
        ShowGraph.printGraph(graphDApostrophe2);
        ShowGraph.printGraphEdges(graphDApostrophe2);
        ShowGraph.givenAdaptedGraph_whenWriteBufferedImage_thenFileShouldExist(graphDApostrophe2);
        System.out.println(GraphTests.isEulerian(graphDApostrophe2));


    }

    public Double getNumberOfNewEdges(Graph<String, LabelEdge> graphDApostrophe, MinimumCostFlowAlgorithm.MinimumCostFlow<LabelEdge> flows, String target, String source) {
        String label_ij = "(" + source + "," + target + ")";
        LabelEdge labelEdge = null;
        Set<LabelEdge> setOfEdges = graphDApostrophe.getAllEdges(source, target);
        for (LabelEdge tmpLabelEdge : setOfEdges) {
            if (tmpLabelEdge.getLabel().equals(label_ij)) {
                labelEdge = tmpLabelEdge;
                break;
            }
        }
        Double numberOfNewEdges = flows.getFlow(labelEdge);
        System.out.println(setOfEdges + numberOfNewEdges.toString());
        return numberOfNewEdges;
    }

    public Map<String, Integer> createDemandMap(Graph<String, DefaultWeightedEdge> graphD_G) {
        Iterator<String> iter = new DepthFirstIterator<>(graphD_G);
        Map<String, Integer> demand = new HashMap<>(this.size);
        while (iter.hasNext()) {
            String vertex = iter.next();
            // demand przeciwny znak - tak jest w algorytmie
            int d_i = -(graphD_G.outDegreeOf(vertex) - graphD_G.inDegreeOf(vertex));
            demand.put(vertex, d_i);
        }
        System.out.println("demand: " + demand);
        return demand;
    }

    public Graph<String, DefaultWeightedEdge> createGraphD_G() {
        Graph<String, DefaultWeightedEdge> graphD_G =
                new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class);

        //populate nodes
        for (String vertex : this.graph.vertexSet()) {
            graphD_G.addVertex(vertex);
        }
        //populate edges
        for (CustomEdge edge : this.graph.edgeSet()) {
            if (edge.checkWhichWeightIsBigger()) {
                DefaultWeightedEdge edgeNew = graphD_G.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge));
                graphD_G.setEdgeWeight(edgeNew, edge.getWeight1());
            } else {
                DefaultWeightedEdge edgeNew = graphD_G.addEdge(graph.getEdgeTarget(edge), graph.getEdgeSource(edge));
                graphD_G.setEdgeWeight(edgeNew, edge.getWeight2());
            }
        }
        return graphD_G;
    }

    public void func() {
        final int qwerty = 0;
        CustomGraphOld customGraphOld = new CustomGraphOld();
        Graph graph = customGraphOld.createDirectedGraph();
        Function<String, Integer> nodesFunction = x -> {
            if (x.equals("v1")) {
                System.out.println(x);
                System.out.println("aaaaaaaaaa");
                return -5;
            } else if (x.equals("v2")) {
                System.out.println(x);
                System.out.println("bbbbbbbbbb");
                return 5;
            } else {
                System.out.println(x);
                System.out.println("ccccccccccc");
                return 0;
            }

        };
        nodesFunction.apply("(v1,v3)");
        Function<DefaultWeightedEdge, Integer> edgesFunction = x -> 10;
//        MinimumCostFlowProblem.MinimumCostFlowProblemImpl problem =
        MinimumCostFlowProblem problem =
                new MinimumCostFlowProblem.MinimumCostFlowProblemImpl(graph, nodesFunction, edgesFunction);
        System.out.println("**************************");
        System.out.println(problem.getGraph());
        System.out.println(problem.getNodeSupply());
        System.out.println(problem.getArcCapacityLowerBounds());
        System.out.println(problem.getArcCapacityUpperBounds());

//        MinimumCostFlowAlgorithm.MinimumCostFlowImpl minimumCostFlowAlgorithm = new MinimumCostFlowAlgorithm.MinimumCostFlowImpl()
//        CapacityScalingMinimumCostFlow()
//        Map<DefaultWeightedEdge,Double> flowMap = new HashMap<>();
        //todo: tutaj jest coś bradzo nie tak, może by użyć tej drugiej funckji CapacityScalingMinimumCostFlow
//        MinimumCostFlowAlgorithm rrrr= new MinimumCostFlowAlgorithm.MinimumCostFlowImpl(5.0,flowMap);
//        rrrr.getMinimumCostFlow()


//        MinimumCostFlowAlgorithm.MinimumCostFlow

//        CapacityScalingMinimumCostFlow.
        //to jest dużo lepsze podejście bo coś dizała
        CapacityScalingMinimumCostFlow costFlow = new CapacityScalingMinimumCostFlow();
//        costFlow.getFlowMap();
        MinimumCostFlowAlgorithm.MinimumCostFlow<DefaultWeightedEdge> flows = costFlow.getMinimumCostFlow(problem);

        System.out.println("**************************");
        System.out.println(flows.getFlowMap());

    }

    public void func222() {
        final int qwerty = 0;
        CustomGraphOld customGraphOld = new CustomGraphOld();
        Graph graph = customGraphOld.createDirectedGraphFromBookMultigraphCustomEdge();
        Function<String, Integer> nodesFunction = x -> {
            if (x.equals("t")) {
                System.out.println(x);
                System.out.println("aaaaaaaaaa");
                return -5;
            } else if (x.equals("s")) {
                System.out.println(x);
                System.out.println("bbbbbbbbbb");
                return 5;
            } else {
                System.out.println(x);
                System.out.println("ccccccccccc");
                return 0;
            }

        };
//        Function<DefaultWeightedEdge, Integer> edgesFunction = x -> {
        Function<CustomEdge, Integer> edgesFunction = x -> {
            if (x.toString().equals("(s : v1)")) {
                System.out.println(x.toString());
                return 4;
            } else if (x.toString().equals("(s : v2)")) {
                System.out.println(x.toString());
                return 5;
            } else if (x.toString().equals("(v1 : v3)")) {
                System.out.println(x.toString());
                return 5;
            } else if (x.toString().equals("(v2 : v4)")) {
                System.out.println(x.toString());
                return 2;
            } else if (x.toString().equals("(v1 : v2)")) {
                System.out.println(x.toString());
                return 2;
            } else if (x.toString().equals("(v4 : v3)")) {
                System.out.println(x.toString());
                return 3;
            } else if (x.toString().equals("(v3 : t)")) {
                System.out.println(x.toString());
                return 5;
            } else if (x.toString().equals("(v4 : t)")) {
                System.out.println(x.toString());
                return 3;
            } else {
                System.out.println(x.toString());
                System.out.println("wwwwwwwwwww");
                return 10;

            }
        };

//        MinimumCostFlowProblem.MinimumCostFlowProblemImpl problem =
        MinimumCostFlowProblem problem =
                new MinimumCostFlowProblem.MinimumCostFlowProblemImpl(graph, nodesFunction, edgesFunction);
        System.out.println("**************************");
        System.out.println(problem.getGraph());
        System.out.println(problem.getNodeSupply());
        System.out.println(problem.getArcCapacityLowerBounds());
        System.out.println(problem.getArcCapacityUpperBounds());

        CapacityScalingMinimumCostFlow costFlow = new CapacityScalingMinimumCostFlow();
        MinimumCostFlowAlgorithm.MinimumCostFlow<DefaultWeightedEdge> flows = costFlow.getMinimumCostFlow(problem);

        System.out.println("**************************");
        System.out.println(flows.getFlowMap());

    }


    @Override
    public String toString() {
        return "Postman{" +
                "graph=" + graph +
//                ", weightsMap=" + weightsMap +
                '}';
    }
}
