import java.util.*;
import java.util.regex.*;

interface Shelf{                            // deklarowanie interfejsu na asortyment biblioteki
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

    public boolean rent(){                  // zabieranie ksiazki z polki
        if(quantity <= 0){                  // sprawdzanie czy na polce sa jakies ksiazki do zabrania
            return false;
        }
        
        quantity--;
        return true;
    }

    public boolean handBack(){              // odkladanie ksiazki na polke
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

    public boolean rent(){                  // zabieranie ksiazki z polki
        if(quantity <= 0){                  // sprawdzanie czy na polce sa jakies ksiazki do zabrania
            return false;
        }
        
        quantity--;
        return true;
    }

    public boolean handBack(){              // odkladanie ksiazki na polke
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
    private Vector<Member> visitors = new Vector<Member>(0);
    
    public Library(String name){
        this.name=name;
    }

    public boolean registerMember(String name, String lastName, String id){
        for(Member i : visitors){
            if(i.getID().equals(id)){
                System.out.println("Error: There is already registered member using this ID");
                return false;
            }
        }

        try{
            visitors.add(new Member(name, lastName, id));
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean registerBook(String name, String author, int quantity, int type){
        switch(type){
            case 0:{
                stack.add(new BookShelf(name.toLowerCase(), author.toLowerCase(), quantity));
            break;
            }
            case 1:{
                stack.add(new AudioShelf(name.toLowerCase(), author.toLowerCase(), quantity));
            break;
            }
            default:{
                System.out.println("Error: Couldn't create shelf due to mismatching type");
                return false;
            }
            }

        return true;
    }
    

    public boolean borrowBook(String id, String... ksiazki){  // zlecenie na wypozyczenie ksiazek z polek dla czytelnika
        boolean exist;
        Shelf[] temp = new Shelf[ksiazki.length];
        int indeks = 0;
        for(String i: ksiazki){              // sprawdzanie czy dane ksiazki naleza do asortymentu biblioteki
            exist = false;
            for(Shelf j : stack){
                if(i.toLowerCase().equals(j.getName())) {
                    temp[indeks] = j;
                    indeks++;
                    exist = true;
                    break;
                }
            }
            if(!exist){
                System.out.println("Request canceled due to: book doesn't belong to this library");
                temp = null;
                System.gc();
                return false;
            }
        }

        for(Member k : visitors){
            if(k.getID().equals(id)){
                k.addRent(temp);         // przeslanie ksiazek do czytelnika
                temp = null;
                System.gc();
                return true;
            }
        }

        System.out.println("Error: Missing registered member with this ID");
        return false;
    }

    public void displayName(){
        System.out.println(this.name);
    }

}

class Member{
    private String lastName;
    private String name;
    private String id;
    private int borrowed_limit = 5;
    private Vector<Shelf> borrowed_books = new Vector<Shelf>(borrowed_limit);
    private int debt = 0;
    
    public Member(String name, String lastName, String id){ 
        if(!Pattern.matches("[0-9]{11}", id)){
            throw new IllegalArgumentException ("ID have to consists of 11 numbers");
        } 
        this.name=name;
        this.lastName=lastName;
        this.id = id;
    }
    
    public boolean addRent(Shelf[] args){      // wypozyczanie ksiazek przez czytelnika
        if(args.length > borrowed_limit-borrowed_books.size()){         // sprawdzanie czy nie przekracza limitu ksiazek do wypozyczenia na osobe
            System.out.println("Request canceled due to: exceeding the limit of rental books for member");
            return false;
        }
        int indeks = 0;
        for(Shelf i : args){                    // sprawdzanie czy na polkach sa ksiazka do wypozyczenia
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
        borrowed_books.addAll(Arrays.asList(args));     // rejestrowanie wypozyczenia ksiazek przez czytelnika
        System.out.println(borrowed_books+" "+borrowed_books.size());
        return true;
    }

    public String getID(){
        return id;
    }

    public void displayBorrowedBooks(){         // wyswietlenie listy wypozyczonych ksiazek przez czytelnika
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
        czytelnia1.displayName();

        czytelnia1.registerBook("TesT", "Ja", 1, 0);
        czytelnia1.registerMember("Man", "iek", "123456789");
        czytelnia1.borrowBook("123456789", "tEst");
        czytelnia1.registerMember("Man", "iek", "12345678910");
        czytelnia1.borrowBook("12345678910", "tEst");
        czytelnia1.registerMember("Man", "iek", "123b567891a");
        czytelnia1.borrowBook("123b567891a", "tEst");
    }
}
