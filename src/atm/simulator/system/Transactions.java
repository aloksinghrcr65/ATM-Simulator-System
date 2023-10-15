
package atm.simulator.system;

/**
 *
 * @author aloks
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Transactions extends JFrame implements ActionListener{

    JLabel l1;
    JButton b1,b2,b3,b4,b5,b6,b7;
    String pin;
    String cardNumber;
    Transactions( String cardNumber,String pin){
        this.cardNumber = cardNumber;
        this.pin = pin;
        
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l2 = new JLabel(i3);
        l2.setBounds(0, 0, 900, 900);
        add(l2);
        
        l1 = new JLabel("Please Select Your Transaction");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(210,300,700,30);
        l2.add(l1);
        
       
        b1 = new JButton("DEPOSIT");
        b1.setBounds(170,415,150,30);
        b1.setBackground(new Color(220, 220, 220));
        b1.addActionListener(this);
        l2.add(b1);
        
        b2 = new JButton("CASH WITHDRAWAL");
        b2.setBounds(355,415,150,30);
        b2.setBackground(new Color(220, 220, 220));
        b2.addActionListener(this);
        l2.add(b2);
        
        b3 = new JButton("FAST CASH");
        b3.setBounds(170,450,150,30);
        b3.setBackground(new Color(220, 220, 220));
        b3.addActionListener(this);
        l2.add(b3);
        
        b4 = new JButton("MINI STATEMENT");
        b4.setBounds(355,450,150,30);
        b4.setBackground(new Color(220, 220, 220));
        b4.addActionListener(this);
        l2.add(b4);
        
        b5 = new JButton("PIN CHANGE");
        b5.setBounds(170,485,150,30);
        b5.setBackground(new Color(220, 220, 220));
        b5.addActionListener(this);
        l2.add(b5);
        
        b6 = new JButton("BALANCE ENQUIRY");
        b6.setBounds(355,485,150,30);
        b6.setBackground(new Color(220, 220, 220));
        b6.addActionListener(this);
        l2.add(b6);
        
        b7 = new JButton("EXIT");
        b7.setBounds(355,520,150,30);
        b7.setBackground(new Color(220, 220, 220));
        b7.addActionListener(this);
        l2.add(b7);
        
         
        setSize(900,900);
        setLocation(300,0);
        setUndecorated(true);
        setVisible(true);
      
        
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==b1){ 
            setVisible(false);
            new Deposit(cardNumber,pin).setVisible(true);
        }else if(ae.getSource()==b2){ 
            setVisible(false);
            new Withdrawal(cardNumber,pin).setVisible(true);
        }else if(ae.getSource()==b3){ 
            setVisible(false);
            new FastCash(cardNumber,pin).setVisible(true);
        }else if(ae.getSource()==b4){ 
            new MiniStatement(cardNumber,pin).setVisible(true);
        }else if(ae.getSource()==b5){ 
            setVisible(false);
            new PinChange(cardNumber,pin).setVisible(true);
        }else if(ae.getSource()==b6){ 
            this.setVisible(false);
            new BalanceEnquiry(cardNumber,pin).setVisible(true);
        }else if(ae.getSource()==b7){ 
            System.exit(0);
        }
        
    }
    
    public static void main(String[] args){
        new Transactions("","").setVisible(true);
    }
}
