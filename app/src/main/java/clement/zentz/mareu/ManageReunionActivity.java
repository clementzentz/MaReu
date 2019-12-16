package clement.zentz.mareu;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import clement.zentz.mareu.models.Reunion;

public class ManageReunionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String INTENT_RETOUR_MANAGE_REUNION = "INTENT_RETOUR_MANAGE_REUNION";

    //UI components
    EditText idReunionEditText, lieuReunionEditText, sujetReunionEditText, emailReunionEditText;
    TextView mDisplayDatePicker, mDisplayTimePicker;
    Button addReunionBtn, updateReunionBtn;
    ImageButton returnReunionActivityBtn;

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

        returnReunionActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addReunionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getInputsEditText();

                Intent intent = new Intent();
                intent.putExtra("INTENT_RETOUR_MANAGE_REUNION", mReunion);
                setResult(RESULT_OK, intent);
                Toast.makeText(ManageReunionActivity.this, "Reunion "+mReunion.getId()+" correctement ajouté", Toast.LENGTH_SHORT).show();
            }
        });

        updateReunionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputsEditText();

                Intent intent = new Intent();
                intent.putExtra("INTENT_RETOUR_MANAGE_REUNION", mReunion);
                setResult(RESULT_OK, intent);
                Toast.makeText(ManageReunionActivity.this, "Informations Reunion "+mReunion.getId()+" correctement mises à jours", Toast.LENGTH_SHORT).show();
            }
        });

        mDisplayTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        mDisplayDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void initViews() {
        idReunionEditText = findViewById(R.id.idReunion_txt);
        mDisplayTimePicker = findViewById(R.id.openDialogTP_txt);
        mDisplayDatePicker = findViewById(R.id.openDialogDP_txt);
        lieuReunionEditText = findViewById(R.id.lieuReunion_txt);
        sujetReunionEditText = findViewById(R.id.sujetReunion_txt);
        emailReunionEditText = findViewById(R.id.emailReunion_txt);

        returnReunionActivityBtn = findViewById(R.id.return_button);
        addReunionBtn = findViewById(R.id.add_btn);
        updateReunionBtn = findViewById(R.id.update_btn);
    }

    private void getInputsEditText(){
        mReunion.setId(idReunionEditText.getText().toString());
        mReunion.setHeureReunion(mDisplayTimePicker.getText().toString());
        mReunion.setDateReunion(mDisplayDatePicker.getText().toString());
        mReunion.setLieuReunion(lieuReunionEditText.getText().toString());
        mReunion.setSujetReunion(sujetReunionEditText.getText().toString());
        mReunion.setEmail(emailReunionEditText.getText().toString());
    }

    private void getIncomingIntent(){
        if (getIntent().hasExtra("UPDATE_REUNION")){
            mReunion = (Reunion)getIntent().getSerializableExtra("UPDATE_REUNION");
            idReunionEditText.setText(mReunion.getId());
            mDisplayTimePicker.setText(mReunion.getHeureReunion());
            mDisplayDatePicker.setText(mReunion.getDateReunion());
            lieuReunionEditText.setText(mReunion.getLieuReunion());
            sujetReunionEditText.setText(mReunion.getSujetReunion());
            emailReunionEditText.setText(mReunion.getEmail());
        }else{
            mReunion = new Reunion();
        }
    }

    private void showTimePickerDialog(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.HOUR),
                Calendar.getInstance().get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mReunion.setHeureReunion(hourOfDay+":"+minute);
        mDisplayTimePicker.setText(mReunion.getHeureReunion());
    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mReunion.setDateReunion(dayOfMonth+"/"+month+"/"+year);
        mDisplayDatePicker.setText(mReunion.getDateReunion());
    }
}
