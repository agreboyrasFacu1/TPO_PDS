package negocio.vehiculos;

import java.io.Serializable;
import java.util.List;

public class ConfiguracionAd implements Serializable {
    private List<String> extras;
    private boolean garantiaExtendida;
    private List<String> accesorios;

    public ConfiguracionAd(List<String> extras, boolean garantiaExtendida, List<String> accesorios) {
        this.extras = extras;
        this.garantiaExtendida = garantiaExtendida;
        this.accesorios = accesorios;
    }

    public List<String> getExtras() {
        return extras;
    }

    public boolean isGarantiaExtendida() {
        return garantiaExtendida;
    }

    public List<String> getAccesorios() {
        return accesorios;
    }
        // Setters
    public void setExtras(List<String> extras) {
        this.extras = extras;
    }

    public void setGarantiaExtendida(boolean garantiaExtendida) {
        this.garantiaExtendida = garantiaExtendida;
    }

    public void setAccesorios(List<String> accesorios) {
        this.accesorios = accesorios;
    }
}