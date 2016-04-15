package com.mocircle.cidrawingsample;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mocircle.cidrawing.board.Layer;

import java.util.ArrayList;
import java.util.List;

public class LayerAdapter extends RecyclerView.Adapter<LayerAdapter.ViewHolder> {

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Layer layer);
    }

    private List<Layer> layerList = new ArrayList<>();
    private OnRecyclerViewItemClickListener listener;

    public LayerAdapter() {
    }

    public void setLayers(List<Layer> layerList) {
        this.layerList = layerList;
    }

    public void setOnItemClick(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layer_item_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        vh.nameText = (TextView) v.findViewById(R.id.name_text);
        vh.visibleBox = (CheckBox) v.findViewById(R.id.visible_checkbox);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, (Layer) v.getTag());
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Layer layer = layerList.get(position);
        holder.itemView.setTag(layer);
        holder.nameText.setText(layer.getName());
        holder.visibleBox.setChecked(layer.isVisible());
        holder.visibleBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                layer.setVisible(isChecked);
                notifyDataSetChanged();
            }
        });
        if (layer.isSelected()) {
            holder.itemView.setBackgroundColor(Color.parseColor("#eeeeee"));
        } else {
            holder.itemView.setBackgroundColor(0);
        }
    }

    @Override
    public int getItemCount() {
        return layerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameText;
        public CheckBox visibleBox;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
