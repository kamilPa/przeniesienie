package pl.topteam.przeniesienie.weryfikujDAO;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import pl.topteam.przeniesienie.genericDAO.GenericDAOImpl;
import pl.topteam.przeniesienie.model.Osoba;

public class WeryfikujDAOImpl extends GenericDAOImpl<Osoba, Integer> implements WeryfikujDAO {

	@Override
	public List<Osoba> listaOsob() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Osoba> cq = cb.createQuery(Osoba.class);
		cq.select(cq.from(Osoba.class));
		TypedQuery<Osoba> q = getEntityManager().createQuery(cq);
		return q.getResultList();
	}
	
	@Override
	public List<Osoba> listaOsobBezZarzadcy() {
		String sql = ""
				+ "SELECT osoba "
				+ "FROM Osoba osoba"
				+ "WHERE osoba.zarzadca IS NULL";
		return getEntityManager().createQuery(sql, Osoba.class)
				.getResultList();
	}

}
