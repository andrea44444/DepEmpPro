package model;

import java.time.LocalDate;
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
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id;
	private String nombre;
	private Double salario;
	
	
	@Temporal(TemporalType.DATE)
	private LocalDate fNacimiento;
	@ManyToOne
	@JoinColumn(name = "departamento_id") // Nombre de la columna en la tabla empleados que hace referencia al departamento
	private Departamento departamento;
	
	public Empleado(Integer id, String nombre,Double salario, LocalDate fNacimiento) {
		setId(id);
		setNombre(nombre);
		setSalario(salario);
		setFNacimiento (fNacimiento);
	}
}
