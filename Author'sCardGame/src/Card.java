/**
 * Card class
 * Created for use in Authors project, Fall 2015
 * Based on the Elevens Lab
 * @author Richard Schenke
 * @version 20151130
 */
// Schenke, October 14, 2015

public class Card
{
    // class attributes
    private String rank;
    private String suit;
    private int pointValue; // (1 to 13 or 2 to 14)
    private int rankNum; // 0 to 12
    private int suitNum; // 0 to 3

    private static final String[] RANKS
        = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K"};
    //  String[] suits = {"spades", "hearts", "diamonds", "clubs"};
    // Unicode values for suit symbols. Works in applet and BlueJ, doesn't work in cmd or DrJava
    private static final String[] SUIT_UNI = {"\u2660","\u2661","\u2662","\u2663"};
    private static final String[] SUIT_INITS = {"S","H","D","C"};
    private static final int[] POINT_VALUES = {1,2,3,4,5,6,7,8,9,10,11,12,13};

    /**
     * Constructor for objects of class Card, using integers for rank and suit.
     * The constructor contains arrays that determine the rank string, suit string and point
     * value, according to the rules of the game.
     * @param  cardRankNum  an integer from 0 to 12
     * @param  cardSuitNum  an integer from 0 to 3
     */
    public Card(int cardRankNum, int cardSuitNum)
    {
        //initializes a new Card with the given rank, suit, and point value
        rankNum = cardRankNum;
        rank = RANKS[rankNum];
        pointValue = POINT_VALUES[rankNum];
        suitNum = cardSuitNum;
        suit =  SUIT_INITS[suitNum];
    }

    /**
     * Get the String for this card's rank.
     * The rank is a one-character String: A for ace, 2 for 2, ..., 9 for 9,
     * X for 10, J for jack, Q for queen and K for king.
     * @return     the String for this card's rank
     */
    public String rank() {return rank;}

    /**
     * Get the String for this card's suit.
     * The suit is a String with a single Unicode character that produces the four symbols
     * in this order: "spades", "hearts", "diamonds", "clubs".
     * The unicode values are (hex) 2660, 2661, 2662 and 2663. These display the symbol in
     * an applet's drawString and in BlueJ terminal window. They do not display correctly in
     * a cmd window or DrJava' console output.
     * @return     the String for this card's suit
     */
    public String suit() {return suit;}

    /**
     * Get the int representing the point value of the card.
     * These values vary according to the game. The game of Authors does not
     * have a hierarchy of ranks - no rank is more important than another.
     * The point values are 1 for Ace, pip count for 2 through 10, 11 for Jack,
     * 12 for Queen, and 13 for King.
     * @return     the point value of the card
     */
    public int pointValue() { return pointValue;}

    /**
     * Provide a String for the Card.
     * The String consists of the rank String, the word " of " and the suit String.
     * Each rank and suit String is one character, so the card output is 6 characters wide.
     */
    public String toString()
    {
        return rank+" of "+suit;
    }

    /**
     * Get the int equivalent for this card's suit.
     * "spades" is 0, "hearts" is 1, "diamonds" is 2, "clubs" is 3.
     *  @return     the int for this card's suit.
     */
    public int suitNum() { return suitNum; }

    /**
     * Get the int equivalent for this card's rank.
     * Ace ("A") is 0, "2" is 1, ..., "9" is 8, ten ("X") is 9, Jack ("J") is 10,
     * Queen ("Q") is 11 and King ("K") is 12.
     * @return     the int for this card's rank.
     */
    public int rankNum() { return rankNum; }

}
