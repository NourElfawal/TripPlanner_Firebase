package com.example.belal.tripplanner;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */

public class PastFragment extends Fragment {

    static  String TRIP_NAME,START_POINT,END_POINT,DATE,TIME,NOTES,TRIP_TYPE;

    StringBuffer myBufferPastTrips,myBufferDialogData,idBuffer;
    ListView simpleList;
    String [] data;
    String [] data2;
    static  String[] IDS;
    static  int  postion=0;
    static long myTripId;

    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_past, container, false);

        MyDatabaseAdapter db = new MyDatabaseAdapter(getActivity());
        db.open();
        Cursor c = db.getAllContacts_Past_Trips();
        if (c.moveToFirst()) {
            myBufferPastTrips = new StringBuffer();
            idBuffer=new StringBuffer();
            do {
                ShowDataTrip(c);
            } while (c.moveToNext());
        }

        try {
            if (myBufferPastTrips == null) {

                Toast.makeText(getActivity(), "Just Empty!", Toast.LENGTH_SHORT).show();
            }

            else {
                simpleList = (ListView) v.findViewById(R.id.listViewPastTrips);

                data = myBufferPastTrips.toString().split("#");


                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);

                simpleList.setAdapter(adapter);


                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
                        builder.setMessage("Are you sure To delete Past Trip ?");
                        builder.setTitle("Confirm Request");
                        builder.setNegativeButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                MyDatabaseAdapter db = new MyDatabaseAdapter(getActivity());
                                db.open();
                                IDS=idBuffer.toString().split("#");
                                Cursor c = db.getId(Long.parseLong(IDS[position]));
                                System.out.println("***  getId() >>  "+Long.parseLong(IDS[position]));


                                if (c.moveToFirst()) {
                                    getId(c);

                                }else {
                                    Toast.makeText(getActivity(), "No Trip found", Toast.LENGTH_LONG).show();
                                }

                                if (db.deleteContact(Long.parseLong(IDS[position])))
                                    Toast.makeText(getActivity(), "Delete successful.", Toast.LENGTH_SHORT).show();

                                else
                                    Toast.makeText(getActivity(), "Delete  failed.", Toast.LENGTH_SHORT).show();
                                db.close();


                            }
                        });

builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
    }
});
                    builder.show();

                    }
                });

            }
        } catch (NullPointerException ex) {

            Toast.makeText(getActivity(), "Null Pointer Exception!", Toast.LENGTH_SHORT).show();
             ex.printStackTrace();

        }

        return  v;



    }

    private void ShowDataTrip(Cursor c) {
        // idBuffer.append(c.getString(0)+"#");
        myTripId=Long.parseLong(c.getString(0));
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+myTripId);
        idBuffer.append(myTripId+"#");

        myBufferPastTrips.append(c.getString(1)+"#");
    }



    private void getId(Cursor c) {
        // myTripId=Long.parseLong(c.getString(0));
        // System.out.println("Under ******   "+myTripId);
        myTripId=Long.parseLong(c.getString(0));
        System.out.println(">>>>>>>>>>>>>>getId()>>>>>>>>>>>>>>>>>>>>>>>>>>> "+myTripId);
        idBuffer.append(myTripId+"#");
    }

}

