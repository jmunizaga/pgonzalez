/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jaime Munizaga L. <jaime.munizaga.l@gmail.com>
 */
@Entity
@Table(name = "ficha")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ficha.findAll", query = "SELECT f FROM Ficha f"),
    @NamedQuery(name = "Ficha.findById", query = "SELECT f FROM Ficha f WHERE f.id = :id"),
    @NamedQuery(name = "Ficha.findByFechaCreacion", query = "SELECT f FROM Ficha f WHERE f.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Ficha.findByPeso", query = "SELECT f FROM Ficha f WHERE f.peso = :peso"),
    @NamedQuery(name = "Ficha.findByTama\u00f1o", query = "SELECT f FROM Ficha f WHERE f.tama\u00f1o = :tama\u00f1o")})
public class Ficha implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "peso")
    private float peso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tama\u00f1o")
    private int tamaño;
    @JoinColumn(name = "mascota_id_FK", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Mascota mascotaidFK;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fichaidFK")
    private Collection<Atencion> atencionCollection;

    public Ficha() {
    }

    public Ficha(Integer id) {
        this.id = id;
    }

    public Ficha(Integer id, Date fechaCreacion, float peso, int tamaño) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.peso = peso;
        this.tamaño = tamaño;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public Mascota getMascotaidFK() {
        return mascotaidFK;
    }

    public void setMascotaidFK(Mascota mascotaidFK) {
        this.mascotaidFK = mascotaidFK;
    }

    @XmlTransient
    public Collection<Atencion> getAtencionCollection() {
        return atencionCollection;
    }

    public void setAtencionCollection(Collection<Atencion> atencionCollection) {
        this.atencionCollection = atencionCollection;
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
        if (!(object instanceof Ficha)) {
            return false;
        }
        Ficha other = (Ficha) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Ficha[ id=" + id + " ]";
    }
    
}
