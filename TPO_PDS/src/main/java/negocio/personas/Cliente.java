package negocio.personas;

import java.io.Serializable;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private int dni;
    private String direccion;
    private String telefono;

    // Constructor con parámetros
    public Cliente(int id, String nombre, String apellido, String email, int dni, String direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.dni = dni;
        this.direccion = direccion;
        this.telefono = telefono;
    }
    //Constructor Vacio:
    public Cliente(){
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getDni() { return dni; }
    public void setDni(int dni) { this.dni = dni; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni=" + dni +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
