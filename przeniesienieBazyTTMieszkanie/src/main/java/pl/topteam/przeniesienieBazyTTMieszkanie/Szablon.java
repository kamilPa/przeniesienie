package pl.topteam.przeniesienieBazyTTMieszkanie;

public class Szablon {
	
	private String nazwa;
	private String tresc;
	private String nrLinijki;
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	public String getTresc() {
		return tresc;
	}
	public void setTresc(String tresc) {
		this.tresc = tresc;
	}
	public String getNrLinijki() {
		return nrLinijki;
	}
	public void setNrLinijki(String nrLinijki) {
		this.nrLinijki = nrLinijki;
	}
	public Integer getIntValueOfNrLinijki() {
		return Integer.valueOf(nrLinijki);
	}
	

}
