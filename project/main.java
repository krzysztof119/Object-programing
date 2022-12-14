import java.util.*;

interface IBook{
    boolean rent(Member X);
}

class Library{
    private String name;
    
    public Library(String name){
        this.name=name;
        
    }
    
    public void displayName(){
        System.out.print(this.name+"\n");
    }

}

class Member{
    private String lastName;
    private String name;
    private int borrowed_limit = 5;
    private Vector<String> borrowed_books = new Vector<String>(borrowed_limit);
    private int debt = 0;
    
    public Member(String name, String lastname){   
        this.name=name;
        this.lastName=lastName;
    }
    
    public boolean AddRent(String... args){
        if(args.length > borrowed_limit-borrowed_books.size()){
            System.out.print("Request canceled due to exceeding the limit of rental books possible"+"\n");
            return false;
        }
        borrowed_books.addAll(Arrays.asList(args));
        System.out.print(borrowed_books+" "+borrowed_books.size()+"\n");
        return true;
    }

    public void DisplayLastName(){
        System.out.print(this.lastName+"\n");
    }

    public void DisplayDebt(){
        System.out.print(this.debt+"\n");
    }
}

public class Main {
    public static void main(String[] args) {
        Member czytelnik1 = new Member("Pawel", "Kowalski");
        czytelnik1.AddRent("0", "1", "2", "3", "4", "5");czytelnik1.AddRent("0");

        czytelnik1.DisplayLastName();czytelnik1.DisplayDebt();
    }
}
