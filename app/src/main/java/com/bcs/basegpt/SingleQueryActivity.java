package com.bcs.basegpt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingleQueryActivity extends AppCompatActivity {

    Button backToListBtn;
    SQLiteDatabase myDb;
    LinearLayout singleQuestionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_query);
        backToListBtn = findViewById(R.id.back_to_list_btn);
        singleQuestionLayout = findViewById(R.id.singleQuestionLayout);

        String questionId = getIntent().getStringExtra("questionId");

        Log.d("My Question id", "onCreate: "+ questionId);

        goBack();


        myDb = openOrCreateDatabase("answersdb", MODE_PRIVATE, null);

        Cursor record = myDb.rawQuery("SELECT * FROM answers WHERE id = '"+questionId+"'", null);

        if(record.moveToFirst()){
            TextView questionView = singleQuestionLayout.findViewById(R.id.historyQuestionView);
            TextView answerView = singleQuestionLayout.findViewById(R.id.historyAnswerView);
//            answerView.setMovementMethod(new ScrollingMovementMethod());

            questionView.setText(record.getString(1));
            answerView.setText(record.getString(2));
        } else {
            showMessage( "Error", "No records found" );
            return;
        }
    }

    private void goBack(){
        backToListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(addIntent);
            }
        });
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