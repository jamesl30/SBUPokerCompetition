# SBUPokerCompetition
Template for the Stony Brook Computing Society's Poker Bot Tournament for Geek Games.

Copy BotPlayer.java and rename it with your name, and implement the makeMove function (to call, return bet; to raise, return **at least twice current bet or your remaining chips**; and any other number to fold), list player names in players.txt to run games with them, and run PokerSimulation.java to simulate 100 rounds with those players. There are two bots, BotPlayer.java and BotPlayer2.java for you to test your code against- the current setup in players.txt has three BotPlayers playing against one BotPlayer2.

You do NOT need to use all arguments for your bot, they are only there for convenience.

Card interface: getRank() returns "2" to "10", "J", "Q", "K", or "A" (there is getRankValue() which returns the corresponding "value" from 2 to 14), getSuit() returns one of "Hearts", "Diamonds", "Clubs", "Spades"

List<Card> hand is a list of the two cards you have.

List<Card> communityCards is a list of the communityCards

pot is the amount in the pot.

bet is the amount you need to bet to call.

chips is the amount of chips you have.

current_bet is the amount you already have bet that is in the pot.

playerChips is the amount of chips all players have.

already_bet is the amount that each player has bet so far.

index is your index in the two above arrays.

dealer_index is the index of the dealer.

Finally, learn Java here! https://www.w3schools.com/java/default.asp

Have fun!