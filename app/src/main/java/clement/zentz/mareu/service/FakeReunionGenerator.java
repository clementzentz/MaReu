package clement.zentz.mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import clement.zentz.mareu.models.Reunion;

public abstract class FakeReunionGenerator {

    public static final String salle1 = "Laboratoire", salle2 = "Amphi", salle3 = "Refectoire";

    private static List<Reunion> FAKE_REUNIONS = Arrays.asList(
            new Reunion(0, "09:30","03/03/1996", salle1, "kotlin", "clement@gmail.com",false),
            new Reunion(1, "10:15","02/03/1996", salle2, "flutter", "teddy@gmail.com",false),
            new Reunion(2, "12:25","01/03/1996", salle3, "Django", "alan@gmail.com",false),
            new Reunion(3, "13:40","03/03/1996", salle1, "Vue.js", "laurent@gmail.com",false),
            new Reunion(4, "15:00","02/03/1996", salle2, "Docker", "nico@gmail.com",false),
            new Reunion(5, "17:05","01/03/1996", salle3, "DNS Load Balancing", "sarah@gmail.com",false),
            new Reunion(6, "18:50","03/03/1996", salle1, "cache database", "solenn@gmail.com",false),
            new Reunion(7, "11:10","02/03/1996", salle2, "Amazon S3", "andrea@gmail.com",false),
            new Reunion(8, "10:10","01/03/1996", salle3, "PWA", "melanie@gmail.com",false),
            new Reunion(9, "08:30","03/03/1996", salle1, "Slave Database", "pauline@gmail.com",false));

    public static List<Reunion> getFakeReunions() {
            return new ArrayList<>(FAKE_REUNIONS);
        }
}
