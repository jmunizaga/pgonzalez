/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jaime Munizaga L. <jaime.munizaga.l@gmail.com>
 */
@Entity
@Table(name = "atencion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Atencion.findAll", query = "SELECT a FROM Atencion a"),
    @NamedQuery(name = "Atencion.findById", query = "SELECT a FROM Atencion a WHERE a.id = :id"),
    @NamedQuery(name = "Atencion.findByFechaAtencion", query = "SELECT a FROM Atencion a WHERE a.fechaAtencion = :fechaAtencion"),
    @NamedQuery(name = "Atencion.findByDiagnostico", query = "SELECT a FROM Atencion a WHERE a.diagnostico = :diagnostico"),
    @NamedQuery(name = "Atencion.findByTratamiento", query = "SELECT a FROM Atencion a WHERE a.tratamiento = :tratamiento"),
    @NamedQuery(name = "Atencion.findByObservacion", query = "SELECT a FROM Atencion a WHERE a.observacion = :observacion")})
public class Atencion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_atencion")
    @Temporal(TemporalType.DATE)
    private Date fechaAtencion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "diagnostico")
    private String diagnostico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "tratamiento")
    private String tratamiento;
    @Size(max = 200)
    @Column(name = "observacion")
    private String observacion;
    @JoinColumn(name = "ficha_id_FK", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ficha fichaidFK;
    @JoinColumn(name = "veterinario_rut_FK", referencedColumnName = "rut")
    @ManyToOne(optional = false)
    private Veterinario veterinariorutFK;

    public Atencion() {
    }

    public Atencion(Integer id) {
        this.id = id;
    }

    public Atencion(Integer id, Date fechaAtencion, String diagnostico, String tratamiento) {
        this.id = id;
        this.fechaAtencion = fechaAtencion;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(Date fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Ficha getFichaidFK() {
        return fichaidFK;
    }

    public void setFichaidFK(Ficha fichaidFK) {
        this.fichaidFK = fichaidFK;
    }

    public Veterinario getVeterinariorutFK() {
        return veterinariorutFK;
    }

    public void setVeterinariorutFK(Veterinario veterinariorutFK) {
        this.veterinariorutFK = veterinariorutFK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Atencion)) {
            return false;
        }
        Atencion other = (Atencion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Atencion[ id=" + id + " ]";
    }
    
}
