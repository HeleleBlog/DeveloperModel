package example.developermodel.tab01;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.model.Post;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import example.developermodel.R;

/**
 * Created by Administrator on 2016/8/4 0004.
 * 注意：Fragment 是support包下的
 */
public class TabFragment01 extends Fragment {

    private String[] IMG_URL_LIST = {
            "http://ac-QYgvX1CC.clouddn.com/36f0523ee1888a57.jpg",
            "http://ac-QYgvX1CC.clouddn.com/07915a0154ac4a64.jpg",
            "http://ac-QYgvX1CC.clouddn.com/9ec4bc44bfaf07ed.jpg",
            "http://ac-QYgvX1CC.clouddn.com/fa85037f97e8191f.jpg",
            "http://ac-QYgvX1CC.clouddn.com/de13315600ba1cff.jpg",
            "http://ac-QYgvX1CC.clouddn.com/15c5c50e941ba6b0.jpg",
            "http://ac-QYgvX1CC.clouddn.com/10762c593798466a.jpg",
            "http://ac-QYgvX1CC.clouddn.com/eaf1c9d55c5f9afd.jpg",
            "http://ac-QYgvX1CC.clouddn.com/ad99de83e1e3f7d4.jpg",
            "http://ac-QYgvX1CC.clouddn.com/233a5f70512befcc.jpg",
    };
    private ListView listView;
    private List<Post> listData;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        listView = (ListView) view.findViewById(R.id.listview_chat);
        initData();
        // addHeaderView一定要在setAdapter前调用
        listView.addHeaderView(inflater.inflate(R.layout.listview_chat_head,null));
        listView.addHeaderView(inflater.inflate(R.layout.listview_chat_head,null));

        listView.setAdapter(new ListItemAdapter(getContext(),listData));

        //第二个参数控制在滑动过程中暂停加载图片，第三个参数控制猛的滑动界面的时候图片是否加载
        PauseOnScrollListener listener = new PauseOnScrollListener(ImageLoader.getInstance(), false, false);
        listView.setOnScrollListener(listener);
        return view;
    }

    private void initData() {
        listData = new ArrayList<Post>();
        for (int i = 0; i < 18; i++) {
            Post post = new Post();
            post.setHeadImg(IMG_URL_LIST[i % 9]);
            post.setName("张三！");
            post.setContentStr("今天天气真好！");
            List<String> imgUrlList = new ArrayList<>();
            imgUrlList.addAll(Arrays.asList(IMG_URL_LIST).subList(0, i % 9 + 1));
            post.setContentImgUrl(imgUrlList);
            listData.add(post);
        }
    }
}
