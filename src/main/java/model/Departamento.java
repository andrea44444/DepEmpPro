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
@EqualsAndHashCode(exclude = {"empleados", "jefe"})

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
	private Set<Empleado> empleados = new HashSet<>();
    
    @Nullable
    @OneToOne
    @JoinColumn(name = "jefe_id") 
    private Empleado jefe;
    
	public Departamento(String nombre) {
		setNombre(nombre);
	}
	
	public void addJefe(Empleado jefe) {
        this.setJefe(jefe);
        jefe.setDepartamento(this);
        jefe.setDepartamentoJefe(this);
    }

    public void deleteJefe(Empleado jefe) {
        if (this.getJefe() != null) {
        	this.getJefe().setDepartamento(null);
            this.getJefe().setDepartamentoJefe(null);
            this.setJefe(null);
        } else if (jefe.getDepartamentoJefe()!= null) {
        	jefe.setDepartamentoJefe(null);
            jefe.getDepartamentoJefe().setJefe(null);
        }else if(jefe.getDepartamento()!= null) {
        	jefe.setDepartamento(null);
        }
    }

	@Override
	public String toString() {
		if(this.getJefe() != null) {
			return "Departamento [id=" + id + ", nombre=" + nombre + ", \n\templeados=" + empleados + ", \n\tjefe = " + jefe.getNombre()+" - Id= "+ jefe.getId() + "]";
		} else {
			return "Departamento [id=" + id + ", nombre=" + nombre + ", empleados=" + empleados + ", jefe=" + jefe + "]";
		}
	}
   
}
