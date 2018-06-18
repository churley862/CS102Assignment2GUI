package TennisDatabase;

import java.util.Scanner;


public class TennisMatch {
    // Getters
    public  String getPlayer1Id() {
        return player1.getId();
    }

    public String getPlayer1Name() { return player1.getName() + ((getWinner() == 1) ? " (W)" : ""); }

    public String getPlayer2Id() {
        return player2.getId();
    }

    public String getPlayer2Name() { return player2.getName() + ((getWinner() == 2) ? " (W)" : ""); }

    public int getDateYear() {
        return year;
    }


    public int getDateMonth() {
        return month;
    }


    public int getDateDay() {
        return day;
    }

    public String getDate() { return String.format("%d/%d/%d", month, day, year); }

    public String getTournament() {
        return event;
    }


    public String getScore() {
        return scores;
    }


    public int getWinner() {
        return recursiveGetWinner();
    }


    public void print(){
        System.out.println("" + year + "/" + month + "/" + day + "" + player1.getId() + "-" + player2.getId() +
        " "+ event + " " + scores);
    }

    @Override
    // Checks for a duplicate match in existance
    public boolean equals(Object obj) {
        if (obj instanceof TennisMatch) {
           return ((TennisMatch) obj).getTournament().equals(getTournament());
        }

        return false;
    }
    // output: the String of the match
    // desc: gets a string representation of a match
    public String toString(){
        String match = "";
        match = "MATCH" +"/"+ player1.getId() + "/" + player2.getId() +"/"+ dateToString(year,month,day) +
                "/"+ event + "/" + scores;
        return match.toUpperCase();
    }
    // input int month,day,year - of the date of the match
    // output: the string representation of that date in the format yearmonthday
    // Desc: converts the date to a string
    public String dateToString(int year, int month, int day) {
        String date = "";
        date += year;
        if (month<10){
            date = date + "0" + month;
        }else{
            date += month;
        }
        if(day < 10) {
            date = date + "0" + day;
        }else {
            date += day;
        }
        return date;
    }

    private TennisPlayer player1;
    private TennisPlayer player2;
    private int day;
    private int month;
    private int year;
    private String event;
    private String scores;

    public TennisMatch(TennisPlayer player1, TennisPlayer player2, int year, int month, int day, String event, String scores) {
        this.player1 = player1;
        this.player2 = player2;
        this.day = day;
        this.month = month;
        this.year = year;
        this.event = event;
        this.scores = scores;
    }

    // output - int representation of the date for ordering purposes
    // desc: a integer of the date used to order the matches
    private int dateAsInt() {
        return (year *10000) + (month * 100) + day;
    }
    // output: integer - flag for the compare to
    // desc: returns -1 if the date is less than that of the input, 0 if they are the same and 1 if it's bigger
    public int compareTo(TennisMatch o) {
        // arbitrary value to calculate date is older
        int dateVal = o.dateAsInt();
        int newDateVal = dateAsInt();
        if (dateVal > newDateVal)
        { return -1; }
        else if (dateVal == newDateVal)
        { return 0; }
        else
        { return 1; }
    }
    // First part of the recursive function that runs and returns the integer value of the winner (Player 1 is 1 and player 2 is 2)
    // output: integer 1 if player 1 won and 2 if player 2 won, will return -1 if there is a tie
    public int recursiveGetWinner() {
        Scanner sc = new Scanner(scores);
        return recursiveGetWinner(sc, 0);
    }
    // continuation of above
    public int recursiveGetWinner(Scanner scores, int win) {
        scores.useDelimiter("-");
        int score1 = scores.nextInt();
        scores.skip("-");
        scores.useDelimiter(",");
        int score2 = scores.nextInt();

        if (score1 > score2) {
            win += 1;
        } else {
            win -= 1;
        }

        if (scores.hasNext()) {
            scores.skip(",");
            return recursiveGetWinner(scores, win);
        } else {
            if (win == 0) return -1;
            return (win > 0) ? 1 : 2;
        }
    }
    // output: string of the winning players ID
    // desc: returns the winning players ID as a string
    public String getWinnerId() {
        if (getWinner() == 1) {
            return player1.getId();
        } else {
            return player2.getId();

        }
    }
}