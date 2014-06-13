package pl.topteam.przeniesienie.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pl.topteam.przeniesienie.model.adres.Adres;





@Entity
@Table(name = "ZARZADCA")
public class Zarzadca implements Serializable, Comparable<Zarzadca> {

	private static final long serialVersionUID = 682060011464575525L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "zarzadcaidgen")
	@SequenceGenerator(name = "zarzadcaidgen", sequenceName = "zarzadcaidgen", allocationSize=1)
	private Integer id;

	@Column(name = "NAZWA")
	private String nazwa;

	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn(name = "ADRES_ID")
	private Adres adres;

	@Column(name = "RODZAJ")
	private Integer rodzaj;

	@Column(name = "FAX")
	private String fax;

	@Column(name = "TELEFON")
	private String telefon;

	@Column(name = "KARTA_PRZEDPLACONA")
	private String kartaPrzedplacona;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "KONTO_ID")
	private Konto konto;

	@OneToMany(mappedBy = "zarzadca", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<Osoba> osoby = new ArrayList<Osoba>();
	
	@Column(name= "sposob_wyplaty")
	private Integer sposobWyplaty;


	@Override
	public int compareTo(Zarzadca o) {
		return 0;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public Adres getAdres() {
		return adres;
	}
	public void setAdres(Adres adres) {
		this.adres = adres;
	}
	public Integer getRodzaj() {
		return rodzaj;
	}
	public void setRodzaj(Integer rodzaj) {
		this.rodzaj = rodzaj;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public String getKartaPrzedplacona() {
		return kartaPrzedplacona;
	}
	public void setKartaPrzedplacona(String kartaPrzedplacona) {
		this.kartaPrzedplacona = kartaPrzedplacona;
	}
	public Konto getKonto() {
		return konto;
	}
	public void setKonto(Konto konto) {
		this.konto = konto;
	}
	public List<Osoba> getOsoby() {
		return osoby;
	}
	public void setOsoby(List<Osoba> osoby) {
		this.osoby = osoby;
	}
	public Integer getSposobWyplaty() {
		return sposobWyplaty;
	}
	public void setSposobWyplaty(Integer sposobWyplaty) {
		this.sposobWyplaty = sposobWyplaty;
	}
	

}
