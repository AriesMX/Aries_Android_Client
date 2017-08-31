package mx.com.aries.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import mx.com.aries.BuildConfig;
import mx.com.aries.R;
import mx.com.aries.adapters.MyScenesAdapter;
import mx.com.aries.entities.Scene;
import mx.com.aries.entities.WebApiResult;
import mx.com.aries.listeners.EndlessRecyclerViewScrollListener;
import mx.com.aries.network.GenericRequest;
import mx.com.aries.network.VolleySingleton;

public class MyScenes extends Fragment {
    private static final String TAG = "MyScenes";

    private VolleySingleton mVolley;
    Context mContex;
    FragmentActivity mActivity;
    private EndlessRecyclerViewScrollListener scrollListener;

    private RecyclerView recyclerView;
    private MyScenesAdapter adapter;
    private List<Scene> sceneList;

    public MyScenes() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_my_scenes, container, false);

        mActivity= getActivity();
        mContex= mActivity.getApplicationContext();

        sceneList = new ArrayList<>();
        adapter = new MyScenesAdapter(mActivity, sceneList);

        recyclerView = (RecyclerView) view.findViewById(R.id.my_scenes_recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContex, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener((GridLayoutManager)mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadScenes(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);

        mVolley = VolleySingleton.getInstance(mContex);
        loadScenes(1);
        return view;
    }

    public void loadScenes(int page) {
        int idUser=2;
        String url = BuildConfig.WEB_API_URL + "users/"+idUser+"/scenes?page=" + page;
        GenericRequest request = new GenericRequest<WebApiResult>(Request.Method.GET, url, WebApiResult.class, null, new Response.Listener<WebApiResult>() {
            @Override
            public void onResponse(WebApiResult response) {
                if (response.getCode() == 0) {
                    Scene a = new Scene(1,"hola","hola des",12,13,2,"http://192.168.15.194:8090/AriesWebAPIResources/Scenes/Images/2017/08/08/S_2_20170808224100935.png");
                    sceneList.add(a);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(mContex,"Error " + response.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContex,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, false);
        mVolley.addToRequestQueue(request, TAG);




        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }
}
