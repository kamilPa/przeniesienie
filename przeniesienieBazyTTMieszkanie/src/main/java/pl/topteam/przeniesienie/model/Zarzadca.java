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
		// TODO Auto-generated method stub
		return 0;
	}
	

}
