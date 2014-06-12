package pl.topteam.przeniesienieBazyTTMieszkanie;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class NowaBaza {
		static String skrypt;
		public void wczytajSkryptBazy() {
			FileReader fr;
			try {
				fr = new FileReader("C:/Users/Kamil/Desktop/skrypt_baza.txt");
			} catch (FileNotFoundException e) {
				fr=null;
				e.printStackTrace();
			}
			BufferedReader br = new BufferedReader(fr); 
			String a = new String();
			a = null;
			try {
				while((a = br.readLine()) != null) { 
					System.out.println(a);
					skrypt=skrypt+a;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public void utworzBaze() {
					wczytajSkryptBazy();
					//System.out.print(skrypt);
					BaseConnection.executeStatement("CREATE DATABASE my_db");
					//BaseConnection.executeStatement(skrypt);	  
		}
}

