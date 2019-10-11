public class UnoGame{
    
    public static UnoPlayer playerOne = new UnoPlayer();
    public static UnoPlayer playerTwo = new UnoPlayer();
    public final static int WINNINGSCORE = 500;

	private static boolean isGameOver;
    private static UnoPlayer whoWon;
    public static UnoDeck totalDeck;
    public static UnoDeck discardPile;


    public static UnoHand dealCards(int numberOfCardsToDeal){

        UnoHand genericHand = new UnoHand();
        
        //Gives cards to the players
        for (int i = 0; i < numberOfCardsToDeal; i++ ){
            UnoCard currentCard = totalDeck.dealOneCard();
            genericHand.addCard(currentCard);
        }

        return genericHand;
    }
 

    public static void turnTheDiscardPileIntoDeck(){
    
        System.out.println("Deck ran out of cards, turning discardPile into deck," +
            " shuffling, and then add one card to discardPile" );
        totalDeck = discardPile;
        totalDeck.shuffleDeck(5);

        //PREVENTS TOP CARD FROM BEING A WILD
        while (totalDeck.getCardOnTop().getValue() == 13 || totalDeck.getCardOnTop().getValue() == 14){
            totalDeck.shuffleDeck(5);
        }
        discardPile = new UnoDeck(totalDeck.getCardOnTop());

    }

    private static void reInitializeDeckAndHands(int scorePlayerOne, int scorePlayerTwo) {

        // create deck
        totalDeck = new UnoDeck();
        totalDeck.shuffleDeck(5);

        playerOne = new UnoPlayer(dealCards(7), "PlayerOne", scorePlayerOne); 
        playerTwo = new UnoPlayer(dealCards(7), "PlayerTwo", scorePlayerTwo);
        


        // create empty discard deck with the first card on top of totalCard
        //PREVENTS TOP CARD FROM BEING A WILD
        while (totalDeck.getCardOnTop().getValue() == 13 || totalDeck.getCardOnTop().getValue() == 14){
            totalDeck.shuffleDeck(5);
        }
        discardPile = new UnoDeck(totalDeck.getCardOnTop());


    }

    private static void InitializeDeckAndHands() {

        // create deck
        totalDeck = new UnoDeck();
        totalDeck.shuffleDeck(5);

        playerOne = new UnoPlayer(dealCards(7), "PlayerOne", 0);
        playerTwo = new UnoPlayer(dealCards(7), "PlayerTwo", 0);

        //PREVENTS TOP CARD FROM BEING A WILD
        while (totalDeck.getCardOnTop().getValue() == 13 || totalDeck.getCardOnTop().getValue() == 14){
            totalDeck.shuffleDeck(5);
        }
        discardPile = new UnoDeck(totalDeck.getCardOnTop());

    }

    private static int getValueOfThisCard(UnoCard card){

        System.out.println(card);

        switch (card.getValue()) {
            case 0:   return 0;
            case 1:   return 1;
            case 2:   return 2;
            case 3:   return 3;
            case 4:   return 4;
            case 5:   return 5;
            case 6:   return 6;
            case 7:   return 7;
            case 8:   return 8;
            case 9:   return 9;
            case 10:  return 20;
            case 11:  return 20;
            case 12:  return 20;
            case 13:  return 50;
            case 14:  return 50;
            default:  return 0;

        }
    }

    private static void concealScreenForOtherPlayer(int blankLinesToPrint) {

        for (int i = 0; i < blankLinesToPrint; i++ ){
            System.out.println(" ");
        }
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("You are not allowed to look at the other's players hand. Do not cheat.");

    }

    private static boolean didReachUno(UnoPlayer thisPlayer, UnoPlayer otherPlayer) {

        boolean didWin = false;
        if (thisPlayer.getScore() >= WINNINGSCORE){
            didWin = true;
            whoWon = thisPlayer;
        }
        else if (otherPlayer.getScore() >= WINNINGSCORE){
            didWin = true;
            whoWon = thisPlayer;
        }
        else{
            // no one has won yet, calculate scores and return false
            int scoreToAdd = 0;
            for(int cardIterator = 0; cardIterator < otherPlayer.getPlayerNumberOfCards(); cardIterator++) {
                UnoCard otherPlayerCard = otherPlayer.getUnoHand().getCard(cardIterator);    
                scoreToAdd  = scoreToAdd + getValueOfThisCard(otherPlayerCard);
            }

            System.out.println("Just awarded a total of " + scoreToAdd + " points to " + thisPlayer.getName());
            thisPlayer.addScore(scoreToAdd);
        }

        return didWin;

    }



	public static void main(String[] args) {

        System.out.println(" ");
        System.out.println(" ");
        System.out.println("This program lets you play the simple card game,");
        System.out.println("Uno.  A hand is dealt from a deck of cards.");
        System.out.println("Your objective is to get rid of all your cards. ");
        System.out.println("For two player Uno, the Reverse card acts as a skip.");
        System.out.println("Winner is the first person to 500 points");
        System.out.println("IMPORTANT: ONLY ONE PERSON SHOULD BE LOOKING AT THE COMPUTER AT A TIME");
        System.out.println("ONCE YOUR TURN IS OVER, THERE WILL BE A DELAY");
        System.out.println("DURING WHICH, GIVE THE COMPUTER TO THE OTHER PERSON");
        System.out.println(" ");
        
        InitializeDeckAndHands();

        int turnSet = 1;
        int rounds = 1;

        while(!isGameOver){

            System.out.println("TURN SET #  " + turnSet);
            System.out.println(" " );


            // does the deck have enough cards for this round or should flip the discard pile?
            if (totalDeck.deckSize() < 5){
                turnTheDiscardPileIntoDeck();
            }

            // player ONE logic: get info needed and pass to playTurn
            UnoCard currentCardOnTop = discardPile.getCardOnTop();

            playerOne.playTurn(currentCardOnTop, playerTwo, totalDeck, discardPile);
            concealScreenForOtherPlayer(30);

            if (playerOne.getClaimedUnoForSelf() && playerOne.getPlayerNumberOfCards() == 0){

                isGameOver = didReachUno(playerOne, playerTwo);
                if (!isGameOver){

                    reInitializeDeckAndHands(playerOne.getScore(), playerTwo.getScore());
                    rounds++;
                    turnSet=1;
                    System.out.println("ROUND SET #  " + rounds);
                    System.out.println("ROUND SET #  " + rounds);
                    System.out.println("ROUND SET #  " + rounds);
                    System.out.println("ROUND SET #  " + rounds);
                    System.out.println("ROUND SET #  " + rounds);

                }

            }
            else if (playerOne.getClaimedUnoForSelf() && playerOne.getPlayerNumberOfCards() > 1){
                playerOne.setClaimedUnoForSelf(false);
            }


            System.out.println(" ");

            if (isGameOver){
                break;
            }


            // player ONE logic: get info needed and pass to playTurn
             currentCardOnTop = discardPile.getCardOnTop();
            playerTwo.playTurn(currentCardOnTop, playerOne, totalDeck, discardPile);
            concealScreenForOtherPlayer(30);


            if (playerTwo.getClaimedUnoForSelf() && playerTwo.getPlayerNumberOfCards() == 0){

                isGameOver = didReachUno(playerTwo, playerOne);
                if (!isGameOver){

                    reInitializeDeckAndHands(playerTwo.getScore(), playerOne.getScore());
                }

            }
            else if (playerTwo.getClaimedUnoForSelf() && playerTwo.getPlayerNumberOfCards() > 1){
                playerTwo.setClaimedUnoForSelf(false);
            }

            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            turnSet++;

        }

        // calculate score

        ///END Game
        System.out.println(" ");
        System.out.println("THE WINNER IS: " + whoWon.getName() + " with a score of " + whoWon.getScore());

    }

}