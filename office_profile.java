
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Block Head
 */
public class office_profile {

    private static final String dbHost = "jdbc:mysql://localhost:3306/hall_management";
    private static final String dbUser = "root";
    private static final String dbPass = "";

    private Connection connection;
    private PreparedStatement ps;
    private Statement statement;
    private Statement statement1;
    private ResultSet rs;
    DefaultTableModel model = new DefaultTableModel();
    JTable tabGrid = new JTable(model);
    JScrollPane scrlPane = new JScrollPane(tabGrid);

    private JFrame jframe;
    ImageIcon icon = new ImageIcon("E:\\student.png");
    private JButton imagebutton = new JButton(icon);

    private JTextArea report_provost = new JTextArea();
    private String send_report_provost = null;

    JLabel report = new JLabel("Write Report");
    private JButton send_report = new JButton("Send Report");

    JPanel notice_panel = new JPanel();
    JLabel noticebord = new JLabel("Notice Board");
    JTextArea notice = new JTextArea();

    public office_profile() {
        jframe = new JFrame();
        jframe.setLayout(null);
        jframe.getContentPane().setBackground(Color.GRAY);
        jframe.setTitle("Office Profile");
        jframe.setSize(400, 450);
        jframe.setLocation(335, 168);
        jframe.setResizable(false);
        jframe.setVisible(true);
        jframe.setBackground(Color.BLUE);

        notice_panel.setLayout(null);
        notice_panel.setBounds(150, 260, 213, 147);
        notice_panel.setBackground(Color.DARK_GRAY);
        notice_panel.setBorder(BorderFactory.createLineBorder(Color.yellow));
        jframe.add(notice_panel);

        noticebord.setBounds(70, 0, 100, 20);
        noticebord.setForeground(Color.red);
        notice_panel.add(noticebord);

        notice.setBounds(5, 20, 203, 122);
        notice.setBorder(BorderFactory.createLineBorder(Color.yellow));
        notice.setEditable(false);
        notice.setForeground(Color.red);
        notice_panel.add(notice);
        //display notice from database
        try {
            connection = DriverManager.getConnection(dbHost, dbUser, dbPass);
            statement1 = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = statement1.executeQuery("select  report from  student_report");
            while (rs.next()) {
                notice.setText(rs.getString(1));
            }

            connection.close();
        } catch (SQLException se) {
            System.out.println(se);
            JOptionPane.showMessageDialog(null, "SQL Error:" + se);
        }
        imagebutton.setBounds(120, 20, 150, 150);
        imagebutton.setBackground(Color.black);
        jframe.add(imagebutton);

        scrlPane.setBounds(20, 180, 343, 50);
        jframe.add(scrlPane);
        tabGrid.setFont(new Font("Times New Roman", 0, 15));

        model.addColumn("Name");
        model.addColumn("Designation");
        model.addColumn("Contact");
        model.addColumn("Email");

        report_provost.setBounds(20, 260, 120, 120);
        report_provost.setBackground(Color.white);
        report_provost.setBorder(BorderFactory.createLineBorder(Color.yellow));
        jframe.add(report_provost);

        report.setBounds(40, 240, 93, 25);
        jframe.add(report);
        report.setFont(new Font("Times New Roman", Font.BOLD, 14));

        send_report.setBounds(20, 383, 120, 23);
        send_report.setBorder(BorderFactory.createLineBorder(Color.yellow));
        jframe.add(send_report);
        send_report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();

                if (source == send_report) {
                    send_report_provost = report_provost.getText();

                    if (((report_provost.getText().equals("")))) {
                        JOptionPane.showMessageDialog(null, "Every Field is Required");
                    } else {
                        try {
                            int check = 0;
                            Class.forName("com.mysql.jdbc.Driver");
                            connection = (Connection) DriverManager.getConnection(dbHost, dbUser, dbPass);
                            PreparedStatement ps = connection.prepareStatement("insert into   student_report (report) values (?)");
                            ps.setString(1, send_report_provost);
                            check = ps.executeUpdate();

                            if (check > 0) {
                                report_provost.setText(null);
                                JOptionPane.showMessageDialog(null, "Report send  successfully");

                            } else {
                                JOptionPane.showMessageDialog(null, "Error occurd");

                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                }
            }
        });

        //display provost profile
        int r = 0;
        try {

            connection = DriverManager.getConnection(dbHost, dbUser, dbPass);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = statement.executeQuery("select * from registration");
            while (rs.next()) {
                model.insertRow(r++, new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)});

            }

            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "SQL Error:" + e);
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error:" + e);
        }
        //end display provost profile
    }

    public static void main(String args[]) {
        new office_profile();
    }

}
