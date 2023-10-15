
package atm.simulator.system;

/**
 *
 * @author aloks
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class MiniStatement extends JFrame implements ActionListener{
 
    JButton b1, b2;
    JLabel l1;
    MiniStatement(String cardNumber,String pin){
        super("Mini Statement");
        getContentPane().setBackground(Color.WHITE);
        setSize(400,600);
        setLocation(20,20);
        
        setLayout(null);
        
        l1 = new JLabel();
        l1.setBounds(20, 140, 400, 200);
        add(l1);
        
        JLabel l2 = new JLabel("Your Apna Bank");
        l2.setBounds(150, 20, 100, 20);
        add(l2);
        
        JLabel l3 = new JLabel();
        l3.setBounds(20, 80, 300, 20);
        add(l3);
        
        JLabel l4 = new JLabel();
        l4.setBounds(20, 400, 300, 20);
        add(l4);
        
        try{
            Conn c = new Conn();
//            ResultSet rs = c.s.executeQuery("select * from login1 where pin = '"+pin+"'");
//            ResultSet rs = c.s.executeQuery("select * from login where pin = '"+pin+"'");
            ResultSet rs = c.s.executeQuery("select * from login where pin = '"+pin+"' AND cardNumber = '"+cardNumber+"'");

            while(rs.next()){
                l3.setText("Card Number:    " + rs.getString("cardNumber").substring(0, 4) + "XXXXXXXX" + rs.getString("cardNumber").substring(12));
            }
        }catch(Exception e){}
        	 
        try{
            int balance = 0;
            Conn c1  = new Conn();
//            ResultSet rs = c1.s.executeQuery("SELECT * FROM Bank1 where pin = '"+pin+"'");
//            ResultSet rs = c1.s.executeQuery("SELECT * FROM bank where pin = '"+pin+"'");
            ResultSet rs = c1.s.executeQuery("select * from bank where pin = '"+pin+"' AND cardNumber = '"+cardNumber+"'");
            while(rs.next()){
                l1.setText(l1.getText() + "<html>"+rs.getString("date")+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString("type") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString("amount") + "<br><br><html>");
                if(rs.getString("type").equals("Deposit")){
                    balance += Integer.parseInt(rs.getString("amount"));
                }else{
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }
            l4.setText("Your total Balance is Rs "+balance);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        b1 = new JButton("Exit");
        b1.setBounds(20, 500, 100, 25);
        b1.addActionListener(this);
        add(b1);
           
    }
    public void actionPerformed(ActionEvent ae){
        this.setVisible(false);
    }
    
    
    public static void main(String[] args){
        new MiniStatement("","").setVisible(true);
    }
    
}