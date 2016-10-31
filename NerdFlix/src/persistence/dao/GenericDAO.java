package persistence.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import persistence.dominio.Banco;

public class GenericDAO {

	public void inserir (Object entidade){
		EntityManager em = getEntityManager();
		try {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();	
			}
			
			em.persist(entidade);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		finally {
		}
	}
	
	public void alterar (Object entidade){
		EntityManager em = getEntityManager();
		try {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();	
			}
			em.merge(entidade);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		finally {
		}
	}
	
	public void deletar (Object entidade){
		EntityManager em = getEntityManager();
		try {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();	
			}
			em.remove(entidade);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		finally {
		}
	}
	
	public void inserirLog(Object log){
		EntityManager em = getEntityManager();
		try {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();	
			}
			em.persist(log);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		finally {
			em.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> Object findById (int id, Class<T> objClass) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		EntityManager em = getEntityManager();
		T obj = (T) Class.forName(objClass.getName()).newInstance();
		try {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();	
			}
			Query q = em.createQuery("Select o from "+ objClass.getName() +" as o " + "where o.id = :param");
			q.setParameter("param", id);
			obj = (T) q.getSingleResult();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		finally {
		}
		
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> findAll (Class<T> objClass) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		EntityManager em = getEntityManager();
		List<T> lista = new ArrayList<T>();
		
		try {
			if (!em.getTransaction().isActive()) {
				em.getTransaction().begin();	
			}
			Query q = em.createQuery("Select o from "+ objClass.getName() +" as o");
			lista = q.getResultList();
		} catch (Exception e) {
			em.getTransaction().rollback();
		}
		finally {
		}
		
		return lista;
	}
	
	private EntityManager getEntityManager(){
		return Banco.getInstance();
	}
}
