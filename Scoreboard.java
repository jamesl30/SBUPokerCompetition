public class Scoreboard
{
    private String playerList[];
    private int scores[];

    public Scoreboard(String playerList[]) {
        scores = new int[playerList.length];
        for (int i = 0; i < playerList.length; i++) {
            scores[i] = 0;
        }
        this.playerList = playerList;
    }

    public void addToScore(int player, int points) {
        scores[player] += points; 
    }

    public int getScore(int player) {
        return scores[player];
    }

    public String toString() {
        String retval = "";
        for (int i = 0; i < scores.length; i++) {
            retval += "Player #" + i + " (" + playerList[i] + "): " + scores[i] + "\n";
        }
        return retval;
    }

    /**
     * Return the list of player names.
     */
    public String[] getPlayerList() {
        return playerList;
    }

    /**
     * Return the number of players in the game.
     */
    public int getNumPlayers() {
        return playerList.length; 
    }

    /**
     * Return the zero-based number of the player who has won the game,
     * <i>presuming someone has.</i> (This method should only be called
     * once the end of the entire match has been detected by some other
     * means, and returns the number of the player with the highest score.)
     */
    public int getWinner() {
        int winner = 0;
        int topScore = scores[0];

        for (int i = 1; i < scores.length; i++) {
            if (scores[i] > topScore) {
                topScore = scores[i];
                winner = i;
            }
        }
        return winner;
    }
}
