import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
class ViewRecord
{
    JDialog viewRecordDialog;
    JTable recordTable;
    DefaultTableModel dtm;
    JScrollPane sp;
    ViewRecord()
    {
        viewRecordDialog=new JDialog(Managenment.frame,"View Entry",true);
        viewRecordDialog.setBounds(320,250,780,760);

        String head[]={"Reg No","Name","Contact","Course","Batch Start","Batch Timing","Batch NO","Add by"};
        String item[][]={};
        dtm=new DefaultTableModel(item,head);
        try 
        {
            ResultSet rs;
            int rowno=0,noOfRow=0;
            int course_id[];
            String row[]= new String[8];
        
            rs=LoginDatabase.stan.executeQuery("select * from batchinfo");
            while (rs.next())
            {
                // row[4]=rs.getString("batch_date");
                // row[5]=rs.getString("batch_time");
                noOfRow++;
                System.out.println(noOfRow+"-"+rs.getString("batch_date")+"\n"+rs.getString("batch_time"));
            }
            course_id=new int[noOfRow];
            int m=0;
            rs=LoginDatabase.stan.executeQuery("select * from studentinfo;");
            Statement stm = LoginDatabase.conn.createStatement();
            ResultSet rs1;
            Statement stm1 = LoginDatabase.conn.createStatement();
            ResultSet rs2;

            while(rs.next())
            {
                row[0]=rs.getString("reg_no");
                row[1]=rs.getString("name");
                row[2]=rs.getString("contact");
                
                rs1 = stm.executeQuery("select * from courseinfo where course_id="+rs.getInt("course_id")+";");
                while(rs1.next())
                    row[3]=rs1.getString("course_name");
                
                rs2=LoginDatabase.stan.executeQuery("select * from batchinfo where ");
                while (rs.next())
                {
                    row[4]=rs.getString("batch_date");
                    row[5]=rs.getString("batch_time");
                    noOfRow++;
                    System.out.println(noOfRow+"-"+rs.getString("batch_date")+"\n"+rs.getString("batch_time"));
                }
                
                row[6]=rs.getString("batch_no");
                row[7]=rs.getString("addby");
                dtm.insertRow(rowno, row);
                rowno++;
            }
            
        } 
        catch (SQLException e) 
        {
            System.out.println("sql exception in view Record "+e.getMessage());
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
