
package atm.simulator.system;

/**
 *
 * @author aloks
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class FastCash extends JFrame implements ActionListener {

    JLabel l1, l2;
    JButton b1, b2, b3, b4, b5, b6, b7, b8;
    JTextField t1;
    String pin;
    String cardNumber;

    FastCash(String cardNumber,String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 900, 900);
        add(l3);

        l1 = new JLabel("SELECT WITHDRAWAL AMOUNT");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(210, 300, 700, 35);
        l3.add(l1);


        b1 = new JButton("Rs 500");
        b1.setBounds(170, 415, 150, 30);
        b1.setBackground(new Color(220, 220, 220));
        b1.addActionListener(this);
        l3.add(b1);
        
        b2 = new JButton("Rs 1000");
        b2.setBounds(355, 415, 150, 30);
        b2.setBackground(new Color(220, 220, 220));
        b2.addActionListener(this);
        l3.add(b2);
        
        b3 = new JButton("Rs 2000");
        b3.setBounds(170, 450, 150, 30);
        b3.setBackground(new Color(220, 220, 220));
        b3.addActionListener(this);
        l3.add(b3);
        
        b4 = new JButton("Rs 3000");
        b4.setBounds(355, 450, 150, 30);
        b4.setBackground(new Color(220, 220, 220));
        b4.addActionListener(this);
        l3.add(b4);
        
        b5 = new JButton("Rs 5000");
        b5.setBounds(170, 485, 150, 30);
        b5.setBackground(new Color(220, 220, 220));
        b5.addActionListener(this);
        l3.add(b5);
        
        b6 = new JButton("Rs 10000");
        b6.setBounds(355, 485, 150, 30);
        b6.setBackground(new Color(220, 220, 220));
        b6.addActionListener(this);
        l3.add(b6);
        
        b7 = new JButton("BACK");
        b7.setBounds(355, 520, 150, 30);
        b7.setBackground(new Color(220, 220, 220));
        b7.addActionListener(this);
        l3.add(b7);

        setSize(900, 900);
        setLocation(300, 0);
        setUndecorated(true);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String amount = ((JButton)ae.getSource()).getText().substring(3); //k
            Conn c = new Conn();
//            ResultSet rs = c.s.executeQuery("select * from bank where pin = '"+pin+"'");
//            ResultSet rs = c.s.executeQuery("select * from Bank1 where pin = '"+pin+"'");
             ResultSet rs = c.s.executeQuery("select * from bank where pin = '"+pin+"' AND cardNumber = '"+cardNumber+"'");

            int balance = 0;
            while (rs.next()) {
                String cardNumber = rs.getString("cardNumber");
                if (rs.getString("type").equals("Deposit")) {
                    balance += Integer.parseInt(rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            } String num = "17";
            if (ae.getSource() != b7 && balance < Integer.parseInt(amount)) {
                JOptionPane.showMessageDialog(null, "Insuffient Balance");
                return;
            }

            if (ae.getSource() == b7) {
                this.setVisible(false);
                new Transactions(cardNumber,pin).setVisible(true);
            }else{
                String encryptedPin = encrypt(pin);
                Date date = new Date();
//                c.s.executeUpdate("insert into bank values('"+pin+"', '"+date+"', 'Withdrawal', '"+amount+"')");
//                c.s.executeUpdate("insert into Bank1 values('"+pin+"', '"+date+"', 'Withdrawal', '"+amount+"')");
                  c.s.executeUpdate("insert into bank (cardNumber, pin, date, type, amount) values ('" + cardNumber + "', '" + encryptedPin + "', '" + date + "', 'Withdrawal', '" + amount + "')");
                JOptionPane.showMessageDialog(null, "Rs. "+amount+" Debited Successfully");
                    
                setVisible(false);
                new Transactions(cardNumber,pin).setVisible(true);
            }
        } catch (Exception e) {
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

    public static void main(String[] args) {
        new FastCash("","").setVisible(true);
    }
}
