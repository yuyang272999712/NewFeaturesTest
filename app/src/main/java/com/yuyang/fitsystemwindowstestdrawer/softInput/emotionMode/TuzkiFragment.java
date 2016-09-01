package com.yuyang.fitsystemwindowstestdrawer.softInput.emotionMode;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.yuyang.fitsystemwindowstestdrawer.R;
import com.yuyang.fitsystemwindowstestdrawer.softInput.emotionMode.utils.EmotionUtils;
import com.yuyang.fitsystemwindowstestdrawer.softInput.emotionMode.utils.GlobalOnItemClickManager;

import java.util.ArrayList;
import java.util.List;

public class TuzkiFragment extends Fragment {

    private Context mContext;
    private ArrayMap<String,Integer> emoji;
    private List<String> emojiNames = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emotion_gird, container, false);
        emoji = EmotionUtils.getEmojiMap(EmotionUtils.EMOTION_TUZKI_TYPE);
        for (String emojiName:emoji.keySet()){
            emojiNames.add(emojiName);
        }
        BaseAdapter adapter = new TuzkiEmojiAdapter(mContext);
        GridView grid = (GridView) view.findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(GlobalOnItemClickManager.getInstance().getOnItemClickListener());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private class TuzkiEmojiAdapter extends BaseAdapter {

        private Context mContext;

        public TuzkiEmojiAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return emojiNames.size();
        }

        @Override
        public String getItem(int position) {
            return emojiNames.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_emotion_gird, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
//            String path = "file:///android_asset/ems/emb" + position + ".gif";
//            Picasso.with(mContext).load(path).into(holder.image);
            holder.image.setImageResource(emoji.get(emojiNames.get(position)));
            return convertView;
        }

        private class ViewHolder {
            public ImageView image;
        }
    }
}
