package com.android4dev.navigationview.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android4dev.navigationview.R;
import com.android4dev.navigationview.utility.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Charlie on 15/06/2016.
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ItemHolder>{

    private List<Result> results;
    private int rowLayout;
    private Context context;


    public ResultAdapter(Context context, List<Result> results, int rowLayout){

        this.results = results;
        this.context = context;
        this.rowLayout = rowLayout;

        notifyDataSetChanged();
    }


    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ItemHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        final Result result = results.get(position);
        holder.name.setText(result.getTrackName());
        holder.description.setText(result.getArtistName());
        Picasso.with(context)
                .load(result.getArtworkUrl100())
                .into( holder.Image);
        holder.setClickListener(new ItemClickListener() {

            @Override
            public void onClick(View view, int position) {
                Log.i("Clicked",""+position);

            }
        });
    }


    @Override
    public int getItemCount() {
        return results ==null ? 0:results.size();
    }


    public class ItemHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnLongClickListener{

            //@BindView(R.id.titleText)
            TextView name;
            TextView description;
            ImageView Image;
            private ItemClickListener clickListener;

            public void setClickListener(ItemClickListener itemClickListener){
                this.clickListener = itemClickListener;
            }


            public ItemHolder(View row) {
                super(row);
                Image = (ImageView)row.findViewById(R.id.Image1);
                name = (TextView)row.findViewById(R.id.titleText);
                description = (TextView)row.findViewById(R.id.descText);
              //  ButterKnife.bind(this, row);
                row.setTag(row);
                row.setOnClickListener(this);
                row.setOnLongClickListener(this);
            }

            @Override
            public void onClick(View v) {
                clickListener.onClick(v, getPosition());
            }

            @Override
            public boolean onLongClick(View v) {
                clickListener.onClick(v, getPosition());
                return false;
            }

        }
}
