
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
public class ElectricityBillPay extends JFrame implements ActionListener {
    JFrame jf;
    Font f,f1;
    JButton b1,b2,b3;
    JTextField txt , txt1;
    int atmno,acno,pino;
    String actype,strdate,strtime;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Date date1;
    GregorianCalendar calendar;



    ElectricityBillPay(int atmno,int acno,int pino,String actype){

        this.atmno = atmno;
        this.acno = acno;
        this.pino = pino;
        this.actype = actype;

        date1= new Date();
        calendar=new GregorianCalendar();
        calendar.setTime(date1);
        strdate =calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DATE);
        System.out.println(strdate);
        strtime = String.valueOf(date1.getHours()+":"+date1.getMinutes()+":"+date1.getSeconds());

        f = new Font("Times New Roman", Font.BOLD, 25);//button
        f1 = new Font("Times New Roman", Font.BOLD, 30);//label

        jf = new JFrame();
        jf.setLayout(null);
        ImageIcon img1 = new ImageIcon("meezan-bank-vector-logo.png");

        JLabel l1 = new JLabel();
        l1.setIcon(img1);
        l1.setBounds(175, 80, 700, 200);
        jf.add(l1);

        JLabel l2 = new JLabel("Electricity Bill pay");
        l2.setFont(new Font("Times New Roman", Font.BOLD, 50));
        l2.setBounds(350, 275, 600, 100);
        l2.setForeground(Color.BLACK);
        jf.add(l2);

        JLabel l3 = new JLabel();
        l3.setText("Enter Electricity bill no :");
        l3.setFont(f1);
        l3.setForeground(Color.BLACK);
        l3.setBounds(90,450,500,30);
        jf.add(l3);

        JLabel l4 = new JLabel();
        l4.setText("Enter Electricity bill amount :");
        l4.setForeground(Color.BLACK);
        l4.setFont(f1);
        l4.setBounds(90,570,450,30);
        jf.add(l4);

        txt1  = new JTextField();
       txt1.setFont(new Font("Times New Roman",Font.BOLD,35));
        txt1.setBounds(570,570,300,30);
        jf.add(txt1);

        txt = new JTextField();
        txt.setBounds(570,450,300,30);
        txt.setFont(new Font("Times New Roman",Font.BOLD,35));
        jf.add(txt);

        b1 = new JButton("Enter",new ImageIcon("ok.png"));
        b1.setFont(f);
        b1.setBounds(220,780,150,40);
        jf.add(b1);
        b1.addActionListener(this);

        b2 = new JButton("Back", new ImageIcon("back.png"));
        b2.setFont(f);
        b2.setBounds(450,780,150,40);
        b2.addActionListener(this);
        jf.add(b2);

        b3 = new JButton("Exit", new ImageIcon("cancel.png"));
        b3.setFont(f);
        b3.setBounds(680,780,150,40);
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

    /*public static void main(String[] args) {
        new ElectricityBillPay(6000,42301,6666,"current");
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
        else if(e.getSource() == b1){
            if(txt.getText().equals("")&& txt1.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please enter electricity bill no and amount","Warning",JOptionPane.WARNING_MESSAGE);
            }
            int d_bill=0;
            int d_amount=0;
            boolean found = false;
            float giv_am = 0;
            float amt =0;
            int billno = Integer.parseInt(txt.getText());
            giv_am = Float.parseFloat(txt1.getText());
            System.out.println("you enter bill amount is "+giv_am);
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb","root","root");
                System.out.println("Connect to database");
                ps = con.prepareStatement("select * from billno where billno="+billno);
                rs = ps.executeQuery();
                while (rs.next()) {
                    d_bill = rs.getInt("billno");
                    d_amount = rs.getInt("amount");

                }
                if(d_bill == billno && giv_am == d_amount){
                    found = true;
                }
                if (found) {
                    ps = con.prepareStatement("select * from accountdetail where atmno='" + atmno + "' and accno='" + acno + "' and pinno='" + pino + "' and acctype='" + actype + "'");
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        float abal = Float.parseFloat(rs.getString("balance"));
                        if (abal > 1000) {
                            if (giv_am <= (abal - 1000)) {
                                amt = (abal - 1000) - giv_am;
                                float amount = amt;
                                ps = con.prepareStatement("update accountdetail set balance=" + amount + " where atmno='" + atmno + "'");
                                ps.executeUpdate();
                                ps = con.prepareStatement("insert into transaction (atmno,accno,depositamt,withdrawal,avbalance,tdate)values('" + atmno + "','" + acno + "',0,'" + giv_am + "','" + amount + "','" + strdate + "') ");
                                ps.executeUpdate();
                                ps = con.prepareStatement("insert into electricitybill (atmno,accno,ebillno,ebillamount,edate)values('" + atmno + "','" + acno + "','" + billno + "','" + giv_am + "','" + strdate + "') ");
                                ps.executeUpdate();

                                System.out.println("You paid bill RS: " + txt1.getText());
                                int reply = JOptionPane.showConfirmDialog(this, "your bill paid , Do you want to take the receipt", "Electricity bill pay", JOptionPane.YES_NO_OPTION);

                                if (reply == JOptionPane.YES_OPTION) {
                                    new ElectricityBillPaid(atmno, acno, pino, actype, billno, giv_am, strdate, strtime);
                                    jf.setVisible(false);
                                } else if (reply == JOptionPane.NO_OPTION) {
                                    JOptionPane.showMessageDialog(this, "Your available balance is '" + amount + "'", "Electricity bill paid", JOptionPane.WARNING_MESSAGE);
                                    new Welcome();
                                    jf.setVisible(false);

                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Your balance is less to pay electricity bill", "Warning", JOptionPane.WARNING_MESSAGE);
                                txt1.setText("");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Your balance is less,You should keep minimum balance 1000 RS", "Warning", JOptionPane.WARNING_MESSAGE);
                            txt1.setText("");
                        }

                    }
                }
                else{
                    JOptionPane.showMessageDialog(this, "Please enter correct bill no and amount" ,"ERROR",JOptionPane.ERROR_MESSAGE);
                }
                con.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }


        }

    }
}
