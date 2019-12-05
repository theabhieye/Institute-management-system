import javax.swing.*;
import java.awt.*;
import java.sql.*;
class AddCourses
{ 
    private JDialog addCoursesDialog;
    private JButton saveButton,cancelButton;
    private JTextField courseNameTextField,coursePriceTextField;
    private JLabel courseNameLabel,coursePriceLabel;
    private GridBagConstraints gbc=new GridBagConstraints();
    AddCourses()
    {
        addCoursesDialog=new JDialog(Managenment.frame,"Add course",true);
        addCoursesDialog.setLayout(new GridBagLayout());

        gbc=new GridBagConstraints();


        courseNameLabel=new JLabel("Course name");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets=new Insets(10, 10, 10,10);
        addCoursesDialog.add(courseNameLabel,gbc);
        
        courseNameTextField=new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets=new Insets(10, 10, 10,10);
        addCoursesDialog.add(courseNameTextField,gbc);
        
        coursePriceLabel=new JLabel("Course price");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets=new Insets(10, 10, 10,10);
        addCoursesDialog.add(coursePriceLabel,gbc);
        
        coursePriceTextField=new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets=new Insets(10, 10, 10,10);
        addCoursesDialog.add(coursePriceTextField,gbc);

        cancelButton=new JButton("Cancel");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets=new Insets(10, 10, 10,10);
        addCoursesDialog.add(cancelButton,gbc);
        
        saveButton=new JButton("Save");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets=new Insets(10, 10, 10,10);
        addCoursesDialog.add(saveButton,gbc);

        saveButton.addActionListener(S->
        {
            int maxCourseId=1;
            try
            {
                ResultSet rs=LoginDatabase.stan.executeQuery("select max(course_id) from courseinfo;");
                while(rs.next())
                {
                    if(rs.getInt(1)>1)
                        maxCourseId=rs.getInt(1)+1;
                }
            }
            catch(SQLException e)
            {
                System.out.println("exception in addCourses while getting max(course id)\n "+e.getMessage());
            }

            boolean isCourseNameSame=true;
            try
            {
                ResultSet rs=LoginDatabase.stan.executeQuery("select course_name from courseinfo;");
                while(rs.next())
                {
                    if(rs.getString("course_name").toLowerCase().equals(courseNameTextField.getText().toLowerCase()))
                    {
                        JOptionPane.showMessageDialog(addCoursesDialog,"Course already exist");
                        isCourseNameSame=false;
                    }   
                }
            }
            catch(SQLException e)
            {
                System.out.println("select course_name from courseinfo;\n "+e.getMessage());
            }

            try
            {
                Long.parseLong(coursePriceTextField.getText());
            }
            catch(NumberFormatException e)
            {
                coursePriceTextField.setText("");
                JOptionPane.showMessageDialog(addCoursesDialog,"Contact must be interger");
            }


            try
            {    
                if(isCourseNameSame)
                {
                    LoginDatabase.stan.executeUpdate("insert into courseinfo(course_id,course_name,fees) values ("+maxCourseId+",'"+courseNameTextField.getText()+"',"+Float.parseFloat(coursePriceTextField.getText())+");");
                    JOptionPane.showMessageDialog(addCoursesDialog,"Courses id -"+maxCourseId);
                    
                    courseNameTextField.setText("");
                    coursePriceTextField.setText("");
                }

            }
            catch(SQLException e)
            {
                System.out.println("insert into courseinfo(course_id,course_name,fees) values ("+maxCourseId+",'"+courseNameTextField.getText()+"',"+Float.parseFloat(coursePriceTextField.getText())+");");
                System.out.println(e.getMessage());
            }
            

        });

        cancelButton.addActionListener(l->
        {
            addCoursesDialog.setVisible(false);
        });


        addCoursesDialog.setBounds(320,250,780,760);
        addCoursesDialog.setVisible(true);

    }
}