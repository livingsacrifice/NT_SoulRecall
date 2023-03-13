/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.me.ntmemtest;

/**
 *
 * @author srichard
 */
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;

public class ShowSettingsActivity extends PreferenceActivity {
     @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        String str1 = (((ColorPickerPreference)findPreference("color1")).convertToARGB(((ColorPickerPreference)findPreference("color1")).getValue())).toString();
        ((ColorPickerPreference)findPreference("color1")).setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
            preference.setSummary(ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(newValue))));
            return true;
            }

                    });
        ((ColorPickerPreference)findPreference("color1")).setAlphaSliderEnabled(true);
        ((ColorPickerPreference)findPreference("color2")).setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
            preference.setSummary(ColorPickerPreference.convertToARGB(Integer.valueOf(String.valueOf(newValue))));
            return true;
            }

                    });
        ((ColorPickerPreference)findPreference("color2")).setAlphaSliderEnabled(true);
        //((Preference)findPreference("add_OT")).setEnabled(false);

     }
}
