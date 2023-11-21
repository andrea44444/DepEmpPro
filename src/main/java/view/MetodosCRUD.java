package view;

import java.time.LocalDate;

import io.IO;
import model.Empleado;

public class MetodosCRUD {
	public static Empleado insertarEmpleado() {
		IO.print("Nombre?");
		String nombre = IO.readString();
		IO.print("Salario?");
		Double salario = IO.readDouble();
		IO.print("Fecha nacimiento ? (YYYY-MM-DD)");
		LocalDate fechaNacimiento= IO.readLocalDate();
		return new Empleado(nombre,salario,fechaNacimiento);
	}
	public static void modificarEmpleado() {}
	public static void eliminarEmpleado() {}
	public static void buscarCodEmpleado() {}
	public static void buscarNomEmpleado() {}
	
	public static void insertarDepartamento() {}
	public static void modificarDepartamento() {}
	public static void eliminarDepartamento() {}
	public static void buscarCodDepartamento() {}
	public static void buscarNomDepartamento() {}
	
	public static void insertarProyecto() {}
	public static void modificarProyecto() {}
	public static void eliminarProyecto() {}
	public static void buscarCodProyecto() {}
	public static void buscarNomProyecto() {}
}
