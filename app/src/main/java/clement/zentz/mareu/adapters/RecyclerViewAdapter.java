package clement.zentz.mareu.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import clement.zentz.mareu.ActivityToRVAdapter;
import clement.zentz.mareu.R;
import clement.zentz.mareu.models.Reunion;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    private List<Reunion> lesReunions;
    private List<Reunion> lesReunionsFull;

    private ActivityToRVAdapter mActivityToRVAdapter;

    public RecyclerViewAdapter(List<Reunion> lesReunions, ActivityToRVAdapter anInterface) {
        this.lesReunions = lesReunions;
        this.lesReunionsFull = new ArrayList<>(lesReunions);
        mActivityToRVAdapter = anInterface;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reunion_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.infoReunion.setText(lesReunions.get(position).generateTitleReu());
        holder.emailOrganisateur.setText(lesReunions.get(position).getEmail());

        holder.mView.setOnClickListener(v -> mActivityToRVAdapter.launchMyActivity(lesReunions.get(position), position));

        holder.deleteButton.setOnClickListener(v -> mActivityToRVAdapter.callDeleteReunion(lesReunions.get(position)));
    }

    @Override
    public int getItemCount() {
        return lesReunions.size();
    }

    @Override
    public Filter getFilter() {
        return reunionFilter;
    }

    private Filter reunionFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Reunion> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0 ){
                filteredList.addAll(lesReunionsFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Reunion currentReu: lesReunionsFull) {
                    if (currentReu.generateTitleReu().toLowerCase().contains(filterPattern)){
                        filteredList.add(currentReu);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            lesReunions.clear();
            lesReunions.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
       private TextView infoReunion;
       private TextView emailOrganisateur;
       private ImageButton deleteButton;
       View mView;

       ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.infoReunion = itemView.findViewById(R.id.sujetReu_txt);
            this.emailOrganisateur = itemView.findViewById(R.id.email_organisateur);
            this.deleteButton = itemView.findViewById(R.id.delete_btn);

            ButterKnife.bind(this, itemView);
            mView = itemView;
        }
    }
}
