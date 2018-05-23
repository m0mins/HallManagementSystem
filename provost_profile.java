
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Block Head
 */
public class provost_profile {

    private static final String dbHost = "jdbc:mysql://localhost:3306/hall_management";
    private static final String dbUser = "root";
    private static final String dbPass = "";

    Connection connection;
    PreparedStatement ps;
    Statement statement;
    ResultSet rs;
    DefaultTableModel model = new DefaultTableModel();
    JTable tabGrid = new JTable(model);
    JScrollPane scrlPane = new JScrollPane(tabGrid);

    private JFrame jframe;
    ImageIcon icon = new ImageIcon("E:\\student.png");
    private JButton imagebutton = new JButton(icon);

    private JTextArea report_provost = new JTextArea();
    private String send_report_provost = null;
    private JTextArea request_for_room = new JTextArea();

    JLabel report = new JLabel("Write Report");
    private JButton send_report = new JButton("Send Report");
    private JButton send_room_request = new JButton("Send Request");

    private JTextField txtold_room = new JTextField();
    private JTextField txtnew_room = new JTextField();
    private JTextField txtsession = new JTextField();

    private String old_insert = null;
    private String new_insert = null;
    private String session_insert = null;
    private String text_insert = null;

    public provost_profile() {
        jframe = new JFrame();
        jframe.setLayout(null);
        jframe.getContentPane().setBackground(Color.GRAY);
        jframe.setTitle("Provost Profile");
        jframe.setSize(400, 450);
        jframe.setLocation(335, 168);
        jframe.setResizable(false);
        jframe.setVisible(true);
        jframe.setBackground(Color.BLUE);

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
        JLabel old_room = new JLabel("Old Room:");
        old_room.setBounds(165, 244, 93, 20);
        jframe.add(old_room);
        old_room.setFont(new Font("Times New Roman", Font.BOLD, 14));
        txtold_room.setBounds(250, 244, 113, 20);
        txtold_room.setBorder(BorderFactory.createLineBorder(Color.yellow));
        jframe.add(txtold_room);

        JLabel new_room = new JLabel("New Room:");
        new_room.setBounds(165, 274, 93, 20);
        jframe.add(new_room);
        new_room.setFont(new Font("Times New Roman", Font.BOLD, 14));
        txtnew_room.setBounds(250, 274, 113, 20);
        txtnew_room.setBorder(BorderFactory.createLineBorder(Color.yellow));
        jframe.add(txtnew_room);

        JLabel session = new JLabel("Session:");
        session.setBounds(165, 304, 93, 20);
        jframe.add(session);
        session.setFont(new Font("Times New Roman", Font.BOLD, 14));
        txtsession.setBounds(250, 304, 113, 20);
        txtsession.setBorder(BorderFactory.createLineBorder(Color.yellow));
        jframe.add(txtsession);

        JLabel request_room = new JLabel("Text:");
        request_room.setBounds(165, 330, 93, 20);
        jframe.add(request_room);
        request_for_room.setBounds(250, 330, 113, 50);
        request_for_room.setBackground(Color.white);
        request_for_room.setBorder(BorderFactory.createLineBorder(Color.yellow));
        jframe.add(request_for_room);

        send_room_request.setBounds(250, 384, 113, 23);
        send_room_request.setBorder(BorderFactory.createLineBorder(Color.yellow));
        jframe.add(send_room_request);
        send_room_request.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Object source = e.getSource();

                if (source == send_room_request) {

                    old_insert = txtold_room.getText();
                    new_insert = txtnew_room.getText();
                    session_insert = txtsession.getText();
                    text_insert = request_for_room.getText();

                    if (((txtold_room.getText().equals("")) || (txtnew_room.getText().equals("")) || (txtsession.getText().equals("")) || (request_for_room.getText().equals("")))) {
                        JOptionPane.showMessageDialog(null, "Every Field is Required");
                    } else {

                        try {
                            int check = 0;
                            Class.forName("com.mysql.jdbc.Driver");
                            connection = (Connection) DriverManager.getConnection(dbHost, dbUser, dbPass);
                            PreparedStatement ps = connection.prepareStatement("insert into   student_request (old_room,new_room,session,text) values (?,?,?,?)");
                            ps.setString(1, old_insert);
                            ps.setString(2, new_insert);
                            ps.setString(3, session_insert);
                            ps.setString(4, text_insert);

                            check = ps.executeUpdate();
                            if (check > 0) {

                                txtold_room.setText(null);
                                txtnew_room.setText(null);
                                txtsession.setText(null);
                                request_for_room.setText(null);

                                JOptionPane.showMessageDialog(null, "Request send  successfully");

                            } else {
                                JOptionPane.showMessageDialog(null, "Error occurd!");

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
        } catch (SQLException se) {
            System.out.println(se);
            JOptionPane.showMessageDialog(null, "SQL Error:" + se);
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error:" + e);
        }
        //end display provost profile
    }

    public static void main(String args[]) {
        new provost_profile();
    }

}
