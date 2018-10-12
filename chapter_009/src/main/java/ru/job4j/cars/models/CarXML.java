package ru.job4j.cars.models;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 07.10.18
 */
public class CarXML {
    private int id;
    private String name;
    private CarcaseXML carcase;
    private EngineXML engine;
    private TransmissionXML transmission;

    public CarXML() {

    }

    public CarXML(final String name, final CarcaseXML carcase,
                  final EngineXML engine, final TransmissionXML transmission) {
        this.name = name;
        this.carcase = carcase;
        this.engine = engine;
        this.transmission = transmission;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CarcaseXML getCarcase() {
        return carcase;
    }

    public EngineXML getEngine() {
        return engine;
    }

    public TransmissionXML getTransmission() {
        return transmission;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setCarcase(final CarcaseXML carcase) {
        this.carcase = carcase;
    }

    public void setEngine(final EngineXML engine) {
        this.engine = engine;
    }

    public void setTransmission(final TransmissionXML transmission) {
        this.transmission = transmission;
    }
}
