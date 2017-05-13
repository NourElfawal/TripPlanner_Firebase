package com.example.belal.tripplanner;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RoundTripScreen extends AppCompatActivity {

    StringBuffer myId;
    static  String trip_Id_Time[];
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    Button   TimeBtn,DateBtn,add;
    EditText nameOfTrip,notes;
    AutoCompleteTextView startPoint,endPoint;
    int i=0;
    String   start_point;
    String end_point;
    Intent intent1;
    PendingIntent pendingIntent;
    int hour, min;
    int yr, month, day;
    Calendar today;
    Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_trip_screen);

        TimeBtn = (Button) findViewById(R.id.TimeBtn);
        DateBtn = (Button) findViewById(R.id.DateBtn);

        add = (Button) findViewById(R.id.button3);

        nameOfTrip= (EditText) findViewById(R.id.editText1);
        startPoint = (AutoCompleteTextView) findViewById(R.id.editText2);
        endPoint= (AutoCompleteTextView) findViewById(R.id.editText3);
        notes= (EditText) findViewById(R.id.editText6);



        today = Calendar.getInstance();
        yr = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH);
        day = today.get(Calendar.DAY_OF_MONTH);


// ======== action Of AutoComplette Start And End Point ==============//
        startPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    i=1;

                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(RoundTripScreen.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);


                } catch (Exception e) {
                    // TODO: Handle the error.
                }

            }
        });

        endPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    i=2;
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(RoundTripScreen.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

                } catch (Exception e) {
                    // TODO: Handle the error.
                }

            }
        });
        //================================================//



        TimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TimePickerDialog(
                        RoundTripScreen.this, mTimeSetListener, hour, min, false).show();
            }
        });
        DateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RoundTripScreen.this, mDateSetListener, yr, month, day).show();


            }
        });


//=============  Get All Values From Inputs    =========================================================//

        // =================== Add Action Button =====================//

           start_point=DialogActivity.arr_Of_Round_Trip[2];
         end_point=DialogActivity.arr_Of_Round_Trip[1];
startPoint.setText(start_point);
        endPoint.setText(end_point);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String  trip_name=nameOfTrip.getText().toString();

                    String  date=DateBtn.getText().toString();
                    String time=TimeBtn.getText().toString();
                    String note=notes.getText().toString();

                    String dateString = ""+date+" " +time;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
                    Date convertedDate = new Date();
                    try {
                        convertedDate = dateFormat.parse(dateString);
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    System.out.println("Format ****    "+convertedDate);



                    MyDatabaseAdapter db=new MyDatabaseAdapter(RoundTripScreen.this);
                    db.open();
                    long id=db.insertContact(trip_name,start_point,end_point,date,time,note,"One Direction Trip","not_done");

                    if (id>  -1){
                        Toast.makeText(RoundTripScreen.this, "Inserted Done ", Toast.LENGTH_SHORT).show();

                    }      else{

                        Toast.makeText(RoundTripScreen.this, "Not Inserted", Toast.LENGTH_SHORT).show();
                    }

                    Cursor c = db.getLastId();
                    if (c.moveToLast()) {
                        myId=new StringBuffer();
                        do {
                            ShowDataTrip(c);
                        } while (c.moveToNext());
                    }

                    trip_Id_Time =myId.toString().split("#");


                    System.out.println("my Id At Add >> "+Long.parseLong(trip_Id_Time[0]));
                    //Calendar calendar;

                    today.setTime(convertedDate);

                    // TODO Alarm Intent Code
                    intent1 = new Intent(RoundTripScreen.this, DialogActivity.AlarmReceiver.class);

                    int your_Id_After_Parse=Integer.parseInt(trip_Id_Time[0]);
                    intent1.putExtra("Test",your_Id_After_Parse);
                    pendingIntent = PendingIntent.getBroadcast(RoundTripScreen.this,your_Id_After_Parse, intent1, PendingIntent.FLAG_ONE_SHOT);

                    AlarmManager am = (AlarmManager) getSystemService(RoundTripScreen.this.ALARM_SERVICE);
                    am.set(AlarmManager.RTC_WAKEUP, today.getTimeInMillis(), pendingIntent);

                    db.close();



                }
            });


        }



    private void ShowDataTrip(Cursor c) {
        myId.append(c.getString(0)+"#");

    }

//--- onActivitResult Related to start and end Point AutoCompltete-----------------------------------//

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                place = PlaceAutocomplete.getPlace(RoundTripScreen.this, data);
                Log.i("****", "Place" + place.getName());
                if(i==1) {
                    startPoint.setText(place.getName());
                }
                if(i ==2){

                    endPoint.setText(place.getName());
                }
            }
            else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(RoundTripScreen.this, data);
                // TODO: Handle the error.

            }

            else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
    //-----------------------------------------//


    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
    {
        public void onDateSet(
                DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            yr = year;
            month = monthOfYear;
            day = dayOfMonth;


            DateBtn.setText(""+ (month+1)+ "/" + day + "/" + yr);
        }
    };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener()
    {
        public void onTimeSet(
                TimePicker view, int hourOfDay, int minuteOfHour)
        {
            //   view.setIs24HourView(true);
            hour = hourOfDay;
            min = minuteOfHour;
//                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss aa");

            Date date = new Date(0,0,0, hour, min);

            String strDate = timeFormat.format(date);
            try {
                today.setTime(timeFormat.parse(strDate));

            } catch (ParseException e) {
                e.printStackTrace();
            }
            TimeBtn.setText(""+strDate);

        }
    };


}
