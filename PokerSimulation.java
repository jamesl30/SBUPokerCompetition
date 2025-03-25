import java.util.*;
import java.io.*;
public class PokerSimulation {
    public static boolean PRINT_VERBOSE = true;

    public static final String PLAYER_FILENAME = "players.txt";

    private static ArrayList<String> playerNames = new ArrayList<String>();
    private static ArrayList<String> playerClasses = new ArrayList<String>();

    public static int GAMES = 100;

    public static void main(String args[]) {
        int numGames = GAMES;
        try {
            loadPlayerData();
            Scoreboard s = new Scoreboard(playerNames.toArray(new String[0]));
            for (int i = 0; i < numGames; i++) {
                Game g = new Game(s,playerClasses, i);
                int winner = g.play();
                System.out.println("Game " + i + ": player " + winner + " (" + playerNames.get(winner) + ") wins!");
            }
            System.out.println(s);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadPlayerData() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(PLAYER_FILENAME));
        String playerLine = br.readLine();
        while (playerLine != null) {
            Scanner line = new Scanner(playerLine);
            String s = line.next();
            playerNames.add(s);
            playerClasses.add(s);
            playerLine = br.readLine();
            line.close();
        }
        br.close();
    }
}