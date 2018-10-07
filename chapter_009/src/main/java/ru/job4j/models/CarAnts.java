package ru.job4j.models;

import javax.persistence.*;
import javax.persistence.Entity;

/**
 * @author Alexander Belov (whiterabbit.nsk@gmail.com)
 * @since 07.10.18
 */
@Entity
@Table(name = "cars")
public class CarAnts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name", unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "carcase", foreignKey = @ForeignKey(name = "carcase_fk"))
    private CarcaseAnts carcase;
    @ManyToOne
    @JoinColumn(name = "engine", foreignKey = @ForeignKey(name = "engine_fk"))
    private EngineAnts engine;
    @ManyToOne
    @JoinColumn(name = "transmission", foreignKey = @ForeignKey(name = "transmission_fk"))
    private TransmissionAnts transmission;

    public CarAnts() {

    }

    public CarAnts(String name, CarcaseAnts carcase, EngineAnts engine, TransmissionAnts transmission) {
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

    public CarcaseAnts getCarcase() {
        return carcase;
    }

    public EngineAnts getEngine() {
        return engine;
    }

    public TransmissionAnts getTransmission() {
        return transmission;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCarcase(CarcaseAnts carcase) {
        this.carcase = carcase;
    }

    public void setEngine(EngineAnts engine) {
        this.engine = engine;
    }

    public void setTransmission(TransmissionAnts transmission) {
        this.transmission = transmission;
    }
}
