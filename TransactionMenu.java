import javax.swing.*;
import java.awt.*;
import java.sql.Connection;import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;


public class TransactionMenu extends JFrame implements ActionListener {
    JFrame jf ;
    Font f,f1;
    JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b51;
    int atmno,acno,pino;
    String actype;


    TransactionMenu(int atmno,int acno,int pino ,String actype){

        this.atmno = atmno;
        this.acno = acno;
        this.pino = pino;
        this.actype = actype;

        f = new Font("Times New Roman",Font.BOLD,25);//button
        f1 = new Font("Times New Roman",Font.BOLD,35);//label

        jf = new JFrame();
        jf.setLayout(null);
        ImageIcon img1 = new ImageIcon("meezan-bank-vector-logo.png");

        JLabel l1 = new JLabel();
        l1.setIcon(img1);
        l1.setBounds(175,30,700,200);
        jf.add(l1);

        JLabel l2 = new JLabel();
        l2.setText("SELECT ANY OPTION FROM THE BELOW ");
        l2.setFont(new Font("Times New Roman",Font.BOLD,40));
        l2.setForeground(Color.BLACK);
        l2.setBounds(85,175,900,200);
        jf.add(l2);

        b1 = new JButton("Cash Withdrawal ");
        b1.setFont(f);
        b1.setBounds(150,350,250,50);
        b1.addActionListener(this);
        jf.add(b1);

        b2 = new JButton("Cash Deposit ");
        b2.setFont(f);
        b2.setBounds(550,350,250,50);
        b2.addActionListener(this);
        jf.add(b2);

        b3 = new JButton("Balance Enquiry");
        b3.setFont(f);
        b3.setBounds(150,450,250,50);
        b3.addActionListener(this);
        jf.add(b3);

        b4 = new JButton("Pin Change");
        b4.setFont(f);
        b4.setBounds(550,450,250,50);
        b4.addActionListener(this);
        jf.add(b4);

        b5 = new JButton("Mini Statement ");
        b5.addActionListener(this);
        b5.setBounds(150,550,250,50);
        b5.setFont(f);
        jf.add(b5);

        b6 = new JButton("Loan Information ");
        b6.setFont(f);
        b6.setBounds(550,550,250,50);
        b6.addActionListener(this);
        jf.add(b6);

        b7 = new JButton("Electricity Bill Pay ");
        b7.setFont(f);
        b7.setBounds(150,650,250,50);
        b7.addActionListener(this);
        jf.add(b7);

        b8 = new JButton("Help");
        b8.setFont(f);
        b8.setBounds(550,650,250,50);
        b8.addActionListener(this);
        jf.add(b8);

        b9 = new JButton("Exit ",new ImageIcon("cancel.png"));
        b9.setFont(f);
        b9.setBounds(525,765,175,50);
        b9.addActionListener(this);
        jf.add(b9);

        b51 = new JButton("Back ",new ImageIcon("back.png"));
        b51.setFont(f);
        b51.setBounds(275,765,175,50);
        b51.addActionListener(this);
        jf.add(b51);



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
        new TransactionMenu(6000,42301,6666,"current");
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b1){
            new CashWithdrawal(atmno,acno,pino,actype);
            jf.setVisible(false);
        }
        else if(e.getSource()==b2){
            new CashDeposit(atmno,acno,pino,actype);
            jf.setVisible(false);
        }
        else if(e.getSource()==b3){
            try {
                new BalanceEnquiry(atmno,acno,pino,actype);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            jf.setVisible(false);
        }
        else if (e.getSource()==b4){
            new PinChange(atmno,acno,pino,actype);
            jf.setVisible(false);
        }
        else if (e.getSource() == b5){
            new MiniStatement(atmno,acno,pino,actype);
            jf.setVisible(false);
        }
        else if (e.getSource() == b6){
            new LoanInformation(atmno,acno,pino,actype);
            jf.setVisible(false);
        }
        else if (e.getSource()==b7){
            new ElectricityBillPay(atmno,acno,pino,actype);
            jf.setVisible(false);
        }
        else if(e.getSource()==b8){
            new Help( atmno, acno, pino, actype);
            jf.setVisible(false);
        }
        else if (e.getSource() == b9){
            JOptionPane.showMessageDialog(this,"Your Last Transaction cancel","Warning",JOptionPane.WARNING_MESSAGE);
            try {
                new Welcome();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            jf.setVisible(false);
        }
        else if (e.getSource()== b51){
            JOptionPane.showMessageDialog(this,"You are moving to the back page","Warning",JOptionPane.WARNING_MESSAGE);
            new AccountType(atmno,acno,pino);
            jf.setVisible(false);
        }

    }
}
