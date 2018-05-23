
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Toxic
 */
public class SignUp extends JFrame implements ActionListener {

    //For connection
    public static final String dbHost = "jdbc:mysql://localhost:3306/hall_management";
    public static final String dbUser = "root";
    public static final String dbPass = "";

    private String hall_choice_insert, faculty_insert, name_insert, student_id_insert, reg_no_insert, session_insert, phoneno_insert, password_insert;

    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet rs = null;

    JLabel faculty = new JLabel("Faculty");
    JLabel name = new JLabel("Name");
    JLabel student_id = new JLabel("StudentId");
    JLabel reg_no = new JLabel("Reg no");
    JLabel session = new JLabel("Session");
    JLabel phoneno = new JLabel("PhoneNo");
    JLabel password = new JLabel("Password");

    JLabel logintxtset = new JLabel("Have an account?", JLabel.RIGHT);

    JTextField txtfaculty = new JTextField(20);
    JTextField txtname = new JTextField(20);
    JTextField txtstudent_id = new JTextField(20);
    JTextField txtreg_no = new JTextField(20);
    JTextField txtsession = new JTextField(20);
    JTextField txtphoneno = new JTextField(20);
    JTextField txtpassword = new JPasswordField(20);

    private String sUser = "";

    String[] hall_name = {"Select Hall", "D1", "D2", "D4", "D5", "D6", "D7", "D8"};
    JComboBox hall_choose = new JComboBox(hall_name);
    JLabel hall = new JLabel("Choice");
    JButton login = new JButton("Login");

    JButton newbtn = new JButton("Submit");
    private Object frame;

    public SignUp() throws SQLException {

        JPanel pane = new JPanel();
        pane.setLayout(null);
        pane.setBackground(Color.gray);
        setContentPane(pane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Create user account....."));

        hall.setBounds(265, 20, 60, 25);
        hall.setFont(new Font("Times New Roman", Font.BOLD, 20));
        pane.add(hall);

        hall_choose.setBounds(355, 20, 150, 20);
        hall_choose.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        hall_choose.setToolTipText("Select hall");
        pane.add(hall_choose);

        faculty.setBounds(236, 50, 93, 25);
        pane.add(faculty);
        faculty.setFont(new Font("Times New Roman", Font.BOLD, 20));
        //username.setForeground(Color.GREEN);
        txtfaculty.setToolTipText("Enter Username");
        txtfaculty.setBounds(355, 50, 150, 25);
        pane.add(txtfaculty);

        name.setBounds(236, 85, 81, 25);
        pane.add(name);
        name.setFont(new Font("Times New Roman", Font.BOLD, 20));
        //password.setForeground(Color.GREEN);
        txtname.setToolTipText("Enter Password");
        txtname.setBounds(355, 85, 150, 25);
        pane.add(txtname);

        student_id.setBounds(270, 120, 90, 25);
        pane.add(student_id);
        student_id.setFont(new Font("Times New Roman", Font.BOLD, 20));
        //  firstname.setForeground(Color.GREEN);
        txtstudent_id.setToolTipText("Enter FirstName");
        txtstudent_id.setBounds(355, 120, 150, 25);
        pane.add(txtstudent_id);

        reg_no.setBounds(270, 155, 90, 25);
        pane.add(reg_no);
        reg_no.setFont(new Font("Times New Roman", Font.BOLD, 20));
        // lastname.setForeground(Color.GREEN);
        txtreg_no.setToolTipText("Enter LastName");
        txtreg_no.setBounds(355, 155, 150, 25);
        pane.add(txtreg_no);

        session.setBounds(270, 190, 90, 25);
        pane.add(session);
        session.setFont(new Font("Times New Roman", Font.BOLD, 20));
        //  country.setForeground(Color.GREEN);
        txtsession.setToolTipText("Enter Country");
        txtsession.setBounds(355, 190, 150, 25);
        pane.add(txtsession);

        phoneno.setBounds(270, 225, 90, 25);
        pane.add(phoneno);
        phoneno.setFont(new Font("Times New Roman", Font.BOLD, 20));
        //  phoneno.setForeground(Color.GREEN);
        txtphoneno.setToolTipText("Enter PhoneNo");
        txtphoneno.setBounds(355, 225, 150, 25);
        pane.add(txtphoneno);

        password.setBounds(270, 255, 90, 25);
        pane.add(password);
        password.setFont(new Font("Times New Roman", Font.BOLD, 20));
        txtpassword.setToolTipText("Enter PhoneNo");
        txtpassword.setBounds(355, 255, 150, 25);
        pane.add(txtpassword);

        logintxtset.setBounds(210, 335, 180, 25);
        pane.add(logintxtset);
        logintxtset.setFont(new Font("Times New Roman", Font.BOLD, 15));
        logintxtset.setForeground(Color.BLACK);

        newbtn = new JButton("Submit", new ImageIcon("images//addnew.png"));
        newbtn.setToolTipText("Click To Submit Details");
        newbtn.setBounds(355, 290, 150, 35);
        pane.add(newbtn);
        newbtn.setForeground(Color.BLACK);
        newbtn.setFont(new Font("Times New Roman", Font.BOLD, 15));
        newbtn.addActionListener(this);

        login.setToolTipText("Click To Login");
        login.setBounds(405, 335, 100, 20);
        pane.add(login);
        login.setForeground(Color.BLACK);
        login.setFont(new Font("Times New Roman", Font.BOLD, 15));
        login.addActionListener(this);

    
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == newbtn) {
            hall_choice_insert = (String)hall_choose.getSelectedItem();
            faculty_insert = txtfaculty.getText();
            name_insert = txtname.getText();
            student_id_insert = txtstudent_id.getText();
            reg_no_insert = txtreg_no.getText();
            session_insert = txtsession.getText();
            phoneno_insert = txtphoneno.getText();
            password_insert = txtpassword.getText();

            if (((txtfaculty.getText().equals("")) || txtname.getText().equals("") || txtstudent_id.getText().equals("") || txtreg_no.getText().equals("") || txtsession.getText().equals("") || txtphoneno.getText().equals("") || txtpassword.getText().equals(""))) {
                JOptionPane.showMessageDialog(this, "Every Field is Required");
            } else {
                try {
                    int check = 0;
                    connection = (Connection) DriverManager.getConnection(dbHost, dbUser, dbPass);
                    PreparedStatement ps = connection.prepareStatement("insert into registration (hall_choose,txtfaculty,txtname,txtstudent_id,txtreg_no,txtsession,txtphoneno,txtpassword) values (?,?,?,?,?,?,?,?)");
                   
                    ps.setString(1, hall_choice_insert);
                    ps.setString(2, faculty_insert);
                    ps.setString(3, name_insert);
                    ps.setString(4, student_id_insert);
                    ps.setString(5, reg_no_insert);
                    ps.setString(6, session_insert);
                    ps.setString(7, phoneno_insert);
                    ps.setString(8, password_insert);
                    check = ps.executeUpdate();

                    if (check > 0) {

                        txtfaculty.setText(null);
                        txtname.setText(null);
                        txtstudent_id.setText(null);
                        txtreg_no.setText(null);
                        txtsession.setText(null);
                        txtphoneno.setText(null);
                        txtpassword.setText(null);

                        JOptionPane.showMessageDialog(null, "Create account successfully");
                        dispose();
                        Login panel = new Login();
                        panel.setTitle("Login Window");
                        panel.setLocation(270, 80);
                        panel.setSize(810, 600);
                        panel.setResizable(false);
                        panel.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "ID already exists");

                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }

        if (source == login) {
            dispose();
            Login panel = new Login();
            panel.setTitle("Login");
            panel.setLocation(270, 100);
            panel.setSize(810, 600);
            panel.setResizable(false);
            panel.setVisible(true);
        }

    }

    public static void main(String[] args) throws SQLException {

        SignUp panel = new SignUp();
        panel.setTitle("SignUp");
        panel.setSize(810, 600);
        panel.setLocation(270, 100);
        panel.setResizable(false);
        panel.setVisible(true);

    }
}
