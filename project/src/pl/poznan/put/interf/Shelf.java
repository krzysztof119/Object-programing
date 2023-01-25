package pl.poznan.put.interf;

public interface Shelf{                            // deklarowanie interfejsu na asortyment biblioteki
    boolean rent();
    boolean handBack();
    String getName();
    String getAuthor();
}