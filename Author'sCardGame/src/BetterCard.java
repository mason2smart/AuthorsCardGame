
public class BetterCard extends Card { //adds a method to compare card objects

   public BetterCard(int cardRankNum, int CardSuitNum) {
      super(cardRankNum, CardSuitNum);
   }
   public BetterCard(Card MyCard)//take a card, trash it, and use values to create a new and better card
   {
      super(MyCard.rankNum(), MyCard.suitNum());

   }

   @Override
   public boolean equals(Object obj)//overrides equals method of object superclass, everything extends the object superclass. obj is object compared to this
   {
      if (this == obj) {
         return true;
      }
      if (obj == null) {
         return false;
      }
      if (this.getClass() != obj.getClass()) {
         return false;
      }
      BetterCard other = (BetterCard) obj; //casting into other
      if (rankNum() != other.rankNum()) {
         return false;
      }
      if (suitNum() != other.suitNum()) {
         return false;
      }
      return true; //if get this far, return true b/c both equal

   }
}
