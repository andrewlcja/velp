package idp.velp.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tokenautocomplete.TokenCompleteTextView;

import java.util.Arrays;

import idp.velp.R;

/**
 * Created by andrew.lim.2013 on 9/6/2016.
 */
public class TokenCompletionView extends TokenCompleteTextView<String> {
    private String[] options;

    public TokenCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View getViewForObject(String name) {

        LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        LinearLayout view = (LinearLayout) l.inflate(R.layout.token_default, (ViewGroup) TokenCompletionView.this.getParent(), false);
        ((TextView) view.findViewById(R.id.token_name)).setText(name);

        //colour clear icon
        ImageView tokenIconClear = (ImageView) view.findViewById(R.id.token_icon_clear);
        tokenIconClear.setColorFilter(R.color.windowColor, PorterDuff.Mode.MULTIPLY);

        return view;
    }

    @Override
    protected String defaultObject(String completionText) {
        if (options != null) {
            if (!Arrays.asList(options).contains(completionText)) {
                return "";
            }
        }
        return completionText;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
}