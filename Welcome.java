import javafx.scene.shape.Line;
import org.jetbrains.annotations.NotNull;
import sun.java2d.loops.DrawLine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.applet.*;

public class Welcome extends JFrame implements ActionListener {
    JFrame jf;
    Font f ,f1;
    JButton b ,b1;
    JLabel l1,l2,l3,l4,l5,l6;
    ImageIcon img1;
    ImageIcon img2;
    JTextField t1;


    public void paint(Graphics g){
        g.drawLine(0,380,1000,380);
        g.setColor(Color.BLACK);
    }

    public Welcome() throws IOException {
        jf = new JFrame();
        img1 = new ImageIcon("meezan-bank-vector-logo.png");
        img2 = new ImageIcon("meezanlogo.png");
        f = new Font("Times New Roman",Font.BOLD,20);//button
        f1 = new Font("Times New Roman",Font.BOLD,25);//label
        jf.setLayout(null);

        l2 = new JLabel();
        l2.setIcon(img2);
        l2.setText("Welcome To Meezan Bank");
        l2.setFont(new Font("Times New Roman",Font.BOLD,40));
        l2.setHorizontalTextPosition(JLabel.CENTER);
        l2.setVerticalTextPosition(JLabel.BOTTOM);
        l2.setForeground(Color.BLACK);
        l2.setBounds(280,20,500,300);
        l2.setIconTextGap(30);
        l2.setHorizontalAlignment(JLabel.CENTER);
        l2.setVerticalAlignment(JLabel.TOP);
        jf.add(l2);

        l3 = new JLabel("ATM Services");
        l3.setFont(new Font("Times New Roman",Font.BOLD,30));
        l3.setForeground(Color.BLACK);
        l3.setBounds(440,350,300,30);
        l3.setVerticalTextPosition(JLabel.BOTTOM);
        l3.setHorizontalTextPosition(JLabel.CENTER);
        jf.add(l3);

        l4 = new JLabel("Press OK To Use ATM Service or Press Exit to Quit.");
        l4.setFont(f1);
        l4.setForeground(Color.BLACK);
        l4.setBounds(250,500,750,40);
        jf.add(l4);

        b = new JButton("OK",new ImageIcon("ok.png"));
        b.setFont(f);
        b.setBounds(220,650,130,60);
        jf.add(b);
        b.addActionListener(this);

        b1 = new JButton("Exit",new ImageIcon("cancel.png"));
        b1.setFont(f);
        b1.setBounds(700,650,130,60);
        jf.add(b1);
        b1.addActionListener(this);

        jf.setTitle("MEEZAN BANK ATM");
        jf.setVisible(true);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(1000,900);
        jf.setLocation(220,20);
        jf.setIconImage(img1.getImage());
        jf.getContentPane().setBackground(new Color(0x939393));




    }


  /*  public static void main(String[] args) throws IOException {
        Welcome w1 = new Welcome();
    }*/


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b){
            new AtmCardno();
            jf.setVisible(false);
        }
        else if(e.getSource() == b1){
            System.exit(0);
        }
    }
}

