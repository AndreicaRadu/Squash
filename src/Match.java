public class Match {
    private String winner;
    private String loser;

    Match(String p1 , String p2){
        this.winner = p1;
        this.loser = p2;
    }

    public String getLoser() {
        return loser;
    }

    public String getWinner() {
        return winner;
    }

    public void setLoser(String loser) {
        this.loser = loser;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
