package example.developermodel;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * FragmentPagerAdapter派生自PagerAdapter，它是用来呈现Fragment页面的，
 * 这些Fragment页面会一直保存在fragment manager中，以便用户可以随时取用
 *
 * 这个适配器最好用于有限个静态fragment页面的管理。
 * 尽管不可见的视图有时会被销毁，但用户所有访问过的fragment都会被保存在内存中。
 * 因此fragment实例会保存大量的各种状态，这就造成了很大的内存开销。
 * 所以如果要处理大量的页面切换，建议使用FragmentStatePagerAdapter.
 *
 * 每一个使用FragmentPagerAdapter的ViewPager都要有一个有效的ID集合，有效ID的集合就是Fragment的集合
 *
 * 对于FragmentPagerAdapter的派生类，只需要重写getItem(int)和getCount()就可以了。
 */
public class FragmentAdapter extends FragmentPagerAdapter {


    private final List<Fragment> fragments;

    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
