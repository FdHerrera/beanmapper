package nl.mad.beanmapper.testmodel.person;

public class PersonForm {

    private String name;

    private String place;

    private String bankAccount;

    private String unidentifiableFluff;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getUnidentifiableFluff() {
        return unidentifiableFluff;
    }

    public void setUnidentifiableFluff(String unidentifiableFluff) {
        this.unidentifiableFluff = unidentifiableFluff;
    }
}
