import java.util.*;
import java.util.regex.*;

interface Shelf{                            // deklarowanie interfejsu na asortyment biblioteki
    boolean rent();
    boolean handBack();
    String getName();
    String getAuthor();
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

    public String getAuthor(){
        return author;
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

    public String getAuthor(){
        return author;
    }

    public void displayQuantity(){
        System.out.println(this.quantity);
    }
}

class Helper{
    public Shelf[] shelf;
    public Member member;

    public Helper(Shelf[] shelf, Member member){
        this.shelf = shelf;
        this.member = member;
    }
}

class Library{
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
            System.out.println(e);
            return false;
        }
        return true;
    }

    private Shelf existBook(String bookName, String author, int type){
        switch(type){
            case 0:
                for(Shelf arg : stack){
                    if(arg.getClass().getName().equals("BookShelf") && arg.getName().equals(bookName.toLowerCase()) && arg.getAuthor().equals(author.toLowerCase()))
                        return arg;
                }
            break;
            case 1:
                for(Shelf arg : stack){
                    if(arg.getClass().getName().equals("AudioShelf") && arg.getName().equals(bookName.toLowerCase()) && arg.getAuthor().equals(author.toLowerCase()))
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
            return false;
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
        Scanner scanner = new Scanner(System.in);
        Shelf[] array = new Shelf[number];
        for(int j = 0; j < number; j++){
            try{
                array[j] = existBook(bookName[j], author[j], type[j]);
            }catch(Exception e){
                System.out.println("Couldn't find this book in library");
                scanner.nextLine();
                return null;
            }
            if(array[j] == null){
                System.out.println("Couldn't find this book in library");
                scanner.nextLine();
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
        scanner.nextLine();
        return null;
    }

    public boolean borrowBook(String id, int number, String[] bookName, String[] author, int[] type){  // zlecenie na wypozyczenie ksiazek z polek dla czytelnika
        Helper helper = checkCorrectness(id, number, bookName, author, type);
        if(helper == null)
            return false;

        helper.member.rentBook(helper.shelf);                // przeslanie ksiazek do czytelnika
        helper = null;
        System.gc();
        return true;
    }

    public boolean restoreBook(String id, int number, String[] bookName, String[] author, int[] type){   // zlecenie na odlozenie ksiazek od czytelnika na polki
        Helper helper = checkCorrectness(id, number, bookName, author, type);
        if(helper == null)
            return false;

        helper.member.subtractBook(helper.shelf);         // odbieranie ksiazek od czytelnika
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
    
    public boolean rentBook(Shelf[] args){      // wypozyczanie ksiazek przez czytelnika
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
        if(blunder)
            return false;
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
    public static Scanner scanner = new Scanner(System.in);
    public static int choice, type, i;
    public static boolean success;
    public static String libraryName, title, author, name, lastname, id;
    public static String[] arrtitle, arrauthor;
    public static int[] arrtype;
    public static Vector<Library> libraryList = new Vector<Library>(0); 
    public static Library selectedLibrary = null;
    public static void main(String[] args) {
        start();
    }
    static void start(){ 
        while(true){
            success = false;
            System.out.println("Welcome to Library managing system");
            System.out.println("You have access to the following actions (Select actions by input correct digit next to it, program is taking first passed number): ");
            System.out.println("1. Register new library");
            System.out.println("2. Select library");
            System.out.println("3. Add books to selected library");
            System.out.println("4. Register new reader account");
            System.out.println("5. Borrow books from library");
            System.out.println("6. Return books to library");
            System.out.println("7. Return list of borrowed books");
            System.out.println("8. Exit program");
            choice = -1;
            while(!success){
                while(true){
                    try{
                        choice = scanner.nextInt();
                        scanner.nextLine();
                        break;
                    }catch (Exception e){
                        scanner.nextLine();
                        System.out.println("You have to insert integer");
                    }
                }
                success = true;
                switch(choice){
                    case 1:
                    registerLibrary();
                    break;

                    case 2:
                    if(libraryList.size() == 0){
                        System.out.print("There is no existing Library to select, please register one first. Confirm by pressing enter");
                        scanner.nextLine();
                    }
                    else
                        selectLibrary();
                    break;

                    case 3:
                    if(selectedLibrary == null){
                        System.out.print("There is no selected library, please do this first. Confirm by pressing enter");
                        scanner.nextLine();
                    }
                    else
                        registerBook();
                    break;

                    case 4:
                        if(selectedLibrary == null){
                            System.out.print("There is no selected library, please do this first. Confirm by pressing enter");
                            scanner.nextLine();
                        }
                        else
                            registerMember();
                    break;

                    case 5:
                    if(selectedLibrary == null){
                        System.out.print("There is no selected library, please do this first. Confirm by pressing enter");
                        scanner.nextLine();
                    }
                    else
                        borrow();
                    break;

                    case 6:
                    if(selectedLibrary == null){
                        System.out.print("There is no selected library, please do this first. Confirm by pressing enter");
                        scanner.nextLine();
                    }
                    else
                        handBack();
                    break;

                    case 7:
                    if(selectedLibrary == null){
                        System.out.print("There is no selected library, please do this first. Confirm by pressing enter");
                        scanner.nextLine();
                    }
                    else
                        borrowedList();
                    break;

                    case 8:
                    return;

                    default:
                    System.out.println("Inserted value doesn't represent option to select");
                    success = false;
                }
            }
        }
    }
    
    static boolean existLibrary(){
        for(Library arg : libraryList){
            if(arg.getName() == libraryName)
                return true;
        }
        return false;
    }

    static boolean registerLibrary(){
            
            System.out.println("Type unique name you want to, or type word exit if you want to return.");
            libraryName = scanner.nextLine();
            if(libraryName.toLowerCase().equals("exit"))
                return false;
            if(existLibrary()){
                System.out.printf("There is registered library with this name. Confirm by pressing enter.");
                scanner.nextLine();
                return false;
            }
            try{
            libraryList.add(new Library(libraryName));
            }catch(Exception e){
                System.out.printf("Couldnt register new library, confirm by pressing enter");
                scanner.nextLine();
                return false;
            }
        System.out.printf("Registered new library, confirm by pressing enter");
        scanner.nextLine();
        return true;
    }

    static boolean showLibraries(){
        i = 1;
        for(Library arg : libraryList){
            System.out.printf(i+". ");
            System.out.println(arg.getName());
            i++;
        }
        return true;
    }

    static boolean selectLibrary(){
        System.out.println("Select by number one of the libraries");
        showLibraries();
        while(true){
            try{
                choice = scanner.nextInt();
                scanner.nextLine();
            }catch (Exception e){
                scanner.nextLine();
                System.out.println("please type a numberical value");
            }
            if(choice >= 1 && choice <= libraryList.size()){
                selectedLibrary = libraryList.elementAt(--choice);
                System.out.printf("Selected library: " + selectedLibrary.getName() + ". Confirm by pressing enter.");
                scanner.nextLine();
                break;
            }
            System.out.println("Inserted value doesn't represent option to select");
        }
        return true;
    }

    static boolean registerBook(){
        System.out.println("Select type of what you want to register.");
        System.out.println("1. Paper Book");
        System.out.println("2. Audio Book");
        while(true){
            try{
                type = scanner.nextInt();
                scanner.nextLine();
            }catch (Exception e){
                scanner.nextLine();
                System.out.println("please type a numberical value");
            }
            if(type >= 1 && type <= 2)
                break;
            System.out.println("Selected option was out of range. please try again.");
        }
        System.out.println("Type the Title");
        title = scanner.nextLine();
        System.out.println("Type the Author");
        author = scanner.nextLine();
        System.out.println("Type Quantity");
        while(true){
            try{
                i = scanner.nextInt();
                scanner.nextLine();
                break;
            }catch (Exception e){
                scanner.nextLine();
                System.out.println("You have to insert integer");
            }
        }
        System.out.println("Are you sure, you want to add book \""+title+"\" written by "+author+" to the library?");
        System.out.println("1. yes");
        System.out.println("2. no");
        while(true){
            try{
                choice = scanner.nextInt();
                scanner.nextLine();
            }catch (Exception e){
                scanner.nextLine();
                System.out.println("please type a numberical value");
            }
            if(choice >= 1 && choice <= 2)
                break;
            System.out.println("Selected option was out of range. please try again.");
        }
        if(choice == 2)
            return false;
        selectedLibrary.registerBook(title, author, i, --type);
        return true;
    }

    static boolean registerMember(){
        System.out.println("Type the Name");
        name = scanner.nextLine();
        System.out.println("Type the Last name");
        lastname = scanner.nextLine();
        System.out.println("Type the id (11 digit number)");
        while(true){
            try{
                id = Long.toString(scanner.nextLong());
                scanner.nextLine();
                break;
            }catch (Exception e){
                scanner.nextLine();
                System.out.println("You have to insert integer");
            }
        }
        System.out.println("Are you sure, you want to add member " + name + " " + lastname + " to the library?\n1. yes\n2. no");
        while(true){
            try{
                choice = scanner.nextInt();
                scanner.nextLine();
            }catch (Exception e){
                scanner.nextLine();
                System.out.println("please type a numberical value");
            }
            if(choice >= 1 && choice <= 2)
                break;
            System.out.println("Selected option was out of range. please try again.");
        }
        if(choice == 2)
            return false;
        selectedLibrary.registerMember(name, lastname, id);
        System.out.println("Returning");
        scanner.nextLine();
        return true;
    }

    static boolean borrow(){
        System.out.println("Type the id (11 digit number)");
        while(true){
            try{
                id = Long.toString(scanner.nextLong());
                scanner.nextLine();
                break;
            }catch (Exception e){
                scanner.nextLine();
                System.out.println("You have to insert integer");
            }
        }
        System.out.println("How many books do you want to borrow (max 5).");
        while(true){
            try{
                i = scanner.nextInt();
                scanner.nextLine();
            }catch (Exception e){
                scanner.nextLine();
                System.out.println("please type a numerical value");
            }
            if(i >= 1 && i <= 5)
                break;
            System.out.println("Selected option was out of range. please try again.");
        }
        arrtitle = new String[i];
        arrauthor = new String[i];
        arrtype = new int[i];
        for(int j = 0; j<i; j++){
            System.out.println("Select type of what you want to register.");
            System.out.println("1. Paper Book");
            System.out.println("2. Audio Book");
            while(true){
                try{
                    type = scanner.nextInt();
                    scanner.nextLine();
                }catch (Exception e){
                    scanner.nextLine();
                    System.out.println("please type a numberical value");
                }
                if(type >= 1 && type <= 2)
                    break;
                System.out.println("Selected option was out of range. please try again.");
            }
            arrtype[j] = --type;
            System.out.println("Type the Title of book");
            arrtitle[j] = scanner.nextLine();
            System.out.println("Type the Author of book");
            arrauthor[j] = scanner.nextLine();
        }
        System.out.println("Are you sure, you want to borrow book(s):");
        for(int j=0; j<i; j++){
            System.out.println(arrtitle[j] + " written by "+ arrauthor[j]);
        }
        System.out.println("from the library?\n1. yes\n2. no");
        while(true){
            try{
                choice = scanner.nextInt();
                scanner.nextLine();
            }catch (Exception e){
                scanner.nextLine();
                System.out.println("please type a numberical value");
            }
            if(choice >= 1 && choice <= 2)
                break;
            System.out.println("Selected option was out of range. please try again.");
        }
        if(choice == 2)
            return false;
        selectedLibrary.borrowBook(id, i, arrtitle, arrauthor, arrtype);
        return true;
    }

    static boolean handBack(){
        System.out.println("Type the id (11 digit number)");
        while(true){
            try{
                id = Long.toString(scanner.nextLong());
                scanner.nextLine();
                break;
            }catch (Exception e){
                scanner.nextLine();
                System.out.println("You have to insert integer");
            }
        }
        System.out.println("How many books do you want to borrow (max 5).");
        while(true){
            try{
                i = scanner.nextInt();
                scanner.nextLine();
            }catch (Exception e){
                scanner.nextLine();
                System.out.println("please type a numerical value");
            }
            if(i >= 1 && i <= 5)
                break;
            System.out.println("Selected option was out of range. please try again.");
        }
        arrtitle = new String[i];
        arrauthor = new String[i];
        arrtype = new int[i];
        for(int j = 0; j<i; j++){
            System.out.println("Select type of what you want to return.");
            System.out.println("1. Paper Book");
            System.out.println("2. Audio Book");
            while(true){
                try{
                    type = scanner.nextInt();
                    scanner.nextLine();
                }catch (Exception e){
                    scanner.nextLine();
                    System.out.println("please type a numberical value");
                }
                if(type >= 1 && type <= 2)
                    break;
                System.out.println("Selected option was out of range. please try again.");
            }
            arrtype[j] = --type;
            System.out.println("Type the Title of book");
            arrtitle[j] = scanner.nextLine();
            System.out.println("Type the Author of book");
            arrauthor[j] = scanner.nextLine();
        }
        System.out.println("Are you sure, you want to return book(s):");
        for(int j=0; j<i; j++){
            System.out.println(arrtitle[j] + " written by "+ arrauthor[j]);
        }
        System.out.println("to the library?\n1. yes\n2. no");
        while(true){
            try{
                choice = scanner.nextInt();
                scanner.nextLine();
            }catch (Exception e){
                scanner.nextLine();
                System.out.println("please type a numberical value");
            }
            if(choice >= 1 && choice <= 2)
                break;
            System.out.println("Selected option was out of range. please try again.");
        }
        if(choice == 2)
            return false;
        selectedLibrary.restoreBook(id, i, arrtitle, arrauthor, arrtype);
        return true;
    }

    static boolean borrowedList(){
        System.out.println("Type the id (11 digit number)");
        while(true){
            try{
                id = Long.toString(scanner.nextLong());
                scanner.nextLine();
                break;
            }catch (Exception e){
                scanner.nextLine();
                System.out.println("You have to insert integer");
            }
        }
        selectedLibrary.displayBorrowedList(id);
        scanner.nextLine();
        return true;
    }
}
