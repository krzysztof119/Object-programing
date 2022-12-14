import java.util.*;
interface X{
    
}

class Library{
    private String name;
    private String lastName;
    
    public Library(String name, String lastName){
        this.name=name;
        this.lastName=lastName;
    }
    
    public void displayName(){
        System.out.print(this.name+"\n");
    }
    public void displayLastName(){
        System.out.print(this.lastName+"\n");
    }
}

class Member extends Library{
    private int borrowed_limit = 5;
    private Vector<String> borrowed_books = new Vector<String>(borrowed_limit);
    private int debt = 0;
    
    public Member(String name, String lastname){
        super(name, lastname);
    }
    
    public void addrent(String... args){
        if(args.length > borrowed_limit-borrowed_books.size()){
            System.out.print("Request canceled due to exceeding the limit of rental books possible"+"\n");
            return;
        }
        for(String arg : args){
            borrowed_books.add(arg);
            borrowed_limit += 1;
        }
        System.out.print(borrowed_books+" "+borrowed_books.size()+"\n");
    }

    public void displayDebt(){
        System.out.print(this.debt+"\n");
    }
}

public class Main {
    public static void main(String[] args) {
        Member czytelnik1 = new Member("Pawel", "Kowalski");
        czytelnik1.addrent("Nic", "Test", "1", "2", "3", "4");czytelnik1.addrent();

        czytelnik1.displayName();czytelnik1.displayLastName();czytelnik1.displayDebt();
    }
}
