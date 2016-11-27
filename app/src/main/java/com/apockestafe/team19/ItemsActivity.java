package com.apockestafe.team19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemsActivity extends AppCompatActivity {
    private Button backButton, addItemButton;
    private ListView itemsListView;
    private EditText addItemEditText;
    private ArrayList<String> itemList;
    private DatabaseReference ref;
    private  FirebaseDatabase database;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        setTitle("Item List");
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        final String s = getIntent().getStringExtra("eventNumber");

        itemsListView = (ListView) findViewById(R.id.itemsListView);
        initializeItemsListView(s);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ItemsActivity.this, EventInfo.class);
                i.putExtra("eventNumber", s);
                startActivity(i);
            }
        });

        addItemEditText = (EditText) findViewById(R.id.addItemEditText);
        addItemButton = (Button) findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String value = addItemEditText.getText().toString();
                database = FirebaseDatabase.getInstance();
                ref = database.getReference("TEAM19");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                        itemList = dataSnapshot.child("events").child(s).child("itemsList").getValue(t);
                        if (itemList == null)
                            itemList = new ArrayList<>();
                        if(value.length() > 0)
                            itemList.add(value);
                        adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, itemList);
                        itemsListView.setAdapter(adapter);
                        addItemEditText.setText("");
                        ref.child("events").child(s).child("itemsList").setValue(itemList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private  void initializeItemsListView(final String s) {
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("TEAM19");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                itemList = dataSnapshot.child("events").child(s).child("itemsList").getValue(t);
                if (itemList != null) {
                    adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, itemList);
                    itemsListView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
