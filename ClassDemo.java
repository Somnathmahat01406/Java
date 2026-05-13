import java.sql.*;
import java.util.Scanner;

class FetchData {
    public void getData(Statement st) throws Exception {
        ResultSet rs = st.executeQuery("Select * from Student");
        while (rs.next()) {
            System.out.print(rs.getInt(1));
            System.out.print("\t" + rs.getString(2));
            System.out.println("\t" + rs.getInt(3));
        }
        rs.close();
    }
}

class Demo {
    public static void main(String args[]) {
        try {
            String constr = "jdbc:mysql://localhost/ri_db";
            String user = "test", pass = "test123";
            Connection cn = DriverManager.getConnection(constr, user,
                    pass);
            /*
             * DriverManager class is being used
             * getConnection() is a static method of that class
             */
            Statement st = cn.createStatement();
            st.executeUpdate("Create Table Student (roll int, name varchar(10), cgpa int)");
            System.out.println("Table created: Student");
            Scanner sc = new Scanner(System.in);
            int rows = sc.nextInt();
            int rows1 = rows;
            while (rows > 0) {
                int roll = sc.nextInt();
                sc.nextLine(); // consume newline
                String name = sc.nextLine();
                int cgpa = sc.nextInt();
                st.executeUpdate("Insert into Student values(" + roll + ",'" + name + "'," + cgpa + ")");
                rows--;
            }
            System.out.println("Inserted " + rows1 + " records.");
            FetchData objj = new FetchData();
            objj.getData(st);
            // Delete
            int whichDelete = sc.nextInt();
            int delCount = st.executeUpdate("delete from Student where roll =" + whichDelete);
            if (delCount > 0)
                System.out.println("Row deleted Successfully");
            else
                System.out.println("Record Not Found to perform Delete Operation");
            objj.getData(st);
            // Update
            int whichUpdate = sc.nextInt();
            sc.nextLine();
            String newName = sc.nextLine();
            int updateCount = st
                    .executeUpdate("UPDATE Student SET name = '" + newName + "' WHERE roll = " + whichUpdate);
            if (updateCount > 0)
                System.out.println("Row Updated Successfully");
            else
                System.out.println("Record Not Found to perform Update Operation");
            objj.getData(st);
            st.close();
            cn.close();
        } catch (Exception e) {
            System.out.print("Exception: " + e.getMessage());
        }
    }
}