package core;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

public class CustomEdge extends DefaultWeightedEdge {
    private String label;
    private Double weight1;
    private Double weight2;

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

    @Override
    public String toString() {
        return "CustomEdge{" +
                "(" + this.getSource() + " : " + this.getTarget() + "), " +
                "label='" + label + '\'' +
                ", weight1=" + weight1 +
                ", weight2=" + weight2 +
                '}';
    }
}