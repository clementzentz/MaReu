package clement.zentz.mareu.service;

import java.util.List;

import clement.zentz.mareu.models.Reunion;

public interface ReunionApiService {
    List<Reunion> getReunions();
    void addReunion(Reunion reunion);
    void deleteReunion(int index);
    void updateReunion(Reunion reunion, int indexReunion);
}
