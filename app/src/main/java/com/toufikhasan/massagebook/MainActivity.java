package com.toufikhasan.massagebook;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.massagebook.BuildConfig;
import com.example.massagebook.R;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final long LENGTH_MILLISECONDS = 45000;
    AdsController adsController;
    InternetAccess internetAccess;
    LinearLayout bannerAdsShowLayout;
    AdView mAdView;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Title Change
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.app_name_bn);

        MobileAds.initialize(this, initializationStatus -> {
        });
        mAdView = findViewById(R.id.adView);
        bannerAdsShowLayout = findViewById(R.id.layoutAdsLayout);

        if (countDownTimer == null) {
            AdsLoadAfterSomeTime(LENGTH_MILLISECONDS);
            countDownTimer.start();
        }

        internetAccess = new InternetAccess(this);
        adsController = new AdsController(this);

        // Drawable Show & hide java program

        drawerLayout = findViewById(R.id.dowableId);

        NavigationView navigationView = findViewById(R.id.navication);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (!VisibleDialog()) {
            JOIN_OUR_GROUP_DIALOG_BOX_ABER_VINNO_KISU_APP();
        }

        //this comment is activity change java program

        Button quranerMa = (Button) findViewById(R.id.quranerMa);
        quranerMa.setOnClickListener(view -> startActivityFunction("quranerMa.txt", "কুরআনের মা"));
        Button muminerHatiar = (Button) findViewById(R.id.muminerHatiar);
        muminerHatiar.setOnClickListener(view -> startActivityFunction("mominerHatiar.txt", "মুমিনের হাতিয়ার"));
        Button quranicShistachar = (Button) findViewById(R.id.quranicShistachar);
        quranicShistachar.setOnClickListener(view -> startActivityFunction("quranicSistacher.txt", "কুরআনিক শিষ্টাচার"));
        Button umorDarazDil = (Button) findViewById(R.id.umorDarazDil);
        umorDarazDil.setOnClickListener(view -> startActivityFunction("omorDarajDil.txt", "উমর দারাজ দিল"));
        Button dableStandard = (Button) findViewById(R.id.dableStandard);
        dableStandard.setOnClickListener(view -> startActivityFunction("dableStandert.txt", "ডাবল স্ট্যান্ডার্ড"));
        Button osriUsera = (Button) findViewById(R.id.osriUsera);
        osriUsera.setOnClickListener(view -> startActivityFunction("usriUsora.txt", "উসরি ইউসরা - কষ্টের সাথে স্বস্তি"));
        Button regegelenTo = (Button) findViewById(R.id.regegelenTo);
        regegelenTo.setOnClickListener(view -> startActivityFunction("regeGelenToh.txt", "রেগে গেলেন তো - হেরে গেলেন"));
        Button sassotoJibonBidhan = (Button) findViewById(R.id.sassotoJibonBidhan);
        sassotoJibonBidhan.setOnClickListener(view -> startActivityFunction("shasSotoJibonBidhan.txt", "শাশ্বত জীবন বিধান"));
        Button smartParenting = (Button) findViewById(R.id.smartParenting);
        smartParenting.setOnClickListener(view -> startActivityFunction("smartParenting.txt", "স্মার্ট প্যারেন্টিং"));
        Button mojjid = (Button) findViewById(R.id.mojjid);
        mojjid.setOnClickListener(view -> startActivityFunction("mozjid.txt", "মসজিদ - মুসলিম উম্মাহর নিউক্লিয়াস"));
        Button oisiBorkoterCabi = (Button) findViewById(R.id.oisiBorkoterCabi);
        oisiBorkoterCabi.setOnClickListener(view -> startActivityFunction("oisiBorkoterChabi.txt", "ঐশী বরকতের চাবি"));
        Button bidaiBela = (Button) findViewById(R.id.bidaiBela);
        bidaiBela.setOnClickListener(view -> startActivityFunction("bidayBela.txt", "বিদায় বেলা"));


    }

    public void AdsLoadAfterSomeTime(final long milliseconds) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(milliseconds, 50) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (internetAccess.isConnected()) {
                    // TODO: GOOGLE ADS LOADER
                    if (mAdView != null) {
                        adsController.loadBannerAds(mAdView);
                        bannerAdsShowLayout.setVisibility(View.VISIBLE);
                    } else {
                        bannerAdsShowLayout.setVisibility(View.GONE);
                    }

                }
            }
        };
    }

    public void JOIN_OUR_GROUP_DIALOG_BOX_ABER_VINNO_KISU_APP() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_box);
        dialog.setCancelable(false);

        Button GROUP_JOIN = dialog.findViewById(R.id.group_join_now);
        GROUP_JOIN.setOnClickListener(v -> {
            gotoUrl("https://www.facebook.com/groups/books.my.friend");
            dialog.dismiss();
        });
        TextView alreadyJOIN = dialog.findViewById(R.id.group_already_join);
        alreadyJOIN.setOnClickListener(v -> {
            preferencesDialogSaveData();
            dialog.dismiss();
        });
        ImageView close = dialog.findViewById(R.id.close_btn_dialog);
        close.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    @SuppressLint("ApplySharedPref")
    private void preferencesDialogSaveData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DialogBoxData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("group_join", true);
        editor.commit();

    }

    private boolean VisibleDialog() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("DialogBoxData", MODE_PRIVATE);
        return sharedPreferences.getBoolean("group_join", false);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void startActivityFunction(String fileName, String titleName) {
        Intent intent = new Intent(MainActivity.this, TextShow.class);
        intent.putExtra(TextShow.FILE_NAME, fileName);
        intent.putExtra(TextShow.TITLE_NAME, titleName);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            exitAlertBox();
        }
    }

    public void exitAlertBox() {
        AlertDialog.Builder exitAlert = new AlertDialog.Builder(MainActivity.this);
        // Title set
        exitAlert.setTitle("Alert Dialog.");
        // Massage Set
        exitAlert.setMessage("আপনি কি এখান থেকে বের হতে চান?");
        // Icon set
        exitAlert.setIcon(R.drawable.question);

        // Positive Button
        exitAlert.setCancelable(false);

        exitAlert.setPositiveButton("হ্যাঁ", (dialogInterface, i) -> finish());
        exitAlert.setNegativeButton("না", (dialogInterface, i) -> dialogInterface.cancel());

        AlertDialog alertDialog = exitAlert.create();
        alertDialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home) {
            drawerLayout.closeDrawers();
        } else if (item.getItemId() == R.id.writer) {
            startActivityFunction("site_bar_text/writter_info.txt", "মিজানুর রহমান আজহারী");
        } else if (item.getItemId() == R.id.about_us) {
            startActivityFunction("site_bar_text/about.txt", "আমাদের সম্পর্কে");
        } else if (item.getItemId() == R.id.privacyPolicy) {
            gotoUrl("https://toufikhasan.com/android-apk/book/message-book/privacy-policy.html");
        } else if (item.getItemId() == R.id.heJubok) {
            gotoUrl("https://play.google.com/store/apps/details?id=com.toufikhasan.fireasoroberdike");
        } else if (item.getItemId() == R.id.moreApp) {
            gotoUrl("https://play.google.com/store/apps/dev?id=5871408368342725724");
        } else if (item.getItemId() == R.id.contact_us) {
            startActivityFunction("site_bar_text/contact.txt", "যোগাযোগ পেইজ");
        } else if (item.getItemId() == R.id.facebook) {
            gotoUrl("https://facebook.com/toufik.bd.official");
        } else if (item.getItemId() == R.id.facebook_page) {
            gotoUrl("https://www.facebook.com/golper.somaroh");
        } else if (item.getItemId() == R.id.youtube_toufik) {
            gotoUrl("https://www.youtube.com/channel/UCJWmYNTgEvJsDm0zqj3lIxw");
        } else if (item.getItemId() == R.id.youtube) {
            gotoUrl("https://www.youtube.com/channel/UCyuwOnlZWsDv1IcTOp9l0jA");
        } else if (item.getItemId() == R.id.website) {
            gotoUrl("https://toufikhasan.com/");
        } else if (item.getItemId() == R.id.share) {
            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Message Book App Contact");
            String shareMassage = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + getString(R.string.shair_text_option);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMassage);

            startActivity(Intent.createChooser(shareIntent, "ShareVia"));
        }
        return false;
    }

    private void gotoUrl(String link) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
    }


    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}