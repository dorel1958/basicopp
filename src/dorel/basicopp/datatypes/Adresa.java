package dorel.basicopp.datatypes;

public class Adresa {

    private String tara;

    private String judet;
    private String sector;

    private String localitate;
    private String strada;
    private String numar;
    private String litera;
    private String bloc;
    private String scara;
    
    private String etaj;

    private String apartament;
    private String codPostal;

    //<editor-fold defaultstate="collapsed" desc="Get Set">
    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getEtaj() {
        return etaj;
    }

    public void setEtaj(String etaj) {
        this.etaj = etaj;
    }

    public String getLocalitate() {
        return localitate;
    }

    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getApartament() {
        return apartament;
    }

    public void setApartament(String apartament) {
        this.apartament = apartament;
    }

    public String getScara() {
        return scara;
    }

    public void setScara(String scara) {
        this.scara = scara;
    }

    public String getBloc() {
        return bloc;
    }

    public void setBloc(String bloc) {
        this.bloc = bloc;
    }

    public String getLitera() {
        return litera;
    }

    public void setLitera(String litera) {
        this.litera = litera;
    }

    public String getNumar() {
        return numar;
    }

    public void setNumar(String numar) {
        this.numar = numar;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }
    //</editor-fold>

    public Adresa() {
        tara = "";
        judet = "";
        sector = "";
        localitate = "";
        strada = "";
        numar = "";
        litera = "";
        bloc = "";
        scara = "";
        apartament = "";
        codPostal = "";
    }

    public String getAdresa() {
        String adr = "";
        boolean ePrima = true;
        if (!tara.isEmpty()) {
            ePrima = false;
            adr += "Tara " + tara;
        }
        if (!judet.isEmpty()) {
            if (ePrima) {
                ePrima = false;
            } else {
                adr += ", ";
            }
            adr += "Jud. " + judet;
        }
        if (!sector.isEmpty()) {
            if (ePrima) {
                ePrima = false;
            } else {
                adr += ", ";
            }
            adr += "Sector " + sector;
        }
        if (!localitate.isEmpty()) {
            if (ePrima) {
                ePrima = false;
            } else {
                adr += ", ";
            }
            adr += "Loc. " + localitate;
        }
        if (!strada.isEmpty()) {
            if (ePrima) {
                ePrima = false;
            } else {
                adr += ", ";
            }
            adr += "Str. " + strada;
        }
        if (!numar.isEmpty()) {
            if (ePrima) {
                ePrima = false;
            } else {
                adr += ", ";
            }
            adr += "Nr. " + numar;
            if (!litera.isEmpty()) {
                adr += litera;
            }
        }
        if (!bloc.isEmpty()) {
            if (ePrima) {
                ePrima = false;
            } else {
                adr += ", ";
            }
            adr += "Bl. " + bloc;
        }
        if (!codPostal.isEmpty()) {
            if (ePrima) {
                ePrima = false;
            } else {
                adr += ", ";
            }
            adr += "Cod postal " + codPostal;
        }
        if (!scara.isEmpty()) {
            if (ePrima) {
                ePrima = false;
            } else {
                adr += ", ";
            }
            adr += "Sc. " + scara;
        }
        if (!etaj.isEmpty()) {
            if (ePrima) {
                ePrima = false;
            } else {
                adr += ", ";
            }
            adr += "Et. " + etaj;
        }
        if (!apartament.isEmpty()) {
            if (ePrima) {
                //ePrima = false;
            } else {
                adr += ", ";
            }
            adr += "Ap. " + apartament;
        }

        return adr;
    }

}
