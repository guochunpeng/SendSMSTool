package com.a58program.sendsmstool.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.a58program.sendsmstool.R;
import com.a58program.sendsmstool.adapter.ContactsAdapter;
import com.a58program.sendsmstool.base.BaseActivity;
import com.a58program.sendsmstool.model.ContactsBean;
import com.a58program.sendsmstool.utils.ToastUtil;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import static com.a58program.sendsmstool.activity.MainActivity.SELECT_CONTACTS_OK;

public class SelectContactsActivity extends BaseActivity implements View.OnClickListener {
    private final String URL = "";
    private ListView lv_contacts;
    private Button bt_select_file,bt_confirm;
    private String path="/storage/emulated/0/tencent/MicroMsg/Download/6.27gcp.xls";

    private ContactsAdapter adapter;
    private List<ContactsBean> contactsBeans=new ArrayList<ContactsBean>();
    private CheckBox cb_all;

    private EditText et_start,et_end;
    private Button bt_select;
    private TextView tv_total_length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new ContactsAdapter(contactsBeans,mContext);
        lv_contacts.setAdapter(adapter);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_select_contacts;
    }

    @Override
    protected void setListensers() {
        bt_select_file.setOnClickListener(this);
        bt_confirm.setOnClickListener(this);
        cb_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // 全选——全不选
                if (b) {
                    adapter.initCheck(true);
                    // 通知刷新适配器
                    adapter.notifyDataSetChanged();
                } else  {
                    adapter.initCheck(false);
                    // 通知刷新适配器
                    adapter.notifyDataSetChanged();
                }
            }
        });

        bt_select.setOnClickListener(this);
    }

    @Override
    protected void findViews() {
        bt_select_file = (Button) findViewById(R.id.bt_select_file);
        lv_contacts = (ListView) findViewById(R.id.lv_contacts);
        bt_confirm=(Button)findViewById(R.id.bt_confirm);
        cb_all=(CheckBox)findViewById(R.id.cb_all);
        et_start=(EditText)findViewById(R.id.et_start);
        et_end=(EditText)findViewById(R.id.et_end);
        bt_select=(Button)findViewById(R.id.bt_select);
        tv_total_length=(TextView)findViewById(R.id.tv_total_length);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_select_file:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/xls");//无类型限制
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
                break;
            case R.id.bt_confirm:
                Map<Integer, Boolean> isCheck = adapter.getMap();
                ArrayList<ContactsBean> selectBeans=new ArrayList<ContactsBean>();
                for(int i=0;i<isCheck.size();i++){
                    if(isCheck.get(i)){
                     selectBeans.add(contactsBeans.get(i));
                    }
                }
                if(null==selectBeans){
                    ToastUtil.showToast(mContext,"请勾选联系人");
                    return;
                }

                if(0==selectBeans.size()){
                    ToastUtil.showToast(mContext,"请勾选联系人");
                    return;
                }

                Intent intent1=new Intent();
                intent1.putExtra("selectData", selectBeans);
                setResult(SELECT_CONTACTS_OK,intent1);
                backActivity();
                break;
            case R.id.bt_select:
                String start=et_start.getText().toString().trim();
                String end=et_end.getText().toString().trim();
                if(null==start||null==end||"".equals(start)||"".equals(end)){
                    ToastUtil.showToast(mContext,"请填写完整");
                    return;
                }

                try{
                    int startIndex=Integer.valueOf(start);
                    int endIndex=Integer.valueOf(end);
                    if(endIndex<=startIndex||endIndex>=contactsBeans.size()){
                        ToastUtil.showToast(mContext,"填写范围不合法");
                        return;
                    }

                    adapter.selectRange(startIndex,endIndex);
                    adapter.notifyDataSetChanged();
                }catch (Exception e){

                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int arg0, int resultCode, Intent data) {
        super.onActivityResult(arg0, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                path = uri.getPath();
                return;
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                path = getPath(this, uri);
            } else {//4.4以下下系统调用方法
                path = getRealPathFromURI(uri);
            }

            if(!path.endsWith("xls")) {
                ToastUtil.showToast(mContext,"仅支持xls文件类型");
               return;
            }
            new ReadAsync().execute(path);
        }
    }

    public class ReadAsync extends AsyncTask<String,Integer,Integer>{

        @Override
        protected Integer doInBackground(String... strings) {
            readExcel(strings[0]);
            return null;
        }
    }

    public void readExcel(String path) {
        try {
            /**
             * 后续考虑问题,比如Excel里面的图片以及其他数据类型的读取
             **/
            InputStream is = new FileInputStream(path);
            //Workbook book = Workbook.getWorkbook(new File("mnt/sdcard/test.xls"));
            Workbook book = Workbook.getWorkbook(is);

            int num = book.getNumberOfSheets();
            // 获得第一个工作表对象
            Sheet sheet = book.getSheet(0);
            int Rows = sheet.getRows();
            int Cols = sheet.getColumns();

            int nameIndex=-1;
            int mobileIndex=-1;
            for(int i=0;i<Cols;i++){
                String content=sheet.getCell(i, 0).getContents();
                if("name".equals(content)){
                    nameIndex=i;
                    continue;
                }

                if("mobile".equals(content)){
                    mobileIndex=i;
                    continue;
                }
            }

            if(-1==nameIndex||-1==mobileIndex){
                ToastUtil.showToast(mContext,"表格首行请注明'name'和'mobile'标示");
                return;
            }


            for (int i = 1; i < Rows; ++i) {
                Cell nameCell = sheet.getCell(nameIndex, i);
                Cell mobileCell = sheet.getCell(mobileIndex, i);
                    if(null==nameCell||null==mobileCell){
                        continue;
                    }
                    String name=nameCell.getContents();
                    String mobile=mobileCell.getContents();
                    if(null==name||null==mobile||"".equals(mobile)){
                        continue;
                    }

                    ContactsBean contactsBean=new ContactsBean();
                    contactsBean.setName(name);
                    contactsBean.setMobile(mobile);
                    contactsBeans.add(contactsBean);
            }
            book.close();
            updateData();

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    private void updateData(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_total_length.setText(contactsBeans.size()+"");
                adapter.notifyDataSetChanged();
            }
        });
    }


    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            ;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }


    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
