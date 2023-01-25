package pl.poznan.put.auxiliaryStructure;
import pl.poznan.put.interf.Shelf;
import pl.poznan.put.mainClasses.Member;

public class Helper{
    public Shelf[] shelf;
    public Member member;

    public Helper(Shelf[] shelf, Member member){
        this.shelf = shelf;
        this.member = member;
    }
}