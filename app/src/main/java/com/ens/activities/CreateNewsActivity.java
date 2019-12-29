package com.ens.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

import com.ens.R;
import com.ens.bus.FileUploadedEvent;
import com.ens.bus.NewsCreatedEvent;
import com.ens.model.news.ContentType;
import com.ens.model.news.NewsItemRequest;
import com.ens.model.news.NewsType;
import com.ens.model.news.VideoRequest;
import com.ens.model.news.VideoType;
import com.ens.service.ContentService;
import com.ens.service.NewsService;
import com.ens.utils.AppUtils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import de.greenrobot.event.EventBus;

import static com.ens.utils.AppUtils.CAMERA_REQUEST_CODE;
import static com.ens.utils.AppUtils.GALLERY_REQUEST_CODE;
import static com.ens.utils.AppUtils.PERMISSION_REQUEST_CODE;

public class CreateNewsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final String TAG = CreateNewsActivity.class.getCanonicalName();


    @BindView(R.id.autoCompleteContentType)
    private AutoCompleteTextView autoCompleteContentType;

    @BindView(R.id.newsImageView)
    private ImageView newsImageView;

    @BindView(R.id.txtNewsHeadline)
    private TextInputEditText txtNewsHeadline;

    @BindView(R.id.txtNewsDescription)
    private TextInputEditText txtNewsDescription;

    @BindView(R.id.txtNewsVideoUrl)
    private TextInputEditText txtNewsVideoUrl;

    @BindView(R.id.txtYouTubeVideoId)
    private TextInputEditText txtYouTubeVideoId;

    @BindView(R.id.switchInternational)
    private Switch switchInternational;

    @BindView(R.id.layoutVideoUrl)
    private TextInputLayout layoutVideoUrl;

    @BindView(R.id.layoutYouTubeVideoId)
    private TextInputLayout layoutYouTubeVideoId;

    @BindView(R.id.btnSubmit)
    private Button btnSubmit;

    private String cameraFilePath;

    private EventBus eventBus = EventBus.getDefault();

    private ContentService contentService;

    private NewsService newsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_news);
        ButterKnifeLite.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btnSubmit.setOnClickListener(this);
        newsImageView.setOnClickListener(this);

        autoCompleteContentType.setThreshold(1);
        autoCompleteContentType.setAdapter(new ArrayAdapter<>
                (this, android.R.layout.select_dialog_item, AppUtils.getContentTypes()));
        autoCompleteContentType.setOnItemClickListener(this);

        contentService = new ContentService(this);
        newsService = new NewsService(this);

        if (!AppUtils.checkPermission(this)) {
            AppUtils.requestPermission(this);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.newsImageView: {

                this.cameraFilePath = AppUtils.selectImage(this);

            }
            break;

            case R.id.btnSubmit: {

                File imageFile = new File(cameraFilePath);

                contentService.uploadFile(imageFile);
            }
            break;

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (ContentType.valueOf(parent.getAdapter().getItem(position).toString())) {
            case VIDEO: {
                layoutVideoUrl.setVisibility(View.VISIBLE);
                layoutYouTubeVideoId.setVisibility(View.GONE);
            }
            break;
            case YOUTUBE: {
                layoutYouTubeVideoId.setVisibility(View.VISIBLE);
                layoutVideoUrl.setVisibility(View.GONE);
            }
            break;
            case IMAGE:
            case SCROLL:
            case IMAGE_SLIDER:
            case LIVE: {
                layoutYouTubeVideoId.setVisibility(View.GONE);
                layoutVideoUrl.setVisibility(View.GONE);
            }
            break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case CAMERA_REQUEST_CODE: {

                    newsImageView.setImageURI(Uri.parse(cameraFilePath));

                }
                break;

                case GALLERY_REQUEST_CODE: {

                    Uri selectedImage = data.getData();

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    // Set the Image in ImageView after decoding the String
                    newsImageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

                    cameraFilePath = imgDecodableString;

                    //newsImageView.setImageURI(selectedImage);

                }
                break;

            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {

                if (grantResults.length > 0) {

                    if (!AppUtils.isPermissionsGranted(grantResults)) {

                        AppUtils.requestPermission(this);

                    }

                }

            }
            break;
        }

    }

    public void onEvent(FileUploadedEvent fileUploadedEvent) {

        if (fileUploadedEvent != null && fileUploadedEvent.getContentResponse() != null) {

            Log.d(TAG, "### Content URL : " + fileUploadedEvent.getContentResponse().getDownloadUrl());


            String contentType = autoCompleteContentType.getText().toString();
            String headLine = txtNewsHeadline.getText().toString();
            String description = txtNewsDescription.getText().toString();
            String videoUrl = txtNewsVideoUrl.getText().toString();
            String videoId = txtYouTubeVideoId.getText().toString();
            boolean isInternational = switchInternational.isChecked();


            switch (ContentType.valueOf(contentType)) {

                case VIDEO:
                case YOUTUBE: {

                    VideoRequest videoRequest = new VideoRequest();
                    videoRequest.setThumbnailImageUrl(fileUploadedEvent.getContentResponse().getDownloadUrl());
                    videoRequest.setHeadLine(headLine);
                    videoRequest.setContentType(ContentType.valueOf(contentType));
                    videoRequest.setDescription(description);
                    videoRequest.setInternational(isInternational);
                    videoRequest.setNewsType(NewsType.NEWS);
                    videoRequest.setVideoType(VideoType.NORMAL);
                    videoRequest.setVideoUrl(videoUrl);
                    videoRequest.setYoutubeVideoId(videoId);

                    newsService.createVideoNews(videoRequest);

                }
                break;
                default: {

                    NewsItemRequest newsItemRequest = new NewsItemRequest();
                    newsItemRequest.setImageUrl(fileUploadedEvent.getContentResponse().getDownloadUrl());
                    newsItemRequest.setHeadLine(headLine);
                    newsItemRequest.setContentType(ContentType.valueOf(contentType));
                    newsItemRequest.setDescription(description);
                    newsItemRequest.setInternational(isInternational);
                    newsItemRequest.setNewsType(NewsType.NEWS);

                    newsService.createNews(newsItemRequest);

                }
                break;
            }


        }

    }

    public void onEvent(NewsCreatedEvent newsCreatedEvent) {

        if (newsCreatedEvent != null && newsCreatedEvent.getApiResponse() != null) {

            Log.d(TAG, "### News Created : " + newsCreatedEvent.getApiResponse().getId());

        }

    }

}
