import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;

public class AccountType extends JFrame implements ActionListener {
    JFrame jf;
    Font f,f1;
    JButton b1,bs1,bc1,b2;
    ImageIcon img1;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int atmno,acno,pno;
    String actype,actypegive;

    AccountType(int atmno , int acno, int pin){
        this.atmno = atmno;
        this.acno = acno;
        this.pno = pin;

        jf = new JFrame();
        jf.setLayout(null);
        ImageIcon img1 = new ImageIcon("meezan-bank-vector-logo.png");

        f = new Font("Times New Roman",Font.BOLD,30);
        f1 = new Font("Times New Roman",Font.BOLD,35);

        JLabel l1 = new JLabel();
        l1.setIcon(img1);
        l1.setBounds(175,80,700,200);
        jf.add(l1);

        JLabel l4 = new JLabel("Account Type");
        l4.setFont(new Font("Times New Roman",Font.BOLD,50));
        l4.setForeground(Color.BLACK);
        l4.setBounds(420,260,700,100);
        jf.add(l4);

        JLabel l2 = new JLabel();
        l2.setText("Select Account Type:");
        l2.setFont(f1);
        l2.setForeground(Color.BLACK);
        l2.setBounds(50,500,700,50);
        jf.add(l2);

        bs1 = new JButton("Saving");
        bs1.setFont(f1);
        bs1.setBounds(420,425,250,50);
        bs1.addActionListener(this);
        jf.add(bs1);

        bc1 = new JButton("Current");
        bc1.setFont(f1);
        bc1.setBounds(420,575,250,50);
        bc1.addActionListener(this);
        jf.add(bc1);

        b1 = new JButton("Exit",new ImageIcon("cancel.png"));
        b1.setFont(f);
        b1.setBounds(650,750,175,50);
        jf.add(b1);
        b1.addActionListener(this);

        b2 = new JButton("Back",new ImageIcon("back.png"));
        b2.setFont(f);
        b2.setBounds(225,750,175,50);
        b2.addActionListener(this);
        jf.add(b2);

        jf.setTitle("MEEZAN BANK ATM");
        jf.setVisible(true);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(1000,900);
        jf.setLocation(220,20);
        jf.setIconImage(img1.getImage());
        jf.getContentPane().setBackground(new Color(0x939393));


    }

    /*public static void main(String[] args) {
        new AccountType(6000,42301,6666);
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b1) {         //EXIT
            JOptionPane.showMessageDialog(this, "Your last transaction cancel");
            try {
                new Welcome();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            jf.setVisible(false);

        }
        else if(e.getSource()==b2){         //BACK
            JOptionPane.showMessageDialog(this,"Going to previous page");
            new AtmCardno();
            jf.setVisible(false);
        }
        else if (e.getSource()==bs1){       //SAVING ACCOUNT
            int foundrec =0;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb","root","root");
                System.out.println("Connected to database.");
                ps = con.prepareStatement("select acctype from accountdetail where atmno='"+atmno+"'and accno='"+acno+"'and pinno='"+pno+"'");
                rs = ps.executeQuery();
                while (rs.next()){
                    actypegive = rs.getString("acctype");
                    System.out.println(actypegive);
                    foundrec =1;
                }
                if(foundrec == 1){
                    if(actypegive.equals("saving")){
                        actype="saving";
                        System.out.println(actypegive);
                        new TransactionMenu(atmno,acno,pno,actype);
                        jf.setVisible(false);
                    }
                    else{
                        JOptionPane.showMessageDialog(this,"Your account type is not matched wiht database","warning",JOptionPane.WARNING_MESSAGE);
                        new Welcome();
                        jf.setVisible(false);
                    }
                    con.close();

                }

            } catch (ClassNotFoundException | SQLException | IOException ex) {
                ex.printStackTrace();
            }

        }
        else if(e.getSource()==bc1){        //Current
            try{
                int foundrec1 = 0;
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb","root","root");
                System.out.println("Connected to Databse");
                ps = con.prepareStatement("select acctype from accountdetail where atmno='"+atmno+"'and accno='"+acno+"'and pinno='"+pno+"'");
                rs = ps.executeQuery();
                while (rs.next()){
                    actypegive= rs.getString("acctype");
                    System.out.println(actypegive);
                    foundrec1 =1;

                }
                if (foundrec1==1){
                    if (actypegive.equals("current")){
                        actype = actypegive;
                        System.out.println(actype);
                        new TransactionMenu(atmno,acno,pno,actype);
                        jf.setVisible(false);
                    }
                    else {
                        JOptionPane.showMessageDialog(this,"Your Account doesnot matched with the database","Warning",JOptionPane.WARNING_MESSAGE);
                        new Welcome();
                        jf.setVisible(false);
                    }
                }
                con.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
            catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }
}
