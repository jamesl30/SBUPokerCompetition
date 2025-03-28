# SBUPokerCompetition
Template for the Stony Brook Computing Society's Poker Bot Tournament for Geek Games.

Copy BotPlayer.java and rename it with your name, and implement the makeMove function (to call, return bet; to raise, return **at least twice current bet or your remaining chips**; and any other number to fold), list player names in players.txt to run games with them, and run PokerSimulation.java to simulate 100 rounds with those players. There are two bots, BotPlayer.java and BotPlayer2.java for you to test your code against- the current setup in players.txt has three BotPlayers playing against one BotPlayer2.

You do NOT need to use all arguments for your bot, they are only there for convenience.

Card interface: getRank() returns "2" to "10", "J", "Q", "K", or "A" (there is getRankValue() which returns the corresponding "value" from 2 to 14), getSuit() returns one of "Hearts", "Diamonds", "Clubs", "Spades"

List<Card> hand is a list of the two cards you have.

List<Card> communityCards is a list of the communityCards

pot is the amount in the pot.

current_bet is the amount that someone bet that you need to match/call/raise.

chips is the amount of chips you have.

already_bet is the amount that you have already bet (for example, binds or how much you raised but someone reraised you).

playerChips is an array of the amount of chips all players have.

bets is an array of all bets that have been made.

index is your index in the two above arrays.

dealer_index is the index of the dealer.

Finally, learn Java here! https://www.w3schools.com/java/default.asp

# Code Submissions

When you are done coding your bot, submit your program **[here](https://docs.google.com/forms/d/e/1FAIpQLSeSl6_iEJTGXu3T41AW8iNbE_sYoXJYYY43_uzvx9CG8xtUKA/viewform?usp=dialog).** Your code will be run in a tournament with 4-6 people who submit at around the same time.

Have fun!
