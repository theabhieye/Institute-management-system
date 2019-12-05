import java.sql.*;
class feeDatabase
{
    feeDatabase()
    {
        try 
        {
            LoginDatabase.stan.executeQuery("select * from feeinfo;");
        } 
        catch (SQLException e) 
        {
            try
            {
                
                LoginDatabase.stan.executeUpdate("create table feeinfo(recepit_no tinyint,reg_no tinyint,amount_paid float,dated date);");
                System.out.println("fee database created");
            }
            catch (SQLException e1) 
            {
                System.out.println("create table feeinfo(recepit_no tinyint,reg_no tinyint,amount_paid float);");
                System.out.println(e1.getMessage());
            }
        }
    }
}