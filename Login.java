
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Login extends JFrame implements ActionListener {

    public static final String dbHost = "jdbc:mysql://localhost:3306/hall_management";
    public static final String dbUser = "root";
    public static final String dbPass = "";

    private Connection connection = null;
    private PreparedStatement ps;

    JButton b1, b2, b3;
    JLabel l1, l2, l3, l4, l5, l6;
    JTextField t1, t2;
    JPasswordField p1;
    JLabel password = new JLabel("Password", JLabel.RIGHT);
    JLabel admn = new JLabel("Login as", JLabel.RIGHT);

    private String[] category = {"Provost", "Office", "Student"};
    JComboBox select_login = new JComboBox(category);

    JLabel logintxtset = new JLabel("New user?", JLabel.RIGHT);
    JButton signupbtn = new JButton("Sign up?");

    public Login() {

        JPanel pane = new JPanel();
        pane.setLayout(null);
        pane.setBackground(Color.GREEN);
        setContentPane(pane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Login with username & password"));

    
        admn.setBounds(240, 210, 100, 40);
        pane.add(admn);
        admn.setFont(new Font("Times New Roman", Font.BOLD, 22));

        select_login.setBounds(350, 210, 200, 40);
        select_login.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        select_login.setToolTipText("Choose one");
        pane.add(select_login);

      

        password.setBounds(230, 300, 110, 25);
        password.setFont(new Font("Times New Roman", Font.BOLD, 20));
        pane.add(password);

        p1 = new JPasswordField(20);
        p1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        p1.setBounds(350, 300, 200, 25);
        p1.setToolTipText("Enter Password");
        pane.add(p1);

        b1 = new JButton("Login");
        b1.setToolTipText("Click To Login");
        b1.setFocusPainted(false);
        b1.setBounds(350, 350, 95, 35);
        b1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pane.add(b1);
        b1.addActionListener(this);

        b2 = new JButton("Clear");
        b2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        b2.setFocusPainted(false);
        b2.setBounds(455, 350, 95, 35);
        b2.setToolTipText("Click To Clear All Textfields");
        pane.add(b2);
        b2.addActionListener(this);

        logintxtset.setBounds(240, 420, 180, 25);
        pane.add(logintxtset);
        logintxtset.setFont(new Font("Times New Roman", Font.BOLD, 15));
        logintxtset.setForeground(Color.BLACK);

        signupbtn.setToolTipText("Click To signup");
        signupbtn.setBounds(450, 420, 100, 20);
        pane.add(signupbtn);
        signupbtn.setForeground(Color.BLACK);
        signupbtn.setFont(new Font("Times New Roman", Font.BOLD, 15));
        signupbtn.addActionListener(this);

    }

    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if (source == signupbtn) {
            dispose();
            try {

                SignUp panel = new SignUp();
                panel.setTitle("SignUp");
                panel.setSize(810, 600);
                panel.setLocation(270, 100);
                panel.setResizable(false);
                panel.setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (ae.getSource() == b1) {

            String choice = (String) select_login.getSelectedItem();
            String password = String.valueOf(p1.getPassword());

            if (choice.equals("Provost") && password.equals("provost")) {
                JOptionPane.showMessageDialog(null, " Login Sucecssesfully ");
                dispose();
                try {
                    new provost_home();
                } catch (IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (choice.equals("Office") && password.equals("office")) {
                JOptionPane.showMessageDialog(null, " Login Successesfully ");
                dispose();
                try {
                    new office_home();
                } catch (IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (choice.equals("Student")) {
                try {
                    connection = (Connection) DriverManager.getConnection(dbHost, dbUser, dbPass);
                    ps = connection.prepareStatement("SELECT  `txtpassword` FROM `registration` WHERE `txtpassword` = ?");
                    ps.setString(1, password);
                    ResultSet result = ps.executeQuery();
                    if (result.next()) {
                        JOptionPane.showMessageDialog(null, " Login Successesfully ");
                        dispose();
                        new student_home();
                    } else {
                        JOptionPane.showMessageDialog(null, " Password mismatch");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, " Login Failed ");
            }
        } else if (ae.getSource() == b2) {
          p1.setText("");

        }

    }

    public static void main(String args[]) {

        Login panel = new Login();
        panel.setTitle("Login Window");
        panel.setLocation(270, 80);
        panel.setSize(810, 600);
        panel.setResizable(false);
        panel.setVisible(true);

    }
}
