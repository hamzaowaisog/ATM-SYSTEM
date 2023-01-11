import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Help extends JFrame implements ActionListener {
    JFrame jf;
    Font f,f1;
    JButton b2 ,b3;
    int atmno,acno,pino;
    String actype;

    Help(int atmno,int acno,int pino,String actype){
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

        JLabel l2 = new JLabel("HELP");
        l2.setFont(new Font("Times New Roman", Font.BOLD, 50));
        l2.setBounds(525, 275, 600, 100);
        l2.setForeground(Color.BLACK);
        jf.add(l2);

        JLabel l3=new JLabel("Cash withdrawal options used to getting money from the ATM.");
        l3.setFont(f);
        l3.setForeground(Color.BLACK);
        l3.setBounds(40,400,670,25);
        jf.add(l3);

       JLabel l4=new JLabel("Balance enquiry used to display and print your balance.");
        l4.setFont(f);
        l4.setForeground(Color.BLACK);
        l4.setBounds(40,440,670,25);
        jf.add(l4);

       JLabel l5=new JLabel("Mini statement used to print your balance.");
        l5.setFont(f);
        l5.setForeground(Color.BLACK);
        l5.setBounds(40,485,650,25);
        jf.add(l5);

       JLabel l51=new JLabel("Electricty bill used pay electricty bill.");
        l51.setFont(f);
        l51.setForeground(Color.BLACK);
        l51.setBounds(40,530,650,25);
        jf.add(l51);

       JLabel l61=new JLabel("cash deposit used deposit money into your account.");
        l61.setFont(f);
        l61.setForeground(Color.BLACK);
        l61.setBounds(40,575,650,25);
        jf.add(l61);

       JLabel l6=new JLabel("PIN change option used to change PIN no of ATM card.");
        l6.setFont(f);
        l6.setForeground(Color.BLACK);
        l6.setBounds(40,620,670,25);
        jf.add(l6);


       JLabel l7=new JLabel("Loan information option used to give various loan rate.");
        l7.setFont(f);
        l7.setForeground(Color.BLACK);
        l7.setBounds(40,660,650,25);
        jf.add(l7);


       JLabel l8=new JLabel("For more information Visit our your nearest branch of our Bank.");
        l8.setFont(f);
        l8.setForeground(Color.BLACK);
        l8.setBounds(40,705,700,25);
        jf.add(l8);

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

  /*  public static void main(String[] args) {
        new Help(6000,42301,6666,"current");
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
