package clement.zentz.mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import clement.zentz.mareu.models.Reunion;

public abstract class FakeReunionGenerator {

    private static List<Reunion> FAKE_REUNIONS = Arrays.asList(
            new Reunion("0","21/03/1996", "refectoire", "kotlin", "clement@gmail.com",false),
            new Reunion("1","03/03/1996", "laboratoire", "flutter", "teddy@gmail.com",false),
            new Reunion("2","02/03/1996", "refectoire", "Django", "alan@gmail.com",false));

    public static List<Reunion> getFakeReunions() {
            return new ArrayList<>(FAKE_REUNIONS);
        }
}
