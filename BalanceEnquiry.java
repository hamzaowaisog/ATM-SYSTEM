import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BalanceEnquiry extends JFrame implements ActionListener {
    JFrame jf ;
    Font f,f1;
    JButton b1,b2;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Date date;
    int atmno,acno,pinno;
    String actype,Strdate,Strtime;

    GregorianCalendar calendar;


    BalanceEnquiry(int atmno,int acno,int pino,String actype) throws ClassNotFoundException {
        this.atmno = atmno;
        this.acno = acno;
        this.pinno = pino;
        this.actype = actype;

        date = new Date();
        calendar = new GregorianCalendar();
        calendar.setTime(date);
        Strdate = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DATE);
        Strtime = String.valueOf(date.getHours()+":"+date.getMinutes()+":"+date.getSeconds());
        System.out.println(Strdate);
        System.out.println(Strtime);

        f = new Font("Times New Roman",Font.BOLD,30);   //Button
        f1 = new Font("Times New Roman",Font.BOLD,35);  //Label


        jf = new JFrame();
        jf.setLayout(null);
        ImageIcon img1 = new ImageIcon("meezan-bank-vector-logo.png");

        JLabel l1 = new JLabel();
        l1.setIcon(img1);
        l1.setBounds(175, 80, 700, 200);
        jf.add(l1);

        JLabel l2 = new JLabel("Balance Enquiry");
        l2.setFont(new Font("Times New Roman", Font.BOLD, 50));
        l2.setBounds(350, 250, 450, 100);
        l2.setForeground(Color.BLACK);
        jf.add(l2);

        JLabel l3 = new JLabel("DATE");
        l3.setFont(f1);
        l3.setForeground(Color.BLACK);
        l3.setBounds(80,350,250,100);
        jf.add(l3);

        JLabel l4 = new JLabel(Strdate);
        l4.setFont(f1);
        l4.setForeground(Color.BLACK);
        l4.setBounds(50,400,250,100);
        jf.add(l4);

        JLabel l5 = new JLabel("TIME");
        l5.setFont(f1);
        l5.setForeground(Color.BLACK);
        l5.setBounds(450,350,250,100);
        jf.add(l5);

        JLabel l6 = new JLabel(Strtime);
        l6.setFont(f1);
        l6.setForeground(Color.BLACK);
        l6.setBounds(435,400,250,100);
        jf.add(l6);

        JLabel l7 = new JLabel("ATM NO");
        l7.setFont(f1);
        l7.setForeground(Color.BLACK);
        l7.setBounds(750,350,250,100);
        jf.add(l7);

        JLabel l8 = new JLabel(String.valueOf(atmno));
        l8.setFont(f1);
        l8.setForeground(Color.BLACK);
        l8.setBounds(780,400,250,100);
        jf.add(l8);

        JLabel l9 = new JLabel("Your Available Balance is RS: ");
        l9.setFont(f1);
        l9.setForeground(Color.BLACK);
        l9.setBounds(150,500,500,100);
        jf.add(l9);

        JLabel l10 = new JLabel();
        l10.setFont(f1);
        l10.setForeground(Color.BLACK);
        l10.setBounds(615,500,500,100);
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

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /*public static void main(String[] args) {
        try {
            new BalanceEnquiry(6000,42301,6666,"current");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==b1){
            new TransactionMenu(atmno,acno,pinno,actype);
            jf.setVisible(false);
        }
        else if (e.getSource()==b2){
            try {
                new Welcome();
                jf.setVisible(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


    }
}
