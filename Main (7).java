// Safa Mohammed
// sxm200175

import java.util.*;
import java.io.*;

public class Main
{
	public static void main(String[] args) throws IOException {
		String fileName;
        FileInputStream inputFile = null;
        
        Scanner scnr = new Scanner(System.in);
        
        // get file name from user 
        System.out.println("Enter file name: ");
        fileName = scnr.next();
        
        // open input file and link scanner
        inputFile = new FileInputStream(fileName);
        Scanner infs = new Scanner(inputFile);
        
        String equation;
        
        // for each line, store equation in binary tree, calculate integral, and print
        while (infs.hasNext()) {
            int upperBound = 0;
            int lowerBound = 0;
            boolean isDefinite = false;
            equation = infs.nextLine();
            
            // find the upper and lower bound of the integral
            if (equation.indexOf('|') != 0) {
                lowerBound = Integer.parseInt(equation.substring(0,equation.indexOf('|')));
                upperBound = Integer.parseInt(equation.substring(equation.indexOf('|') + 1, equation.indexOf(' ')));
                isDefinite = true;
            }
            
        // format the string to parse term
        equation = formatString(equation, isDefinite, upperBound);
        
        // switch upper and lower bound if upper is less than lower
            if (upperBound < lowerBound) {
                int test = upperBound;
                upperBound = lowerBound;
                lowerBound = test;
            }
        
        // find how many terms there are in the equation bu counting the spaces in the formatted string
        int numTerms = 0;
        int numSpaces = 0;
        for(int x = 0; x < equation.length(); x++) {
            if (equation.charAt(x) == ' ') {
                numSpaces++;
            }
        }
        numTerms = numSpaces - 1;
        
        // get the indexes of the first term
        int indx = equation.indexOf(' ') + 1;
        int indx2 = equation.indexOf(' ', indx);
        
        BinTree<Term> binT = new BinTree<Term>();
        
        String term;
        
        // for each term, parse, create node, and store inside of the tree
        while (numTerms != 0) {
            term = equation.substring(indx, indx2);
            
            // put term into a node and organize it inside the binary tree
            parseTerm(term, binT);
            indx = indx2 + 1; 
            indx2 = equation.indexOf(' ', indx);
            numTerms--;
        }
        
        // starting at the root, calculate the antiderivate 
        calcIntegral(binT.getRoot());
        
        findFirstTerm(binT);
        
        // print the tree with correct formatting
        binT.print(binT.getRoot());
        if (isDefinite) {
            System.out.print(", " + lowerBound + "|" + upperBound + " = ");
            System.out.printf("%.03f", printResult(binT.getRoot(), upperBound, lowerBound, 0));
        }
        else {
            System.out.print(" + C"); 
        }
        System.out.println();
            
	}
	
	inputFile.close();
	scnr.close();
	
	}
	
	public static void findFirstTerm(BinTree<Term> tree) {
	    Node <Term> cur = tree.getRoot();
	    // start at the root and find the greatest term
	    while (cur.getRight() != null) {
	        cur = cur.getRight();
	    }
	    
	    // set it as the first
	    if (cur != null) {
	        (cur.getObject()).setFirst(true);
	    }
	}
	
	public static void parseTerm(String t, BinTree<Term> tree) {
	    int exp;
	    int coef;
	    
	    if (t != "" && t != "-") {
	        if (t.indexOf('x') != -1) {
	        // set coefficient up until the x
	        
	        if (t.charAt(0) != 'x' && t.charAt(0) != '-') {
	            coef = Integer.parseInt(t.substring(0, t.indexOf('x')));
	            exp = 1;
	        }
	        else if (t.charAt(0) != 'x' && t.charAt(1) != 'x') {
	            coef = Integer.parseInt(t.substring(0, t.indexOf('x')));
	            exp = 1;
	        }
	        else if (t.charAt(0) == '-') {
	            coef = -1;
	            exp = 1;
	        }
	        else {
	            coef = 1;
	            exp = 1;
	        }
	       
	        if (t.indexOf('^') != -1) {
	            // set exponent if it exists
	            exp = Integer.parseInt(t.substring(t.indexOf('^') + 1, t.length()));
	        }
	        
	            
	        }
	    
	    // if no x then the entire string is one number ie coefficient
	    else {
	        coef = Integer.parseInt(t);
	        exp = 0;
	    }
	    
	    // Calculate antiderivative
	    int denominator = 1;
	    
	    
	    Term aTerm = new Term(coef, exp, denominator);
	     
	    Node<Term> n = new Node<Term>();
	    
	    n.setObject(aTerm);
	    
	    // search for a like term ,if found update the coefficient
	    if (tree.search(n) != null) {
	        Node<Term> o = tree.search(n);
	        int oldC = (o.getObject()).getCoefficient();
	        int newC = (n.getObject()).getCoefficient();
	        int val = oldC + newC;
	        (o.getObject()).setCoefficient(val);
	    }
	    // if not a like term then add to the tree
	    else {
	        tree.insert(n);
	   }
	    
	       
	    }
	}
	
	// calculate the integral
	public static void calcIntegral(Node<Term> n) {
	    if (n == null) {
	        return;
	    }
        
        calcIntegral(n.getRight());
        
        // if exponent is not zero
        if ((n.getObject()).getExponent() != 0) {
            // increase exponent by 1
	        (n.getObject()).setExponent((n.getObject()).getExponent() + 1);
	        // divide by the new exponent --> the denominator is now equal to the exponent
	        (n.getObject()).setDenominator((n.getObject()).getExponent());
	        
	        // find the greatest common divisor
	        int gcd = getGCD((n.getObject()).getCoefficient(), (n.getObject()).getDenominator());
	        
	        // divide both the coefficient and denominator by the factor to simplify
	        (n.getObject()).setCoefficient((n.getObject()).getCoefficient()/gcd);
	        (n.getObject()).setDenominator((n.getObject()).getDenominator() / gcd);
	        
	       // if the denominator is a negative one, divide the coefficient by -1 and reset denominator to 1
	        if ((n.getObject()).getDenominator() == -1) {
	            (n.getObject()).setCoefficient(-((n.getObject()).getCoefficient()));
	            (n.getObject()).setDenominator(1);
	        }
	        // if the denominator is negative then to make it easier while printing make the coefficient negative
	        else if ((n.getObject()).getDenominator() < 0) {
	            (n.getObject()).setCoefficient(-((n.getObject()).getCoefficient()));
	            (n.getObject()).setDenominator(-((n.getObject()).getDenominator()));
	        }
	        
	        // the denominator and coef are divisible by one another but have different signs
	        int coef2 = -((n.getObject()).getCoefficient());
	       
	         if ((n.getObject()).getDenominator() == coef2) {
	            (n.getObject()).setCoefficient(-1);
	            (n.getObject()).setDenominator(1);
	        }
	    }
	    // if the exponent is zero then just add one to it
	    else {
	        (n.getObject()).setExponent((n.getObject()).getExponent() + 1);
	    }
        
        calcIntegral(n.getLeft());
	    
	}
	
	// find the greatest common factor
	public static int getGCD (int coef, int denominator) {
	    if (denominator == 0) {
	        return coef;
	    }
	    
	    return getGCD(denominator, (coef%denominator));
	}
	

	public static double printResult(Node<Term> n, int up, int low, double result) {
        if (n == null) {
            return 0;
        } 
        // calculate the value of the current term in the tree for both the lower and upped bound
        double lower = ( ((double)(n.getObject()).getCoefficient()/(double)(n.getObject()).getDenominator()) * (Math.pow(low, (n.getObject()).getExponent())));
        double upper = (((double)(n.getObject()).getCoefficient()/(double)(n.getObject()).getDenominator()) * (Math.pow(up, (n.getObject()).getExponent())));
        
        result = upper - lower;
        
        return (result + printResult(n.getRight(), up, low, result) + printResult(n.getLeft(), up, low, result));

    }
	
	
	public static String formatString(String eq, boolean isD, int upper) {
	    // remove all spaces
        eq = eq.replace(" ", "");
         
        // replace all the addition signs with a space
        eq = eq.replace("+", " ");
        
        // replace any double space that may have been caused by removing the addition sign
        eq = eq.replace("  ", " ");
        
        // add a space before a subtraction sign 
        for(int x = 0; x < eq.length(); x++) {
            if (!isD && eq.charAt(x) == '|') {
                String newString="";
                // iterate through every character of the original equation
                for(int z = 0; z < eq.length(); z++) {
                    // append every character to the new string
                    newString += eq.charAt(z);
                    // at the space after the integral sign append a space
                    if (z == (x)) {
                        newString += " ";
                    }
                }
                    // replace the equation with the modified equation
                    eq = newString;
            }
           
            // convert upper bound into a string for comparison
            String up = Integer.toString(upper);
            
            // if definite then start the formatting after the upper bound
            if (isD && eq.indexOf(up, eq.indexOf('|')) == x) {
                String newString="";
                // iterate through every character of the original equation
                for(int z = 0; z < eq.length(); z++) {
                    // append every character to the new string
                    newString += eq.charAt(z);
                    // at the space after the integral sign append a space
                    if (z == (x + up.length() - 1)) {
                        newString += " ";
                    }
                }
                    // replace the equation with the modified equation
                    eq = newString;
            }
        
            
            // add a space before the dx in the string
            if (eq.charAt(x) == 'd') {
                if (eq.charAt(x-1) != ' ') {
                    String newString="";
                    for(int z = 0; z < eq.length(); z++) {
                        // append every character to the new string
                        newString += eq.charAt(z);
                        // at the space before the dx append a space
                        if (z == (x-1)) {
                            newString += " ";
                        }
                    }
                    // replace the equation with the modified equation
                    eq = newString;
                }
            }
            
            if (eq.charAt(x) == '-') {
                // if the character before the negative sign of a coefficient is a digit (exponent) then add a space there
                if (x != 0 && Character.isDigit(eq.charAt(x-1))) {
                    String newString="";
                    // iterate through every character of the original equation
                    for(int z = 0; z < eq.length(); z++) {
                        // append every character to the new string
                        newString += eq.charAt(z);
                        // at the space before the negative sign append a space
                        if (z == (x-1)) {
                            newString += " ";
                        }
                    }
                    // replace the equation with the modified equation
                    eq = newString;
                }
                //if the character before the negative sign of a coefficient is an x then add a space there
                else if (x != 0 && eq.charAt(x-1) == 'x') {
                    String newString="";
                    
                    for(int z = 0; z < eq.length(); z++) {
                        // append every character to the new string
                        newString += eq.charAt(z);
                        // at the space before the negative sign append a space
                        if (z == (x-1)) {
                            newString += " ";
                        }
                    }
                    // replace the equation with the modified equation
                    eq = newString;
                }
                
            }
        }
        
        // return the formatted string
        return eq;
	}
}
