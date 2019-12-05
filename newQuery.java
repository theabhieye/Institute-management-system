import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

class newQuery
{
    JDialog queryButtonDialog;
    JLabel dateLabel,studentNameLabel,contactLabel,courseLabel,remarkLabel;
    JTextField studentNameTextField,contactTextField;
    JTextArea remarkTextArea;
    JButton saveButton,cancelButton;
    JComboBox courseComboBox;
    Font font=new Font("Arial",Font.PLAIN,52);
    Calendar rightnow=Calendar.getInstance();
    GridBagConstraints gbc=new GridBagConstraints();
    Date date;
    newQuery()
    {
        //new Query diloag box

        queryButtonDialog=new JDialog(Managenment.frame,"New Query",true);
        queryButtonDialog.setLayout(new GridBagLayout());
        
        gbc=new GridBagConstraints();
        
        gbc.gridx = 10;
        gbc.gridy = 0;
        dateLabel=new JLabel("DATE "+new SimpleDateFormat("dd : MM : yyyy").format(Calendar.getInstance().getTime())); 
        // queryButtonDialog.setFont(font);
        queryButtonDialog.add(dateLabel,gbc);

        studentNameLabel=new JLabel("Student name");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets=new Insets(10, 10, 10,10);
        // queryButtonDialog.setFont(font);
        queryButtonDialog.add(studentNameLabel,gbc);

        studentNameTextField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets=new Insets(10, 10, 10,10);
        queryButtonDialog.add(studentNameTextField,gbc);

        contactLabel = new JLabel("Contact");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.insets=new Insets(10, 10, 10,10);
        queryButtonDialog.add(contactLabel,gbc);

        contactTextField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets=new Insets(10, 10, 10,10);
        queryButtonDialog.add(contactTextField,gbc);

        courseLabel=new JLabel("Course");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.insets=new Insets(10, 10, 10,10);
        queryButtonDialog.add(courseLabel,gbc);

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


        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor=GridBagConstraints.WEST;     
        gbc.fill=GridBagConstraints.BOTH;     
        gbc.insets=new Insets(10, 10, 10,10);
        queryButtonDialog.add(courseComboBox,gbc);

       

        remarkLabel=new JLabel("Remark");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor=GridBagConstraints.NORTHWEST;
        gbc.insets=new Insets(10, 10, 10,10);
        queryButtonDialog.add(remarkLabel,gbc);

        remarkTextArea = new JTextArea(15, 15);
        // JScrollPane sp = new JScrollPane();
        // sp.add(remarkTextArea);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.insets=new Insets(10, 10, 10,10);
        queryButtonDialog.add(remarkTextArea,gbc);


        saveButton = new JButton("Save");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.insets=new Insets(10, 10, 10,10);
        queryButtonDialog.add(saveButton,gbc);
        saveButton.addActionListener(l->_saveButtonClicked());
        
        cancelButton= new JButton("Cancel");
        gbc.fill=GridBagConstraints.NONE;
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.insets=new Insets(10, 10, 10,10);
        queryButtonDialog.add(cancelButton,gbc);
        cancelButton.addActionListener(l->
        {
            queryButtonDialog.setVisible(false);
        });
        queryButtonDialog.add(cancelButton,gbc);
        queryButtonDialog.setBounds(320,250,780,760);
        queryButtonDialog.setVisible(true);
    }
    private void _saveButtonClicked()
    {
        if(!(studentNameTextField.getText()!=null || studentNameTextField.getText().length()!=0)==false)
        {
            JOptionPane.showMessageDialog(queryButtonDialog,"Student Name cannot be Empty");
            System.out.println("Hey student is empty");
            return ;
        }
        
        for(int i=0;i<=9;i++)
        {   
            if(studentNameTextField.getText().contains(String.valueOf(i)))
            {
                JOptionPane.showMessageDialog(queryButtonDialog,"Student name can not contain interger value");
                return;
            }   
        }
        try
        {
            Long.parseLong(contactTextField.getText());
        }
        catch(NumberFormatException e)
        {
            contactTextField.setText("");
            JOptionPane.showMessageDialog(queryButtonDialog,"Contact must be interger");
            return ;
        }
        
        
       
        String final_query="";
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

        //Course_id
        int c_id=0;
        try
        {
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
  
        //query_no
        int maxQue=1;
        try
        {
            ResultSet rs=LoginDatabase.stan.executeQuery("select * from queryinfo order by query_id desc");
            while(rs.next())
            {
                maxQue=rs.getInt("query_id")+1;
                break;
            }
        }
        catch(SQLException e1)
        {
            try 
            {
                LoginDatabase.stan.executeUpdate("insert into queryinfo(query_id) values (1);") ;
            } 
            catch (SQLException e2) 
            {
                System.out.println("execption in new Query while updating quer_id");
            }
        }
        final_query+=maxQue;
    
        try
        {
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


        try
        {
            
            LoginDatabase.stan.executeUpdate("insert into queryinfo(dated,name,contact,comments,addby,course_id,query_id) values( "+final_query+");");
            
            JDialog regDialog=new JDialog(queryButtonDialog,"Query no",true);
            JButton okButton=new JButton("Ok");
            JLabel regLabel=new JLabel("   Query number "+maxQue);
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
        catch(SQLSyntaxErrorException e)
        {
            JOptionPane.showMessageDialog(queryButtonDialog, "Invalid Syntax","Error",JOptionPane.ERROR_MESSAGE);
        }
        catch(SQLException w)
        {
            System.out.println("insert into queryinfo(name,contact,comments,addby,course_id,query_id) values( "+final_query+");");
            System.out.println(w);
            System.out.println(w.getMessage());
        }
        
    }
}