package view;

import java.util.List;

import controlador.Controlador;
import io.IO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class Menu {

	public static void main(String[] args) {
		
		//Dividir MetodosCRUD y un mostrar 
		EntityManager em = Controlador.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		
		try {
			List<String> opciones = List
					.of("1. Empleado\n" + "2. Departamento\n" + "3. Proyecto\n" + "4. Salir");
			while (true) {
				System.out.println(opciones);
				switch (IO.readInt()) {
				case 1:
					menu(1,transaction, em);
					break;
				case 2:
					menu(2,transaction, em);
					break;
				case 3:
					menu(3,transaction, em);
					break;
				case 4:
					return;
				}
			}
		} catch(Exception e) {
			if (transaction.isActive()) {
	            transaction.rollback();
	        }
			IO.println("ERROR");
	        e.printStackTrace();
		} finally {
			em.close(); 
			Controlador.closeEntityManagerFactory();
		}
	}

	private static void menu(int tipo, EntityTransaction transaction, EntityManager em) {
		List<String> opciones = List.of("1. Insertar\n" + "2. Modificar\n" + "3. Eliminar\n" + "4. Buscar por codigo\n"
				+ "5. Buscar por nombre\n" + "6. Salir\n");
		while (true) {
			System.out.println(opciones);
			switch (IO.readInt()) {
			case 1:
				switch (tipo) {
				case 1:
					MetodosCRUD.insertarEmpleado(em,transaction);
					break;
				case 2:
					MetodosCRUD.insertarDepartamento(em,transaction);
					break;
				case 3:
					MetodosCRUD.insertarProyecto(em,transaction);
					break;
				}
				break;
			case 2:
				switch (tipo) {
				case 1:
					MetodosCRUD.modificarEmpleado(em,transaction);
					break;
				case 2:
					MetodosCRUD.modificarDepartamento(em,transaction);
					break;
				case 3:
					MetodosCRUD.modificarProyecto(em,transaction);
					break;
				}
				break;
			case 3:
				switch (tipo) {
				case 1:
					
					MetodosCRUD.eliminarEmpleado(em,transaction);
					break;
				case 2:
					MetodosCRUD.eliminarDepartamento(em,transaction);
					break;
				case 3:
					MetodosCRUD.eliminarProyecto(em,transaction);
					break;
				}
				break;
			case 4:
				switch (tipo) {
				case 1:
					MetodosCRUD.buscarCodEmpleado(em);
					break;
				case 2:
					MetodosCRUD.buscarCodDepartamento(em);
					break;
				case 3:
					MetodosCRUD.buscarCodProyecto(em);
					break;
				}
				break;
			case 5:
				switch (tipo) {
				case 1:
					MetodosCRUD.buscarNomEmpleado(em);
					break;
				case 2:
					MetodosCRUD.buscarNomDepartamento(em);
					break;
				case 3:
					MetodosCRUD.buscarNomProyecto(em);
					break;
				}
				break;
			case 6:
				return;
			}
		}
	}
}
