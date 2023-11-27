package operacionesCRUD;

import java.util.List;

import io.IO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.Proyecto;

public class OperacionesProyecto implements OperacionesCRUD{

	@Override
	public void insertar(EntityManager em, EntityTransaction transaction) {
		transaction.begin();
		IO.print("Nombre?");
		String nombre = IO.readString();
		em.persist(new Proyecto(nombre));
		transaction.commit();
	}

	@Override
	public void modificar(EntityManager em, EntityTransaction transaction) {
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

	@Override
	public void eliminar(EntityManager em, EntityTransaction transaction) {
		IO.print("Id?");
        Integer id = IO.readInt();

        Proyecto proyecto = em.find(Proyecto.class, id);

        if (proyecto != null) {
        	transaction.begin();
        	//Eliminar rastros del proyecto en la tabla empleados
        	proyecto.getEmpleado().forEach(empleado -> {
        	    empleado.salirDelProyecto(proyecto);
        	});
        	transaction.commit();
        	
        	transaction.begin();
        	em.remove(proyecto);
        	transaction.commit();
            System.out.println("Proyecto eliminado exitosamente.");
        } else {
        	IO.println("No se encontró un proyecto con el ID proporcionado.");
        }
	}

	@Override
	public void buscarPorCodigo(EntityManager em) {
		IO.println("Cod?");
		Proyecto proyecto = em.find(Proyecto.class, IO.readInt());
		if(proyecto != null) {
			IO.println(proyecto.toString());
		}else {
			IO.println("No se encontró un proyecto con el ID proporcionado.");
		}
	}

	@Override
	public void buscarPorNombre(EntityManager em) {
		IO.println("Nombre del proyecto?");
	    String nombre = IO.readString();

	    TypedQuery<Proyecto> query = em.createQuery("SELECT p FROM Proyecto p WHERE p.nombre LIKE :nombre", Proyecto.class);
	    query.setParameter("nombre", "%" + nombre + "%");

	    List<Proyecto> proyectos = query.getResultList();

	    if (!proyectos.isEmpty()) {
	        for (Proyecto proyecto : proyectos) {
	            IO.println(proyecto.toString());
	        }
	    } else {
	        IO.println("No se encontró ningún proyecto con el nombre proporcionado.");
	    }
	}

}
