import java.util.List;
import java.util.Random;

public class BotPlayer2 implements Player {
    /**
     * TODO: implement this function (replace the name of the class with your name in your new file)
     *
     * @param hand           The player's current hand (list of cards).
     * @param communityCards The community cards available on the table.
     * @param pot            The total amount of chips in the pot.
     * @param current_bet    The highest current bet in the round.
     * @param chips          The player's remaining chips.
     * @param already_bet    The player's current bet.
     * @param playerChips    An array representing the chips each player has.
     * @param bets           An array representing the current bets of each player.
     * @param index          The index of the player making the move.
     * @return The amount to bet:
     *         - Return `current_bet` to call.
     *         - Return at least twice `current_bet` or all remaining `chips` to raise.
     *         - Return any other value to fold.
     */
    public Double makeMove(List<Card> hand, List<Card> communityCards, Double pot, Double current_bet, Double chips, Double bet, Double[] playerChips, Double[] bets, int index) {
    Random rng = new Random();
        if (rng.nextInt() > (1<<28)) {
            return Math.min(chips, current_bet); //call
        }
        else if (rng.nextBoolean()) {
                return chips; //go all in
        }
        else {
            return 0.; //fold
        }
    }
}
