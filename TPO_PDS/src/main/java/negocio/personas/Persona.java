// Paquete: negocio
package negocio.personas;
 

public abstract class Persona {
    protected String nombre;
    protected String apellido;
    protected int documento;
    protected String correoElectronico;
    protected String telefono;

    public Persona(String nombre, String apellido, int documento, String correoElectronico, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
    }
    
    // Geters y Seters 
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getDocumento() {
        return documento;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDocumento(int documento) {
        this.documento = documento;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
}


