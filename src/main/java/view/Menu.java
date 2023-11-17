package view;

import java.time.LocalDate;

import controlador.Controlador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Departamento;
import model.Empleado;
import model.Proyecto;

public class Menu {
	
	public static void main(String[] args) {
		EntityManager em = Controlador.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
	        // Nuevo empleado
	        Empleado empleado = new Empleado();
	        empleado.setNombre("Andrea");
	        LocalDate fecha = LocalDate.parse("2003-10-21");
	        empleado.setFNacimiento(fecha);
	        empleado.setSalario(1300.0);
	        
	        //Nuevo proyecto
	        Proyecto proyecto = new Proyecto();
	        proyecto.setNombre("Proyecto nutria");
	        
	        //Nuevo departamento
	        Departamento departamento = new Departamento();
	        departamento.setNombre("Informática");
	        
	
	        // Inicia una transacción
	        transaction.begin();
	        
	
	        // Persiste el nuevo empleado en la base de datos
	        em.persist(empleado);
	        em.persist(proyecto);
	        
	        departamento.addEmpleado(empleado);
	        
	        em.persist(departamento);
	
	        // Confirma la transacción
	        transaction.commit();
		}catch(Exception e) {
			if (transaction.isActive()) {
	            transaction.rollback();
	        }
	        e.printStackTrace();
		} finally {
			em.close(); // Asegura que se cierre el EntityManager en caso de un error
			// Cierra el EntityManager
			Controlador.closeEntityManagerFactory();
		}
	}
}
