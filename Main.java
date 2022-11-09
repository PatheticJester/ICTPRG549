import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class Main extends JFrame implements ActionListener{
    public static void main(String[] args) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        Main frame = new Main();
                        frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        
    public JPanel contentPane;
    // Giving outside access to these objects
    JLabel UserL = new JLabel("Username:");
    JLabel PassL = new JLabel("Password:");
    JTextField User = new JTextField("");
    JTextField Pass = new JTextField("");
    JButton btnLogin = new JButton("Login");
    JButton btnRegister = new JButton("Register");
    SQLHandle Sqlconnect = new SQLHandle();

    public Main() {
        // Main sets the propertites for the frame, adds elements and calls the refresh function that sets up all the default langages and fonts.
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 650, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null); // Because of the language change working with a layout becomes borderline impossible.

        UserL.setBounds(220,135,100,50);
        contentPane.add(UserL);

        PassL.setBounds(220,168,100,50);
        contentPane.add(PassL);

        User.setBounds(310,155,150,18);
        contentPane.add(User);

        Pass.setBounds(310,185,150,18);
        contentPane.add(Pass);

        btnLogin.setBounds(220,220,70,20);
        btnLogin.addActionListener(this);
        contentPane.add(btnLogin);

        btnRegister.setBounds(320,220,90,20);
        btnRegister.addActionListener(this);
        contentPane.add(btnRegister);

    }
        @Override
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==btnLogin){
                if(Sqlconnect.sqlrequest(User.getText(), Pass.getText()) == true){
                    GameGUI.frame();
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Wrong User or Password", "Error:", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            
            if(e.getSource()==btnRegister){
                Sqlconnect.sqlinsert(User.getText(), Pass.getText());
            }
    }
}