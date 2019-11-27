package clement.zentz.mareu.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import clement.zentz.mareu.models.Reunion;
import clement.zentz.mareu.repositories.ReunionRepository;

public class ReunionViewModel extends ViewModel {

    private MutableLiveData<List<Reunion>> mReunions;
    private ReunionRepository mReunionRepository;

    public void init(){
            if (mReunions != null){
                return;
            }
            mReunionRepository = ReunionRepository.getInstance();
            mReunions = mReunionRepository.getDatas();
    }

    public LiveData<List<Reunion>> getReunions(){
        return mReunions;
    }
}
