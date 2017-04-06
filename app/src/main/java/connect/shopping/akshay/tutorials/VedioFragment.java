package connect.shopping.akshay.tutorials;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.Map;


public class VedioFragment extends Fragment {

    private int language_type = 0;
    private RequestQueue requestQueue;
//    private StringRequest stringRequest;
    private final static  String url_fetch_vedios = "https://akki7272.000webhostapp.com/tutorials/getAllVedios.php";


    public VedioFragment() {


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_vedio, container, false);

        requestQueue = Volley.newRequestQueue(getContext());
        Bundle bundle = getArguments();
        if (bundle != null) {

            if(bundle.containsKey("type"))
            {
                language_type = bundle.getInt("type");
            }
        }

        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        final ArrayList<VedioClass> vedio_list = new ArrayList<VedioClass>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading..");
        progressDialog.show();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_fetch_vedios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
                    Log.d("JSON",response.toString());

                    for(int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject = (JSONObject)jsonArray.get(i);

                        VedioClass vedio = new VedioClass();
                        vedio.setId(jsonObject.getInt("id"));
                        vedio.setName(jsonObject.getString("name"));
                        vedio.setVedio_path(jsonObject.getString("path"));
                        vedio.setLikes(jsonObject.getInt("likes"));
                        vedio_list.add(vedio);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RecyclerAdaptor adaptor = new RecyclerAdaptor(getContext(), language_type, vedio_list);
                recyclerView.setAdapter(adaptor);
                progressDialog.dismiss();




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.d("Error","Error: "+error.getMessage());


            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("language_type",language_type+"");
                return hashMap;
            }
        };

        //AppController.getInstance().addToRequestQueue(stringRequest,"fetch_vedios");
        requestQueue.add(stringRequest);


        return view;
    }


}
