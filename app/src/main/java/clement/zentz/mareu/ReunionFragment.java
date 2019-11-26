package clement.zentz.mareu;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import clement.zentz.mareu.viewmodels.ReunionViewModel;

public class ReunionFragment extends Fragment {

    private ReunionViewModel mViewModel;

    public static ReunionFragment newInstance() {
        return new ReunionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.reunion_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ReunionViewModel.class);
        // TODO: Use the ViewModel
    }

}
