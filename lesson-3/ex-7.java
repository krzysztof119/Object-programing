interface Number{
    double doubleValue();
}

class Logarithm implements Number {
  private double base, argument;
        @Override
        public double doubleValue() { 
            try{
                if(base<=0){
                    throw new ArithmeticException("error: base " +  Double.toString(base) + " cannot be less then 0\n");
                }
                return Math.log(this.argument) / Math.log(this.base);
            }   catch(ArithmeticException x){
                throw new IllegalArgumentException(
                    String.format(
                        "Cannot make calculations for this number: %d",
                        x
                    ),
                x
                );
            }
        }
  public Logarithm(double inputBase, double inputArgument) {
    this.base = inputBase;
    this.argument = inputArgument;
  }
}

class HelloWorld {
    public static void main(String[] args) {
        Logarithm Log = new Logarithm(0, 2);
        System.out.println(Log.doubleValue());
    }
}
