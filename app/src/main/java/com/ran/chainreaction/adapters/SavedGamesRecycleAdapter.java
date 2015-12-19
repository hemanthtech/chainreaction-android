package com.ran.chainreaction.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ran.chainreaction.R;
import com.ran.chainreaction.database.ChainReactionDBOpsHelper;
import com.ran.chainreaction.entities.PlayColorValues;
import com.ran.chainreaction.entities.SavedGamesEntity;
import com.ran.chainreaction.gameplay.GamePlayerInfo;
import com.ran.chainreaction.interfaces.SavedGamesSelectionInterface;

import java.util.ArrayList;

/**
 * Created by ranjith on 02/12/15.
 * <p/>
 * Recycler View Adapter Responsible for SavedGames RecyclerView ..
 */
public class SavedGamesRecycleAdapter extends RecyclerView.Adapter<SavedGamesRecycleAdapter.ViewHolder> {

    private ArrayList<SavedGamesEntity> dataSet;
    private SavedGamesSelectionInterface savedGamesSelectionInterface;
    private Context context;

    public SavedGamesRecycleAdapter(Context context, SavedGamesSelectionInterface savedGamesSelectionInterface,
                                    ArrayList<SavedGamesEntity> dataSet) {
        this.dataSet = dataSet;
        this.savedGamesSelectionInterface = savedGamesSelectionInterface;
        this.context = context;
    }

    /**
     * Method to populate the Background of the Player View based on the PlayerColorValues State
     *
     * @param colorValues -- Current Color value Enum
     * @param mTextView   -- textView to be colored
     * @param drawable    -- Drawable to be used
     * @param context     -- Context of the screen
     */
    private static void updateColorView(PlayColorValues colorValues, TextView mTextView, Drawable drawable, Context context) {
        int colorId;
        switch (PlayColorValues.getIndex(colorValues)) {
            case 0:
                colorId = R.color.red_background_color;
                break;
            case 1:
                colorId = R.color.orange_background_color;
                break;
            case 2:
                colorId = R.color.yellow_background_color;
                break;
            case 3:
                colorId = R.color.green_background_color;
                break;
            case 4:
                colorId = R.color.white_background_color;
                break;
            case 5:
                colorId = R.color.blue_background_color;
                break;
            case 6:
                colorId = R.color.violet_background_color;
                break;
            case 7:
                colorId = R.color.pink_background_color;
                break;
            default:
                colorId = R.color.white_background_color;
                break;
        }
        drawable.setColorFilter(context.getResources().getColor(colorId), PorterDuff.Mode.SRC_ATOP);
        mTextView.setBackground(drawable);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_games_layout_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitleGame.setText(dataSet.get(position).getGameName());
        addPlayerInfo(holder.mGamePlayerInfoContainer, position);
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return dataSet.size();
    }


    /**
     * Method to create the Player Info and add to Parent ScrollView
     *
     * @param scrollView -- Parent View
     * @param position   -- Position to retrieve the data
     */
    private void addPlayerInfo(LinearLayout scrollView, int position) {

        //First Remove any Previous elements ..
        scrollView.removeAllViews();

        for (int i = 1; i <= dataSet.get(position).getGamePlayerInfos().size(); i++) {
            GamePlayerInfo currentPlayer = dataSet.get(position).getGamePlayerInfos().get(i - 1);

            TextView textView = new TextView(context);
            textView.setText(currentPlayer.getPlayerName());
            textView.setTextAppearance(context, android.R.style.TextAppearance_Small);
            textView.setAllCaps(true);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextColor(context.getResources().getColor(R.color.player_color_textColor));
            LinearLayout.LayoutParams childParam = new LinearLayout.LayoutParams(context.getResources().
                getDimensionPixelSize(R.dimen.saved_games_item_player_info_size), context.getResources().
                getDimensionPixelSize(R.dimen.saved_games_item_player_info_size));
            childParam.setMargins(context.getResources().getDimensionPixelSize(R.dimen.saved_games_item_player_info_margin),
                context.getResources().getDimensionPixelSize(R.dimen.saved_games_item_player_info_margin),
                context.getResources().getDimensionPixelSize(R.dimen.saved_games_item_player_info_margin),
                context.getResources().getDimensionPixelSize(R.dimen.saved_games_item_player_info_margin));
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(childParam);

            updateColorView(currentPlayer.getPlayerColor(), textView,
                context.getResources().getDrawable(R.drawable.player_color_drawable), context);

            scrollView.addView(textView);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleGame;
        private Button mGamePlay;
        private LinearLayout mGamePlayerInfoContainer;
        private ImageView mGameDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitleGame = (TextView) itemView.findViewById(R.id.saved_games_item_title1);
            mGamePlay = (Button) itemView.findViewById(R.id.saved_games_item_button);
            mGamePlay.setOnClickListener(this);
            mGamePlayerInfoContainer = (LinearLayout) itemView.findViewById(R.id.saved_games_item_playerInfo);
            mGameDelete = (ImageView) itemView.findViewById(R.id.saved_games_item_delete);
            mGameDelete.setOnClickListener(this);
        }


        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.saved_games_item_delete:

                    //Retrieve the Id and delete from DataBase .
                    long toDelId = dataSet.get(getAdapterPosition()).getGameId();
                    boolean deleteStatus = ChainReactionDBOpsHelper.getDBInstance(context).deleteGame(toDelId);
                    if (deleteStatus) {
                        dataSet.remove(getAdapterPosition());
                        notifyDataSetChanged();
                    }

                    //Propagate to UI to show the Empty Message , if data set is null
                    if (dataSet.size() == 0) {
                        savedGamesSelectionInterface.onAllGamesDeleted();
                    }

                    break;

                case R.id.saved_games_item_button:

                    //Logic to start the Saved game ..
                    break;
            }

        }
    }

}
