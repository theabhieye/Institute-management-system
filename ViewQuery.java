import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
class ViewQuery
{
    JDialog viewRecordDialog;
    JTable recordTable;
    DefaultTableModel dtm;
    JScrollPane sp;
    ViewQuery()
    {
        viewRecordDialog=new JDialog(Managenment.frame,"View Entry",true);
        viewRecordDialog.setBounds(320,250,780,760);

        String head[]={"Query No","Name","Contact","Date","Course","Comments","Add by"};
        String item[][]={};
        dtm=new DefaultTableModel(item,head);
        try 
        {
            ResultSet rs;
            int rowno=0,noOfRow=0;
            String row[]= new String[7];
            
            rs=LoginDatabase.stan.executeQuery("select * from queryinfo;");
            
            Statement stm = LoginDatabase.conn.createStatement();
            ResultSet rs1;

            while(rs.next())
            {
                row[0]=rs.getString("query_id");
                row[1]=rs.getString("name");
                row[2]=rs.getString("contact");
                
                row[3]=rs.getString("dated");
                rs1 = stm.executeQuery("select * from courseinfo where course_id="+rs.getInt("course_id")+";");
                while(rs1.next())
                    row[4]=rs1.getString("course_name");

                row[5]=rs.getString("comments");
                row[6]=rs.getString("addby");
                dtm.insertRow(rowno, row);
                rowno++;
            }           
        } 
        catch (SQLException e) 
        {
            System.out.println("sql exception in Query Record "+e.getMessage());
        }
        recordTable=new JTable(dtm);
        sp=new JScrollPane(recordTable);
        recordTable.setRowSelectionAllowed(false);
        recordTable.setColumnSelectionAllowed(false);
        sp.setVisible(true); 
        viewRecordDialog.add(sp);
        viewRecordDialog.setVisible(true);
    }
}
