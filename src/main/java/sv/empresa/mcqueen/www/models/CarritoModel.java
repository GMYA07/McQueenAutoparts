package sv.empresa.mcqueen.www.models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import sv.empresa.mcqueen.www.entities.MecanicosEntity;
import sv.empresa.mcqueen.www.utils.JpaUtil;
import sv.empresa.mcqueen.www.entities.CarritoEntity;
import java.util.List;

public class CarritoModel {
    public List<CarritoEntity> listarCarrito(){
        EntityManager Em = JpaUtil.getEntityManager();
        try {
            Query consulta = Em.createQuery("SELECT e FROM CarritoEntity e");

            // El método getResultList() de la clase Query permite obtener
            // la lista de resultados de una consulta de selección
            List<CarritoEntity> lista = consulta.getResultList();

            Em.close(); // Cerrando el EntityManager
            return lista;
        } catch (Exception e) {
            Em.close();
            return null;
        }
    }
    public CarritoEntity obtenerItemCarrito(String idItem){
        EntityManager Em = JpaUtil.getEntityManager();
        try {
            //Obtener Item Carrito
            Query consulta = Em.createQuery("SELECT e FROM CarritoEntity e WHERE e.repuestosByIdProducto.idRepuesto = :item ");
            consulta.setParameter("item",idItem);
            if (!consulta.getResultList().isEmpty()){
                CarritoEntity itemCarrito = (CarritoEntity) consulta.getSingleResult(); //Con "getSingleResult" Obtenemos solo 1 resultado de lo q desesmos
                Em.close();
                return itemCarrito;
            }else {
                Em.close();
                return null;
            }
        }catch (Exception e){
            Em.close();
            return null;
        }
    }
    public int insertarCarrito(CarritoEntity newItemCarrito){
        EntityManager eM = JpaUtil.getEntityManager();
        EntityTransaction trans = eM.getTransaction();
        try {
            trans.begin();
            eM.persist(newItemCarrito);
            trans.commit();
            eM.close();
            return 1;
        }catch (Exception e){
            eM.close();
            return 0;
        }
    }
    public int modificarCarrito(CarritoEntity modiCarrito){
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try{
            trans.begin();
            em.merge(modiCarrito);
            trans.commit();
            em.close();
            return 1;
        }catch (Exception e){
            em.close();
            return 0;
        }
    }
    public int borrarItemCarrito(int idCarrito){
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tran = em.getTransaction();
        int filasBorradas = 0;
        try {
            // Recuperando el objeto a eliminar
            CarritoEntity carrito = em.find(CarritoEntity.class,idCarrito);
            if (carrito != null){
                tran.begin();
                em.remove(carrito);
                tran.commit();
                em.close();
                filasBorradas = 1;
            }
            return filasBorradas;
        }catch (Exception e){
            em.close();
            return 0;
        }
    }
    public int vaciarCarrito(){
        EntityManager eM = JpaUtil.getEntityManager();
        EntityTransaction tran = eM.getTransaction();
        try {

            tran.begin();
            Query consulta = eM.createQuery("DELETE FROM CarritoEntity e");
            int rowCount = consulta.executeUpdate();
            tran.commit();

            if (rowCount == 1){
                eM.close();
                return rowCount;
            }else {
                return 0;
            }

        }catch (Exception e){
            eM.close();
            return 0;
        }
    }
}
