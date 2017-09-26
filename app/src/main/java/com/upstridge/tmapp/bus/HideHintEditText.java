package com.upstridge.tmapp.bus;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Wango-PC on 5/23/2017.
 */

public class HideHintEditText extends android.support.v7.widget.AppCompatEditText {

    String hint;

    public HideHintEditText(Context context, String hint) {
        super(context);
        this.hint = hint;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if(text.length() > 0)
            this.setHint("");
        else
            this.setHint(hint);
    }

}

