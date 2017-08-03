import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class AI extends JPanel {
	private ArrayList<Card> hand;
	private int handAnalysis[][];
	private ArrayList<Card> neededCards;
	JPanel playerPanel;
	JScrollPane neededScrollPane;
	JList<String> neededList;
	private boolean endsTurn=false;
	static int debug=0;
	String[] NeededCard;
	int playerNum;
	ArrayList<Card> Books;
	ArrayList<Integer> removeLoc;
	private int NumBooks=0;
	int primaryPref=0;
	int secondaryPref = 0;
	int tertiaryPref = 0;
	String[] StringHand;
	ArrayList<String> history;
	private boolean StillRunning = true;//used by card game to end threads
public AI(int x, ArrayList<String> history)
{	
this.history = history;
playerNum = x;
initArrays();
initPlayerPanel();
updateGUI();
bookSearch();
}
public void SetRunning(boolean StillRunning)//has user quit game?
{
	this.StillRunning=StillRunning;
}
public void initArrays()
{
	Books = new ArrayList<Card>();
	NeededCard = new String[]{"Loading..."};
	hand = new ArrayList<Card>()
			{
		public String toString()
		{
			String returns = "";
			for(Card element: this)
			{ 
				returns = returns.concat(element.toString());
			}
			return returns;
		}
		
			};
	StringHand = new String[hand.size()];
	handAnalysis = new int[13][5];
	neededCards=new ArrayList<Card>()
			{
				public String toString()
				{
					String returns = "";
					for(Card element: this)
					{ 
						returns = returns.concat(element.toString()+"\t");
					}
					return returns;
			}
};
}
public void initPlayerPanel()//each player has own statistics panel
{
	JPanel SuperPanel = new JPanel();
	SuperPanel.setPreferredSize(new Dimension((int)(400*CardGame.widthMulti),(int)(800*CardGame.heightMulti)));
	SuperPanel.setLayout(new FlowLayout(3,50,5));//alignment, horizontal gap, vertical gap
	playerPanel = new JPanel();
	playerPanel.setLayout(new GridLayout(1,1));//grid layout for pane for adding buttons - sets number of rows
	playerPanel.setBackground(Color.black);//sets secondary panel dark grey
	playerPanel.setOpaque(false);//removed opacity from btnPane to improve GUI Look...
	//playerPanel.setSize(playerPanel.getPreferredSize());
	neededList = new JList<String>(NeededCard);
	//create scroll pane
	JLabel Needed = new JLabel("cards needed in order of importance:");
	neededScrollPane = new JScrollPane(neededList);
	neededScrollPane.getViewport().setBackground(Color.darkGray);//scrollpane Background
	neededScrollPane.getViewport().setBackground(Color.green);//scrollpane Foreground
	playerPanel.add(neededScrollPane);
	SuperPanel.add(Needed);
	SuperPanel.add(playerPanel);
	SuperPanel.setOpaque(true);
	this.add(SuperPanel);
	this.setOpaque(true);
}

public ArrayList<Card> getHand() {
	return hand;
}
public Boolean TransferCard(Card aCard) {// transfers card to player and checks if card requested is in deck
	
	StringHand = new String[hand.size()];
	for (int h=0; h<StringHand.length;h++)
	{
		StringHand[h]=hand.get(h).toString();
	}
	for(int h=0;h<StringHand.length;h++)
	{
	if(StringHand[h].equals(aCard.toString())==true)
	{
		System.out.println("Transferred " + aCard.toString());
		history.add("  Successfully Transferred: " + aCard.toString());
		hand.remove(aCard);
	return true;
	}
	}
	//else
	
	System.out.print("Card Not in Hand Err: ");
	history.add("  Card Not in Hand Err");
	return false;
	}

public void addCard(Card card)
{
	hand.add(card);
}
private void analyzeHand()
{
	primaryPref = 0;//how many cards it needs most
	secondaryPref =0;//how many cards it needs second most
	tertiaryPref = 0;//how many cards it needs third most
	debug++;
	for(int x = 0; x<handAnalysis.length;x++)
	{
		for (int y = 0; y<handAnalysis[0].length;y++)
		{
				handAnalysis[x][y]=0; //resets array
		}
	}
	for(int x=0; x<hand.size(); x++)
	{
		handAnalysis[hand.get(x).rankNum()][hand.get(x).suitNum()]=x+1;//adds to 2d array list to see what ranks it 
		//has the most of and their suites														 	//sets the value to the card's index in the ArrayList(Hand) + 1 
	}
	for(int x = 0; x<handAnalysis.length; x++)
	{
		for(int y = 0; y<handAnalysis[0].length-1;y++)
		{
			if (handAnalysis[x][y]>0)
		handAnalysis[x][4]=handAnalysis[x][4]+1;//add counter to bottom row for the number of cards in column
		}
		}
		neededCards.clear();//resets neededCard list
	for(int x = 0; x<handAnalysis.length; x++)
	{
		for(int y = 0; y<handAnalysis[0].length-1;y++)
		{
			if (handAnalysis[x][y]==0 && handAnalysis[x][4]==3)
			{
				//adds missing cards based on priority, eg. first cards are closest to completing a stack.
				neededCards.add(0, new BetterCard(x,y));
				primaryPref++;
			}
		}}
		for(int x = 0; x<handAnalysis.length; x++)
		{
			for(int y = 0; y<handAnalysis[0].length-1;y++)
			{
				if (handAnalysis[x][y]==0 && handAnalysis[x][4]==2)
				{
					//adds missing cards based on priority, eg. first cards are closest to completing a stack.
					neededCards.add(neededCards.size(), new BetterCard(x,y));		
					secondaryPref++;
				}
			}
		}
		for(int x = 0; x<handAnalysis.length; x++)
		{
			for(int y = 0; y<handAnalysis[0].length-1;y++)
			{
				if (handAnalysis[x][y]==0 && handAnalysis[x][4]==1)
				{
					//adds missing cards based on priority, eg. first cards are closest to completing a stack.
					neededCards.add(neededCards.size(), new BetterCard(x,y));	
					tertiaryPref++;
				}
			}
		}
	System.out.println("DEBUG: \n" + debug + "   "+ neededCards.toString());
	}
public void updateGUI()//refreshes gui by running analyze hand and pushing results to window
{ 
	Thread pushGui = new Thread()
		{
	public void run()
	{
		while (StillRunning)
		{
		try{
		analyzeHand();
		bookSearch();
		NeededCard = new String[neededCards.size()];
		System.out.println("Gui AI Player " + playerNum + "  Debug: "+NeededCard.length);
		if (hand.isEmpty()==false)
		{
			for(int x=0; x<neededCards.size();x++)//Makes Wanted Card Objects into Strings for GUI
			{
				NeededCard[x]=neededCards.get(x).toString();
			}
			neededList.setListData(NeededCard);
		}
		Thread.sleep(500);//slows it down so it not overload system
	}
	catch(Exception e3)
	{
		e3.printStackTrace();
	}
	}}
		};pushGui.start();
}

public void bookSearch()//******************may want to run and repeat ANALYZE HAND IN THIS FOR SIMPLIFICATION
{
	removeLoc = new ArrayList<Integer>();
	Thread cardIndexer = new Thread()
			{
		public void run()
		{
			for (int x=0; x<handAnalysis.length;x++)//pull out books
			{		
				if (handAnalysis[x][4]==4)
			{
			for(int y = 0; y<handAnalysis[x].length-1;y++)
			{
				Books.add(hand.get(handAnalysis[x][y]-1));
				removeLoc.add(handAnalysis[x][y]-1);//store location of card to be removed from hand
			}
			}}
				removeLoc.trimToSize();
			   Collections.sort(removeLoc);
			if (removeLoc.size()>=0)//prevents ArrayIndexOutOfBoundsError;
			{
			for(int z = removeLoc.size()-1; z>=0; z--)
			{
						System.out.println("Removing//// " + removeLoc.get(z));
					   RemoveFromHand(removeLoc.get(z));
			//Done backwards to prevent going outside array
			}
			}
			removeLoc.clear();//clear array w/ Hand arrayList locations
			removeLoc.trimToSize();
			System.out.println("RemoveLoc.size  "+removeLoc.size());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StringHand = new String[hand.size()];
			for (int h=0; h<StringHand.length;h++)
			{
				StringHand[h]=hand.get(h).toString();
			}
		}
	
};cardIndexer.start();}
public void initTurn()
{
	System.out.println("Player " + playerNum + " Hand:  " + hand.toString());
	endsTurn=true;
}
public void RemoveFromHand(int index)
{
	hand.remove(index);
}
public boolean isEmpty()
{
	if(hand.isEmpty()==true)
	{
		return true;
	}
	else
	{
		return false;
	}
}
public boolean endTurn()
{
	return endsTurn;
}

public boolean RequestCard(AI robot)//requests card from other player
{
	analyzeHand();
	Random randy = new Random();
	int cardToGet=0;
	int x = neededCards.size();
	if(hand.size()==1)//fixes out of bounds error
	{
		cardToGet=0;
	}
	else
	if (primaryPref>3)
		{
			cardToGet = randy.nextInt(primaryPref);
		//	return neededCards.get(primaryPref);
		}
		else
			if(secondaryPref>3)
			{
				cardToGet = randy.nextInt(primaryPref+secondaryPref);
		//		neededCards.get(cardToGet);
			}
			else
			{
				if (isEmpty()==false)
				{
				cardToGet = randy.nextInt(neededCards.size());
		//		return neededCards.get(cardToGet);
				}
				
			}
		if(isEmpty()==false)
		{
		history.add("Player " + playerNum + " requesting card " + neededCards.get(cardToGet).toString() + " from " + robot.playerNum);
		

		if 	(robot.TransferCard(neededCards.get(cardToGet))==true)
		{
			hand.add(neededCards.get(cardToGet));
			System.out.println("getting card " + neededCards.get(cardToGet).toString());
			return true;//can ask another player for card
		}
		}
		return false;//cannot ask another player for card
		
}
		
public int getNumBooks() {//returns number of books the player created
	return NumBooks;
}
/*
 * public void dispOrderedHand()
{
	for(int x = 0; x<handAnalysis.length; x++)
	{
		for(int y = 0; y<handAnalysis[0].length-1;y++)
		{
			if (handAnalysis[x][y]>0)
		handAnalysis[x][4]=handAnalysis[x][4]+1;
		}
	}		
	}
*/

}
