interface Number{
    double doubleValue();
}

class Logarithm implements Number {
  private double base, argument;
        @Override
        public double doubleValue() { 
            try{
                if(base<=0 || (base == 1 && argument != 1)){
                    throw new ArithmeticException("cannot make calculation with base " +  Double.toString(base) + "\n");
                }
                if(argument<=0){
                    throw new ArithmeticException("cannot make calculation with argument " +  Double.toString(argument) + "\n");
                }
            }   catch(ArithmeticException x){
                System.out.println("Error: "+x);
                return 0;
            }
            System.out.println(Math.log(this.argument) / Math.log(this.base));
            return 0;
        }
  public Logarithm(double inputBase, double inputArgument) {
    this.base = inputBase;
    this.argument = inputArgument;
  }
}

class HelloWorld {
    public static void main(String[] args) {
        Logarithm Log = new Logarithm(0, 0);
        Log.doubleValue();
    }
}
