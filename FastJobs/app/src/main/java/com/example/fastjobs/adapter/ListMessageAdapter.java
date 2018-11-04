package com.example.fastjobs.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fastjobs.MessageFragment.ListMessageFragment;
import com.example.fastjobs.R;
import com.example.fastjobs.entity.MessageOne;
import com.example.fastjobs.firebase.CallbackSupport;

import java.util.ArrayList;
import java.util.List;

public class ListMessageAdapter extends BaseAdapter {
    private List<MessageOne> messages;
    private ListMessageFragment listMessageFragment;

    public ListMessageAdapter(List<MessageOne> messages, ListMessageFragment listMessageFragment) {
        this.messages = messages;
        this.listMessageFragment = listMessageFragment;
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

    private List<ListMessageHolder> listMessageHolderCallbacks = new ArrayList<>();
    private View tmpConvertView;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ListMessageHolder listMessageHolder = null;
        if(convertView == null){
            convertView = listMessageFragment.getLayoutInflater().inflate(R.layout.list_message_layout, null);
            listMessageHolder = new ListMessageHolder();
            listMessageHolder.textViewListMessageTo = convertView.findViewById(R.id.textViewListMessageTo);
            listMessageHolder.textViewListMessageContent = convertView.findViewById(R.id.textViewListMessageContent);
            convertView.setTag(listMessageHolder);
        }else {
            listMessageHolder = (ListMessageHolder)convertView.getTag();
        }
        listMessageHolderCallbacks.add(listMessageHolder);
        listMessageHolder.textViewListMessageTo.setText(messages.get(position).getTo());
        tmpConvertView = convertView;
        return convertView;
    }

    public void setTextContent(int position, String content){
        for(int i = 0; i < listMessageHolderCallbacks.size(); i++){
            if(i == position){
                listMessageHolderCallbacks.get(i).textViewListMessageContent.setText(content);
            }
        }
    }

    class ListMessageHolder{
        public TextView textViewListMessageTo;
        public TextView textViewListMessageContent;
    }
}
