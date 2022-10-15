class Pen {
    String brand;
    float length;
    float thickness;
    
    public Pen(String _brand, double _length, double _thickness) {
        this(_brand, (float) _length, (float) _thickness);
    }
    
    public Pen(String _brand, float _length, double _thickness) {
        this(_brand, _length, (float) _thickness);
    }
    
    public Pen(String _brand, double _length, float _thickness) {
        this(_brand, (float) _length, _thickness);
    }
    
    public Pen(String _brand, float _length, float _thickness) {
        brand = _brand;
        length = _length;
        thickness = _thickness;
    }
    
    public void getInfo(){
        System.out.println("Brand is: " + brand + ", length is: " + length + ", thickenss is: " + thickness);
    }
}

class main {
    public static void main(String[] args) {
        Pen Marker1 = new Pen("Copic", 2.1f, 0.2f);
        Marker1.getInfo();
    }
}
