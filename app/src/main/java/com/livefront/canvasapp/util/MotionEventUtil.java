package com.livefront.canvasapp.util;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.view.MotionEvent;

public class MotionEventUtil {

    public static Point eventToPoint(@NonNull MotionEvent event) {
        return new Point(
                (int) event.getX(),
                (int) event.getY());
    }

    public static PointF eventToPointF(@NonNull MotionEvent event) {
        return new PointF(
                event.getX(),
                event.getY());
    }

    public static Rect sqRectFromPoint(@NonNull Point point,
                                       int size,
                                       int horizontalLimit,
                                       int verticalLimit,
                                       int paddingW,
                                       int paddingH) {
        int areaHalf = size / 2;
        int left = point.x - areaHalf;
        left = left < paddingW
                ? paddingW
                : left;
        int top = point.y - areaHalf;
        top = top < paddingH
                ? paddingH
                : top;

        int right = left + areaHalf;
        right = right > paddingW + horizontalLimit
                ? paddingW + horizontalLimit
                : right;
        left = right - areaHalf;

        int bottom = top + areaHalf;
        bottom = bottom > paddingH + verticalLimit
                ? paddingH + verticalLimit
                : bottom;
        top = bottom - areaHalf;
        return new Rect(left, top, right, bottom);
    }

    public static MultiRect getMultiRect(@NonNull Point startPoint,
                                         @NonNull Point endPoint,
                                         int horizontalLimit,
                                         int verticalLimit,
                                         int paddingWidth,
                                         int paddingHeight,
                                         int rectGap) {
        int left = startPoint.x < endPoint.x
                ? startPoint.x
                : endPoint.x;
        left = left < paddingWidth
                ? paddingWidth
                : left;
        int top = startPoint.y < endPoint.y
                ? startPoint.y
                : endPoint.y;
        top = top < paddingHeight
                ? paddingHeight
                : top;
        int right = startPoint.x >= endPoint.x
                ? startPoint.x
                : endPoint.x;
        right = right > horizontalLimit + paddingWidth
                ? horizontalLimit + paddingWidth
                : right;
        int bottom = startPoint.y >= endPoint.y
                ? startPoint.y
                : endPoint.y;
        bottom = bottom > verticalLimit + paddingHeight
                ? verticalLimit + paddingHeight
                : bottom;

        Rect innerRect = new Rect(
                left + rectGap,
                top + rectGap,
                right - rectGap,
                bottom - rectGap);
        Rect outerRect = new Rect(left, top, right, bottom);
        return new MultiRect(innerRect, outerRect);
    }

    public static class MultiRect implements Parcelable {

        public final Rect innerRect;
        public final Rect outerRect;

        public MultiRect(@NonNull Rect innerRect, @NonNull Rect outerRect) {
            this.innerRect = innerRect;
            this.outerRect = outerRect;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(outerRect, flags);
            dest.writeParcelable(innerRect, flags);
        }

        private MultiRect(Parcel in) {
            outerRect = in.readParcelable(Rect.class.getClassLoader());
            innerRect = in.readParcelable(Rect.class.getClassLoader());
        }

        public static final Creator<MultiRect> CREATOR = new Creator<MultiRect>() {
            @Override
            public MultiRect createFromParcel(Parcel source) {
                return new MultiRect(source);
            }

            @Override
            public MultiRect[] newArray(int size) {
                return new MultiRect[size];
            }
        };
    }

}
