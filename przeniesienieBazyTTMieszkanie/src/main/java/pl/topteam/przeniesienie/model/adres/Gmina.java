package pl.topteam.przeniesienie.model.adres;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

@SqlResultSetMapping(name="getGminyZakres", entities={@EntityResult(entityClass=Gmina.class)})

@Entity
@Table(name="GMINA")
public class Gmina implements Serializable, Comparable<Gmina> {
    
 
	private static final long serialVersionUID = -2426343874935050081L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gminaidgen")
	@SequenceGenerator(name = "gminaidgen", sequenceName = "gminaidgen", allocationSize=1)
    private Integer id;
    
    @Column(name="NAZWA")
    private String nazwa;
    
    public Gmina() {
    }

	@Override
	public int compareTo(Gmina o) {
		// TODO Auto-generated method stub
		return 0;
	}
    
    
    
}
