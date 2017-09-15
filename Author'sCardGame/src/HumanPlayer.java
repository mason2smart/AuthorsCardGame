import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import sun.tools.jar.Main;


public class HumanPlayer extends AI{
	JFrame humanFrame;
	JPanel humanPanel;
//	JFrame ImgFrame;
	private JButton select;
	private JComboBox<String> choices;
	private int cardToGet;
	private Boolean cardNotChosen;
	private JLabel img;
	String card = " ";
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
		//ImageIcon AAA =new ImageIcon("cards_jpeg/5D.jpg");
		img = new JLabel();
		//System.out.println("AAA Height" + AAA.getIconHeight());
		//System.out.println("AAA location" + AAA.getDescription());
		img.setLayout(new BorderLayout());

		img.setVisible(true);
				humanFrame = new JFrame();
			//	ImgFrame = new JFrame();
			//	ImgFrame.setPreferredSize(new Dimension(500,100));
			//	ImgFrame.setVisible(true);
			//	ImgFrame.add(img);
			//	ImgFrame.pack();
				humanPanel = new JPanel();
				humanPanel.setLayout(new FlowLayout());
				humanPanel.setBackground(Color.black);
				humanFrame.setVisible(false);
				humanFrame.setTitle("Human Player");
				humanPanel.setPreferredSize(new Dimension(450,800));
				humanPanel.setAlignmentY(TOP_ALIGNMENT);
				humanPanel.setAlignmentX(RIGHT_ALIGNMENT);
				img.setVisible(true);
				humanFrame.add(humanPanel);
				humanFrame.setAlwaysOnTop(true);
				humanFrame.setResizable(false);
				humanPanel.add(choices);
				humanPanel.add(select);
				humanPanel.add(img);
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
		for(int i = 0;i<lim-1;i++) {
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
		addImage();
	}
	public void addImage(){
		Thread a = new Thread(){
			@Override
         public void run(){
				while(cardNotChosen)
				{
					try
					{
						String newCard = (neededCards.get(choices.getSelectedIndex()).rank()+neededCards.get(choices.getSelectedIndex()).suit());
						if(!(newCard.equals(card))){
						   URL url = Main.class.getResource("/resources/cards_jpeg/"+newCard+".jpg");							
						   System.out.println(url);
							ImageIcon imgIco = new ImageIcon(url);
							imgIco.setImage(imgIco.getImage().getScaledInstance((int) Math.ceil(imgIco.getIconWidth()/3*2), (int) Math.ceil(imgIco.getIconHeight()/3*2), Image.SCALE_DEFAULT));
							System.out.println(imgIco.getDescription());
							System.out.println("ICON WIDTH" + imgIco.getIconWidth());
							img.setIcon(imgIco);
							card = newCard;
							humanFrame.pack();
						}
					}
				catch (Exception e)
				{
						System.err.println("IndexOutOfBoundsException: " + e.getMessage());
						System.out.println("null pointer --  cant compare cards for image");
						waits(1000);
					}
				}
			}};a.run();
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
			cardNotChosen=false;
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
				if(secondaryPref+primaryPref>3)
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
					if (hand.size()>1)
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


