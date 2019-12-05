import java.sql.*;
class CourseDatabase
{
    public static PreparedStatement courseDatabasenPreStat;    
    static
    {
        try
        {
            ResultSet rs=LoginDatabase.stan.executeQuery("select * from  courseinfo");
        }
        catch(SQLException e)
        {
            try
            {
                LoginDatabase.stan.executeUpdate("Create table CourseInfo(course_id tinyint,course_name varchar(30),fees float);");
                courseDatabasenPreStat=LoginDatabase.conn.prepareStatement("insert into courseinfo(course_id,course_name,fees) values(?,?,?);");

                courseDatabasenPreStat.setInt(1, 1);
                courseDatabasenPreStat.setString(2,"C");
                courseDatabasenPreStat.setFloat(3,(float)4000.0);
                courseDatabasenPreStat.execute();


                //Execption in CourseDatabasejava.sql.SQLException: Before start of result set
                // ResultSet rs=LoginDatabase.stan.executeQuery("select * from courseinfo;");
                // int t=rs.getInt("course_id");
                // System.out.println("Course info-"+t);

                courseDatabasenPreStat=LoginDatabase.conn.prepareStatement("insert into courseinfo(course_id,course_name,fees) values(?,?,?);");
                courseDatabasenPreStat.setInt(1, 2);
                courseDatabasenPreStat.setString(2,"C++");
                courseDatabasenPreStat.setFloat(3,(float)5000.0);
                courseDatabasenPreStat.execute();

                courseDatabasenPreStat=LoginDatabase.conn.prepareStatement("insert into courseinfo(course_id,course_name,fees) values(?,?,?);");
                courseDatabasenPreStat.setInt(1, 3);
                courseDatabasenPreStat.setString(2,"Java");
                courseDatabasenPreStat.setFloat(3,(float)8000.0);
                courseDatabasenPreStat.execute();

                
                courseDatabasenPreStat=LoginDatabase.conn.prepareStatement("insert into courseinfo(course_id,course_name,fees) values(?,?,?);");
                courseDatabasenPreStat.setInt(1, 4);
                courseDatabasenPreStat.setString(2,"Java Adv");
                courseDatabasenPreStat.setFloat(3,(float)10000.0);
                courseDatabasenPreStat.execute();

                
                courseDatabasenPreStat=LoginDatabase.conn.prepareStatement("insert into courseinfo(course_id,course_name,fees) values(?,?,?);");
                courseDatabasenPreStat.setInt(1, 5);
                courseDatabasenPreStat.setString(2,"Python");
                courseDatabasenPreStat.setFloat(3,(float)7000.0);
                courseDatabasenPreStat.execute();

                
                courseDatabasenPreStat=LoginDatabase.conn.prepareStatement("insert into courseinfo(course_id,course_name,fees) values(?,?,?);");
                courseDatabasenPreStat.setInt(1, 6);
                courseDatabasenPreStat.setString(2,"Swift");
                courseDatabasenPreStat.setFloat(3,(float)12000.0);
                courseDatabasenPreStat.execute();

                System.out.println("created in course database");
            }
            catch(SQLException A)
            {
                System.out.println("Execption in CourseDatabase"+A);
            }

        }
    }  
}