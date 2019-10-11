import java.lang.reflect.Array;
import java.util.*;
public class UnoDeck{

	private int cardsUsed = 0;
    private int NOCOLOR = -1;

	private ArrayList<UnoCard> deck = new ArrayList<UnoCard>(108);

    public UnoDeck(UnoCard firstCard) {
        addToDeck(firstCard);
    }

    // constructor
    public UnoDeck() {

        // create half the cards
		int cardCt = 0; // How many cards have been created so far.
		for(int currentColor = 0; currentColor <= 3; currentColor++) {
			for(int currentValue = 0; currentValue <= 14; currentValue++) {

                // do I need to pass a color?
                UnoCard newCard;
                if (doesCardNeedColor(currentValue)){
                    newCard = new UnoCard(currentValue, currentColor);
                }
                else{
                    newCard = new UnoCard(currentValue, NOCOLOR);

                }
                addToDeck(newCard);

                cardCt++;
			}
		}

		// create second half of cards needed
        for(int currentColor = 0; currentColor <= 3; currentColor++) {
            for(int currentValue = 1; currentValue <= 12; currentValue++) {

                UnoCard newCard;
                if (doesCardNeedColor(currentValue)){
                    newCard = new UnoCard(currentValue, currentColor);
                }
                else{
                    newCard = new UnoCard(currentValue, NOCOLOR);
                }
                addToDeck(newCard);

                cardCt++;
            }
        }
        cardsUsed = 0;
	}

    public boolean doesCardNeedColor(int card){
        if (card == 13 || card == 14){
            return false;
        }
        else{
            return true;
        }
    }

    public void addToDeck(UnoCard card){
        this.deck.add(card);
    }


    public UnoCard getCardOnTop(){
        UnoCard currentCard = this.deck.get(deck.size()-1);
        return currentCard;
    }


    public ArrayList<UnoCard> getDeck(){
        return this.deck;
    }


	public void shuffleDeck(int timesToShuffle){
                //Gives cards to the players
        for (int i = 0; i < timesToShuffle; i++ ){
            Collections.shuffle(deck);
        }

	}

    public int deckSize() {
        return deck.size();
    }

	public int cardsLeft() {
		return deck.size() - cardsUsed;
	}

    public UnoCard dealOneCard(){
        UnoCard currentCard = this.deck.get(0);
        this.deck.remove(0);
        return currentCard;
    }


	public String toString() {
		String str = "";
		for(int i = 0; i < deck.size(); i++) {

			str += deck.get(i).toString();
            String thisCard = deck.get(i).toString();
            if(i == deck.size() - 1)
				str += ".";
			else
			    str += ", ";

			if(i > 0 && i % 4 == 0)
				str += "\n";
		}
		return str;
	}


}