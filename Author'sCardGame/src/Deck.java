// Deck.java
// modified from Elevens to use in Authors
// Cards are created from numbers, not Strings

/**
 * The Deck class represents a shuffled deck of cards.
 * It provides several operations including initialize, shuffle, deal, and check if empty.
 * This version is based on the Elevens lab, modified for the Authors card game.
 * @author Richard Schenke
 * @version 20151130
 */
public class Deck
{
 /**
  * cards contains all the cards in the deck.
  */
 private Card[] cards;

 /**
  * size is the number of not-yet-dealt cards.
  * Cards are dealt from the top (highest index) down.
  * The next card to be dealt is at size - 1.
  */
 private int size;


 /**
  * Creates a new <code>Deck</code> instance.<BR>
  * It pairs each element of ranks with each element of suits,
  * and produces one of the corresponding card.
  * The Card constructor has the String arrays of ranks and suits
  * and the int array of point values.
  * @param numRanks is the number of ranks.
  * @param numSuits is the number of suits.
  */
 public Deck(int numRanks, int numSuits)
 {

  int index = 0;
  cards = new Card[numRanks*numSuits];
  for (int s=0; s<numSuits; s++)
  {
   for (int r=0; r<numRanks; r++)
   {
    cards[index] = new Card(r, s);
    index++;
   }
  }
  size = index;
  shuffle();
 }


 /**
  * Determines if this deck is empty (no undealt cards).
  * @return true if this deck is empty, false otherwise.
  */
 public boolean isEmpty()
 {
  return (size<=0);
 }

 /**
  * Accesses the number of undealt cards in this deck.
  * @return the number of undealt cards in this deck.
  */
 public int size()
 {
  return size;
 }

 /**
  * Randomly permute the given collection of cards
  * and reset the size to represent the entire deck.
  */
 public void shuffle()
 {
  Card temp;
  int range = cards.length-1;
  int select;
  for (int i=cards.length-1; i>=0; i--)
  {
   // swap card i with another one randomly selected
   // from the unshuffled cards
   select = (int)(Math.random()*range);
   temp = cards[select];
   cards[select] = cards[i];
   cards[i] = temp;
   range--;
  }

 }

 /**
  * Deals a card from this deck.
  * @return the card just dealt, or null if all the cards have been
  *         previously dealt.
  */
 public Card deal()
 {
  Card next;
  if (isEmpty()) next = null;
  else
  {
   next = cards[size-1];
   size--;
  }
  return next;
 }

 /**
  * Generates and returns a string representation of this deck.
  * @return a string representation of this deck.
  */
// @Override
/*
 * advanced version
 */
 public String toString()
 {
  String rtn = "size = " + size + "\nUndealt cards: \n";

  for (int k = size - 1; k >= 0; k--) {
   rtn = rtn + String.format("%-9s" ,cards[k]);
   if ((size - k) % 8 == 0)
   {
    // Insert carriage returns so entire deck is visible on console.
    rtn = rtn + "\n";
   }
  }

  rtn = rtn + "\nDealt cards: \n";
  for (int k = cards.length - 1; k >= size; k--) {

   rtn = rtn + String.format("%-9s" ,cards[k]);
   if ((cards.length - k) % 8 == 0)
   {
    // Insert carriage returns so entire deck is visible on console.
    rtn = rtn + "\n";
   }
  }

  rtn = rtn + "\n";
  return rtn;
 }

 /**
  * Generates and returns a string representation of this deck.
  * @return a string representation of this deck.
  */
 /*
  * simple version
  *
//@Override
 public String toString()
 {
//     String rtn = "size = " + size + "\nUndealt cards: \n";
     String rtn = "";
     for (int k = size - 1; k >= 0; k--)
     {
         rtn = rtn + cards[k]+ "\n";
     }
     return rtn;
  }
*/
 /*
  * method getDeck allows a subclass to access the parent's deck
  */
//  public Card[] getDeck() { return cards; }
}
