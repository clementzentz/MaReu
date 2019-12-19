package clement.zentz.mareu.di;

import clement.zentz.mareu.service.FakeReunionApiService;
import clement.zentz.mareu.service.ReunionApiService;

public class DI {

    private static ReunionApiService service = new FakeReunionApiService();

    public static ReunionApiService getReunionApiService() {
        return service;
    }

    public static ReunionApiService getNewInstanceApiService(){
        return new FakeReunionApiService();
    }
}
