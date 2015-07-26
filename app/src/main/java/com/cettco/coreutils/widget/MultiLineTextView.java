package com.cettco.coreutils.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cettco on 7/26/15.
 * Unchecked
 * When the content in the textView contains both Chinese and English
 * characters, the content is not aligned.
 * So it should rewrite the onDraw.
 */
public class MultiLineTextView extends TextView {
    private Paint paint;
    private int row = 2;
    private int lineSpacing;

    public MultiLineTextView(Context context) {
        super(context);
        paint = new Paint();
    }

    public MultiLineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.paint.setTextSize(this.getTextSize());
        List<String> list = splitString();
        Paint.FontMetrics metrics = this.paint.getFontMetrics();
        float baseline = metrics.descent - metrics.ascent;
        float x = 0;
        float y = baseline;
        if (list.size() == 2 && super.getLineCount() == 1) {
            canvas.drawText(getText().toString(), x, y, this.paint);
        } else {
            for (int i = 0; i < list.size(); i++) {
                canvas.drawText(list.get(i), x, y, this.paint);
                y += baseline + lineSpacing;
            }
        }
    }

    private List<String> splitString() {
        List<String> list = new ArrayList<String>();
        float width = getWidth() - this.getTextSize();
        String content = this.getText().toString();
        int contentLenght = content.length();
        int curRow = 1;
        for (int start = 0, end = 1; end <= contentLenght; end++) {
            if (this.paint.measureText(content, start, end) > width) {
                if (curRow < row) {
                    list.add(content.substring(start, end));
                    start = end;
                    row++;
                } else if (curRow == row) {
                    if (this.getEllipsize() != null) {
                        list.add(content.substring(start, end - 1) + "...");
                    } else {
                        list.add(content.substring(start, end));
                    }
                    break;
                }
            } else {

            }
            if (end == contentLenght) {
                if (curRow <= row) {
                    list.add(content.substring(start, end));
                }
            }
        }
        return list;
    }
}
