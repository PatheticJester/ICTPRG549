// Importing packages
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GameGUI extends JFrame implements ActionListener{
	// The main class calling an instance of frame
	public static void frame() {
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
	// Giving outside access to these objects
	int Count;
	Lang Current = new Lang();
	JButton btnCoinAction = new JButton("");
	JLabel OutcomeL = new JLabel("");
	JLabel Coin1L = new JLabel("");
	JLabel Coin2L = new JLabel("");
	JLabel Score = new JLabel("");
	JLabel ScoreNumber = new JLabel("");
	JMenu Settings = new JMenu("");
	JCheckBoxMenuItem Darkchck = new JCheckBoxMenuItem("");
	JMenu Langmenu = new JMenu("");
	JCheckBoxMenuItem Englishchck = new JCheckBoxMenuItem("");
	JCheckBoxMenuItem Thaichck = new JCheckBoxMenuItem("");
	JCheckBoxMenuItem Mandarinchck = new JCheckBoxMenuItem("");
	int defcolour = 1;

	public GameGUI() {
		// Main sets the propertites for the frame, adds elements and calls the refresh function that sets up all the default langages and fonts.
		setTitle("Two Up");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null); // Because of the language change working with a layout becomes borderline impossible. See OutcomeL

		btnCoinAction.setBounds(250, 350, 100, 25);
		btnCoinAction.addActionListener(this);
		contentPane.add(btnCoinAction);

		Score.setBounds(550, 250, 150, 30);
		contentPane.add(Score);

		ScoreNumber.setBounds(590, 250, 150, 30);
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
		 */
		Coin Toss = new Coin();
		if(e.getSource()==btnCoinAction){
			OutcomeL.setBounds(270, 300, 100, 30);
			if(Toss.coin == 2)
			{
				Count++;
				String Score = Integer.toString(Count);
				Coin1L.setText(Current.Coin1L[1]);
				Coin2L.setText(Current.Coin2L[1]);
				OutcomeL.setText(Current.OutcomeL[0]);
				Toss.Resultout("Win", Count);
				ScoreNumber.setText(Score);
			} else if(Toss.coin == 1)
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
				Toss.Resultout("Lose", Count);
			}
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
					defcolour++;
					break;
				case 2:
					contentPane.setBackground(new Color(238,238,238));
					defcolour = 1;
					break;
			}
		}
	}
}