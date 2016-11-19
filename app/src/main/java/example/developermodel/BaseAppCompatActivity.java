package example.developermodel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BaseAppCompatActivity extends AppCompatActivity {

    private static final String TAG = BaseAppCompatActivity.class.getSimpleName();
    /**
     * 子Activity中需要使用this的地方直接使用mContext成员变量
     */
    protected Activity mContext;
    private TextView mToolbarTitle;
    private TextView mToolbarSubTitle;
    private Toolbar mToolbar;
    /**
     * 进度对话框
     */
    protected ProgressDialog progressDialog;
    private static Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        // 设置App只能竖屏显示
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //向活动管理器中添加Activity
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //继续、恢复
    @Override
    protected void onResume() {
        super.onResume();
    }

    //暂停
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 从活动管理器中移除Activity
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * Toast 弹出一段信息，告诉用户某某事情已经发生了，过一段时间后就会自动消息
     * 避免了重复生成多个Toast
     * @param content
     */
    protected void showToast(Context context, String content){
        if(toast == null){
            toast = Toast.makeText(context,content,Toast.LENGTH_SHORT);
        }else{
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * Snackbar
     *      应用场景：Toast/Dialog 两者之外的任何其他场景，可以选择Snackbar
     *      优点：1.snackbar可以响应点击事件 2.同一时间有且只有一个snackbar在显示。
     *      注意：Snackbar 中不能包含图标，操作只能以文本的形式
     * @param view
     * @param content 内容
     */
    protected void showSnackbar(View view, String content){
        Snackbar.make(view, content,
                Snackbar.LENGTH_LONG)
                .setAction("Undo",
                        new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                // TODO 点击Undo的监听事件

                            }
                        })
                .show();
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
