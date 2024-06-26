package sv.empresa.mcqueen.www.entities;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "usuario", schema = "mcqueenautoparts", catalog = "")
public class UsuarioEntity {
    @Id
    @Column(name = "dui", nullable = false, length = 10)
    private String dui;
    @Basic
    @Column(name = "telefono", nullable = false, length = 10)
    private String telefono;
    @Basic
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Basic
    @Column(name = "correo", nullable = false, length = 50)
    private String correo;
    @Basic
    @Column(name = "pass", nullable = false, length = 25)
    private String pass;
    @OneToMany(mappedBy = "usuarioByIdClienteVenta")
    private Collection<AutomovilesEntity> automovilesByDui;
    @OneToMany(mappedBy = "usuarioByIdCliente")
    private Collection<CitasmecEntity> citasmecsByDui;
    @OneToMany(mappedBy = "usuarioByIdCliente")
    private Collection<RentasEntity> rentasByDui;
    @OneToMany(mappedBy = "usuarioByIdCliente")
    private Collection<VentasautoEntity> ventasautosByDui;
    @OneToMany(mappedBy = "usuarioByIdCliente")
    private Collection<VentasrepuestosEntity> ventasrepuestosByDui;

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsuarioEntity that = (UsuarioEntity) o;

        if (dui != null ? !dui.equals(that.dui) : that.dui != null) return false;
        if (telefono != null ? !telefono.equals(that.telefono) : that.telefono != null) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (correo != null ? !correo.equals(that.correo) : that.correo != null) return false;
        if (pass != null ? !pass.equals(that.pass) : that.pass != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dui != null ? dui.hashCode() : 0;
        result = 31 * result + (telefono != null ? telefono.hashCode() : 0);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (correo != null ? correo.hashCode() : 0);
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        return result;
    }

    public Collection<AutomovilesEntity> getAutomovilesByDui() {
        return automovilesByDui;
    }

    public void setAutomovilesByDui(Collection<AutomovilesEntity> automovilesByDui) {
        this.automovilesByDui = automovilesByDui;
    }

    public Collection<CitasmecEntity> getCitasmecsByDui() {
        return citasmecsByDui;
    }

    public void setCitasmecsByDui(Collection<CitasmecEntity> citasmecsByDui) {
        this.citasmecsByDui = citasmecsByDui;
    }

    public Collection<RentasEntity> getRentasByDui() {
        return rentasByDui;
    }

    public void setRentasByDui(Collection<RentasEntity> rentasByDui) {
        this.rentasByDui = rentasByDui;
    }

    public Collection<VentasautoEntity> getVentasautosByDui() {
        return ventasautosByDui;
    }

    public void setVentasautosByDui(Collection<VentasautoEntity> ventasautosByDui) {
        this.ventasautosByDui = ventasautosByDui;
    }

    public Collection<VentasrepuestosEntity> getVentasrepuestosByDui() {
        return ventasrepuestosByDui;
    }

    public void setVentasrepuestosByDui(Collection<VentasrepuestosEntity> ventasrepuestosByDui) {
        this.ventasrepuestosByDui = ventasrepuestosByDui;
    }
}
