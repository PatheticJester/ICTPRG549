// Importing packages
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;


public class GameGUI extends JFrame implements ActionListener{
	static String User;
	// The main class calling an instance of frame it creates a variable user which is used to identify the current user when calling the SQL database
	public static void frame(String LoggedUser) {
		User = LoggedUser;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGUI frame = new GameGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public JPanel contentPane;
	public JPanel contentPane2;
	// Giving outside access to these objects
	int Count;
	Lang Current = new Lang();
	JButton btnCoinAction = new JButton("");
	JLabel OutcomeL = new JLabel("");
	JLabel Coin1L = new JLabel("");
	JLabel Coin2L = new JLabel("");
	JLabel Highscore = new JLabel("");
	JLabel Score = new JLabel("");
	JLabel ScoreNumber = new JLabel("");
	JLabel LeaderboardL = new JLabel("");
	JMenu Settings = new JMenu("");
	JCheckBoxMenuItem Darkchck = new JCheckBoxMenuItem("");
	JMenu Langmenu = new JMenu("");
	JCheckBoxMenuItem Englishchck = new JCheckBoxMenuItem("");
	JCheckBoxMenuItem Thaichck = new JCheckBoxMenuItem("");
	JCheckBoxMenuItem Mandarinchck = new JCheckBoxMenuItem("");
	int defcolour = 1;
	SQLHandle Sqlconnect = new SQLHandle();
	// Creating the leaderboard object and obtaining the values from the sql method
	JList<String> LeaderboardDisplay = new JList<>(Sqlconnect.sqlleaderboard());
	JScrollPane Leaderboard = new JScrollPane(LeaderboardDisplay);
	// Creating animation onjects and scaling them to fit the Jpanel
	ImageIcon CoinAni1 = new ImageIcon(new ImageIcon("Animation/Coinflip.gif").getImage().getScaledInstance(150, 100, Image.SCALE_DEFAULT));
	ImageIcon CoinAni2 = new ImageIcon(new ImageIcon("Animation/Coinflip.gif").getImage().getScaledInstance(150, 100, Image.SCALE_DEFAULT));
	JLabel Ani1L = new JLabel(CoinAni1);
	JLabel Ani2L = new JLabel(CoinAni2);

	public GameGUI() {
		// Main sets the propertites for the frame, adds elements and calls the refresh function that sets up all the default langages and fonts.
		// We also created two JPanels. One for the animation and the other for everything else
		setTitle("Two Up");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);

		Ani1L.setBounds(0, 50, 150, 100); // You can use your own values
		Ani2L.setBounds(150, 50, 150, 100); // You can use your own values
		contentPane2 = new JPanel();
		contentPane2.setBounds(150,50,300,200);
		contentPane2.setLayout(null);
		contentPane2.add(Ani1L);
		contentPane2.add(Ani2L);
		Ani1L.setVisible(false);
		Ani2L.setVisible(false);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.add(contentPane2);
		contentPane.setLayout(null); // Because of the language change working with a layout becomes borderline impossible. See OutcomeL

		Leaderboard.setBounds(550, 220, 100, 210);
		contentPane.add(Leaderboard);

		LeaderboardL.setBounds(550, 190, 120, 30);
		contentPane.add(LeaderboardL);

		btnCoinAction.setBounds(250, 350, 100, 25);
		btnCoinAction.addActionListener(this);
		contentPane.add(btnCoinAction);

		Highscore.setBounds(550, 150, 150, 30);
		contentPane.add(Highscore);

		Score.setBounds(550, 170, 50, 30);
		contentPane.add(Score);

		ScoreNumber.setBounds(590, 170, 150, 30);
		contentPane.add(ScoreNumber);
	
		Coin1L.setBounds(210, 250, 150, 30);
		contentPane.add(Coin1L);
		
		Coin2L.setBounds(340, 250, 150, 30);
		contentPane.add(Coin2L);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(20, 22, 60, 22);
		contentPane.add(menuBar);

		menuBar.add(Settings);
		Settings.add(Darkchck);
		Darkchck.addActionListener(this);
		Settings.add(Langmenu);

		Langmenu.add(Englishchck);
		Englishchck.setSelected(true);
		Englishchck.addActionListener(this);

		Langmenu.add(Thaichck);
		Thaichck.addActionListener(this);

		Langmenu.add(Mandarinchck);
		Mandarinchck.addActionListener(this);
		Current.setEnglish();
		refresh();
		contentPane.add(OutcomeL);
	}

	public void refresh(){
		// This function refreshes the text parts of the frame dependant on what the variables have been set too in the Lang class.
		btnCoinAction.setText(Current.btnCoinAction);
		btnCoinAction.setFont(Current.Font);

		Highscore.setText(Current.Highscore);
		Highscore.setFont(Current.Font);

		LeaderboardL.setText(Current.LeaderboardL);
		LeaderboardL.setFont(Current.Font);

		OutcomeL.setText("");
		OutcomeL.setFont(Current.Font);

		Coin1L.setText(Current.Coin1L[0]);
		Coin1L.setFont(Current.Font);

		Coin2L.setText(Current.Coin2L[0]);
		Coin2L.setFont(Current.Font);

		Score.setText(Current.Score);
		Score.setFont(Current.Font);

		Settings.setText(Current.Settings);
		Settings.setFont(Current.Font);

		Darkchck.setText(Current.Darkchck);
		Darkchck.setFont(Current.Font);

		Langmenu.setText(Current.Langmenu);
		Langmenu.setFont(Current.Font);

		Englishchck.setText(Current.Englishchck);
		Englishchck.setFont(Current.Font);

		Thaichck.setText(Current.Thaichck);
		Thaichck.setFont(Current.Font);

		Mandarinchck.setText(Current.Mandarinchck);
		Mandarinchck.setFont(Current.Font);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * This method catches any clicks on buttons and determines their origin. If the button came from the coin 'spin' then it calls
		 * setText() to change the coins text to the accurate readout dependant on what the sum of the two coins produced in Coin.java were.
		 * Pesudocoin is generated just to provide the illussion that the labels are changing based on the original coin determination.
		 * If it detects the source is one of the menu buttons it calls the relevant function in class Lang and then refreshes all the elements.
		 * It also keeps track of the score by having a constant count that increseas, gets cast to string and then displayed.
		 * The actionlistener was the only way I was able to get the animation to play for one second without interrupting the thread. It 
		 * also prevents the user from spanning the spin button.
		 */
		Coin Coin1 = new Coin();
		if(e.getSource()==btnCoinAction){
			btnCoinAction.setEnabled(false);
			OutcomeL.setBounds(270, 300, 100, 30);
			OutcomeL.setText("");
			Ani1L.setVisible(true);
			Ani2L.setVisible(true);
			ActionListener task = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					btnCoinAction.setEnabled(true);
					Ani1L.setVisible(false);
					Ani2L.setVisible(false);
					if(Coin1.coin == 2)
					{
						Count++;
						String Score = Integer.toString(Count);
						Coin1L.setText(Current.Coin1L[1]);
						Coin2L.setText(Current.Coin2L[1]);
						OutcomeL.setText(Current.OutcomeL[0]);
						ScoreNumber.setText(Score);
						Sqlconnect.sqlupdateuser(User, Count);
					} else if(Coin1.coin == 1)
					{
						int pesudocoin = new Random().nextInt(2);
						if(pesudocoin == 0){
							Coin1L.setText(Current.Coin1L[1]);
							Coin2L.setText(Current.Coin2L[2]);
						} else {
							Coin1L.setText(Current.Coin1L[2]);
							Coin2L.setText(Current.Coin2L[1]);
						}
							// OutcomeL's bounds change because of the font and language change. This was a pain to get to work but with userability comes adaptability.
							OutcomeL.setBounds(250, 300, 100, 30);
							OutcomeL.setText(Current.OutcomeL[1]);
					} else{
						Coin1L.setText(Current.Coin1L[2]);
						Coin2L.setText(Current.Coin2L[2]);
						OutcomeL.setText(Current.OutcomeL[2]);
					}
				}
			};
		Timer countdown = new Timer(1000, task);
		countdown.setRepeats(false);
		countdown.start();	
		}

		if(e.getSource()==Englishchck){
			// Setting the other menu options as false. Same occurs in every have language check
			Thaichck.setSelected(false);
			Mandarinchck.setSelected(false);
			Current.setEnglish();
			refresh();
		}

		if(e.getSource()==Thaichck){
			Englishchck.setSelected(false);
			Mandarinchck.setSelected(false);
			Current.setThai();
			refresh();
		}

		if(e.getSource()==Mandarinchck){
			Thaichck.setSelected(false);
			Englishchck.setSelected(false);
			Current.setMandarin();
			refresh();
		}
		if(e.getSource()==Darkchck){
			// Using a swtich here because I got bored of if statements. Probably helps optimisation too.
			// Changes colour depending on whether defcolour has been changed. It starts on 1
			switch (defcolour){
				case 1:
					contentPane.setBackground(Color.black);
					contentPane2.setBackground(Color.black);
					defcolour++;
					break;
				case 2:
					contentPane.setBackground(new Color(238,238,238));
					contentPane2.setBackground(new Color(238,238,238));
					defcolour = 1;
					break;
			}
		}
	}
}