import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ElectricityBillPaid implements ActionListener {
    JFrame jf;
    Font f,f1;
    JButton b1,b2;
    String actype,Strdate,Strtime;
    int atmno,acno,pinno,billno;
    float amount;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    ElectricityBillPaid(int  atmno,int acno,int pino,String actype,int billno,float amount,String strdate,String strtime){
        this.atmno = atmno;
        this.acno = acno;
        this.pinno = pino;
        this.actype = actype;
        this.billno = billno;
        this.amount = amount;
        this.Strdate = strdate;
        this.Strtime = strtime;


        f = new Font("Times New Roman",Font.BOLD,25);   //Button
        f1 = new Font("Times New Roman",Font.BOLD,25);  //Label


        jf = new JFrame();
        jf.setLayout(null);
        ImageIcon img1 = new ImageIcon("meezan-bank-vector-logo.png");

        JLabel l1 = new JLabel();
        l1.setIcon(img1);
        l1.setBounds(195, 80, 700, 200);
        jf.add(l1);

        JLabel l2 = new JLabel("Electricity Bill paid");
        l2.setFont(new Font("Times New Roman", Font.BOLD, 50));
        l2.setBounds(370, 250, 450, 100);
        l2.setForeground(Color.BLACK);
        jf.add(l2);

        JLabel l3 = new JLabel("DATE");
        l3.setFont(f1);
        l3.setForeground(Color.BLACK);
        l3.setBounds(170,350,250,100);
        jf.add(l3);

        JLabel l4 = new JLabel(Strdate);
        l4.setFont(f1);
        l4.setForeground(Color.BLACK);
        l4.setBounds(140,400,250,100);
        jf.add(l4);

        JLabel l5 = new JLabel("TIME");
        l5.setFont(f1);
        l5.setForeground(Color.BLACK);
        l5.setBounds(300,350,250,100);
        jf.add(l5);

        JLabel l6 = new JLabel(Strtime);
        l6.setFont(f1);
        l6.setForeground(Color.BLACK);
        l6.setBounds(300,400,250,100);
        jf.add(l6);

        JLabel l7 = new JLabel("ATM NO");
        l7.setFont(f1);
        l7.setForeground(Color.BLACK);
        l7.setBounds(400,350,250,100);
        jf.add(l7);

        JLabel l8 = new JLabel(String.valueOf(atmno));
        l8.setFont(f1);
        l8.setForeground(Color.BLACK);
        l8.setBounds(425,400,250,100);
        jf.add(l8);

        JLabel l15 = new JLabel("BILL NO ");
        l15.setFont(f1);
        l15.setForeground(Color.BLACK);
        l15.setBounds(545,350,250,100);
        jf.add(l15);

        JLabel l16 = new JLabel(String.valueOf(billno));
        l16.setFont(f1);
        l16.setBounds(555,400,250,100);
        l16.setForeground(Color.BLACK);
        jf.add(l16);

        JLabel l17 = new JLabel("BILL AMOUNT ");
        l17.setFont(f1);
        l17.setBounds(685,350,250,100);
        l17.setForeground(Color.BLACK);
        jf.add(l17);

        JLabel l18 = new JLabel(String.valueOf(amount));
        l18.setFont(f1);
        l18.setBounds(715,400,250,100);
        l18.setForeground(Color.BLACK);
        jf.add(l18);


        JLabel l9 = new JLabel("Your Available Balance is RS: ");
        l9.setFont(f1);
        l9.setForeground(Color.BLACK);
        l9.setBounds(180,500,500,100);
        jf.add(l9);

        JLabel l10 = new JLabel();
        l10.setFont(f1);
        l10.setForeground(Color.BLACK);
        l10.setBounds(520,500,500,100);
        jf.add(l10);

        JLabel l11 = new JLabel("Thank you for Banking with Meezan Bank . For more Infromation,");
        l11.setFont(f);
        l11.setForeground(Color.BLACK);
        l11.setBounds(50,570,900,100);
        jf.add(l11);

        JLabel l12 = new JLabel("Please Call Toll Free No:1234-777-777 or Visit www.meezanbank.com");
        l12.setBounds(50,620,900,100);
        l12.setFont(f);
        l12.setForeground(Color.black);
        jf.add(l12);

        b1 = new JButton("Back", new ImageIcon("back.png"));
        b1.setFont(f);
        b1.setBounds(325, 750, 140, 50);
        b1.addActionListener(this);
        jf.add(b1);

        b2 = new JButton("Exit", new ImageIcon("cancel.png"));
        b2.setFont(f);
        b2.setBounds(525, 750, 140, 50);
        b2.addActionListener(this);
        jf.add(b2);

        jf.setTitle("MEEZAN BANK ATM");
        jf.setVisible(true);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(1000, 900);
        jf.setLocation(220, 20);
        jf.setIconImage(img1.getImage());
        jf.getContentPane().setBackground(new Color(0x939393));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb","root","root");
            System.out.println("Connected to database.");
            ps=con.prepareStatement("select * from accountdetail where atmno='"+atmno+"' and accno='"+acno+"'");
            rs=ps.executeQuery();
            while(rs.next())
            {
                String curbal=rs.getString(6);
                System.out.println("Available balance are:"+curbal);
                l10.setText(curbal);
            }
            con.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b1){
            new TransactionMenu(atmno,acno,pinno,actype);
            jf.setVisible(false);
        }
        else if (e.getSource()==b2){
            try {
                new Welcome();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            jf.setVisible(false);
        }

    }

   /* public static void main(String[] args) {
        new ElectricityBillPaid(6000,42301,6666,"current",65231,1200,"2022-05-20","3:07:07");
    }*/
}
