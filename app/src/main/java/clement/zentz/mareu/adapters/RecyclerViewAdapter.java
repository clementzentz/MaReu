package clement.zentz.mareu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import clement.zentz.mareu.R;
import clement.zentz.mareu.models.Reunion;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Reunion> lesReunions = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(List<Reunion> lesReunions, Context context) {
        this.lesReunions = lesReunions;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reunion_fragment, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.infoReunion.setText(lesReunions.get(position).getSujetReunion());
        holder.emailOrganisateur.setText(lesReunions.get(position).getEmailParticipant());
    }

    @Override
    public int getItemCount() {
        return lesReunions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       private ImageView couleurReunion;
       private TextView infoReunion;
       private TextView emailOrganisateur;

       public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.couleurReunion = itemView.findViewById(R.id.color_reu);
            this.infoReunion = itemView.findViewById(R.id.name_meeting);
            this.emailOrganisateur = itemView.findViewById(R.id.email_organisateur);
        }
    }
}
