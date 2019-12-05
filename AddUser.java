import javax.swing.*;
import java.sql.*;
import java.awt.*;
class AddUser
{
    private JDialog addUserDialog;
    private JLabel usernameLabel,passwordLabel,conformPasswordLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordField,conformPasswordField;
    private JButton saveButton,cancelButton;
    private GridBagConstraints gbc=new GridBagConstraints();
    AddUser()
    {
        addUserDialog=new JDialog(Managenment.frame,"Add user",true);
        addUserDialog.setLayout(new GridBagLayout());
        gbc=new GridBagConstraints();
        usernameLabel=new JLabel("Username");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets=new Insets(10,10,10,10);
        addUserDialog.add(usernameLabel,gbc);

        passwordLabel=new JLabel("Password");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets=new Insets(10,10,10,10);
        addUserDialog.add(passwordLabel,gbc);

        conformPasswordLabel=new JLabel("Conform Password");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets=new Insets(10,10,10,10);
        addUserDialog.add(conformPasswordLabel,gbc);

        usernameTextField=new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets=new Insets(10,10,10,10);
        addUserDialog.add(usernameTextField,gbc);
        
        passwordField=new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets=new Insets(10,10,10,10);
        addUserDialog.add(passwordField,gbc);

        conformPasswordField=new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets=new Insets(10,10,10,10);
        addUserDialog.add(conformPasswordField,gbc);

        saveButton=new JButton("Save");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets=new Insets(10,10,10,10);
        addUserDialog.add(saveButton,gbc);

        cancelButton=new JButton("Cancel");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets=new Insets(10,10,10,10);
        cancelButton.addActionListener(l->addUserDialog.setVisible(false));
        addUserDialog.add(cancelButton,gbc);

        saveButton.addActionListener(l->
        {
            boolean isUserExist=true;
            try
            {
                ResultSet rs = LoginDatabase.stan.executeQuery("select username from logininfo;");
                while(rs.next())
                {
                    if(usernameTextField.getText().equals(rs.getString(1)))
                    {
                        isUserExist=false;
                        JOptionPane.showMessageDialog(addUserDialog,"User already exist");
                        usernameTextField.setText("");
                        passwordField.setText("");
                        conformPasswordField.setText("");
                    }
                }
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
            
            if(conformPasswordField.getText().equals(passwordField.getText()))
            {
                int maxuserid=1;
                try
                {
                    ResultSet rs = LoginDatabase.stan.executeQuery("select max(userid) from logininfo;");
                    while(rs.next())
                    
                    {
                        maxuserid=rs.getInt(1)+1;
                    }
                }
                catch(SQLException e)
                {
                    System.out.println("select max(userid) from logininfo;");
                    System.out.println(e.getMessage());
                }
                try
                {
                    if(isUserExist)
                    {
                        LoginDatabase.stan.executeUpdate("insert into logininfo(userid,username,password,privileges) values ("+maxuserid+",'"+usernameTextField.getText()+"','"+conformPasswordField.getText()+"','user');");
                        JOptionPane.showMessageDialog(addUserDialog,"User id-"+maxuserid);
                        usernameTextField.setText("");
                        passwordField.setText("");
                        conformPasswordField.setText("");
                    }
                }
                catch(SQLException e)
                {
                    System.out.println("insert into logininfo(userid,username,password,privileges) values ("+maxuserid+",'"+usernameTextField.getText()+"','"+conformPasswordField.getText()+"','user');");
                    System.out.println(e.getMessage());
                }
            }
            else
            {
                if(isUserExist)
                {
                    JOptionPane.showMessageDialog(addUserDialog,"Password and conform password doesn't match");
                }
            }
        });
        addUserDialog.setBounds(320,250,780,760);
        addUserDialog.setVisible(true);
    }
}