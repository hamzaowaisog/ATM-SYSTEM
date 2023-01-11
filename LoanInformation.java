import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoanInformation implements ActionListener {
    JFrame jf;
    Font f,f1;
    JButton b2 ,b3;
    int atmno,acno,pino;
    String actype;

    LoanInformation(int atmno,int acno,int pino,String actype){
        this.atmno = atmno;
        this.acno = acno;
        this.pino = pino;
        this.actype = actype;

        f = new Font("Times New Roman", Font.BOLD, 25);//button
        f1 = new Font("Times New Roman", Font.BOLD, 30);//label

        jf = new JFrame();
        jf.setLayout(null);
        ImageIcon img1 = new ImageIcon("meezan-bank-vector-logo.png");

        JLabel l1 = new JLabel();
        l1.setIcon(img1);
        l1.setBounds(175, 80, 700, 200);
        jf.add(l1);

        JLabel l2 = new JLabel("LOAN INFORMATION");
        l2.setFont(new Font("Times New Roman", Font.BOLD, 50));
        l2.setBounds(250, 275, 600, 100);
        l2.setForeground(Color.BLACK);
        jf.add(l2);

        JLabel l3=new JLabel("Home Loan @ only 9%.");
        l3.setFont(f1);
        l3.setForeground(Color.BLACK);
        l3.setBounds(40,400,680,40);
        jf.add(l3);

        JLabel l4=new JLabel("Personal Loan @ only 10%.");
        l4.setFont(f1);
        l4.setForeground(Color.BLACK);
        l4.setBounds(40,450,680,40);
        jf.add(l4);

        JLabel l5=new JLabel("Car Loan @ only 12%.");
        l5.setFont(f1);
        l5.setForeground(Color.BLACK);
        l5.setBounds(40,500,680,40);
        jf.add(l5);

        JLabel l6=new JLabel("Student Loan @ only 5%.");
        l6.setFont(f1);
        l6.setForeground(Color.BLACK);
        l6.setBounds(40,550,680,40);
        jf.add(l6);

        JLabel l7=new JLabel("Computer or Laptop Loan @ only 8%.");
        l7.setFont(f1);
        l7.setForeground(Color.BLACK);
        l7.setBounds(40,600,680,40);
        jf.add(l7);

        JLabel l8=new JLabel("For more information Visit our nearest branch of our Bank.");
        l8.setFont(f1);
        l8.setForeground(Color.BLACK);
        l8.setBounds(40,650,800,40);
        jf.add(l8);

        JLabel l9=new JLabel("Visit www.MeezanBank.com or call Toll free no 111-733-733 .");
        l9.setFont(f1);
        l9.setForeground(Color.BLACK);
        l9.setBounds(40,700,900,40);
        jf.add(l9);

        b2 = new JButton("Back", new ImageIcon("back.png"));
        b2.setFont(f);
        b2.setBounds(330,780,150,40);
        b2.addActionListener(this);
        jf.add(b2);

        b3 = new JButton("Exit", new ImageIcon("cancel.png"));
        b3.setFont(f);
        b3.setBounds(530,780,150,40);
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
        new LoanInformation(6000,42301,6666,"current");
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b2){
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
