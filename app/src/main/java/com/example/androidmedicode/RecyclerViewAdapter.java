package com.example.androidmedicode;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>  {

    private Context mContext ;
    private List<MedicalEvents> mData ;

    public RecyclerViewAdapter(Context mContext, List<MedicalEvents> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.cardview_item_book, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        holder.tv_event_ID.setText(mData.get(position).getEventID());
        holder.tv_date.setText(mData.get(position).getDate());
        holder.tv_short_description.setText(mData.get(position).getShortDescription());
        holder.prof_thumbnail.setImageResource(mData.get(position).getThumbnail());

        //Set onclicklistener

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                creating intent
                Intent intent = new Intent (mContext, medical_Events_Activity.class);

//                passing data to medical events activity
                intent.putExtra("medicaleventID", mData.get(position).getEventID());
                intent.putExtra("date", mData.get(position).getDate());
                intent.putExtra("shortDescription", mData.get(position).getShortDescription());
                intent.putExtra("longDescription", mData.get(position).getLongDescription());
                intent.putExtra("doctorGMC", mData.get(position).getDoctor_GMC());
                intent.putExtra("thumbnail", mData.get(position).getThumbnail());
//                start activity
                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_event_ID, tv_date, tv_short_description;
        ImageView prof_thumbnail;

        CardView cardView ;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_event_ID = (TextView) itemView.findViewById(R.id.event_id);
            tv_date = (TextView) itemView.findViewById(R.id.date);
            tv_short_description = (TextView) itemView.findViewById(R.id.shortDescription);
            prof_thumbnail = itemView.findViewById(R.id.prof_thumbnail);
            cardView = itemView.findViewById(R.id.cardview_id);


        }
    }



}
