package calculator;

public class Main {
    public static void main(String args[]){
        System.out.println("Calculator");

		EquationSolver equationSolver = new EquationSolver("2 + 2");
        System.out.println(equationSolver.operate());
    }
}
