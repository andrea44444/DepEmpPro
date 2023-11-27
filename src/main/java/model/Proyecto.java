package model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "empleado")

@Entity
@Table(name = "proyectos")
public class Proyecto {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY )
	private Integer id;
	@Column(nullable = false)
	private String nombre;
	
	@ManyToMany
	@JoinTable(
	        name = "empleado_proyecto", 
	        joinColumns = @JoinColumn(name = "proyecto_id"), 
	        inverseJoinColumns = @JoinColumn(name = "empleado_id") 
	    )
	private Set<Empleado> empleado = new HashSet<>();

	public Proyecto(String nombre) {
		setNombre(nombre);
	}

	@Override
	public String toString() {
		return "Proyecto [id=" + id + ", nombre=" + nombre + "]";
	}
	
	
}
