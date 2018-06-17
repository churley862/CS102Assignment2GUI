
import gui.VerticalButtonBar;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
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
import java.util.Collection;
import java.util.Scanner;

import TennisDatabase.*;
import javafx.util.StringConverter;

public class Assignment2 extends Application {
    private TableView table = new TableView();
    private TennisDatabase tennisDatabase = new TennisDatabase();

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

    private Pane bodyPane;
    private Pane playerPane;
    private GridPane addPlayerPane;
    private GridPane addMatchPane;
    private Pane matchPane;
    private TableView playerTable;

    private void buildPlayerPane(Pane parent) {
        playerPane = new Pane();
        playerPane.prefHeightProperty().bind(parent.heightProperty());
        playerPane.prefWidthProperty().bind(parent.widthProperty());

        ObservableList<TennisPlayer> players = tennisDatabase.getPlayers();
        playerTable = new TableView(players);
        playerTable.prefHeightProperty().bind(playerPane.heightProperty());
        playerTable.prefWidthProperty().bind(playerPane.widthProperty());

        TableColumn<TennisPlayer,String> playerId = new TableColumn("Id");
        playerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<TennisPlayer,String> playerFirst = new TableColumn("First");
        playerFirst.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        TableColumn<TennisPlayer,String> playerLast = new TableColumn("Last");
        playerLast.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        TableColumn<TennisPlayer,Integer> playerYear = new TableColumn("Year");
        playerYear.setCellValueFactory(new PropertyValueFactory<>("BirthYear"));
        TableColumn<TennisPlayer,String> playerCountry = new TableColumn("Country");
        playerCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        TableColumn<TennisPlayer,Integer> playerWins = new TableColumn("Wins");
        playerWins.setCellValueFactory(new PropertyValueFactory<>("wins"));
        TableColumn<TennisPlayer,Integer> playerLosses = new TableColumn("Losses");
        playerLosses.setCellValueFactory(new PropertyValueFactory<>("losses"));
        playerTable.getColumns().addAll(playerId, playerFirst, playerLast, playerYear, playerCountry, playerWins, playerLosses);

        playerTable.autosize();
        playerTable.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
                int pos = playerTable.getSelectionModel().getSelectedIndex();

                if (pos >= 0) {
                    TennisPlayer player = players.get(pos);
                    if (player.getWins() + player.getLosses() <= 0) {
                        tennisDatabase.removePlayer(player);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                "This player has matches, continuing will delete all matches containing this player and this player.");
                        if (alert.showAndWait().get() == ButtonType.OK){
                            tennisDatabase.deleteMatchesOfPlayer(player);
                        }

                    }
                }
            }
        });
        playerPane.getChildren().add(playerTable);
    }

    private void buildAddPlayerPane(Pane parent) {
        addPlayerPane = new GridPane();
        addPlayerPane.prefHeightProperty().bind(parent.heightProperty());
        addPlayerPane.prefWidthProperty().bind(parent.widthProperty());

        addPlayerPane.setAlignment(Pos.TOP_CENTER);
        addPlayerPane.setHgap(5);
        addPlayerPane.setVgap(5);
        addPlayerPane.setPadding(new Insets(50,25,25,25)); // set top, right, bottom, left

        int row = 1;
        Label l0 = new Label("Id: ");
        GridPane.setHalignment(l0, HPos.RIGHT);
        addPlayerPane.add(l0, 0, row);
        TextField id = new TextField("");
        id.setPrefColumnCount(14);
        addPlayerPane.add(id, 1, row++);

        Label l1 = new Label("First Name: ");
        GridPane.setHalignment(l1, HPos.RIGHT);
        addPlayerPane.add(l1, 0, row);
        TextField firstName = new TextField("");
        firstName.setPrefColumnCount(14);
        addPlayerPane.add(firstName, 1, row++);

        Label l2 = new Label("Last Name: ");
        GridPane.setHalignment(l2, HPos.RIGHT);
        addPlayerPane.add(l2, 0, row);
        TextField lastName = new TextField("");
        lastName.setPrefColumnCount(14);
        addPlayerPane.add(lastName, 1, row++);

        Label l3 = new Label("Country: ");
        GridPane.setHalignment(l3, HPos.RIGHT);
        addPlayerPane.add(l3, 0, row);

        TextField country = new TextField("");
        country.setPrefColumnCount(14);
        addPlayerPane.add(country, 1, row++);

        Label l4 = new Label("Year: ");
        GridPane.setHalignment(l4, HPos.RIGHT);
        addPlayerPane.add(l4, 0, row);

        TextField year = new TextField("");
        year.setPrefColumnCount(14);
        addPlayerPane.add(year, 1, row++);

        Button addButton = new Button("Add");
        addPlayerPane.add(addButton, 0, row + 5);
        addButton.setOnAction(e -> {
            int yearNum = Integer.parseInt(year.getText());
            tennisDatabase.addPlayer(new TennisPlayer(id.getText(),
                    firstName.getText(), lastName.getText(),
                    yearNum, country.getText()));
            bodyPane.getChildren().setAll(playerPane);
        });

        Button cancelButton = new Button("Cancel");
        addPlayerPane.add(cancelButton, 1, row + 5);
        cancelButton.setOnAction(e -> {
            bodyPane.getChildren().setAll(playerPane);
        });
    }

    private void buildMatchPane(Pane parent) {
        matchPane = new Pane();
        matchPane.prefHeightProperty().bind(parent.heightProperty());
        matchPane.prefWidthProperty().bind(parent.widthProperty());

        ObservableList<TennisMatch> matches = tennisDatabase.getMatches();
        TableView matchTable = new TableView(matches);
        matchTable.prefHeightProperty().bind(matchPane.heightProperty());
        matchTable.prefWidthProperty().bind(matchPane.widthProperty());

        TableColumn matchPlayer1 = new TableColumn("Player 1");
        matchPlayer1.setCellValueFactory(new PropertyValueFactory<>("Player1Id"));
        TableColumn matchPlayer2 = new TableColumn("Player 2");
        matchPlayer2.setCellValueFactory(new PropertyValueFactory<>("Player2Id"));
        TableColumn matchDate = new TableColumn("Date");
        matchDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        TableColumn matchEvent = new TableColumn("Event");
        matchEvent.setCellValueFactory(new PropertyValueFactory<>("tournament"));
        TableColumn matchScores = new TableColumn("Scores");
        matchScores.setCellValueFactory(new PropertyValueFactory<>("score"));
        matchTable.getColumns().addAll(matchPlayer1, matchPlayer2, matchDate, matchEvent, matchScores);

        matchTable.autosize();

        matchTable.setOnKeyPressed(event -> {
           if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
               int pos = matchTable.getSelectionModel().getSelectedIndex();
               
               if (pos >= 0) {
                  TennisMatch match = matches.get(pos);
                  tennisDatabase.removeMatch(match);
               }
           }
        });
        matchPane.getChildren().add(matchTable);
    }

    private void buildAddMatchPane(Pane parent) {
        addMatchPane = new GridPane();
        addMatchPane.prefHeightProperty().bind(parent.heightProperty());
        addMatchPane.prefWidthProperty().bind(parent.widthProperty());

        addMatchPane.setAlignment(Pos.TOP_CENTER);
        addMatchPane.setHgap(5);
        addMatchPane.setVgap(5);
        addMatchPane.setPadding(new Insets(50,25,25,25)); // set top, right, bottom, left

        int row = 1;
        Label l0 = new Label("Player 1: ");
        GridPane.setHalignment(l0, HPos.RIGHT);
        addMatchPane.add(l0, 0, row);

        ObservableList<TennisPlayer> playerIds = tennisDatabase.getPlayers();
        StringConverter<TennisPlayer> converter = new StringConverter<TennisPlayer>() {
            @Override
            public String toString(TennisPlayer player) {
                if (player == null) return "";
                return player.getId();
            }

            @Override
            public TennisPlayer fromString(String string) {
                return null;
            }
        };
        ComboBox player1 = new ComboBox(playerIds);
        player1.setConverter(converter);
        addMatchPane.add(player1, 1, row++);

        Label l1 = new Label("Player 2: ");
        GridPane.setHalignment(l1, HPos.RIGHT);
        addMatchPane.add(l1, 0, row);

        ComboBox player2 = new ComboBox(playerIds);
        player2.setConverter(converter);
        addMatchPane.add(player2, 1, row++);

        Label l2 = new Label("Event");
        GridPane.setHalignment(l2, HPos.RIGHT);
        addMatchPane.add(l2, 0, row);

        TextField event = new TextField("");
        event.setPrefColumnCount(14);
        addMatchPane.add(event, 1, row++);

        Label l3 = new Label("Date: ");
        GridPane.setHalignment(l3, HPos.RIGHT);
        addMatchPane.add(l3, 0, row);

        TextField date = new TextField("");
        date.setPrefColumnCount(14);
        addMatchPane.add(date, 1, row++);

        Label l4 = new Label("Scores: ");
        GridPane.setHalignment(l4, HPos.RIGHT);
        addMatchPane.add(l4, 0, row);

        TextField scores = new TextField("");
        scores.setPrefColumnCount(14);
        addMatchPane.add(scores, 1, row++);

        Button addButton = new Button("Add");
        addMatchPane.add(addButton, 0, row + 5);
        addButton.setOnAction(e -> {
            TennisPlayer p1 = (TennisPlayer) player1.getValue();
            TennisPlayer p2 = (TennisPlayer) player2.getValue();
            if (p1 == null || p2 == null || p1.getId().equals(p2.getId())) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "You need two players to have a valid match.");
                alert.showAndWait();
                return;
            }

            boolean validDate = false;
            int year = 0;
            int month = 0;
            int day = 0;
            Scanner sc = new Scanner(date.getText());
            sc.useDelimiter("/");
            if (sc.hasNextInt()) {
                month = sc.nextInt();
                if (sc.hasNextInt()) {
                    day = sc.nextInt();
                    if (sc.hasNextInt()) {
                        year = sc.nextInt();
                        validDate = true;
                    }
                }
            }
            if (!validDate) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Invalid date.");
                alert.showAndWait();
                return;
            }
            tennisDatabase.insertMatch(p1.getId(), p2.getId(),
                    year, month, day,
                    event.getText(), scores.getText());
            bodyPane.getChildren().setAll(matchPane);
        });

        Button cancelButton = new Button("Cancel");
        addMatchPane.add(cancelButton, 1, row + 5);
        cancelButton.setOnAction(e -> {
            bodyPane.getChildren().setAll(matchPane);
        });
    }

    //TennisDatabase tennisDatabase = new TennisDatabase();
    @Override
    public void start(Stage primaryStage) throws Exception {
        SplitPane spane = new SplitPane();
        spane.setDividerPositions(0.1);
        VerticalButtonBar bbar = new VerticalButtonBar();
        bbar.setMinWidth(200);
        bbar.setPrefWidth(200);

        bodyPane = new Pane();
        bodyPane.setMinWidth(300);

        buildPlayerPane(bodyPane);
        buildMatchPane(bodyPane);
        buildAddPlayerPane(bodyPane);
        buildAddMatchPane(bodyPane);

        bodyPane.getChildren().setAll(playerPane);
        spane.getItems().addAll(bbar, bodyPane);

        primaryStage.setTitle("Tennis Database Assignment 2");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);

        Button showPlayers = new Button("Show Players");
        showPlayers.setOnAction(event -> {
            bodyPane.getChildren().setAll(playerPane);
        });

        Button showMatches = new Button("Show Matches");
        showMatches.setOnAction(event -> {
            bodyPane.getChildren().setAll(matchPane);
        });

        Button addPlayer = new Button("Add Player");
        addPlayer.setOnAction(event -> {
            bodyPane.getChildren().setAll(addPlayerPane);
        });

        Button addMatch = new Button("Add Match");
        addMatch.setOnAction(event -> {
            bodyPane.getChildren().setAll(addMatchPane);
        });

        Button exportButton = new Button("Export");
        exportButton.setOnAction(event -> { buttonExport(primaryStage); });

        Button importButton = new Button("Import");
        importButton.setOnAction(event -> { buttonImport(primaryStage); });

        Button quitButton = new Button("Quit Tennis Database");
        quitButton.setOnAction(event -> {
                    Platform.exit();
                    System.exit(0);
                    });

        bbar.addAll(showPlayers, showMatches, addPlayer, addMatch, exportButton, importButton,quitButton);

        Scene scene = new Scene(spane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


