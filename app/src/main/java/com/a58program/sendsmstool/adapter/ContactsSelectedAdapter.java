package com.a58program.sendsmstool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.a58program.sendsmstool.R;
import com.a58program.sendsmstool.model.ContactsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactsSelectedAdapter extends BaseAdapter {
    private ArrayList<ContactsBean> datas;
    private Context context;
    private LayoutInflater inflater;
    public ContactsSelectedAdapter(ArrayList<ContactsBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(null==view){
            viewHolder=new ViewHolder();
            view=inflater.inflate(R.layout.item_contacts,null);
            viewHolder.tv_name=(TextView)view.findViewById(R.id.tv_name);
            viewHolder.tv_mobile=(TextView)view.findViewById(R.id.tv_mobile);
            viewHolder.cb_select=(CheckBox)view.findViewById(R.id.cb_select);
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)view.getTag();
        }
        ContactsBean data=datas.get(i);
        viewHolder.tv_name.setText(data.getName());
        viewHolder.tv_mobile.setText(data.getMobile());
        viewHolder.cb_select.setVisibility(View.GONE);
        return view;
    }

    public class ViewHolder{
        private TextView tv_name;
        private TextView tv_mobile;
        private CheckBox cb_select;
    }
}
