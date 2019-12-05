import java.sql.*;
class QueryDatabase
{
    static{
        try
        {
            LoginDatabase.stan.execute("Select * from queryinfo;");
        }
        catch(SQLException e)
        {
            try 
            {
                LoginDatabase.stan.executeUpdate("create table queryinfo(addby varchar(25),dated date,query_id tinyint,name varchar(25),contact varchar(15),course_id tinyint,comments varchar(100));");
            } 
            catch (SQLException e1) 
            {
                System.out.println("Execptioin in Student database : "+e1);    
            }
            
        }
    }
}