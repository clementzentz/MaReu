package clement.zentz.mareu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import clement.zentz.mareu.adapters.RecyclerViewAdapter;
import clement.zentz.mareu.di.DI;
import clement.zentz.mareu.models.Reunion;
import clement.zentz.mareu.service.ReunionApiService;

public class ReunionActivity extends AppCompatActivity implements ActivityToRVAdapter {

    private RecyclerViewAdapter mRecyclerViewAdapter;

    private ReunionApiService mReunionApiService;

    private List<Reunion> mReunions;

    private int mIndexReunion;

    public static final int MANAGE_REUNION_ACTIVITY_REQUEST_CODE = 21;

    private static final String TAG = "ReunionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reunion);

       mReunionApiService = DI.getReunionApiService();

       mReunions = mReunionApiService.getReunions();

        iniRecyclerView(mReunions);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReunionActivity.this, ManageReunionActivity.class);
                intent.putExtra("IS_NEW_REUNION", true);
                startActivityForResult(intent, MANAGE_REUNION_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    private void iniRecyclerView(List<Reunion> reunions){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewAdapter = new RecyclerViewAdapter(reunions, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mRecyclerViewAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (MANAGE_REUNION_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            if (data != null) {
                Reunion currentReunion = (Reunion)data.getSerializableExtra(ManageReunionActivity.INTENT_RETOUR_MANAGE_REUNION);
                if (currentReunion != null && currentReunion.isNewReunion()){
                    Log.d(TAG, "onActivityResult: on récupère le résultat de l'activity : "+currentReunion.getId());
                    currentReunion.setIsNewReunion(false);
                    mReunions.add(currentReunion);
                    callAddReunion(currentReunion);
                }else{
                    callUpdateReunion(currentReunion, mIndexReunion);
                }
            }
        }
    }

    @Override
    public void callAddReunion(Reunion reunion){
        mReunionApiService.addReunion(reunion);
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void callDeleteReunion(Reunion reunion){
        mReunionApiService.deleteReunion(reunion);
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void callUpdateReunion(Reunion reunion, int indexReunion) {
        mReunionApiService.updateReunion(reunion, indexReunion);
        mRecyclerViewAdapter.notifyItemChanged(indexReunion);
    }

    @Override
    public void launchMyActivity(Reunion reunion, int indexReunion) {
        mIndexReunion = indexReunion;
        Intent intent = new Intent(this, ManageReunionActivity.class);
        intent.putExtra("UPDATE_REUNION", reunion);
        startActivityForResult(intent, MANAGE_REUNION_ACTIVITY_REQUEST_CODE);
    }
}
