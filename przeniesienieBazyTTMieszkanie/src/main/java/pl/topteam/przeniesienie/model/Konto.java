package pl.topteam.przeniesienie.model;

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
@Table(name = "KONTO")
public class Konto implements Serializable {

	private static final long serialVersionUID = 3419753316802085745L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kontoidgen")
	@SequenceGenerator(name = "kontoidgen", sequenceName = "kontoidgen", allocationSize=1)
	private Integer id;

	@Column(name = "NUMER")
	private String numer;

	@Column(name = "NAZWA")
	private String nazwa;

	@Column(name = "OPIS")
	private String opis;
	
	@ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JoinColumn(name="bank_id")
	private Bank bank;


}
