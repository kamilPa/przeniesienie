package pl.topteam.przeniesienie.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import pl.topteam.przeniesienie.model.adres.Adres;



@Entity
@Table(name = "OSOBA")
public class Osoba implements Serializable {

		private static final long serialVersionUID = 1456461374883142517L;

		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "osobaidgen")
		@SequenceGenerator(name = "osobaidgen", sequenceName = "osobaidgen", allocationSize=1)
		private Integer id;

		@Column(name = "NUMER")
		private String numer;

		@Column(name = "IMIE")
		private String imie;

		@Column(name = "NAZWISKO")
		private String nazwisko;
		
		@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
		@JoinColumn(name = "ADRES_ID")
		private Adres adres;

		@Column(name = "PESEL")
		private String pesel;

		@Column(name = "NR_DOWODU")
		private String nrDowodu;

		@Column(name = "PLEC")
		private Boolean plec;

		@Column(name = "TELEFON")
		private String telefon;

		@Column(name = "KARTA_PRZEDPLACONA")
		private String kartaPrzedplacona;

		@Column(name = "FAX")
		private String fax;

		@Temporal(TemporalType.DATE)
		@Column(name = "DATA_URODZENIA")
		private Date dataUrodzenia;

		@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
		@JoinColumn(name = "KONTO_ID")
		private Konto konto;

		
		// Zarządca pod którego podlega wnioskodawca
		@ManyToOne(fetch = FetchType.LAZY, cascade={CascadeType.REFRESH})
		@JoinColumn(name = "ZARZADCA_ID")
		private Zarzadca zarzadca;

		// Dane osoby upoważnionej:

		
	

}





	
