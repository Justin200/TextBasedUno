public class UnoPlayer{

	private int playerNumberOfCards;
	private int score;
	private UnoHand unoHand;
	private boolean hasClaimedUnoForSelf = false;
    private boolean validTurn = false;
    private String name;

	public UnoPlayer(){
		// do nothing
	}


	public void discardCard(UnoCard cardToDiscard, UnoDeck discardPile){
		unoHand.removeCard(cardToDiscard);
		discardPile.addToDeck(cardToDiscard);
		this.playerNumberOfCards = this.playerNumberOfCards - 1;
	}

	public void drawCards(int numberOfCardsToDraw, UnoDeck deckDrawingCards){

        //Gives cards to the players
        for (int i = 0; i < numberOfCardsToDraw; i++ ){
            UnoCard currentCard = deckDrawingCards.dealOneCard();
            this.unoHand.addCard(currentCard);
        }
    }

	public UnoHand getUnoHand(){
		return this.unoHand;
	}

	public int getScore(){
		return this.score;
	}

	public boolean getClaimedUnoForSelf(){
		return this.hasClaimedUnoForSelf;
	}

	public void setClaimedUnoForSelf(boolean claim){
		this.hasClaimedUnoForSelf = claim;
	}

	public int getPlayerNumberOfCards(){
		return this.playerNumberOfCards;
	}	

	public void addScore(int scorePoints){
		this.score = this.score + scorePoints;
	}

	public String getName(){
		return this.name;
	}

	public UnoPlayer(UnoHand unoHand, String name, int previousRoundScore){
		this.unoHand = unoHand;
		this.score = previousRoundScore;
		this.name = name;
		this.playerNumberOfCards = unoHand.getCardCount();
	}

	public void setValidTurn(boolean b){
        this.validTurn = b;
    }

    public boolean getValidTurn(){
        return this.validTurn;
    }

    public boolean matchesColorOrValue(UnoCard currentCardOnTop, UnoCard cardTryingToDiscard){

        if (currentCardOnTop.getColor() == cardTryingToDiscard.getColor()){
            return true;
    	}
    	else if (currentCardOnTop.getValue() == cardTryingToDiscard.getValue()){
            return true;
    	}

        return false;
    }


    public void discardReverse(UnoDeck discardPile, UnoCard mustMatchSomehowCard){
    	for (int i = 0; i < playerNumberOfCards; i++ ){
            UnoCard currentCard = unoHand.getCard(i);
            System.out.println("Card value = " + currentCard.getValue());
            if (currentCard.getValue() == 10){

            	if (matchesColorOrValue(mustMatchSomehowCard, currentCard)){
                    System.out.println("You had a reverse card so it is being discarded, we are going to skip the other player's turn");
            		discardCard(currentCard, discardPile);
                    break;
            	}
            }
        }

    }

    // discard all cards (simulates skipping a turn for the player)
    public void discardSkips(UnoDeck discardPile, UnoCard mustMatchSomehowCard){
        for (int i = 0; i < playerNumberOfCards; i++ ){
            UnoCard currentCard = unoHand.getCard(i);

            if (currentCard.getValue() == 11){
	        	if (matchesColorOrValue(mustMatchSomehowCard, currentCard)){
	        		System.out.println("You had a skip card so it is being discarded, we are going to skip the other player's turn");
	        		discardCard(currentCard, discardPile);
                    break;
	        	}
            }
        }
    }



    public void playDrawTwo(UnoDeck discardPile, UnoPlayer otherPlayer, UnoDeck currentDeck, UnoCard cardToPlay){
    	for (int i = 0; i < playerNumberOfCards; i++ ){
            UnoCard currentCard = unoHand.getCard(i);

            if (currentCard.getValue() == 12){
 
	        	if (matchesColorOrValue(cardToPlay, currentCard)){
            	System.out.println("You had a draw two, so it is being played now, and the other player is being forced to draw two cards from the deck");
	        		discardCard(currentCard, discardPile);
            		otherPlayer.drawCards(2, currentDeck);
                    setValidTurn(true);
                    break;
	        	}

            }
        }

    }

    public void setWildCardColorAndDiscard(UnoDeck discardPile, int colorValue, UnoCard cardToPlay){
        discardCard(cardToPlay, discardPile);
        UnoCard cardOnTop = UnoGame.discardPile.getCardOnTop();
        cardOnTop.setColor(colorValue);
        System.out.println("The card on top is now " + cardOnTop);
    }

    public boolean hasUsableCards(UnoCard card){
        for(int i = 0; i < unoHand.getCardCount() ; i++){
            if (unoHand.getCard(i).getValue() == 14 || unoHand.getCard(i).getValue() == 13){
                return true;
            }
            else if(matchesColorOrValue(card, unoHand.getCard(i))) { //IF ONE MATCHING CARD, BREAK OUT OF LOOP
                return true;
            }
        }
        return false;
    }


	public void playTurn(UnoCard currentCardOnTop, UnoPlayer otherPlayer, UnoDeck currentDeck,
		UnoDeck currentDiscardPile){

        //RESETS VALID TURN EACH TURN
        setValidTurn(false);
		int numberOfCardsInOtherPlayerHand = otherPlayer.getPlayerNumberOfCards();
        boolean otherPlayerClaimedUno = otherPlayer.getClaimedUnoForSelf();
	
        if (!otherPlayerClaimedUno && numberOfCardsInOtherPlayerHand == 1){

            System.out.println("The other player has one card, but forgot to claim uno and are being dealt 2 cards");
                otherPlayer.drawCards(2, currentDeck);
        }

		System.out.println("Starting " + this.name + "'s turn");
        System.out.println("The current card on top of the pile is " + currentCardOnTop.toString());


        while (!getValidTurn()){

            System.out.println("Here are your card options");
            System.out.println("0. Draw Card");
            System.out.println(unoHand.listCards());
            System.out.println(" ");

            //CHECK IF PLAYER HAS ANY USABLE CARDS
            if(!hasUsableCards(currentCardOnTop)){
                drawCards(1, currentDeck);
                System.out.println("You had no playable cards, so you drew a card. Turn Over");
                setValidTurn(true);
                //Pause for 6 seconds
                try{
                    Thread.sleep(6000);
                }catch(InterruptedException e){
                    System.out.println("got interrupted!");
                }
                break;
            }

            System.out.println("Choose a valid card or draw");
            int userInput = TextIO.getlnInt();

            if( userInput == 0 ){
                drawCards(1, currentDeck);
                setValidTurn(true);
                break;
            }

            int userInputInMethod = userInput-1;


            //IF THE CARD IS OUTSIDE OF ARRAY
            while ( userInputInMethod + 1 > unoHand.getCardCount() ||  userInputInMethod + 1 <1){
                System.out.println("That card does not exist, try a different card");
                userInputInMethod = TextIO.getlnInt() - 1;
            }

            UnoCard cardChosen = unoHand.getCard(userInputInMethod);
            int cardChosenValue = cardChosen.getValue();

            //WILD CARD CASES
            if(cardChosenValue == 13 || cardChosenValue == 14) {


                System.out.println(unoHand.getCard(userInputInMethod).getValueAsString());
                System.out.println("What color would you like to set to");
                System.out.println("RED = 0");
                System.out.println("GREEN = 1");
                System.out.println("BLUE = 2");
                System.out.println("YELLOW = 3");
                int wildCardChangeColorTo = TextIO.getlnInt();

                while (wildCardChangeColorTo > 3 ||  wildCardChangeColorTo <0){
                    System.out.println("Pick a valid color from your options above.");
                    wildCardChangeColorTo = TextIO.getlnInt();

                }


                //PUT WILDCARD ON THE TOP AND CHANGE COLOR
                setWildCardColorAndDiscard(UnoGame.discardPile, wildCardChangeColorTo, unoHand.getCard(userInputInMethod));
                //FOR WILD DRAW 4, DRAW 4
                if (cardChosenValue == 14) {
                    System.out.println("Dealing out 4 more cards to the other player");
                    otherPlayer.drawCards(4, currentDeck);
                }
                setValidTurn(true);
            }


            else if (!matchesColorOrValue(UnoGame.discardPile.getCardOnTop(), unoHand.getCard(userInputInMethod ))) {
                    System.out.println("You cannot play " + cardChosen + " with " + UnoGame.discardPile.getCardOnTop());

            }

            //DRAW TWO CARD
            else if(cardChosenValue == 12 ){
                playDrawTwo(currentDiscardPile, otherPlayer, currentDeck, currentCardOnTop);
            }

            //SKIP AND REVERSE CARDS
            else if(cardChosenValue == 11){
                discardSkips(UnoGame.discardPile, cardChosen);
            }

            else if(cardChosenValue == 10){
                System.out.println("reverse running");
                discardReverse(UnoGame.discardPile, cardChosen);
            }

            //NON SPECIAL CARDS
            else if(cardChosenValue <= 9){
                if(matchesColorOrValue(UnoGame.discardPile.getCardOnTop(), cardChosen)){
                    setValidTurn(true);
                    discardCard(cardChosen, UnoGame.discardPile);
                }
            }

        }

		 if (playerNumberOfCards == 1 && !getValidTurn() == false){

             System.out.println("Would you like to call UNO");
             System.out.println("0 = YES ");
             System.out.println("1 = NO, I WANT TO STYLE ON MY OPPONENT (You will draw two)");
             int response = TextIO.getlnInt();

             while (response != 0 && response != 1){
                 System.out.println("Pick a valid options from your options above.");
                 response = TextIO.getlnInt();
             }
             if (response == 0){
                 hasClaimedUnoForSelf = true;
             }
        }

        System.out.println("Hand computer to other player ");
        try{
            Thread.sleep(2000);
        }
        catch(InterruptedException e){
            System.out.println("got interrupted!");
        }

	}


}