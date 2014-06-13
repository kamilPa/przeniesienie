package pl.topteam.przeniesienie.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.SequenceGenerator;

import javax.persistence.Table;

@Entity
@Table(name = "BANK")
public class Bank implements Serializable {

	private static final long serialVersionUID = -8278988075964258200L;

	public Bank() {

	}

	public Bank(String nazwa) {
		this.nazwa = nazwa;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bankidgen")
	@SequenceGenerator(name = "bankidgen", sequenceName = "bankidgen", allocationSize = 1)
	private Integer id;

	@Column(name = "nazwa")
	private String nazwa;


	
}
