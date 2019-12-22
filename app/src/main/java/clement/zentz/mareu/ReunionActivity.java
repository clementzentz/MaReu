package clement.zentz.mareu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

    private RecyclerView mRecyclerView;

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

        getReunionsFromService();

        mRecyclerView = findViewById(R.id.recyclerView);

        initRecyclerView(mReunions);

        FloatingActionButton floatingActionButton = findViewById(R.id.addReu_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReunionActivity.this, ManageReunionActivity.class);
                intent.putExtra("IS_NEW_REUNION", true);
                startActivityForResult(intent, MANAGE_REUNION_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    private void getReunionsFromService(){
        mReunions = mReunionApiService.getReunions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Collections.sort(mReunions, new ComparatorIdReu());
                initRecyclerView(mReunions);
                return true;
            case R.id.item2:
                Collections.sort(mReunions, new ComparatorDateReu());
                initRecyclerView(mReunions);
                return true;
            case R.id.item3:
                Collections.sort(mReunions, new ComparatorLieuReu());
                initRecyclerView(mReunions);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initRecyclerView(List<Reunion> reunions){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewAdapter = new RecyclerViewAdapter(reunions, this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
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
        getReunionsFromService();
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void callDeleteReunion(Reunion reunion){
        mReunionApiService.deleteReunion(reunion);
        getReunionsFromService();
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void callUpdateReunion(Reunion reunion, int indexReunion) {
        mReunionApiService.updateReunion(reunion, indexReunion);
        getReunionsFromService();
        mRecyclerViewAdapter.notifyItemChanged(indexReunion);
    }

    @Override
    public void launchMyActivity(Reunion reunion, int indexReunion) {
        mIndexReunion = indexReunion;
        Intent intent = new Intent(this, ManageReunionActivity.class);
        intent.putExtra("UPDATE_REUNION", reunion);
        startActivityForResult(intent, MANAGE_REUNION_ACTIVITY_REQUEST_CODE);
    }

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
            return Integer.compare(o1.getId(), o2.getId());
        }
    }
}
