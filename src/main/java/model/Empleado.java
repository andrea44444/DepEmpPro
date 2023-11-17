package model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "empleados")
public class Empleado {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY )
	private Integer id;
	@Column(nullable = false)
	private String nombre;
	@Column(nullable = false)
	private Double salario;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private LocalDate fNacimiento;
	@ManyToOne
	@JoinColumn(name = "departamento_id") // Nombre de la columna en la tabla empleados que hace referencia al departamento
	private Departamento departamento;
	
	@ManyToMany
	@JoinTable(
		    name = "empleado_proyecto",
		    joinColumns = @JoinColumn(name = "empleado_id"),
		    inverseJoinColumns = @JoinColumn(name = "proyecto_id")
		)
	private Set<Proyecto> proyecto = new HashSet<>();
	
	public Empleado(Integer id, String nombre,Double salario, LocalDate fNacimiento) {
		setId(id);
		setNombre(nombre);
		setSalario(salario);
		setFNacimiento (fNacimiento);
	}
}
