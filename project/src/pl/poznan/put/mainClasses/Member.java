package pl.poznan.put.mainClasses;
import pl.poznan.put.interf.Shelf;
import java.util.*;
import java.util.regex.*;

public class Member{
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
    
    public boolean rentBook(Shelf[] args){      // wypozyczanie ksiazek przez czytelnika
        if(args.length > borrowed_limit-borrowed_books.size()){         // sprawdzanie czy nie przekracza limitu ksiazek do wypozyczenia na osobe
            System.out.println("Request canceled due to: exceeding the limit of rental books for member");
            throw new IllegalArgumentException ("Request canceled due to: exceeding the limit of rental books for member");
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
                throw new IllegalArgumentException ("Request canceled due to: exceeding the limit of rental books accessible");
            }
            indeks++;
        }
        borrowed_books.addAll(Arrays.asList(args));     // rejestrowanie wypozyczenia ksiazek przez czytelnika
        return true;
    }

    public boolean subtractBook(Shelf[] args){
        boolean exist, blunder = false;
        for(Shelf i : args){
            exist = false;
            if(borrowed_books.contains(i)){
                borrowed_books.removeElement(i);
                exist = true;
            }
            if(exist == false){
                System.out.println("Book: "+ i.getName() +" doesn't belong to this library");
                blunder = true;
            }
        }
        if(blunder){
            System.out.println("Request canceled due to: Missing book to return.");
            throw new IllegalArgumentException ("Request canceled due to: Missing book to return.");
        }
        return true;
    }

    public String getID(){
        return id;
    }

    public void displayBorrowedBooks(){         // wyswietlenie listy wypozyczonych ksiazek przez czytelnika
        for(int i=0;i<borrowed_books.size();i++){
        if(borrowed_books.elementAt(i).getClass().getName().equals("pl.poznan.put.mainClasses.BookShelf"))
            System.out.println(borrowed_books.elementAt(i).getName()+" Book (Paper)");
        else
            System.out.println(borrowed_books.elementAt(i).getName()+" Audio Book");
        }
    }

    public void displayFullName(){
        System.out.println(name + " " + lastName);
    }

    public void displayDebt(){
        System.out.println(debt);
    }
}