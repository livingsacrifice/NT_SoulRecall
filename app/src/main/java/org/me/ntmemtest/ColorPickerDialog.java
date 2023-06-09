/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.ntmemtest;

/**
 *
 * @author srichard
 */
import android.app.Dialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class ColorPickerDialog
extends
Dialog
implements
ColorPickerView.OnColorChangedListener,
View.OnClickListener {

private ColorPickerView mColorPicker;

private ColorPickerPanelView mOldColor;
private ColorPickerPanelView mNewColor;

private OnColorChangedListener mListener;

public interface OnColorChangedListener {
public void onColorChanged(int color);
}

public ColorPickerDialog(Context context, int initialColor) {
super(context);

init(initialColor);
}

private void init(int color) {
// To fight color branding.
getWindow().setFormat(PixelFormat.RGBA_8888);

setUp(color);

}

private void setUp(int color) {

LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

View layout = inflater.inflate(R.layout.dialog_color_picker, null);

setContentView(layout);

setTitle(R.string.dialog_color_picker);

mColorPicker = (ColorPickerView) layout.findViewById(R.id.color_picker_view);
mOldColor = (ColorPickerPanelView) layout.findViewById(R.id.old_color_panel);
mNewColor = (ColorPickerPanelView) layout.findViewById(R.id.new_color_panel);

((LinearLayout) mOldColor.getParent()).setPadding(
Math.round(mColorPicker.getDrawingOffset()),
0,
Math.round(mColorPicker.getDrawingOffset()),
0
);

mOldColor.setOnClickListener(this);
mNewColor.setOnClickListener(this);
mColorPicker.setOnColorChangedListener(this);
mOldColor.setColor(color);
mColorPicker.setColor(color, true);

}

@Override
public void onColorChanged(int color) {

mNewColor.setColor(color);

/*
if (mListener != null) {
mListener.onColorChanged(color);
}
*/

}

public void setAlphaSliderVisible(boolean visible) {
mColorPicker.setAlphaSliderVisible(visible);
}

/**
* Set a OnColorChangedListener to get notified when the color
* selected by the user has changed.
* @param listener
*/
public void setOnColorChangedListener(OnColorChangedListener listener){
mListener = listener;
}

public int getColor() {
return mColorPicker.getColor();
}

@Override
public void onClick(View v) {
switch(v.getId()) {
case R.id.old_color_panel:
break;
case R.id.new_color_panel:
if (mListener != null) {
mListener.onColorChanged(mNewColor.getColor());
}
break;
}
dismiss();
}

}