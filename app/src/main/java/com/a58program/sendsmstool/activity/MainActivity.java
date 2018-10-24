package com.a58program.sendsmstool.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.a58program.sendsmstool.R;
import com.a58program.sendsmstool.adapter.ContactsSelectedAdapter;
import com.a58program.sendsmstool.base.BaseActivity;
import com.a58program.sendsmstool.model.ContactsBean;
import com.a58program.sendsmstool.utils.ToastUtil;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ListView lv_contacts;
    private EditText et_content;
    private Button bt_send,bt_select_contacts;
    private final int SELECT_CONTACTS_REQUEST=0x101;
    public static final int SELECT_CONTACTS_OK=0x102;
    public ContactsSelectedAdapter adapter;
    private ArrayList<ContactsBean> contactsBeans=new ArrayList<ContactsBean>();
    private String[] PERMISSIONS={Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        et_content.setText("恭喜您成为今借到平台优选客户，专享最高5000额度，需要借款请回复短信或添加薇信17600195364");
        et_content.setText("小额借，额度1000～5000元，审核催收不拨打联系人，芝麻分600以上五分钟下款，需要借款请联系薇信：17600195364 渠道码：");
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void setListensers() {
        bt_send.setOnClickListener(this);
        bt_select_contacts.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        lv_contacts=(ListView)findViewById(R.id.lv_contacts);
        et_content=(EditText)findViewById(R.id.et_content);
        bt_send=(Button)findViewById(R.id.bt_send);
        bt_select_contacts=(Button)findViewById(R.id.bt_select_contacts);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.bt_send:
                String content=et_content.getText().toString().trim();
                if("".equals(content)){
                    ToastUtil.showToast(mContext,"短信内容不能为空");
                    return;
                }
                sendMessage3(content);
                break;
            case R.id.bt_select_contacts:
                Intent intent=new Intent(mContext,SelectContactsActivity.class);
                startActivityForResult(intent,SELECT_CONTACTS_REQUEST);
                break;
        }
    }


    /**
     * 发送短信(掉起发短信页面)
     *
     * @param tel     电话号码
     * @param content 短息内容
     */
    private void sendMessage3( String content) {
        if(null==contactsBeans){
            ToastUtil.showToast(mContext,"未选择联系人");
            return;
        }
        if(0==contactsBeans.size()){
            ToastUtil.showToast(mContext,"未选择联系人");
            return;
        }
        StringBuffer tels=new StringBuffer("smsto:");
        for(int i=0;i<contactsBeans.size();i++){
            String tel=contactsBeans.get(i).getMobile();
            if (PhoneNumberUtils.isGlobalPhoneNumber(tel)) {
                tels.append(tel);
                if(i<contactsBeans.size()-1){
                    tels.append(";");
                }
            }
        }

        if("smsto:".equals(tels.toString())){
            ToastUtil.showToast(mContext,"无合法联系人");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(tels.toString()));
        intent.putExtra("sms_body", content);
        startActivity(intent);

    }


    Runnable runnable=new Runnable() {
        @Override
        public void run() {

        }
    };



        @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        switch (arg0){
            case SELECT_CONTACTS_REQUEST:
                if(SELECT_CONTACTS_OK==arg1){
                    contactsBeans=(ArrayList<ContactsBean>)arg2.getSerializableExtra("selectData");
                    if(null!=contactsBeans) {
                        adapter=new ContactsSelectedAdapter(contactsBeans,mContext);
                        lv_contacts.setAdapter(adapter);
                    }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionsUtil.TipInfo tip = new PermissionsUtil.TipInfo("注意:", "请允许权限：获取手机信息,发送短信", null, "打开权限");
        PermissionsUtil.requestPermission(this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permissions) {

            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {

            }
        }, PERMISSIONS, true, tip);
    }
}
