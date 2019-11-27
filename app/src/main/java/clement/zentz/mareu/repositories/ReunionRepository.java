package clement.zentz.mareu.repositories;

import androidx.lifecycle.MutableLiveData;
import java.util.List;
import clement.zentz.mareu.di.DI;
import clement.zentz.mareu.models.Reunion;
import clement.zentz.mareu.service.ReunionApiService;

public class ReunionRepository {

    private ReunionApiService mReunionApiService = DI.getReunionApiService();
    private static ReunionRepository instance;

    public static ReunionRepository getInstance(){
        if (instance == null){
            instance = new ReunionRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Reunion>> getDatas(){
        List<Reunion> dataSet = mReunionApiService.getReunions();
        MutableLiveData<List<Reunion>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }
}
