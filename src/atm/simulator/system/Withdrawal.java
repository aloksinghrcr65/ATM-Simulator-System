
package atm.simulator.system;

/**
 *
 * @author aloks
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Date;
import java.sql.*;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Withdrawal extends JFrame implements ActionListener{
    
    JTextField t1,t2;
    JButton b1,b2,b3;
    JLabel l1,l2,l3,l4;
    String pin;
    String cardNumber;
    
    Withdrawal( String cardNumber,String pin){
        this.cardNumber = cardNumber;
        this.pin = pin;
        
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 900, 900);
        add(l3);
        
        l1 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(170,300,400,20);
        l3.add(l1);
        
        l2 = new JLabel("(Maximum Withdrawal is RS.1000)");
        l2.setForeground(Color.WHITE);
        l2.setFont(new Font("System", Font.BOLD, 16));
        l2.setBounds(170,320,400,20);
        l3.add(l2);
        
        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 25));
        t1.setBounds(170,360,330,30);
        l3.add(t1);
        
        b1 = new JButton("WITHDRAW");
        b1.setBounds(355,480,130,30);
        b1.setBackground(new Color(220, 220, 220));
        b1.addActionListener(this);
        l3.add(b1);
        
        b2 = new JButton("BACK");
        b2.setBounds(355,515,130,30);
        b2.setBackground(new Color(220, 220, 220));
        b2.addActionListener(this);
        l3.add(b2);
        
        setSize(960,1080);
        setLocation(300,0);
        setUndecorated(true);
        setVisible(true);
    }
    
    
    public void actionPerformed(ActionEvent ae){
        try{        
            String amount = t1.getText();
            Date date = new Date();
            if(ae.getSource()==b1){
                if(t1.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Please enter the Amount to you want to Withdraw");
                }else{
                    String encryptedPin = encrypt(pin);
                    Conn c1 = new Conn();
                    
//                    ResultSet rs = c1.s.executeQuery("select * from bank where pin = '"+pin+"'");
//                    ResultSet rs = c1.s.executeQuery("select * from Bank1 where pin = '"+pin+"'");
                      ResultSet rs = c1.s.executeQuery("select * from bank where pin = '"+pin+"' AND cardNumber = '"+cardNumber+"'");

                    int balance = 0;
                    while(rs.next()){
                       if(rs.getString("type").equals("Deposit")){
                           balance += Integer.parseInt(rs.getString("amount"));
                       }else{
                           balance -= Integer.parseInt(rs.getString("amount"));
                       }
                    }
                    if(balance < Integer.parseInt(amount)){
                        JOptionPane.showMessageDialog(null, "Insuffient Balance");
                        return;
                    }
                    
                    c1.s.executeUpdate("insert into bank (cardNumber, pin, date, type, amount) values ('" + cardNumber + "', '" + encryptedPin + "', '" + date + "', 'Withdrawal', '" + amount + "')");                    c1.s.executeUpdate("insert into Bank1 values('"+pin+"', '"+date+"', 'Withdrawal', '"+amount+"')");
                    JOptionPane.showMessageDialog(null, "Rs. "+amount+" Debited Successfully");
                    
                    setVisible(false);
                    new Transactions(cardNumber,pin).setVisible(true);
                }
            }else if(ae.getSource()==b2){
                setVisible(false);
                new Transactions(cardNumber,pin).setVisible(true);
            }
        }catch(Exception e){
                e.printStackTrace();
                System.out.println("error: "+e);
        }
            
    }
    
    private String encrypt(String pin) throws Exception {
        // Change this key to a strong and secure key in your real application
        String key = "thisIsASecretKey";
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(pin.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Method to decrypt the PIN using AES
    private String decrypt(String encryptedPin) throws Exception {
        // Change this key to the same strong and secure key used for encryption in your real application
        String key = "thisIsASecretKey";
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPin));
        return new String(decryptedBytes);
    }

    
    
    public static void main(String[] args){
        new Withdrawal("","").setVisible(true);
    }
}