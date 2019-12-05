import javax.swing.*;
import java.awt.*; 
import java.sql.*;
import java.awt.event.*;
import java.util.Calendar;
class FeeDeposit implements ActionListener
{
    private JDialog feeDepositDialog;
    private JLabel receiptnoLabel,recepitno,courseLabel,batchLabel,studentnameLabel,feeAmountLabel,dueLabel,feeLabel,dateLabel,contactLabel,depositedLabel,amountToDepositLabel,discountLabel,depositedamtLabel,due;
    private JComboBox studentnameComboBox,courseComboBox,batchComboBox,discountComboBox;                                                                                                         
    private JButton dateButton,saveButton,cancelButton;
    private JTextField amountToDepositTextField,contactTextField;
    private Calendar cal;
    FeeDeposit()
    {
        feeDepositDialog=new JDialog(Managenment.frame,"Fee Deposit",true);
        feeDepositDialog.setLayout(new GridBagLayout());

        GridBagConstraints gbc=new GridBagConstraints(); 
        
        dateLabel=new JLabel("Date");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(dateLabel,gbc);

        cal=Calendar.getInstance();
        dateButton=new JButton(cal.get(Calendar.DATE)+":"+cal.get(Calendar.MONTH)+":"+cal.get(Calendar.YEAR));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(dateButton,gbc);
        

        receiptnoLabel=new JLabel("Recepit No.");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(receiptnoLabel,gbc);

        recepitno=new JLabel("1");
        try
        {
            ResultSet rs=LoginDatabase.stan.executeQuery("Select max(recepit_no) from feeinfo;");
            while(rs.next())
            {
                recepitno.setText(rs.getString(1));
            }
        }
        catch (SQLException e) 
        {
            System.out.println("Select max(recepit_no) from feeinfo;\n"+e.getMessage());
        }
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(recepitno,gbc);
        
        courseLabel=new JLabel("Course");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(courseLabel,gbc);
        
        courseComboBox=new JComboBox<>();
        try
        {
            ResultSet rs=LoginDatabase.stan.executeQuery("select course_name from courseinfo;");
            while(rs.next())
            {
                courseComboBox.addItem(rs.getString("course_name"));;
            }
        }
        catch (SQLException e) 
        {
            System.out.println("select course_name from courseinfo;\n"+e.getMessage());
        }
        courseComboBox.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(courseComboBox,gbc);
        
        batchLabel=new JLabel("Batch");
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(batchLabel,gbc);

        batchComboBox=new JComboBox<>();
        try
        {
            ResultSet rs=LoginDatabase.stan.executeQuery("select batch_no from batchinfo where course_id=(select course_id from courseinfo where  course_name='"+courseComboBox.getSelectedItem()+"');");
            while(rs.next())
            {
                batchComboBox.addItem(rs.getString(1));;
            }
        }
        catch (SQLException e) 
        {
            System.out.println("select batch_no from batchinfo where course_id=(select course_id from courseinfo where  course_id='"+courseComboBox.getSelectedItem()+"');");
            System.out.println(e.getMessage());
        }

        batchComboBox.addActionListener(this);
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(batchComboBox,gbc);

        studentnameLabel=new JLabel("Student name");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(studentnameLabel,gbc);

        studentnameComboBox=new JComboBox<>();
        try
        {
            ResultSet rs=LoginDatabase.stan.executeQuery("select name from studentinfo where (course_id=(select course_id from courseinfo where course_name='"+courseComboBox.getSelectedItem()+"')"+"and batch_no="+batchComboBox.getSelectedItem()+");");
            while(rs.next())
            {
                studentnameComboBox.addItem(rs.getString("name"));;
            }
        }
        catch (SQLException e) 
        {
            System.out.println("select name from studentinfo where (course_id=(select course_id from courseinfo where course_name='"+courseComboBox.getSelectedItem()+"')"+"and batch_no="+batchComboBox.getSelectedItem()+");");
            System.out.println(e.getMessage());
        }
        studentnameComboBox.addActionListener(l->{
        try
        {
            Statement st;
            st=LoginDatabase.conn.createStatement();
            ResultSet rs=st.executeQuery("Select amount_paid from feeinfo where reg_no="+"(Select reg_no from studentinfo where (name='"+studentnameComboBox.getSelectedItem()+"'and course_id="+"(select course_id from courseinfo where  course_name='"+courseComboBox.getSelectedItem()+"') and batch_no="+batchComboBox.getSelectedItem()+"));");
            try
            {
                int amtpaid=0;
                while(rs.next())
                {
                    amtpaid+=rs.getInt(1);
                }
                depositedamtLabel.setText(String.valueOf(amtpaid));
            }
            catch (SQLException e6) 
            {
                System.out.println("Select amount_paid from feeinfo where reg_no="+"(Select reg_no from studentinfo where (name='"+studentnameComboBox.getSelectedItem()+"'and course_id="+"(select course_id from courseinfo where  course_name='"+courseComboBox.getSelectedItem()+"') and batch_no="+batchComboBox.getSelectedItem()+"));");
                System.out.println(e6.getMessage());
            }

            contactTextField.setText("");
            rs=st.executeQuery("select contact from studentinfo where name='"+studentnameComboBox.getSelectedItem()+"';");
            // System.out.println("select contact from studentinfo where name='"+studentnameComboBox.getSelectedItem()+"';");
            while(rs.next())
            {
                // System.out.println(rs.getString(1)+"\n");
                contactTextField.setText(rs.getString(1));
            }
        }
        catch (SQLException e4) 
        {
            System.out.println("select contact from studentinfo where name='"+studentnameComboBox.getSelectedItem()+"';");
            System.out.println(e4.getMessage());
        }
        });
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(studentnameComboBox,gbc);
        
        contactLabel=new JLabel("Contact");
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(contactLabel,gbc);
        
        contactTextField=new JTextField(15);
        try
        {
            ResultSet rs=LoginDatabase.stan.executeQuery("select contact from studentinfo where name='"+studentnameComboBox.getSelectedItem()+"';");
            while(rs.next())
            {
                contactTextField.setText(rs.getString(1));
            }
        }
        catch (SQLException e) 
        {
            System.out.println("select contact from studentinfo where name='"+studentnameComboBox.getSelectedItem()+"';");
            System.out.println(e.getMessage());
        }
        contactTextField.setEditable(false);
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(contactTextField,gbc);
        
        feeAmountLabel=new JLabel("Fee Amount");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(feeAmountLabel,gbc);
        
        feeLabel=new JLabel("4564");
        try
        {
            ResultSet rs=LoginDatabase.stan.executeQuery("select fees from courseinfo where course_id=(select course_id from courseinfo where course_name='"+courseComboBox.getSelectedItem()+"')");
            while(rs.next())
            {
                feeLabel.setText(rs.getString(1));
            }
        }
        catch (SQLException e) 
        {
            System.out.println("select fees from courseinfo where course_id=(select course_id from courseinfo where course_name='"+courseComboBox.getSelectedItem()+"')");
            System.out.println(e.getMessage());
        }

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(feeLabel,gbc);
        
        depositedLabel=new JLabel("Deposited");
        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(depositedLabel,gbc);

        depositedamtLabel=new JLabel("0.0");
        
        try
        {
            int amtpaid=0;
            ResultSet rs=LoginDatabase.stan.executeQuery("Select amount_paid from feeinfo where reg_no="+"(Select reg_no from studentinfo where (name='"+studentnameComboBox.getSelectedItem()+"'and course_id="+"(select course_id from courseinfo where  course_name='"+courseComboBox.getSelectedItem()+"') and batch_no="+batchComboBox.getSelectedItem()+"));");
            while(rs.next())
            {
                amtpaid+=rs.getInt(1);
            }
            depositedamtLabel.setText(String.valueOf(amtpaid));
        }
        catch (SQLException e6) 
        {
            System.out.println("Select amount_paid from feeinfo where reg_no="+"(Select reg_no from studentinfo where (name='"+studentnameComboBox.getSelectedItem()+"'and course_id="+"(select course_id from courseinfo where  course_name='"+courseComboBox.getSelectedItem()+"') and batch_no="+batchComboBox.getSelectedItem()+"));");
            System.out.println(e6.getMessage());
        }

        gbc.gridx = 4;
        gbc.gridy = 5;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(depositedamtLabel,gbc);

        discountLabel=new JLabel("Discount");
        gbc.gridx = 5;
        gbc.gridy = 5;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(discountLabel,gbc);

        discountComboBox=new JComboBox<>();
        discountComboBox.setEditable(false);
        gbc.gridx = 6;
        gbc.gridy = 5;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(discountComboBox,gbc);
        
        dueLabel=new JLabel("Due");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(dueLabel,gbc);
        
        due=new JLabel();
        due.setText(String.valueOf(Float.parseFloat(feeLabel.getText())-Float.parseFloat(depositedamtLabel.getText())));
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(due,gbc);
        
        amountToDepositLabel=new JLabel("Amount to deposit");
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(amountToDepositLabel,gbc);
        
        amountToDepositTextField=new JTextField(5);
        amountToDepositTextField.setText("0.0");
        gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(amountToDepositTextField,gbc);
        
        saveButton=new JButton("Save");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets=new Insets(10,10,10,10);

        saveButton.addActionListener(l->
        {
            due.setText(String.valueOf(Float.parseFloat(feeLabel.getText())-Float.parseFloat(depositedamtLabel.getText())));
            int reg_no=1,recp_no=1;
            try
            {
                ResultSet rs=LoginDatabase.stan.executeQuery("Select reg_no from studentinfo where (name='"+studentnameComboBox.getSelectedItem()+"'and course_id="+"(select course_id from courseinfo where  course_name='"+courseComboBox.getSelectedItem()+"') and batch_no="+batchComboBox.getSelectedItem()+");");
                while(rs.next())
                {
                    reg_no=rs.getInt(1);
                }
                rs=LoginDatabase.stan.executeQuery("select max(recepit_no) from feeinfo;");
                while(rs.next())
                {
                    recp_no=rs.getInt(1)+1;
                }
                Integer.parseInt(amountToDepositTextField.getText());
            }
            catch(SQLException e)
            {
                System.out.println("Select reg_no from studentinfo where (name='"+studentnameComboBox.getSelectedItem()+"' and course_id="+"(select course_id from courseinfo where  course_name='"+courseComboBox.getSelectedItem()+"') and batch_no="+batchComboBox.getSelectedItem()+");");
                System.out.println(("select max(recepit_no) from feeinfo;"));
                System.out.println(e.getMessage());
            }
            catch(NumberFormatException e)
            {
                JOptionPane.showMessageDialog(feeDepositDialog,"Invalid amount");
                return;
            }

            try
            {
                LoginDatabase.stan.executeUpdate("insert into feeinfo values ("+recp_no+","+reg_no+","+Float.parseFloat(amountToDepositTextField.getText())+",'"+new Date(System.currentTimeMillis())+"');");
                JOptionPane.showMessageDialog(feeDepositDialog,"Recepit No- "+recp_no);
                amountToDepositTextField.setText("0000");
                try
                {
                    int amtpaid=0;
                    ResultSet rs=LoginDatabase.stan.executeQuery("Select amount_paid from feeinfo where reg_no="+"(Select reg_no from studentinfo where (name='"+studentnameComboBox.getSelectedItem()+"'and course_id="+"(select course_id from courseinfo where  course_name='"+courseComboBox.getSelectedItem()+"') and batch_no="+batchComboBox.getSelectedItem()+"));");
                    while(rs.next())
                    {
                        amtpaid+=rs.getInt(1);
                    }
                    depositedamtLabel.setText(String.valueOf(amtpaid));
                }
                catch (SQLException e6) 
                {
                    System.out.println("Select amount_paid from feeinfo where reg_no="+"(Select reg_no from studentinfo where (name='"+studentnameComboBox.getSelectedItem()+"'and course_id="+"(select course_id from courseinfo where  course_name='"+courseComboBox.getSelectedItem()+"') and batch_no="+batchComboBox.getSelectedItem()+"));");
                    System.out.println(e6.getMessage());
                }
            }
            catch(SQLException e)
            {
                System.out.println("insert into feeinfo values ("+recp_no+","+reg_no+","+Float.parseFloat(amountToDepositTextField.getText())+",'"+new Date(System.currentTimeMillis())+"');");
                System.out.println(e.getMessage());
            }
            try
            {
                ResultSet rs=LoginDatabase.stan.executeQuery("select max(recepit_no) from feeinfo;");
                while(rs.next())
                {
                    recp_no=rs.getInt(1)+1;
                    recepitno.setText(String.valueOf(recp_no));
                }
                Integer.parseInt(amountToDepositTextField.getText());
            }
            catch(SQLException e)
            {
                System.out.println("Select reg_no from studentinfo where (name='"+studentnameComboBox.getSelectedItem()+"' and course_id="+"(select course_id from courseinfo where  course_name='"+courseComboBox.getSelectedItem()+"') and batch_no="+batchComboBox.getSelectedItem()+");");
                System.out.println(("select max(recepit_no) from feeinfo;"));
                System.out.println(e.getMessage());
            }
            
            due.setText(String.valueOf(Float.parseFloat(feeLabel.getText())-Float.parseFloat(depositedamtLabel.getText())));
        });




        feeDepositDialog.add(saveButton,gbc);
        
        cancelButton=new JButton("Cancel");
        gbc.gridx = 2;
        gbc.gridy = 7;
        gbc.insets=new Insets(10,10,10,10);
        feeDepositDialog.add(cancelButton,gbc);

        feeDepositDialog.setBounds(320,250,780,760);
        feeDepositDialog.setVisible(true);

    }
    public void actionPerformed(ActionEvent e)
    {
        depositedamtLabel.setText("0.0");
        due.setText(String.valueOf(Float.parseFloat(feeLabel.getText())-Float.parseFloat(depositedamtLabel.getText())));
        //courses
        try
        {
            Statement s1=LoginDatabase.conn.createStatement();
            ResultSet rs=LoginDatabase.stan.executeQuery("select course_name from courseinfo;");
            while(rs.next())
            {
                courseComboBox.addItem(rs.getString("course_name"));;
            }
        }
        catch (SQLException e1) 
        {
            System.out.println("select course_name from courseinfo;\n"+e1.getMessage());
        }
        //batchComboBox
        if(e.getSource()!=batchComboBox) 
        {   
            try
            {
                
                Statement s2=LoginDatabase.conn.createStatement();
                ResultSet rs=s2.executeQuery("select batch_no from batchinfo where course_id=(select course_id from courseinfo where  course_name='"+courseComboBox.getSelectedItem()+"');");
                batchComboBox.removeAllItems();
                while(rs.next())
                {
                    batchComboBox.addItem(rs.getString(1));;
                }
            }
            catch (SQLException e2) 
            {
                System.out.println("select batch_no from batchinfo where course_id=(select course_id from courseinfo where  course_id='"+courseComboBox.getSelectedItem()+"');");
                System.out.println(e2.getMessage());
            }
        } 
       //student
        try
        {
            // Statement s3=LoginDatabase.conn.createStatement();
            studentnameComboBox.removeAllItems();
            ResultSet rs=LoginDatabase.stan.executeQuery("select name from studentinfo where (course_id=(select course_id from courseinfo where course_name='"+courseComboBox.getSelectedItem()+"')"+"and batch_no="+batchComboBox.getSelectedItem()+");");
            // System.out.println("select name from studentinfo where (course_id=(select course_id from courseinfo where course_name='"+courseComboBox.getSelectedItem()+"')"+"and batch_no="+batchComboBox.getSelectedItem()+");");
            while(rs.next())
            {
                studentnameComboBox.addItem(rs.getString("name"));;
            }
        }
        catch (SQLException e3) 
        {
            System.out.println("select name from studentinfo where (course_id=(select course_id from courseinfo where course_name='"+courseComboBox.getSelectedItem()+"')"+"and batch_no="+batchComboBox.getSelectedItem()+");");
            System.out.println(e3.getMessage());
        }      
        //fee
        try
        {
            ResultSet rs=LoginDatabase.stan.executeQuery("select fees from courseinfo where course_id=(select course_id from courseinfo where course_name='"+courseComboBox.getSelectedItem()+"')");
            while(rs.next())
            {
                feeLabel.setText(rs.getString(1));
            }
        }
        catch (SQLException e5) 
        {
            System.out.println("select fees from courseinfo where course_id=(select course_id from courseinfo where course_name='"+courseComboBox.getSelectedItem()+"')");
            System.out.println(e5.getMessage());
        }
        //deposited amt
        // try
        // {
        //     ResultSet rs=LoginDatabase.stan.executeQuery("Select amount_paid from feeinfo");
        //     while(rs.next())
        //     {
        //         // System.out.println(String.valueOf(Float.parseFloat(feeLabel.getText())-rs.getFloat(1)));
        //         // depositedamtLabel.setText(String.valueOf(Float.parseFloat(feeLabel.getText())-rs.getFloat(1)));;
        //         // due.setText(rs.getString(1));
        //     }
        // }
        // catch (SQLException e6) 
        // {
        //     System.out.println("Select amount_paid from feeinfo");
        //     System.out.println(e6.getMessage());
        // }
        
    
    }
}