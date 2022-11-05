public class main {
    public static void main(String[] args) {
        Comedy book1 = new Comedy("Title", 0);
        book1.describe();
    }
}

class Comedy implements Book{
    private String title;
    private int pages;
    
    public Comedy(String title, int pages){
        this.title = title;
        this.pages = pages;
    }
    
    public boolean decribe(){
        System.out.println("Comedy is type of book which is focusing on make reader laugh");
        return true;
    }
}

class Fantasy implements Book{
    public boolean decribe(){
        System.out.println("Fantasy is type of book which is focusing on things that doesn't exist in real life");
        return true;
    }
}

interface Book{
    abstract boolean describe();
}
