package model;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "empleado")

@Entity
@Table(name = "departamentos")
public class Departamento {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY )
	private Integer id;
	@Column(nullable = false)
	private String nombre;
	// Relación 1-N con Empleado, un departamento puede tener muchos empleados
    @OneToMany
	private Set<Empleado> empleado = new HashSet<>();
	
	public Departamento(Integer id,String nombre) {
		setId(id);
		setNombre(nombre);
	}
	
	public void addEmpleado(Empleado e) {
		if (e.getDepartamento() != null) {
			e.getDepartamento().getEmpleado().remove(e);
		}
		e.setDepartamento(this);
		empleado.add(e);
	}
	
	public void removeEmpleado(Empleado e) {
		e.setDepartamento(null);
		empleado.remove(e);
	}
}
