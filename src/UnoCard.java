public class UnoCard{

    public final static int RED = 0;
    public final static int GREEN = 1;
    public final static int BLUE = 2;
    public final static int YELLOW = 3;
    public final static int NOCOLOR = -1;


    private int color;
    private final int value;

    public UnoCard(int value, int color){
        if (color != BLUE && color != RED && color != YELLOW &&
                color != GREEN && color != NOCOLOR){
            throw new IllegalArgumentException("Illegal playing card color");
        }

        if (value < 0 || value > 15){
            throw new IllegalArgumentException("Illegal playing card value");
        }
        this.value = value;
        this.color = color;
    }

    public int getValue(){
        return this.value;
    }

    public int getColor(){
        return this.color;
    }

    // for setting the color of wild cards dynamically
    public void setColor(int color){
        this.color = color;
    }

    public String getColorAsString() {
        switch (color) {
            case RED:     return "Red";
            case GREEN:   return "Green";
            case BLUE:    return "Blue";
            case YELLOW:  return "Yellow";
            case NOCOLOR: return "NONE";

            default:  return "error";
        }
    }

    public String getValueAsString() {

        switch (value) {
            case 0:   return "0";
            case 1:   return "1";
            case 2:   return "2";
            case 3:   return "3";
            case 4:   return "4";
            case 5:   return "5";
            case 6:   return "6";
            case 7:   return "7";
            case 8:   return "8";
            case 9:   return "9";
            case 10:  return "Reverse";
            case 11:  return "Skip";
            case 12:  return "Draw 2";
            case 13:  return "Wild";
            case 14:  return "Wild Draw 4";
            default:  return "error";
        }
    }

    public String toString() {
        if (getColorAsString().equals("NONE")){
            return getValueAsString();
        }
        else{
            return getColorAsString() + " " + getValueAsString();
        }
    }


}