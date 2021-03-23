package com.example.photogram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class PostDetailActivity extends AppCompatActivity {

    private TextView tvUsername;
    private ImageView ivImage;
    private TextView tvDescription;
    private TextView tvTimestamp;

    //private ActivityPostDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        //binding = DataBindingUtil.setContentView(this,R.layout.activity_post_detail);

        tvUsername = findViewById(R.id.tvUsername);
        ivImage = findViewById(R.id.ivImage);
        tvDescription = findViewById(R.id.tvDescription);
        tvTimestamp = findViewById(R.id.tvTimestamp);

        Post post = Parcels.unwrap(getIntent().getParcelableExtra("post"));
        //binding.setPost(post);

        tvDescription.setText(post.getDescription());
        tvUsername.setText(post.getUser().getUsername());
        tvTimestamp.setText(post.getTimeDate());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(ivImage);
        }
    }
}