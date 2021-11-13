package com.example.testfirebase.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testfirebase.databinding.ItemContainerRecievedMessageBinding;
import com.example.testfirebase.databinding.ItemContainerSentMessageBinding;
import com.example.testfirebase.models.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<ChatMessage> chatMessages;
    private final Bitmap recieverProfileImage;
    private final String senderId;

    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED=2;

    //Constructors of the above three final variables...
    public ChatAdapter(List<ChatMessage> chatMessages, Bitmap recieverProfileImage, String senderId) {
        this.chatMessages = chatMessages;
        this.recieverProfileImage=recieverProfileImage;
        this.senderId=senderId;
    }


    //Sent or Recieved Message
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_SENT)
        {
            return new SentMessageViewHolder(
                    ItemContainerSentMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,false
                    )
            );
        }else {
            return new RecievedMessageViewHolder(
                    ItemContainerRecievedMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }
    }

    //Sender or Reciever Message position in Recycler View
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(getItemViewType(position)==VIEW_TYPE_SENT){
            ((SentMessageViewHolder)holder).setData(chatMessages.get(position));
        } else {
            ((RecievedMessageViewHolder)holder).setData(chatMessages.get(position),recieverProfileImage);
        }

    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(chatMessages.get(position).senderId.equals(senderId))
        {
            return VIEW_TYPE_SENT;
        }
        else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    //setting message and date time in recyler View Chatscreen activity
    static class SentMessageViewHolder extends RecyclerView.ViewHolder {

        private final ItemContainerSentMessageBinding binding;

        SentMessageViewHolder(ItemContainerSentMessageBinding itemContainerSentMessageBinding)
        {
            super(itemContainerSentMessageBinding.getRoot());
            binding=itemContainerSentMessageBinding;
        }

        void setData(ChatMessage chatMessage)
        {
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
        }
    }

    //ChatMessage details like textmessage,datetime,and dp
    static class RecievedMessageViewHolder extends RecyclerView.ViewHolder {

        private final ItemContainerRecievedMessageBinding binding;

        RecievedMessageViewHolder(ItemContainerRecievedMessageBinding itemContainerRecievedMessageBinding)
        {
            super(itemContainerRecievedMessageBinding.getRoot());
            binding=itemContainerRecievedMessageBinding;
        }

        void setData(ChatMessage chatMessage,Bitmap receiverProfileImage)
        {
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
            binding.imageProfile.setImageBitmap(receiverProfileImage);
        }

    }
}
