import javax.swing.*;
import java.awt.*;
class Admin
{
    
    private JDialog adminDialog=new JDialog(Managenment.frame,"Admin",true);
    private JButton addCourseButton,addBatchButton,feeRecordButton,addUserButton,closeButton;
    private JPanel adminEntryDialog;
    Admin()
    {
        adminEntryDialog = new JPanel(new GridBagLayout());

        GridBagConstraints gbc=new GridBagConstraints();
        addCourseButton=new JButton("Add Course");
        gbc.insets=new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        addCourseButton.addActionListener(c->{
            adminDialog.setVisible(false);
            new AddCourses();
        });
        gbc.fill=GridBagConstraints.BOTH;

        adminEntryDialog.add(addCourseButton,gbc);

        addBatchButton=new JButton("Add Batch");
        gbc.insets=new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill=GridBagConstraints.BOTH;
        addBatchButton.addActionListener(l->
        {
            adminDialog.setVisible(false);
            new AddBatch();
        });
        adminEntryDialog.add(addBatchButton,gbc);

        feeRecordButton=new JButton("View fee");
        gbc.insets=new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill=GridBagConstraints.BOTH;
        adminEntryDialog.add(feeRecordButton,gbc);

        addUserButton=new JButton("Add User");
        gbc.insets=new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 4;
        addUserButton.addActionListener(l->new AddUser());
        gbc.fill=GridBagConstraints.BOTH;
        adminEntryDialog.add(addUserButton,gbc);

        closeButton=new JButton("Close");
        gbc.insets=new Insets(10,10,10,10);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill=GridBagConstraints.BOTH;
        closeButton.addActionListener(l->adminDialog.setVisible(false));
        adminEntryDialog.add(closeButton,gbc);

        adminDialog.add(adminEntryDialog);
        adminDialog.setBounds(400,400,200,300);
        adminDialog.setVisible(true);
    }
}