import java.awt.*;
import javax.swing.*;
class Managenment
{
    public static JFrame frame;
    JButton adminButton,neuEntry,viewRecordButton,feeDepositButton,editButton,logoutButton,exitButton;
    JPanel mainPanel,leftCenterPanel,leftPanel,activeUserPanel;
    JButton activeUserButton;
    Font font;
    
    Managenment()
    {
        frame=new JFrame("Managenment");
        font=new Font("Arial",Font.PLAIN,18);
        mainPanel = new JPanel();
        frame.add(mainPanel);
        frame.setLayout(new BorderLayout());

        leftPanel=new JPanel();
        leftPanel.setLayout(new BorderLayout());
        
        leftCenterPanel = new JPanel();
        leftCenterPanel.setLayout(new GridBagLayout());
        leftCenterPanel.setBackground(Color.RED);
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.fill=GridBagConstraints.BOTH;
        gbc.insets=new Insets(5,5,5,5);
        adminButton=new JButton("Administor");
        adminButton.setSize(20,20);
        leftCenterPanel.add(adminButton,gbc);
        adminButton.setVisible(false);
        if(LoginWindow.privileges.equals("admin"))
        {
            adminButton.setVisible(true);
        };
        adminButton.addActionListener(l->new Admin());

        neuEntry=new JButton("New Entry");
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.fill=GridBagConstraints.BOTH;
        gbc.insets=new Insets(5,5,5,5);
        neuEntry.addActionListener(n->new NewEntry());
        leftCenterPanel.add(neuEntry,gbc);

        viewRecordButton=new JButton("view Record");
        gbc.gridx=0;
        gbc.gridy=3;
        gbc.insets=new Insets(5,5,5,5);
        gbc.fill=GridBagConstraints.BOTH;
        viewRecordButton.addActionListener(v->new ViewRecordDialog());
        leftCenterPanel.add(viewRecordButton,gbc);

        feeDepositButton=new JButton("Fee Deposit");
        gbc.gridx=0;
        gbc.gridy=4;
        gbc.insets=new Insets(5,5,5,5);
        gbc.fill=GridBagConstraints.BOTH;
        feeDepositButton.addActionListener(l->new FeeDeposit());
        leftCenterPanel.add(feeDepositButton,gbc);

        editButton=new JButton("Edit Record");
        gbc.gridx=0;
        gbc.gridy=5;
        gbc.insets=new Insets(5,5,5,5);
        gbc.fill=GridBagConstraints.BOTH;
        leftCenterPanel.add(editButton,gbc);

        logoutButton=new JButton("Logout");
        gbc.gridx=0;
        gbc.gridy=6;
        gbc.insets=new Insets(5,5,5,5);
        gbc.fill=GridBagConstraints.BOTH;
        logoutButton.addActionListener(l->
        {
            Managenment.frame.setVisible(false);
            new LoginWindow();
        });
        leftCenterPanel.add(logoutButton,gbc);


        exitButton=new JButton("Exit");
        gbc.gridx=0;
        gbc.gridy=7;
        gbc.insets=new Insets(5,5,5,5);
        gbc.fill=GridBagConstraints.BOTH;
        exitButton.addActionListener(e->{
            System.exit(0);
        });
        leftCenterPanel.add(exitButton,gbc);

        activeUserPanel=new JPanel();
        activeUserPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        activeUserButton=new JButton(LoginWindow.activeUser);
        activeUserButton.setEnabled(false);

        activeUserPanel.add(activeUserButton);

        leftPanel.add(leftCenterPanel,"Center");
        frame.add(activeUserPanel,"South");
        frame.add(leftPanel,BorderLayout.WEST);

        frame.setBounds(200,200,1000,800);
        frame.setVisible(true);
    }
}