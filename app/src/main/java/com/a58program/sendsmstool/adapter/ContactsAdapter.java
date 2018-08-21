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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactsAdapter extends BaseAdapter {
    private List<ContactsBean> datas;
    private Context context;
    private LayoutInflater inflater;
    private Map<Integer,Boolean> isCheck=new HashMap<Integer, Boolean>();
    public ContactsAdapter(List<ContactsBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
        this.inflater=LayoutInflater.from(context);
        initCheck(false);
    }

    public void initCheck(boolean checked){
        for (int i = 0; i < datas.size(); i++) {
            // 设置默认的显示
            isCheck.put(i, checked);
        }
    }

    public void selectRange(int start ,int end){
        for(int i=start;i<end;i++){
            isCheck.put(i,true);
        }
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
        viewHolder.cb_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCheck.put(i,b);
            }
        });

        // 设置状态
        if (null==isCheck.get(i)) {
            isCheck.put(i, false);
        }

        viewHolder.cb_select.setChecked(isCheck.get(i));
        return view;
    }

    public class ViewHolder{
        private TextView tv_name;
        private TextView tv_mobile;
        private CheckBox cb_select;
    }

    // 全选button获取状态
    public Map<Integer, Boolean> getMap() {
        // 返回状态
        return isCheck;
    }
    // 加入数据
    public void addData(ContactsBean bean) {
        // 下标 数据
        datas.add(0, bean);
    }
    // 删除一个数据
    public void removeData(int position) {
        datas.remove(position);
    }
}
