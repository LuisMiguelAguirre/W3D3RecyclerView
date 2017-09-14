package com.example.admin.w3d3recyclerview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.facebook.stetho.Stetho;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {

    RecyclerView famousRecyclerView;
    ArrayList<Famous> famousData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);

        DatabaseHelper databaseHelper = new DatabaseHelper(this, "");
        databaseHelper.loadData();
        famousData = databaseHelper.getFamousData();

        setUpRecyclerView();


    }

    private void setUpRecyclerView() {

        famousRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_activity_main);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        famousRecyclerView.setLayoutManager(layoutManager);

        RecyclerView.ItemAnimator itemAnimator;
        itemAnimator = new DefaultItemAnimator();
        famousRecyclerView.setItemAnimator(itemAnimator);

        RecyclerViewFamouseAdapter recyclerViewFamouseAdapter = new RecyclerViewFamouseAdapter(famousData);
        famousRecyclerView.setAdapter(recyclerViewFamouseAdapter);

        setUpItemTouchHelper();
        //setUpAnimationDecoratorHelper();

    }

    private void setUpItemTouchHelper() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int swipedPosition = viewHolder.getAdapterPosition();
                RecyclerViewFamouseAdapter recyclerViewFamouseAdapter =  (RecyclerViewFamouseAdapter) famousRecyclerView.getAdapter();
                recyclerViewFamouseAdapter.remove(swipedPosition);
            }
        };
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(famousRecyclerView);
    }

    private void setUpAnimationDecoratorHelper() {
        famousRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration(){
            Drawable background;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                initiated = true;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                if (!initiated) {
                    init();
                }

                if (parent.getItemAnimator().isRunning()) {
                    View lastViewComingDown = null;
                    View firstViewComingUp = null;

                    // this is fixed
                    int left = 0;
                    int right = parent.getWidth();

                    // this we need to find out
                    int top = 0;
                    int bottom = 0;

                    // find relevant translating views
                    int childCount = parent.getLayoutManager().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = parent.getLayoutManager().getChildAt(i);
                        if (child.getTranslationY() < 0) {
                            // view is coming down
                            lastViewComingDown = child;
                        } else if (child.getTranslationY() > 0) {
                            // view is coming up
                            if (firstViewComingUp == null) {
                                firstViewComingUp = child;
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = lastViewComingDown.getBottom();
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.getTop();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    }

                    background.setBounds(left, top, right, bottom);
                    background.draw(c);

                }
                super.onDraw(c, parent, state);
            }

        });
    }
}
