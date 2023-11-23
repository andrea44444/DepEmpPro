package view;

import java.time.LocalDate;
import java.util.List;

import io.IO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
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
	
	public static void modificarEmpleado(EntityManager em, EntityTransaction transaction) {
		IO.print("Id?");
        Integer id = IO.readInt();

        // Busca el empleado por ID
        Empleado empleado = em.find(Empleado.class, id);

        if (empleado != null) {

            // Modifica Nombre
            IO.print("Nuevo nombre?");
            String nuevoNombre = IO.readString();
            if(!nuevoNombre.isEmpty()) {
            	transaction.begin();
            	empleado.setNombre(nuevoNombre);
            	transaction.commit();
            }

            // Modifica Salario
            IO.print("Modificar salario?");
            if(!IO.readString().isEmpty()) {
	            IO.print("Nuevo salario?");
	            Double nuevoSalario = IO.readDouble();
	            transaction.begin();
	            empleado.setSalario(nuevoSalario);
	            transaction.commit();     
            }
            
            //Modificar FechaNacimiento
            IO.print("Modificar fecha?");
            if(IO.readString()!= "") {
            	IO.print("Nueva fecha?");
            	LocalDate nuevaFecha = IO.readLocalDate();
            	transaction.begin();
            	empleado.setFNacimiento(nuevaFecha);
            	transaction.commit();
            }
            
          //Modificar Departamento
            IO.print("Modificar departamento?");
            if(!IO.readString().isEmpty()) {
            	IO.print("Nuevo departamento?");
            	int nuevoDepartamento = IO.readInt();
            	Departamento departamento = em.find(Departamento.class, nuevoDepartamento);
            	if (departamento != null) {
            		transaction.begin();
            		empleado.salirDelDepartamento();
            		transaction.commit();
            		
            		transaction.begin();
                	empleado.addDepartamento(departamento);
                	transaction.commit();
            	} else {
            		IO.println("No se encontró un departamento con el ID proporcionado.");
            	}
            }
            
          //Modificar Proyecto
            IO.print("Modificar proyecto?");
            if(!IO.readString().isEmpty()) {
            	IO.println("1.Eliminar	2.Añadir");
            	int eleccion = IO.readInt();
            	if(eleccion == 1 || eleccion == 2) {
            		IO.println("Id del proyecto?");
	            	int idProyecto = IO.readInt();
	            	Proyecto proyecto = em.find(Proyecto.class, idProyecto);
	            	//System.out.println("proyecto ->"+proyecto);
	            	if(proyecto != null) {
	            		transaction.begin();
	            		if(eleccion == 1) {
	            			empleado.salirDelProyecto(proyecto);
	            		}else {
	            			empleado.addProyecto(proyecto);
	            		}
	            		transaction.commit();
	            	}else {
	            		IO.println("No se encontró un proyecto con el ID proporcionado.");
	            	}
            	}else {
            		IO.println("Indice incorrecto");
            	}
            	
            }    
            IO.println("Empleado modificado exitosamente.");
        } else {
            IO.println("No se encontró un empleado con el ID proporcionado.");
        }
	}
	
	public static void eliminarEmpleado(EntityManager em, EntityTransaction transaction) {
		IO.print("Id?");
        Integer id = IO.readInt();

        // Busca el empleado por ID
        Empleado empleado = em.find(Empleado.class, id);

        if (empleado != null) {
        	//
        	transaction.begin();
        	//Poner a null si era jefe de algun departamento
        	empleado.getDepartamentoJefe().deleteJefe(empleado);
        	//eliminarlo si formaba parte de algun proyecto
        	empleado.getProyecto().forEach(proyecto -> {    
        		empleado.salirDelProyecto(proyecto);
        		System.out.println("ss");
        		IO.readString();
        	});
        	transaction.commit();
        	
        	transaction.begin();
        	em.remove(empleado);
        	transaction.commit();
            System.out.println("Empleado eliminado exitosamente.");
        } else {
        	IO.println("No se encontró un empleado con el ID proporcionado.");
        }
	}
	
	public static <T> T buscarCod(int id, Class<T> clase, EntityManager em) {
		return em.find(clase, id);
	}
	
	public static <T> List<T> buscarPorNombre(String nombre, Class<T> clase, EntityManager em) {
	    String query = "SELECT e FROM " + clase.getSimpleName() + " e WHERE e.nombre = :nombre";
	    TypedQuery<T> typedQuery = em.createQuery(query, clase);
	    typedQuery.setParameter("nombre", nombre);
	    return typedQuery.getResultList();
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
	
	public static void modificarDepartamento(EntityManager em, EntityTransaction transaction) {
		IO.print("Id?");
        Integer id = IO.readInt();

        // Busca el departamento por ID
        Departamento departamento = em.find(Departamento.class, id);

        if (departamento != null) {
            
            // Modifica el nombre del departamento
            IO.print("Nuevo nombre?");
            String nuevoNombre = IO.readString();
            if (!nuevoNombre.isEmpty()) {  	
            	transaction.begin();
            	departamento.setNombre(nuevoNombre);
            	transaction.commit();
            }

            // Modifica el jefe del departamento(NO FUNCIONA BIEN)
            IO.println("Modificar jefe?");
            if(!IO.readString().isEmpty()) {
            	IO.println("Id del nuevo jefe?" );
                int idJefe = IO.readInt();
                Empleado nuevoJefe = em.find(Empleado.class, idJefe);
                if(nuevoJefe != null) {
                    transaction.begin();
                    departamento.deleteJefe(nuevoJefe); 
                    em.merge(departamento);
                    transaction.commit();
                System.out.println("seguir?");
                IO.readString();
                    transaction.begin();
                    departamento.addJefe(nuevoJefe);
                    em.merge(departamento);
                    transaction.commit();
                } else {
                    IO.println("No se encontró un empleado con el ID proporcionado.");
                }      
            }
        
        IO.println("Departamento modificado exitosamente.");
            
        } else {
            IO.println("No se encontró un departamento con el ID proporcionado.");
        }
	}
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
	
	public static void modificarProyecto(EntityManager em, EntityTransaction transaction) {
		IO.print("Id?");
        Integer id = IO.readInt();

        // Busca el departamento por ID
        Proyecto proyecto = em.find(Proyecto.class, id);

        if (proyecto != null) {
            // Modifica el nombre del proyecto
            IO.print("Nuevo nombre?");
            String nuevoNombre = IO.readString();
            if(!nuevoNombre.isEmpty()) {
            	transaction.begin();
            	proyecto.setNombre(nuevoNombre);
            	transaction.commit();
            }
       
        } else {
            IO.println("Ese proyecto no existe");
        }
	}
	
	public static void eliminarProyecto() {}
	public static void buscarNomProyecto() {}
}
