package mx.com.aries.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import mx.com.aries.entities.Scene;

import java.util.List;

import mx.com.aries.R;

public class ScenesAdapter extends RecyclerView.Adapter<ScenesAdapter.SceneViewHolder> {

    private Context mContext;
    private List<Scene> scenesList;

    public class SceneViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle, mDescription;
        public ImageView mPreviewImageUrl, mMenu;

        public SceneViewHolder(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.fr_scene_card_title);
            mDescription = (TextView) view.findViewById(R.id.fr_scene_card_description);
            mPreviewImageUrl = (ImageView) view.findViewById(R.id.fr_scene_card_preview_image_url);
            mMenu = (ImageView) view.findViewById(R.id.fr_scene_card_menu);
        }
    }


    public ScenesAdapter(Context mContext, List<Scene> scenesList) {
        this.mContext = mContext;
        this.scenesList = scenesList;
    }

    @Override
    public SceneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scene_card, parent, false);

        return new SceneViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SceneViewHolder holder, int position) {
        Scene scene = scenesList.get(position);
        holder.mTitle.setText(scene.getName());
        holder.mDescription.setText(scene.getDescription());

        // loading album cover using Glide library
       Glide.with(mContext).load(scene.getPreviewImageUrl()).into(holder.mPreviewImageUrl);

        holder.mMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.mMenu);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.scene_card_menu, popup.getMenu());
       // popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.scene_fragment_card_menu_1:
                    Toast.makeText(mContext, mContext.getString(R.string.scene_fragment_card_menu_1), Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.scene_fragment_card_menu_2:
                    Toast.makeText(mContext,mContext.getString(R.string.scene_fragment_card_menu_2), Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return scenesList.size();
    }
}