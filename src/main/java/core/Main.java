package core;

public class Main {
    public static void main(String... args) throws Exception {

//        Postman postman = new Postman(CustomGraph.createComleteGraphUndireted(60),90,true,10);
//        Postman postman = new Postman(CustomGraph.createGraphForCyclicity(45,11),100,true, 10);
        Postman postman = new Postman(CustomGraph.createGraphForDensity(10,0.5),60,true,10);
        postman.auxiliaryAlg();
        postman.alg();

    }
}
