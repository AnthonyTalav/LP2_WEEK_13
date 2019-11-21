/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deportes.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import deportes.controlador.Articulo;
import deportes.controlador.Categoria;
import deportes.dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author lab01
 */
public class CategoriaJpaController implements Serializable {

    public CategoriaJpaController() {
        this.emf =  Persistence.createEntityManagerFactory("appJPAPU"); ;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoria categoria) {
        if (categoria.getArticuloCollection() == null) {
            categoria.setArticuloCollection(new ArrayList<Articulo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Articulo> attachedArticuloCollection = new ArrayList<Articulo>();
            for (Articulo articuloCollectionArticuloToAttach : categoria.getArticuloCollection()) {
                articuloCollectionArticuloToAttach = em.getReference(articuloCollectionArticuloToAttach.getClass(), articuloCollectionArticuloToAttach.getIdarticulo());
                attachedArticuloCollection.add(articuloCollectionArticuloToAttach);
            }
            categoria.setArticuloCollection(attachedArticuloCollection);
            em.persist(categoria);
            for (Articulo articuloCollectionArticulo : categoria.getArticuloCollection()) {
                Categoria oldIdcategoriaOfArticuloCollectionArticulo = articuloCollectionArticulo.getIdcategoria();
                articuloCollectionArticulo.setIdcategoria(categoria);
                articuloCollectionArticulo = em.merge(articuloCollectionArticulo);
                if (oldIdcategoriaOfArticuloCollectionArticulo != null) {
                    oldIdcategoriaOfArticuloCollectionArticulo.getArticuloCollection().remove(articuloCollectionArticulo);
                    oldIdcategoriaOfArticuloCollectionArticulo = em.merge(oldIdcategoriaOfArticuloCollectionArticulo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoria categoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria persistentCategoria = em.find(Categoria.class, categoria.getIdcategoria());
            Collection<Articulo> articuloCollectionOld = persistentCategoria.getArticuloCollection();
            Collection<Articulo> articuloCollectionNew = categoria.getArticuloCollection();
            Collection<Articulo> attachedArticuloCollectionNew = new ArrayList<Articulo>();
            for (Articulo articuloCollectionNewArticuloToAttach : articuloCollectionNew) {
                articuloCollectionNewArticuloToAttach = em.getReference(articuloCollectionNewArticuloToAttach.getClass(), articuloCollectionNewArticuloToAttach.getIdarticulo());
                attachedArticuloCollectionNew.add(articuloCollectionNewArticuloToAttach);
            }
            articuloCollectionNew = attachedArticuloCollectionNew;
            categoria.setArticuloCollection(articuloCollectionNew);
            categoria = em.merge(categoria);
            for (Articulo articuloCollectionOldArticulo : articuloCollectionOld) {
                if (!articuloCollectionNew.contains(articuloCollectionOldArticulo)) {
                    articuloCollectionOldArticulo.setIdcategoria(null);
                    articuloCollectionOldArticulo = em.merge(articuloCollectionOldArticulo);
                }
            }
            for (Articulo articuloCollectionNewArticulo : articuloCollectionNew) {
                if (!articuloCollectionOld.contains(articuloCollectionNewArticulo)) {
                    Categoria oldIdcategoriaOfArticuloCollectionNewArticulo = articuloCollectionNewArticulo.getIdcategoria();
                    articuloCollectionNewArticulo.setIdcategoria(categoria);
                    articuloCollectionNewArticulo = em.merge(articuloCollectionNewArticulo);
                    if (oldIdcategoriaOfArticuloCollectionNewArticulo != null && !oldIdcategoriaOfArticuloCollectionNewArticulo.equals(categoria)) {
                        oldIdcategoriaOfArticuloCollectionNewArticulo.getArticuloCollection().remove(articuloCollectionNewArticulo);
                        oldIdcategoriaOfArticuloCollectionNewArticulo = em.merge(oldIdcategoriaOfArticuloCollectionNewArticulo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoria.getIdcategoria();
                if (findCategoria(id) == null) {
                    throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoria;
            try {
                categoria = em.getReference(Categoria.class, id);
                categoria.getIdcategoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.", enfe);
            }
            Collection<Articulo> articuloCollection = categoria.getArticuloCollection();
            for (Articulo articuloCollectionArticulo : articuloCollection) {
                articuloCollectionArticulo.setIdcategoria(null);
                articuloCollectionArticulo = em.merge(articuloCollectionArticulo);
            }
            em.remove(categoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoria> findCategoriaEntities() {
        return findCategoriaEntities(true, -1, -1);
    }

    public List<Categoria> findCategoriaEntities(int maxResults, int firstResult) {
        return findCategoriaEntities(false, maxResults, firstResult);
    }

    private List<Categoria> findCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoria.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Categoria findCategoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoria> rt = cq.from(Categoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
