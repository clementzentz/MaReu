package clement.zentz.mareu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import clement.zentz.mareu.adapters.RecyclerViewAdapter;
import clement.zentz.mareu.models.Reunion;
import clement.zentz.mareu.viewmodels.ReunionViewModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerViewAdapter mRecyclerViewAdapter;

    private ReunionViewModel mReunionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReunionViewModel = ViewModelProviders.of(this).get(ReunionViewModel.class);

        mReunionViewModel.init();

        mReunionViewModel.getReunions().observe(this, new Observer<List<Reunion>>() {
            @Override
            public void onChanged(List<Reunion> reunions) {
                mRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        iniRecyclerView();
    }

    private void iniRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewAdapter = new RecyclerViewAdapter(mReunionViewModel.getReunions().getValue(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mRecyclerViewAdapter);
    }
}
