package connect.shopping.akshay.tutorials.Comments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import connect.shopping.akshay.tutorials.R;

public class CommentsActivity extends AppCompatActivity {

    private ListView comment_list_view;
    private List<Comment> comments_list;
    private RequestQueue requestQueue;
    private StringRequest stringRequest, send_comment_string_request;
    private CommentAdaptor commentAdaptor;
    private static final String url_fetch_comments = "https://akki7272.000webhostapp.com/tutorials/getAllComments.php";
    private static final String url_send_comment = "https://akki7272.000webhostapp.com/tutorials/send_comment.php";
    private ProgressDialog progressDialog;
    private int vedio_id;
    private EditText edtComment;
    private ImageButton btnSend;
    private String user_comment;
    private int user_id;
    private SharedPreferences prefs;
    private int send_flag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

//        getActivity().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        comments_list = new ArrayList<Comment>();
        requestQueue = Volley.newRequestQueue(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        user_id = prefs.getInt("user_id",0);

        vedio_id = getIntent().getIntExtra("vedio_id",0);
//        user_id = getIntent().getIntExtra("user_id",0);

        comment_list_view = (ListView)findViewById(R.id.comment_list_view);

        edtComment = (EditText)findViewById(R.id.send_comment);
        btnSend = (ImageButton)findViewById(R.id.send_button);


        commentAdaptor = new CommentAdaptor(this,comments_list);
        comment_list_view.setAdapter(commentAdaptor);

        fetchData(vedio_id);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                user_comment = edtComment.getText().toString();
                //vedio_id
                //user_id
                
                send_comment_string_request = new StringRequest(Request.Method.POST, url_send_comment, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject comment_obj = null;
                        try {
                            comment_obj = new JSONObject(response);
                            send_flag = comment_obj.getInt("status");
                            if(send_flag==0){
                                Toast.makeText(CommentsActivity.this, "Comment not send..Please try again..!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(CommentsActivity.this, "Comment Success", Toast.LENGTH_SHORT).show();
                                fetchData(vedio_id);
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
                        hashMap.put("comment",user_comment);
                        hashMap.put("user_id",user_id+"");
                        hashMap.put("vedio_id",vedio_id+"");
                        
                        
                        return hashMap;
                    }
                };

                requestQueue.add(send_comment_string_request);
                edtComment.setText("");

            }
        });







    }

    private void fetchData(final int vedio_id){

        comments_list.clear();

        stringRequest = new StringRequest(Request.Method.POST, url_fetch_comments, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
                    Log.d("JSON",response.toString());

                    for(int i = 0;i<jsonArray.length();i++){

                        JSONObject jsonObject = (JSONObject)jsonArray.get(i);

                        Comment c = new Comment();
                        String fname = "";
                        String lname  = "";
                        c.setUser_id(jsonObject.getInt("user_id"));
                        c.setVedio_id(jsonObject.getInt("vedio_id"));
                        c.setComment(jsonObject.getString("comment_text"));
                        fname = jsonObject.getString("fname");
                        lname = jsonObject.getString("lname");
                        c.setUser_name(fname + " "+lname);
                        c.setTime(jsonObject.getString("time"));

                        comments_list.add(c);
                    }
                    commentAdaptor.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", "Error: " + error.getMessage());


            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("vedio_id",vedio_id+"");
                return hashMap;


            }
        };

        requestQueue.add(stringRequest);

    }
}
