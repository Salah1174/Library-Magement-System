package com.project.librarysystem;

import javafx.application.Application;

import java.util.ArrayList;

public class librarian  extends Person {
    private int librarianNo;
    private int libraryId;
    private static int librarianNocounter=0;


    public librarian(String password, String type, String firstName, String lastName,
                     String address, int cellPhone, String email, int libraryId )
    {
        super(password,type,firstName,lastName,address,cellPhone,email);

        librarianNocounter++;
        librarianNo=librarianNocounter;
        this.libraryId = libraryId;


    }

    public int getLibrarianNo() {
        return librarianNo;
    }

    public void setLibrarianNo(int librarianNo) {
        this.librarianNo = librarianNo;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    public static int getLibrarianNocounter() {
        return librarianNocounter;
    }

    public static void setLibrarianNocounter(int librarianNocounter) {
        librarian.librarianNocounter = librarianNocounter;
    }


    public void printinfo(){
        super.printinfo();
        System.out.println("Librarian Number: " + librarianNo+"  Library ID:  "+ libraryId);
    }
}