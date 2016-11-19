package example.developermodel.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import example.developermodel.R;

/**
 * Created by orangeLe on 2016/8/8 0008.
 * 自定义Toolbar
 */
public class CustomToolbar extends Toolbar {

    private LayoutInflater inflater;
    private EditText searchEdit;
    private TextView titleText;

    public CustomToolbar(Context context) {
        this(context,null);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.toolbar_element,null);
        searchEdit = (EditText) findViewById(R.id.search_edit);
        titleText = (TextView) findViewById(R.id.title_text);
    }

    @Override
    public void setTitle(@StringRes int resId) {
        super.setTitle(resId);
    }
}
