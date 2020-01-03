package clement.zentz.mareu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
        floatingActionButton.setOnClickListener(v -> launchMyActivity(new Reunion(true), mReunions.size()+1));
    }

    private void getReunionsFromService(){
        mReunions = mReunionApiService.getReunions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.item0);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mRecyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    //pb ecrasement premier élément recyclerView
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Collections.sort(mReunions, new ComparatorSujetReu());
                mRecyclerViewAdapter.notifyDataSetChanged();
                return true;
            case R.id.item2:
                Collections.sort(mReunions, new ComparatorDateReu());
                mRecyclerViewAdapter.notifyDataSetChanged();
                return true;
            case R.id.item3:
                Collections.sort(mReunions, new ComparatorLieuReu());
                mRecyclerViewAdapter.notifyDataSetChanged();
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
                if (currentReunion != null){
                    if (currentReunion.isNewReunion()){
                        callAddReunion(currentReunion);
                    }else {
                        callUpdateReunion(currentReunion, mIndexReunion);
                    }
                }
            }
        }
    }

    @Override
    public void callAddReunion(Reunion reunion){
        reunion.setIsNewReunion(false);
        mReunionApiService.addReunion(reunion);
        mRecyclerViewAdapter.notifyDataSetChanged();
        getReunionsFromService();
    }

    @Override
    public void callDeleteReunion(Reunion reunion, int indexReunion){
        Log.d(TAG, "callDeleteReunion: indexReunion = "+indexReunion);
        mReunionApiService.deleteReunion(reunion);
        getReunionsFromService();
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void callUpdateReunion(Reunion reunion, int indexReunion) {
        Log.d(TAG, "callUpdateReunion: indexReunion = "+indexReunion);
        mReunionApiService.updateReunion(reunion, indexReunion);
        getReunionsFromService();
        mRecyclerViewAdapter.notifyItemChanged(indexReunion);
    }

    @Override
    public void launchMyActivity(Reunion reunion, int indexReunion) {
        Log.d(TAG, "launchMyActivity: indexReunion = "+indexReunion);
        mIndexReunion = indexReunion;
        Intent intent = new Intent(this, ManageReunionActivity.class);
        intent.putExtra("REUNION", reunion);
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

    public class ComparatorSujetReu implements Comparator<Reunion> {
        @Override
        public int compare(Reunion o1, Reunion o2) {
            return o1.getSujetReunion().compareTo(o2.getSujetReunion());
        }
    }
}
