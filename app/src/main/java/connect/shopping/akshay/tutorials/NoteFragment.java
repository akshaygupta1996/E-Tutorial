package connect.shopping.akshay.tutorials;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class  NoteFragment extends Fragment {


    private int language_type = 0;
    private static final String url_get_articles = "https://akki7272.000webhostapp.com/tutorials/getArticles.php";
    private StringRequest stringRequest;
    private RequestQueue requestQueue;
    private ArticleAdaptor articleAdaptor;
    private List<Articles> articlesList = new ArrayList<Articles>();
    private RecyclerView recyclerView;
    public NoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note,container,false);
        requestQueue = Volley.newRequestQueue(getContext());
        Bundle bundle = getArguments();
        if (bundle != null) {

            if(bundle.containsKey("type"))
            {
                language_type = bundle.getInt("type");
            }
        }

        recyclerView = (RecyclerView)view.findViewById(R.id.recycle_view_notes);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading..");
        progressDialog.show();



        stringRequest = new StringRequest(Request.Method.POST, url_get_articles, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("RESPONSE",response.toString());
                Log.d("LANGAGE",language_type+"");

                JSONArray jsonArray = null;

                try {
                    jsonArray = new JSONArray(response);

                    for(int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Articles a = new Articles();
                        a.setArticle_id(jsonObject.getInt("article_id"));
                        a.setName(jsonObject.getString("article_name"));
                        a.setText(jsonObject.getString("article_content"));
                        articlesList.add(a);

                    }

                    articleAdaptor = new ArticleAdaptor(articlesList);
                    recyclerView.setAdapter(articleAdaptor);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Unable to Load.Try again later", Toast.LENGTH_SHORT).show();
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
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("language_type",language_type+"");
                return hashMap;
            }
        };

        requestQueue.add(stringRequest);


        // Inflate the layout for this fragment
        return view;
    }

}
