package com.lib.utils.app.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by orangeLe on 2016/8/5 0005.
 * AppUI 的工具类
 */
public class AppUIUtils {

    public static Toast toast;
    public static ProgressDialog progressDialog;

    /**
     * Activity派生类的去标题方法
     */
    public static void setWindow(Context context) {
        ((Activity)context).requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * AppCompatActivity及其派生类去标题的方法
     */
    public static void setSupportWindow(AppCompatActivity activity) {
        activity.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 使用场景：弹出一段信息，告诉用户某某事情已经发生了，用户不需要对这个事情做出响应的时候，使用Toast
     * 描述：弹出一个Toast，当短时间内弹出多个Tost时，只会更改Toast的内容，而不会重复的生成Toast对象。
     * @param content
     */
    public static void showToast(Context context, String content){
        if(toast == null){
            toast = Toast.makeText(context,content,Toast.LENGTH_SHORT);
        }else{
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * 用途：当提示信息是至关重要的，并且必须要由用户做出决定才能继续的时候，使用Dialog。
     * 阻止用户正在进行的操作，必须停下来对Dialog进行处理
     * android.support.v7.app.AlertDialog 可以让对话框在所有的系统版本中都保持一致的风格
     *
     * @param context    上下文环境
     * @param cancelFlag 对话框是否可以取消,false 表示按下back键不可以退出
     * @param titleStr   对话框的标题
     * @param msgContent 消息的内容
     */
    public static void showDialog(Context context, boolean cancelFlag, String titleStr, String msgContent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(cancelFlag);
        builder.setTitle(titleStr);
        builder.setMessage(msgContent);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface d,
                                int which) {

//                广播接收器里启动活动：
//                在广播接收器里启动活动一定要给Intent 加入 FLAG_ACTIVITY_NEW_TASK 这个标志
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface d,
                                        int which) {
                    }
                });
//        广播接收器里弹出对话框：
//        需要设置AlertDialog的类型(设置为系统级别)，保证在广播接收器中可以正常弹出
//        AlertDialog dialog = builder.create();
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//        系统级别的对话框需要声明 android.permission.SYSTEM_ALERT_WINDOW 权限

        builder.show();
    }

    /**
     * Snackbar
     *      应用场景：Toast/Dialog 两者之外的任何其他场景，可以选择Snackbar
     *      优点：1.snackbar可以响应点击事件 2.同一时间有且只有一个snackbar在显示。
     *      注意：Snackbar 中不能包含图标，操作只能以文本的形式
     * @param view
     */
    public static void showSnackbar(View view, String content){
        Snackbar.make(view, content,
                Snackbar.LENGTH_LONG)
                .setAction("Undo",
                        new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                            }
                        })
                .show();
    }

    /**
     * 显示进度对话框
     */
    public void showProgressDialog(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 跳转到图片裁剪
     *
     * @param uri
     */
    public static void goZoomImage(Fragment activity, Uri uri, int width,
                                   int height) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            // intent.putExtra("crop", "false"); //
            // 设置了参数，就会调用裁剪，如果不设置，就会跳过裁剪的过程。
            // 设置裁剪图片的额宽高
            intent.putExtra("outputX", width);
            intent.putExtra("outputY", height);
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("return-data", true);
            activity.startActivityForResult(intent, 2000);
        } catch (Exception e) {
            Toast.makeText(activity.getActivity(), "请先安装图库", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 跳转到图库
     */
    public static void goImageChoice(Fragment activity) {
        try {
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*"); // 设置文件类型
            activity.startActivityForResult(intent, 2);// 转到图片
        } catch (Exception e) {
            Toast.makeText(activity.getActivity(), "请先安装图库", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    /**
     * 获取照相机uri
     *
     * @return
     */
    private static File getUri() {
        File dcimFile = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (!dcimFile.exists()) {
            return null;
        }
        File file = new File(dcimFile.getAbsolutePath() + "/dcim"
                + System.currentTimeMillis() + ".jpg");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 跳转到照相机
     */
    public static Uri goCamera(Fragment activity) {
        Intent captrueIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        File picFile = getUri();
        if (picFile == null) {
            return null;
        }
        captrueIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picFile));
        // captrueIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
        // captrueIntent.putExtra(MediaStore.ACTION_IMAGE_CAPTURE,
        // Uri.fromFile(picFile));
        activity.startActivityForResult(captrueIntent, 1);
        return Uri.fromFile(picFile);
    }

    public static int getRelativeTop(View myView) {
        // if (myView.getParent() == myView.getRootView())
        if (myView.getId() == android.R.id.content)
            return myView.getTop();
        else
            return myView.getTop() + getRelativeTop((View) myView.getParent());
    }

    public static int getRelativeLeft(View myView) {
        // if (myView.getParent() == myView.getRootView())
        if (myView.getId() == android.R.id.content)
            return myView.getLeft();
        else
            return myView.getLeft()
                    + getRelativeLeft((View) myView.getParent());
    }

    /*
     * 适配4.4的选择图片
     */
    @SuppressLint("NewApi")
    public static String getPath(final Fragment context, final Uri uri) {

        final boolean isKitKat = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat
                && DocumentsContract.isDocumentUri(context.getActivity(), uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context.getActivity(), contentUri, null,
                        null);
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

                return getDataColumn(context.getActivity(), contentUri,
                        selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context.getActivity(), uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        android.database.Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
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
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }
}
