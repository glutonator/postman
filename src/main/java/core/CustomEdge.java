package core;

import javafx.geometry.Pos;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.concurrent.ThreadLocalRandom;

public class CustomEdge extends DefaultWeightedEdge {
    private String label;
    private Double weight1;
    private Double weight2;

    public CustomEdge() {
        double random = ThreadLocalRandom.current().nextInt(2, 10);
        this.label = "aaaa";
        this.weight1 = random;
//        this.weight2 = random+2;
        this.weight2 = 999.0;


    }

    public CustomEdge(String label, Double weight1) {
        this.label = label;
        this.weight1 = weight1;
        this.weight2 = Postman.INF_WEIGHT;
    }

    public CustomEdge(String label, Double weight1, Double weight2) {
        this.label = label;
        this.weight1 = weight1;
        this.weight2 = weight2;
    }

    public String getLabel() {
        return label;
    }

    // c_ij
    public Double getWeight1() {
        return weight1;
    }

    // c_ji
    public Double getWeight2() {
        return weight2;
    }

    public boolean checkWhichWeightIsBigger() {
        if (this.weight1 <= this.weight2) {
            // c_ij <= c_ji
            return true;
        } else {
            // c_ij > c_ji
            return false;
        }
    }

    public String sourceAndTargetString() {
        return
                "(" + this.getSource() + " : " + this.getTarget() + ")";
    }

    @Override
    public String toString() {
        return "CustomEdge{" +
                "(" + this.getSource() + " : " + this.getTarget() + "), " +
                "label='" + label + '\'' +
                ", weight1=" + weight1 +
                ", weight2=" + weight2 +
                '}';
    }
//    @Override
//    public String toString() {
//        return
//                "(" + this.getSource() + " : " + this.getTarget() + ")";
//    }
}
