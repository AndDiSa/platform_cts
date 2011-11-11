/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.theme.cts;

import com.android.cts.theme.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * This activity exists solely for debugging purposes. It is
 * the first activity seen when launching the manual viewer of tests.
 * It allows the user to select whether they want to see the tests
 * in portrait or landscape mode.
 */
public class OrientationSelectorActivity extends Activity {
    private static final String[] mOrientations = {"landscape", "portrait"};
    /**
     * Called with the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themetestlistactivity);

        ListView lv = (ListView) findViewById(R.id.tests_list);

        lv.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mOrientations));

        lv.setOnItemClickListener(mTestClickedListener);
    }

    private OnItemClickListener mTestClickedListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent =
                new Intent(OrientationSelectorActivity.this, ThemeSelectorActivity.class);
            intent.putExtra(ThemeTests.EXTRA_ORIENTATION, position);
            startActivity(intent);
        }
    };
}