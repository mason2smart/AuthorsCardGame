import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class HumanPlayer extends AI{
	JFrame humanFrame;
	JPanel humanPanel;
	public HumanPlayer(int x, ArrayList<String> history)
	{
		super(x, history);
	}
	public void initPlayerPanel()//each player has own statistics panel
	{
		super.initPlayerPanel();
	}
	public void initHumanPanel()
	{
		
				humanFrame = new JFrame();		
				humanPanel = new JPanel();
				humanFrame.setVisible(true);
				humanFrame.setTitle("Human Player");
				humanPanel.setSize(300,300);
				humanPanel.setAlignmentY(TOP_ALIGNMENT);
				humanPanel.setAlignmentX(RIGHT_ALIGNMENT);
				humanFrame.add(humanPanel);
				try//try to get system look for gui interface
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}
				catch (Exception ex)//error exception
        {
            ex.printStackTrace();
      
	}}
	public void closeHumanPanel()
	{
		humanFrame.dispose();
	}
	public void initComboChoice()
	{
		
	}
	public boolean RequestCard(AI robot)//requests card from other player
	{
		initHumanPanel();
		super.analyzeHand();
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
				closeHumanPanel();/////////////////////////////////////////////////////////////MAY WANT TO FIND MORE EFFICIENT WAY OF DOING THIS
				return true;//can ask another player for card
			}
			}
			closeHumanPanel(); /////////////////////////////////////////////////////////////MAY WANT TO FIND MORE EFFICIENT WAY OF DOING THIS
			return false;//cannot ask another player for card
		
	}

}


