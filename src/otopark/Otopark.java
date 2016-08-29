/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otopark;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Otopark {

    public static void main(String[] args) {
       
     

        
           OtoparkMain main = new OtoparkMain();
           main.setVisible(true);
		SQLiteYonetim db = new SQLiteYonetim();
		ResultSet rs;
                
       // try {
      //     db.addUser("Hatice okur", "38 okr 38",85);
      // } catch (ClassNotFoundException | SQLException ex) {
      //      Logger.getLogger(Otopark.class.getName()).log(Level.SEVERE, null, ex);
      // }
		
          
                
	//	try {
	//		  rs = db.displayUsers();
			  
	//		  while (rs.next()) {
	//			     System.out.println("isim : "+rs.getString("isim") + "  "
          //                           + "  " +"plaka : "+ rs.getString("plaka")+"  "
            //                         + "  " +"saatlik ucret : "+ rs.getString("ucret")
              //                       + "  " +"tarih : "+ rs.getString("tarih")+"  "
                //                     + "  " +"saat : "+ rs.getString("saat")+"  ");
		//		  }
			  
		  //} catch (Exception e) {
		//	  e.printStackTrace();
		  //}

            
 
	}
    }
    

