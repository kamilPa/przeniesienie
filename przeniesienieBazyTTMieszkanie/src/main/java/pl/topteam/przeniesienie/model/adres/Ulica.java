package pl.topteam.przeniesienie.model.adres;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="ULICA")
public class Ulica implements Serializable, Comparable<Ulica> {
    
	private static final long serialVersionUID = -3802430969874212197L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ulicaidgen")
	@SequenceGenerator(name = "ulicaidgen", sequenceName = "ulicaidgen", allocationSize=1)
    private Integer id;
    
    @Column(name="NAZWA")
    private String nazwa;
    
    public Ulica() {
    }

	@Override
	public int compareTo(Ulica o) {
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
	
	
    
}
