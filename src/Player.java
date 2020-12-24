public class Player {
    private String name;
    private double rtg;
    private double k;
    private int wins;
    private int matches;

    Player(String name, double rtg){
        this.name = name;
        this.rtg = rtg;
        this.k = 40;
        this.wins = 0;
        this.matches = 0;
    }

    Player(String name, double rtg, double k, int wins, int matches){
        this.name = name;
        this.rtg = rtg;
        this.k = k;
        this.wins = wins;
        this.matches = matches;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRtg(double rtg) {
        this.rtg = rtg;
    }

    public String getName() {
        return name;
    }

    public double getRtg() {
        return rtg;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void print(){
        System.out.println(this.name + " " + this.rtg + "\n");
    }
}