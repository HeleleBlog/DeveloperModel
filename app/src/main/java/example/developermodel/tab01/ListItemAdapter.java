package example.developermodel.tab01;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lib.utils.open_source_lib.ninegridimageview.NineGridImageView;
import com.lib.utils.open_source_lib.ninegridimageview.NineGridImageViewAdapter;
import com.model.Post;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

import example.developermodel.R;

/**
 * Created by orangeLe on 2016/8/17 0017.
 */
public class ListItemAdapter extends BaseAdapter {
    private final Context context;
    private final List<Post> listData;
    private final LayoutInflater layoutInflater;

    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.ic_launcher)
            .showImageForEmptyUri(R.mipmap.ic_launcher)
            .showImageOnFail(R.mipmap.ic_launcher)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.ARGB_4444)//ARGB 每个像素占4位
            .displayer(new RoundedBitmapDisplayer(100))
            .build();

    public ListItemAdapter(Context context, List<Post> listData) {
        this.context = context;
        this.listData = listData;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int i) {
        return listData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Post post = listData.get(i);
        View v;
        ViewHolder vh;
        if(view==null){
            v = layoutInflater.inflate(R.layout.list_item_chat,null);
            vh = new ViewHolder();
            vh.headImg = (ImageView) v.findViewById(R.id.head_img_chat_item);
            vh.name = (TextView) v.findViewById(R.id.name_chat_item);
            vh.content = (TextView) v.findViewById(R.id.content_chat_item);
            vh.ngl = (NineGridImageView) v.findViewById(R.id.ngl_chat_item);
            v.setTag(vh);
        }else{
            v = view;
            vh = (ViewHolder) view.getTag();
        }
        ImageLoader.getInstance().displayImage(post.getHeadImg(),vh.headImg,options);
        vh.name.setText(post.getName());
        vh.content.setText(post.getContentStr());

        vh.ngl.setImagesData(post.getContentImgUrl());
        if (post.getContentImgUrl() == null || post.getContentImgUrl().size() == 0) { // 没有图片资源就隐藏GridView
            vh.ngl.setVisibility(View.GONE);
        } else {
            vh.ngl.setAdapter(mAdapter);
        }
        return v;
    }

    private NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>(){

        @Override
        protected void onDisplayImage(Context context, ImageView imageView, String s) {
            //显示Image
            ImageLoader.getInstance().displayImage(s,imageView);
        }

        @Override
        protected void onItemImageClick(Context context, int index, List<String> list) {
            //ngl 子项的点击事件,打开图片查看器
            ViewPagerActivity.actionStart(context,index, (ArrayList<String>) list);
        }
    };

    private class ViewHolder{
        private ImageView headImg;
        private TextView name;
        private TextView content;
        private NineGridImageView ngl;
    }
}
