
package atm.simulator.system;

/**
 *
 * @author aloks
 */
import javax.swing.*;
import java.awt.*;
import java.util.*;
import com.toedter.calendar.JDateChooser;
import java.awt.event.*;
import java.sql.*;

public class SignupOne extends JFrame implements ActionListener{
    
    JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14,l15,l16;
    JTextField t1,t2,t3,t4,t5,t6,t7,t8;
    JRadioButton r1,r2,r3,r4,r5;
    JButton b;
    JDateChooser dateChooser;
    
    
    Random ran = new Random();
    long first4 = (ran.nextLong() % 9000L) + 1000L;
    String first = "" + Math.abs(first4);
    
    SignupOne(){
        
        setTitle("NEW ACCOUNT APPLICATION FORM");
        
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image i2 = i1.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3);
        label.setBounds(80, 0, 100, 100);
        add(label);
        
        l1 = new JLabel("APPLICATION FORM NO. "+first);
        l1.setFont(new Font("Raleway", Font.BOLD, 32));
        l1.setBounds(230,20,600,40);
        add(l1);
        
        l2 = new JLabel("Page 1: Personal Details(All fields are Mandatory)");
        l2.setFont(new Font("Raleway", Font.BOLD, 22));
        l2.setBounds(230,80,600,30);
        add(l2);
        
        l3 = new JLabel("Name:");
        l3.setFont(new Font("Raleway", Font.BOLD, 20));
        l3.setBounds(100,140,100,30);
        add(l3);
        
        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.PLAIN, 14));
        t1.setBounds(300,140,400,30);
        add(t1);
        
        l4 = new JLabel("Father's Name:");
        l4.setFont(new Font("Raleway", Font.BOLD, 20));
        l4.setBounds(100,190,200,30);
        add(l4);
        
        t2 = new JTextField();
        t2.setFont(new Font("Raleway", Font.PLAIN, 14));
        t2.setBounds(300,190,400,30);
        add(t2);
        
        l5 = new JLabel("Date of Birth:");
        l5.setFont(new Font("Raleway", Font.BOLD, 20));
        l5.setBounds(100,240,200,30);
        add(l5);
        
        dateChooser = new JDateChooser();
	//dateChooser.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
	dateChooser.setForeground(new Color(105, 105, 105));
	dateChooser.setBounds(137, 337, 200, 29);
	add(dateChooser);
        
        l6 = new JLabel("Gender:");
        l6.setFont(new Font("Raleway", Font.BOLD, 20));
        l6.setBounds(100,290,200,30);
        add(l6);
        
        r1 = new JRadioButton("Male");
        r1.setFont(new Font("Raleway", Font.PLAIN, 14));
        r1.setBackground(Color.WHITE);
        r1.setBounds(300,290,60,30);
        add(r1);
        
        r2 = new JRadioButton("Female");
        r2.setFont(new Font("Raleway", Font.PLAIN, 14));
        r2.setBackground(Color.WHITE);
        r2.setBounds(450,290,90,30);
        add(r2);
        
        ButtonGroup groupgender = new ButtonGroup();
        groupgender.add(r1);
        groupgender.add(r2);
        
        l7 = new JLabel("Email Address:");
        l7.setFont(new Font("Raleway", Font.BOLD, 20));
        l7.setBounds(100,340,200,30);
        add(l7);
        
        t3 = new JTextField();
        t3.setFont(new Font("Raleway", Font.PLAIN, 14));
        t3.setBounds(300,340,400,30);
        add(t3);
        
        l8 = new JLabel("Marital Status:");
        l8.setFont(new Font("Raleway", Font.BOLD, 20));
        l8.setBounds(100,390,200,30);
        add(l8);
        
        r3 = new JRadioButton("Married");
        r3.setFont(new Font("Raleway", Font.PLAIN, 14));
        r3.setBackground(Color.WHITE);
        r3.setBounds(300,390,100,30);
        add(r3);
        
        r4 = new JRadioButton("Unmarried");
        r4.setFont(new Font("Raleway", Font.PLAIN, 14));
        r4.setBackground(Color.WHITE);
        r4.setBounds(450,390,100,30);
        add(r4);
        
        r5 = new JRadioButton("Other");
        r5.setFont(new Font("Raleway", Font.PLAIN, 14));
        r5.setBackground(Color.WHITE);
        r5.setBounds(635,390,100,30);
        add(r5);
        
        ButtonGroup groupstatus = new ButtonGroup();
        groupstatus.add(r3);
        groupstatus.add(r4);
        groupstatus.add(r5);
        
        l9 = new JLabel("Address:");
        l9.setFont(new Font("Raleway", Font.BOLD, 20));
        l9.setBounds(100,440,200,30);
        add(l9);
        
        t4 = new JTextField();
        t4.setFont(new Font("Raleway", Font.PLAIN, 14));
        t4.setBounds(300,440,400,30);
        add(t4);
        
        
        l10 = new JLabel("City:");
        l10.setFont(new Font("Raleway", Font.BOLD, 20));
        l10.setBounds(100,490,200,30);
        add(l10);
        
        t5 = new JTextField();
        t5.setFont(new Font("Raleway", Font.PLAIN, 14));
        t5.setBounds(300,490,400,30);
        add(t5);
        
        l11 = new JLabel("Pin Code:");
        l11.setFont(new Font("Raleway", Font.BOLD, 20));
        l11.setBounds(100,540,200,30);
        add(l11);
        
        t6 = new JTextField();
        t6.setFont(new Font("Raleway", Font.PLAIN, 14));
        t6.setBounds(300,540,400,30);
        add(t6);
        
        l12 = new JLabel("State:");
        l12.setFont(new Font("Raleway", Font.BOLD, 20));
        l12.setBounds(100,590,200,30);
        add(l12);
        
        t7 = new JTextField();
        t7.setFont(new Font("Raleway", Font.PLAIN, 14));
        t7.setBounds(300,590,400,30);
        add(t7);
        
        l13 = new JLabel("Country:");
        l13.setFont(new Font("Raleway", Font.BOLD, 20));
        l13.setBounds(100,640,200,30);
        add(l13);
        
        t8 = new JTextField();
        t8.setFont(new Font("Raleway", Font.PLAIN, 14));
        t8.setBounds(300,640,400,30);
        add(t8);
        
        l14 = new JLabel("Date");
        l14.setFont(new Font("Raleway", Font.BOLD, 14));
        
        l15 = new JLabel("Month");
        l15.setFont(new Font("Raleway", Font.BOLD, 14));
        
        l16 = new JLabel("Year");
        l16.setFont(new Font("Raleway", Font.BOLD, 14));
        
        b = new JButton("Next");
        b.setFont(new Font("Raleway", Font.BOLD, 14));
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);
        b.setBounds(670,710,80,30);
        b.addActionListener(this); 
        add(b);
       
        dateChooser.setBounds(300, 240, 400, 30);
               
        getContentPane().setBackground(Color.WHITE);
        
        setSize(850,800);
        setLocation(400,25);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        
        String formno = "" + first;
        String name = t1.getText();
        String fname = t2.getText();
        String dob = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
        String gender = null;
        if(r1.isSelected()){ 
            gender = "Male";
        }else if(r2.isSelected()){ 
            gender = "Female";
        }
            
        String email = t3.getText();
        String marital = null;
        if(r3.isSelected()){ 
            marital = "Married";
        }else if(r4.isSelected()){ 
            marital = "Unmarried";
        }else if(r5.isSelected()){ 
            marital = "Other";
        }
           
        String address = t4.getText();
        String city = t5.getText();
        String pincode = t6.getText();
        String state = t7.getText();
        String country = t8.getText();
        

//        try{
//           
//            if(t6.getText().equals("")){
//                JOptionPane.showMessageDialog(null, "Fill all the required fields");
//            }else{
//                Conn c1 = new Conn();
//                String q1 = "insert into signup values('"+formno+"','"+name+"','"+fname+"','"+dob+"','"+gender+"','"+email+"','"+marital+"','"+address+"','"+city+"','"+pincode+"','"+state+"','"+country+"')";
//                c1.s.executeUpdate(q1);
//                
////                new SignupTwo(first).setVisible(true);
//                setVisible(false);
//            }
//            
//        }catch(Exception e){
//             e.printStackTrace();
//        }
//        if (name.isEmpty() || fname.isEmpty() || dob.isEmpty() || gender == null || email.isEmpty()
//            || marital == null || address.isEmpty() || city.isEmpty()
//            || pincode.isEmpty() || state.isEmpty() || country.isEmpty()) {
//
//            JOptionPane.showMessageDialog(null, "Fill all the required fields");
//        } 
           // Validation for SignupOne form
           if(name.isEmpty() && fname.isEmpty() && dob.isEmpty() && gender == null && email.isEmpty()
               && marital == null && address.isEmpty() && city.isEmpty()
               && pincode.isEmpty() && state.isEmpty() && country.isEmpty()){
                   JOptionPane.showMessageDialog(null, "Please Fill all the required fields");
           }else if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter your Name");
            } else if (fname.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter your Father's Name");
            } else if (dob.isEmpty()) {
                 JOptionPane.showMessageDialog(null, "Please select your Date of Birth");
            } else if (gender == null) {
                JOptionPane.showMessageDialog(null, "Please select your Gender");
            } else if (email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter your Email Address");
            } else if (marital == null) {
                JOptionPane.showMessageDialog(null, "Please select your Marital Status");
            } else if (address.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter your Address");
            } else if (city.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter your City");
            } else if (pincode.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter your Pin Code");
            } else if (state.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please select your State");
            } else if (country.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please select your Country");
            }else{ 
                try {
                 // Proceed with inserting the data into the database
                    Conn c1 = new Conn();
                    String query = "insert into signup values('" + formno + "','" + name + "','" + fname + "','" + dob + "','" + gender + "','" + email + "','" + marital + "','" + address + "','" + city + "','" + pincode + "','" + state + "','" + country + "')";
                    c1.s.executeUpdate(query);

                    // If everything is successful, show the next page or perform any other action
                    new SignupTwo(first).setVisible(true);
                    setVisible(false);

                } catch (Exception e) {
                      e.printStackTrace();
                }
            }    
        }
    
        public static void main(String[] args){
            new SignupOne().setVisible(true);
        }
}
