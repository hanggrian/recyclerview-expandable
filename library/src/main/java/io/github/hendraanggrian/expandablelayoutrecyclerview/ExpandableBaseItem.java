package io.github.hendraanggrian.expandablelayoutrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by hendraanggrian on 14/06/16.
 */
public interface ExpandableBaseItem {

    void init(Context context, AttributeSet attrs);

    void expand(View v);

    void collapse(View v);

    void hideNow();

    void showNow();

    Boolean isOpened();

    void show();

    FrameLayout getHeaderLayout();

    FrameLayout getContentLayout();

    void hide();

    Boolean getCloseByUser();

    void setOnClickListener(View.OnClickListener onClickListener);

}