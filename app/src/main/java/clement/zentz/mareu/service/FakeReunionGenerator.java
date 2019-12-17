package clement.zentz.mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import clement.zentz.mareu.models.Reunion;

public abstract class FakeReunionGenerator {

    private static List<Reunion> FAKE_REUNIONS = Arrays.asList(
            new Reunion(0, "09:30","03/03/1996", "laboratoire", "kotlin", "clement@gmail.com",false),
            new Reunion(1, "10:15","02/03/1996", "salle de cours", "flutter", "teddy@gmail.com",false),
            new Reunion(2, "12:25","01/03/1996", "refectoire", "Django", "alan@gmail.com",false),
            new Reunion(3, "13:40","03/03/1996", "laboratoire", "Vue.js", "clement@gmail.com",false),
            new Reunion(4, "15:00","02/03/1996", "salle de cours", "Docker", "teddy@gmail.com",false),
            new Reunion(5, "17:05","01/03/1996", "refectoire", "DNS Load Balancing", "alan@gmail.com",false),
            new Reunion(6, "18:50","03/03/1996", "laboratoire", "cache database", "clement@gmail.com",false),
            new Reunion(7, "11:10","02/03/1996", "salle de cours", "Amazon S3", "teddy@gmail.com",false),
            new Reunion(8, "10:10","01/03/1996", "refectoire", "PWA", "alan@gmail.com",false),
            new Reunion(9, "08:30","03/03/1996", "laboratoire", "Slave Database", "teddy@gmail.com",false));

    public static List<Reunion> getFakeReunions() {
            return new ArrayList<>(FAKE_REUNIONS);
        }
}
