package dorel.basicopp.majorari;

public class LinieMajorare {

    public int nrData;
    public double soldi;
    public double intrari;
    public double iesiri;
    public double soldf;
    public double majLuna;

    public LinieMajorare(int nrData) {
        this.nrData = nrData;
        this.soldi = 0;
        this.intrari = 0;
        this.iesiri = 0;
        this.soldf = 0;
        this.majLuna = 0;
    }

    public double calcul(double soldi, double procMajLuna) {
        this.soldi = soldi;
        majLuna = soldi * procMajLuna / 100;
        soldf = soldi + intrari - iesiri;
        return soldf;
    }
}
