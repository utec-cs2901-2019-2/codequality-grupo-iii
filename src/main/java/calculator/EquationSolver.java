package main.java.calculator;

import java.util.HashMap;
import java.util.*;
import static java.lang.Math.pow;

public class EquationSolver {
    private StringBuilder equation;
	HashMap<Integer, Double> numbers = new HashMap<Integer, Double>();
	HashMap<Character, Double> variables = new HashMap<Character, Double>();
	HashMap<Integer, Integer> brackets = new HashMap<Integer, Integer>();
	Set<Character> operators = new HashSet<Character>(Arrays.asList('+', '-', '/', '*', '^'));


	public EquationSolver(String str){
	    equation = new StringBuilder(str);
    }

    public double operate(){
    simplify_sign();
        format_equation();
        map_brackets();
        return solve(0, equation.length() - 1);
    }


	 double solve (int l, int r) {
		if (brackets.containsKey(l) && brackets.get(l) == r) {
			return solve(l + 1, r - 1);
		}

		if(l == r){
			return numbers.get(l);
		}

		HashMap<Character, Integer> op_pos = new HashMap();
		for(int i = l; i <= r; i++){
			if(equation.charAt(i) == '('){
				i = brackets.get(i);
				continue;
			}
			if(operators.contains(equation.charAt(i))){
				op_pos.put(equation.charAt(i), i);
			}
		}

		if(op_pos.containsKey('+')){
			return solve(l, op_pos.get('+') - 1) + solve(op_pos.get('+') + 1, r);
		}
		if(op_pos.containsKey('-')){
			return solve(l, op_pos.get('-')-1) - solve(op_pos.get('-')+1, r);
		}
		if(op_pos.containsKey('*')){
			return solve(l, op_pos.get('*')-1) * solve(op_pos.get('*')+1, r);
		}
		if(op_pos.containsKey('/')){
			return solve(l, op_pos.get('/')-1) / solve(op_pos.get('/')+1, r);
		}
		if(op_pos.containsKey('^')){
			return pow(solve(l, op_pos.get('^')-1), solve(op_pos.get('^')+1, r));
		}

		return 0;
	}
  
  
      void simplify_sign () {
        StringBuilder simplified_equation = new StringBuilder();

        if(equation.charAt(0) == '+' || equation.charAt(0) == '-'){
            simplified_equation.append('0');
        }

        for(int i = 0; i<equation.length(); i++){
            if(equation.charAt(i) != '+' && equation.charAt(i) != '-'){
                simplified_equation.append(equation.charAt(i));
                continue;
            }

            int negcnt = 0, j;
            for(j=i; j < equation.length() && (equation.charAt(j) == '+' || equation.charAt(j) == '-'); j++){
                if(equation.charAt(j) == '-') negcnt++;
            }

            if(negcnt%2 == 0) simplified_equation.append('+');
            else simplified_equation.append('-');
            i = j-1;
        }
        equation = simplified_equation;
        
    }
      
    public void format_equation() {
        StringBuilder formated_equation = new StringBuilder();
        for (int i = 0; i < equation.length(); i++) {
            if ((equation.charAt(i) >= '0' && equation.charAt(i) <= '9') || equation.charAt(i) == '-'
                    || equation.charAt(i) == '+') {
                i = map_number(i, formated_equation);;
            } else {
                formated_equation.append(equation.charAt(i));
            }
        }
        equation = formated_equation;
    }

    public Integer map_number(Integer i, StringBuilder formated_equation) {

        if (this.equation.charAt(i) == '+') {
            if (i - 1 >= 0 && !this.operators.contains(this.equation.charAt(i - 1))) {
                formated_equation.append('+');
            }
            return i;
        }

        int j = 0;
        if (this.equation.charAt(i) == '-') {
            if (i - 1 >= 0 && !this.operators.contains(this.equation.charAt(i - 1))) {
                formated_equation.append('-');
                return i;
            }
            j++;
        }

        while (i + j < this.equation.length()
                && ((this.equation.charAt(i + j) >= '0' && this.equation.charAt(i + j) <= '9')
                        || this.equation.charAt(i + j) == '.'))
            j++;
        

        double number = Double.parseDouble(this.equation.substring(i, j));
        this.numbers.put(formated_equation.length(), number);

        formated_equation.append('?');
        i += j - 1;

        return i;
    }

    public void map_brackets() throws IllegalArgumentException {
     Stack bracket_stack = new Stack();
     for (int i = 0; i < this.equation.length(); i++) {
        char x = this.equation.charAt(i);
        if (x == '(') {
            bracket_stack.push(i);
        }
        else if (x == ')') {
            if(bracket_stack.empty()) {
                throw new IllegalArgumentException("Parenthesis of equation " + this.equation + " are not balanced");
            }
            brackets.put((Integer) bracket_stack.peek(), i);
            bracket_stack.pop();
        }
    }
    if (!bracket_stack.empty()) {
        throw new IllegalArgumentException("Parenthesis of equation " + this.equation + " are not balanced");
    }
  } 
}
