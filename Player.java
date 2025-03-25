import java.util.*;

public interface Player {
    public abstract Double makeMove(List<Card> hand, List<Card> communityCards, Double pot, Double current_bet, Double chips, Double already_bet, Double[] playerChips, Double[] bets, int index);
}
