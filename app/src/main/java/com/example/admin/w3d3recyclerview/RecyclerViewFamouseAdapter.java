package com.example.admin.w3d3recyclerview;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Luis Aguirre on 9/6/2017.
 */

public class RecyclerViewFamouseAdapter extends RecyclerView.Adapter<RecyclerViewFamouseAdapter.ViewHolder> {


    List<Famous> famouses;

    public RecyclerViewFamouseAdapter(List<Famous> famouses) {
    this.famouses = famouses;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_view,parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Famous famous = famouses.get(position);
        holder.textViewName.setText(famous.getName());
        holder.textViewDescription.setText(famous.getDescription());
        holder.imageViewFamous.setImageResource(famous.getImage_id());

    }

    @Override
    public int getItemCount() {
        return famouses.size();
    }

    public void remove(int position) {
        famouses.remove(position);
        notifyItemRemoved(position);
        Log.d("TAG", "remove: " + famouses.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewFamous;
        TextView textViewName;
        TextView textViewDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            imageViewFamous = (ImageView) itemView.findViewById(R.id.image_view_famous_image_id);
            textViewName = (TextView) itemView.findViewById(R.id.text_view_famous_name_id);
            textViewDescription = (TextView) itemView.findViewById(R.id.text_view_famous_description_id);

        }
    }
}
