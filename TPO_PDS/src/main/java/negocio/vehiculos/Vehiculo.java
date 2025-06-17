package negocio.vehiculos;


public abstract class Vehiculo {
    protected String marca;
    protected String modelo;
    protected double precioBase;
    protected String color;
    protected int nroChasis;
    protected int nroMotor;
    private ConfiguracionAd extra;

    public Vehiculo(String marca, String modelo, double precioBase, String color, int nroChasis, int nroMotor, ConfiguracionAd extra) {
        this.marca = marca;
        this.modelo = modelo;
        this.precioBase = precioBase;
        this.color = color;
        this.nroChasis = nroChasis;
        this.nroMotor = nroMotor;
        this.extra = extra;
    }
    //Geters y Seters
    public String getMarca() {
        return marca;
    }
    public String getModelo() {
        return modelo;
    }
    public String getColor() {
        return color;
    }
    public int getNroChasis() {
        return nroChasis;
    }
    public int getNroMotor() {
        return nroMotor;
    }
    public ConfiguracionAd getExtra() {
        return extra;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setNroChasis(int nroChasis) {
        this.nroChasis = nroChasis;
    }

    public void setNroMotor(int nroMotor) {
        this.nroMotor = nroMotor;
    }

    public void setExtra(ConfiguracionAd extra) {
        this.extra = extra;
    }
    //metodos: 
    public String mostrarDetalle() {
        return "Marca: " + marca + ", Modelo: " + modelo + ", Precio Base: " + precioBase +", Color: " + color +
               ", N° Chasis: " + nroChasis + ", N° Motor: " + nroMotor + ", Extras: " + extra.toString();
    }
    public double getPrecioBase(){
        return precioBase;
    }
    public abstract double getPrecioConImpuesto();
    
}
