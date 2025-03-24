import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    private ArrayList<Card> cards = new ArrayList<>();
    Random rng;
    public Deck() {
        rng = new Random();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
        shuffle();
    }

    public String toString() {
        String res = "";
        for (Card c : cards) res = res + c.toString() + " ";
        return res;
    }

    public void shuffle() {
        Collections.shuffle(cards, rng);
    }

    public Card draw() {
        return cards.isEmpty() ? null : cards.remove(cards.size() - 1);
    }

    public int remainingCards() {
        return cards.size();
    }
}
