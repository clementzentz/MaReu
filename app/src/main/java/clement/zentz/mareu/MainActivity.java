package clement.zentz.mareu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import clement.zentz.mareu.adapters.RecyclerViewAdapter;
import clement.zentz.mareu.di.DI;
import clement.zentz.mareu.models.Reunion;
import clement.zentz.mareu.service.ReunionApiService;

public class MainActivity extends AppCompatActivity {

    private List<Reunion> mReunionList = new ArrayList<>();
    private ReunionApiService mReunionApiService = DI.getReunionApiService();

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReunionList = mReunionApiService.getReunions();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewAdapter = new RecyclerViewAdapter(mReunionList, this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }
}
