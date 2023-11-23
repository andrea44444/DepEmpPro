package model;

import java.util.HashSet;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.Nullable;

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
    
    @Nullable
    @OneToOne//(mappedBy = "departamentoJefe")
	private Empleado jefe;
    
	public Departamento(String nombre) {
		setNombre(nombre);
	}
	
	public void addJefe(Empleado jefe) {
		this.setJefe(jefe);
		jefe.setDepartamento(this);
	}
	
	public void deleteJefe(Empleado jefe) {
		if (jefe != null && jefe.getDepartamento() != null && jefe.getDepartamento().getJefe() != null) {
	        Departamento departamentoJefeActual = jefe.getDepartamento();
	        departamentoJefeActual.setJefe(null);
	    }
	}
}
