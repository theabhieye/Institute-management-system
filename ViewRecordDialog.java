import java.awt.*;
import javax.swing.*;

class ViewRecordDialog
{
    ViewRecordDialog()
    {
        JDialog neuDialog=new JDialog(Managenment.frame,"View Entry",true);
        JButton entryButton,queryButton;
        JPanel newEntryPanel;

        newEntryPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc=new GridBagConstraints();
        entryButton=new JButton("Entry");
        gbc.gridx = 0;
        gbc.gridy = 1;
        newEntryPanel.add(entryButton,gbc);
        entryButton.addActionListener(l->{
            neuDialog.setVisible(false);
            new ViewRecord();
        });

        queryButton=new JButton("Query");
        gbc.insets=new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill=GridBagConstraints.BOTH;
        queryButton.addActionListener(l->{
            neuDialog.setVisible(false);
            new ViewQuery();
        });
        newEntryPanel.add(queryButton,gbc);

        neuDialog.add(newEntryPanel);
        neuDialog.setBounds(400,400,200,200);
        neuDialog.setVisible(true);
    }
}