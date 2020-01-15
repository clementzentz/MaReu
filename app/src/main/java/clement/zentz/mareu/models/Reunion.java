package clement.zentz.mareu.models;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Reunion implements Serializable {
    private String dateReunion;
    private String heureReunion;
    private String lieuReunion;
    private String sujetReunion;
    private String email;
    private boolean mIsNewReunion;

    public Reunion(String heureReunion, String dateReunion, String lieuReunion, String sujetReunion, String email, boolean isNewReunion) {
        this.dateReunion = dateReunion;
        this.heureReunion = heureReunion;
        this.lieuReunion = lieuReunion;
        this.sujetReunion = sujetReunion;
        this.email = email;
        this.mIsNewReunion = isNewReunion;
    }

    public Reunion(boolean isNewReunion){
        this.mIsNewReunion = isNewReunion;
    }

    public String getDateReunion() {
        return dateReunion;
    }

    public void setDateReunion(String dateReunion) {
        this.dateReunion = dateReunion;
    }

    public String getHeureReunion() {
        return heureReunion;
    }

    public void setHeureReunion(String heureReunion) {
        this.heureReunion = heureReunion;
    }

    public String getLieuReunion() {
        return lieuReunion;
    }

    public void setLieuReunion(String lieuReunion) {
        this.lieuReunion = lieuReunion;
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

    public String generateTitleReu(){
        return sujetReunion+" - "+heureReunion+" - "+email.substring(0, email.indexOf("@"));
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null){
            return false;
        }if (getClass() != obj.getClass()){
            return false;
        }
        Reunion reunion = (Reunion) obj;
        return reunion.getSujetReunion().equals(getSujetReunion())
                && reunion.getDateReunion().equals(getDateReunion())
                && reunion.getLieuReunion().equals(getLieuReunion());
    }
}
