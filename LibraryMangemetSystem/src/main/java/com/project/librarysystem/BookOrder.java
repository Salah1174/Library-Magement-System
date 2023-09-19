package com.project.librarysystem;

import java.time.LocalDateTime;
import java.util.Date;

public class BookOrder {
    private Person reader;
    private Book book;
    private LocalDateTime rentDate;
    private LocalDateTime returnDate;

    public BookOrder(Person reader, Book book, LocalDateTime returnDate, LocalDateTime rentDate ) {
        this.reader = reader;
        this.book = book;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDateTime getRentDate() {
        return rentDate;
    }

    public void setRentDate(LocalDateTime rentDate) {
        this.rentDate = rentDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public Person getReader() {
        return reader;
    }

    public Book getBook() {
        return book;
    }


    public void print()
    {
        System.out.println(book.getTitle()+"\t\t\t\t"+reader.getFirstName()+"\t\t\t\t"+rentDate+"\t\t\t\t"+returnDate+"\n");
    }
}
