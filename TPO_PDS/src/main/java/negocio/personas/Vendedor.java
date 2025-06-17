package negocio.personas;

public class Vendedor extends Persona {
    private int Legajo;

    public Vendedor(String nombre, String apellido, int documento, String correoElectronico, String telefono, int Legajo) {
        super(nombre, apellido, documento, correoElectronico, telefono);
        this.Legajo = Legajo;
    }

    // Getters y Setters
    public int getId() { return Legajo; }
    public void setId(int Legajo) { this.Legajo = Legajo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public int getDni() { return documento; }
    public void setDni(int documento) { this.documento = documento; }

    public String getEmail() { return correoElectronico; }
    public void setEmail(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return "Vendedor{" +
                "Legajo=" + Legajo +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", documento=" + documento +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}