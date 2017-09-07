import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class HumanPlayer extends AI{
	JFrame humanFrame;
	JPanel humanPanel;
	private JButton select;
	private JComboBox<String> choices;
	private int cardToGet;
	private Boolean cardNotChosen;

	public HumanPlayer(int x, ArrayList<String> history)
	{
		super(x, history);
		initHumanPanel();	
	}
	@Override
   public void initPlayerPanel()//each player has own statistics panel
	{
		super.initPlayerPanel();
	}
	public void initHumanPanel()
	{
		 choices = new JComboBox<String>();
		 select = new JButton("Submit"); 

				humanFrame = new JFrame();		
				humanPanel = new JPanel();
				humanFrame.setVisible(false);
				humanFrame.setTitle("Human Player");
				humanPanel.setPreferredSize(new Dimension(300,300));
				humanPanel.setAlignmentY(TOP_ALIGNMENT);
				humanPanel.setAlignmentX(RIGHT_ALIGNMENT);
				humanFrame.add(humanPanel);
				humanFrame.setResizable(false);
				humanFrame.setAlwaysOnTop(true);
				 humanPanel.add(choices);
				 humanPanel.add(select);
				 humanFrame.pack();
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
		humanFrame.setVisible(false);
		choices.removeAllItems();
	}
	public void initComboChoice(ArrayList<Card> hand, int lim)
	{
		cardNotChosen=true;
		for(int i = 0;i<lim-1;i++)
		{
			choices.addItem(neededCards.get(i).toString());
		}
		humanFrame.pack();
		humanFrame.setVisible(true);
			select.addActionListener(new ActionListener()
			{
				
				@Override
            public void actionPerformed(ActionEvent e)
				{
			                    select.setText("Requesting Card...");
			                    cardToGet=choices.getSelectedIndex();
			                    new Thread() {
			                       @Override
                              public void run(){
		                           cardNotChosen=false;
			                       }
			                    }.run();
				}}	);
			
			select.setText("Submit");
	}
	@Override
   public boolean RequestCard(AI robot)//requests card from other player
	{
	   cardNotChosen=true;
		super.analyzeHand();
		waits(500);
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
				   waits(500); //refreshes while loop
            //   if (System.currentTimeMillis()%10==0)
              // {
               //   System.out.println("card-not-chosen loop");
               //}
				} 
				
			//	return neededCards.get(primaryPref);
			}
			else
				if(secondaryPref>3)
				{
					initComboChoice(neededCards, primaryPref+secondaryPref);
					while(cardNotChosen)
					{
		             waits(500); //refreshes while loop
					}
			//		neededCards.get(cardToGet);
				}
				else
				{
					if (isEmpty()==false)
					{
					initComboChoice(neededCards, neededCards.size());

			//		return neededCards.get(cardToGet);
					}
					while(cardNotChosen)
					{
	               waits(500); //refreshes while loop

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
	
	public void waits(long waitT)
	{
		long endTime=System.currentTimeMillis()+waitT;
		while (System.currentTimeMillis()<=endTime)
		{
			
		}
	}

}

