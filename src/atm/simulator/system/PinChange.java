/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atm.simulator.system;

/**
 *
 * @author aloks
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class PinChange extends JFrame implements ActionListener{
    
    JPasswordField t1,t2;
    JButton b1,b2;                               
    JLabel l1,l2,l3;
    String pin;
    String cardNumber;
    PinChange(String cardNumber,String pin){
        
        this.cardNumber = cardNumber;
        this.pin = pin;
        
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l4 = new JLabel(i3);
        l4.setBounds(0, 0, 900, 900);
        add(l4);
        
        l1 = new JLabel("CHANGE YOUR PIN");
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setForeground(Color.WHITE);
        l1.setBounds(250,280,500,35);
        l4.add(l1);
        
        l2 = new JLabel("New PIN:");
        l2.setFont(new Font("System", Font.BOLD, 16));
        l2.setForeground(Color.WHITE);
        l2.setBounds(165,320,180,25);
        l4.add(l2);
        
        t1 = new JPasswordField();
        t1.setFont(new Font("Raleway", Font.BOLD, 25));
        t1.setBounds(330,320,180,30);
        l4.add(t1);
        
        l3 = new JLabel("Re-Enter New PIN:");
        l3.setFont(new Font("System", Font.BOLD, 16));
        l3.setForeground(Color.WHITE);
        l3.setBounds(165,360,180,25);
        l4.add(l3);
        
        t2 = new JPasswordField();
        t2.setFont(new Font("Raleway", Font.BOLD, 25));
        t2.setBounds(330,360,180,30);
        l4.add(t2);
        
        b1 = new JButton("CHANGE");
        b1.setBounds(355,485,150,30);
        b1.setBackground(new Color(220, 220, 220));
        b1.addActionListener(this);
        l4.add(b1);
        
        b2 = new JButton("BACK");
        b2.setBounds(355,520,150,30);
        b2.setBackground(new Color(220, 220, 220));
        b2.addActionListener(this);
        l4.add(b2);
 
        setSize(900,900);
        setLocation(300,0);
        setUndecorated(true);
        setVisible(true);
    
    }
    
    public void actionPerformed(ActionEvent ae) {
        try {
            String npin = t1.getText();
            String rpin = t2.getText();

            if (!npin.equals(rpin)) {
                JOptionPane.showMessageDialog(null, "Entered PIN does not match");
                return;
            }

            if (ae.getSource() == b1) {
                if (t1.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please Enter New PIN");
                }
                if (t2.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please Re-Enter new PIN");
                }

                // Encrypt the new PIN before updating it in the database
                String encryptedPin = encrypt(npin);

                Conn c1 = new Conn();
                String query1 = "update bank set pin = '" + encryptedPin + "' where pin = '" + pin + "' ";
                String query2 = "update login set pin = '" + encryptedPin + "' where pin = '" + pin + "' ";
                String query3 = "update signupthree set pin = '" + encryptedPin + "' where pin = '" + pin + "' ";

                c1.s.executeUpdate(query1);
                c1.s.executeUpdate(query2);
                c1.s.executeUpdate(query3);

                JOptionPane.showMessageDialog(null, "PIN changed successfully");
                setVisible(false);
                new Transactions(cardNumber, npin).setVisible(true);

            } else if (ae.getSource() == b2) {
                new Transactions(cardNumber, pin).setVisible(true);
                setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to encrypt the PIN using AES
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

    public static void main(String[] args) {
        new PinChange("", "").setVisible(true);
    }
}
    
//    public void actionPerformed(ActionEvent ae){
//        try{        
//            String npin = t1.getText();
//            String rpin = t2.getText();
//            
//            if(!npin.equals(rpin)){
//                JOptionPane.showMessageDialog(null, "Entered PIN does not match");
//                return;
//            }
//            
//            if(ae.getSource()==b1){
//                if (t1.getText().equals("")){
//                    JOptionPane.showMessageDialog(null, "Please Enter New PIN");
//                }
//                if (t2.getText().equals("")){
//                    JOptionPane.showMessageDialog(null, "Please Re-Enter new PIN");
//                }
//                
//                Conn c1 = new Conn();
////                String q1 = "update Bank1 set pin = '"+rpin+"' where pin = '"+pin+"' ";
////                String q2 = "update login1 set pin = '"+rpin+"' where pin = '"+pin+"' ";
////                String q3 = "update signup3 set pin = '"+rpin+"' where pin = '"+pin+"' ";
//                String query1 = "update bank set pin = '"+rpin+"' where pin = '"+pin+"' ";
//                String query2 = "update login set pin = '"+rpin+"' where pin = '"+pin+"' ";
//                String query3 = "update signupthree set pin = '"+rpin+"' where pin = '"+pin+"' ";
//
//                c1.s.executeUpdate(query1);
//                c1.s.executeUpdate(query2);
//                c1.s.executeUpdate(query3);
//
//                JOptionPane.showMessageDialog(null, "PIN changed successfully");
//                setVisible(false);
//                new Transactions(cardNumber,rpin).setVisible(true);
//            
//            }else if(ae.getSource()==b2){
//                new Transactions(cardNumber,pin).setVisible(true);
//                setVisible(false);
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args){
//        new PinChange("","").setVisible(true);
//    }
//}
