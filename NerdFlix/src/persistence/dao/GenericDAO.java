package persistence.dao;

import java.io.ObjectStreamClass;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import persistence.dominio.Banco;

public class GenericDAO {

	public void inserir (Object entidade){
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.persist(entidade);
		em.getTransaction().commit();
	}
	
	public void alterar (Object entidade){
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.merge(entidade);
		em.getTransaction().commit();
	}
	
	public void deletar (Object entidade){
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.remove(entidade);
		em.getTransaction().commit();
	}
	
	public void inserirLog(Object log){
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(log);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		finally {
			em.close();
		}
	}
	
	public <T> Object findById (int id, Class<T> objClass) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		EntityManager em = getEntityManager();
		T obj = (T) Class.forName(objClass.getName()).newInstance();
		em.getTransaction().begin();
		Query q = em.createQuery("Select o from "+ objClass.getName() +" as o " + "where o.id = :param");
		q.setParameter("param", id);
		obj = (T) q.getSingleResult();
		
		return obj;
	}
	
	private EntityManager getEntityManager(){
		return Banco.getInstance();
	}
}
