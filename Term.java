public class Term implements Comparable <Term> {
    private int coefficient;
    private int exponent;
    private int denominator;
    private boolean first = false;
    
    public Term (int c, int e, int d) {
        coefficient = c;
        exponent = e;
        denominator = d;
    }
    
    public void setCoefficient (int c) {coefficient = c;}
    public int getCoefficient () {return coefficient;}
    
    public void setDenominator (int d) {denominator = d;}
    public int getDenominator () {return denominator;}
    
    public void setExponent(int e) {exponent = e;}
    public int getExponent () {return exponent;}
    
    public void setFirst(boolean f) {first = f;}
    public boolean getFirst () {return first;}
    
    // this basically returns whether or not one term is greater than the other term by comparing the exponents
    @Override
    public int compareTo(Term otherTerm) {
        if (this.exponent > otherTerm.getExponent()) {
            return 1;
        }
        else if (this.exponent < otherTerm.getExponent()) {
            return -1;
        }
        else if (this.exponent == otherTerm.getExponent()) {
            return 0;
        }
        
        return 0;
    }
    
    @Override
    public String toString() {
        String print = "";
        
        if (coefficient == 0) {
                 if (first) {
                     print += "0";
                 }
                 else {print += " + 0";}
                 
             }
          // if there is a denominator print it
        else if (denominator != 1) {
               //print a plus sign if positive
            if (coefficient > 0) {
                if (first) {
                    print += "(" ;
                    print += coefficient;
                }
                else {
                    print += " + (" ;
                    // ERROR HERE
                    //if (coefficient != 1) {
                     print += coefficient;
                 //}
                    //print += coefficient;
                }
             }
            //print a neg sign if negative
             else {
                 if (first) {
                    print += "(-" ;
                    print += (-(coefficient));
                }
                else {
                    print += " - (";
                    print += (-(coefficient));
                }
             }
     
            print += "/";
            print += (denominator);
            print += ")";
        }
           //if there is no denominator just print the coefficient
       else {
             // print a plus sign if positive
             if (coefficient > 0) {
                 if (first) {
                     print += "" ;
                     // added
                    if (coefficient != 1) {
                     print += coefficient;
                 }
                     //print += coefficient;
                 }
                 else {
                     print += " + " ;
                     // added
                    if (coefficient != 1) {
                     print += coefficient;
                 }
                     //print += coefficient;
                }
              }
            // print a neg sign if negative
              else if (coefficient < 0) {
                 print += " - ";
                 if (coefficient != -1) {
                     print += (-(coefficient));
                 }
              }
              else {
                  if (first) {
                     print += "" ;
                      
                  }
                  else {print += " + ";}
              }
          }
          
          // if there's an exponent print it
          if (exponent != 0 && coefficient != 0) {
              print += "x";
              if (exponent != 1) {
                  print += "^";
                  print += exponent;
              }
          }
          
          return print;
        
    }
    
}