package main.java.TennisDatabase;

import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
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

    public void returnButton(Stage stage){

    }

    public String fileChooser(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath));
        File selectedFile = fileChooser.showOpenDialog(stage);
        return selectedFile.getName();
    }
    private void buttonDeletePlayer(Stage stage) {
        stage.setTitle("Delete a Tennis Player");
        TextField text = new TextField("Delete a player by ID");
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
            buttonSubmitDeletePlayer(stage, text.getText());
            try {
                start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        stage.setScene(new Scene(root, 500, 575));
        stage.show();
    }

    private void buttonSubmitDeletePlayer(Stage stage, String text) {
        tennisDatabase.removePlayer(tennisDatabase.searchTennisPlayer(text));
    }
    private void buttonPrintMatches(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Matches list");
        stage.setWidth(500);
        stage.setHeight(500);

        final Label label = new Label("Matches List");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        ArrayList<TennisMatch> matcheslist = tennisDatabase.returnAllMatches();
        TableView<Integer> table = new TableView<>();
        for (int i =0; i < matcheslist.size(); i++){
            table.getItems().add(i);
        }
        TableColumn<Integer,String > player1IDCol = new TableColumn<>("Player 1 ID");
        player1IDCol.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(matcheslist.get(rowIndex).getPlayer1Id());
        });
        TableColumn<Integer,String > player2IDCol = new TableColumn<>("Player 2 ID");
        player2IDCol.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(matcheslist.get(rowIndex).getPlayer2Id());
        });
        TableColumn<Integer,String > scoreCol = new TableColumn<>("Score");
        scoreCol.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(matcheslist.get(rowIndex).getScore());
        });
        TableColumn<Integer,String > dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(matcheslist.get(rowIndex).dateToString(matcheslist.get(rowIndex).getDateYear()
                    ,matcheslist.get(rowIndex).getDateMonth(),matcheslist.get(rowIndex).getDateDay()));
        });
        TableColumn<Integer,String> tounCol = new TableColumn<>("Location");
        tounCol.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper( (matcheslist.get(rowIndex).getTournament()));
        });

        table.getColumns().addAll(player1IDCol,player2IDCol, dateCol, scoreCol,tounCol);
        Button button1 = new Button("Done");
        button1.setOnAction(event -> {
            try {
                start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        HBox rootH1 = new HBox();
        rootH1.getChildren().addAll(button1);


        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, rootH1);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();

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

        ArrayList<TennisPlayer> playerlist = tennisDatabase.returnAllPlayers();
        TableView<Integer> table = new TableView<>();
        for (int i =0; i < playerlist.size(); i++){
            table.getItems().add(i);
        }
        TableColumn<Integer,String > playerIDCol = new TableColumn<>("Player ID");
        playerIDCol.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(playerlist.get(rowIndex).getId());
        });
        TableColumn<Integer,String > locationCol = new TableColumn<>("Birth Location");
        locationCol.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(playerlist.get(rowIndex).getCountry());
        });
        TableColumn<Integer,String > firstNameCol = new TableColumn<>("First name");
        firstNameCol.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(playerlist.get(rowIndex).getFirstName());
        });
        TableColumn<Integer,String > lastNameCol = new TableColumn<>("Last name");
        lastNameCol.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper(playerlist.get(rowIndex).getLastName());
        });
        TableColumn<Integer,String> yearCol = new TableColumn<>("Birth year");
        playerIDCol.setCellValueFactory(cellData -> {
            Integer rowIndex = cellData.getValue();
            return new ReadOnlyStringWrapper( (playerlist.get(rowIndex).getId()));
        });

        table.getColumns().addAll(playerIDCol,firstNameCol, lastNameCol, yearCol,locationCol);

        Button button1 = new Button("Done");
        button1.setOnAction(event -> {
            try {
                start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        HBox rootH1 = new HBox();
        rootH1.getChildren().addAll(button1);


        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table,rootH1);

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
        Button printMatches = new Button("Print all Matches");
        Button deletePlayer = new Button("Delete Player By ID");
        Button resetDatabase = new Button("Reset Database");
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
        printMatches.setOnAction(event -> {
            buttonPrintMatches(primaryStage);
        });
        deletePlayer.setOnAction(event -> {
            buttonDeletePlayer(primaryStage);
        });
        resetDatabase.setOnAction(event -> {
           buttonResetDatabase(primaryStage);
        });

        VBox root = new VBox();
        root.getChildren().addAll(button1, button2,insert,printPlayers,printMatches,deletePlayer,resetDatabase);
        primaryStage.setScene(new Scene(root, 500, 575));
        primaryStage.show();

    }

    private void buttonResetDatabase(Stage stage) {
        Button button1 = new Button("Cancel");
        Button button2 = new Button("Continue");
        button1.setOnAction(event -> {
            try {
                start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        button2.setOnAction(event -> {
            try {
                tennisDatabase.reset();
                start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        HBox rootH1 = new HBox();
        rootH1.getChildren().addAll(button1,button2);
        stage.setTitle("Reset Database confirmation");
        stage.setScene(new Scene(rootH1, 300,300));

    }


    public static void main(String[] args) {
        launch(args);
    }
}


