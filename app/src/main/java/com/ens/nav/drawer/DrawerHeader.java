package com.ens.nav.drawer;

import android.widget.ImageView;
import android.widget.TextView;

import com.ens.R;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;

@NonReusable
@Layout(R.layout.drawer_header)
public class DrawerHeader {

    @BindView(R.id.userProfileImageView)
    private ImageView profileImage;

    @BindView(R.id.userNameTextView)
    private TextView nameTxt;

    @BindView(R.id.userEmailTextView)
    private TextView emailTxt;

    @Resolve
    private void onResolved() {
        nameTxt.setText("Naresh Patchipulusu");
        emailTxt.setText("patchipulusu.naresh@gmail.com");
    }
}
