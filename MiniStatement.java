import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MiniStatement extends JFrame implements ActionListener {
    JFrame jf;
    Font f,f1;
    JButton b1,b2,b3;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int atmno,acno,pino;
    String actype, Strdate,Strtime;
    Date date;
    GregorianCalendar calender;
    DefaultTableModel model;
    JTable tab;
    JScrollPane scrlpane;
    MiniStatement(int atmno,int acno,int pino,String actype){
        this.acno = acno;
        this.atmno = atmno;
        this.pino = pino;
        this.actype = actype;

        date =  new Date();
        calender = new GregorianCalendar();
        calender.setTime(date);
        Strdate = calender.get(Calendar.YEAR)+"-"+(calender.get(Calendar.MONTH)+1)+"-"+calender.get(Calendar.DATE);
        Strtime = date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
        System.out.println(Strdate);
        System.out.println(Strtime);

        f = new Font("Times New Roman", Font.BOLD, 25);//button
        f1 = new Font("Times New Roman", Font.BOLD, 30);//label

        jf = new JFrame();
        jf.setLayout(null);
        ImageIcon img1 = new ImageIcon("meezan-bank-vector-logo.png");

        JLabel l1 = new JLabel();
        l1.setIcon(img1);
        l1.setBounds(175, 80, 700, 200);
        jf.add(l1);

        JLabel l2 = new JLabel("MINI STATEMENT");
        l2.setFont(new Font("Times New Roman", Font.BOLD, 50));
        l2.setBounds(325, 275, 450, 100);
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

        model = new DefaultTableModel();
        tab = new JTable(model);
        scrlpane = new JScrollPane(tab);
        scrlpane.setBounds(80,500,800,200);
        scrlpane.setBackground(new Color(0x939393));
        jf.add(scrlpane);
        tab.setOpaque(false);
        tab.setFont(new Font("Times New Roman",0,20));

        model.addColumn("TRDate");
        model.addColumn("Deposit");
        model.addColumn("Withdrawal");
        model.addColumn("Balance");

        JLabel l9 = new JLabel("Your Available Balance is RS: ");
        l9.setFont(f1);
        l9.setForeground(Color.BLACK);
        l9.setBounds(150,675,500,100);
        jf.add(l9);

        JLabel l10 = new JLabel();
        l10.setFont(f1);
        l10.setForeground(Color.BLACK);
        l10.setBounds(550,675,500,100);
        jf.add(l10);

        b1 = new JButton("Back", new ImageIcon("back.png"));
        b1.setFont(f);
        b1.setBounds(325, 785, 140, 50);
        b1.addActionListener(this);
        jf.add(b1);

        b2 = new JButton("Exit", new ImageIcon("cancel.png"));
        b2.setFont(f);
        b2.setBounds(525, 785, 140, 50);
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

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb","root","root");
            System.out.println("Connected to Databse.");
            ps=con.prepareStatement("select * from transaction where atmno='"+atmno+"' and accno='"+acno+"' order by trid desc limit 11",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            rs.last();
            while(rs.previous()){
                model.insertRow(0,new Object[]{rs.getString("tdate"),rs.getString("depositamt"),rs.getString("withdrawal"),rs.getString("avbalance")});
            }
            ps= con.prepareStatement(" select balance from accountdetail where atmno='"+atmno+"' and accno='"+acno+"'");
            rs= ps.executeQuery();
            while(rs.next()){
                //String curbal = rs.getString("balance");
                System.out.println(rs.getString("balance"));
                l10.setText(rs.getString("balance"));
            }

            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
   /* public static void main(String[] args) {
        new MiniStatement(6000,42301,6666,"current");
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1){
            new TransactionMenu(atmno,acno,pino,actype);
            jf.setVisible(false);
        }
        else if(e.getSource() == b2){
            try {
                new Welcome();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            jf.setVisible(false);
        }

    }
}
