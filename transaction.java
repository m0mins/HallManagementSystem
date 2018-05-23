
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Block Head
 */
public class transaction {

    private static final String dbHost = "jdbc:mysql://localhost:3306/hall_management";
    private static final String dbUser = "root";
    private static final String dbPass = "";

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet result, depositeresult, withdrawresult, balanceresult;
    PreparedStatement ps;
    JLabel labelb1;
    JButton createaccount, deposite, withdraw, transaction, balance;
    private JLabel close;
    Font f;
    private int totalbalance;
    private String account_name, a_balance, w_balance;
    private String account_no = null;
    private String createAccountSql, createDepositeSql, createWithdrawSQl, updateAccountTableSql, createupdatebalancesql;
    private JFrame jframe;

    public transaction() {
        jframe = new JFrame();
        jframe.setLayout(null);
        jframe.getContentPane().setBackground(Color.GRAY);
        jframe.setTitle("Your All Transaction");
        jframe.setSize(400, 450);
        jframe.setLocation(335, 168);
        jframe.setResizable(false);
        jframe.setVisible(true);
        jframe.setBackground(Color.BLUE);

        labelb1 = new JLabel("Momin Bank");
        labelb1.setBounds(160, 20, 200, 20);
        jframe.add(labelb1);

        JLabel depo = new JLabel("Deposite Money");
        depo.setBounds(85, 250, 120, 20);
        jframe.add(depo);
        deposite = new JButton("Deposite");
        deposite.setBounds(70, 280, 120, 25);
        deposite.setToolTipText("Click To Deposite");
        deposite.setBorder(BorderFactory.createLineBorder(Color.yellow));
        jframe.add(deposite);
        deposite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int i = 0;
                account_no = JOptionPane.showInputDialog(null, "Enter Bank Account Number:");
                a_balance = JOptionPane.showInputDialog(null, "Enter Deposite Amount:");
                if (Integer.parseInt(a_balance) < i) {
                    JOptionPane.showMessageDialog(null, "Please Deposite Minimum 1 tk");

                } else {
                    if (account_no.isEmpty() || a_balance.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Enter Account Number and Deposite Amount");
                    } else {
                        int acc_no = Integer.parseInt(account_no);
                        double d_balance = Double.parseDouble(a_balance);
                        try {
                            int tmp = 0;
                            connection = (Connection) DriverManager.getConnection(dbHost, dbUser, dbPass);
                            statement = connection.createStatement();
                            createDepositeSql = "insert into deposite (account_no,d_amount)values('" + acc_no + "','" + d_balance + "')";
                            statement.executeUpdate(createDepositeSql);
                            result = statement.executeQuery("select * from account_table where account_no = '" + acc_no + "'");
                            if (result.next()) {

                                d_balance = d_balance + Double.parseDouble(result.getString("balance"));
                                updateAccountTableSql = "update account_table set balance = '" + d_balance + "' where account_no = '" + acc_no + "'";
                                statement.executeUpdate(updateAccountTableSql);

                                tmp = 1;
                            }
                            if (tmp == 1) {
                                JOptionPane.showMessageDialog(null, "Amount Deposited Successfully.\nYour Current Balance is tk = " + d_balance);

                            } else {
                                JOptionPane.showMessageDialog(null, "Account Number Mismatch!");

                            }

                        } catch (SQLException err) {
                            JOptionPane.showMessageDialog(null, err.toString());

                        }

                    }
                }

            }
        });
        JLabel withd = new JLabel("Pay Hall Fee");
        withd.setBounds(215, 250, 120, 20);
        jframe.add(withd);
        withdraw = new JButton("Payment");
        withdraw.setBorder(BorderFactory.createLineBorder(Color.yellow));
        withdraw.setBounds(200, 280, 100, 25);
        withdraw.setToolTipText("Click To  Withdraw");
        jframe.add(withdraw);
        withdraw.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                account_no = JOptionPane.showInputDialog(null, "Enter Account Number:");
                w_balance = JOptionPane.showInputDialog(null, "Enter Hall Fee:");
                int acc_no = Integer.parseInt(account_no);
                double w_bal = Double.parseDouble(w_balance);

                if (account_no.isEmpty() && w_balance.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter Account Number and Withdraw Amount");

                } else {

                    try {
                        int tmp = 0;
                        connection = (Connection) DriverManager.getConnection(dbHost, dbUser, dbPass);
                        statement = connection.createStatement();
                        createWithdrawSQl = "insert into withdraw(account_no,w_amount)values('" + acc_no + "','" + w_bal + "')";
                        statement.executeUpdate(createWithdrawSQl);
                        result = statement.executeQuery("select * from account_table where account_no = '" + acc_no + "'");
                        if (result.next()) {

                            if (Double.parseDouble(result.getString("balance")) < w_bal) {
                                JOptionPane.showMessageDialog(null, "You Have Not Sufficient Balance");
                                connection.close();
                                statement.close();

                            } else {
                                w_bal = Double.parseDouble(result.getString("balance")) - w_bal;
                            }
                            createupdatebalancesql = "update account_table set balance = '" + w_bal + "' where account_no = '" + acc_no + "'";
                            statement.executeUpdate(createupdatebalancesql);
                            tmp = 1;
                        }
                        if (tmp == 1) {
                            JOptionPane.showMessageDialog(null, "Paid Hall Fee Successfully!\n Your Current Balance is tk:" + w_bal);
                        } else {
                            JOptionPane.showMessageDialog(null, "Password Mismatch");
                        }

                    } catch (SQLException err) {

                    }

                }

            }
        });
        JLabel TRANS = new JLabel("See All Transaction");
        TRANS.setBounds(72, 150, 120, 20);
        jframe.add(TRANS);
        transaction = new JButton("Transaction");
        transaction.setBounds(70, 180, 120, 25);
        transaction.setBorder(BorderFactory.createLineBorder(Color.yellow));
        transaction.setToolTipText("Click To  Transaction");
        jframe.add(transaction);
        transaction.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                account_no = JOptionPane.showInputDialog(null, "Enter Bank Account Noumber:");
                int acc_no = Integer.parseInt(account_no);
                if (account_no == null) {
                    JOptionPane.showMessageDialog(null, " Please fill up Bank Account Number");

                } else {
                    try {
                        connection = (Connection) DriverManager.getConnection(dbHost, dbUser, dbPass);
                        statement = connection.createStatement();
                        depositeresult = statement.executeQuery("select * from deposite where account_no = '" + acc_no + "'");
                        if (depositeresult.next()) {
                            JOptionPane.showMessageDialog(null, "Deposited Details:\n" + "Account No:" + depositeresult.getString("account_no") + "\nDeposite Amount:" + depositeresult.getString("d_amount") + "\nDate:" + depositeresult.getString("date") + "\nClick 'OK' To See Payment");
                        }
                        withdrawresult = statement.executeQuery("select * from withdraw where account_no = '" + acc_no + "'");
                        if (withdrawresult.next()) {
                            JOptionPane.showMessageDialog(null, "Payment Details\n" + "Account No:" + withdrawresult.getString("account_no") + "\nPayment Amount:" + withdrawresult.getString("w_amount") + "\nDate:" + withdrawresult.getString("date"));
                        }

                    } catch (SQLException err) {
                        JOptionPane.showMessageDialog(null, err.toString());
                    }

                }

            }
        });
        JLabel baln = new JLabel("Total Balance");
        baln.setBounds(212, 150, 120, 20);
        jframe.add(baln);
        balance = new JButton("Balance");
        balance.setBounds(200, 180, 100, 25);
        balance.setToolTipText("Click To Balance");
        balance.setBorder(BorderFactory.createLineBorder(Color.yellow));
        jframe.add(balance);
        balance.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                account_no = JOptionPane.showInputDialog(null, "Enter Bank Account Noumber:");
                int acc_no = Integer.parseInt(account_no);
                if (account_no == null) {
                    JOptionPane.showMessageDialog(null, " Please fill up Bank Account Number");
                }
                try {
                    connection = (Connection) DriverManager.getConnection(dbHost, dbUser, dbPass);
                    statement = connection.createStatement();
                    result = statement.executeQuery("select * from account_table where account_no like '" + acc_no + "'");
                    result.next();
                    totalbalance = (int) Double.parseDouble(result.getString("balance"));
                    JOptionPane.showMessageDialog(null, "Your Bank Balance is = " + totalbalance + "tk");
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, "Account Number Mismatch!");

                }

            }
        });

        close = new JLabel("Create an account?");
        close.setBounds(70, 80, 120, 25);
        jframe.add(close);

        createaccount = new JButton("New Acc");
        createaccount.setBounds(200, 80, 100, 25);
        createaccount.setBorder(BorderFactory.createLineBorder(Color.yellow));
        createaccount.setToolTipText("Click To Create Account");
        jframe.add(createaccount);
        createaccount.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int i = 500;
                account_name = JOptionPane.showInputDialog(null, "Enter account name:");
                account_no = JOptionPane.showInputDialog(null, "Enter account number:");
                a_balance = JOptionPane.showInputDialog(null, "Enter RS balance 500tk:");
                if (Integer.parseInt(a_balance) < i) {
                    JOptionPane.showMessageDialog(null, "Keep minimum 500tk");

                } else {

                    if (account_name != null && account_no != null && a_balance != null) {
                        try {
                            connection = (Connection) DriverManager.getConnection(dbHost, dbUser, dbPass);
                            statement = connection.createStatement();
                            createAccountSql = "insert into account_table (account_name,account_no,balance)values('" + account_name + "','" + Integer.parseInt(account_no) + "','" + Double.parseDouble(a_balance) + "')";
                            statement.executeUpdate(createAccountSql);
                            statement.close();
                            JOptionPane.showMessageDialog(null, "Account Create Successfully");

                        } catch (SQLException err) {
                            JOptionPane.showMessageDialog(null, "Account Number already exist");
                        }

                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter account_name and account_no");

                    }
                }

            }
        });

    }

    public static void main(String args[]) {
        new transaction();
    }

}
