import java.util.*;

interface IBook{
    boolean rent();
    boolean handBack();
    String getName();
}

class PaperBook implements IBook{
    private String name;
    private String author;
    private int quantity;

    public PaperBook(String name, String author, int quantity){
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
}

class Library{
    private String name;
    private Vector<IBook> stack = new Vector<IBook>(0);
    
    public Library(String name){
        this.name=name;
    }
    
    public boolean addBook(IBook x){
        return stack.add(x);
    }

    public void displayName(){
        System.out.print(this.stack+"\n");
    }

}

class Member{
    private String lastName;
    private String name;
    private int borrowed_limit = 5;
    private Vector<IBook> borrowed_books = new Vector<IBook>(borrowed_limit);
    private int debt = 0;
    
    public Member(String name, String lastName){   
        this.name=name;
        this.lastName=lastName;
    }
    
    public boolean addRent(IBook... args){
        if(args.length > borrowed_limit-borrowed_books.size()){
            System.out.print("Request canceled due to exceeding the limit of rental books possible"+"\n");
            return false;
        }
        for(int i = 0; i < args.length; i++){
            if(!args[i].rent()) {
                for(int j = i-1; j >= 0; j--){
                    args[j].handBack();
                }
                System.out.print("Za malo egzemplarzy"+"\n");
                return false;
            }
        }
        borrowed_books.addAll(Arrays.asList(args));
        System.out.print(borrowed_books+" "+borrowed_books.size()+"\n");
        return true;
    }

    public void displayBorrowedBooks(){
        for(int i=0;i<borrowed_books.size();i++)
            System.out.print(borrowed_books.elementAt(i).getName()+" ");
        System.out.println();
    }

    public void displayFullName(){
        System.out.print(name + " " + lastName + "\n");
    }

    public void displayDebt(){
        System.out.print(debt+"\n");
    }
}

public class Main {
    public static void main(String[] args) {

        Library czytelnia1 = new Library("Czytelnia");
        PaperBook zemsta = new PaperBook("Zemsta", "Aleksander Fredro", 3);
        czytelnia1.addBook(zemsta);
        czytelnia1.displayName();


        Member czytelnik1 = new Member("Pawel", "Kowalski");
        czytelnik1.addRent(zemsta);czytelnik1.addRent(zemsta, zemsta, zemsta);czytelnik1.addRent(zemsta);
        czytelnik1.displayBorrowedBooks();

        czytelnik1.displayFullName();czytelnik1.displayDebt();
    }
}
