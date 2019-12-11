package clement.zentz.mareu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import clement.zentz.mareu.models.Reunion;

public class ManageReunionActivity extends AppCompatActivity {

    public static final String INTENT_RETOUR_MANAGE_REUNION = "INTENT_RETOUR_MANAGE_REUNION";

    //UI components
    EditText idReunion_txt, dateReunion_txt, lieuReunion_txt, sujetReunion_txt, emailReunion_txt;
    Button add_button, update_button;
    ImageButton return_button;

    //vars
    Reunion mReunion;

    public ManageReunionActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_reunion);

        initViews();

        getIncomingIntent();

        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getInputsEditText();

                Intent intent = new Intent();
                intent.putExtra("INTENT_RETOUR_MANAGE_REUNION", mReunion);
                setResult(RESULT_OK, intent);
                Toast.makeText(ManageReunionActivity.this, "Reunion "+mReunion.getId()+" correctement ajouté", Toast.LENGTH_SHORT).show();
            }
        });

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputsEditText();

                Intent intent = new Intent();
                intent.putExtra("INTENT_RETOUR_MANAGE_REUNION", mReunion);
                setResult(RESULT_OK, intent);
                Toast.makeText(ManageReunionActivity.this, "Informations Reunion "+mReunion.getId()+" correctement mises à jours", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews() {
        idReunion_txt = findViewById(R.id.idReunion_txt);
        dateReunion_txt = findViewById(R.id.dateReunion_txt);
        lieuReunion_txt = findViewById(R.id.lieuReunion_txt);
        sujetReunion_txt = findViewById(R.id.sujetReunion_txt);
        emailReunion_txt = findViewById(R.id.emailReunion_txt);

        return_button = findViewById(R.id.return_button);
        add_button = findViewById(R.id.add_btn);
        update_button = findViewById(R.id.update_btn);
    }

    private void getInputsEditText(){
        mReunion.setId(idReunion_txt.getText().toString());
        mReunion.setDateReunion(dateReunion_txt.getText().toString());
        mReunion.setLieuDeReunion(lieuReunion_txt.getText().toString());
        mReunion.setSujetReunion(sujetReunion_txt.getText().toString());
        mReunion.setEmail(emailReunion_txt.getText().toString());
    }

    private void getIncomingIntent(){
        if (getIntent().hasExtra("UPDATE_REUNION")){
            mReunion = (Reunion)getIntent().getSerializableExtra("UPDATE_REUNION");
            idReunion_txt.setText(mReunion.getId());
            dateReunion_txt.setText(mReunion.getDateReunion());
            lieuReunion_txt.setText(mReunion.getLieuDeReunion());
            sujetReunion_txt.setText(mReunion.getSujetReunion());
            emailReunion_txt.setText(mReunion.getEmail());

        }else{
            mReunion = new Reunion();
        }
    }
}
