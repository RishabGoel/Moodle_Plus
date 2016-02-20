package com.example.rishab.moodle;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;

/**
 * Created by Rishab on 19-02-2016.
 */
public class CustomCommentListAdapter extends BaseAdapter{
    private Activity activity;
    private String[] comments;
    private String[] user;
    public CustomCommentListAdapter(Activity act, String[] comments,String[] user_id){
        super();
        this.comments=comments;
        activity=act;
//        user=user_id;
//        this.data_desc=data_desc;
    }
    public int getCount() {
        // TODO Auto-generated method stub
        return comments.length;
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.comment_list,null,true);
        TextView title=(TextView) row.findViewById(R.id.title);
//        TextView desc=(TextView) row.findViewById(R.id.Description);
        title.setText(comments[position]);
//        desc.setText(data_desc[position]);
        return row;
    }
}
