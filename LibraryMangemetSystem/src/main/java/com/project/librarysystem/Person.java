package com.project.librarysystem;

import java.util.ArrayList;

public class Person {
    private int personId;
    private String password;
    private String type;
    private String firstName;
    private String lastName;
    private String address;
    private int cellPhone;
    private String email;
    private boolean isBlocked;

    private ArrayList<Book> booksRented;
    private static int personidcounter = 0;

    public Person (String password, String type, String firstName, String lastName,
                   String address, int cellPhone, String email) {

        personidcounter++;
        personId = personidcounter;

        this.password = password;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.cellPhone = cellPhone;
        this.email = email;
        this.isBlocked = false;

        this.booksRented = new ArrayList<>();

    }

    public int getPersonId() {
        return personId;
    }

    public ArrayList<Book> getBooksRented() {
        return booksRented;
    }

    public void setBooksRented(ArrayList<Book> booksRented) {
        this.booksRented = booksRented;
    }

    public static int getPersonidcounter() {
        return personidcounter;
    }

    public static void setPersonidcounter(int personidcounter) {
        Person.personidcounter = personidcounter;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(int cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public static int getPersonIdCounter() {
        return personidcounter;
    }

    public static void setPersonIdCounter(int personIdCounter) {
        Person.personidcounter = personidcounter;
    }

    public void printinfo(){
        System.out.println("-----------------------------------------");
        System.out.println("\nThe details are: \n");
        System.out.println("ID: " + personId);
        System.out.println("First Name: " + firstName + lastName);
        System.out.println("Address: " + address);
        System.out.println("Phone No: " + cellPhone + "\n");
    }

//    public void search_user(){
//        if(UserName.equals(FirstName.concat(LastName))){
//            System.out.println("Here are all the details about the person you are looking for: ");
//        }
//    }

    
}