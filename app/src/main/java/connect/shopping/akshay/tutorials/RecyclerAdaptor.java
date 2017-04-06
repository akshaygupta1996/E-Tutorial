package connect.shopping.akshay.tutorials;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import connect.shopping.akshay.tutorials.Comments.CommentsActivity;

/**
 * Created by Akshay on 26-03-2017.
 */

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.VedioInfoHolder> {


    private ArrayList<VedioClass> vedios = new ArrayList<VedioClass>();;
//    private static final String url_fetch_vedios = "https://akki7272.000webhostapp.com/tutorials/getAllVedios.php";
    Context ctx;
    String KEY  = "AIzaSyCGiHF9NDV2kuR1IagQTPoXIt3Z112IT1Y";
    private int language_type = 0;
    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    private SharedPreferences prefs;
    private int user_id;
    private static final String url_user_like = "https://akki7272.000webhostapp.com/tutorials/userLike.php";
    private int like_response = 0;

    public RecyclerAdaptor(Context context, final int language_type, ArrayList<VedioClass> vedios){

        this.ctx = context;
        this.language_type = language_type;
        this.vedios = vedios;

        Log.d("VEDIOS",vedios.size()+"");
        Log.d("ADAPTOR","RecyclerAdaptor Constructor Called"+language_type);
    }
    @Override
    public RecyclerAdaptor.VedioInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item,parent,false);
        requestQueue = Volley.newRequestQueue(ctx);
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        user_id =prefs.getInt("user_id",0);

        return new VedioInfoHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final RecyclerAdaptor.VedioInfoHolder holder, final int position) {


        holder.vedio_name.setText(vedios.get(position).getName()+"");
        holder.num_of_likes.setText(vedios.get(position).getLikes()+"");

        holder.comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int vedio_id  = vedios.get(position).getId();

                Intent intent = new Intent(ctx.getApplicationContext(), CommentsActivity.class);
                intent.putExtra("vedio_id",vedio_id);
//                intent.putExtra("user_id",user_id);
                ctx.startActivity(intent);


            }
        });

        holder.like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(ctx, "Like Button Clicked"+position, Toast.LENGTH_SHORT).show();
                stringRequest = new StringRequest(Request.Method.POST, url_user_like, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            like_response = jsonObject.getInt("result");

                            if(like_response == 1){

                                holder.num_of_likes.setText(vedios.get(position).getLikes()+1+"");

                            }else {
                                Toast.makeText(ctx, "Already Liked", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();

                        Log.d("LIKE","vedio_id  "+vedios.get(position).getId());
                        Log.d("LIKE","user_id "+user_id);
                        hashMap.put("vedio_id", vedios.get(position).getId()+"");
                        hashMap.put("user_id", user_id+"");
                        return hashMap;
                    }
                };

                requestQueue.add(stringRequest);


            }
        });



        final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {

                youTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }
        };
        holder.youTubeThumbnailView.initialize(KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                youTubeThumbnailLoader.setVideo(vedios.get(position).getVedio_path().toString());
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return vedios.size();
    }

    public class VedioInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        protected YouTubeThumbnailView youTubeThumbnailView;
        protected ImageView playButton;
        protected TextView vedio_name, num_of_likes;
        protected ImageButton like_button, comment_button;




        public VedioInfoHolder(View itemView) {
            super(itemView);
            playButton=(ImageView)itemView.findViewById(R.id.btnYoutube_player);
            playButton.setOnClickListener(this);
            vedio_name = (TextView)itemView.findViewById(R.id.vedio_name);
            num_of_likes = (TextView)itemView.findViewById(R.id.num_of_likes);
            like_button = (ImageButton)itemView.findViewById(R.id.like_button);
            like_button.setOnClickListener(this);
            comment_button = (ImageButton)itemView.findViewById(R.id.comment_button);
            comment_button.setOnClickListener(this);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout)itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView)itemView.findViewById(R.id.youtube_thumbnail);
//            vedio_name.setText(video_names[getLayoutPosition()]);
        }

        @Override
        public void onClick(View view) {

            if(view.getId() == R.id.btnYoutube_player) {
                Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) ctx, KEY, vedios.get(getLayoutPosition()).getVedio_path().toString(), 100, true, false);
                ctx.startActivity(intent);
            }else if(view.getId() == R.id.like_button){
                //on like button click
                Toast.makeText(ctx, "Like Button Clicked", Toast.LENGTH_SHORT).show();
            }else if(view.getId() == R.id.comment_button){
                //on comment button click
                Toast.makeText(ctx, "Comment button clicked", Toast.LENGTH_SHORT).show();
            }


        }
    }
}
