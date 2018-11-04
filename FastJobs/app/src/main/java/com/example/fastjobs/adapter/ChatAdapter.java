package com.example.fastjobs.adapter;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fastjobs.MessageActivity;
import com.example.fastjobs.MessageFragment.MessageFragment;
import com.example.fastjobs.R;
import com.example.fastjobs.entity.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.List;

public class ChatAdapter extends BaseAdapter {

    private List<Message> messages;
    private MessageFragment messageFragment;

    public ChatAdapter(List<Message> messages, MessageFragment messageFragment) {
        this.messages = messages;
        this.messageFragment = messageFragment;
    }
    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        MessageHolder messageHolder = null;
        if(convertView == null){
            convertView = messageFragment.getLayoutInflater().inflate(R.layout.message_layout, null);
            messageHolder = new MessageHolder();
            messageHolder.textViewTo = convertView.findViewById(R.id.textViewTo);
            messageHolder.textViewTimeTo = convertView.findViewById(R.id.textViewTimeTo);
            convertView.setTag(messageHolder);
        }else {
            messageHolder = (MessageHolder)convertView.getTag();
        }

        if(messages.get(position).getFrom().equalsIgnoreCase(
                FirebaseAuth.getInstance().getCurrentUser().getEmail().replaceAll("\\.","_"))){
            ((LinearLayout.LayoutParams) messageHolder.textViewTo.getLayoutParams()).gravity = Gravity.END;
            ((LinearLayout.LayoutParams) messageHolder.textViewTimeTo.getLayoutParams()).gravity = Gravity.END;
            messageHolder.textViewTo.setBackgroundResource(R.drawable.message_chat);
        }else {
            ((LinearLayout.LayoutParams) messageHolder.textViewTo.getLayoutParams()).gravity = Gravity.START;
            ((LinearLayout.LayoutParams) messageHolder.textViewTimeTo.getLayoutParams()).gravity = Gravity.START;
            messageHolder.textViewTo.setBackgroundResource(R.drawable.message_chat_other);
        }
        if(position > 0 && position < messages.size() - 1 &&
                dateFormat.format(messages.get(position).getTime()).equals(dateFormat.format(messages.get(position-1).getTime()))
                && messages.get(position).getFrom().equalsIgnoreCase(messages.get(position - 1).getFrom())
                && messages.get(position).getFrom().equalsIgnoreCase(messages.get(position + 1).getFrom())){
            messageHolder.textViewTimeTo.setVisibility(View.GONE);
        }else {
            messageHolder.textViewTimeTo.setText(dateFormat.format(messages.get(position).getTime()));
            messageHolder.textViewTimeTo.setVisibility(View.VISIBLE);
        }
        messageHolder.textViewTo.setText(messages.get(position).getContent());

        return convertView;
    }

    class MessageHolder{
        public TextView textViewTo;
        public TextView textViewTimeTo;
    }
}
