package datos.serializacion;

import java.util.List;

public interface Repositorio<T> {
    public void agregar(T objeto);
    public void eliminar(int cod);
    public T obtenerObj(int cod);
    public List<T> obtenerTodos();
    public void guardar();

}
