
package atm.simulator.system;

/**
 *
 * @author aloks
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ForgotPin extends JFrame implements ActionListener {

    private JLabel l1, l2, l3, l4;
    private JTextField t1, t2, t3, t4;
    private JButton b1, b2;
    private String cardNumber;
    private String pin;

    ForgotPin(String cardNumber) {
        this.cardNumber = cardNumber;

        setLayout(null);

        l1 = new JLabel("Enter Your Card Number:");
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(60, 30, 250, 25);
        add(l1);

        t1 = new JTextField();
        t1.setBounds(320, 30, 150, 25);
        add(t1);

        l2 = new JLabel("Enter Your PIN:");
        l2.setFont(new Font("System", Font.BOLD, 16));
        l2.setBounds(60, 80, 250, 25);
        add(l2);

        t2 = new JTextField();
        t2.setBounds(320, 80, 150, 25);
        add(t2);

        l3 = new JLabel("Enter New PIN:");
        l3.setFont(new Font("System", Font.BOLD, 16));
        l3.setBounds(60, 130, 250, 25);
        add(l3);

        t3 = new JTextField();
        t3.setBounds(320, 130, 150, 25);
        add(t3);

        l4 = new JLabel("Confirm New PIN:");
        l4.setFont(new Font("System", Font.BOLD, 16));
        l4.setBounds(60, 180, 250, 25);
        add(l4);

        t4 = new JTextField();
        t4.setBounds(320, 180, 150, 25);
        add(t4);

        b1 = new JButton("Change");
        b1.setFont(new Font("System", Font.BOLD, 16));
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.setBounds(120, 250, 120, 30);
        b1.addActionListener(this);
        add(b1);

        b2 = new JButton("Back");
        b2.setFont(new Font("System", Font.BOLD, 16));
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        b2.setBounds(260, 250, 120, 30);
        b2.addActionListener(this);
        add(b2);

        getContentPane().setBackground(Color.WHITE);

        setSize(600, 350);
        setLocation(400, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == b1) {
                String cardNum = t1.getText();
                String pin = t2.getText();
                String newPin = t3.getText();
                String confirmPin = t4.getText();

                if (!cardNum.equals(cardNumber) || pin.isEmpty() || newPin.isEmpty() || confirmPin.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Invalid Input! Please enter valid details.");
                } else if (!pin.equals(decrypt(cardNumber))) {
                    JOptionPane.showMessageDialog(null, "Incorrect PIN! Please enter your correct old PIN.");
                } else if (!newPin.equals(confirmPin)) {
                    JOptionPane.showMessageDialog(null, "New PINs do not match! Please enter the same PIN in both fields.");
                } else {
                    // Encrypt the new PIN and update the database
                    String encryptedPin = encrypt(newPin);
                    Conn c1 = new Conn();
                    c1.s.executeUpdate("update login set pin='" + encryptedPin + "' where cardNumber='" + cardNumber + "'");
                    JOptionPane.showMessageDialog(null, "PIN changed successfully!");
                    setVisible(false);
                    new Transactions(cardNumber, encrypt(newPin)).setVisible(true);
                }
            } else if (ae.getSource() == b2) {
                setVisible(false);
                new Transactions(cardNumber, pin).setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred. Please try again.");
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
        new ForgotPin("").setVisible(true);
    }
}

