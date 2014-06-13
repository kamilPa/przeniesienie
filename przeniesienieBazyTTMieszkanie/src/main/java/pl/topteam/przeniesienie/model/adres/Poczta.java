package pl.topteam.przeniesienie.model.adres;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

@NamedQueries({
    @NamedQuery(name="getPoczty", query="SELECT p FROM Poczta p"),
    @NamedQuery(name="countPoczty", query="SELECT count(p) FROM Poczta p")
})

@SqlResultSetMapping(name="getPocztyZakres", entities={@EntityResult(entityClass=Poczta.class)})


@Entity
@Table(name="POCZTA")
public class Poczta implements Serializable {
	
	private static final long serialVersionUID = 1062634448845078976L;

	public Poczta(){
		
	}
	
	public Poczta(String nazwa){
		this.nazwa = nazwa;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pocztaidgen")
	@SequenceGenerator(name = "pocztaidgen", sequenceName = "pocztaidgen", allocationSize=1)
	private Integer id;
	
	@Column(name="nazwa")
	private String nazwa;

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
	
}
