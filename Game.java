import java.util.*;

public class Game {
    static final boolean PRINT_VERBOSE = true;

    private Deck deck;
    private Hand playerHands[];
    private List<Card> communityCards;
    private int dealer;
    private Scoreboard scoreboard;
    private Double playerChips[];
    private final Double BIG_BIND = 20.;
    private final Double SMALL_BIND = 10.;
    private final Double STARTING_CHIPS = 1000.;
    private final Double eps = 1e-6;

    private boolean eq(Double a, Double b) {
        return (Math.abs(a-b) < eps);
    }

    public Game(Scoreboard scoreboard, ArrayList<String> playerClassList, int start) {
        this.scoreboard = scoreboard;
        deck = new Deck();
        playerHands = new Hand[scoreboard.getNumPlayers()];
        playerChips = new Double[scoreboard.getNumPlayers()];
        try {
            for (int i = 0; i < scoreboard.getNumPlayers(); i++) {
                playerHands[i] = new Hand(playerClassList.get(i),
                    scoreboard.getPlayerList()[i]);
                for (int j = 0; j < 2; j++) {
                    playerHands[i].addCard(deck.draw());
                }
                playerChips[i] = STARTING_CHIPS;
            }
        }
        catch (Exception e) {
            System.out.println("Can't deal initial hands!");
            System.exit(1);
        }
        dealer = start % scoreboard.getNumPlayers();
    }
    public int getNextPlayer() {
        return (dealer + 1) % scoreboard.getNumPlayers();
    }

    void nextRound() {
        dealer = getNextPlayer();
    }

    void addCommunityCard() {
        try {
            communityCards.add(deck.draw());
        }
        catch (Exception e) {
            println("DECK IS COOKED");
        }
    }
    public int play() {
        try {
            while (true) {
                communityCards = new ArrayList<Card>();
                //first time is (dealer+3)%n
                deck = new Deck();
                Double pot = BIG_BIND + SMALL_BIND;
                Double bet = BIG_BIND;
                int n = scoreboard.getNumPlayers();
                int cnt = 0;
                Double bets[] = new Double[n];
                for (int i = 0; i < n; i++) bets[i] = 0.;
                boolean folded[] = new boolean[n];
                for (int i = 0; i < n; i++) {
                    playerHands[i].reset();
                    if (playerChips[i]>0.0001) {
                        cnt++;
                        playerHands[i].addCard(deck.draw());
                        playerHands[i].addCard(deck.draw());
                    }
                    else folded[i] = true;
                }
                if (cnt<=1) {
                    for (int i = 0; i < n; i++) {
                        if (playerChips[i]>0.0001) {
                            scoreboard.addToScore(i, 1);
                            return i;
                        }
                    }
                    return 0;
                }
                int remaining_count = n;
                while (folded[dealer]) {dealer = (dealer+1)%n;}
                int small_bind_player = (dealer+1)%n;
                while (folded[small_bind_player]) small_bind_player = (small_bind_player+1)%n;
                int big_bind_player = (small_bind_player+1)%n;
                while (folded[big_bind_player]) big_bind_player = (big_bind_player+1)%n;
                bets[small_bind_player] = Math.min(playerChips[small_bind_player], SMALL_BIND);
                playerChips[small_bind_player] -= bets[small_bind_player];
                bets[big_bind_player] = Math.min(playerChips[big_bind_player], BIG_BIND);
                playerChips[big_bind_player] -= bets[big_bind_player];
                int stop = (big_bind_player+1)%n;

                for (int player = (big_bind_player+1)%n, count = 0; ; player = (player+1)%n, count++) {
                    if (count >= n && stop==player) break;
                    if (remaining_count==0) break;
                    if (folded[player]) continue;
                    Double move = Math.min(playerChips[player]+bets[player], playerHands[player].play(communityCards, pot, bet, playerChips[player], bets[player], playerChips, bets, player, dealer));
                    if (eq(move, playerChips[player]+bets[player])) {
                        pot += move - bets[player];
                        playerChips[player] -= move - bets[player];
                        bet += move - bets[player];
                        bets[player] = Math.max(bets[player], move);
                        remaining_count--;
                        stop = player;
                    }
                    else if (move < playerChips[player] + bets[player] + eps && eq(move, bet)) {
                        pot += move - bets[player];
                        playerChips[player] -= move - bets[player];
                        bets[player] = move;
                    }
                    else if (move < playerChips[player] + bets[player] + eps && move >= bet*2-eps) {
                        pot += move - bets[player];
                        playerChips[player] -= move - bets[player];
                        bet += move - bets[player];
                        bets[player] = move;
                        stop = player;
                    }
                    else {
                        // System.out.printf("Folded: %.2f %.2f %.2f\n", bets[player], playerChips[player], move);
                        folded[player] = true;
                        remaining_count--;
                    }
                }
                addCommunityCard();
                addCommunityCard();
                addCommunityCard();
                bet = 0.;
                stop = (dealer+1)%n;
                double[] new_bets = new double[n];
                for (int player = (dealer+1)%n, count = 0; ; player = (player+1)%n, count++) {
                    if (count >= n && stop==player) break;
                    if (remaining_count==0) break;
                    if (folded[player]) continue;
                    Double move = Math.min(playerChips[player]+bets[player], playerHands[player].play(communityCards, pot, bet, playerChips[player], bets[player], playerChips, bets, player, dealer));
                    if (bet>eps && eq(move, 0.)) {
                        folded[player] = true;
                        remaining_count--;
                    }
                    else if (eq(move, playerChips[player])) {
                        double change = move - new_bets[player];
                        pot += change;
                        playerChips[player] -= change;
                        new_bets[player] = move;
                        bets[player] += move;
                        remaining_count--;
                        stop = player;
                        bet = Math.max(bet, move);
                    }
                    else if (move < playerChips[player] + bets[player] + eps && eq(move, bet)) {
                        double change = move - new_bets[player];
                        pot += change;
                        playerChips[player] -= change;
                        bets[player] += move;
                        new_bets[player] = move;
                    }
                    else if (move < playerChips[player] + bets[player] + eps && move >= bet*2-eps) {
                        double change = move - new_bets[player];
                        playerChips[player] -= change;
                        bets[player] += move;
                        new_bets[player] = move;
                        bet = Math.max(bet, move);
                        stop = player;
                    }
                    else {
                        // System.out.printf("Folded: %.2f %.2f %.2f\n", bets[player], playerChips[player], move);
                        folded[player] = true;
                        remaining_count--;
                    }
                }

                addCommunityCard();
                bet = 0.;
                stop = (dealer+1)%n;
                new_bets = new double[n];
                for (int player = (dealer+1)%n, count = 0; ; player = (player+1)%n, count++) {
                    if (count >= n && stop==player) break;
                    if (remaining_count==0) break;
                    if (folded[player]) continue;
                    Double move = Math.min(playerChips[player]+bets[player], playerHands[player].play(communityCards, pot, bet, playerChips[player], bets[player], playerChips, bets, player, dealer));
                    if (bet>eps && eq(move, 0.)) {
                        folded[player] = true;
                        remaining_count--;
                    }
                    else if (eq(move, playerChips[player])) {
                        double change = move - new_bets[player];
                        pot += change;
                        playerChips[player] -= change;
                        new_bets[player] = move;
                        bets[player] += move;
                        remaining_count--;
                        stop = player;
                        bet = Math.max(bet, move);
                    }
                    else if (move < playerChips[player] + bets[player] + eps && eq(move, bet)) {
                        double change = move - new_bets[player];
                        pot += change;
                        playerChips[player] -= change;
                        bets[player] += move;
                        new_bets[player] = move;
                    }
                    else if (move < playerChips[player] + bets[player] + eps && move >= bet*2-eps) {
                        double change = move - new_bets[player];
                        playerChips[player] -= change;
                        bets[player] += move;
                        new_bets[player] = move;
                        bet = Math.max(bet, move);
                        stop = player;
                    }
                    else {
                        // System.out.printf("Folded: %.2f %.2f %.2f\n", bets[player], playerChips[player], move);
                        folded[player] = true;
                        remaining_count--;
                    }
                }

                addCommunityCard();
                bet = 0.;
                stop = (dealer+1)%n;
                new_bets = new double[n];
                for (int player = (dealer+1)%n, count = 0; ; player = (player+1)%n, count++) {
                    if (count >= n && stop==player) break;
                    if (remaining_count==0) break;
                    if (folded[player]) continue;
                    Double move = Math.min(playerChips[player]+bets[player], playerHands[player].play(communityCards, pot, bet, playerChips[player], bets[player], playerChips, bets, player, dealer));
                    if (bet>eps && eq(move, 0.)) {
                        folded[player] = true;
                        remaining_count--;
                    }
                    else if (eq(move, playerChips[player])) {
                        double change = move - new_bets[player];
                        pot += change;
                        playerChips[player] -= change;
                        new_bets[player] = move;
                        bets[player] += move;
                        remaining_count--;
                        stop = player;
                        bet = Math.max(bet, move);
                    }
                    else if (move < playerChips[player] + bets[player] + eps && eq(move, bet)) {
                        double change = move - new_bets[player];
                        pot += change;
                        playerChips[player] -= change;
                        bets[player] += move;
                        new_bets[player] = move;
                    }
                    else if (move < playerChips[player] + bets[player] + eps && move >= bet*2-eps) {
                        double change = move - new_bets[player];
                        playerChips[player] -= change;
                        bets[player] += move;
                        new_bets[player] = move;
                        bet = Math.max(bet, move);
                        stop = player;
                    }
                    else {
                        // System.out.printf("Folded: %.2f %.2f %.2f\n", bets[player], playerChips[player], move);
                        folded[player] = true;
                        remaining_count--;
                    }
                }

                //calculate winnings
                int[] score = new int[n];
                for (int i = 0; i < n; i++) {
                    if (folded[i]) score[i] = 1;
                    else {
                        for (Card c : communityCards)
                            playerHands[i].addCard(c);
                        score[i] = playerHands[i].evaluate();
                    }
                }
                ArrayList<Integer> ranks = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    ranks.add(i);
                }
                Collections.sort(ranks, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer i1, Integer i2) {
                        return score[i2] - score[i1] + (score[i2] - score[i1] == 0 ? ((int) Math.round(1000 * (bets[i1] - bets[i2]))) : 0);
                    }
                });
                double[] add = new double[n];
                for (int i = 0; i < n; i++) {
                    if (score[ranks.get(i)]==1) break;
                    int j = i+1;
                    while (j < n && score[ranks.get(j)] == score[ranks.get(i)]) j++;
                    Double gain = 0.;
                    for (int k = i; k < n; k++) {
                        gain += Math.min(bets[ranks.get(i)] / (j-i), bets[ranks.get(k)] / (j-i));
                    }
                    for (int k = n-1; k >= i; k--) {
                        bets[ranks.get(k)] -= Math.min(bets[ranks.get(k)], bets[ranks.get(i)]);
                    }
                    for (int k = i; k < j; k++) {
                        add[ranks.get(k)] += gain;
                    }
                }
                for (int i = 0; i < n; i++) playerChips[ranks.get(i)] += add[ranks.get(i)] + bets[ranks.get(i)];
                nextRound();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    void print(String s) {
        if (PRINT_VERBOSE) {
            System.out.print(s);
        }
    }

    void println(String s) {
        if (PRINT_VERBOSE) {
            System.out.println(s);
        }
    }
}