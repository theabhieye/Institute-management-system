import java.sql.*;
import javax.swing.*;
import java.awt.*;

class LoginDatabase
{
    public static Connection conn;
    public static Statement stan;
    public static PreparedStatement loginWindowPreStat;
    static
    {
        try
        {   
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/institute_management_system","root","admin");
            stan = conn.createStatement();
           // System.out.println("Checking from prebuild database");
        }
        catch(SQLException e)
        {
           // _welcome();
            try
            {
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/","root","admin");
                stan = conn.createStatement();
                
                stan.executeUpdate("create database institute_management_system;");
                
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/institute_management_system","root","admin");
                stan = conn.createStatement();
                
                stan.executeUpdate("create table loginInfo(userid smallint,username varchar(25),password varchar(25),privileges varchar(5),lastLoginDate date,lastLoginTime time);");
                stan.executeUpdate("insert into loginInfo(userid,username,password,privileges) values (1,'Abhishek','987654321','admin');");
                stan.executeUpdate("insert into loginInfo(userid,username,password,privileges) values (2,'deepanshu','123456789','user');");
                stan.executeUpdate("insert into loginInfo(userid,username,password,privileges) values (3,'nishant','123456789','user');");
            }
            catch(SQLException e1)
            {
                System.out.println("Execption in 2nd try catch block of logindatabase"+e1);
            }
        }
    }
    public static void _welcome()
    {
        JFrame frame;
        JLabel welcomeLabel;
        JLabel ipAddressLabel,portLabel,sqlusernameLabel,sqlPasswordLabel;
        JTextField ipAddressTextField,portTextField,sqlusernameTextField,sqlPasswordTextField;
        JButton okButton;

        okButton=new JButton("OK");
        
        frame=new JFrame("Institute Management System");
        frame.setBounds(500,500,400,250);

        GridBagLayout gbl;
        gbl=new GridBagLayout();
        frame.setLayout(gbl);

        GridBagConstraints gbc=new GridBagConstraints();
        welcomeLabel=new JLabel("Institute Management System");
        gbc.insets =new Insets(5, 5, 5, 5);
        gbc.gridx =  0;
        gbc.gridy =  0;
        frame.add(welcomeLabel,gbc);
        
        
        ipAddressLabel = new JLabel("IP Address");
        ipAddressTextField =new JTextField("127.0.0.1",15); 
    
        gbc.insets =new Insets(5, 5, 5, 5);
        gbc.gridx =  0;
        gbc.gridy =  1;
        frame.add(ipAddressLabel,gbc);
        
        gbc.gridx =  1;
        gbc.gridy =  1;
        frame.add(ipAddressTextField,gbc);
        
        portLabel = new JLabel("Port");
        portTextField =new JTextField("3306",15); 
        gbc.insets =new Insets(5, 5, 5, 5);
        gbc.gridx =  0;
        gbc.gridy =  2;
        frame.add(portLabel,gbc);
        
        gbc.gridx =  1;
        gbc.gridy =  2;
        frame.add(portTextField,gbc);
        
        
        sqlPasswordLabel = new JLabel("SQL Password");
        sqlPasswordTextField =new JTextField("admin",15); 
        
        gbc.insets =new Insets(5, 5, 5, 5);
        gbc.gridx =  0;
        gbc.gridy =  3;
        frame.add(sqlPasswordLabel,gbc);
        
        gbc.gridx =  1;
        gbc.gridy =  3;
        frame.add(sqlPasswordTextField,gbc);
        
        
        sqlusernameLabel = new JLabel("SQL username");
        sqlusernameTextField =new JTextField("root",15); 
        
        
        gbc.insets =new Insets(5, 5, 5, 5);
        gbc.gridx =  0;
        gbc.gridy =  4;
        frame.add(sqlusernameLabel,gbc);
        
        gbc.gridx =  1;
        gbc.gridy =  4;
        frame.add(sqlusernameTextField,gbc);
        
        
        gbc.insets =new Insets(5, 5, 5, 5);
        gbc.gridx =  0;
        gbc.gridy =  5;
        frame.add(okButton,gbc);
        
        frame.setResizable(false);
        frame.setVisible(true);  
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        okButton.addActionListener(l->{
            
            try
            {
                conn = DriverManager.getConnection("jdbc:mysql://"+ipAddressTextField.getText()+":"+portTextField.getText()+"/",sqlusernameTextField.getText(),sqlPasswordTextField.getText());
                stan = conn.createStatement();
                
                stan.executeUpdate("create database institute_management_system;");
                
                conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/institute_management_system",sqlusernameTextField.getText(),sqlPasswordTextField.getText());
                stan = conn.createStatement();
                
                stan.executeUpdate("create table loginInfo(username varchar(25),password varchar(25),privileges varchar(5),lastLoginDate date,lastLoginTime time);");
                stan.executeUpdate("insert into loginInfo(username,password,privileges) values ('Abhishek','987654321','admin');");
                stan.executeUpdate("insert into loginInfo(username,password,privileges) values ('deepanshu','123456789','user');");
                stan.executeUpdate("insert into loginInfo(username,password,privileges) values ('nishant','123456789','user');");

                frame.setVisible(false);
            }
            catch(SQLException e1)
            {
                System.out.println("Execption in 2nd try catch block"+e1);
            }
        });
    }
    
}