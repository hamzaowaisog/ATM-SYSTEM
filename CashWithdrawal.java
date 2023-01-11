import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.*;
import java.util.Date;

public class CashWithdrawal extends JFrame implements ActionListener {

    JFrame jf;
    Font f, f1;
    JButton b1, b2, b3;
    JTextField ft1;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int atmno, acno, pino;
    String actype, strdate;
    float giv_am, amt, a_bal, atm_min, sum_bal;  //withdrawal amount  //new balance thats to be transacted  //balance from Database   //new amount  //previous balance
    Date date1;
    Statement stmt, stmt1;
    GregorianCalendar calendar;

    CashWithdrawal(int atmno, int acno, int pino, String actype) {
        this.atmno = atmno;
        this.acno = acno;
        this.pino = pino;
        this.actype = actype;

        date1 = new Date();
        calendar = new GregorianCalendar();
        calendar.setTime(date1);
        strdate = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DATE));
        System.out.println(strdate);
        System.out.println(actype);

        f = new Font("Times New Roman", Font.BOLD, 25);//button
        f1 = new Font("Times New Roman", Font.BOLD, 30);//label

        jf = new JFrame();
        jf.setLayout(null);
        ImageIcon img1 = new ImageIcon("meezan-bank-vector-logo.png");

        JLabel l1 = new JLabel();
        l1.setIcon(img1);
        l1.setBounds(175, 80, 700, 200);
        jf.add(l1);

        JLabel l2 = new JLabel("Cash Withdrawal");
        l2.setFont(new Font("Times New Roman", Font.BOLD, 50));
        l2.setBounds(350, 275, 450, 100);
        l2.setForeground(Color.BLACK);
        jf.add(l2);

        JLabel l3 = new JLabel("Enter the amount to withdraw :");
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
        new CashWithdrawal(6000, 42301, 6666, "current");
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            if (ft1.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please Enter Withdrawal Amount!", "Warning!", JOptionPane.WARNING_MESSAGE);
            }
            giv_am = 0;
            amt = 0;
            giv_am = Float.parseFloat(ft1.getText());
            System.out.println("You Enter amount are: " + giv_am);

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb","root","root");
                System.out.println("Connected to database.");
                if (actype.equals("saving")) {
                    if ( giv_am <= 250000) {
                        if (giv_am < 100) {
                            JOptionPane.showMessageDialog(this, "You cannot withdraw amount less than 100 RS!", "Warning", JOptionPane.WARNING_MESSAGE);
                            ft1.setText("");
                        } else if (giv_am > 10000) {
                            JOptionPane.showMessageDialog(this, "You cannot withdraw greater than 10000 RS!", "Warning", JOptionPane.WARNING_MESSAGE);
                            ft1.setText("");
                        } else if (giv_am > 100 && (!(giv_am % 100 == 0))) {
                            JOptionPane.showMessageDialog(this, "Withdrawal amount should be multiple of 100 ", "Warning", JOptionPane.WARNING_MESSAGE);
                            ft1.setText("");
                        } else {
                            ps = con.prepareStatement("select * from accountdetail where atmno='" + atmno + "' and accno='" + acno + "' and pinno='" + pino + "' and acctype='" + actype + "'");
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                a_bal = Float.parseFloat(rs.getString("balance"));
                            }
                                if (a_bal > 1000) {
                                    if (giv_am <= (a_bal - 1000)) {
                                        amt = ((a_bal - 1000) - giv_am);
                                        atm_min = (amt + 1000);
                                        ps = con.prepareStatement("update accountdetail set balance=" + atm_min + " where atmno='" + atmno + "'");
                                        System.out.println("You withdraw rs: " + ft1.getText());
                                        ps.executeUpdate();
                                        ps = con.prepareStatement("insert into transaction(atmno,accno,depositamt,withdrawal,avbalance,tdate) values ('" + atmno + "','" + acno + "',0,'" + giv_am + "','" + atm_min + "','" + strdate + "')");
                                        ps.executeUpdate();

                                        int reply = JOptionPane.showConfirmDialog(this, "Your cash withdrawal is in process take monet from the machine. Do you want to tke receipt?", "Cash Withdrawal Message", JOptionPane.YES_NO_OPTION);

                                        if (reply == JOptionPane.YES_OPTION) {
                                            new BalanceEnquiry(atmno, acno, pino, actype);
                                            jf.setVisible(false);
                                        } else if (reply == JOptionPane.NO_OPTION) {
                                            ps = con.prepareStatement("select * from accountdetail where atmno='" + atmno + "' and accno='" + acno + "'");
                                            rs = ps.executeQuery();
                                            while (rs.next()) {
                                                float curbal = rs.getFloat("balance");
                                                JOptionPane.showMessageDialog(this, "Your available balance is :'" + curbal + "'", "Balance", JOptionPane.INFORMATION_MESSAGE);
                                                new Welcome();
                                                jf.setVisible(false);
                                            }
                                        }

                                    } else {
                                        JOptionPane.showMessageDialog(this, "Your balance is less to withdraw amount ", "Warning", JOptionPane.WARNING_MESSAGE);
                                        ft1.setText("");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(this, "Your Balance is less , you should keep minimum balance", "Warning", JOptionPane.WARNING_MESSAGE);
                                    ft1.setText("");
                                }

                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "You cannot withdraw per day greater than 250000", "Warning", JOptionPane.WARNING_MESSAGE);
                        ft1.setText("");
                    }
                }
                else if (actype.equals("current") ) {
                    if (giv_am <= 50000) {
                        if (giv_am < 100) {
                            JOptionPane.showMessageDialog(this, "You cannot withdraw amount less than 100 RS!", "Warning", JOptionPane.WARNING_MESSAGE);
                            ft1.setText("");
                        } else if (giv_am > 10000) {
                            JOptionPane.showMessageDialog(this, "You cannot withdraw amount greater than 10000", "Warning", JOptionPane.WARNING_MESSAGE);
                            ft1.setText("");
                        } else if (giv_am > 100 && (!(giv_am % 100 == 0))) {
                            JOptionPane.showMessageDialog(this, "Withdrawal amount should be multiple of 100", "Warnign", JOptionPane.WARNING_MESSAGE);
                            ft1.setText("");
                        } else {
                            ps = con.prepareStatement("select * from accountdetail where atmno='" + atmno + "' and accno='" + acno + "' and pinno='" + pino + "' and acctype='" + actype + "'");
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                a_bal = Float.parseFloat(rs.getString(6));

                                if (a_bal > 1000) {
                                    if (giv_am <= (a_bal - 1000)) {
                                        amt = ((a_bal - 1000) - giv_am);
                                        atm_min = (amt + 1000);
                                        ps = con.prepareStatement("update accountdetail set balance="+atm_min+" where atmno='"+atmno+"'");
                                        ps.executeUpdate();
                                        System.out.println("You withdraw rs:"+ ft1.getText());

                                        ps = con.prepareStatement("insert into transaction (atmno,accno,depositamt,withdrawal,avbalance,tdate)values('"+atmno+"','"+acno+"',0,'"+giv_am+"','"+atm_min+"','"+strdate+"') ");
                                        ps.executeUpdate();

                                        int reply = JOptionPane.showConfirmDialog(this,"Your cash withdrawal is in process take money from the machine .Do you want to take the receipt?","Cash withdarwal",JOptionPane.YES_NO_OPTION);

                                        if (reply == JOptionPane.YES_OPTION){       //go to balance display
                                            new BalanceEnquiry(atmno,acno,pino,actype);
                                            jf.setVisible(false);
                                        }
                                        else if(reply == JOptionPane.NO_OPTION){        //only display the balance on the dialog box
                                            ps = con.prepareStatement("select * from accountdetail where atmno='"+atmno+"' and accno='"+acno+"'");
                                            rs = ps.executeQuery();
                                            while(rs.next()){
                                                int curbal = rs.getInt("balance");
                                                JOptionPane.showMessageDialog(this,"Your available balance are : '"+curbal+"'","Available balance",JOptionPane.INFORMATION_MESSAGE);
                                                new Welcome();
                                                jf.setVisible(false);
                                            }
                                        }
                                    }
                                    else{
                                        JOptionPane.showMessageDialog(this,"your balance is less to withdraw amount","Warning",JOptionPane.WARNING_MESSAGE);
                                        ft1.setText("");
                                    }
                                }
                                else{
                                    JOptionPane.showMessageDialog(this,"Your balance is les,you should keep minimum balance ","Warning",JOptionPane.WARNING_MESSAGE);
                                    ft1.setText("");
                                }
                            }
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(this,"Your can not withdraw per day greater than 50000RS!","Warning",JOptionPane.WARNING_MESSAGE);
                        ft1.setText("");
                    }

                }
                con.close();
            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else if (e.getSource()==b2){
            new TransactionMenu(atmno,acno,pino,actype);
            jf.setVisible(false);
        }
        else if (e.getSource()==b3){
            try {
                new Welcome();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            jf.setVisible(false);
        }
    }
}