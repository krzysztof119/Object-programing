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
        System.out.println(this.name+"\n");
    }
    public void displayLastName(){
        System.out.println(this.lastName+"\n");
    }
}

class Member extends Library{
    private String borrowed_books = "";
    private int debt = 0;
    
    public Member(String name, String lastname){
        super(name, lastname);
    }
    
    public void displayDebt(){
        System.out.println(this.debt+"\n");
    }
}

public class Main {
    public static void main(String[] args) {
        Member czytelnik1 = new Member("Pawel", "Kowalski");
        czytelnik1.displayName();czytelnik1.displayLastName();czytelnik1.displayDebt();
    }
}
