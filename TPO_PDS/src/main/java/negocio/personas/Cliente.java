package negocio.personas;

public class Cliente extends Persona {
    private int idCliente;

    // Constructor con parámetros
    public Cliente(String nombre, String apellido, String documento, String correoElectronico, String telefono, int idCliente) {
        super(nombre, apellido, documento, correoElectronico, telefono);
        this.idCliente = idCliente;
    }

    // Getters y Setters
    public int getId() { return idCliente; }
    public void setId(int idCliente) { this.idCliente = idCliente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getDni() { return documento; }
    public void setDni(String documento) { this.documento = documento; }

    public String getEmail() { return correoElectronico; }
    public void setEmail(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", documento=" + documento +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
