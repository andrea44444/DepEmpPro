package view;

import java.time.LocalDate;
import java.util.List;

import controlador.Controlador;
import io.IO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Departamento;
import model.Empleado;
import model.Proyecto;
import view.MetodosCRUD;

public class Menu {

	public static void main(String[] args) {
		// EntityManager em = Controlador.getEntityManager();
		// EntityTransaction transaction = em.getTransaction();

		List<String> opciones = List
				.of("1. Modificar empleado\n" + "2. Modificar departamento\n" + "3. Modificar proyecto\n" + "4. Salir");
		while (true) {
			System.out.println(opciones);
			switch (IO.readInt()) {
			case 1:
				menu(1);
				break;
			case 2:
				menu(2);
				break;
			case 3:
				menu(3);
				break;
			case 4:
				return;
			}
		}
	}

	private static void menu(int tipo) {
		List<String> opciones = List.of("1. Insertar\n" + "2. Modificar\n" + "3. Eliminar\n" + "4. Buscar por codigo\n"
				+ "5. Buscar por nombre\n" + "6. Salir\n");
		while (true) {
			System.out.println(opciones);
			switch (IO.readInt()) {
			case 1:
				switch (tipo) {
				case 1:
					MetodosCRUD.insertarEmpleado();
					break;
				case 2:
					MetodosCRUD.insertarDepartamento();
					break;
				case 3:
					MetodosCRUD.insertarProyecto();
					break;
				}
				break;
			case 2:
				switch (tipo) {
				case 1:
					MetodosCRUD.modificarEmpleado();
					break;
				case 2:
					MetodosCRUD.modificarDepartamento();
					break;
				case 3:
					MetodosCRUD.modificarProyecto();
					break;
				}
				break;
			case 3:
				switch (tipo) {
				case 1:
					
					MetodosCRUD.eliminarEmpleado();
					break;
				case 2:
					MetodosCRUD.eliminarDepartamento();
					break;
				case 3:
					MetodosCRUD.eliminarProyecto();
					break;
				}
				break;
			case 4:
				switch (tipo) {
				case 1:
					MetodosCRUD.buscarCodEmpleado();
					break;
				case 2:
					MetodosCRUD.buscarCodDepartamento();
					break;
				case 3:
					MetodosCRUD.buscarCodProyecto();
					break;
				}
				break;
			case 5:
				switch (tipo) {
				case 1:
					MetodosCRUD.buscarNomEmpleado();
					break;
				case 2:
					MetodosCRUD.buscarNomDepartamento();
					break;
				case 3:
					MetodosCRUD.buscarNomProyecto();
					break;
				}
				break;
			case 6:
				return;
			}
		}
	}
	
	/*
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
	}*/
}
