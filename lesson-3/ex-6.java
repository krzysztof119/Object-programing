class Numbers {
    private int a;
    private int b;
    
    public Numbers(int a, int b){
        this.a = a;
        this.b = b;
    }
    
    public int max() { return Math.max(a, b);}
    public int min() { return Math.min(a, b);}
    public float avg() { return (a + b) / 2;}
}


class Main{
    public static void main(String[] args) {
        Numbers X = new Numbers(5, 10);
        System.out.println(X.max()+"\n");
        System.out.println(X.min()+"\n");
        System.out.println(X.avg()+"\n");
    }
}
