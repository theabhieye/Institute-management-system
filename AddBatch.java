import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Calendar;
class AddBatch implements ActionListener
{
     
    private JDialog addBatchDialog;
    private JButton saveButton,cancelButton;
    private JLabel courseLabel,batchTimeLabel,batchDateLabel;
    private JComboBox courseComboBox,timeComboBox,am_pmComboBox,yearComboBox,monthComboBox,dateComboBox;
    private GridBagConstraints gbc=new GridBagConstraints();
    private Calendar cal;
    private String timeString;
    AddBatch()
    {
        addBatchDialog=new JDialog(Managenment.frame,"Add Batch",true);
        addBatchDialog.setLayout(new GridBagLayout());
        gbc=new GridBagConstraints();

        courseLabel=new JLabel("Course");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets=new Insets(10,10,10,10);
        addBatchDialog.add(courseLabel,gbc);

        courseComboBox=new JComboBox<>();
      
        try
        {
            ResultSet rs=LoginDatabase.stan.executeQuery("select course_name from courseinfo;");
            while(rs.next())
            {
                courseComboBox.addItem(rs.getString(1));
            }
        }
        catch(SQLException e)
        {
            System.out.println("Execption in courses combo box in add batch");
        }
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets=new Insets(10,10,10,10);
        addBatchDialog.add(courseComboBox,gbc);        


        batchTimeLabel=new JLabel("Batch Time");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets=new Insets(5,5,5,5);
        addBatchDialog.add(batchTimeLabel,gbc);

        timeComboBox=new JComboBox<>();
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets=new Insets(5,5,5,5);
        int hour;
        cal=Calendar.getInstance();
        hour=1;
        for(int i=hour;i<=12;i++)
        {
            if(i<=9)
            {
                timeComboBox.addItem("0"+i+":00");
                timeComboBox.addItem("0"+i+":30");
            }
            else
            {
                timeComboBox.addItem(i+":00");
                timeComboBox.addItem(i+":30");
            }
        }
        addBatchDialog.add(timeComboBox,gbc);
        
        am_pmComboBox=new JComboBox<>();
        am_pmComboBox.addItem("am");
        am_pmComboBox.addItem("pm");
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.insets=new Insets(10,10,10,10);
        addBatchDialog.add(am_pmComboBox,gbc);


        batchDateLabel=new JLabel("Batch Date");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets=new Insets(10,10,10,10);
        addBatchDialog.add(batchDateLabel,gbc);

        yearComboBox=new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets=new Insets(5,5,5,5);
        
        cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        for(int i=year;i<=year+10;i++)
        {
            yearComboBox.addItem(i);
        }
       
        addBatchDialog.add(yearComboBox,gbc);

        monthComboBox=new JComboBox<>();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.insets=new Insets(5,5,5,5);
        
        int month=1;
        for(int i=month;i<=12;i++)
            monthComboBox.addItem(i);

        addBatchDialog.add(monthComboBox,gbc);

        dateComboBox=new JComboBox<>();
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.insets=new Insets(5,5,5,5);
        
        int date=1;
        int maxdate=31;
        if(month==2 && year%4==0)
            maxdate=29;
        else if(month==2 && year%4!=0)
            maxdate=28;
        else if(month==4 || month==6 || month==9 || month==11)
            maxdate=30;

        for(int i=date;i<=maxdate;i++)
            dateComboBox.addItem(i);

        addBatchDialog.add(dateComboBox,gbc);
        cancelButton=new JButton("Cancel");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets=new Insets(10,10,10,10);
        addBatchDialog.add(cancelButton,gbc);

        saveButton=new JButton("Save");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets=new Insets(10,10,10,10);
        saveButton.addActionListener(l->
        {
            int courseid=1,batchno=1;
            try
            {
                ResultSet rs=LoginDatabase.stan.executeQuery("select course_id from courseinfo where  course_name='"+courseComboBox.getSelectedItem()+"';");
                while(rs.next())
                {
                    courseid=rs.getInt(1);
                }
            }
            catch(SQLException e)
            {
                System.out.println(("select course_id from courseinfo where course_name='"+courseComboBox.getSelectedItem()+"';"));
                System.out.println(e.getMessage());
            }
           
            try
            {
                ResultSet rs=LoginDatabase.stan.executeQuery("select max(batch_no) from batchinfo where course_id="+courseid+";");
                while(rs.next())
                {
                    batchno=rs.getInt(1)+1;
                }
            }
            catch(SQLException e)
            {
                System.out.println("select max(batch_no) from batchinfo where course_id="+courseid+";");
                System.out.println(e.getMessage());
            }

            try
            {
                LoginDatabase.stan.executeUpdate("insert into batchinfo(course_id,batch_no,batch_date,batch_time) values ("+courseid+","+batchno+","+"'"+yearComboBox.getSelectedItem()+":"+monthComboBox.getSelectedItem()+":"+dateComboBox.getSelectedItem()+"','"+timeComboBox.getSelectedItem()+":00');");
                JOptionPane.showMessageDialog(addBatchDialog,"Batch no-"+batchno);
            }
            catch(SQLException e)
            {
                System.out.println("insert into batchinfo(course_id,batch_no,batch_date,batch_time) values ("+courseid+","+batchno+","+"'"+yearComboBox.getSelectedItem()+":"+monthComboBox.getSelectedItem()+":"+dateComboBox.getSelectedItem()+"','"+timeComboBox.getSelectedItem()+":00');");
                System.out.println(e.getMessage());
            }
        });

        addBatchDialog.add(saveButton,gbc);

        addBatchDialog.setBounds(320,250,780,760);
        addBatchDialog.setVisible(true);
        yearComboBox.addActionListener(this);
        monthComboBox.addActionListener(this);
        dateComboBox.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("HI");
        // if((int)yearComboBox.getSelectedItem()!=cal.get(Calendar.YEAR))    
        //     {
        //         monthComboBox.removeAllItems();
        //         int month1=1;
        //         for(int i=month1;i<=12;i++)
        //             monthComboBox.addItem(i);
                    
        //         int maxdate1=31;
        //         int year1=(int)yearComboBox.getSelectedItem();
                
        //         dateComboBox.removeAllItems();
        //         if(month1==2 && year1%4==0)
        //             maxdate1=29;
        //         else if(month1==2 && year1%4!=0)
        //             maxdate1=28;
        //         else if(month1==4 || month1==6 || month1==9 || month1==11)
        //             maxdate1=30;
                
        //         for(int i=1;i<=maxdate1;i++)
        //         {
        //             dateComboBox.addItem(i);
        //         }
        //     }
        //     else
        //     {
        //         monthComboBox.removeAllItems();
        //         int month1=cal.get(Calendar.MONTH);
        //         for(int i=month1;i<=12;i++)
        //             monthComboBox.addItem(i);
                    
        //         int maxdate1=31;
        //         int year1=(int)yearComboBox.getSelectedItem();
                
        //         dateComboBox.removeAllItems();
        //         if(month1==2 && year1%4==0)
        //             maxdate1=29;
        //         else if(month1==2 && year1%4!=0)
        //             maxdate1=28;
        //         else if(month1==4 || month1==6 || month1==9 || month1==11)
        //             maxdate1=30;
                
        //         for(int i=cal.get(Calendar.DATE);i<=maxdate1;i++)
        //         {
        //             dateComboBox.addItem(i);
        //         }
        //     }
            



        // monthComboBox.addActionListener(m->{
        //     if((int)monthComboBox.getSelectedItem()!=cal.get(Calendar.MONTH))
        //     {
        //         dateComboBox.removeAllItems();
        //         int date1=1;
        //         int maxdate1=31;
        //         int month2=(int)monthComboBox.getSelectedItem();
        //         int year2=(int)yearComboBox.getSelectedItem();
        //         if(month2==2 && year2%4==0)
        //             maxdate1=29;
        //         else if(month2==2 && year2%4!=0)
        //             maxdate1=28;
        //         else if(month2==4 || month2==6 || month2==9 || month2==11)
        //             maxdate1=30;
        
        //         for(int i=date1;i<=maxdate1;i++)
        //             dateComboBox.addItem(i);
                
        //     }
        // });
    }
}