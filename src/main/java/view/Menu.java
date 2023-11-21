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
	        empleado.setNombre("Juan");
	        LocalDate fecha = LocalDate.parse("2000-12-16");
	        empleado.setFNacimiento(fecha);
	        empleado.setSalario(1200.0);
	        
	        //Nuevo proyecto
	        Proyecto proyecto = new Proyecto();
	        proyecto.setNombre("Proyecto pingüino");
	        
	        //Nuevo departamento
	        Departamento departamento = new Departamento();
	        departamento.setNombre("Informatica");
	        
	
	        // Inicia una transacción
	        transaction.begin();
	        
	
	        // Persiste el nuevo empleado en la base de datos
	        em.persist(empleado);
	        em.persist(proyecto);
	        em.persist(departamento);
	        
	        departamento.addJefe(empleado);
	        empleado.addDepartamento(departamento);
	        empleado.addProyecto(proyecto);
	        
	        em.persist(departamento);
	        em.persist(empleado);
	
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
