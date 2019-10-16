package calculator;

import java.util.*;

public class Main {
    public static void main(String args[]){
        System.out.println("Calculator");

		Scanner s = new Scanner(System.in);
		String eq = s.nextLine();

		EquationSolver equationSolver = new EquationSolver(s);
        System.out.println(equationSolver.operate());
    }
}
