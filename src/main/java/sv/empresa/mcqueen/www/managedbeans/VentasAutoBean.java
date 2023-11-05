package sv.empresa.mcqueen.www.managedbeans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import sv.empresa.mcqueen.www.entities.AutomovilesEntity;
import sv.empresa.mcqueen.www.entities.VentasautoEntity;
import sv.empresa.mcqueen.www.models.AutomovilesModel;
import sv.empresa.mcqueen.www.models.UsuariosModel;
import sv.empresa.mcqueen.www.models.VentasAutoModel;
import sv.empresa.mcqueen.www.utils.JsfUtil;

import java.util.List;
import java.util.Random;

@ManagedBean
@RequestScoped
public class VentasAutoBean {
    private VentasautoEntity ventaAuto;
    private AutomovilesEntity automovil;
    private VentasAutoModel modeloVenta = new VentasAutoModel();
    private AutomovilesModel modeloAutomovil = new AutomovilesModel();
    private UsuariosModel modeloUsuario = new UsuariosModel();

    private List<VentasautoEntity> listaVentas;
    private List<VentasautoEntity> listaMensajesVentas;

    public VentasAutoBean(){ventaAuto = new VentasautoEntity(); automovil = new AutomovilesEntity();}

    public void registrarVentaUsuario(){
        //settearemos los demas campos que requiere la venta
        ventaAuto.setUsuarioByIdCliente(modeloUsuario.obtenerUsuario(JsfUtil.getRequest().getParameter("idComprador")));
        ventaAuto.setAutomovilesByIdCarro(modeloAutomovil.obtenerAutomovil(JsfUtil.getRequest().getParameter("idAuto")));
        ventaAuto.setEstado(11);
        ventaAuto.setPrecio((int)Float.parseFloat(JsfUtil.getRequest().getParameter("precioVenta")));
        //crearemos el id para la venta
        crearIDVentaAuto(1);
        //registraremos la venta
        if (modeloVenta.registrarVentaAutomovilUsuario(ventaAuto) == 1){
            FacesContext.getCurrentInstance().addMessage("successMessage", new FacesMessage(FacesMessage.SEVERITY_INFO, "Ingresado la venta Exitosamente", "Registrado"));
        }else {
            FacesContext.getCurrentInstance().addMessage("successMessage", new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al enviar la venta ", "Error"));
        }
    }

    public void cambiarEstadoVenta(VentasautoEntity venta){
        String estadoAccion = JsfUtil.getRequest().getParameter("estadoAccion");

        if (venta.getEstado() == 11){ //si el estado esta en 11 que significa q la venta esta en modo de conversacion con el usuario interesado pasara dado caso sea asi pues pasa
            //evaluamos si aceptaremos o rechazaremos
            if (estadoAccion.equals("222")){
                venta.setEstado(12);
            }else {
                venta.setEstado(13);
            }
            if (venta.getEstado() == 12){ // aqui verificamos si es una cancelacion de venta por si no llegaron a un acuerdo
                if (modeloVenta.cambiarEstadoVenta(venta) == 1){ // validamos si cambia el estado

                    //Obtenemos el automovil para poder cambiar el estado a vendido para poder sacarlo de publicacin
                    automovil = modeloAutomovil.obtenerAutomovil(venta.getAutomovilesByIdCarro().getIdAutomovil());
                    automovil.setEstado(4);
                    if (modeloAutomovil.cambiarEstadoAutomovil(automovil) == 1){ // validamos que se cambie el estado
                       if (modeloVenta.borrarVentasFallidas(venta.getAutomovilesByIdCarro().getIdAutomovil()) == 1){ // aqui borramos las demas conversaciones de venta que hubieron anteriormente
                           FacesContext.getCurrentInstance().addMessage("successMessage", new FacesMessage(FacesMessage.SEVERITY_INFO, "Ventao Exitosa", "Venta"));
                       }
                    }
                }

            }else {
                if (modeloVenta.cambiarEstadoVenta(venta) == 1){ //dado caso la venta fue cancelada validamos que se cambie el estado a 13 q es venta fallida
                    FacesContext.getCurrentInstance().addMessage("successMessage", new FacesMessage(FacesMessage.SEVERITY_INFO, "Ventao Cancelada", "Venta"));

                }
            }
        }
    }

    public void crearIDVentaAuto(int tipo){
        Random rand = new Random();
        int numeroAleatorio = rand.nextInt(900) + 100;
        if (tipo == 1){
            ventaAuto.setIdVenta("VTU"+numeroAleatorio);
        }else {
            ventaAuto.setIdVenta("VTA"+numeroAleatorio);
        }
    }

    public void settearFormVentas(String idAuto){
        automovil = modeloAutomovil.obtenerAutomovil(idAuto);
    }


    //GETTER Y SETTER
    public List<VentasautoEntity> getListaVentas() {
        return modeloVenta.listarVentas();
    }
    public List<VentasautoEntity> getListaMensajesVentas(String dui) {
        return modeloVenta.listaMensajesVentaUser(dui);
    }

    public VentasautoEntity getVentaAuto() {
        return ventaAuto;
    }

    public void setVentaAuto(VentasautoEntity ventaAuto) {
        this.ventaAuto = ventaAuto;
    }

    public AutomovilesEntity getAutomovil() {
        return automovil;
    }

    public void setAutomovil(AutomovilesEntity automovil) {
        this.automovil = automovil;
    }
}
