package pl.topteam.przeniesienie.model.adres;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="MIEJSCOWOSC")
public class Miejscowosc implements Serializable, Comparable<Miejscowosc> {
    
	private static final long serialVersionUID = -4237269958557484326L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "miejscowoscidgen")
	@SequenceGenerator(name = "miejscowoscidgen", sequenceName = "miejscowoscidgen", allocationSize=1)
    private Integer id;
    
    @Column(name="NAZWA")
    private String nazwa;
    
    @Column(name="czy_miasto")
    @Type(type="onezeroboolean")
    private Boolean miasto;
    
    public Miejscowosc() {
	
    }

	@Override
	public int compareTo(Miejscowosc o) {
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
	public Boolean getMiasto() {
		return miasto;
	}
	public void setMiasto(Boolean miasto) {
		this.miasto = miasto;
	}
    
	
   
}
