
import gui.VerticalButtonBar;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import TennisDatabase.*;

public class Assignment2 extends Application {
    private TableView table = new TableView();
    private TennisDatabase tennisDatabase = new TennisDatabase();

    private void buttonPrintMatches(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Matches list");
        stage.setWidth(300);
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

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

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

    private void buttonImport(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Tennis Players");

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                tennisDatabase.loadFromFile(file.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void buttonExport(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Tennis Players");

        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                tennisDatabase.exportToFile(file.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private Pane playerPane;
    private Pane matchPane;

    private void buildPlayerPane(Pane parent) {
        playerPane = new Pane();
        playerPane.prefHeightProperty().bind(parent.heightProperty());
        playerPane.prefWidthProperty().bind(parent.widthProperty());

        TableView playerTable = new TableView();
        playerTable.prefHeightProperty().bind(playerPane.heightProperty());
        playerTable.prefWidthProperty().bind(playerPane.widthProperty());

        TableColumn playerId = new TableColumn("Id");
        TableColumn playerFirst = new TableColumn("First");
        TableColumn playerLast = new TableColumn("Last");
        TableColumn playerYear = new TableColumn("Year");
        TableColumn playerCountry = new TableColumn("Country");
        TableColumn playerWins = new TableColumn("Wins");
        TableColumn playerLosses = new TableColumn("Losses");
        TableColumn playerTies = new TableColumn("Ties");
        playerTable.getColumns().addAll(playerId, playerFirst, playerLast, playerYear, playerCountry, playerWins, playerLosses, playerTies);

        playerTable.autosize();
        playerPane.getChildren().add(playerTable);
    }

    private void buildMatchPane(Pane parent) {
        matchPane = new Pane();
        matchPane.prefHeightProperty().bind(parent.heightProperty());
        matchPane.prefWidthProperty().bind(parent.widthProperty());

        TableView matchTable = new TableView();
        matchTable.prefHeightProperty().bind(matchPane.heightProperty());
        matchTable.prefWidthProperty().bind(matchPane.widthProperty());

        TableColumn matchPlayer1 = new TableColumn("Player 1");
        TableColumn matchPlayer2 = new TableColumn("Player 2");
        TableColumn matchDate = new TableColumn("Date");
        TableColumn matchEvent = new TableColumn("Event");
        TableColumn matchScores = new TableColumn("Scores");
        matchTable.getColumns().addAll(matchPlayer1, matchPlayer2, matchDate, matchEvent, matchScores);

        matchTable.autosize();
        matchPane.getChildren().add(matchTable);
    }

    //TennisDatabase tennisDatabase = new TennisDatabase();
    @Override
    public void start(Stage primaryStage) throws Exception {
        SplitPane spane = new SplitPane();
        spane.setDividerPositions(0.1);
        VerticalButtonBar bbar = new VerticalButtonBar();
        bbar.setMinWidth(200);
        bbar.setPrefWidth(200);

        Pane bodyPane = new Pane();
        bodyPane.setMinWidth(300);

        buildPlayerPane(bodyPane);
        buildMatchPane(bodyPane);

        bodyPane.getChildren().setAll(playerPane);
        spane.getItems().addAll(bbar, bodyPane);

        primaryStage.setTitle("Tennis Database Assignment 2");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        //label.setFont( Font.font( "Times New Roman", 22 ) );
        //label.setTextFill( color );
        Button showPlayers = new Button("Show Players");
        Button showMatches = new Button("Show Matches");

        Button button1 = new Button("Export");
        Button button2 = new Button("Import");
//        Button insert = new Button("Insert New Player or match");
//        Button printPlayers = new Button("Print all Players");
//        Button printMatches = new Button("Print all Matches");
        button1.setOnAction(event -> { buttonExport(primaryStage); });
        button2.setOnAction(event -> { buttonImport(primaryStage); });

        showMatches.setOnAction(event -> {
            bodyPane.getChildren().setAll(matchPane);
        });

        showPlayers.setOnAction(event -> {
            bodyPane.getChildren().setAll(playerPane);
        });
//        insert.setOnAction(event -> {
//            buttonInsertPlayer(primaryStage);
//        });
//        printPlayers.setOnAction(event -> {
//            buttonPrintPlayers(primaryStage);
//        });
//        printMatches.setOnAction(event -> {
//            buttonPrintMatches(primaryStage);
//        });

        bbar.addButton(showPlayers);
        bbar.addButton(showMatches);
        bbar.addButton(button1);
        bbar.addButton(button2);

        Scene scene = new Scene(spane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }




    public static void main(String[] args) {
        launch(args);
    }
}

