
package otopark;

import java.sql.ResultSet;


public class Otopark {
// ilk çalışan class main 
    public static void main(String[] args) {

        OtoparkMain main = new OtoparkMain();
        main.setVisible(true);
        SQLiteYonetim db = new SQLiteYonetim();
        ResultSet rs;

    }
    
    public void yeniUye(){
        UyeKaydi uye = new UyeKaydi();
        uye.setVisible(true);
    }
}
