package operacionesCRUD;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public interface OperacionesCRUD {
	
	void insertar(EntityManager em, EntityTransaction transaction);
    
    void modificar(EntityManager em, EntityTransaction transaction);
    
    void eliminar(EntityManager em, EntityTransaction transaction);
    
    void buscarPorCodigo(EntityManager em);
    
    void buscarPorNombre(EntityManager em);
    
    void mostrar(EntityManager em);
}
