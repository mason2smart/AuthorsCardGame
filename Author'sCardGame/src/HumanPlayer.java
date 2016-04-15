import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class HumanPlayer extends AI{
	JFrame humanFrame;
	JPanel humanPanel;
	private JButton select;
	private JComboBox choices;
	private int cardToGet;
	private Boolean cardNotChosen;
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
		JComboBox choices = new JComboBox();
		 select = new JButton("Submit"); 

				humanFrame = new JFrame();		
				humanPanel = new JPanel();
				humanFrame.setVisible(true);
				humanFrame.setTitle("Human Player");
				humanPanel.setSize(300,300);
				humanFrame.setSize(320, 320);
				humanPanel.setAlignmentY(TOP_ALIGNMENT);
				humanPanel.setAlignmentX(RIGHT_ALIGNMENT);
				humanFrame.add(humanPanel);
				 humanPanel.add(choices);
				 humanPanel.add(select);
				try//try to get system look for gui interface
				{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}
				catch (Exception ex)//error exception
        {
            ex.printStackTrace();
      
	}}
	public void closeHumanFrame()
	{
		cardNotChosen=true;
		humanPanel.removeAll();
		humanFrame.dispose();
	}
	public void initComboChoice(ArrayList<Card> hand, int lim)
	{
		cardNotChosen=true;
		JComboBox choices = new JComboBox();
		for(int i = 0;i<lim-1;i++)
		{
			choices.addItem(hand.get(i));
		}
			select.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					   new Thread(){//does it in new thread not to freeze up GUI() interface
		                public void run(){
		                  try {
			                    select.setText("Requesting Card...");
			                    cardToGet=choices.getSelectedIndex();
		                    Thread.sleep(1000);
							cardNotChosen=false;
		                    this.interrupt();//ends thread
		                 } catch (InterruptedException exc) {
		                	 System.out.println("Thread Error on StartGame Btn");
		               }
		             }
		           }.start();
				}}	);
	}
	public boolean RequestCard(AI robot)//requests card from other player
	{
		initHumanPanel();
		super.analyzeHand();
		 cardToGet=0;
		int x = neededCards.size();
		if(hand.size()==1)//fixes out of bounds error
		{
			cardToGet=0;
		}
		else
			
		if (primaryPref>3)
			{
				initComboChoice(hand,primaryPref);
				while(cardNotChosen)
				{
					
				}
			//	return neededCards.get(primaryPref);
			}
			else
				if(secondaryPref>3)
				{
					initComboChoice(hand, primaryPref+secondaryPref);
					initComboChoice(hand,primaryPref);
					while(cardNotChosen)
					{
						
					}
			//		neededCards.get(cardToGet);
				}
				else
				{
					if (isEmpty()==false)
					{
					initComboChoice(hand, neededCards.size());
					while(cardNotChosen)
					{
						
					}
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
				closeHumanFrame();/////////////////////////////////////////////////////////////MAY WANT TO FIND MORE EFFICIENT WAY OF DOING THIS
				return true;//can ask another player for card
			}
			}
			closeHumanFrame(); /////////////////////////////////////////////////////////////MAY WANT TO FIND MORE EFFICIENT WAY OF DOING THIS
			return false;//cannot ask another player for card
		
	}

}


