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
@Table(name="WOJEWODZTWO")
public class Wojewodztwo implements Serializable, Comparable<Wojewodztwo> {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 4824649833859438012L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wojewodztwoidgen")
	@SequenceGenerator(name = "wojewodztwoidgen", sequenceName = "wojewodztwoidgen", allocationSize=1)
    private Integer id;
    
    @Column(name="NAZWA")
    private String nazwa;
    
    public Wojewodztwo() {
    }

	@Override
	public int compareTo(Wojewodztwo o) {
		// TODO Auto-generated method stub
		return 0;
	}
    
   
    
}
