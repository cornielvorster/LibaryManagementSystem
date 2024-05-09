//\\//\\//\\//\\//\\//\\//\\
//   Corniel Vorster       \\
//\\//\\//\\//\\//\\//\\//\\//

import java.util.ArrayList;

public class Member extends Main{
//    declaring fields
    private String name;
    private String email;
    private static ArrayList<String> books;

//    constructor
    public Member(String name, String email, ArrayList<String> books ){
        this.name = name;
        this.email = email;
        this.books = books;
    }

    public String getEmail(){
        return email;
    }

    public String getName(){
        return name;
    }

    public String toString(){
        return (name + ' ' + email);
    }

    public ArrayList<String> getBooks(){
        return books;
    }

    public void addBook(String ISBN){
        books.add(ISBN);
    }

    public void displayBooks(){
        System.out.println(books);
    }




}
