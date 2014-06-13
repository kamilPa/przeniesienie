package pl.topteam.przeniesienie.model.adres;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ADRES")
public class Adres implements Serializable, Comparable<Adres> {

	private static final long serialVersionUID = -1455195675612617276L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adresidgen")
	@SequenceGenerator(name = "adresidgen", sequenceName = "adresidgen", allocationSize=1)
	private Integer id;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "ULICA_ID")
	private Ulica ulica;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "MIEJSCOWOSC_ID")
	private Miejscowosc miejscowosc;

	@Column(name = "NR_DOMU")
	private String nrDomu;

	@Column(name = "NR_MIESZKANIA")
	private String nrMieszkania;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "WOJEWODZTWO_ID")
	private Wojewodztwo wojewodztwo;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "GMINA_ID")
	private Gmina gmina;

	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JoinColumn(name="POCZTA_ID")
	private Poczta poczta;

	@Column(name = "KOD_POCZTOWY")
	private String kodPocztowy;

	public Adres() {

	}

	@Override
	public int compareTo(Adres o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Ulica getUlica() {
		return ulica;
	}
	public void setUlica(Ulica ulica) {
		this.ulica = ulica;
	}
	public Miejscowosc getMiejscowosc() {
		return miejscowosc;
	}
	public void setMiejscowosc(Miejscowosc miejscowosc) {
		this.miejscowosc = miejscowosc;
	}
	public String getNrDomu() {
		return nrDomu;
	}
	public void setNrDomu(String nrDomu) {
		this.nrDomu = nrDomu;
	}
	public String getNrMieszkania() {
		return nrMieszkania;
	}
	public void setNrMieszkania(String nrMieszkania) {
		this.nrMieszkania = nrMieszkania;
	}
	public Wojewodztwo getWojewodztwo() {
		return wojewodztwo;
	}
	public void setWojewodztwo(Wojewodztwo wojewodztwo) {
		this.wojewodztwo = wojewodztwo;
	}
	public Gmina getGmina() {
		return gmina;
	}
	public void setGmina(Gmina gmina) {
		this.gmina = gmina;
	}
	public Poczta getPoczta() {
		return poczta;
	}
	public void setPoczta(Poczta poczta) {
		this.poczta = poczta;
	}
	public String getKodPocztowy() {
		return kodPocztowy;
	}
	public void setKodPocztowy(String kodPocztowy) {
		this.kodPocztowy = kodPocztowy;
	}

}
