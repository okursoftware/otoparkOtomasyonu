package otopark;


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
    private static boolean kontrol = false;
    DateFormat df = new SimpleDateFormat("HH:mm"); // saat
    DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy"); // tarih

   
// bağlantı işlemi
    private void getConnection() throws ClassNotFoundException, SQLException {
        // sqlite driver
        Class.forName("org.sqlite.JDBC");
        // db oluşturma
        con = DriverManager.getConnection("jdbc:sqlite:SQLiteTest.db");
        initialise();
    }
// normal ekleme işlemi
    public void addUser(String isim, String plaka, int ucret) throws ClassNotFoundException, SQLException {
        if (con == null) {
            
            // get connection
            System.err.println("Baglantı yok");
            getConnection();
        }
         
        Timestamp stamp = stampAl();
Date date = new Date(stamp.getTime());
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
    // uye ekleme
     public void addUye(String isim, String plaka, long evtel , long ceptel , String adres , String uyelikturu) throws ClassNotFoundException, SQLException {
        if (con == null) {
            // get connection
            getConnection();
        }
        long ekle2,a,ekle = 2592000;
        if(uyelikturu == "Aylık"){
            ekle=ekle*1000;
        }else if(uyelikturu == "Yıllık"){
           ekle*=12*1000;
        a=432000*1000; //fark 5 gun
        ekle+=a; 
        }
            
        
        
        ekle2=ekle*12;
        Timestamp stamp = stampAl();
       a= stamp.getTime() + ekle;
Date date = new Date(stamp.getTime());
        String saat = df.format(date);
        String tarih = df1.format(a);
        PreparedStatement prep = con
                .prepareStatement("insert into uye values(?,?,?,?,?,?,?,?,?,?);");
        prep.setString(2, isim);
        prep.setString(3, plaka);
        prep.setLong(4, evtel);
        prep.setLong(5, ceptel);
        prep.setString(6, adres);
        prep.setString(7, uyelikturu);
        prep.setString(8, tarih);
        prep.setString(9, saat);
        prep.setTimestamp(10, stamp);
        prep.execute();

    }
// silme işlemi
    public void deleteUser(int veri) throws ClassNotFoundException, SQLException {

        System.out.println("silme fonksiyonu  " + veri);
        // PreparedStatement prep;
        // prep = con.prepareStatement("delete from user where saat = "+veri+" " );

        Statement state = con.createStatement();

        ResultSet res = state.executeQuery("delete from user where id='" + veri + "' ");

    }
// ekrana gösterme
    public ResultSet displayUsers() throws SQLException, ClassNotFoundException {
        if (con == null) {
            // get connection
          
            getConnection();
        }
        Statement state = con.createStatement();

        ResultSet res = state.executeQuery("select id,isim, plaka ,ucret,tarih,saat,stamp from user");
        return res;
    }
     public ResultSet displayUye() throws SQLException, ClassNotFoundException {
        if (con == null) {
            // get connection
            getConnection();
        }
        
        Statement state = con.createStatement();

        ResultSet res = state.executeQuery("select id2,isim, plaka ,evtel,ceptel,uyelik turu ,bitistarih from uye");
        return res;
    }
// ödenecek fiyat
    public void hesap(int ıd) throws SQLException, ClassNotFoundException {

        Timestamp stamp = stampAl();
        Date date = new Date(stamp.getTime());

        Statement state = con.createStatement();

        ResultSet res = state.executeQuery("select id,ucret,stamp from user");
        long c, d;
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
                if (30 < c && c < 60) {
                    alinacakPara = 5;
                } else if (c >= 60) {
                    c = c / 60;
                    alinacakPara = 5;

                    for (i = 0; i < c; i++) {

                        alinacakPara += ucret;

                    }

                }
                JOptionPane.showMessageDialog(null, "Üçret = " + alinacakPara + "TL");

            }
        }

    }
// sql oluştur
    private void initialise() throws SQLException {
        if (!kontrol) {
            kontrol = true;
            // check for database table
            Statement state = con.createStatement();
            
            //tablo1
            ResultSet res = state.executeQuery("SELECT * FROM sqlite_master WHERE type='table' AND name='user'");
          if(!res.next()){
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
               state2.executeUpdate("create table uye("
                        + "id2 integer,"
                        + "isim varchar(60),"
                        + "plaka varchar(60),"
                        + "evtel integer,"
                        + "ceptel integer,"
                        + "adres varchar(200),"
                        + "uyelik turu varchar(20),"
                        + "bitistarih varchar(60),"
                        + "saat varchar(60),"
                        + "stamp timestamp,"
                        + "primary key (id2));");
          }
                // örnek atama
              /*  Timestamp stamp = new Timestamp(System.currentTimeMillis());
                Date date = new Date(stamp.getTime());
                String saat = df.format(date);
                String tarih = df1.format(date);
                PreparedStatement prep = con.prepareStatement("insert into user values(?,?,?,?,?,?,?);");
                prep.setString(2, "Muhammed OKUR");
                prep.setString(3, "38 OKR 38");
                prep.setInt(4, 38);
                prep.setString(5, tarih);
                prep.setString(6, saat);
                prep.setTimestamp(7, stamp);
                prep.execute();
               PreparedStatement prep2 = con.prepareStatement("insert into uye values(?,?,?,?,?,?,?,?,?,?);");
                prep2.setString(2, "Muhammed OKUR");
                prep2.setString(3, "38 OKR 38");
                prep2.setInt(4, 0123123);
                prep2.setInt(5,352);
                prep2.setString(6 ,"Kayseri ");
                prep2.setString(7, "yıllık");
                prep2.setString(8, tarih);
                prep2.setString(9, saat);
                prep2.setTimestamp(10, stamp);
                prep2.execute();*/
                
         
      
        }
    }
// zamanı güncel almak için    
    private  Timestamp stampAl(){
        // ms cinsinden 
         Timestamp stamp = new Timestamp(System.currentTimeMillis());

        return stamp;
    }

}
