package com.example.urbanutils.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.urbanutils.R;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RequestedUserAdapter extends RecyclerView.Adapter<RequestedUserAdapter.RequestedUserViewHolder> {


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

   private  OnItemClickListener listener;
    private ArrayList<String> arrayList;



    public RequestedUserAdapter(OnItemClickListener listener, ArrayList<String> arrayList){
        this.listener = (OnItemClickListener) listener;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public RequestedUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.requested_user_list_view,parent,false);
        return new RequestedUserViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestedUserViewHolder holder, int position) {
        String obj=arrayList.get(position);
        holder.userPhoneTextview.setText(obj);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RequestedUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView  userPhoneTextview;
        OnItemClickListener onItemClickListener;
        public RequestedUserViewHolder(@NonNull View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            userPhoneTextview=itemView.findViewById(R.id.user_phone_id);
            this.onItemClickListener=onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition() );
        }
    }
}
