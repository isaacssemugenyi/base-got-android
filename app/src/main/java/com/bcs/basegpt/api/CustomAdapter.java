package com.bcs.basegpt.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bcs.basegpt.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<ChatItem> {
    private Context mContext;
    private List<ChatItem> prevChats;

    public CustomAdapter(Context context, List<ChatItem> chats) {
        super(context, 0, chats);
        mContext = context;
        prevChats = chats;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item_layout, parent, false);
        }

        ChatItem currentItem = prevChats.get(position);

        TextView questionTextView = listItem.findViewById(R.id.questionTextView);
        FloatingActionButton deleteButton = listItem.findViewById(R.id.delete_query_btn);
        questionTextView.setText(currentItem.getQuestion());
        deleteButton.setTag(currentItem.getId());
        listItem.setTag(currentItem.getId());

        return listItem;
    }
}


