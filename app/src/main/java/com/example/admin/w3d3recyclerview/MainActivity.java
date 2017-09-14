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


        /*famousData = new ArrayList<>();
        famousData.add(new Famous("CHARLIZE THERON", "Date of Birth: August 7, 1975 | Birth Place: Benoni, South Africa", R.drawable.charlize));
        famousData.add(new Famous("ANNA KENDRICK", "Date of Birth: August 9, 1985 | Birth Place: Portland, ME", R.drawable.annakendrick164008421));
        famousData.add(new Famous("CHANNING TATUM", "Date of Birth: April 26, 1980 | Birth Place: Cullman, AL", R.drawable.channing162613136));
        famousData.add(new Famous("CHRIS EVANS", "Date of Birth: June 13, 1981 | Birth Place: Sudbury, MA", R.drawable.chrisevans12));
        famousData.add(new Famous("GEORGE CLOONEY", "Date of Birth: May 6, 1961 | Birth Place: Lexington, KY", R.drawable.clooneyperfp));
        famousData.add(new Famous("GAL GADOT", "Date of Birth: April 30, 1985 | Birth Place: Rosh Ha'Ayin, Israel", R.drawable.gadot));
        famousData.add(new Famous("JOHNNY DEPP", "Date of Birth: June 9, 1963 | Birth Place: Owensboro, KY", R.drawable.johnnydeep));
        famousData.add(new Famous("Tom Cruise2", "Actor2", R.drawable.kristinwiig162157550));
        famousData.add(new Famous("JENNIFER LAWRENCE", "Date of Birth: August 15, 1990 | Birth Place: Louisville, KY", R.drawable.lawrencejennifer));
        famousData.add(new Famous("JARED LETO", "Date of Birth: December 26, 1971 | Birth Place: Bossier City, LA", R.drawable.leto));
        famousData.add(new Famous("ROBERT DOWNEY, JR.", "Date of Birth: April 4, 1965 | Birth Place: New York, NY\n", R.drawable.robert162574544));
        famousData.add(new Famous("SCARLETT JOHANSSON", "Date of Birth: November 22, 1984 | Birth Place: New York, NY", R.drawable.scarlett159652076));
        famousData.add(new Famous("EMMA STONE", "Date of Birth: November 6, 1988  | Birth Place:Scottsdale, Arizona, U.S.", R.drawable.stone159231705));
        famousData.add(new Famous("WILL SMITH", "Date of Birth: September 25, 1968 | Birth Place: Philadelphia, PA", R.drawable.willsmith169638915));
*/

//        String json = new Gson().toJson(famousData);
        DatabaseHelper databaseHelper = new DatabaseHelper(this, "");
        databaseHelper.loadData();
        //databaseHelper.insertDB(json);
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
