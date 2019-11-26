package clement.zentz.mareu.models;

import java.util.Date;

public class Reunion {
    private int id;
    private Date hour;
    private String lieuDeReunion;
    private String sujetReunion;
    private String emailParticipants;

    public Reunion(int id, Date hour, String lieuDeReunion, String sujetReunion, String emailParticipants) {
        this.id = id;
        this.hour = hour;
        this.lieuDeReunion = lieuDeReunion;
        this.sujetReunion = sujetReunion;
        this.emailParticipants = emailParticipants;
    }

    public int getId() {
        return id;
    }

    public Date getHour() {
        return hour;
    }

    public String getLieuDeReunion() {
        return lieuDeReunion;
    }

    public String getSujetReunion() {
        return sujetReunion;
    }

    public String getEmailParticipant() {
        return emailParticipants;
    }
}
