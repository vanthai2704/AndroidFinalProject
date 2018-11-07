package com.example.fastjobs.firebase;

import android.support.annotation.NonNull;

import com.example.fastjobs.MessageFragment.ListMessageFragment;
import com.example.fastjobs.Adapter.ListMessageAdapter;
import com.example.fastjobs.Entity.Message;
import com.example.fastjobs.Entity.MessageOne;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageSupport extends BaseSupport{
    private static MessageSupport instance = null;
    public static MessageSupport getInstance(){
        if(instance == null){
            instance = new MessageSupport();
        }
        return instance;
    }
    private DatabaseReference dbMessage;
    public MessageSupport(){
        dbMessage = db.child("message");
    }
    public void chat(String from, String to, Date time, String content){
        DatabaseReference dbMessageFrom = dbMessage.child(from).child(to);
        DatabaseReference dbMessageTo = dbMessage.child(to).child(from);
        String keyFrom = dbMessageFrom.push().getKey();
        String keyTo = dbMessageTo.push().getKey();
        Message message = new Message(content, from, time);
        dbMessageFrom.child(keyFrom).setValue(message).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
        dbMessageTo.child(keyTo).setValue(message).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getMessage(final String from, final String to, final int page, final int pageSize,
                           final CallbackSupport callbackSupport){
        DatabaseReference dbMessageFrom = dbMessage.child(from).child(to);
        dbMessageFrom.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int index = 1;
                List<Message> messages = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    if(index>(page-1)*pageSize && index<= page*pageSize){
                        messages.add(item.getValue(Message.class));
                    }else {
                        break;
                    }
                    index++;
                }
                callbackSupport.onCallback(null, null, messages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getMessageOne(final ListMessageFragment listMessageFragment, final String from, final int page, final int pageSize,
                              final CallbackSupport callbackSupport){
        final DatabaseReference dbMessageOne = dbMessage.child(from);
        dbMessageOne.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int index = 1;
                List<MessageOne> messages = new ArrayList<>();
                for (DataSnapshot item : dataSnapshot.getChildren())
                {
                    if(index>(page-1)*pageSize && index <= page*pageSize){
                        MessageOne messageOne = new MessageOne(from,item.getKey(),"");
                        final int position = index - 1;
                        dbMessageOne.child(item.getKey()).orderByKey()
                                .limitToLast(1).addListenerForSingleValueEvent(
                                        new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                ListMessageAdapter listMessageAdapter
                                                        = (ListMessageAdapter)listMessageFragment.getListViewListMessage().getAdapter();
                                                Message lastMessage = dataSnapshot.getChildren().iterator().next().getValue(Message.class);
                                                listMessageAdapter.setTextContent(position, lastMessage.getContent());
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        }
                                );

                        messages.add(messageOne);
                    }else {
                        break;
                    }
                    index++;
                }
                callbackSupport.onCallback(null, null, messages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public DatabaseReference getDatabaseReferenceMessage(final String from, final String to){
        return dbMessage.child(from).child(to);
    }
}
