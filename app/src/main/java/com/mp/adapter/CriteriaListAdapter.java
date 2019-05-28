package com.mp.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CriteriaListAdapter extends RecyclerView.Adapter<CriteriaListAdapter.ListItemViewHolder> {
    private JSONArray criteriaArray;

    public CriteriaListAdapter(JSONArray criteriaList) {
        criteriaArray=criteriaList;

    }


    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.criteria_adapter_list, viewGroup, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder listItemViewHolder, final int i) {
        if (criteriaArray != null && criteriaArray.length() > 0) {
            try {

                if (criteriaArray.optJSONObject(i).optString("type").equalsIgnoreCase("plain_text")) {
                    listItemViewHolder.txtData.setText(criteriaArray.optJSONObject(i).optString("text"));

                }else if (criteriaArray.optJSONObject(i).optString("type").equalsIgnoreCase("variable")) {
                    listItemViewHolder.txtData.setText(Html.fromHtml(getText(criteriaArray.optJSONObject(i).optString("text"),criteriaArray.optJSONObject(i).optJSONObject("variable"))));
                }

                if ((i==0 && criteriaArray.length()>1) || (i!=0 && i!=criteriaArray.length()-1)) {
                    listItemViewHolder.txtAnd.setVisibility(View.VISIBLE);
                }else {
                    listItemViewHolder.txtAnd.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public int getItemCount() {
        return criteriaArray.length();
    }

    public class ListItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_adapter)
        AppCompatTextView txtData;
        @BindView(R.id.txt_and)
        AppCompatTextView txtAnd;


        public ListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public void setData(JSONArray listData) {
        this.criteriaArray = listData;
        this.notifyDataSetChanged();
    }

    public String getText(String text, JSONObject jsonObject)
    {
        String matchWord="$";
        String output=text;
        if (text.contains(matchWord)) {
            String strArray[] = text.split(" ");
            JSONObject variable=new JSONObject();
            if (strArray.length > 0) {
                output=text.replaceAll("\\$","");
                for (String txt : strArray) {
                    if (txt.contains("$")) {
                        variable=jsonObject.optJSONObject(txt);
                        txt=txt.replaceAll("\\$","");
                        if (variable.optString("type").equalsIgnoreCase("indicator")){
                            output= output.replaceAll(txt,"<font color='#3eb0f7'>("+variable.optString("default_value")+")</font>");
                        }else if (variable.optString("type").equalsIgnoreCase("value")){
                            output= output.replaceAll(" "+txt," <font color='#3eb0f7'>("+variable.optJSONArray("values").opt(0)+") </font>");
                        }
                    }
                }
            }
        }
        return output;
    }
}
