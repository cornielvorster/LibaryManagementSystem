//\\//\\//\\//\\//\\//\\//\\
//   Corniel Vorster       \\
//\\//\\//\\//\\//\\//\\//\\//

public class Book extends Main {
    //declaring fields
    private String title;
    private String author;
    private String ISBN;
    private boolean isAvailable; //if book is available True if not False

    //constructor
    public Book(String title, String author, String ISBN, Boolean isAvailable){
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvailable = isAvailable;
    }

    public void changeAvailable(boolean x){
        if (this.isAvailable == x) { //checking if the parameter is = to the value of field
            if (this.isAvailable) { //checking if book is returned
                System.out.println("The book is already returned");
            } else if (!this.isAvailable) { //checking if book is out
                System.out.println("Unfortunately the book is out, please choose another book.");
            }
        }else {
            this.isAvailable = x;
        }

    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public String getISBN(){
        return ISBN;
    }

    public Boolean getAvailable(){
        return isAvailable;
    }


    //display book
    public String displayBook(){
        String text =  (" Title: " + this.title + " Author: " + this.author + " ISBN: " + this.ISBN + " Available: " + this.isAvailable);
        return text;
    }


}

