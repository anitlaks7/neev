package com.neev.logistinv;

import java.util.ArrayList;

import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.neev.example.R;


public class ListViewMultipleSelectionActivity extends Activity implements
        OnClickListener {

    private Button button;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_multiple_selection);

        findViewsById();

        String[] sports = getResources().getStringArray(R.array.sports_array);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, sports);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);

        button.setOnClickListener(this);
    }
//TODO - prevent this activity from getting on the back stack
    private void findViewsById() {
        listView = (ListView) findViewById(R.id.listView);
        button = (Button) findViewById(R.id.button);
    }

    public void onClick(View v) {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        ArrayList<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(adapter.getItem(position));
        }

        String[] outputStrArr = new String[selectedItems.size()];

        for (int i = 0; i < selectedItems.size(); i++) {
            outputStrArr[i] = selectedItems.get(i);
        }

        Toast.makeText(getApplicationContext(), "Configuration saved successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(),
                MainActivity.class);

        // Create a bundle object
        Bundle b = new Bundle();

        b.putStringArray("selectedItems", outputStrArr);

        // Add the bundle to the intent.
        intent.putExtras(b);

        // start the ResultActivity
        startActivity(intent);
    }
}
