import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CashDeposit extends JFrame implements ActionListener {
    JFrame jf;
    Font f, f1;
    JButton b1, b2, b3;
    JTextField ft1;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int atmno, acno, pino;
    String actype, strdate;
    float giv_am, amt, a_bal, atm_min, sum_bal;  //Deposit amount  //new balance of the account  //Available balance from Database   //new amount  //previous balance
    Date date1;
    GregorianCalendar calendar;


    CashDeposit(int atmno, int acno, int pino, String actype) {

        this.atmno = atmno;
        this.acno = acno;
        this.pino = pino;
        this.actype = actype;

        date1 = new Date();
        calendar = new GregorianCalendar();
        calendar.setTime(date1);
        strdate = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);
        System.out.println(strdate);

        f = new Font("Times New Roman", Font.BOLD, 25);//button
        f1 = new Font("Times New Roman", Font.BOLD, 30);//label

        jf = new JFrame();
        jf.setLayout(null);
        ImageIcon img1 = new ImageIcon("meezan-bank-vector-logo.png");

        JLabel l1 = new JLabel();
        l1.setIcon(img1);
        l1.setBounds(175, 80, 700, 200);
        jf.add(l1);

        JLabel l2 = new JLabel("Cash Deposit");
        l2.setFont(new Font("Times New Roman", Font.BOLD, 50));
        l2.setBounds(350, 275, 450, 100);
        l2.setForeground(Color.BLACK);
        jf.add(l2);

        JLabel l3 = new JLabel("Enter the amount to Deposit :");
        l3.setForeground(Color.BLACK);
        l3.setFont(f1);
        l3.setBounds(45, 480, 450, 100);
        jf.add(l3);

        ft1 = new JTextField(40);
        ft1.setFont(f);
        ft1.setBounds(480, 505, 450, 50);
        jf.add(ft1);

        b1 = new JButton("Ok", new ImageIcon("ok.png"));
        b1.setFont(f);
        b1.setBounds(225, 725, 140, 50);
        b1.addActionListener(this);
        jf.add(b1);

        b2 = new JButton("Back", new ImageIcon("back.png"));
        b2.setFont(f);
        b2.setBounds(425, 725, 140, 50);
        b2.addActionListener(this);
        jf.add(b2);

        b3 = new JButton("Exit", new ImageIcon("cancel.png"));
        b3.setFont(f);
        b3.setBounds(625, 725, 140, 50);
        b3.addActionListener(this);
        jf.add(b3);

        jf.setTitle("MEEZAN BANK ATM");
        jf.setVisible(true);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(1000, 900);
        jf.setLocation(220, 20);
        jf.setIconImage(img1.getImage());
        jf.getContentPane().setBackground(new Color(0x939393));

    }

   /* public static void main(String[] args) {
        new CashDeposit(6000,42301,6666,"currrent");
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b2) {
            new TransactionMenu(atmno, acno, pino, actype);
            jf.setVisible(false);
        } else if (e.getSource() == b3) {
            try {
                new Welcome();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            jf.setVisible(false);
        } else if (e.getSource() == b1) {
            if (ft1.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter deposit amount!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                giv_am = 0;
                amt = 0;
                giv_am = Float.parseFloat(ft1.getText());
                System.out.println("You enter deposit amount is :" + giv_am);

                try {
                    float curbal = 0;
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb", "root", "root");
                    System.out.println("Connected to the Database");
                    ps = con.prepareStatement("select * from accountdetail where atmno='" + atmno + "' and accno='" + acno + "'");
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        curbal = rs.getFloat(6);
                    }
                    amt = curbal + giv_am;
                    ps = con.prepareStatement("update accountdetail set balance=" + amt + " where atmno ='" + atmno + "' and accno='" + acno + "'");
                    ps.executeUpdate();

                    ps = con.prepareStatement("insert into transaction (atmno,accno,depositamt,withdrawal,avbalance,tdate)values('" + atmno + "','" + acno + "','" + giv_am + "',0,'" + amt + "','" + strdate + "') ");
                    ps.executeUpdate();

                    int reply = JOptionPane.showConfirmDialog(this, "Your cash is been depositing .Do you want to take the receipt?", "Cash Deposit", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        new BalanceEnquiry(atmno, acno, pino, actype);
                        jf.setVisible(false);
                    } else if (reply == JOptionPane.NO_OPTION) {
                        ps = con.prepareStatement("select * from accountdetail where atmno='" + atmno + "' and accno='" + acno + "'");
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            float curbal1 = rs.getFloat("balance");
                            JOptionPane.showMessageDialog(this, "your available balance is :'" + curbal1 + "'", "Balance", JOptionPane.WARNING_MESSAGE);
                            new Welcome();
                            jf.setVisible(false);

                        }
                    }
                    con.close();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }
    }
}