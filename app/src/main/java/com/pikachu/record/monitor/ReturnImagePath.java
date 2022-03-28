package com.pikachu.record.monitor;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.pikachu.record.R;
import com.pikachu.record.tool.ToolFile;
import com.pikachu.record.tool.ToolOther;

/**
 有图片选择的activity 继承 这个AccountActivity

 */

public abstract class ReturnImagePath extends AppCompatActivity {

    private final static int RC_CHOOSE_PHOTO=2;
    abstract public void setImagetPath(String path);

    private static void apply(Activity activity) {
        Intent  intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intentToPickPic, RC_CHOOSE_PHOTO);
    }



    //权限申请结果回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_CHOOSE_PHOTO) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                apply(this);
            } else {
                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }

    }



    //选择图片  点击跳转地
    public static void toPhoto(Activity activity) {




        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //未授权，申请授权(从相册选择图片需要读取存储卡的权限)
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, RC_CHOOSE_PHOTO);
        } else {
            apply(activity);
        }

    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_CHOOSE_PHOTO && data != null) {

            Uri uri = data.getData();
            String filePath = ToolFile.getFilePathByUri(this, uri);
            setImagetPath(filePath);
        } else {
            setImagetPath(null);               
        }


    }






}
