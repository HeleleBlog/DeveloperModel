package example.developermodel.tab01;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import example.developermodel.BaseAppCompatActivity;
import example.developermodel.R;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by orangeLe on 2016/8/18 0018.
 * 图片查看器
 */
public class ViewPagerActivity extends BaseAppCompatActivity {
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    private ViewPager viewPager;
    private int pagePosition;
    private List<String> imgUrls;
    private TextView indicatorTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        pagePosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        imgUrls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);

        viewPager = (ViewPager) findViewById(R.id.view_pager_hacky);
        viewPager.setAdapter(mAdapter);

        indicatorTV = (TextView) findViewById(R.id.indicator_tv);
        indicatorTV.setText(getString(R.string.viewpager_indicator, 1, viewPager.getAdapter().getCount()));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String indicator = getString(R.string.viewpager_indicator, position + 1, viewPager.getAdapter().getCount());
                indicatorTV.setText(indicator);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(pagePosition);
    }

    private PagerAdapter mAdapter = new PagerAdapter() {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView view = new PhotoView(container.getContext());
            Picasso.with(ViewPagerActivity.this).load(imgUrls.get(position))
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(view);
            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return imgUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    };

    /**
     * 打开图片查看器
     * @param context
     * @param index 选中的ngl的子项index
     * @param list ngl url的集合
     */
    public static void actionStart(Context context, int index, ArrayList<String> list) {
        Intent intent = new Intent(context, ViewPagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
        intent.putExtra(EXTRA_IMAGE_URLS, list);
        intent.putExtra(EXTRA_IMAGE_INDEX, index);
        context.startActivity(intent);
    }
}
