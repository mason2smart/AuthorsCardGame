import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class CardGameOptions extends JFrame {
	int numInstances;
	int running;
	ArrayList<CardGame> Instances;
	public static double width = 800;
	public static double height = 300;
	protected int numGame;
	JPanel btnPane;
	JPanel layout;
	JButton startG;
	String[] humanPlayers = {"1", "2", "3", "4", "5", "6"};
	JComboBox<String> NumPlayers;
	String NumPlayersSelected = "1"; //get number of human players selected combobox
	private JLabel runningInstances;//will display number of games running
	private JPanel runInf;
	private double widthMulti;
	private double heightMulti;
	public CardGameOptions()//JCOMBOBOX to be used to select threads to remove?
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		widthMulti = screenSize.getWidth()/1920;
		heightMulti = screenSize.getHeight()/1080;
		layout = new JPanel();
		btnPane = new JPanel();
		Instances = new ArrayList<CardGame>();//holds games
		try//try to get system look for gui interface
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
		  catch (Exception ex)//error exception
        {
            ex.printStackTrace();
        }
			StBtn();
			gui();
			NumPlayers();//ComboBox selector for number of players
		ListGames(Instances);
		this.add(layout);
		this.pack();
        setVisible(true);
	}
public void updateGUI() {//loops updater in new thread to constantly update label without interfering with GUI and freezing it
			int gamesRunning=0;
			try {
				for(int i=0; i<=Instances.size(); i++)
				{
					if(Instances.get(i).isShowing())
					gamesRunning++;
				}
			}
			catch(Exception e)
			{
				
			}
		
					runningInstances.setText(" Running Games: "+gamesRunning+" ");
	}
	public void gui()//initiates JPanel
	{
		layout.setBackground(Color.BLACK);//sets main panel background black
		layout.setLayout(new FlowLayout(1,2,10));//Flow Layout wraps the grid layout keeping grid layout objects from expanding to full height
		btnPane.setLayout(new GridLayout(1,2));//grid layout for pane for adding buttons - sets number of rows
		btnPane.setBackground(Color.darkGray);//sets secondary panel dark grey
		btnPane.setOpaque(false);//removed opacity from btnPane to improve GUI Look...
		layout.add(btnPane, BorderLayout.NORTH);
        layout.add(new JSeparator(), BorderLayout.CENTER);//separator top and bottom
		setTitle("Options Menu");//sets window title
		double tempWide = width*widthMulti;
		double tempHigh = height*heightMulti;
         Dimension preferred = new Dimension((int)(tempWide), (int)(tempHigh));//preferred dimensions
         this.setResizable(false);//dimensions unchangeable by user
         this.setPreferredSize(preferred);
         setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void StBtn()//start button
	{

		startG = new JButton("Start Game");
		startG.setBorder(BorderFactory.createLineBorder(Color.green, 2, true));//create border around button
		startG.setPreferredSize(new Dimension((int)(150*widthMulti),(int)(100*heightMulti)));
		startG.setSize((int)(320*widthMulti),(int)(200*heightMulti));
		try {
			Font GamerFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("A.ttf"));//set font //getResource Finds Font in Relation to Classpath Location not System32
			startG.setFont(GamerFont.deriveFont((float) (16 * widthMulti)));//derives font size in order to simplify instead of having to register font in a graphics component
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		btnPane.add(startG,BorderLayout.WEST);
		startG.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						startG.setText("Starting Game....");	
						int numberPlayers = Integer.parseInt(NumPlayersSelected);
						Newgame(numberPlayers);//initiates new game;
			            new Thread(){//does it in new thread not to freeze up GUI() interface
			                public void run(){
			                  try {
				                    startG.setText("Starting Game...");
			                    Thread.sleep(2000);
						           startG.setText("Start Game");
						           updateGUI();
			                    this.interrupt();//ends thread
			                 } catch (InterruptedException exc) {
			                	 System.out.println("Thread Error on StartGame Btn");
			               }
			             }
			           }.start();
					
					
					}}	);
	}
	public void NumPlayers()//allows user to set the number of players
	{
		NumPlayers = new JComboBox<String>(humanPlayers);
		NumPlayers.setSelectedIndex(0);
		NumPlayers.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
		        NumPlayersSelected = (String) NumPlayers.getSelectedItem();//gets currently selected number of human players
			}});
	   ((JLabel) (NumPlayers).getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);//Center objects in Drop-Down Box

		NumPlayers.setBorder(BorderFactory.createLineBorder(Color.green, 2, true));//create border around button
		JLabel SelectorInstructions = new JLabel("           Number of Players: ");//also a cheap spacing fix as well as instructional label..
		Font GamerFont;
				try {
					GamerFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("A.ttf"));//set font
				     SelectorInstructions.setFont(GamerFont.deriveFont((float) (16*widthMulti)));
				     NumPlayers.setFont(GamerFont.deriveFont((float) (14*widthMulti)));
				} catch (FontFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			     //Derive and return a 12 pt version:
			     //Need to use float otherwise
			     //it would be interpreted as style
				Color GamerColor = new Color(71,225,12);
				SelectorInstructions.setForeground(GamerColor);
				SelectorInstructions.setOpaque(false);
			
			// TODO Auto-generated catch block

		btnPane.add(SelectorInstructions);
		btnPane.add(NumPlayers);
	}
	
	public void ListGames(ArrayList<CardGame> CG)
	{
		runInf = new JPanel();
		runInf.setLayout(new GridLayout(1,1));//grid layout for pane for adding buttons - sets number of rows
		runningInstances = new JLabel();
		runningInstances.setText(" Running Games: " + CG.size()+" ");
		runInf.add(runningInstances);
		layout.add(runInf);
		runInf.setBackground(Color.darkGray);
		runningInstances.setFont(NumPlayers.getFont());
	}
	
	
	public void Newgame(int NumPlayers)//using an array list instead of event pool to keep things simple
	{
				numGame++;
		
				Instances.add(new CardGame(Instances.size()+1, NumPlayers, Instances));	
	}
}
