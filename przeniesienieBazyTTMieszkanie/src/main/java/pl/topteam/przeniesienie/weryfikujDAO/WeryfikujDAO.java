package pl.topteam.przeniesienie.weryfikujDAO;

import java.util.List;

import pl.topteam.przeniesienie.model.Osoba;

public interface WeryfikujDAO {
	
	public List<Osoba> listaOsob();

	public List<Osoba> listaOsobBezZarzadcy();

}
