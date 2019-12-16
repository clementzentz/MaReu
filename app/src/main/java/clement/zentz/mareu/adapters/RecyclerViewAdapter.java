package clement.zentz.mareu.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import clement.zentz.mareu.ActivityToRVAdapter;
import clement.zentz.mareu.R;
import clement.zentz.mareu.models.Reunion;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Reunion> lesReunions;

    private ActivityToRVAdapter mActivityToRVAdapter;

    public RecyclerViewAdapter(List<Reunion> lesReunions, ActivityToRVAdapter anInterface ) {
        this.lesReunions = lesReunions;
        mActivityToRVAdapter = anInterface;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reunion_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.infoReunion.setText(lesReunions.get(position).getSujetReunion());
        holder.emailOrganisateur.setText(lesReunions.get(position).getEmail());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityToRVAdapter.launchMyActivity(lesReunions.get(position), position);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivityToRVAdapter.callDeleteReunion(lesReunions.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return lesReunions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
       private ImageView couleurReunion;
       private TextView infoReunion;
       private TextView emailOrganisateur;
       private ImageButton deleteButton;
       View mView;

       ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.couleurReunion = itemView.findViewById(R.id.color_reu);
            this.infoReunion = itemView.findViewById(R.id.sujetReu_txt);
            this.emailOrganisateur = itemView.findViewById(R.id.email_organisateur);
            this.deleteButton = itemView.findViewById(R.id.delete_btn);

            ButterKnife.bind(this, itemView);
            mView = itemView;
        }
    }
}
