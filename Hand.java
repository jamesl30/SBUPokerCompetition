import java.util.*;

public class Hand {
    private ArrayList<Card> cards;
    private Player player;
    private String playerName;

    public Hand(String playerClassName, String playerName) {
        try {
            //System.out.println("Initializing " + playerName);
            player = (Player) Class.forName(playerClassName).newInstance();
        }
        catch (Exception e) {
            System.out.println("Problem with " + playerClassName + ".");
            e.printStackTrace();
            System.exit(1);
        }
        this.playerName = playerName;
        cards = new ArrayList<Card>();
    }

    public void reset() {
        cards = new ArrayList<Card>();
    }

    Double play(List<Card> communityCards, Double pot, Double current_bet, Double chips, Double already_bet, Double[] playerChips, Double[] bets, int index, int dealer_index) {
        ArrayList<Card> communityCardsCopy = new ArrayList<Card>();
        for (Card c : communityCards) {
            communityCardsCopy.add(c);
        }
        ArrayList<Card> cardsCopy = new ArrayList<Card>();
        for (Card c : cards) {
            cardsCopy.add(c);
        }
        return player.makeMove(cardsCopy, communityCardsCopy, pot, current_bet, chips, already_bet, playerChips, bets, index, dealer_index);
    }

    void addCard(Card c) {
        cards.add(c);
    }

    public List<Card> getCards() {
        return cards;
    }
    
    public String getPlayerName() {
        return playerName;
    }

    int straight_flush() {
        ArrayList<Integer> flush_cards = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (cards.get(i).getSuit()=="Hearts") {
                flush_cards.add(i);
            }
        }
        if (flush_cards.size() < 5) {
            flush_cards = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                if (cards.get(i).getSuit()=="Diamonds") {
                    flush_cards.add(i);
                }
            }
        }
        if (flush_cards.size() < 5) {
            flush_cards = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                if (cards.get(i).getSuit()=="Clubs") {
                    flush_cards.add(i);
                }
            }
        }
        if (flush_cards.size() < 5) {
            flush_cards = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                if (cards.get(i).getSuit()=="Spades") {
                    flush_cards.add(i);
                }
            }
        }
        for (int i = 4; i < flush_cards.size(); i++) {
            if (cards.get(flush_cards.get(i)).getRankValue() + 4 == cards.get(flush_cards.get(i-4)).getRankValue())
                return 800000000 + cards.get(flush_cards.get(i-4)).getRankValue();
        }
        return 0;
    }
    int four() {
        for (int i = 3; i < 7; i++) {
            if (cards.get(i).getRankValue() == cards.get(i-3).getRankValue()) {
                int ret = cards.get(0).getRankValue();
                if (i==3) ret = cards.get(4).getRankValue();
                ret += 700000000;
                return ret;
            }
        }
        return 0;
    }
    int house() {
        for (int i = 2; i < 7; i++) {
            if (cards.get(i).getRankValue() == cards.get(i-2).getRankValue()) {
                for (int j = 1; j < 7; j++) {
                    if ((j > i+1 || j < i-2) && cards.get(i).getRankValue() == cards.get(i-1).getRankValue()) {
                        return 600000000 + 100 * cards.get(i).getRankValue() + cards.get(j).getRankValue();
                    }
                }
            }
        }
        return 0;
    }
    int flush() {
        ArrayList<Integer> flush_cards = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (cards.get(i).getSuit()=="Hearts") {
                flush_cards.add(i);
            }
        }
        if (flush_cards.size() < 5) {
            flush_cards = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                if (cards.get(i).getSuit()=="Diamonds") {
                    flush_cards.add(i);
                }
            }
        }
        if (flush_cards.size() < 5) {
            flush_cards = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                if (cards.get(i).getSuit()=="Clubs") {
                    flush_cards.add(i);
                }
            }
        }
        if (flush_cards.size() < 5) {
            flush_cards = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                if (cards.get(i).getSuit()=="Spades") {
                    flush_cards.add(i);
                }
            }
        }
        if (flush_cards.size() >= 5) {
            return 500000000 + 15 * 15 * 15 * 15 * cards.get(flush_cards.get(0)).getRankValue() + 15 * 15 * 15 * cards.get(flush_cards.get(1)).getRankValue() + 15 * 15 * cards.get(flush_cards.get(2)).getRankValue() + 15 * cards.get(flush_cards.get(3)).getRankValue() + cards.get(flush_cards.get(4)).getRankValue();
        }
        return 0;
    }
    int straight() {
        int[] has = new int[15];
        for (Card c : cards) {
            has[c.getRankValue()] = 1;
        }
        for (int i = 14; i > 5; i--) {
            if (has[i]==1 && has[i-1]==1 && has[i-2]==1 && has[i-3]==1 && has[i-4]==1) {
                return 400000000 + i;
            }
        }
        return 0;
    }
    int three() {
        for (int i = 2; i < 7; i++) {
            if (cards.get(i).getRankValue() == cards.get(i-2).getRankValue()) {
                for (int j = 0; j < 7;) {
                    if (j >= i-2 && j <= i) j++;
                    int k = j+1;
                    if (k==i-2) {
                        k = i+1;
                    }
                    return 300000000 + 100 * cards.get(j).getRankValue() + cards.get(k).getRankValue();
                }
            }
        }
        return 0;
    }
    int two_pair() {
        for (int i = 1; i < 7; i++) {
            if (cards.get(i).getRankValue() == cards.get(i-1).getRankValue()) {
                for (int j = i+2; j < 7; j++) {
                    if (cards.get(j).getRankValue() == cards.get(j-1).getRankValue()) {
                        int k = 0;
                        while (k==i-1 || k==i || k==j || k==j-1) k++;
                        return 200000000 + 10000 * cards.get(i).getRankValue() + 100 * cards.get(j).getRankValue() + cards.get(k).getRankValue();
                    }
                }
            }
        }
        return 0;
    }
    int pair() {
        for (int i = 1; i < 7; i++) {
            if (cards.get(i).getRankValue() == cards.get(i-1).getRankValue()) {
                int ret = 100000000 + 15 * 15 * 15 * cards.get(i).getRankValue();
                for (int j = 0, cnt = 2; j < 7 && cnt >= 0; j++) {
                    if (j==i-1 || j==i) continue;
                    cnt--;
                    ret += Math.pow(15, cnt) * cards.get(j).getRankValue();
                }
                return ret;
            }
        }
        return 0;
    }
    int high_card_value() {
        int ret = 0;
        for (int i = 0; i < 5; i++) {
            ret += Math.pow(15, 4-i) * (cards.get(i).getRankValue());
        }
        return ret;
    }
    int evaluate() {
        Collections.sort(cards, new Comparator<Card>() {
            @Override
            public int compare(Card card1, Card card2) {
                return card2.getRankValue() - card1.getRankValue();
            }
        });
        if (straight_flush()>0)
            return straight_flush();
        else if (four()>0) {
            return four();
        }
        else if (house()>0) {
            return house();
        }
        else if (flush()>0) {
            return flush();
        }
        else if (straight()>0) {
            return straight();
        }
        else if (three()>0) {
            return three();
        }
        else if (two_pair()>0) {
            return two_pair();
        }
        else if (pair()>0) {
            return pair();
        }
        return high_card_value();
    }
    public String toString() {
        String res = "";
        for (Card c : cards) {
            res += c.toString() + " ";
        }
        return res;
    }
}