package connect.shopping.akshay.tutorials.Comments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import connect.shopping.akshay.tutorials.R;

/**
 * Created by Akshay on 30-03-2017.
 */

public class CommentAdaptor extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;

    private List<Comment> commentList;

    public CommentAdaptor(Activity activity, List<Comment> commentList) {
        this.activity = activity;
        this.commentList = commentList;
    }



    @Override
    public int getCount() {
        if(commentList!=null){
            return commentList.size();
        }else{
            return 0;
        }

    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater == null){
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.comment_list_item,null);

        }

        String time = "";

        TextView comment_user_name = (TextView)convertView.findViewById(R.id.comment_user_name);
        TextView comment_user_comment = (TextView)convertView.findViewById(R.id.comment_user_comment);
        TextView comment_user_time = (TextView)convertView.findViewById(R.id.comment_user_time);

        Comment c =commentList.get(position);
        comment_user_name.setText(c.getUser_name());
        comment_user_comment.setText(c.getComment());
        comment_user_time.setText("");

        return convertView;
    }
}
