//\\//\\//\\//\\//\\//\\//\\
//   Corniel Vorster       \\
//\\//\\//\\//\\//\\//\\//\\//

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Display extends Main {
    //creating lists for the objects of the classes
    static List<Book> books = new ArrayList<>();
    static List<Member> members = new ArrayList<>();//used to store the different member objects

    //\\//\\//\\//\\//\\//\\//\\
    //     Populating          \\
    //\\//\\//\\//\\//\\//\\//\\//
    //populating books
    public static void populateBooks() {
        //adding Book objects to the list books
        addBook("The Great Gatsby", "F. Scott Fitzgerald", "9780199536405", Boolean.TRUE);
        addBook("Terror", "Corniel Vorster", "978019954569", Boolean.TRUE);
        addBook("Life of Pi", "Yann Martel", "9788324010547", Boolean.TRUE);
        addBook("To Kill a Mockingbird", "Harper Lee", "9780060194994", Boolean.TRUE);
    }

    //populating members
    public static void populateMembers() {
        members.add(new Member("Corniel Vorster", "cornielvorster@gmail.com", new ArrayList<>()));
        members.add(new Member("Victoria Schmidt", "victoriaschmidt@gmail.com", new ArrayList<>()));
        members.add(new Member("Duncan  Madlambudzi", "duncan.madlambudzi@ctucareer.co.za", new ArrayList<>()));
    }

    public static void addBook(String title, String author, String ISBN, Boolean isAvialable) {
        books.add(new Book(title, author, ISBN, isAvialable));
    }

    //\\//\\//\\//\\//\\//\\//\\
    //     Displaying Books    \\
    //\\//\\//\\//\\//\\//\\//\\//
    //display all books
    public static void displayAllBooks() {
        System.out.println("All Books");
        System.out.println("========================");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println((i + 1) + ": " + book.displayBook());
        }
    }

    //display available book
    public static void displayAvailableBooks() {
        System.out.println("Available Books");
        System.out.println("========================");
        for (int i = 0; i < books.size(); i++) { //looping through the book list
            Book book = books.get(i);//getting the current index of the list
            if (book.getAvailable()) {//checking if the book available
                System.out.println((i + 1) + ". Title: " + book.getTitle() + " Author: " + book.getAuthor());
            }
        }
    }

    //display out books
    public static void displayBorrowedBooks() {
        System.out.println("Borrowed Books");
        System.out.println("========================");
        for (int i = 0; i < books.size(); i++) { //looping through the book list
            Book book = books.get(i); //getting the current index of the list
            if (!book.getAvailable()) { //checking if the book is out
                System.out.println((i + 1) + ". Title: " + book.getTitle() + " Author: " + book.getAuthor());
            }
        }
    }

    //\\//\\//\\//\\//\\//\\//\\
    //    Search for book      \\
    //\\//\\//\\//\\//\\//\\//\\//
    public static void searchBook() {
        System.out.println("========================");
        System.out.println("Search for a book");
        System.out.println("========================");
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.print("Enter a book name or author: ");
        String text = (myObj.nextLine()).toLowerCase();
        int found = 0; //everytime a result is found it adds a 1
        for (int i = 0; i < books.size(); i++) { //looping through the book list
            Book book = books.get(i);//getting the current index of the list
            if (Objects.equals((book.getTitle()).toLowerCase(), text) || Objects.equals((book.getAuthor()).toLowerCase(), text)) {
                System.out.println("Book number: " + (i + 1) + ". " + book.displayBook());
                found += 1;
            }
        }
        if (found == 0) { //if no book is found = 0
            System.out.println("No book has been found. ");
        }

    }

    //\\//\\//\\//\\//\\//\\//\\
    //    Borrow book          \\
    //\\//\\//\\//\\//\\//\\//\\//
    public static void borrowBook() {
        try {
            System.out.println("========================");
            System.out.println("Borrow Book");
            System.out.println("========================");
            displayAllBooks();
            int bookNo = getInput(1, books.size(), "Select which book you would like to rent: ");
            if (books.get(bookNo - 1).getAvailable() == Boolean.FALSE) { //checks if book is out
                throw new RuntimeException("Book is not available, please select a book where the available is true");
            } else {
                String memberEmail = getInput("Enter members email address: ");
                if (exists(memberEmail)) { //checks if the member exists
                    String ISBN = books.get(bookNo - 1).getISBN();
                    int memberNo = getMemberNo(memberEmail);
                    members.get(memberNo).addBook(ISBN); //add ISBN to the books list of the member
                    books.get(bookNo - 1).changeAvailable(Boolean.FALSE); //changes the availability of book
                    System.out.println("Book successfully rented! ");
                } else {
                    throw new RuntimeException(memberEmail + " is not recognised");
                }
            }

        } catch (Exception e) {
            System.out.println(e);
            borrowBook();
        }
    }

    public static Boolean exists(String email) {
        boolean isValid = Boolean.FALSE;
        for (Member member : members) {
            if (Objects.equals(member.getEmail(), email)) {
                isValid = Boolean.TRUE;
                break;
            }
        }
        return isValid;
    }

    public static int getMemberNo(String email) {
        int memberNo = 0;
        for (Member member : members) {
            if (Objects.equals(member.getEmail(), email)) {
                return memberNo;
            }
            memberNo += 1;
        }
        return memberNo;
    }

    //\\//\\//\\//\\//\\//\\//\\
    //    Return book          \\
    //\\//\\//\\//\\//\\//\\//\\//
    public static void returnBook() {
        try{
            System.out.println("========================");
            System.out.println("Return Book");
            System.out.println("========================");
            String email = getInput("Enter the members email who is returning the book: ");
            if (exists(email)) { //checking if member registered
                ArrayList<String> bookISBNs = members.get(getMemberNo(email)).getBooks(); //getting list of all the members books
                int i = 0;

                for (String bookISBN : bookISBNs) { //2d loop to find matching isbns
                    i += 1;
                    for (Book book : books) {
                        if (Objects.equals(bookISBN, book.getISBN())) {
                            System.out.println(i + ". " + book.getTitle() + " " + book.getAuthor()); //displaying the details of the book
                        }
                    }

                }

                int selection = getInput(1, bookISBNs.size(), "Which book would you like to return: "); //getting selection of which book to return

                int bookNo = 0; //used for Books arrayList
                int x = 0;
                for (Book book : books) { //looping through books to find which book no it is
                    if (Objects.equals(bookISBNs.get(selection - 1), book.getISBN())) {
                        bookNo = x;
                    }
                    x += 1;
                }
                books.get(bookNo).changeAvailable(true);
                bookISBNs.remove(selection - 1);
                System.out.println("Book successfully returned");
            } else {
                throw new RuntimeException(email + " is not recognised");
            }
        }catch(RuntimeException e){
            System.out.println(e.getMessage());
            returnBook();
        }
    }


    //\\//\\//\\//\\//\\//\\//\\
    //    Manage Books         \\
    //\\//\\//\\//\\//\\//\\//\\//
    public static void bookInput() {
        try {
            String title;
            String author;
            String ISBN; //12 digits

            System.out.println("Add a book");
            System.out.println("========================");

            //getting values for variables
            title = getInput("Title of book: ");
            author = getInput("Author of book: ");
            ISBN = getInput("ISBN of book: ");

            //checking isbn
            if (ISBN.length() < 12) {
                throw new Exception();
            }
            //checking if the ISBN is already in the library
            for (int i = 0; i < books.size(); i++) { //looping through the book list
                Book book = books.get(i);//getting the current index of the list
                if (Objects.equals(book.getISBN(), ISBN)) {//checking if there is already a book with same ISBN
                    throw new RuntimeException();
                }
            }
            addBook(title, author, ISBN, Boolean.TRUE);
            System.out.println("Book successfully added...");
        } catch (Exception e) {
            System.out.println("We ran into a problem....");
        } finally {
            manageBooks();
        }
    }

    //delete books
    public static void deleteBook() {
        try {
            System.out.println("========================");
            System.out.println("Delete a Book");
            System.out.println("========================");
            displayAllBooks();
            int book = getInput(1, books.size() + 1, "Which book would you like to delete: ");
            books.remove(book - 1);
            System.out.println("Book deleted.");

        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        } finally {
            manageBooks();
        }
    }


    public static void manageBooks() {
        manageBookScreen();
        int selection = getInput(1, 3, "Selection: ");
        switch (selection) {
            case 1:
                deleteBook(); //delete book
                break;
            case 2:
                bookInput(); //add book
                break;
            case 3:
                break;
        }
    }

    //\\//\\//\\//\\//\\//\\//\\
//    Manage Members       \\
//\\//\\//\\//\\//\\//\\//\\//
    public static void manageMembers() {
        manageMemberScreen();
        int selection = getInput(1, 4, "Selection: ");
        switch (selection) {
            case 1:
                deleteMember(); //delete member
                break;
            case 2:
                addMember(); //add member
                break;
            case 3:
                displayMembers();
                break;
            case 4:
                break;
        }
    }

    public static void displayMembers() {
        int x = 0; //counter
        for (Member member : members) {
            x += 1; //adding to count to use as an index
            System.out.println(x + ". " + member.toString());
        }
        manageMembers();
    }

    public static void addMember() {
        try {
            System.out.println("========================");
            System.out.println("Add a Member");
            System.out.println("========================");

//            getting values for name and email
            String name = getInput("Please enter full name: ");
            String email = getInput("Please enter email: ");

            //checking if email is valid
            String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"; //regex pattern for email
            Pattern pattern = Pattern.compile(regex); //changing the string into a pattern
            Matcher matcher = pattern.matcher(email);
            boolean isValid = matcher.find();
            if (isValid) {
                int count;
                for (Member member : members) { //looping through the members array list
                    if (Objects.equals(member.getEmail(), email)) { //checking if the email is already in system
                        throw new RuntimeException("Email already registered on system");
                    }
                }
                members.add(new Member(name, email, new ArrayList<>()));
                System.out.println("Successfully added: " + name + ", " + email + " to the system!");
            } else {
                throw new RuntimeException("Email is invalid ");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manageMembers();
        }
    }

    public static void deleteMember() {
        try {
            System.out.println("========================");
            System.out.println("Delete a Member");
            System.out.println("========================");
            String email = getInput("Enter email address of member you would like to delete: "); //getting email
            for (Member member : members) {
                if (Objects.equals(member.getEmail(), email)) { //
                    System.out.println("Are you sure you want to delete member: " + member.getName());
                    String confirm = getInput("Type 'yes' to confirm the deletion: ");
                    if (Objects.equals(confirm, "yes")) {
                        members.remove(member);
                        System.out.println("Member successfully removed! ");
                        break;
                    } else {
                        System.out.println("Member not removed. ");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        } finally {
            manageMembers();
        }
    }

    //\\//\\//\\//\\//\\//\\//\\
//        User Input       \\
//\\//\\//\\//\\//\\//\\//\\//
//get user input for navigation
    public static int getInput(int min, int max, String text) {
        try {
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.print(text);
            String num = myObj.nextLine();
            int result = Integer.parseInt(num);
            if (result < min || result > max) { //checking if number is out range
                throw new RuntimeException("The number you selected is out of range");
            } else {
                return result;
            }
        } catch (RuntimeException e) {
            System.out.println(e);
        }
        return 0;
    }

    public static String getInput(String text) {
        try {
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.print(text);
            String result = myObj.nextLine();
            return result;
        } catch (RuntimeException e) {
            System.out.println(e);
        }
        return "";
    }


    //\\//\\//\\//\\//\\//\\//\\
//        Screens          \\
//\\//\\//\\//\\//\\//\\//\\//
//welcome screen
    public static void welcome() {
        System.out.println("Welcome Librarian, to the Library!");
        System.out.println("========================");
        System.out.println("1. See all books");
        System.out.println("2. See available books");
        System.out.println("3. See borrowed books");
        System.out.println("4. Search for book");
        System.out.println("5. Borrow book");
        System.out.println("6. Return book");
        System.out.println("7. Manage books");
        System.out.println("8. Manage members");
        System.out.println("9. Exit");
        System.out.println("========================");
    }

    //manage books screen
    public static void manageBookScreen() {
        System.out.println("Manage books");
        System.out.println("========================");
        System.out.println("1. Delete Book");
        System.out.println("2. Add a book");
        System.out.println("3. Back to main screen");
        System.out.println("========================");
    }

    //manage members screen
    public static void manageMemberScreen() {
        System.out.println("Manage members");
        System.out.println("========================");
        System.out.println("1. Delete member");
        System.out.println("2. Add member");
        System.out.println("3. Display members");
        System.out.println("4. Back to main screen");
        System.out.println("========================");
    }

    //\\//\\//\\//\\//\\//\\//\\
//        Main Display     \\
//\\//\\//\\//\\//\\//\\//\\//
    public void mainDisplay() {
        populateBooks();
        populateMembers();
        mainLoop:
        while (true) {
            welcome();//display welcome screen
            int selection = getInput(1, 9, "Selection: ");
            switch (selection) {
                case 1:
                    displayAllBooks();
                    break;
                case 2:
                    displayAvailableBooks();
                    break;
                case 3:
                    displayBorrowedBooks(); //display out books
                    break;
                case 4:
                    searchBook(); //search for book
                    break;
                case 5:
                    borrowBook(); //borrow book
                    break;
                case 6:
                    returnBook(); //return book
                    break;
                case 7:
                    manageBooks(); //manage book
                    break;
                case 8:
                    manageMembers(); //manage members
                    break;
                case 9:
                    System.out.println("Thank you for using the system!");
                    break mainLoop;
            }
        }
    }
}





