package nl.mad.beanmapper.testmodel.encapsulate.sourceAnnotated;

import nl.mad.beanmapper.annotations.BeanProperty;
import nl.mad.beanmapper.annotations.BeanUnwrap;

public class Driver {

    private String name;
    @BeanUnwrap
    private Car car;
    @BeanProperty(name = "monteur.name")
    private String monteurName;

    public Driver(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getMonteurName() {
        return monteurName;
    }

    public void setMonteurName(String monteurName) {
        this.monteurName = monteurName;
    }
}
