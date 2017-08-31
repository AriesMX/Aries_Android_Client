package mx.com.aries.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mx.com.aries.R;
import mx.com.aries.adapters.ScenesAdapter;
import mx.com.aries.entities.Scene;


public class Scenes extends Fragment {
    Context mContex;
    FragmentActivity mActivity;

    private RecyclerView recyclerView;
    private ScenesAdapter adapter;
    private List<Scene> sceneList;

    public Scenes(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_scenes, container, false);

        mActivity= getActivity();
        mContex= mActivity.getApplicationContext();

        sceneList = new ArrayList<>();
        adapter = new ScenesAdapter(mActivity, sceneList);

        recyclerView = (RecyclerView) view.findViewById(R.id.scenes_recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContex, 1);
        recyclerView.setLayoutManager(mLayoutManager);
       // recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareScenes();

        return view;
    }

    private void prepareScenes() {
        /*Scene a = new Scene("True Romance", "Description", R.drawable.music);
        sceneList.add(a);

        a = new Scene("Xscpae","Description",  R.drawable.mexico1);
        sceneList.add(a);

        a = new Scene("Maroon 5", "Description",  R.drawable.mexico3);
        sceneList.add(a);

        a = new Scene("Born to Die", "Description",  R.drawable.mexico4);
        sceneList.add(a);

        a = new Scene("Honeymoon", "Description",  R.drawable.mexico5);
        sceneList.add(a);

        a = new Scene("I Need a Doctor", "Description",  R.drawable.mexico6);
        sceneList.add(a);

        a = new Scene("Loud", "Description",  R.drawable.mexico7);
        sceneList.add(a);

        a = new Scene("Legend", "Description",  R.drawable.mexico8);
        sceneList.add(a);

        a = new Scene("Hello", "Description",  R.drawable.mexico9);
        sceneList.add(a);

        a = new Scene("Greatest Hits","Description",  R.drawable.mexico10);
        sceneList.add(a);

        a = new Scene("Greatest Hits","Description",  R.drawable.mexico11);
        sceneList.add(a);

        a = new Scene("Greatest Hits","Description",  R.drawable.mexico12);
        sceneList.add(a);

        a = new Scene("Greatest Hits","Description",  R.drawable.mexico13);
        sceneList.add(a);

        adapter.notifyDataSetChanged();*/
    }
}
