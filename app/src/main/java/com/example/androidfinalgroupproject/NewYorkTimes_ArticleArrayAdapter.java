package com.example.androidfinalgroupproject;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewYorkTimes_ArticleArrayAdapter extends ArrayAdapter<NewYorkTimes_Article> {

    public NewYorkTimes_ArticleArrayAdapter(Context context, List<NewYorkTimes_Article> articles) {
        super (context, android.R.layout.simple_list_item_1, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        //get the data item for the position
        NewYorkTimes_Article article = this.getItem(position);

        //check if existing view is being recycled
        //not recycling; inflate layout
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.newyorktimes_item_article_result, parent, false);
        }
        //find image view
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivImage);
        //clear out recycled convertView from last time
        imageView.setImageResource(0);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(article.getHeadline());

        //populate thumbnail
        //remote download image

        String thumbnail = article.getThumbnail();
        if (!TextUtils.isEmpty(thumbnail)) {
            Picasso.with(getContext()).load(thumbnail).into(imageView);
        }

        return convertView;

    }
}
