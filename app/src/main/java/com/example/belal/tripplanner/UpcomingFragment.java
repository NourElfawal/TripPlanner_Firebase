package com.example.belal.tripplanner;


import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.util.Log;
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

public class UpcomingFragment extends Fragment {

    static  String TRIP_NAME,START_POINT,END_POINT,DATE,TIME,NOTES,TRIP_TYPE;

    StringBuffer myBufferUpComingTrips,myBufferDialogData,idBuffer;
    ListView simpleList;
    String [] data;
    String [] data2;
      static  String[] IDS;
    static  int  postion;
   static long myTripId;

    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_upcoming, container, false);

        MyDatabaseAdapter db = new MyDatabaseAdapter(getActivity());
        db.open();
        Cursor c = db.getAllContacts();
        if (c.moveToFirst()) {
            myBufferUpComingTrips = new StringBuffer();
            idBuffer=new StringBuffer();
            do {
                ShowDataTrip(c);
            } while (c.moveToNext());
        }

        try {
            if (myBufferUpComingTrips == null) {

                Toast.makeText(getActivity(), "Just Empty!", Toast.LENGTH_SHORT).show();
            } else {
                simpleList = (ListView) v.findViewById(R.id.listView1);

                data = myBufferUpComingTrips.toString().split("#");


                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);

                simpleList.setAdapter(adapter);


                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MyDatabaseAdapter db = new MyDatabaseAdapter(getActivity());
                        db.open();
                         IDS=idBuffer.toString().split("#");
                         Cursor c = db.getId(Long.parseLong(IDS[position]));
                         System.out.println("***  getId() >>  "+Long.parseLong(IDS[position]));



                if (c.moveToFirst()) {
                   getId(c);

                }else {
                    Toast.makeText(getActivity(), "No contact found", Toast.LENGTH_LONG).show();
                }

                       // IDS = idBuffer.toString().split("#");

                        //System.out.println("***********************************   " + myTripId);

                        postion=Integer.parseInt(IDS[position]);


                        System.out.println("postion in upComing is >> "+postion);
                        Cursor c2 = db.getContact(Long.parseLong(IDS[position]));
                      //  position=postion;
                        System.out.println("My ID *** is    "+Long.parseLong(IDS[position]));
                      //  postion=Long.parseLong(IDS[position]);

                        System.out.println("Position >>  "+postion);
                        if (c2.moveToFirst()) {
                            myBufferDialogData = new StringBuffer();
                            ShowDataTripDialog(c2);
                        } else {
                            Toast.makeText(getActivity(), "No contact found", Toast.LENGTH_LONG).show();
                        }
                        db.close();
                        data2 = myBufferDialogData.toString().split("#");
                        final Dialog dialog1 = new Dialog(getActivity());
                        dialog1.setContentView(R.layout.dialog);

                        TextView txtName = (TextView) dialog1.findViewById(R.id.staticname1);
                        TextView txtStart = (TextView) dialog1.findViewById(R.id.staticstart1);
                        TextView txtEnd = (TextView) dialog1.findViewById(R.id.staticend1);
                        TextView txtDate = (TextView) dialog1.findViewById(R.id.staticdate1);
                        TextView txtTime = (TextView) dialog1.findViewById(R.id.statictime1);
                        TextView txtNotes = (TextView) dialog1.findViewById(R.id.staticnotes1);
                        TextView txtType = (TextView) dialog1.findViewById(R.id.statictype1);


                        txtName.setText(data2[0]);
                        txtStart.setText(data2[1]);
                        txtEnd.setText(data2[2]);
                        txtDate.setText(data2[3]);
                        txtTime.setText(data2[4]);
                        txtNotes.setText(data2[5]);
                        txtType.setText(data2[6]);

                        TRIP_NAME = data2[0];
                        START_POINT = data2[1];
                        END_POINT = data2[2];
                        DATE = data2[3];
                        TIME = data2[4];
                        NOTES = data2[5];
                        TRIP_TYPE = data2[6];


                        dialog1.setTitle("Trip Details");
                        dialog1.show();
                         postion=position;

                        Button dialogcancel = (Button) dialog1.findViewById(R.id.cancelbtn);
                        dialogcancel.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                dialog1.dismiss();
                            }
                        });


                        Button dialogedit = (Button) dialog1.findViewById(R.id.editbtn);

                        dialogedit.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                //Intent intent1 = new Intent (MainActivity.this,AddTripFragment.class);
                                //onAttachFragment(intent1);

                                ProfileNavigationDrawer.i2 = 2;
                                AddTripFragment Fragment1 = new AddTripFragment();
                                FragmentManager fmgr = getFragmentManager();
                                FragmentTransaction ftr = fmgr.beginTransaction();
                                ftr.replace(R.id.RelativeContainner, Fragment1, "Fragment1");
                                ftr.commit();
                                dialog1.dismiss();

                            }
                        });


                        Button deleteBtn = (Button) dialog1.findViewById(R.id.Deletebtn);

                        deleteBtn.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                //Intent intent1 = new Intent (MainActivity.this,AddTripFragment.class);
                                //onAttachFragment(intent1);

                                ProfileNavigationDrawer.i2 = 2;
                                AddTripFragment Fragment1 = new AddTripFragment();
                                FragmentManager fmgr = getFragmentManager();
                                FragmentTransaction ftr = fmgr.beginTransaction();
                                ftr.replace(R.id.RelativeContainner, Fragment1, "Fragment1");
                                ftr.commit();

                                dialog1.dismiss();
                            }
                        });

                    }
                });

            }
        } catch (NullPointerException ex) {

            Toast.makeText(getActivity(), "Null Pointer Exception!", Toast.LENGTH_SHORT).show();
            // Toast.makeText(getActivity(), ""+myBufferUpComingTrips, Toast.LENGTH_SHORT).show();

            //data=myBufferUpComingTrips.toString().split(":");


        }

        return  v;



    }

    private void ShowDataTrip(Cursor c) {
       // idBuffer.append(c.getString(0)+"#");
       myTripId=Long.parseLong(c.getString(0));
       System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+myTripId);
        idBuffer.append(myTripId+"#");

        myBufferUpComingTrips.append(c.getString(1)+"#");
    }

    private void ShowDataTripDialog(Cursor c) {

        myBufferDialogData.append(c.getString(1)+"#"+c.getString(2)+"#"+c.getString(3)+"#"+c.getString(4)+"#"+c.getString(5)+"#"+c.getString(6)+"#"+c.getString(7)+"#");
    }

    private void getId(Cursor c) {
       // myTripId=Long.parseLong(c.getString(0));
        // System.out.println("Under ******   "+myTripId);
        myTripId=Long.parseLong(c.getString(0));
        System.out.println(">>>>>>>>>>>>>>getId()>>>>>>>>>>>>>>>>>>>>>>>>>>> "+myTripId);
        idBuffer.append(myTripId+"#");
    }

}
