package otopark;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.sql.Date;
import java.sql.Timestamp;
import javax.accessibility.AccessibleRole;
import javax.swing.JOptionPane;

public class SQLiteYonetim {

    private static Connection con;
    private static boolean hasData = false;
    DateFormat df = new SimpleDateFormat("HH:mm");
    DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");

    Timestamp stamp = new Timestamp(System.currentTimeMillis());
    Date date = new Date(stamp.getTime());

    private void getConnection() throws ClassNotFoundException, SQLException {
        // sqlite driver
        Class.forName("org.sqlite.JDBC");
        // database path, if it's new database, it will be created in the project folder
        con = DriverManager.getConnection("jdbc:sqlite:SQLiteTest1.db");
        initialise();
    }

    public void addUser(String isim, String plaka, int ucret) throws ClassNotFoundException, SQLException {
        if (con == null) {
            // get connection
            getConnection();
        }
        Timestamp stamp = new Timestamp(System.currentTimeMillis());

        String saat = df.format(date);
        String tarih = df1.format(date);
        PreparedStatement prep = con
                .prepareStatement("insert into user values(?,?,?,?,?,?,?);");
        prep.setString(2, isim);
        prep.setString(3, plaka);
        prep.setInt(4, ucret);
        prep.setString(5, tarih);
        prep.setString(6, saat);
        prep.setTimestamp(7, stamp);
        prep.execute();

    }

    public void deleteUser(String veri) throws ClassNotFoundException, SQLException {

        System.out.println("silme fonksiyonu  " + veri);
        // PreparedStatement prep;
        // prep = con.prepareStatement("delete from user where saat = "+veri+" " );

        Statement state = con.createStatement();

        ResultSet res = state.executeQuery("delete from user where date = '" + veri + "' ");

    }

    public ResultSet displayUsers() throws SQLException, ClassNotFoundException {
        if (con == null) {
            // get connection
            getConnection();
        }
        Statement state = con.createStatement();

        ResultSet res = state.executeQuery("select id,isim, plaka ,ucret,tarih,saat,stamp from user");
        return res;
    }

    public void hesap(int ıd) throws SQLException, ClassNotFoundException {

        Timestamp stamp = new Timestamp(System.currentTimeMillis());
        Date date = new Date(stamp.getTime());

        Statement state = con.createStatement();

        ResultSet res = state.executeQuery("select id,ucret,stamp from user");
        long  c, d;
int i;
        while (res.next()) {

            String id1 = res.getString("id");
            if (Integer.valueOf(id1) == ıd) {
                int ucret = res.getInt("ucret");
                System.out.println(ucret);
                Timestamp sqlStamp = res.getTimestamp("stamp");
                System.out.println(stamp + " " + sqlStamp);
                d = stamp.getTime() - sqlStamp.getTime();
                System.out.println(d);
                c = d / 1000 / 60;
                int alinacakPara = 0;
                if(30<c&&c<60){
                    alinacakPara = 5;
                }else if(c>=60){
                    c=c/60;
                    
                    for (i = 0; i < c; i++) {

                            alinacakPara += ucret;

                        }
                   
                }
                 JOptionPane.showMessageDialog(null,"Üçret = "+ alinacakPara+"TL");
                
           


            }
        }
       

    }

    private void initialise() throws SQLException {
        if (!hasData) {
            hasData = true;
            // check for database table
            Statement state = con.createStatement();
            ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='user'");
            if (!res.next()) {
                System.out.println("Building the User table with prepopulated values.");
                // need to build the table
                Statement state2 = con.createStatement();
                state2.executeUpdate("create table user("
                        + "id integer,"
                        + "isim varchar(60),"
                        + "plaka varchar(60),"
                        + "ucret integer,"
                        + "tarih varchar(60),"
                        + "saat varchar(60),"
                        + "stamp timestamp,"
                        + "primary key (id));");

                // inserting some sample data
                Timestamp stamp = new Timestamp(System.currentTimeMillis());
                Date date = new Date(stamp.getTime());
                String saat = df.format(date);
                String tarih = df1.format(date);
                PreparedStatement prep = con.prepareStatement("insert into user values(?,?,?,?,?,?,?);");
                prep.setString(2, "Muhammed OKUR");
                prep.setString(3, "38 OKR 38");
                prep.setInt(4, 38);
                prep.setString(5, tarih);
                prep.setString(6, saat);

                prep.execute();

            }

        }
    }

}
