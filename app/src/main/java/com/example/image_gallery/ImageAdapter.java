package com.example.image_gallery;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Uri> imageUris;

    public ImageAdapter(Context context, ArrayList<Uri> imageUris) {
        this.context = context;
        this.imageUris = imageUris;
    }
    @Override
    public int getCount() {
        return imageUris.size();
    }

    @Override
    public Object getItem(int position) {
        return imageUris.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.image_view);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Lấy URI của hình ảnh từ danh sách
        Uri imageUri = Uri.parse(String.valueOf(imageUris.get(position)));

        // Kiểm tra xem Uri có hợp lệ không
        if (imageUri != null) {
            Log.d("ImageAdapter", "Loading.....  " + imageUri.toString());


                Glide.with(context)
                        .load(imageUri)
                        .centerCrop()
                        . override(100, 100)
                        .placeholder(R.drawable.img_not_found)
                        .error(R.drawable.img_not_found)
                        .into(viewHolder.imageView)
                        ;

        } else {
            Log.d("ImageAdapter", "Uri is null for position: " + position);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}
