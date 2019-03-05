package core;

import java.util.Scanner;

public class Main {
    public static void main(String... args) throws Exception {

        String command;
        int size = -1;
        int percent = -1;
        int commandInt = -1;
        int cycleLengh = -1;
        int density = -1;

        boolean testConnectivity = true;
        int NumberOfTriesToFindConnectedGraph = 10;

        Postman postman = null;
        Scanner input = new Scanner(System.in);
        System.out.println("Program rozpoczęty");
        instructions();



        while (!(command = input.next()).equals("koniec")) {
            try {
                commandInt = Integer.parseInt(command.trim());
            } catch (NumberFormatException e) {
                System.out.println("Nieprawidłowa komenda");
                continue;
            }

            if (commandInt == 1 || commandInt == 2 || commandInt == 3) {
                System.out.println("Podaj rozmiar grafu:");
                size = getInput(size, input);
                System.out.println("Rozmiar grafu: " + size);

                System.out.println("Podaj procent dróg jednokierunkowych:");
                percent = getInput(percent, input);
                System.out.println("Procent dróg jednokierunkowych: " + percent);

                if (commandInt == 1) {
                    //_________________________________________
                    postman = new Postman(CustomGraph.createComleteGraphUndireted(size), percent, testConnectivity, NumberOfTriesToFindConnectedGraph);
                    //_________________________________________
                } else if (commandInt == 2) {
                    //_________________________________________

                    System.out.println("Podaj długość cyklu:");
                    cycleLengh = getInput(cycleLengh, input);
                    System.out.println("Długość cyklu: " + cycleLengh);

                    postman = new Postman(CustomGraph.createGraphForCyclicity(size, cycleLengh), percent, testConnectivity, NumberOfTriesToFindConnectedGraph);
                    //_________________________________________

                } else {
                    //_________________________________________

                    System.out.println("Podaj gęstość grafu:");
                    density = getInput(density, input);
                    System.out.println("Długość cyklu: " + density);

                    postman = new Postman(CustomGraph.createGraphForDensity(size, density), percent, testConnectivity, NumberOfTriesToFindConnectedGraph);
                    //_________________________________________

                }
                postman.auxiliaryAlg();
                postman.alg();
                System.out.println();
                instructions();
            }
            else {
                System.out.println("Nieprawidłowa komenda");
            }
        }
        System.out.println("Koniec programu");
    }

    private static int getInput(int variable, Scanner input) {
        while (variable < 0) {
            if (input.hasNextInt()) {
                variable = input.nextInt();
            } else {
                input.next();
                System.out.println("Nieprawidłowe wejście");
            }
        }
        return variable;
    }
    private static void instructions() {
        System.out.println("Losowy graf pełny - wpisz 1");
        System.out.println("Losowy graf posiadający cykl o określonej długości - wpisz 2");
        System.out.println("Losowy graf o określonej gęstości- wpisz 3");
        System.out.println("W celu zakończenia - wpisz koniec");
    }


}
