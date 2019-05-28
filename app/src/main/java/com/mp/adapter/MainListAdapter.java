package com.mp.adapter;

import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.mp.R;
import com.mp.interfaces.ClickInterface;
import org.json.JSONArray;
import butterknife.BindView;
import butterknife.ButterKnife;
public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ListItemViewHolder> {
    //private Context context;
    private JSONArray modelList;
    private ClickInterface clickInterface;

    public MainListAdapter(JSONArray listModel,ClickInterface clickInterface) {
        //this.context = context;
        this.modelList = listModel;
        this.clickInterface = clickInterface;
    }


    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_list, viewGroup, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder listItemViewHolder, final int i) {
        if (modelList != null && modelList.length() > 0) {
            listItemViewHolder.txtData.setText(modelList.optJSONObject(i).optString("name"));
            listItemViewHolder.txtData2.setText(modelList.optJSONObject(i).optString("tag"));
            if (modelList.optJSONObject(i).optString("color").equalsIgnoreCase("green")) {
                listItemViewHolder.txtData2.setTextColor(Color.GREEN);
            }else  if (modelList.optJSONObject(i).optString("color").equalsIgnoreCase("red")) {
                listItemViewHolder.txtData2.setTextColor(Color.RED);
            }



        }
    }

    @Override
    public int getItemCount() {
        return modelList.length();
    }

    public class ListItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_adapter_text)
        AppCompatTextView txtData;
        @BindView(R.id.txt_adapter_text2)
        AppCompatTextView txtData2;
        @BindView(R.id.layout_row)
        LinearLayout layoutRow;


        public ListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            layoutRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickInterface.setDetails(modelList.optJSONObject(getAdapterPosition()));

                }
            });
        }
    }

    public void setData(JSONArray listData) {
        this.modelList = listData;
        this.notifyDataSetChanged();
    }
}
