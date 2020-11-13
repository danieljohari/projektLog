//Holding information about our persons.
public class Person {
    public String getFnavn() {
        return fnavn;
    }

    public void setFnavn(String fnavn) {
        this.fnavn = fnavn;
    }

    private String fnavn;

    public String getEnavn() {
        return enavn;
    }

    public void setEnavn(String enavn) {
        this.enavn = enavn;
    }


    public int getTlfnummer() {
        return tlfnummer;
    }

    public void setTlfnummer(int tlfnummer) {
        this.tlfnummer = tlfnummer;
    }

    private String enavn;
    private int tlfnummer;
    private String CPR;

    public Person(String CPR) {
        this.CPR = CPR;
    }

    public String getCPR() {
        return CPR;
    }

    public void setCPR(String CPR) {
        this.CPR = CPR;
    }
}
