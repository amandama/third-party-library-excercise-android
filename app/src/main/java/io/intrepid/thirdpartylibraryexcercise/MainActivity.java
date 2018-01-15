package io.intrepid.thirdpartylibraryexcercise;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainPresenter presenter;
    private ProgressDialog dialog;

    @BindView(R.id.image_view)
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter();
        presenter.bindView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.bindView(this);
    }

    @OnClick(R.id.new_cat_button)
    public void onNewCatButtonClick() {
        presenter.onNewCatButtonClick();
    }

    @Override
    public void showLoadingIndicator() {
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading");
        dialog.show();
    }

    @Override
    public void dismissLoadingIndicator() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void loadImageFromUrl(String url) {
        Glide.with(this)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        dismissLoadingIndicator();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        dismissLoadingIndicator();
                        return false;
                    }
                })
                .into(imageView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unbindView();
    }
}
