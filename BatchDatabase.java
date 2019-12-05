import java.sql.*;
class BatchDatabase
{
    
    static
    {
        try 
        {
            ResultSet rs = LoginDatabase.stan.executeQuery("select * from batchinfo;");        
        } 
        catch (SQLException e) 
        {
            try 
            {
                // CourseDatabase.courseDatabasenPreStat = LoginDatabase.conn.prepareStatement("insert into BatchInfo(course_id,batch_no,batch_date,batch_time) values(?,?,?,?);");
                
                // ResultSet rs=LoginDatabase.stan.executeQuery("select course_id from courseinfo where course_name='c';");
                // int course_id = Integer.parseInt(rs.getString("course_id"));
                // System.out.println("hey2"+course_id);

                LoginDatabase.stan.executeUpdate("create table BatchInfo(course_id tinyint,batch_no tinyint,batch_date date,batch_time time);");
                LoginDatabase.stan.executeUpdate("insert into BatchInfo(course_id,batch_no) values(1,1);"); 
                LoginDatabase.stan.executeUpdate("insert into BatchInfo(course_id,batch_no) values(2,1);"); 
                LoginDatabase.stan.executeUpdate("insert into BatchInfo(course_id,batch_no) values(3,1);"); 
                LoginDatabase.stan.executeUpdate("insert into BatchInfo(course_id,batch_no) values(4,1);"); 
                LoginDatabase.stan.executeUpdate("insert into BatchInfo(course_id,batch_no) values(5,1);"); 
                LoginDatabase.stan.executeUpdate("insert into BatchInfo(course_id,batch_no) values(6,1);"); 
                LoginDatabase.stan.executeUpdate("insert into BatchInfo(course_id,batch_no) values(6,2);"); 
                // CourseDatabase.courseDatabasenPreStat.setInt(1, course_id);
                // CourseDatabase.courseDatabasenPreStat.setInt(2,1);
                // CourseDatabase.courseDatabasenPreStat.setString(3,"22:11:2019");
                // CourseDatabase.courseDatabasenPreStat.setString(3,"7:00");
                // CourseDatabase.courseDatabasenPreStat.execute();
            }
            catch(SQLException e1)  
            {
                System.out.println("E1  Execption in batch database"+e1);
            }
        }
    }
}
