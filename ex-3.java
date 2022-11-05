class Author {
    String name;
    String lastName;
    int age;
    
    public Author(String name, String lastName, int age){
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }
}

class Game {
    private String title;
    private String author;
    private float rating;
    private float size;
    
    public Game(String title, String author, float rating, float size){
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.size = size;
    }
    public Game(String title, String author, double rating, float size){
        this(title,  author, (float) rating, size);
    }
    
    public Game(String title, String author, float rating, double size){
        this(title,  author, rating, (float) size);
    }
    
    public Game(String title, String author, double rating, double size){
        this(title,  author, (float) rating, (float) size);
    }
    
    public void display(){
        System.out.println("Game title: " + title  + ",\nCreated by: " + author + ",\nRated for: " + rating + ",\nRequired disk space: " + size);
    }
}

class main {
    public static void main(String[] args) {
        Game Game1 = new Game("BD", "Janusz Kowalski", 3.5 , 1.3);
        Game1.display();
    }
}
