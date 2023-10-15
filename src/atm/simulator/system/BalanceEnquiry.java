
package atm.simulator.system;

/**
 *
 * @author aloks
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import javax.swing.*;

class BalanceEnquiry extends JFrame implements ActionListener {

    JTextField t1, t2;
    JButton b1, b2, b3;
    JLabel l1, l2, l3;
    String pin;
    String cardNumber;

    BalanceEnquiry(String cardNumber,String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 900, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l3 = new JLabel(i3);
        l3.setBounds(0, 0, 900, 900);
        add(l3);

        l1 = new JLabel();
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setBounds(170, 320, 400, 35);
        l3.add(l1);

        b1 = new JButton("BACK");
        b1.setBounds(355, 520, 150, 30);
        b1.setBackground(new Color(220, 220, 220));
        l3.add(b1);
        
        
        int balance = 0;
        try{
            Conn c1 = new Conn();
//            ResultSet rs = c1.s.executeQuery("select * from bank where pin = '"+pin+"'");
//            ResultSet rs = c1.s.executeQuery("select * from Bank1 where pin = '"+pin+"'");
            ResultSet rs = c1.s.executeQuery("select * from bank where pin = '"+pin+"' AND cardNumber = '"+cardNumber+"'");
            while (rs.next()) {
                if (rs.getString("type").equals("Deposit")) {
                    balance += Integer.parseInt(rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }
        }catch(Exception e){}
        
        l1.setText("Your Current Account Balance is Rs "+balance);

        b1.addActionListener(this);

        setSize(900, 900);
        setUndecorated(true);
        setLocation(300, 0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Transactions(cardNumber,pin).setVisible(true);
    }
    

    public static void main(String[] args) {
        new BalanceEnquiry("","").setVisible(true);
    }
}
