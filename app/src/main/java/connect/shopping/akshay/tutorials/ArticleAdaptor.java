package connect.shopping.akshay.tutorials;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Akshay on 02-04-2017.
 */

public class ArticleAdaptor extends RecyclerView.Adapter<ArticleAdaptor.MyViewHolder> {
    private List<Articles> articlesList;

    public ArticleAdaptor(List<Articles> articlesList) {
        this.articlesList = articlesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Articles article = articlesList.get(position);
        holder.article_name.setText(article.getName().toString());
        holder.article_text.setText(article.getText().toString());
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView article_name,article_text;

        public MyViewHolder(View itemView) {
            super(itemView);
            article_name = (TextView)itemView.findViewById(R.id.article_name);
            article_text = (TextView)itemView.findViewById(R.id.article_text);
        }
    }



}
