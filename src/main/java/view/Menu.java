package view;

import java.util.List;

import controlador.Controlador;
import operacionesCRUD.*;
import io.IO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class Menu {

	public static void main(String[] args) {
		
		//un mostrar 
		EntityManager em = Controlador.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		
		OperacionesCRUD operacionesEmpleado = new OperacionesEmpleado();
		OperacionesCRUD operacionesDepartamento = new OperacionesDepartamento();
		OperacionesCRUD operacionesProyecto = new OperacionesProyecto();
		
		try {
			List<String> opciones = List
					.of("1. Empleado\n" + "2. Departamento\n" + "3. Proyecto\n" + "4. Salir");
			while (true) {
				System.out.println(opciones);
				switch (IO.readInt()) {
				case 1:
					menu(1,transaction, em, operacionesEmpleado, operacionesDepartamento, operacionesProyecto);
					break;
				case 2:
					menu(2,transaction, em, operacionesEmpleado, operacionesDepartamento, operacionesProyecto);
					break;
				case 3:
					menu(3,transaction, em, operacionesEmpleado, operacionesDepartamento, operacionesProyecto);
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

	private static void menu (int tipo, EntityTransaction transaction, EntityManager em, OperacionesCRUD empleado, OperacionesCRUD depa, OperacionesCRUD proy) {
		List<String> opciones = List.of("1. Insertar\n" + "2. Modificar\n" + "3. Eliminar\n" + "4. Buscar por codigo\n"
				+ "5. Buscar por nombre\n" + "6. Salir\n");
		while (true) {
			System.out.println(opciones);
			switch (IO.readInt()) {
			case 1:
				switch (tipo) {
				case 1:
					empleado.insertar(em,transaction);
					break;
				case 2:
					depa.insertar(em,transaction);
					break;
				case 3:
					proy.insertar(em,transaction);
					break;
				}
				break;
			case 2:
				switch (tipo) {
				case 1:
					empleado.modificar(em,transaction);
					break;
				case 2:
					depa.modificar(em,transaction);
					break;
				case 3:
					proy.modificar(em,transaction);
					break;
				}
				break;
			case 3:
				switch (tipo) {
				case 1:
					empleado.eliminar(em,transaction);
					break;
				case 2:
					depa.eliminar(em,transaction);
					break;
				case 3:
					proy.eliminar(em,transaction);
					break;
				}
				break;
			case 4:
				switch (tipo) {
				case 1:
					empleado.buscarPorCodigo(em);
					break;
				case 2:
					depa.buscarPorCodigo(em);
					break;
				case 3:
					proy.buscarPorCodigo(em);
					break;
				}
				break;
			case 5:
				switch (tipo) {
				case 1:
					empleado.buscarPorNombre(em);
					break;
				case 2:
					depa.buscarPorNombre(em);
					break;
				case 3:
					proy.buscarPorNombre(em);
					break;
				}
				break;
			case 6:
				return;
			}
		}
	}
}
