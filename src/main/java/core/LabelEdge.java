package core;

import org.jgrapht.graph.DefaultWeightedEdge;

public class LabelEdge extends DefaultWeightedEdge {
    private String label;

    public LabelEdge(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
//        return "LabelEdge{" +
        return      "{label='" + label + '\'' +
                "(" + this.getSource() + " : " + this.getTarget() + ")"+ "w:" +this.getWeight()+
                '}';
    }
}
