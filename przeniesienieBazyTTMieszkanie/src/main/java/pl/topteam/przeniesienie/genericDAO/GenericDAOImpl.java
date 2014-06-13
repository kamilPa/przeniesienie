package pl.topteam.przeniesienie.genericDAO;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GenericDAOImpl<T, ID> implements GenericDAO<T, ID>{
	private Class<T> persistentClass;
	private Session session;
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public GenericDAOImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
			.getActualTypeArguments()[0];
		//this.session = (Session) getEntityManager().getDelegate();
	}
	
	@PersistenceContext(name="TTMieszkaniePU", unitName="TTMieszkaniePU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	public EntityManager getEntityManager() {
		return em;
	}
	
	@Override
	public void zapisz(T t) {
		em.persist(t);
	}
	
	@Override
	public void usun(T t) {
		em.remove(t);
	}
	
	@Override
	public void usunPoId(ID id) {
		T t = em.find(persistentClass, id);
		usun(t);
	}
	
	@Override
	public T uaktualnij(T t) {
		return em.merge(t);
	}
		
	@Override
	@Transactional(readOnly=true)
	public T znajdzPoId(ID id) {
		return em.find(persistentClass, id);
	}

	@Override
	public void odswiez(T t) {
		em.refresh(t);
	}
	
	@Override
	@Transactional(readOnly=true)
	public void odlacz(T t) {
		em.detach(t);
	}

	@Override
	public List<T> znajdzWszystko() {
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<T> c = qb.createQuery(persistentClass);
		c.select(c.from(persistentClass));
		TypedQuery<T> q = em.createQuery(c);
		return q.getResultList();
	}
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void test() {
	}
	
	@Override
	public void zsynchronizuj() {
		getEntityManager().flush();
	}
}

