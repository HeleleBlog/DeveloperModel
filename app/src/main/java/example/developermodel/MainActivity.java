package example.developermodel;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

import com.lib.utils.app.view.AppUIUtils;

import java.util.ArrayList;
import java.util.List;

import example.developermodel.tab01.TabFragment01;
import example.developermodel.tab02.TabFragment02;
import example.developermodel.tab03.TabFragment03;
import example.developermodel.tab04.TabFragment04;

public class MainActivity extends BaseAppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private List<Fragment> listData = new ArrayList<Fragment>();
    private FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side);
        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //ActionBarDrawerToggle是DrawerLayout.DrawerListener的实现，可以方便的将drawlayout和actionbar结合起来
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };
        actionBarDrawerToggle.syncState();
        drawer.addDrawerListener(actionBarDrawerToggle);

        radioGroup();
        viewPager();

    }

    private void viewPager() {

//        ViewPager 数据初始化三步走：
        viewPager = (ViewPager) findViewById(R.id.view_pager_main);
//        1.初始化ViewPager布局
        listData.add(new TabFragment01());
        listData.add(new TabFragment02());
        listData.add(new TabFragment03());
        listData.add(new TabFragment04());
//        2.构建适配器对象
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), listData);
//        3.给ViewPager设置设配器
        viewPager.setAdapter(fragmentAdapter);
//        ViewPager显示第一个Fragment
        viewPager.setCurrentItem(0);
//        ViwePager页面切换监听
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //            viewpager被选中
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        radioGroup.check(R.id.wx);
                        break;
                    case 1:
                        radioGroup.check(R.id.txl);
                        break;
                    case 2:
                        radioGroup.check(R.id.fx);
                        break;
                    case 3:
                        radioGroup.check(R.id.w);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void radioGroup() {
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_main);
        radioGroup.check(R.id.wx);
//        radioGroup选中改变监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.wx:
//                        setCurrentItem第二个参数控制页面切换动画 true-打开,false-关闭
                        viewPager.setCurrentItem(0, true);
                        break;
                    case R.id.txl:
                        viewPager.setCurrentItem(1, true);
                        break;
                    case R.id.fx:
                        viewPager.setCurrentItem(2, true);
                        break;
                    case R.id.w:
                        viewPager.setCurrentItem(3, true);
                        break;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 加载溢出菜单布局
        getMenuInflater().inflate(R.menu.overflow_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                AppUIUtils.showToast(MainActivity.this, "settings");
                return true;

            case R.id.action_favorite:
                AppUIUtils.showToast(MainActivity.this, "favorite");
                return true;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    super.onBackPressed();
                }
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
