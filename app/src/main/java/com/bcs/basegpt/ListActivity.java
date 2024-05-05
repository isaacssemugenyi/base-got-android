package com.bcs.basegpt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bcs.basegpt.api.ChatItem;
import com.bcs.basegpt.api.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    Button backBtn;
    SQLiteDatabase myDb;
    LinearLayout questionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        backBtn = findViewById(R.id.backBtn);
        questionLayout = findViewById(R.id.questionLayout);

        goBack();

        myDb = openOrCreateDatabase("answersdb", MODE_PRIVATE, null);

        loadAllData();
    }

    private void loadAllData(){
        Cursor results = myDb.rawQuery("SELECT * FROM answers ORDER BY id DESC", null);

        if(results.getCount() == 0) {
            showMessage( "Error", "No records found" );
            return;
        }

//        Another alternative
        List<ChatItem> chatItemsList = new ArrayList<>();


        while (results.moveToNext()) {
            ChatItem newChatItem = new ChatItem(results.getString(0), results.getString(1));
            chatItemsList.add(newChatItem);
//            Log.d("ChatItem", "onCreate: "+ newChatItem);

            ListView listView = findViewById(R.id.prevQueryListView);

            CustomAdapter adapter = new CustomAdapter(this, chatItemsList);
            listView.setAdapter(adapter);
        }
    }

    public void onLinearLayoutClicked(View view){
        String questionId = view.getTag().toString();

        Log.d("default id", "onLinearLayoutClicked: "+questionId);

        Intent detailsIntent = new Intent(getApplicationContext(), SingleQueryActivity.class);
        detailsIntent.putExtra("questionId", questionId);
        startActivity(detailsIntent);

    }

    public void deleteCurrentQuery(View view){
        String questionId = view.getTag().toString();

        confirmDeleteOption(questionId);

        Log.d("default id", "deleteCurrentQuery: "+questionId);
    }


    private void goBack(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(addIntent);
            }
        });
    }

    private void confirmDeleteOption(String questionId){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation")
                .setMessage("Are you sure you want to delete this record?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDb.execSQL("DELETE FROM answers WHERE id='"+questionId+"'");
                        loadAllData();
                        showMessage("Success", "Record Deleted");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showMessage("Failure", "Deleting Cancelled");
                        dialog.dismiss();
                    }
                }).show();
    }

    private void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder
                .setCancelable(true)
                .setTitle(title)
                .setMessage(message)
                .show();
    }
}

