package main.java.TennisDatabase;

public class TennisPlayer {
    public void print(){
        System.out.println("id " + id);
        System.out.println("first name " + fName);
        System.out.println("last name " + lName);
        System.out.println("year " + year);
        System.out.println("country " + country);
        System.out.println(getWinLossRecord());
    }
    public String toString(){
        String player = "";
        player = "PLAYER/" + getId() + "/" + getFirstName() + "/" + getLastName() + "/" + getBirthYear() + "/" + getCountry();
        return player.toUpperCase();
    }
    public String getWinLossRecord(){
        return "Wins: " + wins + " Losses: " + losses ;

    }
    private String id;

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


    public String getId() {
        return id;
    }

    public TennisPlayer(String id, String fName, String lName, int year, String country){
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.year = year;
        this.country = country;
    }

    public void addWin() {
        wins += 1;
    }

    public void addLoss() {
        losses += 1;
    }

    public TennisPlayer(String id){
        // Dummy player
        this.id = id;
    }




    public int compareTo(TennisPlayer o) {
        return id.compareTo(o.id);
    }

    public void updatePlayer(TennisPlayer player) {
        this.fName = player.fName;
        this.lName = player.lName;
        this.year = player.year;
        this.country = player.country;
    }
}
