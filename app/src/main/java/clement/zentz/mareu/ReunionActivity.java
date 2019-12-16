package clement.zentz.mareu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.Comparator;
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

        initRecyclerView(mReunions);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReunionActivity.this, ManageReunionActivity.class);
                intent.putExtra("IS_NEW_REUNION", true);
                startActivityForResult(intent, MANAGE_REUNION_ACTIVITY_REQUEST_CODE);
            }
        });

        ConstraintLayout sortButtonContainer = findViewById(R.id.sort_container);
        ImageButton sortButtonMenu = findViewById(R.id.sort_button_menu);
        sortButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortButtonContainer.setVisibility(View.VISIBLE);
            }
        });

        Button sortDateReunionBtn = findViewById(R.id.sort_dateReu_btn);
        sortDateReunionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(mReunions, new ComparatorDateReu());
                initRecyclerView(mReunions);
            }
        });

        Button sortLieuReunionBtn = findViewById(R.id.sort_lieuReu_btn);
        sortLieuReunionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(mReunions, new ComparatorLieuReu());
                initRecyclerView(mReunions);
            }
        });
    }

    private void initRecyclerView(List<Reunion> reunions){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
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

    //datepicker
    // on create menu options
    //enlever la deuxième toolbar
    //remettre la liste par défault

    public class ComparatorDateReu implements Comparator<Reunion> {
        @Override
        public int compare(Reunion o1, Reunion o2) {
            return o1.getDateReunion().compareTo(o2.getDateReunion());
        }
    }

    public class ComparatorLieuReu implements Comparator<Reunion> {
        @Override
        public int compare(Reunion o1, Reunion o2) {
            return o1.getLieuReunion().compareTo(o2.getLieuReunion());
        }
    }

    public class ComparatorIdReu implements Comparator<Reunion> {
        @Override
        public int compare(Reunion o1, Reunion o2) {
            return o1.getId().compareTo(o2.getId());
        }
    }
}
