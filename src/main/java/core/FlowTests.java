package core;

import org.jgrapht.Graph;
import org.jgrapht.alg.flow.mincost.CapacityScalingMinimumCostFlow;
import org.jgrapht.alg.flow.mincost.MinimumCostFlowProblem;
import org.jgrapht.alg.interfaces.MinimumCostFlowAlgorithm;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.function.Function;

public class FlowTests {
    public static void flowTest1() {
        final int qwerty = 0;
        CustomGraphOld customGraphOld = new CustomGraphOld();
        Graph graph = customGraphOld.createDirectedGraph();
        Function<String, Integer> nodesFunction = x -> {
            if (x.equals("v1")) {
                System.out.println(x);
                return -5;
            } else if (x.equals("v2")) {
                System.out.println(x);
                return 5;
            } else {
                System.out.println(x);
                return 0;
            }

        };
        nodesFunction.apply("(v1,v3)");
        Function<DefaultWeightedEdge, Integer> edgesFunction = x -> 10;
        MinimumCostFlowProblem problem =
                new MinimumCostFlowProblem.MinimumCostFlowProblemImpl(graph, nodesFunction, edgesFunction);
        System.out.println("**************************");
        System.out.println(problem.getGraph());

        CapacityScalingMinimumCostFlow costFlow = new CapacityScalingMinimumCostFlow();
        MinimumCostFlowAlgorithm.MinimumCostFlow<DefaultWeightedEdge> flows = costFlow.getMinimumCostFlow(problem);

        System.out.println("**************************");
        System.out.println("Flows: " + flows.getFlowMap());

    }
    public static void flowTest2() {
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
                System.out.println(x.sourceAndTargetString());
                return 4;
            } else if (x.sourceAndTargetString().equals("(s : v2)")) {
                System.out.println(x.toString());
                return 5;
            } else if (x.sourceAndTargetString().equals("(v1 : v3)")) {
                System.out.println(x.toString());
                return 5;
            } else if (x.sourceAndTargetString().equals("(v2 : v4)")) {
                System.out.println(x.toString());
                return 2;
            } else if (x.sourceAndTargetString().equals("(v1 : v2)")) {
                System.out.println(x.toString());
                return 2;
            } else if (x.sourceAndTargetString().equals("(v4 : v3)")) {
                System.out.println(x.toString());
                return 3;
            } else if (x.sourceAndTargetString().equals("(v3 : t)")) {
                System.out.println(x.toString());
                return 5;
            } else if (x.sourceAndTargetString().equals("(v4 : t)")) {
                System.out.println(x.toString());
                return 3;
            } else {
                System.out.println(x.toString());
                System.out.println("wwwwwwwwwww");
                return 10;

            }
        };

        MinimumCostFlowProblem problem =
                new MinimumCostFlowProblem.MinimumCostFlowProblemImpl(graph, nodesFunction, edgesFunction);
        System.out.println("**************************");

        CapacityScalingMinimumCostFlow costFlow = new CapacityScalingMinimumCostFlow();
        MinimumCostFlowAlgorithm.MinimumCostFlow<DefaultWeightedEdge> flows = costFlow.getMinimumCostFlow(problem);

        System.out.println("**************************");
        System.out.println(flows.getFlowMap());

    }
}
