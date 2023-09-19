package com.project.librarysystem;

import java.util.Date;

public class Book {

    private String title;
    private String author;
    private int bookId;
    private String subject;

    private String searchWord;
    public static int bookIdCounter = 0;
    private boolean isAvaliable ;

    public Book(String title , String author , String subject )
    {
        bookIdCounter++;

        this.bookId = bookIdCounter;

        this.author=author;
        this.title=title;
        this.subject=subject;
        this.isAvaliable=true;

        String[] splitWord = title.split(" ");

        for (String x: splitWord) {
            if(!x.equals(" "))
                this.searchWord+= x.toLowerCase();
        }

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public boolean isAvaliable() {
        return isAvaliable;
    }

    public void setAvaliable(boolean avaliable) {
        isAvaliable = avaliable;
    }

    public void printInfo()
    {
        System.out.println(title + "\t\t\t" + author + "\t\t\t" + subject);
    }

}