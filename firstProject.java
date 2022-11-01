class student {
    String name;
    int age;
    
    public student(String _name, int _age){
        name = _name;
        age = _age;
    }
}

class station {
    int number;
    student user;
    
    public station(int _number, student _user){
        number = _number;
        user = _user;
    }
}

class main {
    public static void main(String[] args) {
        student student1 = new student("Anna", 21);
        station station1 = new station(1, student1);
        System.out.println(station1.user.name);
    }
}
