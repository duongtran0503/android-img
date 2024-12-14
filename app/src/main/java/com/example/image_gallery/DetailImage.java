package com.example.image_gallery;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class DetailImage extends AppCompatActivity {
    private ImageButton btn;
    private int currentPosition = 0;
    private ViewPager2 viewPager;
    private GestureDetector gestureDetector;
    ArrayList<Uri> imageUris;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_image);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn = findViewById(R.id.button_back);
        btn.setOnClickListener(view -> finish());
         viewPager = findViewById(R.id.view_pager);
        // Lấy danh sách ảnh và vị trí ảnh hiện tại
        imageUris = getIntent().getParcelableArrayListExtra("imageUris");
        currentPosition = getIntent().getIntExtra("position", 0);

        // Thiết lập adapter cho ViewPager2
        ImagePagerAdapter adapter = new ImagePagerAdapter(this, imageUris);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(new ZoomOutPageTransformer());
        viewPager.setCurrentItem(currentPosition,false);

        // sửa lý chạm 3 ngón tay
        // Khởi tạo GestureDetector
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float diffX = e2.getX() - e1.getX();
                float diffY = e2.getY() - e1.getY();

                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                } else {
                    if (diffY > 0) {
                        onSwipeDown();
                    } else {
                        onSwipeUp();
                    }
                }
                return true;
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.d("Gesture", "Single Tap");
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d("Gesture", "Long Press");

            }
        });

    }

    // Hàm chuyển sang ảnh trước
    private void showPreviousImage() {
        if (currentPosition > 0) {
            currentPosition--;
            viewPager.setCurrentItem(currentPosition, true); // Chuyển ảnh về ảnh trước
        }
    }

    // Hàm chuyển sang ảnh tiếp theo
    private void showNextImage() {
        if (currentPosition < imageUris.size() - 1) {
            currentPosition++;
            viewPager.setCurrentItem(currentPosition, true); // Chuyển ảnh về ảnh tiếp theo
        }

}

    // Xử lý sự kiện vuốt phải
    private void onSwipeRight() {
        Log.d("Gesture", "Swipe Right");
    }

    // Xử lý sự kiện vuốt trái
    private void onSwipeLeft() {
        Log.d("Gesture", "Swipe Left");
    }

    // Xử lý sự kiện vuốt xuống
    private void onSwipeDown() {
        Log.d("Gesture", "Swipe Down");
    }
    // Xử lý sự kiện vuốt lên
    private void onSwipeUp() {
        Log.d("Gesture", "Swipe Up");
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

}