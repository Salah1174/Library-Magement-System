package com.project.librarysystem;
import java.util.ArrayList;

public class Reader extends Person {
    private int readerNo;
    private static int readerNoCounter=0;

    public Reader(String password, String type, String firstName, String lastName, String address, int cellPhone, String email) {
        super(password, type, firstName, lastName, address, cellPhone, email);
        readerNoCounter++;
        readerNo=readerNoCounter;
    }

    @Override
    public void printinfo()
    {
        super.printinfo();
        System.out.println("Reader Number: " + readerNo);
    }

    public int getReaderNo() {
        return readerNo;
    }

    public void setReaderNo(int readerNo) {
        this.readerNo = readerNo;
    }
}
