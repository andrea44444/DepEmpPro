package operacionesCRUD;

import java.util.List;

import io.IO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import model.Departamento;
import model.Empleado;

public class OperacionesDepartamento implements OperacionesCRUD{

	@Override
	public void insertar(EntityManager em, EntityTransaction transaction) {
		transaction.begin();
		IO.print("Nombre?");
		String nombre = IO.readString();
		em.persist(new Departamento(nombre));
		transaction.commit();
	}

	@Override
	public void modificar(EntityManager em, EntityTransaction transaction) {
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

	@Override
	public void eliminar(EntityManager em, EntityTransaction transaction) {
		IO.print("Id?");
        Integer id = IO.readInt();

        Departamento departamento = em.find(Departamento.class, id);

        if (departamento != null) {
        	transaction.begin();
        	//Eliminar rastros del departamento en la tabla empleados
        	departamento.getEmpleados().forEach(empleado -> {
        	    empleado.salirDelDepartamento();
        	});
        	transaction.commit();
        	
        	transaction.begin();
        	em.remove(departamento);
        	transaction.commit();
            System.out.println("Departamento eliminado exitosamente.");
        } else {
        	IO.println("No se encontró un departamento con el ID proporcionado.");
        }
	}

	@Override
	public void buscarPorCodigo(EntityManager em) {
		IO.println("Cod?");
		Departamento departamento = em.find(Departamento.class, IO.readInt());
		if(departamento != null) {
			IO.println(departamento.toString());
		}else {
			IO.println("No se encontró un departamento con el ID proporcionado.");
		}
	}

	@Override
	public void buscarPorNombre(EntityManager em) {
		IO.println("Nombre del departamento?");
	    String nombre = IO.readString();

	    TypedQuery<Departamento> query = em.createQuery("SELECT p FROM Departamento p WHERE p.nombre LIKE :nombre", Departamento.class);
	    query.setParameter("nombre", "%" + nombre + "%");

	    List<Departamento> departamentos = query.getResultList();

	    if (!departamentos.isEmpty()) {
	        for (Departamento departamento : departamentos) {
	            IO.println(departamento.toString());
	        }
	    } else {
	        IO.println("No se encontró ningún departamento con el nombre proporcionado.");
	    }
	}
	
	@Override
	public void mostrar(EntityManager em) {
		TypedQuery<Departamento> query = em.createQuery("SELECT d FROM Departamento d", Departamento.class);
		
		List<Departamento> departamentos = query.getResultList();

	    if (!departamentos.isEmpty()) {
	        for (Departamento departamento : departamentos) {
	            IO.println(departamento.toString());
	        }
	    } else {
	        IO.println("No se encontraron datos que mostrar");
	    }
	}

}
