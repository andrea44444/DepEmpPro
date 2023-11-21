package view;

import java.time.LocalDate;

import io.IO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Departamento;
import model.Empleado;
import model.Proyecto;

public class MetodosCRUD {
	
	//EMPLEADO
	public static void insertarEmpleado(EntityManager em, EntityTransaction transaction) {
		transaction.begin();
		IO.print("Nombre?");
		String nombre = IO.readString();
		IO.print("Salario?");
		Double salario = IO.readDouble();
		IO.print("Fecha nacimiento ? (YYYY-MM-DD)");
		LocalDate fechaNacimiento= IO.readLocalDate();
		em.persist(new Empleado(nombre,salario,fechaNacimiento));
		transaction.commit();
	}
	public static void modificarEmpleado() {}
	public static void eliminarEmpleado() {}
	
	public static <T> T buscarCod(int id, Class<T> clase, EntityManager em) {
		return em.find(clase, id);
	}
	
	public static <T> T buscarCodUsuario(Class<T> clase, EntityManager em) {
		IO.print("Cod?");
		return buscarCod(IO.readInt(),clase,em);
	}
	
	public static void buscarNomEmpleado() {}
	
	
	//DEPARTAMENTO
	public static void insertarDepartamento(EntityManager em, EntityTransaction transaction) {
		transaction.begin();
		IO.print("Nombre?");
		String nombre = IO.readString();
		em.persist(new Departamento(nombre));
		transaction.commit();
	}
	
	public static void modificarDepartamento() {}
	public static void eliminarDepartamento() {}
	public static void buscarNomDepartamento() {}
	
	
	//PROYECTO
	public static void insertarProyecto(EntityManager em, EntityTransaction transaction) {
		transaction.begin();
		IO.print("Nombre?");
		String nombre = IO.readString();
		em.persist(new Proyecto(nombre));
		transaction.commit();
	}
	public static void modificarProyecto() {}
	public static void eliminarProyecto() {}
	public static void buscarNomProyecto() {}
}
