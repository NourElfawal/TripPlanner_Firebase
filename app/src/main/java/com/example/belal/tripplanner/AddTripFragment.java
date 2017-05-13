package com.example.belal.tripplanner;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
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

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddTripFragment extends Fragment{
    int i2;
    static  StringBuffer ARRAY_IDS;
    StringBuffer myId;
    static  String trip_Id_Time[];
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
	Button   delete,TimeBtn,DateBtn,edit,add;
    EditText nameOfTrip,notes;
    AutoCompleteTextView startPoint,endPoint;
    RadioButton oneDirectionRadio,roundDirectionRadio;
    int i=0;

    Intent intent1;
    PendingIntent pendingIntent;
    int hour, min;
    int yr, month, day;
    Calendar today;
    Place place;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addtrip, container, false);

        TimeBtn = (Button) v.findViewById(R.id.TimeBtn);
        DateBtn = (Button) v.findViewById(R.id.DateBtn);

        add = (Button) v.findViewById(R.id.button3);
        edit = (Button) v.findViewById(R.id.button2);
        delete = (Button) v.findViewById(R.id.button1);


        nameOfTrip= (EditText) v.findViewById(R.id.editText1);
        startPoint = (AutoCompleteTextView) v.findViewById(R.id.editText2);
        endPoint= (AutoCompleteTextView) v.findViewById(R.id.editText3);
        notes= (EditText) v.findViewById(R.id.editText6);
        oneDirectionRadio= (RadioButton) v.findViewById(R.id.radio0);
        roundDirectionRadio= (RadioButton) v.findViewById(R.id.radio1);




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

                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(getActivity());
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
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(getActivity());
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
                        getActivity(), mTimeSetListener, hour, min, false).show();
            }
        });
        DateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), mDateSetListener, yr, month, day).show();


            }
        });


//     //  if(MainActivity.i==1){
//
//    	   add.setVisibility(View.INVISIBLE);
//    	   edit.setVisibility(View.VISIBLE);
//    	   delete.setVisibility(View.VISIBLE);
//      // }

//=============  Get All Values From Inputs    =========================================================//

  // =================== Add Action Button =====================//

       if(ProfileNavigationDrawer.i2==1) {
           add.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   String trip_Type;
                   String  trip_name=nameOfTrip.getText().toString();
                   String   start_point=startPoint.getText().toString();
                   String end_point=endPoint.getText().toString();
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

                   if(oneDirectionRadio.isChecked()){

                       trip_Type=oneDirectionRadio.getText().toString().trim();

                   }else {
                       trip_Type=roundDirectionRadio.getText().toString().trim();
                   }

                   MyDatabaseAdapter db=new MyDatabaseAdapter(getActivity());
                   db.open();
                   long id=db.insertContact(trip_name,start_point,end_point,date,time,note,trip_Type,"not_done");

                   if (id>  -1){
                       Toast.makeText(getActivity(), "Inserted Done ", Toast.LENGTH_SHORT).show();

                   }      else{

                       Toast.makeText(getActivity(), "Not Inserted", Toast.LENGTH_SHORT).show();
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
                   intent1 = new Intent(getActivity(), DialogActivity.AlarmReceiver.class);

                   int your_Id_After_Parse=Integer.parseInt(trip_Id_Time[0]);
                   intent1.putExtra("Test",your_Id_After_Parse);
                    pendingIntent = PendingIntent.getBroadcast(getActivity(),your_Id_After_Parse, intent1, PendingIntent.FLAG_ONE_SHOT);

                   AlarmManager am = (AlarmManager)getActivity().getSystemService(getActivity().ALARM_SERVICE);
                  // am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                    //       AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
                   System.out.println("      ***** After alarm *****   "+your_Id_After_Parse);


                   am.set(AlarmManager.RTC_WAKEUP, today.getTimeInMillis(), pendingIntent);

               //    ARRAY_IDS.append(your_Id_After_Parse+"#");

                   db.close();


//                   DBAdapter db = new DBAdapter();
//                   db.open(getActivity());
//                   db.insertTripInfo(trip_name, start_point, end_point, date, time, note, trip_Type);
//                   Toast.makeText(getActivity(), "Trip Inserted ! ", Toast.LENGTH_SHORT).show();

               }
           });
       }
       else  if(ProfileNavigationDrawer.i2==2){

           add.setVisibility(View.INVISIBLE);
	       edit.setVisibility(View.VISIBLE);
     	   delete.setVisibility(View.VISIBLE);

           nameOfTrip.setText(UpcomingFragment.TRIP_NAME);
           startPoint.setText(UpcomingFragment.START_POINT);
           endPoint.setText(UpcomingFragment.END_POINT);
           DateBtn.setText(UpcomingFragment.DATE);
           TimeBtn.setText(UpcomingFragment.TIME);
           notes.setText(UpcomingFragment.NOTES);
           if(UpcomingFragment.TRIP_TYPE.equals("One Direction Trip")){
             oneDirectionRadio.setChecked(true);

           }else {
               roundDirectionRadio.setChecked(true);

           }
           // ===============  Action of Edit Button ======================//
edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String trip_typeCheck;
        if(oneDirectionRadio.isChecked()){
            trip_typeCheck=oneDirectionRadio.getText().toString();

        }else {
            trip_typeCheck=roundDirectionRadio.getText().toString();


        }


        String name_Of_Trip=nameOfTrip.getText().toString();
        String start_Point=startPoint.getText().toString();
        String end_Point=endPoint.getText().toString();
        String date_Trip=DateBtn.getText().toString();
        String time_Trip=TimeBtn.getText().toString();
        String notes_Trip=notes.getText().toString();





        MyDatabaseAdapter db =new MyDatabaseAdapter(getActivity());
        db.open();
        System.out.println("at edit >> "+Long.parseLong(UpcomingFragment.IDS[UpcomingFragment.postion]));
        if (db.updateContact(Long.parseLong(UpcomingFragment.IDS[ UpcomingFragment.postion]),name_Of_Trip, start_Point,end_Point,date_Trip,time_Trip,notes_Trip,trip_typeCheck,"not done" ))
            Toast.makeText(getActivity(), "Update successful.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getActivity(), "Update failed.", Toast.LENGTH_LONG).show();
            db.close();


        String dateString = ""+date_Trip+" " +time_Trip;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

           today.setTime(convertedDate);

        Intent  intent1 = new Intent(getActivity(), DialogActivity.AlarmReceiver.class);
        int your_Id_After_Parse=Integer.parseInt(UpcomingFragment.IDS[UpcomingFragment.postion]);
        intent1.putExtra("Test",your_Id_After_Parse);
      PendingIntent  pendingIntent = PendingIntent.getBroadcast(getActivity(),your_Id_After_Parse, intent1, PendingIntent.FLAG_ONE_SHOT);

       AlarmManager am = (AlarmManager)getActivity().getSystemService(getActivity().ALARM_SERVICE);

        am.set(AlarmManager.RTC_WAKEUP, today.getTimeInMillis(), pendingIntent);


                 }
});


  // Delete Action in Dialog  ========================//
           delete.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   MyDatabaseAdapter db =new MyDatabaseAdapter(getActivity());
                   db.open();
                   if (db.deleteContact(Long.parseLong(UpcomingFragment.IDS[ UpcomingFragment.postion])))
                       Toast.makeText(getActivity(), "Delete successful.", Toast.LENGTH_SHORT).show();

                   else
                       Toast.makeText(getActivity(), "Delete  failed.", Toast.LENGTH_SHORT).show();
                       db.close();
                   Intent  intent1 = new Intent(getActivity(), DialogActivity.AlarmReceiver.class);
                   int your_Id_After_Parse=Integer.parseInt(UpcomingFragment.IDS[UpcomingFragment.postion]);
                   intent1.putExtra("Test",your_Id_After_Parse);
                   PendingIntent  pendingIntent = PendingIntent.getBroadcast(getActivity(),your_Id_After_Parse, intent1, PendingIntent.FLAG_ONE_SHOT);

                   AlarmManager am = (AlarmManager)getActivity().getSystemService(getActivity().ALARM_SERVICE);
                   am.cancel(pendingIntent);
                  // am.set(AlarmManager.RTC_WAKEUP, today.getTimeInMillis(), pendingIntent);


               }
           });

       }

        return v;
    }

    private void ShowDataTrip(Cursor c) {

       myId.append(c.getString(0)+"#");

    }

//--- onActivitResult Related to start and end Point AutoCompltete-----------------------------------//

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                 place = PlaceAutocomplete.getPlace(getActivity(), data);
                Log.i("****", "Place" + place.getName());
                if(i==1) {
                    startPoint.setText(place.getName());
                }
                if(i ==2){

                    endPoint.setText(place.getName());
                }
            }
            else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
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
