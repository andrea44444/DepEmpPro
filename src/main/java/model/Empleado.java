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
	
	@ManyToMany
	@JoinTable(
		    name = "empleado_proyecto",
		    joinColumns = @JoinColumn(name = "empleado_id"),
		    inverseJoinColumns = @JoinColumn(name = "proyecto_id")
		)
	private Set<Proyecto> proyecto = new HashSet<>();
	
	@OneToOne(mappedBy = "jefe")
    private Departamento departamentoJefe;
	
	public Empleado(String nombre,Double salario, LocalDate fNacimiento) {
		setNombre(nombre);
		setSalario(salario);
		setFNacimiento (fNacimiento);
	}
	
	public void addDepartamento(Departamento d) {
		this.setDepartamento(d);
		d.getEmpleados().add(this);
	}
	
	public void salirDelDepartamento() {
		Departamento departamentoActual = this.getDepartamento();
        if (departamentoActual != null) {
            departamentoActual.getEmpleados().remove(this);
            this.setDepartamento(null);
        }
        if(this.getDepartamentoJefe()!= null) {
        	this.getDepartamentoJefe().deleteJefe(this);
        	this.setDepartamentoJefe(null); 
        }
        
	}
    
	public void addProyecto(Proyecto p) {
		//this.getProyecto().add(p);  si pongo esto me lo añade dos veces
		p.getEmpleado().add(this);
	}
	
	public void salirDelProyecto(Proyecto p) {
		this.getProyecto().remove(p);
		//p.getEmpleado().remove(this);   da error intentar borrar asi
	}

	@Override
	public String toString() {
		if(this.getDepartamento() != null) {
			return "Empleado [id=" + id + ", nombre=" + nombre + ", salario=" + salario + ", fNacimiento=" + fNacimiento
				+ ", departamento=" + departamento.getNombre() + "]";
		} else {
			return "Empleado [id=" + id + ", nombre=" + nombre + ", salario=" + salario + ", fNacimiento=" + fNacimiento
					+ ", departamento= null]";
		}
	}
	
	
}
