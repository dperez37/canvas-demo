package com.livefront.canvasapp.util;

import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;

public class AdapterUtil {

    public static class OnSeekBarChangeAdapter implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // no-op
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // no-op
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // no-op
        }
    }

    public static class OnItemSelectedAdapter implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // no-op
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // no-op
        }
    }
}
