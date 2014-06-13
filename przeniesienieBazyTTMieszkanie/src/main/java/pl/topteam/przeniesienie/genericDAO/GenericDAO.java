package pl.topteam.przeniesienie.genericDAO;

import java.util.List;

public interface GenericDAO<T, ID> {
	public void zapisz(T t) ;
	public void usun(T t);
	public void usunPoId(ID id);
	public void odswiez(T t);
	public void odlacz(T t);
	public T uaktualnij(T t);
	public T znajdzPoId(ID id);
	public List<T> znajdzWszystko();
	void zsynchronizuj();

}
