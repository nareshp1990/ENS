package com.ens;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ens.adapters.CarouselViewListener;
import com.ens.adapters.NewsCardViewAdapter;
import com.ens.adapters.PollCardViewAdapter;
import com.ens.adapters.VideoViewAdapter;
import com.ens.adapters.YoutubeVideoAdapter;
import com.ens.config.ENSApplication;
import com.ens.model.CarouselViewItem;
import com.ens.model.NewsCardViewItem;
import com.ens.model.PollCardItem;
import com.ens.model.YoutubeVideoItem;
import com.ens.nav.drawer.DrawerHeader;
import com.ens.nav.drawer.DrawerMenuItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.placeholderview.PlaceHolderView;
import com.synnapps.carouselview.CarouselView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getCanonicalName();

    @BindView(R.id.drawerView)
    private PlaceHolderView mDrawerView;

    @BindView(R.id.drawerLayout)
    private DrawerLayout mDrawer;

    @BindView(R.id.toolbar)
    private Toolbar mToolbar;

    @BindView(R.id.scrollTextView)
    private TextView scrollTextView;

    @BindView(R.id.mainPageCarouselView)
    private CarouselView mainPageCarouselView;

    @BindView(R.id.youtubeThumbnailRecyclerView)
    private RecyclerView youtubeThumbnailRecyclerView;

    @BindView(R.id.newsCardRecyclerView)
    private RecyclerView newsCardRecyclerView;

    @BindView(R.id.newsPollRecyclerView)
    private RecyclerView newsPollRecyclerView;

    @BindView(R.id.ensVideoRecyclerView)
    private RecyclerView ensVideoRecyclerView;

    @BindView(R.id.fabPostNews)
    private FloatingActionButton fabPostNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnifeLite.bind(this);
        hideToolbarTitle();

        setupDrawer();
        initializePage();

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                if (!task.isSuccessful()) {
                    Log.w(TAG, "getInstanceId failed", task.getException());
                    return;
                }

                // Get new Instance ID token
                String token = task.getResult().getToken();

                Log.d(TAG, "### Firebase Token: " + token );


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ENSApplication.activityPaused();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ENSApplication.activityResumed();
    }

    public void onEvent(Boolean isInternetAvailable){
        Toast.makeText(this, isInternetAvailable ? "Internet Available" : "Internet Not Available", Toast.LENGTH_SHORT).show();
    }

    public void hideToolbarTitle() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(null);
        mToolbar.setTitle("");
        mToolbar.setSubtitle("");
        mToolbar.setLogo(null);
    }

    private void setupDrawer() {

        mDrawerView
                .addView(new DrawerHeader())
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_REQUESTS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_MESSAGE))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_GROUPS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_NOTIFICATIONS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_TERMS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_SETTINGS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_LOGOUT));

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //remove drawer icon
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.setHomeAsUpIndicator(null);
    }

    private void initializePage() {

        scrollTextView.setSelected(true);

        // Start Main Page Carousel View
        List<CarouselViewItem> carouselViewItems = prepareCarouselImageViewData();

        CarouselViewListener carouselViewListener = new CarouselViewListener(carouselViewItems, this);
        mainPageCarouselView.setPageCount(carouselViewItems.size());
        mainPageCarouselView.setViewListener(carouselViewListener);
        mainPageCarouselView.setImageClickListener(carouselViewListener);
        // End Main Page Carousel View

        youtubeThumbnailRecyclerView.setAdapter(new YoutubeVideoAdapter(prepareYoutubeVideoItems(), this));
        youtubeThumbnailRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        newsCardRecyclerView.setAdapter(new NewsCardViewAdapter(this, prepareNewsCardViewItems()));
        newsCardRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        newsPollRecyclerView.setAdapter(new PollCardViewAdapter(this, preparePollCardItems()));
        newsPollRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ensVideoRecyclerView.setAdapter(new VideoViewAdapter(this, prepareENSVideoViewItems()));
        ensVideoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        fabPostNews.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Create News", Toast.LENGTH_SHORT).show());

    }

    private List<CarouselViewItem> prepareCarouselImageViewData() {

        List<CarouselViewItem> carouselViewItems = new ArrayList<>();

        carouselViewItems.add(new CarouselViewItem("https://i0.wp.com/www.eerojunews.net/wp-content/uploads/2019/11/WhatsApp-Image-2019-11-29-at-4.32.46-PM.jpeg", "వ్యక్తిగత పట్టాలు పంపిణీ చేయడానికి చర్యలు..ఐటిడి ఏ పి ఓ డికె బాలాజీ"));
        carouselViewItems.add(new CarouselViewItem("https://i2.wp.com/www.eerojunews.net/wp-content/uploads/2019/11/1.jpg", "క్రీడలతో మెరుగైన ఆరోగ్యం..ఇండియన్ రైల్వే బాక్సింగ్ కోచ్ దుర్గాప్రసాద్"));
        carouselViewItems.add(new CarouselViewItem("https://i0.wp.com/www.eerojunews.net/wp-content/uploads/2019/11/WhatsApp-Image-2019-11-27-at-11.08.30-AM.jpeg", "విశాఖలో క్రిస్మస్ మ్యూజిక్ కాన్సెర్ట్ పోస్టర్ ఆవిష్కరణ…డా.జాన్ క్రిష్టోఫర్"));
        carouselViewItems.add(new CarouselViewItem("https://i1.wp.com/www.eerojunews.net/wp-content/uploads/2019/11/WhatsApp-Image-2019-11-27-at-4.57.24-PM.jpeg", "పిహెచ్ సిల ఆధునీకరణకు అంచనాలు రూపొందించండి..ఐటిడి ఏ పి ఓ డికె బాలాజీ"));

        return carouselViewItems;
    }

    private List<YoutubeVideoItem> prepareYoutubeVideoItems() {

        List<YoutubeVideoItem> youtubeVideoItems = new ArrayList<>();

        youtubeVideoItems.add(new YoutubeVideoItem("sxXwkVlmyOI", "face to face with swethareddy on bigboss show costing couch|"));
        youtubeVideoItems.add(new YoutubeVideoItem("LhT1bcGR1iA", "nadeeswara pooja better talking for child babys"));
        youtubeVideoItems.add(new YoutubeVideoItem("MnRQrPHJd5M", "face to face on alluri girijana zilla sadanasamithi turaga sriram"));
        youtubeVideoItems.add(new YoutubeVideoItem("BQfOtbmhm_c", "kalala rahadari pai book by sr journalist nnr"));
        youtubeVideoItems.add(new YoutubeVideoItem("zLKU2HvaULc", "ens live|news is adventure by eeroju news service| ens live| vizag"));

        return youtubeVideoItems;
    }

    private List<NewsCardViewItem> prepareNewsCardViewItems() {

        List<NewsCardViewItem> newsCardViewItems = new ArrayList<>();

        NewsCardViewItem newsCardViewItem = new NewsCardViewItem();
        newsCardViewItem.setImageUrl("https://i2.wp.com/www.eerojunews.net/wp-content/uploads/2019/10/924a227b-b123-44dc-8a48-286667464e45.jpg");
        newsCardViewItem.setHeadLine("గ్రామసచివాలయ ఉద్యోగులకు 14 నుంచి ప్రత్యేక శిక్షణ");
        newsCardViewItem.setDescription("గ్రామసచివాలయ ఉద్యోగాలు పొందిన అభ్యర్ధులకు అక్టోబరు 14 నుంచి వారం రోజుల పాటు అన్ని విభాగాల ఉద్యోగలకు ప్రత్యేక శిక్షణ వుంటుందని డిజిఓ వి.క్రిష్ణకుమారి తెలియజేశారు. విశాఖలో ఆమె మీడియాతో మాట్లాడుతూ, ఉద్యోగులకు తొలి వారం రోజుల పాటు రెసిడెన్సియల్ శిక్షణ వుంటుందన్నారు. దానికి తగ్గట్టుగా నగరంలో ఏర్పాట్లు చేసినట్టు ఆమె వివరించారు. అభ్యర్దుల జాయినింగ్ అనంతరం వారికి శిక్షణకు సంబంధించిన సమాచారం పంపనున్నట్టు వెల్లడించారు.");
        newsCardViewItem.setStrCreatedOn("October 5, 2019");
        newsCardViewItem.setViews(1000);
        newsCardViewItem.setLikes(234);
        newsCardViewItem.setUnLikes(20);
        newsCardViewItem.setComments(1290);
        newsCardViewItem.setWhatsAppShares(35);
        newsCardViewItem.setFacebookShares(900);
        newsCardViewItem.setInstagramShares(9080);
        newsCardViewItem.setHelloAppShares(9034);

        newsCardViewItems.add(newsCardViewItem);

        NewsCardViewItem newsCardViewItem2 = new NewsCardViewItem();
        newsCardViewItem2.setImageUrl("https://i1.wp.com/www.eerojunews.net/wp-content/uploads/2019/11/WhatsApp-Image-2019-11-27-at-4.57.24-PM.jpeg");
        newsCardViewItem2.setHeadLine("పిహెచ్ సిల ఆధునీకరణకు అంచనాలు రూపొందించండి..ఐటిడి ఏ పి ఓ డికె బాలాజీ");
        newsCardViewItem2.setDescription("ప్రాధమిక ఆరోగ్య కేంద్రాల ఆధునీకరణకు ప్రతిపాదనలు సిద్దం చేయాలని సమీకృత గిరిజనాభివృధ్ది సంస్ధ ప్రాజెక్టు అధికారి డికె బాలాజీ ఆదేశించారు. గురువారం ఆయన కార్యాలయంలో గిరిజన సంక్షేమ శాఖ ఇంజనీరింగ్ అధికారులు, ఎపి ఎస్ ఎం ఐడిసి ఇంజనీర్లతో ఆసుపత్రుల ఆధునీకరణపై\n" +
                "\n" +
                "సమావేశం నిర్వహించారు. ప్రభుత్వం నిర్దేశించిన విధంగా ఆధునీకరించడానికి చర్యలు చేపట్టాలన్నారు. ఉన్న భవనాలను మార్చకుండా అదనపు వసతి సౌకర్యాలు కల్పించాలని చెప్పారు. మొదటి విడతలో జి.మాడుగుల, కె.డి.పేట, తాజంగి, పెదబయలు, ఉప్ప , ఈదులపాలెం, ఆసుపత్రులను ఆధునీకరిస్తామని చెప్పారు.\n" +
                "\n" +
                "ఆసుపత్రులకు,ప్లోరింగ్, ప్రహారీ గోడలు, ఆసుపత్రి ఆవరణలో సిసిరోడ్లు పనులు ,మురికి కాలువల నిర్మాణాలు చేపట్టాలని సూచించారు. ఆసుపత్రి ఇప్పటికే ఉన్న గదులను మార్పు చేయకూడదన్నారు. అవసరమైన చోట వైద్యాధికారి సూచనల మేరకు అదనపు వార్డులను నిర్మించాలని స్పష్టం చేసారు. ఆసుపత్రి భవనంలో లీకేజీలను అదుపు చేయాలన్నారు.\n" +
                "ఈ సమావేశంలో గిరిజన సంక్షేమశాఖ ఈ ఈ లు కె వి ఎస్ ఎన్ కుమార్, మురళి, ఎపి ఎస్ ఎం ఐ డిసి ఈ ఈ ఉమేష్ కుమార్, గిరిజన సంక్షేమశాఖ డి. ఇ డివి ఆర్ ఎం రాజు, అదనపు జిల్లా వైద్యాధికారి కిషోర్ కుమార్, వైద్యాధికారులు లీలా ప్రసాద్, శివ ప్రసాద్ తదితరులు పాల్గొన్నారు.");
        newsCardViewItem2.setStrCreatedOn("November 28, 2019");
        newsCardViewItem2.setViews(1020);
        newsCardViewItem2.setLikes(2343);
        newsCardViewItem2.setUnLikes(220);
        newsCardViewItem2.setComments(120);
        newsCardViewItem2.setWhatsAppShares(350);
        newsCardViewItem2.setFacebookShares(9043);
        newsCardViewItem2.setInstagramShares(9343);
        newsCardViewItem2.setHelloAppShares(9023);

        newsCardViewItems.add(newsCardViewItem2);


        return newsCardViewItems;
    }


    private List<PollCardItem> preparePollCardItems() {

        List<PollCardItem> pollCardItems = new ArrayList<>();

        PollCardItem pollCardItem = new PollCardItem();
        pollCardItem.setQuestion("How long have you used our products/service?");
        pollCardItem.setOption1("Less than 6 months");
        pollCardItem.setOption2("1 year to less than 3 years");
        pollCardItem.setOption3("3 years to less than 5 years");
        pollCardItem.setOption4("5 years or more");
        pollCardItem.setStrPollCreatedOn("30 November 2019");

        pollCardItem.setOption1Votes(29);
        pollCardItem.setOption2Votes(200);
        pollCardItem.setOption3Votes(212);
        pollCardItem.setOption4Votes(23);
        pollCardItem.setTotalVotes(660);

        pollCardItems.add(pollCardItem);

        PollCardItem pollCardItem2 = new PollCardItem();
        pollCardItem2.setQuestion("How would you rate your overall satisfaction with us?");
        pollCardItem2.setOption1("Very satisfied");
        pollCardItem2.setOption2("Somewhat satisfied");
        pollCardItem2.setOption3("Neutral");
        pollCardItem2.setOption4("Somewhat dissatisfied");
        pollCardItem2.setStrPollCreatedOn("30 November 2019");

        pollCardItem2.setOption1Votes(229);
        pollCardItem2.setOption2Votes(220);
        pollCardItem2.setOption3Votes(512);
        pollCardItem2.setOption4Votes(233);
        pollCardItem2.setTotalVotes(1530);

        pollCardItems.add(pollCardItem2);

        return pollCardItems;
    }

    private List<NewsCardViewItem> prepareENSVideoViewItems() {

        List<NewsCardViewItem> newsCardViewItems = new ArrayList<>();

        NewsCardViewItem newsCardViewItem = new NewsCardViewItem();
        newsCardViewItem.setVideoThumbnailUrl("https://i2.wp.com/www.eerojunews.net/wp-content/uploads/2019/10/924a227b-b123-44dc-8a48-286667464e45.jpg");
        newsCardViewItem.setHeadLine("గ్రామసచివాలయ ఉద్యోగులకు 14 నుంచి ప్రత్యేక శిక్షణ");
        newsCardViewItem.setDescription("");
        newsCardViewItem.setStrCreatedOn("October 5, 2019");
        newsCardViewItem.setViews(1000);
        newsCardViewItem.setLikes(234);
        newsCardViewItem.setUnLikes(20);
        newsCardViewItem.setComments(1290);
        newsCardViewItem.setWhatsAppShares(35);
        newsCardViewItem.setFacebookShares(900);
        newsCardViewItem.setInstagramShares(9080);
        newsCardViewItem.setHelloAppShares(9034);
        newsCardViewItem.setVideoUrl("https://androidwave.com/media/androidwave-video-exo-player.mp4");

        newsCardViewItems.add(newsCardViewItem);

        NewsCardViewItem newsCardViewItem2 = new NewsCardViewItem();
        newsCardViewItem2.setVideoThumbnailUrl("https://i1.wp.com/www.eerojunews.net/wp-content/uploads/2019/11/WhatsApp-Image-2019-11-27-at-4.57.24-PM.jpeg");
        newsCardViewItem2.setHeadLine("పిహెచ్ సిల ఆధునీకరణకు అంచనాలు రూపొందించండి..ఐటిడి ఏ పి ఓ డికె బాలాజీ");
        newsCardViewItem2.setDescription("");
        newsCardViewItem2.setStrCreatedOn("November 28, 2019");
        newsCardViewItem2.setViews(1020);
        newsCardViewItem2.setLikes(2343);
        newsCardViewItem2.setUnLikes(220);
        newsCardViewItem2.setComments(120);
        newsCardViewItem2.setWhatsAppShares(350);
        newsCardViewItem2.setFacebookShares(9043);
        newsCardViewItem2.setInstagramShares(9343);
        newsCardViewItem2.setHelloAppShares(9023);

        newsCardViewItem2.setVideoUrl("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");

        newsCardViewItems.add(newsCardViewItem2);


        return newsCardViewItems;
    }

}
