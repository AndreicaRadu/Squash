import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static java.lang.Double.*;

public class Ranking {
    private ArrayList<Player> playerList;
    private ArrayList<Match> matchList;
    private HashMap<String , Integer> playerMap;
    public Ranking(){
        playerList = new ArrayList<Player>();
        matchList = new ArrayList<Match>();
        playerMap = new HashMap<String , Integer>();
    }
    public void addPlayer() throws IOException {
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        if(playerMap.containsKey(name))
        {
            System.out.println("Player already registered\n");
            return;
        }

        Player k = new Player(name , 1000);
        playerList.add(k);
        playerMap.put(k.getName() , this.playerList.size()-1);
    }
    public void addPlayer(String name) throws IOException {
        if(playerMap.containsKey(name))
        {
            System.out.println("Player already registered\n");
            return;
        }

        Player k = new Player(name , 1000);
        playerList.add(k);
        playerMap.put(k.getName() , this.playerList.size()-1);
    }

    public void print(){
        for(int i=0 ; i<playerList.size() ; i++)
            playerList.get(i).print();
    }
    static double Probability(double rating1, double rating2)
    {
        return 1.0f * 1.0f / (1 + 1.0f *
                (double)(Math.pow(10, 1.0f *
                        (rating1 - rating2) / 400)));
    }
    public void match() throws IOException {
        Scanner sc = new Scanner(System.in);
        String p1 = sc.nextLine();
        String p2 = sc.nextLine();
        if(!playerMap.containsKey(p1))
        {
            System.out.println(p1 + " is not a registered player!\n");
            return;
        }
        if(!playerMap.containsKey(p2))
        {
            System.out.println(p2 + " is not a registered player!\n");
            return;
        }

        this.playerList.get(playerMap.get(p1)).setMatches(this.playerList.get(playerMap.get(p1)).getMatches() + 1);
        this.playerList.get(playerMap.get(p2)).setMatches(this.playerList.get(playerMap.get(p2)).getMatches() + 1);
        this.playerList.get(playerMap.get(p1)).setWins(this.playerList.get(playerMap.get(p1)).getWins() + 1);

        Match m = new Match(p1 , p2);
        matchList.add(m);
        FileWriter fwm = new FileWriter("matches.csv" , true );
        fwm.write(p1 + "," + p2 + "\n");
        fwm.close();

        double aux1 = playerList.get(playerMap.get(p1)).getRtg();
        double aux2 = playerList.get(playerMap.get(p2)).getRtg();
        double k1 = playerList.get(playerMap.get(p1)).getK();
        double k2 = playerList.get(playerMap.get(p2)).getK();
        //prob pl2
        double Pb = Probability(aux1, aux2);
        // prob pl1
        double Pa = Probability(aux2, aux1);

        aux1 = aux1 + k1 * (1 - Pa);
        aux2 = aux2 + k2 * (0 - Pb);

        playerList.get(playerMap.get(p1)).setRtg(aux1);
        playerList.get(playerMap.get(p2)).setRtg(aux2);
        playerList.get(playerMap.get(p1)).setK(max(15.0 , k1 - 1));
        playerList.get(playerMap.get(p2)).setK(max(15.0 , k2 - 1));

        this.normalize();

        FileWriter fw = new FileWriter("ranking.csv" );
        for(int i=0 ; i<playerList.size() ; i++)
            fw.write(playerList.get(i).getName() + "," + playerList.get(i).getRtg() + "," + playerList.get(i).getK() + "," + playerList.get(i).getWins() + "," + playerList.get(i).getMatches() + "\n");
        fw.close();
    }
    public void normalize(){
        double sum = 0;
        for(int i=0 ; i<playerList.size() ; i++)
            sum += playerList.get(i).getRtg();
        for(int i=0 ; i<playerList.size() ; i++)
            playerList.get(i).setRtg(playerList.get(i).getRtg() * playerList.size() * 1000 / sum);
    }
    public void match(String p1, String p2) throws IOException{
        if(!playerMap.containsKey(p1))
        {
            System.out.println(p1 + "is not a registered player!\n");
            return;
        }
        if(!playerMap.containsKey(p2))
        {
            System.out.println(p2 + "is not a registered player!\n");
            return;
        }

        this.playerList.get(playerMap.get(p1)).setMatches(this.playerList.get(playerMap.get(p1)).getMatches() + 1);
        this.playerList.get(playerMap.get(p2)).setMatches(this.playerList.get(playerMap.get(p2)).getMatches() + 1);
        this.playerList.get(playerMap.get(p1)).setWins(this.playerList.get(playerMap.get(p1)).getWins() + 1);

        Match m = new Match(p1 , p2);
        matchList.add(m);
        FileWriter fwm = new FileWriter("matches.csv" , true );
        fwm.write("2\n" + p1 + "\n" + p2 + "\n");
        fwm.close();

        double aux1 = playerList.get(playerMap.get(p1)).getRtg();
        double aux2 = playerList.get(playerMap.get(p2)).getRtg();
        double k1 = playerList.get(playerMap.get(p1)).getK();
        double k2 = playerList.get(playerMap.get(p2)).getK();

        //prob pl2
        double Pb = Probability(aux1, aux2);
        // prob pl1
        double Pa = Probability(aux2, aux1);

        aux1 = aux1 + k1 * (1 - Pa);
        aux2 = aux2 + k2 * (0 - Pb);

        playerList.get(playerMap.get(p1)).setRtg(aux1);
        playerList.get(playerMap.get(p2)).setRtg(aux2);
        playerList.get(playerMap.get(p1)).setK(max(15.0 , k1 - 1));
        playerList.get(playerMap.get(p2)).setK(max(15.0 , k2 - 1));

        this.normalize();

        FileWriter fw = new FileWriter("ranking.csv" );
        for(int i=0 ; i<playerList.size() ; i++)
            fw.write(playerList.get(i).getName() + "," + playerList.get(i).getRtg() + "," + playerList.get(i).getK() + "," + playerList.get(i).getWins() + "," + playerList.get(i).getMatches() + "\n");
        fw.close();
    }
    public void menu() throws IOException {
        this.initializePlayers();
        this.initializeMatches();
        while(true){
            Scanner sc = new Scanner(System.in);
            System.out.println("1. Add player \n2. Add match \n3. Print ranking\n4. Get from csv\n5. H2H");
            System.out.println("Input a valid operation number:");
            int c = sc.nextInt();
            while(c < 0 || c > 11){
                System.out.println("Input a valid operation number:");
                c = sc.nextInt();
                String aux = sc.nextLine();
            }
            if(c == 0) return;
            switch (c){
                case 1:
                    this.addPlayer();
                    break;
                case 2:
                    this.match();
                    break;
                case 3:
                    this.print();
                    break;
                case 4:
                    System.out.println("Give path or input 0 to return:\n");
                    String path = sc.nextLine();
                    path = sc.nextLine();
                    if(!path.equals("0")) {
                        this.getFromCsv(path);
                        break;
                    }
                case 5:
                    this.h2h();
                    break;
            }

        }
    }
    public void getFromCsv(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        while((line = br.readLine()) != null) {
            int op = Integer.parseInt(line);
            if(op == 1){
                line = br.readLine();
                this.addPlayer(line);
            }
            if(op == 2){
                String n1 = br.readLine();
                String n2 = br.readLine();
                this.match(n1,n2);
            }
        }
        br.close();
    }

    public void initializePlayers() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("ranking.csv"));
        String line;
        while((line = br.readLine()) != null) {
            String[] str = line.split(",");

            Player k = new Player(str[0] , parseDouble(str[1]), parseDouble(str[2]), Integer.parseInt(str[3]), Integer.parseInt(str[4]));
            playerList.add(k);
            playerMap.put(k.getName() , this.playerList.size()-1);
        }
        br.close();
    }

    public void initializeMatches() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("matches.csv"));
        String line;
        while((line = br.readLine()) != null) {
            int op = Integer.parseInt(line);
            if(op == 2){
                String p1 = br.readLine();
                String p2 = br.readLine();
                Match m = new Match(p1 , p2);
                matchList.add(m);
            }
        }
        br.close();
    }

    public void h2h(){
        Scanner sc = new Scanner(System.in);
        String p1 = sc.nextLine();
        String p2 = sc.nextLine();
        if(!playerMap.containsKey(p1))
        {
            System.out.println(p1 + " is not a registered player!\n");
            return;
        }
        if(!playerMap.containsKey(p2))
        {
            System.out.println(p2 + " is not a registered player!\n");
            return;
        }
        int w1 = 0 , w2 = 0;
        for(int i=0 ; i<matchList.size() ; i++) {
            if(matchList.get(i).getWinner().equals(p2) && matchList.get(i).getLoser().equals(p1))
                w2++;
            if((matchList.get(i).getWinner().equals(p1) && matchList.get(i).getLoser().equals(p2)))
                w1++;
        }
        System.out.println(p1 + " - " + p2 + " " + w1 + " - " + w2 + "\n");
    }
}
