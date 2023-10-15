
package atm.simulator.system;

/**
 *
 * @author aloks
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Deposit extends JFrame implements ActionListener{
    
    JTextField t1,t2;
    JButton b1,b2,b3;
    JLabel l1,l2,l3;
    String pin;
    String cardNumber;

    Deposit( String cardNumber,String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 900, 900);
        add(l3);
        
        l1 = new JLabel("ENTER AMOUNT YOU WANT TO DEPOSIT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(170,300,400,20);
        l3.add(l1);
        
        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 22));
        t1.setBounds(170,350,320,25);
        l3.add(t1);
        
        b1 = new JButton("DEPOSIT");
        b1.setBounds(355,480,130,30);
        b1.setBackground(new Color(220, 220, 220));
        b1.addActionListener(this);
        l3.add(b1);
        
        b2 = new JButton("BACK");
        b2.setBounds(355,515,130,30);
        b2.setBackground(new Color(220, 220, 220));
        b2.addActionListener(this);
        l3.add(b2);
    
        setSize(900,900);
        setUndecorated(true);
        setLocation(400,0);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        try{        
            String amount = t1.getText();
            Date date = new Date();
            if(ae.getSource()==b1){
                if(t1.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Please enter the Amount to you want to Deposit");
                }else{
                    String encryptedPin = encrypt(pin);
                    Conn c1 = new Conn();
                    c1.s.executeUpdate("insert into bank (cardNumber, pin, date, type, amount) values ('" + cardNumber + "', '" + encryptedPin + "', '" + date + "', 'Deposit', '" + amount + "')");//                      c1.s.executeUpdate("insert into Bank1 values('"+pin+"', '"+date+"', 'Deposit', '"+amount+"')");

                    JOptionPane.showMessageDialog(null, "Rs. "+amount+" Deposited Successfully");
                    setVisible(false);
                    new Transactions(cardNumber,pin).setVisible(true);
                }
            }else if(ae.getSource()==b2){
                setVisible(false);
                new Transactions(cardNumber,pin).setVisible(true);
            }
        }catch(Exception e){
            e.printStackTrace();
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
        new Deposit("","").setVisible(true);
    }
}
