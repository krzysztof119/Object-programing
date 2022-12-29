import java.util.*;

interface Shelf{
    boolean rent();
    boolean handBack();
    String getName();
}

class BookShelf implements Shelf{

    private String name;
    private String author;
    private int quantity;

    public BookShelf(String name, String author, int quantity){
        this.name=name;
        this.author=author;
        this.quantity=quantity;
    }

    public boolean rent(){
        if(quantity <= 0){
            return false;
        }
        
        quantity--;
        return true;
    }

    public boolean handBack(){
        quantity++;
        return true;
    }

    public String getName(){
        return name;
    }

    public void displayQuantity(){
        System.out.println(this.quantity);
    }
}

class AudioShelf implements Shelf{

    private String name;
    private String author;
    private int quantity;

    public AudioShelf(String name, String author, int quantity){
        this.name=name;
        this.author=author;
        this.quantity=quantity;
    }

    public boolean rent(){
        if(quantity <= 0){
            return false;
        }
        
        quantity--;
        return true;
    }

    public boolean handBack(){
        quantity++;
        return true;
    }

    public String getName(){
        return name;
    }

    public void displayQuantity(){
        System.out.println(this.quantity);
    }
}

class Library{
    private String name;
    private Vector<Shelf> stack = new Vector<Shelf>(0);
    
    public Library(String name){
        this.name=name;
    }
    
    public boolean addBook(Shelf x){
        if(stack.contains(x)) {         // nie moze istniec 2 razy ten sam element
            System.out.println("Library alredy contains this shelf");
            return false;
        } 
        return stack.add(x);
    }

    public boolean borrowBook(Member czytelnik, Shelf... ksiazki){
        boolean exist;
        for(Shelf i: ksiazki){
            exist = false;
            for(Shelf j : stack){
                if(i == j) {
                    exist = true;
                    break;
                }
            }
            if(!exist){
                System.out.println("Request canceled due to: book doesn't belong to this library");
                return false;
            }
        }

        czytelnik.addRent(ksiazki);
        return true;
    }

    public void displayName(){
        System.out.println(this.name);
    }

}

class Member{
    private String lastName;
    private String name;
    private int borrowed_limit = 5;
    private Vector<Shelf> borrowed_books = new Vector<Shelf>(borrowed_limit);
    private int debt = 0;
    
    public Member(String name, String lastName){   
        this.name=name;
        this.lastName=lastName;
    }
    
    public boolean addRent(Shelf... args){
        if(args.length > borrowed_limit-borrowed_books.size()){
            System.out.println("Request canceled due to: exceeding the limit of rental books for member");
            return false;
        }
        int indeks = 0;
        for(Shelf i : args){
            if(!i.rent()) {
                for(Shelf j : args){
                    if(indeks == 0) break;
                    j.handBack();
                    indeks--;
                }
                System.out.println("Request canceled due to: exceeding the limit of rental books accessible");
                return false;
            }
            indeks++;
        }
        borrowed_books.addAll(Arrays.asList(args));
        System.out.println(borrowed_books+" "+borrowed_books.size());
        return true;
    }

    public void displayBorrowedBooks(){
        for(int i=0;i<borrowed_books.size();i++)
            System.out.print(borrowed_books.elementAt(i).getName()+" ");
        System.out.println();
    }

    public void displayFullName(){
        System.out.println(name + " " + lastName);
    }

    public void displayDebt(){
        System.out.println(debt);
    }
}

public class Main {
    public static void main(String[] args) {

        Library czytelnia1 = new Library("Czytelnia");
        BookShelf zemsta = new BookShelf("Zemsta", "Aleksander Fredro", 3);
        AudioShelf hobbit = new AudioShelf("Hobbit", "John Ronald Reuel Tolkien", 20);
        czytelnia1.addBook(zemsta);
        czytelnia1.displayName();


        Member czytelnik1 = new Member("Pawel", "Kowalski");
        zemsta.displayQuantity();
        czytelnia1.borrowBook(czytelnik1, zemsta);
        zemsta.displayQuantity();
        czytelnia1.borrowBook(czytelnik1, zemsta, zemsta, zemsta);
        zemsta.displayQuantity();
        czytelnia1.borrowBook(czytelnik1, zemsta);
        zemsta.displayQuantity();
        czytelnia1.borrowBook(czytelnik1);
        zemsta.displayQuantity();

        czytelnia1.borrowBook(czytelnik1, hobbit);
        czytelnik1.displayBorrowedBooks();
        czytelnia1.addBook(hobbit);
        hobbit.displayQuantity();
        czytelnia1.borrowBook(czytelnik1, hobbit, hobbit, hobbit, hobbit);
        hobbit.displayQuantity();
        czytelnia1.borrowBook(czytelnik1, hobbit);
        hobbit.displayQuantity();
        czytelnia1.borrowBook(czytelnik1, zemsta, hobbit);
        zemsta.displayQuantity();
        hobbit.displayQuantity();

        czytelnik1.displayBorrowedBooks();

        czytelnik1.displayFullName();
        czytelnik1.displayDebt();
    }
}
