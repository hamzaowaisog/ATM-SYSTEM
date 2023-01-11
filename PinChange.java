import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;

public class PinChange extends JFrame implements ActionListener {

    JFrame jf;
    Font f,f1;
    JButton b1,b2,b3;
    JPasswordField p1,p2,p3;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int atmno , acno , pino;
    String actype;
    int pp1 ,pp2, pp3;
    PinChange(int atmno,int acno,int pino,String actype){
        this.atmno =atmno;
        this.acno = acno;
        this.pino = pino;
        this.actype = actype;

        f = new Font("Times New Roman", Font.BOLD, 35);//button
        f1 = new Font("Times New Roman", Font.BOLD, 30);//label

        jf = new JFrame();
        jf.setLayout(null);
        ImageIcon img1 = new ImageIcon("meezan-bank-vector-logo.png");

        JLabel l1 = new JLabel();
        l1.setIcon(img1);
        l1.setBounds(175, 80, 700, 200);
        jf.add(l1);

        JLabel l2 = new JLabel("PIN CHANGE");
        l2.setFont(new Font("Times New Roman", Font.BOLD, 50));
        l2.setBounds(350, 275, 450, 100);
        l2.setForeground(Color.BLACK);
        jf.add(l2);

        JLabel l3 = new JLabel("Enter Old PIN");
        l3.setFont(f1);
        l3.setForeground(Color.BLACK);
        l3.setBounds(150,450,200,30);
        jf.add(l3);

        JLabel l4 = new JLabel("Enter New PIN");
        l4.setFont(f1);
        l4.setForeground(Color.BLACK);
        l4.setBounds(150,525,200,30);
        jf.add(l4);

        JLabel l5 = new JLabel("Re-Enter New PIN");
        l5.setFont(f1);
        l5.setForeground(Color.BLACK);
        l5.setBounds(150,600,250,30);
        jf.add(l5);

        p1 = new JPasswordField(10);
        p1.setBounds(420,450,300,30);
        jf.add(p1);

        p2 = new JPasswordField(10);
        p2.setBounds(420,525,300,30);
        jf.add(p2);

        p3 = new JPasswordField(10);
        p3.setBounds(420,600,300,30);
        jf.add(p3);

        b1 = new JButton("Ok", new ImageIcon("ok.png"));
        b1.setFont(f);
        b1.setBounds(180,750,150,40);
        b1.addActionListener(this);
        jf.add(b1);

        b2 = new JButton("Back", new ImageIcon("back.png"));
        b2.setFont(f);
        b2.setBounds(410,750,150,40);
        b2.addActionListener(this);
        jf.add(b2);

        b3 = new JButton("Exit", new ImageIcon("cancel.png"));
        b3.setFont(f);
        b3.setBounds(630,750,150,40);
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
        new PinChange(6000,42301,6666,"current");
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== b2){
            new TransactionMenu(atmno,acno,pino,actype);
            jf.setVisible(false);
        }
        else if (e.getSource() == b3){
            try {
                new Welcome();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            jf.setVisible(false);
        }
        else if (e.getSource() == b1){
            if(p1.getText().equals("")&& p2.getText().equals("")&& p3.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please enter the Pin , you have set the pin to the blank","Warning",JOptionPane.WARNING_MESSAGE);
            }
            else if (Integer.parseInt(p2.getText())!=(Integer.parseInt(p3.getText()))){
                JOptionPane.showMessageDialog(this,"Please Enter the correct new Pin no,Your Pin no doesnot match","Warning",JOptionPane.WARNING_MESSAGE);
                p1.setText("");
                p2.setText("");
                p3.setText("");
            }
            else {
                try {
                    int foundrec = 0;
                    int p11 = Integer.parseInt(p1.getText());
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb","root","root");
                    System.out.println("Connected to Database");
                    ps=con.prepareStatement("select * from accountdetail where atmno='"+atmno+"' and accno='"+acno+"' and acctype='"+actype+"' and pinno='"+p11+"' ");
                    rs=ps.executeQuery();
                    while(rs.next())
                    {
                        foundrec=1;
                    }
                    if(foundrec==1) {
                        pp1 = Integer.parseInt(p1.getText());
                        pp2 = Integer.parseInt(p2.getText());
                        pp3 = Integer.parseInt(p3.getText());

                        ps = con.prepareStatement("update accountdetail set pinno="+pp2+" where atmno ='"+atmno+"' and accno = '"+acno+"' and acctype ='"+actype+"' and pinno ='"+pino+"'");
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(this,"Your Password Changed!","Pin Change",JOptionPane.WARNING_MESSAGE);

                    }
                    else {
                        JOptionPane.showMessageDialog(null,"You enter wrong old PIN no.");
                        p1.setText("");
                        p2.setText("");
                        p3.setText("");
                    }
                    con.close();

                    new TransactionMenu(atmno,acno,pino,actype);
                    jf.setVisible(false);

                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
