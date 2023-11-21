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
@EqualsAndHashCode(exclude = {"empleado", "jefe"})

@Entity
@Table(name = "departamentos")
public class Departamento {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY )
	private Integer id;
	@Column(nullable = false)
	private String nombre;
	
	// Relación 1-N con Empleado, un departamento puede tener muchos empleados
    @OneToMany(mappedBy="departamento")
	private Set<Empleado> empleado = new HashSet<>();
    
    @OneToOne//(mappedBy = "departamentoJefe")
	private Empleado jefe;
    
	public Departamento(Integer id,String nombre) {
		setId(id);
		setNombre(nombre);
	}
	/*
	public void addEmpleado(Empleado e) {
		this.getEmpleado().add(e);
		e.setDepartamento(this);
	}
	*/
	public void addJefe(Empleado jefe) {
		this.setJefe(jefe);
		jefe.setDepartamento(this);
	}
}
