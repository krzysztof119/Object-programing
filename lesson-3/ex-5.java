// Online Java Compiler
// Use this editor to write, compile and run your Java code online
import java.util.HashMap;
 class FakeCantor {
  private final HashMap<String, Float> rates = new HashMap<>() {{
    put("USD", 1.0366f);
    put("GBP", 0.87063f);
    put("CHF", 0.9881f);
    put("JPY", 145.12f);
  }};
  public float euroToRate(String currency) {return this.rates.get(currency);}
  public FakeCantor() {}
}

interface Currency {
  Currency addedCurrency(float value, String currency);
  Currency subtractedCurrency(float value, String currency);
  String abbreviation();
  String symbol();
  String balance();
  float dollarExchangeRate();
}

class Euro extends FakeCantor implements Currency{
    private float money;
    
    public Euro(float money){
        this.money = money;
    }
    public Euro(int money){
        this((float)money);
    }
    
    public Euro addedCurrency(float value, String currency){return new Euro(money + FakeCantor.euroToRate(currency) * value);}
    public Euro subtractedCurrency(float value, String currency){return new Euro(money - FakeCantor.euroToRate(currency) * value);}
    public String abbreviation(){return "EUR";}
    public String symbol(){return "E";}
    public String balance(){return Float.toString(money);}
    public float dollarExchangeRate(){return 1.0366f;}
}

class Main {
    public static void main(String[] args) {
        Euro customer1 = new Euro(20f);
    }
}
