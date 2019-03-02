package core;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.GraphTests;
import org.jgrapht.alg.connectivity.GabowStrongConnectivityInspector;
import org.jgrapht.alg.cycle.HierholzerEulerianCycle;
import org.jgrapht.alg.flow.mincost.CapacityScalingMinimumCostFlow;
import org.jgrapht.alg.flow.mincost.MinimumCostFlowProblem;
import org.jgrapht.alg.interfaces.MatchingAlgorithm;
import org.jgrapht.alg.interfaces.MinimumCostFlowAlgorithm;
import org.jgrapht.alg.matching.blossom.v5.KolmogorovMinimumWeightPerfectMatching;
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.*;
import java.util.function.Function;

public class Postman {
    private Graph<String, CustomEdge> graph;
    public static final Double INF_WEIGHT = 100.0;
    public static final Integer INF_CAPACITY = CapacityScalingMinimumCostFlow.CAP_INF;

    public Postman() {
    }

    public Postman(Graph createdGraph, int percent, boolean testConnectivity, int NumberOfTriesToFindConnectedGraph) throws Exception {
        if (percent < 0 || percent > 100) {
            throw new Exception("Zmienna percent jest albo mniejsza od 0, albo większa od 100");
        }
        this.graph = createOneDirectionRoads(createdGraph, percent, testConnectivity, NumberOfTriesToFindConnectedGraph);
        System.out.println("Graf wejściowy z drogami jednokierunkowymi");
        ShowGraph.printGraphEdges(this.graph);
        System.out.println("Graf wejściowy z drogami jednokierunkowymi -- KONIEC");

    }

    public Graph<String, CustomEdge> createOneDirectionRoads(Graph<String, CustomEdge> graph, int percent,
                                                             boolean testConnectivity, int NumberOfTriesToFindConnectedGraph)
            throws Exception {

        if (percent < 0 || percent > 100) {
            throw new Exception("Zmienna percent jest albo mniejsza od 0, albo większa od 100");
        }

        int numberOfOneDirectionRoads = Double.valueOf(graph.edgeSet().size() * percent / 100).intValue();
        System.out.println("Liczba dróg jedokierunkowych: " + numberOfOneDirectionRoads);
        boolean isConnected = false;
        int[] arrayOfInfEdges = null;
        int cantfindcombination = 0;
        while (!isConnected && numberOfOneDirectionRoads > 0) {
            arrayOfInfEdges = genereteRandCombiantion(graph.edgeSet().size(), numberOfOneDirectionRoads);

            if (testConnectivity) {
                //******************************
                Graph<String, DefaultEdge> tmpGraph = new DirectedMultigraph<>(DefaultEdge.class);
                //populate nodes
                for (String vertex : graph.vertexSet()) {
                    tmpGraph.addVertex(vertex);
                }
                Set<CustomEdge> customEdges = graph.edgeSet();
                for (CustomEdge edge : customEdges) {
                    if (!edge.getWeight1().equals(Postman.INF_WEIGHT)) {
                        tmpGraph.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge));
                    }
                    if (!edge.getWeight2().equals(Postman.INF_WEIGHT)) {
                        tmpGraph.addEdge(graph.getEdgeTarget(edge), graph.getEdgeSource(edge));
                    }
                }
                //******************************


                int j = 0;
                int count222 = 0;
                for (CustomEdge edge : graph.edgeSet()) {
                    if (arrayOfInfEdges[j] == count222) {
                        tmpGraph.removeEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge));
                        j++;
                    }
                    count222++;
                    if (arrayOfInfEdges.length <= j) {
                        break;
                    }
                }
                GabowStrongConnectivityInspector<String, DefaultEdge> inspector = new GabowStrongConnectivityInspector<>(tmpGraph);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                isConnected = inspector.isStronglyConnected();
                System.out.println("Graf jest silnie spójny:" + isConnected);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                if (!isConnected && (cantfindcombination > NumberOfTriesToFindConnectedGraph)) {
                    throw new Exception("Nie można znaleźć kombinacji by graf był silnie spójny");
                }
                cantfindcombination++;
            } else {
                break;
            }
        }

        if (numberOfOneDirectionRoads > 0) {
            int i = 0;
            int count = 0;
            for (CustomEdge edge : graph.edgeSet()) {
                if (arrayOfInfEdges[i] == count) {
                    edge.setWeight2(Postman.INF_WEIGHT);
                    i++;
                }
                count++;
                if (arrayOfInfEdges.length <= i) {
                    break;
                }
            }
        }
        return graph;
    }

    public int[] genereteRandCombiantion(int numeberOfNumbers, int limitNumbers) {
        Integer[] indices = new Integer[numeberOfNumbers];
        Arrays.setAll(indices, i -> i);
        Collections.shuffle(Arrays.asList(indices));
        return Arrays.stream(indices).mapToInt(Integer::intValue).limit(limitNumbers).sorted().toArray();
    }

    public void auxiliaryAlg() {
        System.out.println("*************************************************");
        System.out.println("Pomocniczy algorytm początek");
        System.out.println("*************************************************");
        if (!GraphTests.isEulerian(this.graph)) {
            System.out.println("Graph is not Eulerian");
            System.out.println("Conversion to Eulerian");

            //nadanie krawędziom grafu G wag - nie używane sa do niczego innego !!!
            for (CustomEdge edge : this.graph.edgeSet()) {
                this.graph.setEdgeWeight(edge, (edge.getWeight1() + edge.getWeight2()) / 2);
            }
            //znalezienie nodów o nieparzystym stopniu
            Set<String> oddVertexes = new HashSet<>();
            Iterator<String> iter = new DepthFirstIterator<>(graph);
            System.out.println("degrees:");
            while (iter.hasNext()) {
                String vertex = iter.next();
                int degree = this.graph.degreeOf(vertex);
                System.out.print(degree + ",");
                if (degree % 2 != 0) {
                    oddVertexes.add(vertex);
                }
            }
            System.out.println();

            Graph<String, DefaultEdge> tmpgraph = new WeightedMultigraph(DefaultEdge.class);
            //populate nodes
            for (String vertex : oddVertexes) {
                tmpgraph.addVertex(vertex);
            }
            String[] arrayOddVer = oddVertexes.toArray(new String[oddVertexes.size()]);
            System.out.println("oddVertexes:");
            System.out.println(oddVertexes.toString());
            FloydWarshallShortestPaths floydWarshallShortestPaths = new FloydWarshallShortestPaths(this.graph);
            for (int i = 0; i < arrayOddVer.length - 1; i++) {
                for (int j = i + 1; j < arrayOddVer.length; j++) {
                    double tmpWeight = floydWarshallShortestPaths.getPathWeight(arrayOddVer[i], arrayOddVer[j]);
                    DefaultEdge tmpedge = tmpgraph.addEdge(arrayOddVer[i], arrayOddVer[j]);
                    tmpgraph.setEdgeWeight(tmpedge, tmpWeight);
                }
            }
            KolmogorovMinimumWeightPerfectMatching<String, DefaultEdge> matching = new KolmogorovMinimumWeightPerfectMatching<>(tmpgraph);
            MatchingAlgorithm.Matching<String, DefaultEdge> matching1 = matching.getMatching();
            for (DefaultEdge edge1 : matching1.getEdges()) {
                GraphPath path = floydWarshallShortestPaths.getPath(tmpgraph.getEdgeSource(edge1), tmpgraph.getEdgeTarget(edge1));
                List<CustomEdge> edgeList = path.getEdgeList();
                for (CustomEdge edge : edgeList) {
                    boolean testIfEdgeAdded = this.graph.addEdge(this.graph.getEdgeSource(edge), this.graph.getEdgeTarget(edge),
                            new CustomEdge(edge.getLabel(), edge.getWeight1(), edge.getWeight2()));
                    System.out.println("edge added: " + testIfEdgeAdded);
                }
            }

//            System.out.println(matching1);
            System.out.println("New graph: " + GraphTests.isEulerian(this.graph));
            ShowGraph.printGraphEdges(this.graph);
            System.out.println("Graph is Eulerian: " + GraphTests.isEulerian(this.graph));

        } else {
            System.out.println("Graph is Eulerian");
        }
        System.out.println("*************************************************");
        System.out.println("Pomocniczy algorytm koniec");
        System.out.println("*************************************************");
    }

    public void alg()  {
        // zmienna graph używana w algorytmie jest grafem G'-eulerowskim który jest podmienioną wersją grafu G
        Graph<String, DefaultWeightedEdge> graphD_G = createGraphD_G();

        Graph<String, LabelEdge> graphDApostrophe =
                new DirectedWeightedMultigraph<String, LabelEdge>(LabelEdge.class);

        //populate nodes
        for (String vertex : this.graph.vertexSet()) {
            graphDApostrophe.addVertex(vertex);
        }
        //populate edges
        //unikalny numer umożliwiający rozróznianie krawędzi !!! Umożliwia rozróżnianie krawędzi równoległych o tych samych wagach!!!
        int uniqueNumber = 0;
        for (CustomEdge edge : this.graph.edgeSet()) {
            String source = this.graph.getEdgeSource(edge);
            String target = this.graph.getEdgeTarget(edge);
            Double c_ij = edge.getWeight1();
            Double c_ji = edge.getWeight2();
            String uniqueAppend = "." + uniqueNumber + ".";
            //(i,j)
            graphDApostrophe.addEdge(source, target, new LabelEdge("(" + source + "," + target + ")" + uniqueAppend));
            //******
            Set<LabelEdge> allEdgesOne = graphDApostrophe.getAllEdges(source, target);
            for (LabelEdge edge1 : allEdgesOne) {
                if (edge1.getLabel().equals("(" + source + "," + target + ")" + uniqueAppend)) {
                    graphDApostrophe.setEdgeWeight(edge1, c_ij);
                }
            }
            //******

            //(j,i)
            graphDApostrophe.addEdge(target, source, new LabelEdge("(" + target + "," + source + ")" + uniqueAppend));
            //******
            Set<LabelEdge> allEdgesTwo = graphDApostrophe.getAllEdges(target, source);
            for (LabelEdge edge1 : allEdgesTwo) {
                if (edge1.getLabel().equals("(" + target + "," + source + ")" + uniqueAppend)) {
                    graphDApostrophe.setEdgeWeight(edge1, c_ji);
                }
            }
            //******

            //(j,i)'
            graphDApostrophe.addEdge(target, source, new LabelEdge("(" + target + "," + source + ")'" + uniqueAppend));
            Set<LabelEdge> allEdgesThree = graphDApostrophe.getAllEdges(target, source);
            for (LabelEdge edge1 : allEdgesThree) {
                if (edge1.getLabel().equals("(" + target + "," + source + ")'" + uniqueAppend)) {
                    graphDApostrophe.setEdgeWeight(edge1, (c_ji - c_ij) / 2);
                }
            }
            uniqueNumber++;
        }

        //definiowanie problemu przepływu o minimalnym koszcie
        Map<String, Integer> demand = createDemandMap(graphD_G);

        Function<String, Integer> nodesFunction = demand::get;
        Function<LabelEdge, Integer> edgesFunction = x -> {
            if (x.getLabel().contains("\'")) {
                return 2;
            }
            return Postman.INF_CAPACITY;
        };

        MinimumCostFlowProblem<String, LabelEdge> problem =
                new MinimumCostFlowProblem.MinimumCostFlowProblemImpl<>(graphDApostrophe, nodesFunction, edgesFunction);
        CapacityScalingMinimumCostFlow<String, LabelEdge> costFlow = new CapacityScalingMinimumCostFlow<String, LabelEdge>();
        MinimumCostFlowAlgorithm.MinimumCostFlow<LabelEdge> flows = costFlow.getMinimumCostFlow(problem);

        Map<LabelEdge, Double> optimalFlowMap = flows.getFlowMap();

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
                //tmpLabel- konwersja - (v3,v0)'.xxx. -> v3,v0 oraz .xxx. //.xxx. służy do rozrózniania krawędzi równoległych
                String[] tmpLabel = edge.getLabel().split("\\.", 2);
                String append = "." + tmpLabel[1];
                String tmpLabel2 = tmpLabel[0].substring(1, tmpLabel[0].length() - 2);
//                System.out.println(tmpLabel2 + "---" + flow.toString());
                //ekstrakcja początku i końca krawędzi
                String[] target_source = tmpLabel2.split(",");
                //nadanie przejrzystych nazw zmiennych - target -> j && source -> i
                String target = target_source[0];
                String source = target_source[1];

                if (flow.equals(0.0)) {
//                    System.out.println("if");
                    Double numberOfNewEdges = getNumberOfNewEdges(graphDApostrophe, flows, target, source, append) + 1;
                    for (int i = 0; i < numberOfNewEdges; i++) {
                        LabelEdge labelEdge = new LabelEdge(source + "," + target);
                        boolean tmp = graphDApostrophe2.addEdge(source, target, labelEdge);
                        graphDApostrophe2.setEdgeWeight(labelEdge, this.graph.getEdge(source, target).getWeight1());

                    }
                } else {
//                    System.out.println("else");
                    double numberOfNewEdges = getNumberOfNewEdges(graphDApostrophe, flows, source, target, append) + 1;
                    for (int i = 0; i < numberOfNewEdges; i++) {
                        LabelEdge labelEdge = new LabelEdge(target + "," + source);
                        boolean tmp = graphDApostrophe2.addEdge(target, source, labelEdge);
                        graphDApostrophe2.setEdgeWeight(labelEdge, this.graph.getEdge(source, target).getWeight2());
                    }
                }
            }
        }
        System.out.println("****************************************************");
        System.out.println("Krawędzie należace do drogi chińskiego listonosza:");
        ShowGraph.printGraphEdges(graphDApostrophe2);
        System.out.println("Graf jest grafem eulera, każda krawędź skierowana wybrana dokładnie raz" +
                " należy do drogi chińskiego listonosza: " + GraphTests.isEulerian(graphDApostrophe2));

        testIfInfEdgesAreUsed(graphDApostrophe2);

        HierholzerEulerianCycle<String, LabelEdge> objectObjectHierholzerEulerianCycle = new HierholzerEulerianCycle<>();
        GraphPath<String, LabelEdge> eulerianCycle = objectObjectHierholzerEulerianCycle.getEulerianCycle(graphDApostrophe2);
        List<String> vertexList = eulerianCycle.getVertexList();

        System.out.println("____________________________________");
        System.out.println("Lista kolejnych wierchołków ścieżki chińskiego listonosza:");
        System.out.println(String.join(" -> ",vertexList));

    }

    public Double getNumberOfNewEdges(Graph<String, LabelEdge> graphDApostrophe,
                                      MinimumCostFlowAlgorithm.MinimumCostFlow<LabelEdge> flows,
                                      String target, String source, String append) {
        String label_ij = "(" + source + "," + target + ")" + append;
        LabelEdge labelEdge = null;
        Set<LabelEdge> setOfEdges = graphDApostrophe.getAllEdges(source, target);
        for (LabelEdge tmpLabelEdge : setOfEdges) {
            if (tmpLabelEdge.getLabel().equals(label_ij)) {
                labelEdge = tmpLabelEdge;
                break;
            }
        }
        return flows.getFlow(labelEdge);
    }

    public Map<String, Integer> createDemandMap(Graph<String, DefaultWeightedEdge> graphD_G) {
        Iterator<String> iter = new DepthFirstIterator<>(graphD_G);
        Map<String, Integer> demand = new HashMap<>(this.graph.vertexSet().size());
        while (iter.hasNext()) {
            String vertex = iter.next();
            // demand przeciwny znak - tak jest w algorytmie
            int d_i = -(graphD_G.outDegreeOf(vertex) - graphD_G.inDegreeOf(vertex));
            demand.put(vertex, d_i);
        }
        System.out.println("Zapotrzebowanie wierzchołków: " + demand);
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

    public int testIfInfEdgesAreUsed(Graph<String, LabelEdge> graphDApostrophe2) {
        int count = 0;
        for (LabelEdge edge : graphDApostrophe2.edgeSet()) {
            Double edgeWeight = graphDApostrophe2.getEdgeWeight(edge);
            if (edgeWeight.equals(Postman.INF_WEIGHT)) {
                count++;
            }
        }
        System.out.println("Liczba użytych karawędzi o nieskończonej wadze: " + count);
        return count;
    }


    @Override
    public String toString() {
        return "Postman{" +
                "graph=" + graph +
                '}';
    }
}
