package clement.zentz.mareu.service;

import java.util.List;

import clement.zentz.mareu.models.Reunion;

public class FakeReunionApiService implements ReunionApiService {

    private List<Reunion> mReunions = FakeReunionGenerator.getFakeReunions();

    @Override
    public List<Reunion> getReunions() {
        return mReunions;
    }

    @Override
    public void addReunion(Reunion reunion) {
        if (!mReunions.contains(reunion)){
            mReunions.add(reunion);
        }
    }

    @Override
    public void deleteReunion(Reunion reunion) {
        if (reunion != null){
            mReunions.remove(reunion);
        }
    }

    @Override
    public void updateReunion(Reunion reunion, int index) {
            if (mReunions.get(index)!= null){
                mReunions.set(index, reunion);
            }
    }
}
