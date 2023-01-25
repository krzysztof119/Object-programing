package pl.poznan.put.mainClasses;
import pl.poznan.put.interf.Shelf;
import pl.poznan.put.auxiliaryStructure.Helper;
import java.util.*;

public class Library{
    private String libraryName;
    private Vector<Shelf> stack = new Vector<Shelf>(0);
    private Vector<Member> visitors = new Vector<Member>(0);
    
    public Library(String libraryName){
        this.libraryName=libraryName;
    }

    public boolean registerMember(String memberName, String lastName, String id){
        for(Member i : visitors){
            if(i.getID().equals(id)){
                System.out.println("Error: There is already registered member using this ID");
                return false;
            }
        }

        try{
            visitors.add(new Member(memberName, lastName, id));
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        return true;
    }

    private Shelf existBook(String bookName, String author, int type){
        switch(type){
            case 0:
                for(Shelf arg : stack){
                    if(arg.getClass().getName().equals("pl.poznan.put.mainClasses.BookShelf") && arg.getName().equals(bookName.toLowerCase()) && arg.getAuthor().equals(author.toLowerCase()))
                        return arg;
                }
            break;
            case 1:
                for(Shelf arg : stack){
                    if(arg.getClass().getName().equals("pl.poznan.put.mainClasses.AudioShelf") && arg.getName().equals(bookName.toLowerCase()) && arg.getAuthor().equals(author.toLowerCase()))
                        return arg;
                    }
            break;
            default:
            System.out.println("Couldn't perform operation");
            throw new IllegalArgumentException("Wrong type");
        }
        return null;
    }

    public boolean registerBook(String bookName, String author, int quantity, int type){
        Shelf existingBook = null;
        try{
        existingBook = existBook(bookName, author, type);
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw e;
        }
        if(existingBook != null){
            System.out.println("These books are already existing in this library, would you rather instead increase their amount?\n1. yes\n2. no");
            Scanner scanner = new Scanner(System.in);
            int choice = 0;
            while(true){
                try{
                    choice = scanner.nextInt();
                    scanner.nextLine();
                }catch (Exception e){
                    System.out.println("please type a numberical value");
                }
                if(choice >= 1 && choice <= 2)
                    break;
                System.out.println("Selected option was out of range. please try again.");
            }
            if(choice == 2)
                return false;
            for(int i = quantity; i > 0; i--)
                existingBook.handBack();
            return true;
        }
        switch(type){
            case 0:{
                stack.add(new BookShelf(bookName.toLowerCase(), author.toLowerCase(), quantity));
            break;
            }
            case 1:{
                stack.add(new AudioShelf(bookName.toLowerCase(), author.toLowerCase(), quantity));
            break;
            }
            default:{
                System.out.println("Error: Couldn't create shelf due to mismatching type");
                return false;
            }
            }

        return true;
    }
    
    private Helper checkCorrectness(String id, int number, String[] bookName, String[] author, int[] type){
        Shelf[] array = new Shelf[number];
        for(int j = 0; j < number; j++){
            try{
                array[j] = existBook(bookName[j], author[j], type[j]);
            }catch(Exception e){
                System.out.println("Couldn't find this book in library");
                throw e;
            }
            if(array[j] == null){
                System.out.println("Couldn't find this book in library");
                return null;
            }
        }
        for(Member k : visitors){
            if(k.getID().equals(id)){           // sprawdzanie czy odwiedzajacy jest zarejestrowany w bibliotece
                Helper helper = new Helper(array, k);
                return helper;
            }
        }

        System.out.println("Error: Couldn't find registered member in this library with this ID");
        return null;
    }

    public boolean borrowBook(String id, int number, String[] bookName, String[] author, int[] type){  // zlecenie na wypozyczenie ksiazek z polek dla czytelnika
        Helper helper = checkCorrectness(id, number, bookName, author, type);
        if(helper == null)
            return false;
        try{
            helper.member.rentBook(helper.shelf);   // przeslanie ksiazek do czytelnika
        }catch(Exception e){
            throw e;
        }
        helper = null;
        System.gc();
        return true;
    }

    public boolean restoreBook(String id, int number, String[] bookName, String[] author, int[] type){   // zlecenie na odlozenie ksiazek od czytelnika na polki
        Helper helper = checkCorrectness(id, number, bookName, author, type);
        if(helper == null)
            return false;
        try{
            helper.member.subtractBook(helper.shelf);         // odbieranie ksiazek od czytelnika
        }catch(Exception e){
            throw e;
        }
        helper = null;
        System.gc();
        return true;
    }

    public boolean displayBorrowedList(String id){
        for(Member k : visitors){
            if(k.getID().equals(id)){
                k.displayBorrowedBooks();
                return true;
            }
        }
        return false;
    }

    public String getName(){
        return libraryName;
    }
}