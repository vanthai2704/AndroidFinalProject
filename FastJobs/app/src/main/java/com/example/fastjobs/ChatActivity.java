package com.example.fastjobs;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.fastjobs.adapter.ChatAdapter;
import com.example.fastjobs.entity.Message;
import com.example.fastjobs.firebase.CallbackSupport;
import com.example.fastjobs.firebase.LoginSupport;
import com.example.fastjobs.firebase.MessageSupport;
import com.firebase.ui.database.FirebaseListAdapter;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ListView listViewChat;
    private EditText editTextMessageContent;
    private MessageSupport messageSupport;
    private LoginSupport loginSupport;
    private String from;
    private String to;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messageSupport = new MessageSupport();
        loginSupport = new LoginSupport();
        Intent intent = getIntent();
        to = intent.getStringExtra("to").replaceAll("\\.","_");
        from = loginSupport.getCurrentUserEmail().replaceAll("\\.","_");
        listViewChat = findViewById(R.id.listViewChat);
        editTextMessageContent = findViewById(R.id.editTextMessageContent);
        displayChatMessages();
    }

    public void chat(View view){
        String content = editTextMessageContent.getText().toString();
        if(!content.equals("")){
            messageSupport.chat(from, to,new Date(),content);
            editTextMessageContent.setText("");
        }
    }


    public void displayChatMessages(){
        messageSupport.getMessage(from, to, 1, 100, new CallbackSupport<Message>() {

            @Override
            public void onCallback(Message message, String key,final List<Message> messages) {
                ChatAdapter chatAdapter = new ChatAdapter(messages, getActivity());
                listViewChat.setAdapter(chatAdapter);
                listViewChat.post(new Runnable() {
                    @Override
                    public void run() {
                        listViewChat.setSelection(messages.size() - 1);
                    }
                });
            }
        });
    }

    public final ChatActivity getActivity(){
        return this;
    }

    public void scrollChatToBottom(View view){
        listViewChat.post(new Runnable() {
            @Override
            public void run() {
                listViewChat.setSelection(listViewChat.getAdapter().getCount()-1);
            }
        });
    }
}
