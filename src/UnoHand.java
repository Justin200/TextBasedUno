import java.util.ArrayList;
public class UnoHand {

    private ArrayList<UnoCard> hand = new ArrayList<UnoCard>(108);

    public UnoHand() {

    }

    public void clear() {
        hand.clear();
    }

    public void addCard(UnoCard c) {
        if (c == null)
            throw new NullPointerException("Can't add a null card to a hand.");
        hand.add(c);

    }

    public void removeCard(UnoCard c) {
        hand.remove(c);
    }


    public void removeCard(int position) {
        if (position < 0 || position >= hand.size()) {
            throw new IllegalArgumentException("Position does not exist in hand: "
                    + position);
        }
        hand.remove(position);
    }


    public int getCardCount() {
        return hand.size();
    }

    public UnoCard getCard(int position) {
        if (position < 0 || position >= hand.size())
            throw new IllegalArgumentException("Position does not exist in hand: "
                    + position);
        return hand.get(position);
    }


    public void sortByColor() {
        ArrayList<UnoCard> newHand = new ArrayList<UnoCard>();
        while (hand.size() > 0) {
            int pos = 0;  // Position of minimal card.
            UnoCard c = hand.get(0);  // Minimal card.
            for (int i = 1; i < hand.size(); i++) {
                UnoCard c1 = hand.get(i);
                if ( c1.getColor() < c.getColor() ||
                        (c1.getColor() == c.getColor() && c1.getValue() < c.getValue()) ) {
                    pos = i;
                    c = c1;
                }
            }
            hand.remove(pos);
            newHand.add(c);
        }
        hand = newHand;
    }

    public void sortByValue() {
        ArrayList<UnoCard> newHand = new ArrayList<UnoCard>();
        while (hand.size() > 0) {
            int pos = 0;  // Position of minimal card.
            UnoCard c = hand.get(0);  // Minimal card.
            for (int i = 1; i < hand.size(); i++) {
                UnoCard c1 = hand.get(i);
                if ( c1.getValue() < c.getValue() ||
                        (c1.getValue() == c.getValue() && c1.getColor() < c.getColor()) ) {
                    pos = i;
                    c = c1;
                }
            }
            hand.remove(pos);
            newHand.add(c);
        }
        hand = newHand;
    }

    public String listCards(){
        String str = "";
        for(int i = 0; i < hand.size(); i++) {

            str += i+1 + ". ";
            str += hand.get(i).toString();
            str += "    ";

            if(i > 0 && i % 4 == 0)
                str += "\n";
        }
        return str;
    }

}