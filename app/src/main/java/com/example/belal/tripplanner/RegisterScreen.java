package com.example.belal.tripplanner;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterScreen extends AppCompatActivity {
    static SharedPreferences sharedPreferences;
    //ImageView   image;
   //>> CircleImageView image;
    EditText userNameReg,passwordReg;
    Button saveReg;
    // For Galley And Camera
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    int flag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        // image= (ImageView) findViewById(R.id.imageView);

       //>> image= (CircleImageView) findViewById(R.id.imageView);

        userNameReg= (EditText) findViewById(R.id.usernameEditReg);
        passwordReg= (EditText) findViewById(R.id.usernameEditReg);
        saveReg= (Button) findViewById(R.id.saveBtnReg);




//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final String takePicture[]={"Camera","Gallery","Cancel"};
//                ArrayAdapter adapter=new ArrayAdapter(RegisterScreen.this,android.R.layout.simple_list_item_1,takePicture);
//
//                final Dialog dialog=new Dialog(RegisterScreen.this);
//                dialog.setContentView(R.layout.camera_gallery_dialog);
//                dialog.setTitle("     Take Picture");
//                ListView listView= (ListView) dialog.findViewById(R.id.listView);
//                listView.setAdapter(adapter);
//                dialog.show();
//
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                        if(takePicture[position].equals("Camera")){
//                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                            startActivityForResult(cameraIntent, 1);
//                            flag=1;
//
//                        }
//                        else if(takePicture[position].equals("Gallery")){
//                            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                            // Start the Intent
//                            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
//                            flag=2;
//
//                        }
//
//                        else  if(takePicture[position].equals("Cancel")){
//                            dialog.dismiss();
//
//                        }
//
//                    }
//                });






//            }
//        });




        saveReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get String name , password
                String name =userNameReg.getText().toString();
                String password=passwordReg.getText().toString();

                sharedPreferences=getSharedPreferences("myTxt",0);
                SharedPreferences.Editor  editor=sharedPreferences.edit();
                editor.putString("username",name);
                editor.putString("password",password);
                editor.commit();
                Toast.makeText(RegisterScreen.this, "Registered Done.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterScreen.this, LoginScreen.class));

//                // get And Convert image to Array of Bytes  .
//                image.buildDrawingCache();
//                Bitmap bitmap=image.getDrawingCache();
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
//                byte[] img = bos.toByteArray();
//
//                // DBAdapter object  And Calling Insert Method .
//                DBAdapter db=new DBAdapter();
//                db.open(RegisterScreen.this);
//                db.insertUserInfo(name, password, img);
//                Toast.makeText(RegisterScreen.this, "Done Register", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(RegisterScreen.this, LoginScreen.class));

            }
        });



    }

    // on Activity Result


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // super.onActivityResult(requestCode, resultCode, data);
//
//        if(flag==1) {
//            if (requestCode == 1 && resultCode == RESULT_OK) {
//                Bitmap photo = (Bitmap) data.getExtras().get("data");
//
//                image.setImageBitmap(photo);
//
//
//
//            }
//        }
//        else if(flag==2){
//
//
//            if (requestCode == RESULT_LOAD_IMG && resultCode == this.RESULT_OK && null != data) {
//                // Get the Image from data
//
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                // Get the cursor
//                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                imgDecodableString = cursor.getString(columnIndex);
//                Uri photoUri = data.getData();
//                String[] proj = {MediaStore.Images.Media.DATA};
//
//                Cursor actualimagecursor = managedQuery(photoUri, proj, null, null, null);
//                int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                actualimagecursor.moveToFirst();
//
//                //--------------------------------------------------------------------------------------------
//                cursor.close();
//
//                image.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
//
//                // Toast.makeText(RegisterScreen.this, "Image Uploaded", Toast.LENGTH_LONG).show();
//
//
//            } else {
//                Toast.makeText(RegisterScreen.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
//            }
//
//
//        }
//
//

    //}


}
