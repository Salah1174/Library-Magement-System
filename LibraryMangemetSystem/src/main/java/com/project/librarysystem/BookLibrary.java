package com.project.librarysystem;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class BookLibrary {
    private String name;
    private int libraryId;
    public static ArrayList<Person> members;
    public static ArrayList<Person> librarians;
    private ArrayList<Book> booksInLibrary ;

    private ArrayList<Book> rentedBooks ;

    private ArrayList<BookOrder> bookOrderLibrary ;

    private int rentDays;
    //    private ArrayList<Loan> loans ;
    private static int libraryCount = 0 ;

     BookLibrary(String name, int rentDays)
    {
        this.name = name;

        members = new ArrayList();
        librarians = new ArrayList();

        booksInLibrary = new ArrayList();
        rentedBooks = new ArrayList<>();

        libraryCount++;
        libraryId = libraryCount;
        this.rentDays = rentDays ;
        this.bookOrderLibrary = new ArrayList<>();
    }

    public void addBooksInLibrary(String title ,String author ,String subject){
        Book b = new Book(title,author,subject);
        booksInLibrary.add(b);
    }

    public boolean removeBooksFromLibrary(int id)
    {

        int index = -1 ;

        for(int i = 0;i<booksInLibrary.size();i++)
        {
            if(id == booksInLibrary.get(i).getBookId())
            {
                index = i ;
            }

        }

        if(index != -1)
        {
            booksInLibrary.remove(index);
            System.out.println("Book was successfully removed!!");
            return  true;
        }
        else
        {
            System.out.println("Book not found");
            return  false;
        }

    }

    public void addLibrarian(String password, String type, String firstName, String lastName,
                             String address, int cellPhone, String email)
    {
        librarian l = new librarian(password, type, firstName, lastName, address, cellPhone, email, libraryId);
        librarians.add(l);
    }

    public void addReader(String password, String type, String firstName, String lastName, String address, int cellPhone, String email)
    {
        Reader r = new Reader(password, type, firstName, lastName, address, cellPhone, email);
        members.add(r);
    }

    public boolean removeReader(int id)
    {

        int index = -1 ;

        for(int i = 0;i<members.size();i++)
        {
            if(id == members.get(i).getPersonId())
            {
                index = i ;
            }

        }

        if(index != -1)
        {
            members.remove(index);
            System.out.println("Member was removed !!");
            return  true;
        }
        else
        {
            System.out.println("Member was not found");
            return  false ;
        }

    }

    public boolean removeLibrarian(int id)
    {

        int index = -1 ;

        for(int i = 0;i<librarians.size();i++)
        {
            if(id == librarians.get(i).getPersonId())
            {
                index = i ;
            }

        }

        if(index != -1)
        {
            librarians.remove(index);
            System.out.println("Librarian was removed !!");
            return true;
        }
        else
        {
            System.out.println("Librarian was not found");
            return  false;
        }

    }

    public List<String> searchForBooksByTitle(String title) {

        String[] words = title.split(" ");

        String allWords = "" ;

        for (String x: words) {
            if(!x.equals(" "))
                allWords+= x.toLowerCase();
        }

        ArrayList<String> result = new ArrayList<>();

        for(int i =0 ; i<booksInLibrary.size();i++){

            Book b = booksInLibrary.get(i);

            if(b.getSearchWord().contains(allWords))
            {
                result.add(b.getTitle()+" - "+b.getAuthor()+" - "+b.getBookId());
            }

        }

        return result;

    }

    public List<String> searchForBooksByAuthor(String author) {

        ArrayList<String> result = new ArrayList<>();

        for(int i =0 ; i<booksInLibrary.size();i++){

            Book b = booksInLibrary.get(i);

            if(b.getAuthor().toLowerCase().contains(author.toLowerCase()))
            {
                result.add(b.getTitle()+" - "+b.getAuthor()+" - "+b.getBookId());
            }

        }

        return result;

    }

    public List<String> searchForBooksById(int id) {

        ArrayList<String> result = new ArrayList<>();

        for(int i =0 ; i<booksInLibrary.size();i++){

            Book b = booksInLibrary.get(i);

            if(b.getBookId() == id)
            {
                result.add(b.getTitle()+" - "+b.getAuthor()+" - "+b.getBookId());
            }

        }

        return result;

    }

    public List<String> searchReaderId(int id)
    {

        ArrayList<String> result = new ArrayList<>();

        for(int i=0 ; i<members.size();i++)
        {
            if(members.get(i).getPersonId() == id )
                result.add(members.get(i).getPersonId()+" - "+members.get(i).getFirstName()+" - "+members.get(i).getLastName()+" - "+members.get(i).getType());

        }

        return result;
    }

    public List<String> searchReaderName(String firstName)
    {

        ArrayList<String> result = new ArrayList<>();

        for(int i=0 ; i<members.size();i++)
        {
            if(members.get(i).getFirstName().toLowerCase().contains(firstName.toLowerCase()) )
                result.add(members.get(i).getPersonId()+" - "+members.get(i).getFirstName()+" - "+members.get(i).getLastName()+" - "+members.get(i).getType());

        }

        return result;
    }
    public List<String> searchLibrarianId(int id)
    {

        ArrayList<String> result = new ArrayList<>();

        for(int i=0 ; i<librarians.size();i++)
        {
            if(librarians.get(i).getPersonId() == id )
                result.add(librarians.get(i).getPersonId()+" - "+librarians.get(i).getFirstName()+" - "+librarians.get(i).getLastName()+" - "+librarians.get(i).getType());

        }

        return result;
    }

    public List<String> searchLibrarianName(String firstName)
    {

        ArrayList<String> result = new ArrayList<>();

        for(int i=0 ; i<librarians.size();i++)
        {
            if(librarians.get(i).getFirstName().toLowerCase().contains(firstName.toLowerCase()) )
                result.add(librarians.get(i).getPersonId()+" - "+librarians.get(i).getFirstName()+" - "+librarians.get(i).getLastName()+" - "+librarians.get(i).getType());

        }

        return result;
    }

    public boolean addBookOrder(int readerId, int bookId)
    {
        Book book = null ;
        Person reader = null ;

        for (Book b: booksInLibrary) {
            if(b.getBookId() == bookId )
            {
                if(b.isAvaliable())
                {
                    book = b;
                    break;
                }
            }
        }

        for (Person b: members) {
            if(b.getPersonId() == readerId )
            {
                reader = (Reader) b;
                break;
            }
        }

        if(reader==null)
        {
            for (Person b: librarians) {
                if(b.getPersonId() == readerId )
                {
                    reader = (librarian) b;
                    break;
                }
            }
        }


        if(book != null && reader != null && !reader.isBlocked())
        {
            // Book was Found
            BookOrder newOrder = new BookOrder(reader,book, LocalDateTime.now().plusDays(rentDays) , LocalDateTime.now());
            bookOrderLibrary.add(newOrder);

            reader.getBooksRented().add(book);
            rentedBooks.add(book);
            book.setAvaliable(false);

            return true;
        }

        return false ;
    }

    public boolean removeBookOrder(int readerId, int bookId)
    {

        int index = -1 ;

        for (int i = 0; i < bookOrderLibrary.size(); i++) {

            if(bookOrderLibrary.get(i).getReader().getPersonId() == readerId
                    && bookOrderLibrary.get(i).getBook().getBookId() == bookId)
            {
                // found the entry
                index = i ;
                break;
            }

        }

        if(index!=-1)
        {
            if(bookOrderLibrary.get(index).getReturnDate().isBefore(LocalDateTime.now()))
            {
                bookOrderLibrary.get(index).getReader().setBlocked(true);
            }

            int bookIndex = -1 ;

            for (int i = 0; i < bookOrderLibrary.get(index).getReader().getBooksRented().size(); i++) {
                if(bookOrderLibrary.get(index).getReader().getBooksRented().get(i).getBookId() == bookId)
                {
                    bookIndex = i ;
                    break;
                }
            }

            if(bookIndex!= -1)
            {
                bookOrderLibrary.get(index).getReader().getBooksRented().remove(bookIndex);
            }

            bookOrderLibrary.get(index).getBook().setAvaliable(true);

            bookOrderLibrary.remove(index);
            return true;
        }

        return false ;
    }

    public ArrayList<Book> getBooksInLibrary() {
        return booksInLibrary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    public static ArrayList<Person> getMembers() {
        return members;
    }

    public static void setMembers(ArrayList<Person> members) {
        BookLibrary.members = members;
    }

    public static ArrayList<Person> getLibrarians() {
        return librarians;
    }

    public static void setLibrarians(ArrayList<Person> librarians) {
        BookLibrary.librarians = librarians;
    }

    public void setBooksInLibrary(ArrayList<Book> booksInLibrary) {
        this.booksInLibrary = booksInLibrary;
    }

    public ArrayList<Book> getRentedBooks() {
        return rentedBooks;
    }

    public void setRentedBooks(ArrayList<Book> rentedBooks) {
        this.rentedBooks = rentedBooks;
    }

    public ArrayList<BookOrder> getBookOrderLibrary() {
        return bookOrderLibrary;
    }

    public void setBookOrderLibrary(ArrayList<BookOrder> bookOrderLibrary) {
        this.bookOrderLibrary = bookOrderLibrary;
    }

    public int getRentDays() {
        return rentDays;
    }

    public void setRentDays(int rentDays) {
        this.rentDays = rentDays;
    }

    public static int getLibraryCount() {
        return libraryCount;
    }

    public static void setLibraryCount(int libraryCount) {
        BookLibrary.libraryCount = libraryCount;
    }

    public void blockUser(int userId){

        for (int i=0;i< members.size();i++){
            if(members.get(i).getPersonId() == userId) {
                members.get(i).setBlocked(true)  ;
                break;
            }
        }

        for (int i=0;i< librarians.size();i++){
            if(librarians.get(i).getPersonId() == userId) {
                librarians.get(i).setBlocked(true)  ;
                break;
            }
        }
        System.out.println("Late users have been blocked successfully ");
    }

    public void removeBlockUser(int userId){

        for (int i=0;i< members.size();i++){
            if(members.get(i).getPersonId() == userId) {
                members.get(i).setBlocked(false)  ;
                break;
            }
        }

        for (int i=0;i< librarians.size();i++){
            if(librarians.get(i).getPersonId() == userId) {
                librarians.get(i).setBlocked(false)  ;
                break;
            }
        }
    }

}

