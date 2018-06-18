package TennisDatabase;

public class TennisPlayer {
    // Desc: prints the tennis player
    public void print(){
        System.out.println("id " + id);
        System.out.println("first name " + fName);
        System.out.println("last name " + lName);
        System.out.println("year " + year);
        System.out.println("country " + country);
        System.out.println(getWinLossRecord());
    }
    //  output : string representation for the TennisPlayer
    public String toString(){
        String player = "";
        player = "PLAYER/" + getId() + "/" + getFirstName() + "/" + getLastName() + "/" + getBirthYear() + "/" + getCountry();
        return player.toUpperCase();
    }
    //output: returns the win loss record of the player
    public String getWinLossRecord(){
        return "Wins: " + wins + " Losses: " + losses ;

    }
    private String id;
    // getters
    public String getFirstName() {
        return fName;
    }

    public String getLastName() {
        return lName;
    }

    public int getBirthYear() { return year; }


    public String getCountry() {
        return country;
    }

    private String fName;
    private String lName;
    private int year;
    private String country;
    private int wins =0;
    private int losses = 0;

    public int getWins() { return wins; }
    public int getLosses() { return losses; }

    public String getId() {
        return id;
    }
    // constructor
    public TennisPlayer(String id, String fName, String lName, int year, String country){
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.year = year;
        this.country = country;
    }

    // Function to add a win to a player
    public void addWin() {
        wins += 1;
    }

    //function to add a loss to a player
    public void addLoss() {
        losses += 1;
    }
    //Dummy player constructor
    public TennisPlayer(String id){
        // Dummy player
        this.id = id;
    }



    // Compare to function that checks if the ID of the object passed in is the same as this ID
    public int compareTo(TennisPlayer o) {
        return id.compareTo(o.id);
    }

    // updates the dummy player
    public void updatePlayer(TennisPlayer player) {
        this.fName = player.fName;
        this.lName = player.lName;
        this.year = player.year;
        this.country = player.country;
    }

    // Removes a win from a player when a match is deleted that they won
    public void removeWin() {
        wins -= 1;
    }
    // Removes a loss from a player when a match is removed that they lost
    public void removeLoss() {
        losses -= 1;
    }
    // Returns name FirstName LastName
    public String getName() {
        return fName + " " + lName;
    }
}
