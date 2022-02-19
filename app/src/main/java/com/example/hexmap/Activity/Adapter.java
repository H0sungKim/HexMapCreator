package com.example.hexmap.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hexmap.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> {

    private ArrayList<Data> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.setText(listData.get(position));
        holder.setOnItemClickListener(listData.get(position).getOnItemClickListener());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    void addItem(Data data) {
        listData.add(data);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private TextView textView2;
        private ImageButton mapcodeBtn;
        private ImageButton openmapBtn;
        private OnItemClickListener mOnItemClickListener;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.recycler_textView);
            textView2 = itemView.findViewById(R.id.recycler_textView2);
            mapcodeBtn = itemView.findViewById(R.id.recycler_button);
            mapcodeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick1();
                }
            });
            openmapBtn = itemView.findViewById(R.id.openmapbtn);
            openmapBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick2();
                }
            });
        }

        void setText(Data data) {
            textView1.setText(data.getTitle());
            textView2.setText(data.getSubTitle());
        }

        void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }
    }
}

interface OnItemClickListener {
    void onClick1();
    void onClick2();
}
