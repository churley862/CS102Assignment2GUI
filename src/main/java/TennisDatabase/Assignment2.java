package main.java.TennisDatabase;

import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Assignment2 extends Application {
    private TableView table = new TableView();
    private TennisDatabase tennisDatabase = new TennisDatabase();

    public String fileChooser(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath));
        File selectedFile = fileChooser.showOpenDialog(stage);
        return selectedFile.getName();
    }
    private void buttonSubmitInsertPlayer(Stage stage, String playerText) {
        tennisDatabase.parseLine(playerText);
    }
    private void buttonPrintPlayers(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Players list");
        stage.setWidth(300);
        stage.setHeight(500);

        final Label label = new Label("Players List");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);
        TableColumn locationCol = new TableColumn("Location");
        TableColumn playerIDCol = new TableColumn("PlayerID");
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
       // firstNameCol.setCellValueFactory(new PropertyValueFactory<>, String>("firstName")););

        TableColumn lastNameCol = new TableColumn("Last Name");
        TableColumn yearCol = new TableColumn("Year");


        table.getColumns().addAll(playerIDCol,firstNameCol, lastNameCol, yearCol,locationCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    private void buttonInsertPlayer(Stage stage) {
        stage.setTitle("Insert a Tennis Player");
        TextField text = new TextField("insert a player in the correct format");
        Button button1 = new Button("Submit");
        Button button2 = new Button("Cancel");
        Scene scene = new Scene(new Group());
        HBox rootH1 = new HBox();
        HBox rootH2 = new HBox();
        VBox root = new VBox();

        rootH1.getChildren().addAll(text);
        rootH2.getChildren().addAll(button1, button2);
        rootH1.setAlignment(Pos.CENTER);
        rootH2.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(rootH1, rootH2);
        button1.setOnAction(event -> {
            buttonSubmitInsertPlayer(stage, text.getText());
            try {
                start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }

    });
        stage.setScene(new Scene(root, 500, 575));
        stage.show();
    }
    public void buttonSubmitExport(Stage stage, String filename) throws FileNotFoundException, UnsupportedEncodingException {
        try {
            tennisDatabase.exportToFile(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }try {
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void buttonSubmitImport(Stage stage, String text) throws FileNotFoundException {
        tennisDatabase.loadFromFile(text);
    }
    private void buttonImport(Stage stage) {
        stage.setTitle("Import Tennis Players");
        TextField text = new TextField("Browse to file");
        Button browseButton = new Button("...");
        browseButton.setOnAction(event -> {
            text.setText(fileChooser(stage));
        });
        Button button1 = new Button("Submit");
        Button button2 = new Button("Cancel");
        Scene scene = new Scene(new Group());
        HBox rootH1 = new HBox();
        HBox rootH2 = new HBox();
        VBox root = new VBox();

        rootH1.getChildren().addAll(browseButton, text);
        rootH2.getChildren().addAll(button1, button2);
        rootH1.setAlignment(Pos.CENTER);
        rootH2.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(rootH1, rootH2);
        button1.setOnAction(event -> {
            try {
                buttonSubmitImport(stage, text.getText());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        stage.setScene(new Scene(root, 500, 575));
        stage.show();

    }

    public void buttonExport(Stage stage) {
        stage.setTitle("Export Tennis Players");
        TextField text = new TextField("Browse to file");
        Button browseButton = new Button("...");
        browseButton.setOnAction(event -> {
            text.setText(fileChooser(stage));
        });
        Button button1 = new Button("Submit");
        Button button2 = new Button("Cancel");
        Scene scene = new Scene(new Group());
        HBox rootH1 = new HBox();
        HBox rootH2 = new HBox();
        VBox root = new VBox();
        rootH1.getChildren().addAll(browseButton, text);
        rootH2.getChildren().addAll(button1, button2);
        rootH1.setAlignment(Pos.CENTER);
        rootH2.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(rootH1, rootH2);
        button1.setOnAction(event -> {
            try {
                buttonSubmitExport(stage,text.getText());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        stage.setScene(new Scene(root, 500, 575));
        stage.show();
    }





    private static final Color color = Color.web("#FF00FF");

    //TennisDatabase tennisDatabase = new TennisDatabase();
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(new Group());
        primaryStage.setTitle("Tennis Database Assignment 2");
        primaryStage.setWidth(300);
        primaryStage.setHeight(190);
        //label.setFont( Font.font( "Times New Roman", 22 ) );
        //label.setTextFill( color );
        Button button1 = new Button("Export");
        Button button2 = new Button("Import");
        Button insert = new Button("Insert New Player or match");
        Button printPlayers = new Button("Print all Players");
        button1.setOnAction(event -> {
            buttonExport(primaryStage);
        });
        button2.setOnAction(event -> {
            buttonImport(primaryStage);
        });
        insert.setOnAction(event -> {
            buttonInsertPlayer(primaryStage);
        });
        printPlayers.setOnAction(event -> {
            buttonPrintPlayers(primaryStage);
        });
        VBox root = new VBox();
        root.getChildren().addAll(button1, button2,insert,printPlayers);
        primaryStage.setScene(new Scene(root, 500, 575));
        primaryStage.show();

    }



    public static void main(String[] args) {
        launch(args);
    }
}


