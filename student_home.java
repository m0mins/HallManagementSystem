
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Toxic
 */
public class student_home extends JFrame implements ActionListener {

    public static final String dbHost = "jdbc:mysql://localhost:3306/hall_management";
    public static final String dbUser = "root";
    public static final String dbPass = "";

    private static Connection connection = null;
    private static PreparedStatement ps = null;
    private static Statement statement1 = null;

    private static ResultSet rs = null;
    private static JFrame jframe;

    JPanel notice_panel = new JPanel();
    JButton provost = new JButton("Hall Provost");
    JButton office = new JButton("Hall Office");
    JButton cost = new JButton("Your Transaction");

    JLabel noticebord = new JLabel("Notice Board");
    JTextArea notice = new JTextArea();

    public student_home() throws IOException {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        jframe = new JFrame();
        jframe.setLayout(null);
        jframe.getContentPane().setBackground(Color.GRAY);
        jframe.setTitle("Student Profile");
        jframe.setSize(810, 600);
        jframe.setLocation(270, 80);
        jframe.setResizable(false);
        jframe.setVisible(true);
        jframe.setBackground(Color.BLUE);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        notice_panel.setLayout(null);
        notice_panel.setBounds(60, 60, 400, 450);
        notice_panel.setBackground(Color.DARK_GRAY);
        notice_panel.setBorder(BorderFactory.createLineBorder(Color.yellow));
        jframe.add(notice_panel);

        noticebord.setBounds(170, 10, 100, 20);
        noticebord.setForeground(Color.red);
        notice_panel.add(noticebord);

        notice.setBounds(40, 40, 320, 370);
        notice.setBorder(BorderFactory.createLineBorder(Color.yellow));
        notice.setEditable(false);
        notice.setForeground(Color.red);
        notice_panel.add(notice);
        //display notice from database
        try {
            connection = DriverManager.getConnection(dbHost, dbUser, dbPass);
            statement1 = connection.createStatement();
            rs = statement1.executeQuery("select  report from  student_report");
            while (rs.next()) {
                notice.setText(rs.getString(1));
            }

            connection.close();
        } catch (SQLException se) {
            System.out.println(se);
            JOptionPane.showMessageDialog(null, "SQL Error:" + se);
        }

        provost.setBounds(520, 180, 200, 40);
        provost.setBorder(BorderFactory.createLineBorder(Color.yellow));
        jframe.add(provost);
        provost.addActionListener(this);

        office.setBounds(520, 230, 200, 40);
        office.setBorder(BorderFactory.createLineBorder(Color.yellow));
        jframe.add(office);
        office.addActionListener(this);

        cost.setBounds(520, 280, 200, 40);
        cost.setBorder(BorderFactory.createLineBorder(Color.yellow));
        jframe.add(cost);
        cost.addActionListener(this);

    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == provost) {
            new provost_profile();
        } else if (ae.getSource() == office) {
            new office_profile();
        } else if (ae.getSource() == cost) {
            new transaction();
        }
    }

    public static void main(String args[]) throws IOException {
        new student_home();

    }
}
