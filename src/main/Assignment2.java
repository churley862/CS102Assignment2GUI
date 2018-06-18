
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
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.regex.Pattern;

import TennisDatabase.*;
import javafx.util.StringConverter;

public class Assignment2 extends Application {
    private TableView table = new TableView();
    private TennisDatabase tennisDatabase = new TennisDatabase();

    // input : Stage from the start section
    // Desc. : Creates a button for importing players
    private void buttonImport(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Tennis Players");

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                tennisDatabase.loadFromFile(file.getName());
                matchPane.autosize();
                playerPane.autosize();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // input : Stage from the start section
    // Desc. : Creates a button for exporting players
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
    private BorderPane playerPane;
    private GridPane addPlayerPane;
    private GridPane addMatchPane;
    private BorderPane matchPane;
    private TableView playerTable;

    // input : Pane from the start section
    // Desc. : Builds the pane containing the player table
    private void buildPlayerPane(Pane parent) {
        playerPane = new BorderPane();
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
                            tennisDatabase.removePlayer(player);
                        }

                    }
                }
            }
        });

        playerPane.setCenter(playerTable);
        playerTable.autosize();
        playerPane.setBottom(new Label("Press Backspace to delete player."));
    }
    // input : Pane from the start section
    // Desc. : Creates a pane for adding players validates inside
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

        Label l4 = new Label("Birth Year: ");
        GridPane.setHalignment(l4, HPos.RIGHT);
        addPlayerPane.add(l4, 0, row);

        TextField year = new TextField("");
        year.setPrefColumnCount(14);
        addPlayerPane.add(year, 1, row++);

        Button addButton = new Button("Add");
        addPlayerPane.add(addButton, 0, row + 5);
        addButton.setOnAction(e -> {

            if (id.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "You must provide an player id.");
                alert.showAndWait();
                return;
            }

            if (firstName.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "You must provide a first name.");
                alert.showAndWait();
                return;
            }

            if (lastName.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "You must provide a last name.");
                alert.showAndWait();
                return;
            }

            if (year.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "You must provide an player birth year.");
                alert.showAndWait();
                return;
            }

            if (country.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "You must provide an player country.");
                alert.showAndWait();
                return;
            }

            int yearNum = -1;
            try {
                yearNum = Integer.parseInt(year.getText());
            } catch (NumberFormatException err) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Birth year must be a valid integer.");
                alert.showAndWait();
                return;
            }

            if (tennisDatabase.hasPlayer(id.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Player id must be unique.");
                alert.showAndWait();
                return;
            }
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
    // input : pane from the start section
    // Desc. : Creates a pane for showing matches
    private void buildMatchPane(Pane parent) {
        matchPane = new BorderPane();
        matchPane.prefHeightProperty().bind(parent.heightProperty());
        matchPane.prefWidthProperty().bind(parent.widthProperty());

        ObservableList<TennisMatch> matches = tennisDatabase.getMatches();
        TableView matchTable = new TableView(matches);
        matchTable.prefHeightProperty().bind(matchPane.heightProperty());
        matchTable.prefWidthProperty().bind(matchPane.widthProperty());

        TableColumn matchPlayer1 = new TableColumn("Player 1");
        matchPlayer1.setCellValueFactory(new PropertyValueFactory<>("Player1Name"));
        TableColumn matchPlayer2 = new TableColumn("Player 2");
        matchPlayer2.setCellValueFactory(new PropertyValueFactory<>("Player2Name"));
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
        matchPane.setCenter(matchTable);
        matchPane.autosize();
        matchPane.setBottom(new Label("(W) Denotes match winner.     Press Backspace to delete match."));
    }

    Pattern validScores = Pattern.compile("\\s*\\d+-\\d+\\s*(,\\s*\\d+-\\d)*");

    // input : pane from the start section
    // Desc. : Creates the pane to add matches validating inside
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

        Label l3 = new Label("Date (MM/DD/YYYY): ");
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

                        if (month > 0 && month <= 12 && day > 0 && day <= 31 && year >= 1000 && year <= 9999) {
                            validDate = true;
                        }
                    }
                }
            }

            if (!validDate) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Invalid date.");
                alert.showAndWait();
                return;
            }

            if (event.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "You must provide an tournament name.");
                alert.showAndWait();
                return;
            } else if (tennisDatabase.matchExists(event.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "Tournament name must be unique.");
                alert.showAndWait();
                return;
            }

            if (scores.getText().isEmpty() || !validScores.matcher(scores.getText()).matches()) {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        "You must provide a well formed score string (1-2, 2-3, etc).");
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

    // input : Stage from main
    // Desc. : Creates the main screen and builds the whole table
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
        primaryStage.setWidth(950);
        primaryStage.setHeight(500);

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

        Button resetButton = new Button("Reset Database");
        resetButton.setOnAction(event -> {tennisDatabase.reset();});

        Button quitButton = new Button("Quit Tennis Database");
        quitButton.setOnAction(event -> {
                    Platform.exit();
                    System.exit(0);
                    });

        bbar.addAll(showPlayers, showMatches, addPlayer, addMatch, exportButton, importButton,resetButton,quitButton);

        Scene scene = new Scene(spane, 950, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


