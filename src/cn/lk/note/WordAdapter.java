package cn.lk.note;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WordAdapter extends BaseAdapter<Word> {

	public WordAdapter(Context context, List<Word> data) {
		super(context, data);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = getLayoutInflater().inflate(R.layout.word_item, null);
			holder.en = (TextView) convertView.findViewById(R.id.tv_word_item_en);
			holder.zh = (TextView) convertView.findViewById(R.id.tv_word_iten_zh);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Word word = getData().get(position);
		
		holder.en.setText(word.getEn());
		holder.zh.setText(word.getZh());
		return convertView;
	}
	class ViewHolder {
		TextView en;
		TextView zh;
	}

}
