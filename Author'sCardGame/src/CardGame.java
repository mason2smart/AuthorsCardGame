import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

class CardGame extends JFrame {
//	protected JComboBox<String> CardOptions;
	Runnable runRotation;
	
	public static final int width = 800;
	public static final int height = 800;
	ArrayList<AI> Players;
	int numGame;
	JPanel btnPane;
	JPanel layout;
	JButton startG;
	int numPlayers;
	String[] Choices = {};
	private int playerTurn=0;//whose turn is it?
	private Deck gameDeck;
	private Card selectedCard; //which card has the player selected
	private JPanel temp;
	private JLabel NumPlayer;
	Font GamerFont;
	JPanel Books;
	ArrayList<JLabel> BooksList;//stores the JLabels that will be shown in Books Pane
	int playerIndex;
	ArrayList<String> History;//will be accessible by AI and will record the cards gotten or not gotten from other players
	private JTextArea histArea;
	private JScrollPane histPane;
	boolean StillRunning=true;//if window is not shut = true
	boolean gameOver = false;//if all players out of hands = true
   	Thread Rotation;
   	private int playerturn;
   	public static double heightMulti;

	public static double widthMulti;
   	public CardGame(int numGame, int numPlayers, ArrayList<CardGame> CG) 
	{	

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		widthMulti = screenSize.getWidth()/1920;
		heightMulti = screenSize.getHeight()/1080;
		
   	  addWindowListener(new WindowAdapter(){//when closed
      	@Override
         public void windowClosing(WindowEvent e){
      		StillRunning=false;//kill threads in this class
      		for(int x=0; x<Players.size();x++)
      		{
      			Players.get(x).SetRunning(StillRunning); //kill threads in players
      		}
      	}
       });
	
   		History = new ArrayList<String>()   				
   		{
   					@Override
                  public String toString()//overrides toString to make it a multi-line list
   					{
   						String returns = "";
   						for (int x=0;x<this.size();x++)
   						{
   							returns = returns.concat(this.get(x))+"\n";
   						}
   						return returns;
   					}
   				};
   		Books = new JPanel();
   		BooksList = new ArrayList<JLabel>();
		Players = new ArrayList<AI>();
		this.numGame = numGame;
		this.numPlayers = numPlayers;
		NumPlayer = new JLabel();
		initGame();
		temp = new JPanel();
		temp.setOpaque(false);
		layout = new JPanel();
		btnPane = new JPanel();
		try//try to get system look for gui interface
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
		  catch (Exception ex)//error exception
        {
            ex.printStackTrace();
        }	
		gui();
		layout.add(temp);
		this.add(layout);//adding J Elements to JFrame
		this.add(Books, BorderLayout.SOUTH);
		layout.add(histPane, BorderLayout.EAST);
		this.pack();
        setVisible(true);//sets Jframe visible
        Rotation = new Thread(){//constantly rotates through players
			@Override
         public void run()
			{	
				while(StillRunning)
			{
				rotatePlayers();

			}
				}				
		};Rotation.start();
		Thread CheckforWinner = new Thread()//checks to see if all players are out of hands
				{
					@Override
               public void run()
					{
					while(StillRunning)
					{
						checkGameOver();
					}
				}};CheckforWinner.start();
}
	public void createPlayers(int numPlayers)//create the desired number of players
	{
		if(numPlayers>4)
		{
		for (int x=0; x<numPlayers; x++)
		{
			Players.add(new AI(x, History));//gives history ArrayList to players to update as well as unique player number
		}
		}
		else
		{
			Random randy = new Random();
			 playerturn = randy.nextInt(numPlayers);//assigns human player a random turn
			for (int x=0; x<playerturn; x++)
			{
				Players.add(new AI(x, History));//gives history ArrayList to players to update as well as unique player number
			}
			Players.add(new HumanPlayer(playerturn,History));
			for (int x = playerturn+1; x<numPlayers;x++)
			{
				Players.add(new AI(x, History));//gives history ArrayList to players to update as well as unique player number

			}
	}}
	public void gameHistory()//updates game history window
	{
		histArea = new JTextArea();
		histArea = new JTextArea(History.toString(),5,50);
   	    histArea.setLineWrap(true);
   	    histArea.setSize(new Dimension((int)(200*widthMulti),(int)(400*heightMulti)));
   	    histArea.setPreferredSize(new Dimension((int)(widthMulti*200),(int)(heightMulti*30000)));
		histPane = new JScrollPane(histArea);//adds text area
		histPane.setPreferredSize(new Dimension((int)(widthMulti*200),(int)(heightMulti*390)));
		histPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);//add scrollbar


		
	}
	public void gui()
	{
		try {
			GamerFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("A.ttf"));//set font
		     NumPlayer.setFont(GamerFont.deriveFont((float)(16*widthMulti)));
		     Books.setFont(GamerFont.deriveFont((float)(14*widthMulti)));
		} catch (FontFormatException e1) {
			e1.printStackTrace();} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Books.setLayout(new GridLayout(2,4));
		Books.setAlignmentY(CENTER_ALIGNMENT);
		Books.setAlignmentX(CENTER_ALIGNMENT);
		Books.setPreferredSize(new Dimension((int)(WIDTH*widthMulti), (int)(heightMulti*100)));
		gameHistory();
		Books.setBackground(Color.darkGray);
		DispBooks();
		Color GamerColor = new Color(71,225,12);//special green
		NumPlayer.setForeground(GamerColor);
		layout.setBackground(Color.BLACK);//sets main panel background black
		layout.setLayout(new FlowLayout(1,2,10));//Flow Layout wraps the grid layout keeping grid layout objects from expanding to full height
		btnPane.setLayout(new GridLayout(1,1));//grid layout for pane for adding buttons - sets number of rows
		btnPane.setBackground(Color.darkGray);//sets secondary panel dark grey
		btnPane.setOpaque(true);//removed opacity from btnPane to improve GUI Look...
		layout.add(btnPane, BorderLayout.NORTH);
     //   layout.add(new JSeparator(), BorderLayout.CENTER);//separator top and bottom
		setTitle("Card Game #"+numGame);//sets window title
         Dimension preferred = new Dimension((int)(widthMulti*width),(int)(heightMulti*height));//preferred dimensions
         this.setResizable(false);//dimensions unchangeable by user
         this.setPreferredSize(preferred);
        // setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);//or JFrame.Exit_ON_CLOSE// keeps entire system from exiting on close
	}
	public void DispBooks()//displays the number of books each player has
	{
		for (int x=0;x<numPlayers;x++)
		{
			if (x!=playerturn)
			{
			BooksList.add(new JLabel("Player  "+ x + "   Books Made:  "+(Players.get(x).Books.size()/4+ "     ")));
			}
			else
			{
			BooksList.add(new JLabel("Human   "+ x + "   Books Made:  "+(Players.get(x).Books.size()/4+ "     ")));
			}
			BooksList.get(x).setFont(GamerFont.deriveFont((float)(14*widthMulti)));
			BooksList.get(x).setForeground(Color.green);
			BooksList.get(x).setPreferredSize(new Dimension(200,200));
			Books.add(BooksList.get(x));
		}
	}
	public void UpdateBooks()//refreshes the number of books shown
	{
		for (int x=0;x<numPlayers;x++)
		{
			if (x!=playerturn)
			{
			BooksList.set(x, new JLabel("Player  "+ x + "   Books Made:  "+(Players.get(x).Books.size()/4+ "     ")));
			}
			else
			{
			BooksList.set(x, new JLabel("Human   "+ x + "   Books Made:  "+(Players.get(x).Books.size()/4+ "     ")));
			}
			BooksList.get(x).setFont(GamerFont.deriveFont((float)(14*widthMulti)));
			BooksList.get(x).setForeground(Color.green);
			BooksList.get(x).setPreferredSize(new Dimension((int)(200*widthMulti),(int)(200*heightMulti)));
			Books.remove(x);
			Books.add(BooksList.get(x), x);
			Books.repaint();
		}
		
	}
	public void initGame()
	{
		gameDeck = new Deck(13,4);
		createPlayers(this.numPlayers);
		gameDeck.shuffle();
		DealCards();
	}
	public void checkGameOver()//is the Game Over?
	{
		boolean check = true;
		int notOver=0;
		while(notOver!=Players.size())
		{
			notOver=0;
			for(int gg=0;gg<Players.size();gg++)
		{
			if (check==Players.get(gg).isEmpty())
					{
					notOver++;
					}
		}
		}
		gameOver=true;
		System.out.println("Game Over");


	}
	public void rotatePlayers()//rotates through players turns and refreshes gui
	{  
		while(!gameOver)
		{
		for(int x=0; x<Players.size();x++)
			{	
				boolean gotACard = true;//keeps asking for new cards if keeps getting cards
				playerIndex=0;
				playerTurn = x;
				temp.removeAll();
				System.out.println(NumPlayer.getText());
				NumPlayer.removeAll();
				temp.add(NumPlayer);
				temp.add(Players.get(playerTurn));
				System.out.println("Player: "+x);
            NumPlayer.setText("Player " + playerTurn + "'s Turn");//Updates GUI with the player whose JPanel is shown
				UpdateBooks();
				this.pack();
				Players.get(x).initTurn();
				playerIndex=x+1;
				histArea.setText(History.toString());				
				while(gotACard && !gameOver){
					int stopWhile=0;
					while (playerIndex<=Players.size()&&stopWhile==0)
					{
						if (playerIndex==Players.size())
						{
							playerIndex=0;
						}
						if(Players.get(x).isEmpty()==true)
						{
							System.out.println("Empty");
							stopWhile=1;
						}
						if(Players.get(playerIndex).isEmpty()==false && playerTurn!=playerIndex)
						{
							System.out.println("break");
							stopWhile=1;
						}
						else
						{
						playerIndex++;
						}
					}
					if (!gameOver && Players.get(x).isEmpty()==false)
					{
					gotACard = Players.get(playerTurn).RequestCard(Players.get(playerIndex));//request card is a boolean so will return if card requested was recieved
					System.out.println("looking in Player "+ playerIndex);
					playerIndex++;
					Thread wait = new Thread()
					{
						@Override
                  public void run()
						{
						try {
							Thread.sleep(750);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						}
						};wait.run();
					}
					else//if the hand is now empty
					{
						gotACard=false; //player doesn't get another turn
					
					}
				}
				NumPlayer.setText("Player " + playerTurn + "'s Turn");//Updates GUI with the player whose JPanel is shown
				UpdateBooks();
				this.pack();
				Thread wait = new Thread()
				{
					@Override
               public void run()
					{
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					};wait.run();
					this.pack();
					
			}}}
	
	public void DealCards()
	{
		while(gameDeck.isEmpty()==false)
		{
			System.out.println("adding cards///");
			for(int x=0; x<Players.size();x++)
			{
				if (gameDeck.isEmpty()==true)
				{
					System.out.println("break");
					break;//quit for loop if deck has been fully dealt
				}
				Players.get(x).addCard(new BetterCard(gameDeck.deal()));//else add BetterCard
			}
		
		}}
	
}
	
	
	
	
	
	
	
	
	
	

