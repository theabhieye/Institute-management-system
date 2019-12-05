import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
class NewEntry
{
    
    //new Entry diloag box
    JDialog entryButtonDialog;
    JLabel dateLabel,studentNameLabel,contactLabel,courseLabel,batchLabel,batchdateLabel,batchTimeLabel,batchTime,batchDate,remarkLabel;
    JTextField studentNameTextField,contactTextField;
    JTextArea remarkTextArea;
    JButton saveButton,cancelButton;
    JComboBox courseComboBox,batchComboBox;
    Font font=new Font("Arial",Font.BOLD,20);
    Calendar rightnow=Calendar.getInstance();
    GridBagConstraints gbc=new GridBagConstraints();
    Date date;
    int result;
    NewEntry()
    {
        //new Entry diloag box

        entryButtonDialog=new JDialog(Managenment.frame,"New Entry",true);
        entryButtonDialog.setLayout(new GridBagLayout());
        
        gbc=new GridBagConstraints();
        
        gbc.gridx = 10;
        gbc.gridy = 0;
        dateLabel=new JLabel("DATE "+new SimpleDateFormat("dd : MM : yyyy").format(Calendar.getInstance().getTime())); 
        // entryButtonDialog.setFont(font);
        entryButtonDialog.setFont(font);
        entryButtonDialog.add(dateLabel,gbc);

        studentNameLabel=new JLabel("Student name");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets=new Insets(10, 10, 10,10);
        // entryButtonDialog.setFont(font);
        entryButtonDialog.add(studentNameLabel,gbc);

        studentNameTextField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets=new Insets(10, 10, 10,10);
        entryButtonDialog.add(studentNameTextField,gbc);

        contactLabel = new JLabel("Contact");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.insets=new Insets(10, 10, 10,10);
        entryButtonDialog.add(contactLabel,gbc);

        contactTextField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets=new Insets(10, 10, 10,10);
        entryButtonDialog.add(contactTextField,gbc);

        courseLabel=new JLabel("Course");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.insets=new Insets(10, 10, 10,10);
        entryButtonDialog.add(courseLabel,gbc);

        courseComboBox=new JComboBox();
        try
        {
            ResultSet rs=LoginDatabase.stan.executeQuery("select course_name from CourseInfo");
            while(rs.next())
            {
                courseComboBox.addItem(rs.getString("course_name"));
            }
        }
        catch(SQLException E)
        {
            System.out.println("Execption in mangenment class"+E);
        }  
        
        courseComboBox.addActionListener(l->
        {
            try 
            {
                batchComboBox.removeAllItems();
                // System.out.println("select batch_no from batchinfo where course_id=(select course_id from CourseInfo where course_name ='"+courseComboBox.getSelectedItem()+"');");
                ResultSet rs=LoginDatabase.stan.executeQuery("select batch_no from batchinfo where course_id=(select course_id from CourseInfo where course_name ='"+courseComboBox.getSelectedItem()+"');");
                while(rs.next())
                {
                    batchComboBox.addItem(rs.getString("batch_no"));
                    // batchComboBox.addItem(courseComboBox.getSelectedItem()+"-"+rs.getString("batch_no")); 
                }
            } 
            catch(SQLException E)
            {
                 System.out.println("Execption in mangenment class"+E);
            }  
        });


        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill=GridBagConstraints.BOTH;   
        gbc.insets=new Insets(10, 10, 10,10);
        entryButtonDialog.add(courseComboBox,gbc);

        batchLabel=new JLabel("Batch");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill=GridBagConstraints.BOTH;
        gbc.insets=new Insets(10, 10, 10,10);
        entryButtonDialog.add(batchLabel,gbc);


        batchComboBox=new JComboBox();
        gbc.gridx = 1;
        gbc.gridy = 4;
        
        gbc.insets=new Insets(10, 10, 10,10);
        try
        {
            ResultSet rs=LoginDatabase.stan.executeQuery("select * from  batchinfo where course_id=1;");
            while(rs.next())
            {
                // course_id tinyint,batch_no tinyint,batch_date date,batch_time time
                batchComboBox.addItem(rs.getInt("batch_no"));
            }
        }
        catch(SQLException E)
        {
            System.out.println("Execptoin in mangenment class"+E);
        }   

        entryButtonDialog.add(batchComboBox,gbc);
        batchdateLabel =new JLabel("Date");
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.anchor=GridBagConstraints.SOUTHWEST;
        gbc.insets=new Insets(5, 5, 5,5);
        entryButtonDialog.add(batchdateLabel,gbc);

        batchTimeLabel =new JLabel("Time");
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.anchor=GridBagConstraints.SOUTHEAST;
        gbc.insets=new Insets(5, 5, 5,5);
        entryButtonDialog.add(batchTimeLabel,gbc);

        batchTime =new JLabel("7:00 PM");
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.insets=new Insets(5, 5, 5,5);
        entryButtonDialog.add(batchTime,gbc);

        batchDate =new JLabel("22 : 12 : 9999");
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.insets=new Insets(5, 5, 5,5);
        entryButtonDialog.add(batchDate,gbc);

        remarkLabel=new JLabel("Remark");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor=GridBagConstraints.NORTHWEST;
        gbc.insets=new Insets(10, 10, 10,10);
        entryButtonDialog.add(remarkLabel,gbc);

        remarkTextArea = new JTextArea(15, 15);
        // remarkTextArea.setText("Enter your text here");
        // JPanel p1=new JPanel();
        JScrollPane sp = new JScrollPane();
        sp.add(remarkTextArea);
        // p1.setSize(15,15);
        // p1.setBackground(Color.RED);
        gbc.gridx = 1;
        gbc.gridy = 5;
        // p1.add(sp);
        gbc.anchor=GridBagConstraints.WEST;
        gbc.insets=new Insets(10, 10, 10,10);
        entryButtonDialog.add(remarkTextArea,gbc);


        saveButton = new JButton("Save");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.insets=new Insets(10, 10, 10,10);
        entryButtonDialog.add(saveButton,gbc);
        saveButton.addActionListener(l->_saveButtonClicked());

        cancelButton= new JButton("Cancel");

        gbc.fill=GridBagConstraints.NONE;
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.insets=new Insets(10, 10, 10,10);
        entryButtonDialog.add(cancelButton,gbc);
        cancelButton.addActionListener(l->
        {
            entryButtonDialog.setVisible(false);
        });
        entryButtonDialog.setBounds(320,250,780,760);
        this._neuEntry();
    }
    private void _neuEntry()
    {
        JDialog neuDialog=new JDialog(Managenment.frame,"New Entry",true);
        JButton entryButton,queryButton;
        JPanel newEntryPanel;
        newEntryPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc=new GridBagConstraints();
        entryButton=new JButton("Entry");
        gbc.gridx = 0;
        gbc.gridy = 1;
        // gbc.fill=GridBagConstraints.BOTH;

        newEntryPanel.add(entryButton,gbc);
        entryButton.addActionListener(l->{
            neuDialog.setVisible(false);
            entryButtonDialog.setVisible(true);
        });

        queryButton=new JButton("Query");
        gbc.insets=new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill=GridBagConstraints.BOTH;
        queryButton.addActionListener(l->{
            neuDialog.setVisible(false);
            new newQuery();
        });
        newEntryPanel.add(queryButton,gbc);

        neuDialog.add(newEntryPanel);
        neuDialog.setBounds(400,400,200,200);
        neuDialog.setVisible(true);
    }
    private void _saveButtonClicked()
    {

        String final_query="";
        studentNameTextField.selectAll();
        int n=studentNameTextField.getCaretPosition();
        studentNameTextField.setCaretPosition(studentNameTextField.getText().length());
        if(n==0)
        {
            JOptionPane.showMessageDialog(entryButtonDialog,"Name cannot be Empty");

            // JOptionPane.showMessageDialog(entryButtonDialog,JOptionPane.ERROR_MESSAGE);
            return ;
        }
        
        try
        {
            Long.parseLong(contactTextField.getText());
        }
        catch(NumberFormatException e)
        {
            contactTextField.setText("");
            JOptionPane.showMessageDialog(entryButtonDialog,"Contact must be interger");
            return;
        }
        
        
    
        
        //date
        
        final_query+="'"+new Date(System.currentTimeMillis())+"',";
        //name
        final_query+="'"+studentNameTextField.getText()+"',";
        //contact

        final_query+="'"+contactTextField.getText()+"',";
        // comments 
        final_query+="'"+remarkTextArea.getText()+"',";
        // dated date
        // final_query+="'"+new SimpleDateFormat(" yyyy : MM :dd").format(Calendar.getInstance().getTime())+"',";
        // addby
        final_query+="'"+LoginWindow.activeUser+"',";
       //reg_no
        int maxReg=1;
        try
        {
            // ResultSet rs=LoginDatabase.stan.executeQuery("select max(reg_no) from studentinfo order by reg_no desc");
            ResultSet rs=LoginDatabase.stan.executeQuery("select * from studentinfo order by reg_no desc");
            while(rs.next())
            {
                maxReg=rs.getInt("reg_no")+1;
                break;
            }
        }
        catch(SQLException e1)
        {
            try 
            {
                LoginDatabase.stan.executeUpdate("insert into studentinfo(reg_no) values (1);") ;
            } 
            catch (SQLException e2) 
            {
                System.out.println("execption in new Entry while updating reg_no");
            }
        }
        final_query+=maxReg+",";

        //Course_id
        int c_id=0;
        try
        {
            // System.out.println("select * from courseinfo where course_name='"+courseComboBox.getSelectedItem()+"';");
            ResultSet rs=LoginDatabase.stan.executeQuery("select * from courseinfo where course_name='"+courseComboBox.getSelectedItem()+"';");
            while(rs.next())
            {
                c_id=rs.getInt("course_id");
            }
        }
        catch(SQLException e3)
        {
            System.out.println(e3.getMessage());
        }
  
        final_query+=c_id+",";
  
        final_query+=batchComboBox.getSelectedItem();

            try
            {
                // System.out.println("insert into studentinfo(dated,name,contact,comments,addby,reg_no,course_id,batch_no) values( "+final_query+");");
                LoginDatabase.stan.executeUpdate("insert into studentinfo(dated,name,contact,comments,addby,reg_no,course_id,batch_no) values( "+final_query+");");
                JDialog regDialog=new JDialog(entryButtonDialog,"Reg no",true);
                JButton okButton=new JButton("Ok");
                JLabel regLabel=new JLabel("   Registion number "+maxReg);
                regDialog.setLayout(new GridBagLayout());
                
                gbc.gridx=0;
                gbc.gridy=1;
                regDialog.add(regLabel,gbc);
                
                gbc.gridx=0;
                gbc.gridy=2;
                gbc.anchor=GridBagConstraints.EAST;
                regDialog.add(okButton,gbc);
                okButton.addActionListener(l->
                {
                    studentNameTextField.setText("");
                    contactTextField.setText("");
                    remarkTextArea.setText("");
                    regDialog.setVisible(false);
                });
                regDialog.setBounds(500,500,250,180);
                regDialog.setVisible(true);
            }
            catch(SQLException w)
            {
                System.out.println(w.getMessage());
                System.out.println(w);
                JOptionPane.showMessageDialog(entryButtonDialog,"Invalid Entries");
            }  
    }
}