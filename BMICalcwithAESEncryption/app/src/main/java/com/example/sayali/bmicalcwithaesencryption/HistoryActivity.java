package com.example.sayali.bmicalcwithaesencryption;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class HistoryActivity extends AppCompatActivity {

    ListView lvData;
    ArrayList<Bm> b = new ArrayList<>();
    ArrayAdapter<Bm> ad;
    FirebaseDatabase database;
    DatabaseReference myRef;

    private DatabaseReference databaseReference;

    private String stringMessage;
    private byte encryptionKey[] = {9, 115, 51, 43, 23, 45, 12, 67, 89, 123, 76, 43, 90, 78, 65, 15};
    private Cipher cipher, decipher;
    SecretKeySpec secretKeySpec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lvData = findViewById(R.id.lvData);
        databaseReference = FirebaseDatabase.getInstance().getReference("Message");
       // myRef = database.getReference("bm");

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                b.clear();
//                for (DataSnapshot d : dataSnapshot.getChildren()) {
//                    Bm data = d.getValue(Bm.class);
//                    b.add(data);
//
//                }
//                ad=new ArrayAdapter<Bm>(HistoryActivity.this,android.R.layout.simple_list_item_1,b);
//                lvData.setAdapter(ad);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

        try {
            //databaseReference = FirebaseDatabase.getInstance().getReference("bm");

            try {
                cipher = Cipher.getInstance("AES");
                decipher = Cipher.getInstance("AES");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }

            secretKeySpec = new SecretKeySpec(encryptionKey, "AES");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                b.clear();
//                for (DataSnapshot d : dataSnapshot.getChildren()) {
//                    ChatApp data = d.getValue(ChatApp.class);
////                    String originalData = AESDecryption();
//                    b.add(data);

                    stringMessage = dataSnapshot.getValue().toString();
                    stringMessage = stringMessage.substring(1, stringMessage.length() - 1);

                    String[] stringMessageArray = stringMessage.split(", ");
                    Arrays.sort(stringMessageArray);
                    String[] stringFinal = new String[stringMessageArray.length * 2];


                    try {
                        for (int i = 0; i < stringMessageArray.length; i++) {
                            String[] stringKeyValue = stringMessageArray[i].split("=", 2);
                            stringFinal[2 * i] = (String) android.text.format.DateFormat.format("dd-MM-yyyy hh:mm:ss", Long.parseLong(stringKeyValue[0]));

                            stringFinal[2 * i + 1] = AESDecryption(stringKeyValue[1]);


                        }
//                ad = new ArrayAdapter<ChatApp>(MainActivity.this, android.R.layout.simple_list_item_1, b);
//                listview.setAdapter(ad);
                        lvData.setAdapter(new ArrayAdapter<String>(HistoryActivity.this, android.R.layout.simple_list_item_1, stringFinal));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String AESDecryption(String string) throws UnsupportedEncodingException {


        byte[] encryptedbyte=string.getBytes("ISO-8859-1");
        String decryptionString=string;

        byte[] deryptedByte;

        try {
            decipher.init(cipher.DECRYPT_MODE,secretKeySpec);
            deryptedByte=decipher.doFinal(encryptedbyte);
            decryptionString=new String(deryptedByte);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }



        return decryptionString;

    }
}