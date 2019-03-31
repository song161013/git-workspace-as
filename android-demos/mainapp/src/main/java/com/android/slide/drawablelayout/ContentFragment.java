package com.android.slide.drawablelayout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.main.app.R;

/**
 * Created by songfei on 2018/12/11
 * Description：
 */
public class ContentFragment extends Fragment {
    public static final String SELECTED_ITEM = "selected_item";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bd = getArguments();

        View view = inflater.inflate(R.layout.layout_slide_fragment_content, null);
        ((TextView) view).setText(bd.getString(SELECTED_ITEM));
        return view;
    }
}
