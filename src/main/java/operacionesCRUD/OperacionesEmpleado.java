package operacionesCRUD;

import java.time.LocalDate;
import java.util.List;

import io.IO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.Departamento;
import model.Empleado;
import model.Proyecto;

public class OperacionesEmpleado implements OperacionesCRUD{
	
	@Override
    public void insertar(EntityManager em, EntityTransaction transaction) {
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
    
    @Override
    public void modificar(EntityManager em, EntityTransaction transaction) {
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
    
    @Override
    public void eliminar(EntityManager em, EntityTransaction transaction) {
    	IO.print("Id?");
        Integer id = IO.readInt();

        // Busca el empleado por ID
        Empleado empleado = em.find(Empleado.class, id);

        if (empleado != null) {
        	
        	transaction.begin();
        	//Poner a null si era jefe de algun departamento
        	if(empleado.getDepartamento().getJefe() == empleado) {
        		empleado.getDepartamento().deleteJefe(empleado);
        	}
        	
        	// Quitar empleado del departamento
            empleado.salirDelDepartamento();
        	
        	//eliminarlo si formaba parte de algun proyecto
        	empleado.getProyecto().forEach(proyecto -> {    
        		empleado.salirDelProyecto(proyecto);
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
    
    @Override
    public void buscarPorCodigo(EntityManager em) {
    	IO.println("Cod?");
		Empleado empleado = em.find(Empleado.class, IO.readInt());
		if(empleado != null) {
			IO.println(empleado.toString());
		}else {
			IO.println("No se encontró un empleado con el ID proporcionado.");
		}
    }
    
    @Override
    public void buscarPorNombre(EntityManager em) {
    	IO.println("Nombre del empleado?");
	    String nombre = IO.readString();

	    TypedQuery<Empleado> query = em.createQuery("SELECT p FROM Empleado p WHERE p.nombre LIKE :nombre", Empleado.class);
	    query.setParameter("nombre", "%" + nombre + "%");

	    List<Empleado> empleados = query.getResultList();

	    if (!empleados.isEmpty()) {
	        for (Empleado empleado : empleados) {
	            IO.println(empleado.toString());
	        }
	    } else {
	        IO.println("No se encontró ningún proyecto con el nombre proporcionado.");
	    }
    }
}
