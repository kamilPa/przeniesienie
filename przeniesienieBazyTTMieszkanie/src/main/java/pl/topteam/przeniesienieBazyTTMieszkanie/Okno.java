package pl.topteam.przeniesienieBazyTTMieszkanie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class Okno extends JFrame implements ActionListener {
	mieszkanie_baza a = new mieszkanie_baza();
	
	public static String pathToDM_KZAR , pathToDM_DWNIO, pathToDM_KTXT, pathToBaza, serverPath="jdbc:firebirdsql:localhost/3050:";
	

	public static String getPathToBaza() {
		return pathToBaza;
	}
	public static String getPathToDM_WNIO() {
		return pathToDM_DWNIO;
	}
	public static String getPathToDM_KZAR() {
		return pathToDM_KZAR;
	}
	public static String getPathToDM_KTXT() {
		return pathToDM_KTXT;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	JFrame frame = new JFrame();
	JFrame pobierzPlik = new JFrame();
	JFrame framePobierz = new JFrame("Pobierz plik");
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	JPanel master = new JPanel();
	JPanel zarzadca = new JPanel();
	JPanel wnioskodawca = new JPanel();
	JPanel wniosek = new JPanel();
	JPanel szablon = new JPanel();
	JPanel decyzja = new JPanel();
	JPanel baza = new JPanel();
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	JLabel lDM_WNIO,lDM_KZAR,lBaza;
	JLabel lStopka, lNaglowek;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	JTextArea txtPathDM_WNIO, txtPathDM_WNIOS, txtPathDM_KZAR, txtPathDM_KTXT,txtPathBaza;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	JButton bImpZarz, bImpWnio, bPlikZarz, bPlikWnio, bPlikWnios, bPlikBaza, bImpWniosek, bImpSzablon, bPlikSzablon, bImpDecyzja, bImpBaza, bCzyscBaza;
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	JTextArea txtConsole = new JTextArea();
	JScrollPane scrolltxt = new JScrollPane(txtConsole);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	private static final long serialVersionUID = 5885531696187266859L;
	
	public Okno() {
		
		frame.setAlwaysOnTop(false);
		frame.setVisible(true);
		frame.setSize(800,600);
		frame.setTitle("Przeniesienie bazy RADIX NDM+ to TT MIESZKANIE ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tabbedPane.addTab("Main", master);
		tabbedPane.addTab("Baza danych TTMieszkanie", baza);
		tabbedPane.addTab("Zarządcy", zarzadca);
		tabbedPane.addTab("Wnioskodawcy", wnioskodawca);
		tabbedPane.addTab("Wnioski", wniosek);
		//tabbedPane.addTab("Szablony decyzji", szablon);
		tabbedPane.addTab("Decyzje", decyzja);
		JPanel witam = new JPanel();
		witam.add(new JLabel("Witamy"));
		frame.add(witam);
		frame.add(tabbedPane);
		
		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/**
		 * Pierwsza zakladka
		 */ 
		lNaglowek = new JLabel("<html><center>Przenoszenie bazy z programu RADIX NDM+ do bazy TT Mieszkanie</center>"
				+ "</br>Przenoszenie danych:<ul>"
				+ "<li>1. Podajemy sciezke do bazy  </li>"
				+ "<li>2. Zarządca - od tego zaczynamy </li>"
				+ "<li>3. Wnioskodawca - później mamy wnioskodawców</li>"
				+ "<li>4. Import wniosków (wczesniej trzeba zaimportowac zarzadców i wnioskodawców)</li>"
				+ "<li>5. Import decyzji.</li></ul></html>");
		lNaglowek.setLocation(0, 0);
		master.add(lNaglowek);
		master.setLayout(new BoxLayout(master, BoxLayout.PAGE_AXIS));
		
		PrintStream out = new PrintStream( new TextAreaOutputStream( txtConsole ) );
		System.setOut(out);
		System.setErr(out);
		scrolltxt.setBounds(0, 160, 500, 150);
		scrolltxt.setAlignmentY(CENTER_ALIGNMENT);
		
		master.add(scrolltxt);
		
		lStopka = new JLabel("<html><h1>TOP-TEAM TT Sp. z o.o. </h1> <br> Górczewska 222 lok. 271 <br> Warszawa <br> Mazowieckie <br> 01-460</html>");
		lStopka.setAlignmentX(CENTER_ALIGNMENT);
		lStopka.setAlignmentY(BOTTOM_ALIGNMENT);
		//master.add(lStopka);
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/**
		 * Zakladka zarzadcy
		 */
		zarzadca.setPreferredSize(new Dimension(800, 600));
		JLabel lNaglowekZarz = new JLabel("<html><center>Podaj sciezke do pliku DM_KZAR.dbf (wybierz) a nastepnie zaimportuj zarzadcow.</center></br></html>");
		zarzadca.add(lNaglowekZarz);
		zarzadca.setSize(600, 200);
	
		
		zarzadca.setLayout(new BoxLayout(zarzadca, BoxLayout.PAGE_AXIS));
		bImpZarz = new JButton("Importuj Zarządcę");
		zarzadca.add(bImpZarz);
		bImpZarz.addActionListener(this); 
		
		bPlikZarz = new JButton("wybierz..");
		zarzadca.add(bPlikZarz);
		bPlikZarz.addActionListener(this);
		
		zarzadca.setLayout(new BoxLayout(zarzadca, BoxLayout.Y_AXIS));
		txtPathDM_KZAR = new JTextArea("wczytaj plik DM_KZAR.DBF");
		txtPathDM_KZAR.setBorder(BorderFactory.createLineBorder(Color.black));
		txtPathDM_KZAR.setTabSize(NORMAL);
		zarzadca.add(txtPathDM_KZAR);
		zarzadca.add(Box.createRigidArea(new Dimension(0,650)));
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/**
		 * Zadkladka wnioskodawcy
		 */
		
		wnioskodawca.setLayout(new BoxLayout(wnioskodawca, BoxLayout.PAGE_AXIS));
		JLabel lNaglowekWnio = new JLabel("<html>Podaj sciezke do pliku DM_WNIO.dbf (wybierz)</br>\n a nastepnie zaimportuj wnioskodawców.</br></html>");
		wnioskodawca.add(lNaglowekWnio);
		bImpWnio = new JButton("Importuj Wnioskodawcę");
		bImpWnio.setBounds(50, 70, 180, 20);
		wnioskodawca.add(bImpWnio);
		bImpWnio.addActionListener(this);
		
		bPlikWnio = new JButton("wybierz..");
		bPlikWnio.setBounds(230, 70, 90, 20);
		wnioskodawca.add(bPlikWnio);
		bPlikWnio.addActionListener(this);
		
		txtPathDM_WNIO = new JTextArea("Podaj ściężkę :)) plik DM_DWNIO ");
		txtPathDM_WNIO.setBounds(320, 70, 300, 20);
		wnioskodawca.add(txtPathDM_WNIO);
		wnioskodawca.add(Box.createRigidArea(new Dimension(0,650)));
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/**
		 * Zakladca wnioski
		 */
		bImpWniosek = new JButton("Importuj Wniosek");
		bImpWniosek.setBounds(50, 90, 180, 20);
		wniosek.add(bImpWniosek);
		bImpWniosek.addActionListener(this);
		
		bPlikWnios = new JButton("wybierz..");
		bPlikWnios.setBounds(230, 70, 90, 20);
		wniosek.add(bPlikWnios);
		bPlikWnios.addActionListener(this);
		
		txtPathDM_WNIOS = new JTextArea("Podaj ściężkę :)) plik DM_DWNIO ");
		txtPathDM_WNIOS.setBounds(320, 70, 300, 20);
		wniosek.add(txtPathDM_WNIOS);
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/**
		 * Zakladka szablony
		 */
		bImpSzablon = new JButton("Importuj Szablony");
		bImpSzablon.setBounds(50, 110, 180, 20);
		szablon.add(bImpSzablon);
		bImpSzablon.addActionListener(this);
		
		bPlikSzablon = new JButton("wybierz..");
		bPlikSzablon.setBounds(230, 110, 90, 20);
		szablon.add(bPlikSzablon);
		bPlikSzablon.addActionListener(this);
		
		txtPathDM_KTXT = new JTextArea("Podaj ściężkę :)) plik DM_KTXT ");
		txtPathDM_KTXT.setBounds(320, 110, 300, 20);
		szablon.add(txtPathDM_KTXT);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		/**
		 * Zakladka szablony
		 */
		bImpDecyzja = new JButton("Importuj Decyzje");
		bImpDecyzja.setBounds(50, 130, 180, 20);
		decyzja.add(bImpDecyzja);
		bImpDecyzja.addActionListener(this);
		
		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/**
		 * Zakladka baza
		 */
		bImpBaza = new JButton("Zatwierdź");
		bImpBaza.setBounds(50, 110, 180, 20);
		baza.add(bImpBaza);
		bImpBaza.addActionListener(this);
		
		bPlikBaza = new JButton("wybierz..");
		bPlikBaza.setBounds(230, 110, 90, 20);
		baza.add(bPlikBaza);
		bPlikBaza.addActionListener(this);
		
		txtPathBaza = new JTextArea("Podaj lokalizacje bazy ");
		txtPathBaza.setBounds(320, 110, 500, 40);
		baza.add(txtPathBaza);
		
		bCzyscBaza = new JButton("Czysc baze");
		bCzyscBaza.setBounds(70, 300, 180, 20);
		baza.add(bCzyscBaza);
		bCzyscBaza.addActionListener(this);
	}

	@Override
	/**
	 * Akcje 
	 */
	public void actionPerformed(ActionEvent e)  {
		
		Object source = e.getSource();
		if (source==bImpZarz) {
			a.importerDlaZarzadcy();
		} if (source==bImpWnio) {
			a.importerDlaWnioskodawcy();
		} if (source==bImpWniosek) {
			a.importerDlaWniosku();
		} if (source == bImpSzablon) {
			a.importerDlaSzablonu();
		} if (source == bImpDecyzja) {
			a.importerDlaDecyzji();
		} if (source == bCzyscBaza) {
			new CleanBase().oczyscBaze();
		} if (source == bImpBaza) {
			pathToBaza= txtPathBaza.getText();
			new PracownikDAO().utworzPracownika();
			System.out.print(pathToBaza);
		}  if (source==bPlikZarz) {
		    framePobierz.setVisible(false);
		    FileDialog fd =new FileDialog(framePobierz,"Wczytaj plik DM_KZAR.dbf",FileDialog.LOAD);
		    fd.setVisible(true);
		    String katalog=fd.getDirectory();
		    String plik=fd.getFile();
		    pathToDM_KZAR=katalog+plik;
		    txtPathDM_KZAR.setText(getPathToDM_KZAR());
		}if (source==bPlikWnio) {
		    pobierzPlik.setVisible(false);
		    FileDialog fd =new FileDialog(pobierzPlik,"Wczytaj plik DM_DWNI.dbf",FileDialog.LOAD);
		    fd.setVisible(true);
		    String katalog=fd.getDirectory();
		    String plik=fd.getFile();
		    pathToDM_DWNIO= katalog+plik;
		    txtPathDM_WNIO.setText(getPathToDM_WNIO());
		} if (source==bPlikWnios) {
		    pobierzPlik.setVisible(false);
		    FileDialog fd =new FileDialog(pobierzPlik,"Wczytaj plik DM_DWNI.dbf",FileDialog.LOAD);
		    fd.setVisible(true);
		    String katalog=fd.getDirectory();
		    String plik=fd.getFile();
		    pathToDM_DWNIO= katalog+plik;
		    txtPathDM_WNIOS.setText(getPathToDM_WNIO());
		} if (source==bPlikSzablon) {
		    pobierzPlik.setVisible(false);
		    FileDialog fd =new FileDialog(pobierzPlik,"Wczytaj plik DM_KTXT.dbf",FileDialog.LOAD);
		    fd.setVisible(true);
		    String katalog=fd.getDirectory();
		    String plik=fd.getFile();
		    pathToDM_KTXT= katalog+plik;
		    txtPathDM_KTXT.setText(getPathToDM_KTXT());	
		} if (source==bPlikBaza) {
		    pobierzPlik.setVisible(false);
		    FileDialog fd =new FileDialog(framePobierz," lokalizacja bazy",FileDialog.LOAD);
		    fd.setVisible(true);
		    String katalog=fd.getDirectory();
		    String plik=fd.getFile();
		    pathToBaza= serverPath+katalog+plik;
		    txtPathBaza.setText(getPathToBaza());	
		}	 	 
	}
}