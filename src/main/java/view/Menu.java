package view;

import java.util.List;

import controlador.Controlador;
import io.IO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Departamento;
import model.Empleado;
import model.Proyecto;

public class Menu {

	public static void main(String[] args) {
		
		//Mostrar, modificar jefe, cambiar find, dividir crud
		//si un jefe modifica su departamento deja de ser jefe
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
					IO.println("Cod?");
					Empleado empleado = MetodosCRUD.buscarCod(IO.readInt(),Empleado.class, em);
					IO.println(empleado.getNombre());
					break;
				case 2:
					IO.println("Cod?");
					Departamento departamento = MetodosCRUD.buscarCod(IO.readInt(),Departamento.class, em);
					IO.println(departamento.getNombre());
					break;
				case 3:
					IO.println("Cod?");
					Proyecto proyecto = MetodosCRUD.buscarCod(IO.readInt(),Proyecto.class, em);
					IO.println(proyecto.getNombre());
					break;
				}
				break;
			case 5:
				switch (tipo) {
				case 1:
					IO.println("Nombre?");
					List<Empleado> listaEmpleados = MetodosCRUD.buscarPorNombre(IO.readString(),Empleado.class, em);
					for (Empleado empleado : listaEmpleados) {
						IO.println(empleado.getId());
					}
					break;
				case 2:
					IO.println("Nombre?");
					List<Departamento> listaDepartamentos = MetodosCRUD.buscarPorNombre(IO.readString(),Departamento.class, em);
					for (Departamento departamento : listaDepartamentos) {
						IO.println(departamento.getId());
					}
					break;
				case 3:
					IO.println("Nombre?");
					List<Proyecto> listaProyectos = MetodosCRUD.buscarPorNombre(IO.readString(),Proyecto.class, em);
					for (Proyecto proyecto : listaProyectos) {
						IO.println(proyecto.getId());
					}
					break;
				}
				break;
			case 6:
				return;
			}
		}
	}
}
