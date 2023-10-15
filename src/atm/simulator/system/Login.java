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

public class Login extends JFrame implements ActionListener{
    JLabel l1_text,l2_card,l3_pin;
    JTextField tf1_card;
    JPasswordField pf2_pin;
    JButton b1,b2,b3,b4;
  
    Login(){
        setTitle("AUTOMATED TELLER MACHINE");
        
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        //for scaling the size of image
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        //change image to imageicon
        ImageIcon i3 = new ImageIcon(i2);
        //for showing the image on frame
        JLabel label = new JLabel(i3);
        label.setBounds(70, 10, 100, 100);
        add(label);
        
        l1_text = new JLabel("WELCOME TO ATM");
        l1_text.setFont(new Font("Osward", Font.BOLD, 38));
        l1_text.setBounds(200,40,450,40);
        add(l1_text);
        
        l2_card = new JLabel("Card No:");
        l2_card.setFont(new Font("Raleway", Font.BOLD, 28));
        l2_card.setBounds(125,150,375,30);
        add(l2_card);
        
        tf1_card = new JTextField(15);
        tf1_card.setBounds(300,150,230,30);
        tf1_card.setFont(new Font("Arial", Font.BOLD, 14));
        add(tf1_card);
        
        l3_pin = new JLabel("PIN:");
        l3_pin.setFont(new Font("Raleway", Font.BOLD, 28));
        l3_pin.setBounds(125,220,375,30);
        add(l3_pin);
        
        pf2_pin = new JPasswordField(15);
        pf2_pin.setFont(new Font("Arial", Font.BOLD, 14));
        pf2_pin.setBounds(300,220,230,30);
        add(pf2_pin);
                
        b1 = new JButton("SIGN IN");
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("Arial", Font.BOLD, 14));
        b1.setBounds(300,300,100,30);
        add(b1);
        
        b2 = new JButton("CLEAR");
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        b2.setFont(new Font("Arial", Font.BOLD, 14));
        b2.setBounds(430,300,100,30);
        add(b2);
        
        b3 = new JButton("SIGN UP");
        b3.setBackground(Color.BLACK);
        b3.setForeground(Color.WHITE);
        b3.setFont(new Font("Arial", Font.BOLD, 14));
        b3.setBounds(300,350,230,30);
        add(b3);
        
        b4 = new JButton("FORGOT PIN");
        b4.setBackground(Color.BLACK);
        b4.setForeground(Color.WHITE);
        b4.setFont(new Font("Arial", Font.BOLD, 14));
        b4.setBounds(300, 400, 230, 30);
        add(b4);
        
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        
        getContentPane().setBackground(Color.WHITE);
        
        setSize(800,480);
        setLocation(450,200);
        setVisible(true);
        
    }
    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == b1) {
                Conn c1 = new Conn();
                String cardNumber = tf1_card.getText();
                String pin = new String(pf2_pin.getPassword());

                // Encrypt the entered PIN before comparing it with the stored PIN
                String encryptedPin = encrypt(pin);

                String query = "select * from login where cardNumber = '" + cardNumber + "' and pin = '" + encryptedPin + "'";
                ResultSet rs = c1.s.executeQuery(query);
//                if (rs.next()) {
//                    setVisible(false);
//                    new Transactions(cardNumber, pin).setVisible(true);
//                } else {
//                    JOptionPane.showMessageDialog(null, "Incorrect Card Number or PIN");
//                }
                  if (rs.next()) {
                // Retrieve the encrypted PIN from the database
//                String encryptedStoredPin = rs.getString("pin");
//
//                // Decrypt the stored encrypted PIN to get the original PIN
//                String originalPin = decrypt(encryptedStoredPin);
//
//                if (pin.equals(originalPin)) {
//                    setVisible(false);
//                    new Transactions(cardNumber, originalPin).setVisible(true);
//                } else {
//                    JOptionPane.showMessageDialog(null, "Incorrect PIN");
//                }
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect Card Number or PIN");
            }

            } else if (ae.getSource() == b2) {
                tf1_card.setText("");
                pf2_pin.setText("");
            } else if (ae.getSource() == b3) {
                setVisible(false);
                new SignupOne().setVisible(true);
            } else if (ae.getSource() == b4) {
                // Handle "Forgot PIN" functionality here
                String cardNumber = tf1_card.getText();

                // Show an input dialog to get the previous PIN from the user
                String prevPin = JOptionPane.showInputDialog(this, "Enter Your Previous PIN:", "Forgot PIN", JOptionPane.PLAIN_MESSAGE);
                
                // Encrypt the entered previous PIN before comparing it with the stored PIN
                String encryptedPrevPin = encrypt(prevPin);

                // Query the database to check if the previous PIN matches the stored PIN
                Conn c1 = new Conn();
                String query = "select * from login where cardNumber = '" + cardNumber + "' and pin = '" + encryptedPrevPin + "'";
                ResultSet rs = c1.s.executeQuery(query);
                if (rs.next()) {
                    // Show an input dialog to get the new PIN from the user
                    String newPin = JOptionPane.showInputDialog(this, "Enter Your New PIN:", "Forgot PIN", JOptionPane.PLAIN_MESSAGE);
                    
                    // Encrypt the entered new PIN before updating the database
                    String encryptedNewPin = encrypt(newPin);

                    // Update the PIN in the database
                    c1.s.executeUpdate("update login set pin='" + encryptedNewPin + "' where cardNumber='" + cardNumber + "'");
                    JOptionPane.showMessageDialog(this, "PIN changed successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect Previous PIN");
                }
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
        new Login().setVisible(true);
    }
}
//    public void actionPerformed(ActionEvent ae){
//        try{        
//            if(ae.getSource()==b1){
//                Conn c1 = new Conn();
//                String cardNumber  = tf1_card.getText();
//                String pin  = pf2_pin.getText();
//                String query  = "select * from login where cardNumber = '"+cardNumber+"' and pin = '"+pin+"'";
////                String query  = "select * from login1 where cardNumber = '"+cardNumber+"' and pin = '"+pin+"'";
//
//
//                ResultSet rs = c1.s.executeQuery(query);
//                if(rs.next()){
//                    setVisible(false);
//                    new Transactions(cardNumber,pin).setVisible(true);
//                }else{
//                    JOptionPane.showMessageDialog(null, "Incorrect Card Number or PIN");
//                }
//            }else if(ae.getSource()==b2){
//                tf1_card.setText("");
//                pf2_pin.setText("");
//            }else if(ae.getSource()==b3){
//                setVisible(false);
//                new SignupOne().setVisible(true);
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    public static void main(String[] args){
//        new Login().setVisible(true);
//    }
//}
