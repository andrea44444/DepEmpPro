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
	private Departamento departamento;
	
	@OneToOne(mappedBy = "jefe")
    private Departamento departamentoJefe;
	
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
	
	public void addDepartamento(Departamento d) {
		this.setDepartamento(d);
		d.getEmpleado().add(this);
	}
	
	public void addProyecto(Proyecto p) {
		this.getProyecto().add(p);
		p.getEmpleado().add(this);
	}
}
