package clement.zentz.mareu;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import clement.zentz.mareu.models.Reunion;
import clement.zentz.mareu.service.FakeReunionGenerator;

public class ManageReunionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String INTENT_RETOUR_MANAGE_REUNION = "INTENT_RETOUR_MANAGE_REUNION";

    //UI components
    EditText idReunionEditText, sujetReunionEditText, emailReunionEditText;
    Spinner lieuReunionSpinner;
    TextView mDisplayDatePicker, mDisplayTimePicker;
    Button addReunionBtn, updateReunionBtn;

    //vars
    Reunion mReunion;

    public ManageReunionActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_reunion);

        initViews();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        List<String> lieuxReu = new ArrayList<>();
        lieuxReu.add(FakeReunionGenerator.salle1);
        lieuxReu.add(FakeReunionGenerator.salle2);
        lieuxReu.add(FakeReunionGenerator.salle3);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lieuxReu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lieuReunionSpinner.setAdapter(adapter);

        getIncomingIntent();

        addReunionBtn.setOnClickListener(v -> {
            getUserInputs();
            Intent intent = new Intent();
            intent.putExtra("INTENT_RETOUR_MANAGE_REUNION", mReunion);
            setResult(RESULT_OK, intent);
            Toast.makeText(ManageReunionActivity.this, "Reunion "+mReunion.getId()+" correctement ajouté", Toast.LENGTH_SHORT).show();
            finish();
        });

        updateReunionBtn.setOnClickListener(v -> {
            getUserInputs();
            Intent intent = new Intent();
            intent.putExtra("INTENT_RETOUR_MANAGE_REUNION", mReunion);
            setResult(RESULT_OK, intent);
            Toast.makeText(ManageReunionActivity.this, "Informations Reunion "+mReunion.getId()+" correctement mises à jours", Toast.LENGTH_SHORT).show();
            finish();
        });

        mDisplayTimePicker.setOnClickListener(v -> showTimePickerDialog());

        mDisplayDatePicker.setOnClickListener(v -> showDatePickerDialog());
    }

    private void initViews() {
        idReunionEditText = findViewById(R.id.idReunion_edt);
        mDisplayTimePicker = findViewById(R.id.openDialogTP_txt);
        mDisplayDatePicker = findViewById(R.id.openDialogDP_txt);
        lieuReunionSpinner = findViewById(R.id.lieuReunion_spinner);
        sujetReunionEditText = findViewById(R.id.sujetReunion_edt);
        emailReunionEditText = findViewById(R.id.emailReunion_edt);

        addReunionBtn = findViewById(R.id.addReu_btn);
        updateReunionBtn = findViewById(R.id.updateReu_btn);
    }

    private void getUserInputs(){
        mReunion.setId(Integer.parseInt(idReunionEditText.getText().toString()));
        mReunion.setHeureReunion(mDisplayTimePicker.getText().toString());
        mReunion.setDateReunion(mDisplayDatePicker.getText().toString());
        mReunion.setLieuReunion(lieuReunionSpinner.getSelectedItem().toString());
        mReunion.setSujetReunion(sujetReunionEditText.getText().toString());
        mReunion.setEmail(emailReunionEditText.getText().toString());
    }

    private void getIncomingIntent(){
        if (getIntent().hasExtra("UPDATE_REUNION")){
            mReunion = (Reunion)getIntent().getSerializableExtra("UPDATE_REUNION");
            idReunionEditText.setText(String.valueOf(mReunion.getId()));
            mDisplayTimePicker.setText(mReunion.getHeureReunion());
            mDisplayDatePicker.setText(mReunion.getDateReunion());

            for (int index = 0; index < lieuReunionSpinner.getAdapter().getCount(); index++){
                if (lieuReunionSpinner.getAdapter().getItem(index).equals(mReunion.getLieuReunion())){
                    lieuReunionSpinner.setSelection(index);
                }
            }

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
        mReunion.setDateReunion(dayOfMonth+"/"+(month+1)+"/"+year);
        mDisplayDatePicker.setText(mReunion.getDateReunion());
    }
}
