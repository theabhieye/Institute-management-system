import java.sql.*;
class studentDatabase
{
    static{
        try
        {
            LoginDatabase.stan.execute("Select * from studentinfo;");
        }
        catch(SQLException e)
        {
            try 
            {
                LoginDatabase.stan.executeUpdate("create table studentinfo(addby varchar(25),dated date, reg_no smallint,name varchar(25),contact varchar(15),course_id tinyint,batch_no tinyint,comments varchar(100));");
            } 
            catch (SQLException e1) 
            {
                System.out.println("Execptioin in Student database : "+e1);    
            }
            
        }
    }
}