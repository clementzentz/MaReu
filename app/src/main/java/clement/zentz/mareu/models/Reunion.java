package clement.zentz.mareu.models;

import java.io.Serializable;

public class Reunion implements Serializable {
    private String id;
    private String dateReunion;
    private String lieuDeReunion;
    private String sujetReunion;
    private String email;
    private boolean mIsNewReunion;

    public Reunion(String id, String dateReunion, String lieuDeReunion, String sujetReunion, String email, boolean isNewReunion) {
        this.id = id;
        this.dateReunion = dateReunion;
        this.lieuDeReunion = lieuDeReunion;
        this.sujetReunion = sujetReunion;
        this.email = email;
        this.mIsNewReunion = isNewReunion;
    }

    public Reunion(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateReunion() {
        return dateReunion;
    }

    public void setDateReunion(String dateReunion) {
        this.dateReunion = dateReunion;
    }

    public String getLieuDeReunion() {
        return lieuDeReunion;
    }

    public void setLieuDeReunion(String lieuDeReunion) {
        this.lieuDeReunion = lieuDeReunion;
    }

    public String getSujetReunion() {
        return sujetReunion;
    }

    public void setSujetReunion(String sujetReunion) {
        this.sujetReunion = sujetReunion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isNewReunion() {
        return mIsNewReunion;
    }

    public void setIsNewReunion(boolean newReunion) {
        mIsNewReunion = newReunion;
    }
}
