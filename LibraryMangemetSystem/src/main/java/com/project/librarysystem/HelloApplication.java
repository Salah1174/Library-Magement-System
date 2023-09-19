package com.project.librarysystem;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;


public class HelloApplication extends Application {
    
    // Reference to the currently open window
    private Stage currentWindow;
    private static int userId;
    private static String userType;
    private static BookLibrary library;
    private ListView<String> resultListView = new ListView<>();
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    
    
        library = new BookLibrary("Geem", 3);
    
        // create some books
        Book book1 = new Book("Harry Potter 1", "JK Rowling", "Adventure");
        Book book2 = new Book("Harry Potter 2", "JK Rowling", "Adventure");
        Book book3 = new Book("Harry Potter 3", "JK Rowling", "Adventure");
    
        // Add Some books to it
        library.getBooksInLibrary().add(book1);
        library.getBooksInLibrary().add(book2);
        library.getBooksInLibrary().add(book3);
    
        // add the librarian and reader to use them
        library.addLibrarian("librarian", "librarian", "Gasser", "S", "Cairo", 0102345677, "gasser.s@gmail.com");
        library.addReader("reader", "Reader", "Mohamed", "Soliman", "Cairo", 0102345677, "moh.salah@gmail.com");

        library.addReader("reader", "Reader", "Mohamed 2", "Soliman 2", "Cairo", 0102345677, "moh.salah@gmail.com");
        library.blockUser(3);

        primaryStage.setTitle("Login");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label sceneTitle = new Label("Login");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("ID:");
        grid.add(userName, 0, 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);
        PasswordField passwordBox = new PasswordField();
        grid.add(passwordBox, 1, 2);

        Button btn = new Button("Sign in");
        btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        grid.add(btn, 1, 4);

        btn.setOnAction(e -> {
            String userid = userTextField.getText().toString();
            String password = passwordBox.getText().toString();

            if (userTextField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error!", "Please enter your ID");
                return;
            }
            if (passwordBox.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error!", "Please enter a password");
                return;
            }

            boolean found = false;

            for (Person reader : library.getMembers()) {
                if (reader.getPassword().equals(password) && reader.getPersonId() == Integer.parseInt(userid)) {
                    infoBox("Login Successful!", null, "Success");
                    found = true;
                    userId = Integer.parseInt(userid);
                    userType = "reader";
                    showReaderOptions(primaryStage);
                }
            }

            for (Person reader : library.getLibrarians()) {
                if (reader.getPassword().equals(password) && reader.getPersonId() == Integer.parseInt(userid)) {
                    infoBox("WELCOME Admin", null, "Success");
                    userId = Integer.parseInt(userid);
                    userType = "librarian";
                    found = true;
                    showAdminOptions(primaryStage);
                }
            }

            if (!found)
                infoBox("Please enter correct ID and Password", null, "Failed");

        });

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);

        // Add a window event listener to close the current window when another window is opened
        primaryStage.setOnShowing(event -> {
            if (currentWindow != null) {
                currentWindow.close();
            }
        });

        primaryStage.show();
        
        // Store the current window reference
        currentWindow = primaryStage;
    }
    
    static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
    
    static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
    
    //book page Done --- >
    public void showBookPage() {
        Stage bookStage = new Stage();
        bookStage.setTitle("Book Management");
        // Create a GridPane as the root layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
    
        // Create controls
    
        ArrayList<String> books = new ArrayList<>();
    
        for (Book b : library.getBooksInLibrary()) {
            String bookName = b.getTitle() + " - " + b.getBookId();
            books.add(bookName);
        }
        ObservableList<String> items = FXCollections.observableArrayList(books);
        Label bookListLabel = new Label("Book List:");
        ListView<String> bookListView = new ListView<>(items);

        if(!userType.toLowerCase().equals("reader"))
        {
            Button addButton = new Button("Add Book");
            Button removeButton = new Button("Remove Book");
            gridPane.add(addButton, 1, 1);
            gridPane.add(removeButton, 1, 2);

            // Add event handlers
            addButton.setOnAction(e -> addBook());
            removeButton.setOnAction(e -> {
                removeBook(bookListView);
                ArrayList<String> booksX = new ArrayList<>();

                for (Book b : library.getBooksInLibrary()) {
                    String bookName = b.getTitle() + " - " + b.getBookId();
                    booksX.add(bookName);
                }

                ObservableList<String> itemsX = FXCollections.observableArrayList(booksX);

                bookListView.setItems(itemsX);
            });
        }

    
        // Add controls to the grid pane
        gridPane.add(bookListLabel, 0, 0);
        gridPane.add(bookListView, 0, 1);

    
        // Create a scene and set it on the stage
        Scene scene = new Scene(gridPane, 500, 550);
        bookStage.setScene(scene);
    

    
        bookStage.show();
    
        // Store the current window reference
        Stage primaryStage = (Stage) bookListView.getScene().getWindow();
        primaryStage.setScene(scene);
    
        // Add a window event listener to close the current window when another window is opened
        primaryStage.setOnShowing(event -> {
            if (bookStage != null && bookStage.isShowing()) {
                bookStage.close();
            }
        });
    
        primaryStage.show();
    
        // Store the current window reference
        Stage currentWindow = primaryStage;
    }
    
    
    //search Done -->
    private void showSearchPage() {
        Stage userStage = new Stage();
        userStage.setTitle("library Management");
    
        // Add a window event listener to close the current window when another window is opened
        userStage.setOnShowing(event -> {
            if (currentWindow != null) {
                currentWindow.close();
            }
        });
        // Create a GridPane as the root layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
    
        // Create controls for searching books
        Label searchBooksLabel = new Label("Search Books:");
        searchBooksLabel.setFont(Font.font("Arial", 16));
        Label booksSearchTypeLabel = new Label("Search By:");
        ChoiceBox<String> booksSearchTypeChoiceBox = new ChoiceBox<>();
        booksSearchTypeChoiceBox.getItems().addAll("Title", "Author", "ISBN");
        booksSearchTypeChoiceBox.getSelectionModel().selectFirst();
        TextField booksSearchTermTextField = new TextField();
        Button booksSearchButton = new Button("Search");
    
        // Add controls for searching books to the grid pane
        gridPane.add(searchBooksLabel, 0, 0);
        gridPane.add(booksSearchTypeLabel, 0, 1);
        gridPane.add(booksSearchTypeChoiceBox, 1, 1);
        gridPane.add(booksSearchTermTextField, 0, 2);
        gridPane.add(booksSearchButton, 1, 2);
    
        // Create controls for searching members
        Label searchMembersLabel = new Label("Search Members:");
        searchMembersLabel.setFont(Font.font("Arial", 16));
        Label membersSearchTypeLabel = new Label("Search By:");
        ChoiceBox<String> membersSearchTypeChoiceBox = new ChoiceBox<>();
        membersSearchTypeChoiceBox.getItems().addAll("Name", "ID");
        membersSearchTypeChoiceBox.getSelectionModel().selectFirst();
        TextField membersSearchTermTextField = new TextField();
        Button membersSearchButton = new Button("Search");
    
        // Add controls for searching members to the grid pane
        gridPane.add(searchMembersLabel, 0, 3);
        gridPane.add(membersSearchTypeLabel, 0, 4);
        gridPane.add(membersSearchTypeChoiceBox, 1, 4);
        gridPane.add(membersSearchTermTextField, 0, 5);
        gridPane.add(membersSearchButton, 1, 5);
    
        // Create a label to display the search results
        Label resultLabel = new Label("Search Results:");
        resultLabel.setFont(Font.font("Arial", 16));
    
        // Create a ListView to display the search results
        resultListView = new ListView<>();
        resultListView.setPrefHeight(200);
    
        // Add the result label and list view to a VBox
        VBox resultVBox = new VBox(10, resultLabel, resultListView);
        resultVBox.setAlignment(Pos.CENTER);
    
        // Add the result VBox to the grid pane
        gridPane.add(resultVBox, 0, 6, 2, 1);
    
        // Add event handlers for searching books and members
        // Event handler for searching books
        booksSearchButton.setOnAction(e -> {
            String searchType = booksSearchTypeChoiceBox.getValue();
            String searchTerm = booksSearchTermTextField.getText().trim();
            if (!searchTerm.isEmpty()) {
                // Perform the search and display the results
                ObservableList<String> searchResults = searchBooks(searchType, searchTerm);
                resultListView.setItems(searchResults);
            }
        });
    
        // Event handler for searching members
        membersSearchButton.setOnAction(e -> {
            String searchType = membersSearchTypeChoiceBox.getValue();
            String searchTerm = membersSearchTermTextField.getText().trim();
            if (!searchTerm.isEmpty()) {
                // Perform the search and display the results
                ObservableList<String> searchResults = searchMembers(searchType, searchTerm);
                resultListView.setItems(searchResults);
            }
        });
    
        // Create a scene and set it on the stage
        Scene scene = new Scene(gridPane, 500, 550);
        userStage.setScene(scene);
        userStage.show();
    }
    
    private ObservableList<String> searchBooks(String searchType, String searchTerm) {
        
        ObservableList<String> searchResults = FXCollections.observableArrayList();
        ;
        
        if (searchType.equals("Title")) {
            searchResults.addAll(library.searchForBooksByTitle(searchTerm));
        } else if (searchType.equals("Author")) {
            searchResults.addAll(library.searchForBooksByAuthor(searchTerm));
        } else if (searchType.equals("ISBN")) {
            searchResults.addAll(library.searchForBooksById(Integer.parseInt(searchTerm)));
        }
        
        return searchResults;
    }
    
    private ObservableList<String> searchMembers(String searchType, String searchTerm) {
        
        ObservableList<String> searchResults = FXCollections.observableArrayList();
        ;
        
        if (searchType.equals("Name")) {
            searchResults.addAll(library.searchReaderName(searchTerm));
            searchResults.addAll(library.searchLibrarianName(searchTerm));
        } else if (searchType.equals("Id")) {
            searchResults.addAll(library.searchLibrarianId(Integer.parseInt(searchTerm)));
            searchResults.addAll(library.searchReaderId(Integer.parseInt(searchTerm)));
        }
        
        
        return searchResults;
    }
    
    //rent Done -->
    private void showRentPage() {
        Stage userStage = new Stage();
        userStage.setTitle("library Management");

        // Add a window event listener to close the current window when another window is opened
        userStage.setOnShowing(event -> {
            if (currentWindow != null) {
                currentWindow.show();
            }
        });

        // Create a GridPane as the root layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ArrayList<String> books = new ArrayList<>();

        for (Book b: library.getBooksInLibrary()) {

            if(b.isAvaliable())
            {
                String bookName = b.getTitle()+" - "+b.getBookId();
                books.add(bookName);
            }
        }

        ArrayList<String> booksRented = new ArrayList<>();

        for (Book b: library.getBooksInLibrary()) {

            if(!b.isAvaliable())
            {
                String bookName = b.getTitle()+" - "+b.getBookId();
                booksRented.add(bookName);
            }
        }

        ObservableList<String> items =FXCollections.observableArrayList (
                books);

        ObservableList<String> itemsR =FXCollections.observableArrayList (
                booksRented);

        // Create controls
        Label availableBooksLabel = new Label("Available Books:");
        ListView<String> availableBooksListView = new ListView<>(items);
        Label rentedBooksLabel = new Label("Rented Books:");
        ListView<String> rentedBooksListView = new ListView<>(itemsR);
        Button rentButton = new Button("Rent Book");
        TextField rentId = new TextField("User Id");

        if(!userType.toLowerCase().equals("reader"))
        {
            Button returnButton = new Button("Return Book");
            gridPane.add(returnButton, 1, 2);

            returnButton.setOnAction(e -> {
                returnBook(rentedBooksListView,  rentId.getText().toString());

                ArrayList<String> booksRentedX = new ArrayList<>();

                for (Book b: library.getBooksInLibrary()) {

                    if(!b.isAvaliable())
                    {
                        String bookName = b.getTitle()+" - "+b.getBookId();
                        booksRentedX.add(bookName);
                    }
                }

                ObservableList<String> itemsRX =FXCollections.observableArrayList (
                        booksRentedX);

                rentedBooksListView.setItems(itemsRX);
                rentedBooksListView.refresh();

                ArrayList<String> booksX = new ArrayList<>();

                for (Book b: library.getBooksInLibrary()) {

                    if(b.isAvaliable())
                    {
                        String bookName = b.getTitle()+" - "+b.getBookId();
                        booksX.add(bookName);
                    }
                }

                ObservableList<String> itemsX =FXCollections.observableArrayList (
                        booksX);

                availableBooksListView.setItems(itemsX);
                availableBooksListView.refresh();

            });
        }


        // Add controls to the grid pane
        gridPane.add(availableBooksLabel, 0, 0);
        gridPane.add(availableBooksListView, 0, 1);
        gridPane.add(rentedBooksLabel, 1, 0);
        gridPane.add(rentedBooksListView, 1, 1);
        gridPane.add(rentButton, 0, 2);
        gridPane.add(rentId, 1, 3);


        // Add event handlers
        rentButton.setOnAction(e ->
        {
            rentBook(availableBooksListView,  rentId.getText().toString());
            ArrayList<String> booksX = new ArrayList<>();

            for (Book b: library.getBooksInLibrary()) {

                if(b.isAvaliable())
                {
                    String bookName = b.getTitle()+" - "+b.getBookId();
                    booksX.add(bookName);
                }
            }

            ObservableList<String> itemsX =FXCollections.observableArrayList (
                    booksX);

            availableBooksListView.setItems(itemsX);
            availableBooksListView.refresh();

            ArrayList<String> booksRentedX = new ArrayList<>();

            for (Book b: library.getBooksInLibrary()) {

                if(!b.isAvaliable())
                {
                    String bookName = b.getTitle()+" - "+b.getBookId();
                    booksRentedX.add(bookName);
                }
            }

            ObservableList<String> itemsRX =FXCollections.observableArrayList (
                    booksRentedX);

            rentedBooksListView.setItems(itemsRX);
            rentedBooksListView.refresh();

        });

        // Create a scene and set it on the stage
        Scene scene = new Scene(gridPane, 500, 550);
        userStage.setScene(scene);
        userStage.show();
    }

    private void rentBook(ListView<String> availableBooksListView, String rentId) {
        String selectedBook = availableBooksListView.getSelectionModel().getSelectedItem();

        String id = "";
        // get the id
        for (int i = selectedBook.length() -1; i >= 0; i--) {

            if(selectedBook.charAt(i) != ' ')
            {
                id = selectedBook.charAt(i) + "" ;
            }
            else
            {
                break;
            }
        }

        library.addBookOrder(Integer.parseInt(rentId),Integer.parseInt(id));

    }

    private void returnBook(ListView<String> rentedBooksListView, String rentId) {
        String selectedBook = rentedBooksListView.getSelectionModel().getSelectedItem();

        String id = "";
        // get the id
        for (int i = selectedBook.length() -1; i >= 0; i--) {

            if(selectedBook.charAt(i) != ' ')
            {
                id = selectedBook.charAt(i) + "" ;
            }
            else
            {
                break;
            }
        }

        library.removeBookOrder(Integer.parseInt(rentId),Integer.parseInt(id));
    }
    
    public void showAdminOptions(Stage primaryStage) {
        Stage optionsStage = new Stage();
        optionsStage.setTitle("Library Management System");
        
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        
        Button bookButton = new Button("Add / Remove Book");
        Button userButton = new Button("Add / Remove User");
        Button searchButton = new Button("Search for Books OR Members");
        Button rentButton = new Button("Rent a Book");
        Button blockUserButton = new Button("Block User");
        
        // Set button styles
        bookButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        userButton.setStyle("-fx-background-color: #03A9F4; -fx-text-fill: white;");
        searchButton.setStyle("-fx-background-color: #009688; -fx-text-fill: white;");
        rentButton.setStyle("-fx-background-color: #673AB7; -fx-text-fill: white;");
        blockUserButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
        
        // Set button actions
        bookButton.setOnAction(e -> showBookPage());
       userButton.setOnAction(e -> showUserPage());
        searchButton.setOnAction(e -> showSearchPage());
        rentButton.setOnAction(e -> showRentPage());
        blockUserButton.setOnAction(e -> showBlockPage());
        
        root.getChildren().addAll(bookButton, userButton, searchButton, rentButton, blockUserButton);
        
        Scene scene = new Scene(root, 350, 450);
        optionsStage.setScene(scene);
        optionsStage.setTitle("Library Management System");
        optionsStage.show();
        
        primaryStage.setOnShowing(event -> {
            if (currentWindow != null) {
                currentWindow.show();
            }
        });
    }

    // optionsss
    public void showReaderOptions(Stage primaryStage) {
        Stage optionsStage = new Stage();
        optionsStage.setTitle("Library Management System");
    
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
    
        Button bookButton = new Button("Add / Remove Book to your list");
        Button searchButton = new Button("Search for Books OR users");
        Button rentButton = new Button("Rent a Book");
    
        // Set button styles
        bookButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        searchButton.setStyle("-fx-background-color: #009688; -fx-text-fill: white;");
        rentButton.setStyle("-fx-background-color: #673AB7; -fx-text-fill: white;");
    
        // Set button actions
        bookButton.setOnAction(e -> showBookPage());
        searchButton.setOnAction(e -> showSearchPage());
        rentButton.setOnAction(e -> showRentPage());
    
        root.getChildren().addAll(bookButton, searchButton, rentButton);
    
        Scene scene = new Scene(root, 350, 450);
        optionsStage.setScene(scene);
        optionsStage.setTitle("Library Management System");
        optionsStage.show();
        primaryStage.setOnShowing(event -> {
            if (currentWindow != null) {
                currentWindow.show();
            }
        }); }
    
    private void addBook() {
        Stage bookStage = new Stage();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER); //position
        grid.setHgap(10);//gap
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        // screen title
        Label sceneTitle = new Label("ADD BOOK");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label title = new Label("Book Title:");
        grid.add(title, 0, 1);
        TextField titleField = new TextField();
        grid.add(titleField, 1, 1);

        Label subject = new Label("Book Subject:");
        grid.add(subject, 0, 2);
        TextField subjectField = new TextField();
        grid.add(subjectField, 1, 2);

        Label author = new Label("Book Author:");
        grid.add(author, 0, 3);
        TextField authorField = new TextField();
        grid.add(authorField, 1, 3);

        Button btn = new Button("Add Book");
        grid.add(btn, 1, 5);

        btn.setOnAction(e -> {
            String titleString = titleField.getText().toString();
            String subjectString = subjectField.getText().toString();
            String authorString = authorField.getText().toString();

            library.addBooksInLibrary(titleString,authorString,subjectString);
            showBookPage();

        });

        // Create a scene and set it on the stage
        Scene scene = new Scene(grid, 500, 550);
        bookStage.setScene(scene);

        bookStage.show();

        // Store the current window reference
        currentWindow = bookStage;


    }
    
    private void removeBook(ListView<String> bookListView) {
        String selectedBook = bookListView.getSelectionModel().getSelectedItem();

        String id = "";
        // get the id
        for (int i = selectedBook.length() -1; i >= 0; i--) {

            if(selectedBook.charAt(i) != ' ')
            {
                id = selectedBook.charAt(i) + "" ;
            }
            else
            {
                break;
            }
        }

        library.removeBooksFromLibrary(Integer.parseInt(id));

    }
    
    
//    user page Done -->

    private void showUserPage() {
        Stage userStage = new Stage();
        userStage.setTitle("library Management");

        // Add a window event listener to close the current window when another window is opened
        userStage.setOnShowing(event -> {
            if (currentWindow != null) {
                currentWindow.show();
            }
        });

        // Create a GridPane as the root layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        ArrayList<String> users = new ArrayList<>();

        for (Person x : library.getMembers()) {

            String u =  x.getFirstName()+" - "+x.getLastName()+" - "+x.getType().toLowerCase()+ " - "+x.getPersonId();
            users.add(u);
        }

        for (Person b: library.getLibrarians()) {
            String bookName = b.getFirstName()+" - "+b.getLastName()+" - "+b.getType()+" - "+b.getPersonId();
            users.add(bookName);
        }

        ObservableList<String> items =FXCollections.observableArrayList (
                users);

        // Create controls
        Label userListLabel = new Label("User List:");
        ListView<String> userListView = new ListView<>(items);
        Button addButton = new Button("Add User");
        Button removeButton = new Button("Remove User");

        // Add controls to the grid pane
        gridPane.add(userListLabel, 0, 0);
        gridPane.add(userListView, 0, 1, 2, 1);
        gridPane.add(addButton, 0, 2);
        gridPane.add(removeButton, 1, 2);

        // Add event handlers
        addButton.setOnAction(e ->
        {
            addUser();

            ArrayList<String> booksX = new ArrayList<>();

            for (Person b: library.getMembers()) {
                String bookName = b.getFirstName()+" - "+b.getLastName()+" - "+b.getType()+" - "+b.getPersonId();
                booksX.add(bookName);
            }

            for (Person b: library.getLibrarians()) {
                String bookName = b.getFirstName()+" - "+b.getLastName()+" - "+b.getType()+" - "+b.getPersonId();
                booksX.add(bookName);
            }

            ObservableList<String> itemsX =FXCollections.observableArrayList (
                    booksX);

            userListView.setItems(itemsX);

        });
        removeButton.setOnAction(e ->
        {
            removeUser(userListView);
            ArrayList<String> booksX = new ArrayList<>();

            for (Person b: library.getMembers()) {
                String bookName = b.getFirstName()+" - "+b.getLastName()+" - "+b.getType()+" - "+b.getPersonId();
                booksX.add(bookName);
            }

            for (Person b: library.getLibrarians()) {
                String bookName = b.getFirstName()+" - "+b.getLastName()+" - "+b.getType()+" - "+b.getPersonId();
                booksX.add(bookName);
            }

            ObservableList<String> itemsX =FXCollections.observableArrayList (
                    booksX);

            userListView.setItems(itemsX);
        });

        // Create a scene and set it on the stage
        Scene scene = new Scene(gridPane, 500, 550);
        userStage.setScene(scene);
        userStage.show();
    }
    
    private void addUser() {
        Stage bookStage = new Stage();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER); //position
        grid.setHgap(10);//gap
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        // screen title
        Label sceneTitle = new Label("ADD User");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label fName = new Label("User First Name:");
        grid.add(fName, 0, 1);
        TextField fNameField = new TextField();
        grid.add(fNameField, 1, 1);

        Label lName = new Label("User Last Name:");
        grid.add(lName, 0, 2);
        TextField lNameField = new TextField();
        grid.add(lNameField, 1, 2);

        Label password = new Label("Password:");
        grid.add(password, 0, 3);
        TextField passwordField = new TextField();
        grid.add(passwordField, 1, 3);

        Label address = new Label("Address:");
        grid.add(address, 0, 4);
        TextField addressField = new TextField();
        grid.add(addressField, 1, 4);

        Label phone = new Label("Phone:");
        grid.add(phone, 0, 5);
        TextField phoneField = new TextField();
        grid.add(phoneField, 1, 5);

        Label email = new Label("Email:");
        grid.add(email, 0, 6);
        TextField emailField = new TextField();
        grid.add(emailField, 1, 6);

        Label type = new Label("Type:");
        grid.add(type, 0, 7);
        TextField typeField = new TextField();
        grid.add(typeField, 1, 7);

        Button btn = new Button("Add User");
        grid.add(btn, 1, 9);

        btn.setOnAction(e -> {
            String fNameString = fNameField.getText().toString();
            String lNameString = lNameField.getText().toString();
            String phoneString = phoneField.getText().toString();

            String typeString = typeField.getText().toString();

            String addressString = addressField.getText().toString();
            String passwordString = passwordField.getText().toString();

            String emailString = emailField.getText().toString();

            if(typeString.toLowerCase().equals("reader"))
            {
                library.addReader(passwordString,typeString,fNameString,lNameString,addressString,Integer.parseInt(phoneString),emailString);
            }
            else
            {
                library.addLibrarian(passwordString,typeString,fNameString,lNameString,addressString,Integer.parseInt(phoneString),emailString);
            }

            showUserPage();

        });

        // Create a scene and set it on the stage
        Scene scene = new Scene(grid, 500, 550);
        bookStage.setScene(scene);

        bookStage.show();

        // Store the current window reference
        currentWindow = bookStage;
    }
    
    private void removeUser(ListView<String> userListView) {
        String selectedUser = userListView.getSelectionModel().getSelectedItem();

        String id = "";

        // get the id
        for (int i = selectedUser.length() -1; i >= 0; i--) {

            if(selectedUser.charAt(i) != ' ')
            {
                id = selectedUser.charAt(i) + "" ;
            }
            else
            {
                break;
            }
        }

        if(selectedUser.contains("reader"))
        {
            library.removeReader(Integer.parseInt(id));
        }
        else
        {
            library.removeLibrarian(Integer.parseInt(id));
        }


    }

    private void showBlockPage() {
        Stage userStage = new Stage();
        userStage.setTitle("library Management");
    
        // Add a window event listener to close the current window when another window is opened
        userStage.setOnShowing(event -> {
            if (currentWindow != null) {
                currentWindow.show();
            }
        });
    
    
        // Create a GridPane as the root layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
    
        ArrayList<String> users = new ArrayList<>();
    
        for (Person x : library.getMembers()) {
        
            if (x.isBlocked()) {
                String u = x.getFirstName() + " - " + x.getLastName() + " - " + x.getType().toLowerCase() + " - " + x.getPersonId();
                users.add(u);
            }
        }
    
        for (Person x : library.getLibrarians()) {
        
            if (x.isBlocked()) {
                String u = x.getFirstName() + " - " + x.getLastName() + " - " + x.getType().toLowerCase() + " - " + x.getPersonId();
                users.add(u);
            }
        }
    
        ObservableList<String> items = FXCollections.observableArrayList(
                users);
    
        Label userListLabel = new Label("User List:");
        ListView<String> userListView = new ListView<>(items);
        Button removeButton = new Button("Remove User from Block List");
        TextField userId = new TextField();
        Button addUserButton = new Button("Add User from Block List");
        Label labelUser = new Label();
        userListView.setPrefHeight(200);

        gridPane.add(userListLabel, 0, 0);
        gridPane.add(userListView, 0, 1);
        gridPane.add(labelUser,1 ,2);
        gridPane.add(userId, 1, 3);
        gridPane.add(removeButton, 1, 4);
        gridPane.add(addUserButton, 1, 5);

        removeButton.setOnAction(e ->{

            if(userId.getText().toString() != "")
            {
                library.removeBlockUser(Integer.parseInt(userId.getText().trim().toString()));

                ArrayList<String> usersX = new ArrayList<>();

                for (Person x : library.getMembers()) {

                    if (x.isBlocked()) {
                        String u = x.getFirstName() + " - " + x.getLastName() + " - " + x.getType().toLowerCase() + " - " + x.getPersonId();
                        usersX.add(u);
                    }
                }

                for (Person x : library.getLibrarians()) {

                    if (x.isBlocked()) {
                        String u = x.getFirstName() + " - " + x.getLastName() + " - " + x.getType().toLowerCase() + " - " + x.getPersonId();
                        usersX.add(u);
                    }
                }

                ObservableList<String> itemsX = FXCollections.observableArrayList(
                        usersX);
                userListView.setItems(itemsX);
            }

        });

        addUserButton.setOnAction(e ->{

            if(userId.getText().toString() != "")
            {
                library.blockUser(Integer.parseInt(userId.getText().trim().toString()));

                ArrayList<String> usersX = new ArrayList<>();

                for (Person x : library.getMembers()) {

                    if (x.isBlocked()) {
                        String u = x.getFirstName() + " - " + x.getLastName() + " - " + x.getType().toLowerCase() + " - " + x.getPersonId();
                        usersX.add(u);
                    }
                }

                for (Person x : library.getLibrarians()) {

                    if (x.isBlocked()) {
                        String u = x.getFirstName() + " - " + x.getLastName() + " - " + x.getType().toLowerCase() + " - " + x.getPersonId();
                        usersX.add(u);
                    }
                }

                ObservableList<String> itemsX = FXCollections.observableArrayList(
                        usersX);
                userListView.setItems(itemsX);

            }

        });
    
        // Create a scene and set it on the stage
        Scene scene = new Scene(gridPane, 500, 550);
        userStage.setScene(scene);
        userStage.show();
    
    }
//
//    //block
//

//
//    private void blockUser() {
//        String selectedUser = userListView.getSelectionModel().getSelectedItem();
//        String lateReturnDaysStr = lateReturnTextField.getText().trim();
//
//        if (selectedUser != null && !selectedUser.isEmpty() && !lateReturnDaysStr.isEmpty()) {
//            int lateReturnDays = Integer.parseInt(lateReturnDaysStr);
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Block User");
//            alert.setHeaderText(null);
//            alert.setContentText("User '" + selectedUser + "' has been blocked for late return of books.");
//            alert.showAndWait();
//        } else {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setHeaderText(null);
//            alert.setContentText("Please select a user and enter the number of late return days.");
//            alert.showAndWait();
//        }
//    }
    

}
