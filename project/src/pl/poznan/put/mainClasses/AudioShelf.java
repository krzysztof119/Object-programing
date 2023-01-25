package pl.poznan.put.mainClasses;
import pl.poznan.put.interf.Shelf;

public class AudioShelf implements Shelf{

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