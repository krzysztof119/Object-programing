class Author {
    String name;
    String lastName;
    int age;
    
    public Author(String name, String lastName, int age){
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }
    
    public String display(){
        return (this.name + " " + this.lastName + " " + this.age);
    }
}

class Game {
    private String title;
    private Author author;
    private float rating;
    private float size;
    
    public Game(String title, Author author, float rating, float size){
        this.title = title;
        this.author = author;
        this.rating = rating;
        this.size = size;
    }
    public Game(String title, Author author, double rating, float size){
        this(title,  author, (float) rating, size);
    }
    
    public Game(String title, Author author, float rating, double size){
        this(title,  author, rating, (float) size);
    }
    
    public Game(String title, Author author, double rating, double size){
        this(title,  author, (float) rating, (float) size);
    }
    
    public void display(){
        System.out.println("Game title: " + title  + ",\nCreated by: " + author.display() + ",\nRated for: " + rating + ",\nRequired disk space: " + size);
    }
}

class main {
    public static void main(String[] args) {
        Author author1 = new Author("Janusz", "Kowalski", 23);
        Game game1 = new Game("BD", author1, 3.5 , 1.3);
        game1.display();
    }
}
