import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
class LoginWindow
{
    JFrame frame;
    public static String activeUser="";
    public static String privileges="user";
    JTextField usernameTextField;
    JLabel usernameLabel,passwordLabel;
    JPasswordField psPasswordField;
    JButton loginButton,cancelButton;
    JRadioButton adminRadioButton,userRadioButton;
    ButtonGroup buttonGroup;
    boolean isVerified=false;
    Date date;
    Time time;
    static boolean isDatabasecreate=false;
    Calendar rightnow;
    // static{
    //    int a= Database._welcome();
    // }
    LoginWindow()
    {
        frame=new JFrame("Login");
        frame.setBounds(500,500,400,250);
        GridBagLayout gbl;
        gbl=new GridBagLayout();
        frame.setLayout(gbl);
       
        buttonGroup=new ButtonGroup();
        adminRadioButton = new JRadioButton("Admin");
        userRadioButton = new JRadioButton("User",true);

        adminRadioButton.addActionListener(l->privileges="admin");
        userRadioButton.addActionListener(l->privileges="user");

        buttonGroup.add(adminRadioButton);
        buttonGroup.add(userRadioButton);
        
        GridBagConstraints gbc=new GridBagConstraints();

        gbc.insets =new Insets(5, 5, 5, 5);
        gbc.gridx =  0;
        gbc.gridy =  0;
        frame.add(adminRadioButton,gbc);
        
        gbc.gridx =  1;
        gbc.gridy =  0;
        frame.add(userRadioButton,gbc);

        usernameLabel = new JLabel("Username");
        usernameTextField =new JTextField(15); 
        //TODO:: REMOVE THIS
        usernameTextField.setText("Abhishek");



        gbc.insets =new Insets(5, 5, 5, 5);
        gbc.gridx =  0;
        gbc.gridy =  1;
        frame.add(usernameLabel,gbc);
        
        gbc.gridx =  1;
        gbc.gridy =  1;
        frame.add(usernameTextField,gbc);

        passwordLabel = new JLabel("Password");
        psPasswordField  =new JPasswordField(15); 
        //TODO:: REMOVE THIS
        psPasswordField.setText("987654321");

        gbc.insets =new Insets(5, 5, 5, 5);
        gbc.gridx =  0;
        gbc.gridy =  2;
        frame.add(passwordLabel,gbc);
        
        gbc.gridx =  1;
        gbc.gridy =  2;
        frame.add(psPasswordField,gbc);


        loginButton= new JButton("Login");
        loginButton.setToolTipText("Click to login");
        cancelButton  =new JButton("Cancel"); 
        cancelButton.setToolTipText("Clcik to cancel");
        loginButton.addActionListener(l->_checkLogin());
        cancelButton.addActionListener(l->{
            frame.setVisible(false);
            System.exit(0);
        });
        // gbc.anchor=GridBagConstraints.WEST;
        gbc.gridx =  1;
        gbc.gridy =  3;
        frame.add(cancelButton,gbc);
        
        gbc.anchor=GridBagConstraints.NORTHEAST;
        gbc.gridx =  0;
        gbc.gridy =  3;
        frame.add(loginButton,gbc);
        
        frame.setResizable(false);
        frame.setVisible(true);
        new LoginDatabase();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }
    private void _checkLogin()
    {
        String s=usernameTextField.getText();
        s=s.trim();
        usernameTextField.setText(s);
        activeUser=usernameTextField.getText();
        try
        {
            ResultSet rs=LoginDatabase.stan.executeQuery("select * from  loginInfo");
            while(rs.next())
	    	{
                String databaseStr=rs.getString("username")+" "+rs.getString("password")+" "+rs.getString("privileges");
                String loginStr= usernameTextField.getText()+" "+psPasswordField.getText()+" "+privileges;

                if(databaseStr.equals(loginStr))
                {
                    isVerified=true;
                    break;
                }
            }
            if(isVerified==true)
            {
                isVerified=false;
                date = new Date(System.currentTimeMillis());
                time = new Time(System.currentTimeMillis());
                LoginDatabase.stan.executeUpdate("update logininfo set lastLoginDate = '"+date+"' where username ='"+activeUser+"';");
                LoginDatabase.stan.executeUpdate("update logininfo set lastLoginTime = '"+time+"' where username ='"+activeUser+"';");
                new CourseDatabase();
                new BatchDatabase();
                new QueryDatabase();
                new studentDatabase();
                new feeDatabase();
                new Managenment();
                frame.setVisible(false);
            }
            else        
            {
                JOptionPane.showMessageDialog(frame,"Incorrect password / username / privileges","Login Response",JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(SQLException e)
        {
            System.out.println("Execption in login window"+e);
        }
    }
    public static void main(String[] arg)
    {
        LoginWindow l=new LoginWindow();
       
    }
}