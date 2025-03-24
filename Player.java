import java.util.*;

public interface Player {
    public abstract Double makeMove(List<Card> hand, List<Card> communityCards, Double pot, Double bet, Double chips, Double current_bet, Double[] playerChips, Double[] bets, int index);
}
