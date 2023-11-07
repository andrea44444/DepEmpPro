package view;

import controlador.Controlador;
import jakarta.persistence.EntityManager;
import model.Empleado;

public class Menu {
	
	public static void main(String[] args) {
		EntityManager em = Controlador.getEntityManager();

        // Ejemplo de inserción de un nuevo empleado en la base de datos
        Empleado empleado = new Empleado();
        empleado.setNombre("NombreEmpleado");
        empleado.setSalario(1000.0);
        // Configura otros atributos del empleado

        // Inicia una transacción
        em.getTransaction().begin();

        // Persiste el nuevo empleado en la base de datos
        em.persist(empleado);

        // Confirma la transacción
        em.getTransaction().commit();

        // Cierra el EntityManager
        Controlador.closeEntityManagerFactory();
	}
}
