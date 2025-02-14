package ventanasGUI.proveedor.proveedorDP;


public class Proveedor {
    private int idProveedor;
    private String nombre;
    private String ruc;
    private String contacto;
    private String fechaRegistro;
    private String registradoPor;

    // Constructor vacío
    public Proveedor() {}

    // Constructor con parámetros
    public Proveedor(int idProveedor, String nombre, String ruc, String contacto, String fechaRegistro, String registradoPor) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.ruc = ruc;
        this.contacto = contacto;
        this.fechaRegistro = fechaRegistro;
        this.registradoPor = registradoPor;
    }

    // Getters y setters
    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getRegistradoPor() {
        return registradoPor;
    }

    public void setRegistradoPor(String registradoPor) {
        this.registradoPor = registradoPor;
    }
}
