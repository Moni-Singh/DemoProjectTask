package com.example.taskdemo.ui.tab.discover;


import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.taskdemo.R;
import com.example.taskdemo.model.response.Product;

import java.util.ArrayList;
import java.util.List;

public class StickyHeaderItemDecoration extends RecyclerView.ItemDecoration {

    private final int headerLayoutId;
    List<Product> productList = new ArrayList<>();
    public StickyHeaderItemDecoration(int headerLayoutId) {
        this.headerLayoutId = headerLayoutId;
    }
    public void setProductList(List<Product> productList) {
        this.productList = productList;

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        // Find the first visible item
        int firstVisibleItemPosition = ((RecyclerView.LayoutParams) parent.getChildAt(0).getLayoutParams()).getViewLayoutPosition();

        // Check if the first visible item is a header
        if (isFirstItemHeader(parent, firstVisibleItemPosition) && firstVisibleItemPosition != 0) {
            Product product=  productList.get(firstVisibleItemPosition);



            View headerView = LayoutInflater.from(parent.getContext()).inflate(headerLayoutId, parent, false);

            TextView textView =  headerView.findViewById(R.id.tvHeader);
            textView.setText(product.getCategory());
            headerView.setVisibility(View.VISIBLE);


            // Measure and layout the header view
            int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);
            headerView.measure(widthSpec, heightSpec);
            headerView.layout(0, 0, headerView.getMeasuredWidth(), headerView.getMeasuredHeight());

            // Draw the header on the canvas
            c.save();
            c.translate(0, 0); // Adjust the position as needed
            headerView.draw(c);
            c.restore();
        }
    }

    // Helper method to determine if an item is a header
    private boolean isFirstItemHeader(RecyclerView parent, int position) {
        // Implement your logic to determine if an item is a header
        // For example, you could check the item type or use a flag in your adapter
        return true; // Replace with your actual logic
    }
}
