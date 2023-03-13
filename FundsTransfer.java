import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FundsTransfer implements ActionListener {
    public static void main(String[] args) {
        new FundsTransfer(1000, 10001, 1234, "saving");
    }
    JButton b1,b2,b3;
    JFormattedTextField txt1,txt2;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int atmno , acno,pno;
    String curdate;
    JFrame jf;
    String actype;
    NumberFormat format,format1;
    NumberFormatter formatter,formatter1;

    FundsTransfer(int atmno, int acno, int pno, String actype){

        this.acno = acno;
        this.atmno = atmno;
        this.pno = pno;
        this.actype = actype;

        format = NumberFormat.getInstance();
        formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(-1);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        format1 = NumberFormat.getInstance();
        formatter1 = new NumberFormatter(format1);
        formatter1.setValueClass(Integer.class);
        formatter1.setMinimum(-1);
        formatter1.setMaximum(Integer.MAX_VALUE);
        formatter1.setAllowsInvalid(true);
        formatter1.setCommitsOnValidEdit(true);

        jf = new JFrame();
        ImageIcon img1 = new ImageIcon("meezan-bank-vector-logo.png");
        Date date = new Date();
        GregorianCalendar calender = new GregorianCalendar();
        calender.setTime(date);
        curdate = calender.get(Calendar.YEAR)+"-"+(calender.get(Calendar.MONTH)+1)+"-"+(calender.get(Calendar.DATE));
        System.out.println(curdate);

        Font f = new Font("Times New Roman", Font.BOLD, 25);
        Font f1 = new Font("TImes New Roman", Font.BOLD, 30);
        jf.setLayout(null);

        JLabel l1 = new JLabel();
        l1.setIcon(img1);
        l1.setBounds(175, 100, 700, 200);
        jf.add(l1);
        JLabel l3 = new JLabel();
        l3.setText("Enter ACCOUNT NO :");
        l3.setFont(f1);
        l3.setForeground(Color.BLACK);
        l3.setBounds(120,400,350,30);
        jf.add(l3);

        JLabel l4 = new JLabel();
        l4.setText("Enter AMOUNT :");
        l4.setForeground(Color.BLACK);
        l4.setFont(f1);
        l4.setBounds(120,520,350,30);
        jf.add(l4);

        txt2 = new JFormattedTextField(formatter1);
        txt2.setFont(new Font("Times New Roman",Font.BOLD,35));
        txt2.setBounds(520,520,300,30);
        jf.add(txt2);

        txt1 = new JFormattedTextField(formatter);
        txt1.setBounds(520,400,300,30);
        txt1.setFont(new Font("Times New Roman",Font.BOLD,35));
        jf.add(txt1);

        b1 = new JButton("Transfer",new ImageIcon("ok.png"));
        b1.setFont(f);
        b1.setBounds(150,700,175,50);
        jf.add(b1);
        b1.addActionListener(this);

        b2 = new JButton("Back",new ImageIcon("back.png"));
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

    }
}
