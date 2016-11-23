package com.apockestafe.team19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.facebook.FacebookSdk;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;


public class EventInfo extends AppCompatActivity {

   // private Button mapButton, listRideButton, backButton;
    private Button mapButton, listRideButton, backButton, inviteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        mapButton = (Button)findViewById(R.id.MapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventInfo.this, MapsActivity.class));

            }
        });

        listRideButton = (Button)findViewById(R.id.listRideButton);
        listRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventInfo.this, ListRideActivity.class));

            }
        });

        backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(EventInfo.this, LoginActivity.class));
                finish();
            }
        });
        inviteButton = (Button)findViewById(R.id.inviteButton);
        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonHandler();
            }
        });
    }

    public void buttonHandler() {
        String appLinkUrl, previewImageUrl;
        appLinkUrl = "https://fb.me/1033003976822219";
        previewImageUrl = "www.google.com";
        if(AppInviteDialog.canShow()) {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(previewImageUrl)
                    .setPromotionDetails("Example Promo msg", "expromo")
                    .build();
            AppInviteDialog.show(this,content);
        }
    }
}
