
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AtmCardno extends JFrame implements ActionListener {

    JButton b1,b2,b3;
    JTextField txt;
    JPasswordField pwd;
    Connection con;
    PreparedStatement ps;
    Statement stmt;
    ResultSet rs;
    int atmno , acno, pno;
    String curdate;
    JFrame jf;


    AtmCardno(){


        jf = new JFrame();
        ImageIcon img1 = new ImageIcon("meezan-bank-vector-logo.png");
        Date date = new Date();
        GregorianCalendar calender = new GregorianCalendar();
        calender.setTime(date);
        curdate = calender.get(Calendar.YEAR)+"-"+(calender.get(Calendar.MONTH)+1)+"-"+(calender.get(Calendar.DATE));
        System.out.println(curdate);

        Font f = new Font("Times New Roman",Font.BOLD,25);
        Font f1 = new Font("Times New Roman",Font.BOLD,30);
        jf.setLayout(null);

        JLabel l1 = new JLabel();
        l1.setIcon(img1);
        l1.setBounds(175,100,700,200);
        jf.add(l1);

        /*JLabel l2 = new JLabel();
        l2.setText("ATM CARD NUMBER");
        l2.setFont(new Font("Times New Roman",Font.BOLD,40));
        l2.setHorizontalTextPosition(JLabel.CENTER);
        l2.setVerticalTextPosition(JLabel.CENTER);
        l2.setHorizontalAlignment(JLabel.CENTER);
        l2.setVerticalAlignment(JLabel.CENTER);
        l2.setForeground(Color.BLACK);
        l2.setBounds(300,350,500,30);
        jf.add(l2);*/

        JLabel l3 = new JLabel();
        l3.setText("Enter ATM CARD NO :");
        l3.setFont(f1);
        l3.setForeground(Color.BLACK);
        l3.setBounds(120,400,350,30);
        jf.add(l3);

        JLabel l4 = new JLabel();
        l4.setText("Enter PIN NO :");
        l4.setForeground(Color.BLACK);
        l4.setFont(f1);
        l4.setBounds(120,520,350,30);
        jf.add(l4);

        pwd  = new JPasswordField(10);
        pwd.setFont(new Font("Times New Roman",Font.BOLD,35));
        pwd.setBounds(520,520,300,30);
        jf.add(pwd);

        txt = new JTextField();
        txt.setBounds(520,400,300,30);
        txt.setFont(new Font("Times New Roman",Font.BOLD,35));
        jf.add(txt);

        b1 = new JButton("Enter",new ImageIcon("ok.png"));
        b1.setFont(f);
        b1.setBounds(150,700,175,50);
        jf.add(b1);
        b1.addActionListener(this);

        b2 = new JButton("Clear",new ImageIcon("clear.png"));
        b2.setFont(f);
        b2.setBounds(435,700,175,50);
        jf.add(b2);
        b2.addActionListener(this);

        b3 = new JButton("Exit",new ImageIcon("cancel.png"));
        b3.setFont(f);
        b3.setBounds(700,700,175,50);
        jf.add(b3);
        b3.addActionListener(this);


        jf.setTitle("MEEZAN BANK ATM");
        jf.setVisible(true);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(1000,900);
        jf.setLocation(220,20);
        jf.setIconImage(img1.getImage());
        jf.getContentPane().setBackground(new Color(0x939393));


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b1){
            try{
                if(((txt.getText()).equals(""))&&(pwd.getText().equals(""))){
                    JOptionPane.showMessageDialog(this,"Please enter ATM card no and Pin no!","Warning",JOptionPane.WARNING_MESSAGE);
                }
                else {
                    int frec=0;
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb","root","root");
                    System.out.println("Connected to DataBase.");
                    ps = con.prepareStatement("select * from accountdetail where atmno='"+txt.getText()+"' and pinno='"+pwd.getText()+"'");
                    rs = ps.executeQuery();
                    while (rs.next()){
                        atmno = rs.getInt(1);
                        System.out.println(atmno);
                        acno = rs.getInt(2);
                        System.out.println(acno);
                        pno = rs.getInt(3);
                        System.out.println(pno);
                        String cname;
                        cname = rs.getString(5);
                        System.out.println(cname);
                        String exdate;
                        exdate = rs.getString(7);
                        System.out.println(exdate);

                        try{
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date d1 = sdf.parse(exdate);
                            Date d2 = sdf.parse(curdate);
                            System.out.println();

                            if(d1.compareTo(d2)>=0){
                                JOptionPane.showMessageDialog(this,"Hello "+cname+" !");
                                new AccountType(atmno,acno,pno);
                                jf.setVisible(false);
                            }
                            else if (d1.compareTo(d2)<0){
                                JOptionPane.showMessageDialog(this,"Your atm is card is expired! please order new atm card","Warning",JOptionPane.WARNING_MESSAGE);
                                new Welcome();
                                jf.setVisible(false);
                            }
                        }
                        catch (ParseException p1){
                            p1.printStackTrace();
                        }

                        frec =1;
                    }
                    if(frec ==0){
                        JOptionPane.showMessageDialog(this,"Invalid ATM no or Pin no","Warning",JOptionPane.WARNING_MESSAGE);
                        txt.setText("");
                        pwd.setText("");
                    }
                    con.close();
                }
            }
            catch (SQLException se){
                se.printStackTrace();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }

        }
        else if(e.getSource()==b2){
            txt.setText("");
            pwd.setText("");
        }
        else if(e.getSource()==b3){
            JOptionPane.showMessageDialog(this,"Your transaction cancel","Warning",JOptionPane.WARNING_MESSAGE);
            try {
                new Welcome();
                jf.setVisible(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }


        }


    }




  /* public static void main(String[] args) {
        new AtmCardno();
    }*/
}