package clement.zentz.mareu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import clement.zentz.mareu.models.Reunion;

public abstract class FakeReunionGenerator {

    private static List<Reunion> FAKE_REUNIONS = Arrays.asList(
            new Reunion(0,null, "refectoire", "kotlin", "clement@gmail.com"),
            new Reunion(1,null, "laboratoire", "xamarin", "teddy@gmail.com"),
            new Reunion(2,null, "refectoire", "ASP.Net", "alan@gmail.com"));

    public static List<Reunion> getFakeReunions() {
            return new ArrayList<>(FAKE_REUNIONS);
        }
}
