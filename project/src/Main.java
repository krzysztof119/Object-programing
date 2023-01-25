import pl.poznan.put.mainClasses.*;
import java.util.*;

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
                        scanner.close();
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
            if(arg.getName().toLowerCase().equals(libraryName.toLowerCase()))
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
        System.out.println("Type Quantity (above 0)");
        while(true){
            try{
                i = scanner.nextInt();
                scanner.nextLine();
            }catch (Exception e){
                scanner.nextLine();
                System.out.println("You have to insert integer");
            }
            if(i >= 1)
                break;
            System.out.println("Selected option was out of range. please try again.");
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
        try{
            selectedLibrary.registerBook(title, author, i, --type);
            System.out.println("Book successfully registered. Confirm by pressing enter.");
            scanner.nextLine();
        }catch(Exception e){
            System.out.println("Book couldn't be registered. Confirm by pressing enter.");
            scanner.nextLine();
            return false;
        }
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
        try{
            selectedLibrary.registerMember(name, lastname, id);
            System.out.println("Member successfully registered. Confirm by pressing enter.");
            scanner.nextLine();
        }catch(Exception e){
            System.out.println("Member couldn't be registered. Confirm by pressing enter.");
            scanner.nextLine();
            return false;
        }
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
        try{
            selectedLibrary.borrowBook(id, i, arrtitle, arrauthor, arrtype);
            System.out.println("Books are successfully borrowed. Confirm by pressing enter.");
            scanner.nextLine();
        }catch(Exception e){
            System.out.println("Books couldn't be borrowed. Confirm by pressing enter.");
            scanner.nextLine();
            return false;
        }
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
        try{
            selectedLibrary.restoreBook(id, i, arrtitle, arrauthor, arrtype);
            System.out.println("Books are successfully returned. Confirm by pressing enter.");
            scanner.nextLine();
        }catch(Exception e){
            System.out.println("Books couldn't be returned. Confirm by pressing enter.");
            scanner.nextLine();
            return false;
        }
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
        try{
            selectedLibrary.displayBorrowedList(id);
            System.out.println("List successfully displayed. Confirm by pressing enter.");
            scanner.nextLine();
        }catch(Exception e){
            System.out.println("List couldn't be displayed. Confirm by pressing enter.");
            scanner.nextLine();
            return false;
        }
        return true;
    }
}