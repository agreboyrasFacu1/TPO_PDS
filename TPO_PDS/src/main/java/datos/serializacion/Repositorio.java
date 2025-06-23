package datos.serializacion;

import java.util.List;

import excepciones.ElementoYaExiste;


public interface Repositorio<T> {
    public void agregar(T objeto) throws ElementoYaExiste;
    public void eliminar(int cod);
    public T obtenerObj(int cod);
    public List<T> obtenerTodos();
    public void guardar();

}
