package com.neev.logistinv;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.neev.example.R;
import com.parse.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by chethana.savalgi on 10-10-2015.
 */



public class ManageDataActivity extends Activity implements OnItemSelectedListener{
    Button button;
    private ListView listView;
    ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1=null;
    private static int fromYear;
    private static int fromMonth;
    private static int fromDay;
    private EditText inputSearch;
    public static final String ARG_Type_Id = "type_id";
    static String Item_Type;
    static Bundle bundle;
    private SearchView mSearchView;
    private EditText inputQty;
    private EditText inputPrice;
    private EditText inputDate;


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        if(id ==1)
        {
            adapter1 = new ArrayAdapter<String>(this,R.layout.simplerow);
            NeevDataLayer data = new NeevDataLayer();
            try {
                List rmList = data.retrieveAllRawMaterialFromLocalStore();
                for(int i=0;i< rmList.size();i++)
                {
                    ParseObject po = (ParseObject)rmList.get(i);
                    String name = (String) po.get("Name");
                    adapter1.add(name);
                    //adapter1.
                }
            }
            catch (Exception e)
            {

            }
            listView.setAdapter(adapter1);
        }
        if(id ==0)
        {
            adapter1 = new ArrayAdapter<String>(this,R.layout.simplerow);
            NeevDataLayer data = new NeevDataLayer();
            try {
                List rmList = data.retrieveAllFinishedProductFromLocalStore();
                for(int i=0;i< rmList.size();i++)
                {
                    ParseObject po = (ParseObject)rmList.get(i);
                    String name = (String) po.get("Name");
                    adapter1.add(name);
                    //adapter1.
                }
            }
            catch (Exception e)
            {

            }
            listView.setAdapter(adapter1);
        }

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    protected void onCreate(Bundle savedInstanceState)
    {
        final Calendar c = Calendar.getInstance();
        fromYear = c.get(Calendar.YEAR);
        fromMonth = c.get(Calendar.MONTH);
        fromDay = c.get(Calendar.DAY_OF_MONTH);
        super.onCreate(savedInstanceState);

       /* Item_id = getArguments().getString("type_id");
        Item_Type = getArguments().getString("type_name");

*/

        /*ListView mRawMaterialListView;
        ArrayAdapter<String> listAdapter ;
        listAdapter = new ArrayAdapter<String>(this,R.layout.simplerow);

        mRawMaterialListView = (ListView) findViewById(R.id.rawMateriaListView);
        DataLayer data = new DataLayer();
        try {
            //ParseQuery query = new ParseQuery("RawMaterialTable");
            //query.fromLocalDatastore();

            List rmList = data.retrieveAllRawMaterialFromLocalStore();
            for(int i=0;i< rmList.size();i++)
            {
                ParseObject po = (ParseObject)rmList.get(i);
                String name = (String) po.get("Name");
                listAdapter.add(name);
            }
            }
        catch (Exception e)
        {

        }
        mRawMaterialListView.setAdapter(listAdapter);*/
/*    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {*/

        // View rootView = inflater.inflate(R.layout.managedata, container, false);

        setContentView(R.layout.managedata);

      /*  TextView txtView = (TextView)findViewById(R.id.product_type);
        txtView.setText(Item_Type);*/

        Spinner item_type = (Spinner) findViewById(R.id.itemtype_spinner);
        item_type.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> itemType_adapter = ArrayAdapter.createFromResource(this, R.array.ItemType_array, android.R.layout.simple_spinner_item);
        itemType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Apply the adapter to the spinner
        item_type.setAdapter(itemType_adapter);

        EditText editText = (EditText) findViewById(R.id.editDate);
        editText.setText(fromDay + " / " + fromMonth + " / " + fromYear);
        // Show the dummy content as text in a TextView.
        editText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                DialogFragment newFragment = new DatePickerFragment();
                Bundle args = new Bundle();
                newFragment.setArguments(args);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        String item_id = "0";
        if (Objects.equals(item_id, "0")) {

            adapter1 = new ArrayAdapter<String>(this,R.layout.simplerow);

            listView = (ListView) findViewById(R.id.listView);
            NeevDataLayer data = new NeevDataLayer();
            try {
                List rmList = data.retrieveAllFinishedProductFromLocalStore();
                for(int i=0;i< rmList.size();i++)
                {
                    ParseObject po = (ParseObject)rmList.get(i);
                    String name = (String) po.get("Name");
                    adapter1.add(name);
                    //adapter1.
                }
            }
            catch (Exception e)
            {

            }
            listView.setAdapter(adapter1);
            //    adapter1 = ArrayAdapter.createFromResource(this, R.array.finished_products, android.R.layout.simple_list_item_1 );

        }

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter1);
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                String selectedItem = (String)parent.getItemAtPosition(position);
                inputSearch.setText(selectedItem);
            }

        });


        inputSearch = (EditText) findViewById(R.id.search_item);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ManageFragment.this.adapter1.getFilter().filter(s);
                String text = inputSearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter1.getFilter().filter(text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        inputQty = (EditText) findViewById(R.id.edit_qty);
        inputPrice = (EditText) findViewById(R.id.edit_price);
        inputDate = (EditText) findViewById(R.id.editDate);

        final Button btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                inputQty.setText("");
                inputPrice.setText("");
                inputSearch.setText("");
                inputDate.setText(fromDay + " / " + fromMonth + " / " + fromYear);
            }
        });


        final Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NeevDataLayer data = new NeevDataLayer();
                NeevRawMaterialItem product = new NeevRawMaterialItem();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    String Price = inputPrice.getText().toString();
                    String Qty = inputQty.getText().toString();
                    String strDate = inputDate.getText().toString();
                    product.setName(inputSearch.getText().toString());
                    //product.setCost(inputPrice.getText().toString());
                    //product.setQuantity(Integer.parseInt(Qty));
                    //Date date = format.parse(strDate);
                    //product.setDate(inputDate.getText().toString());

                    boolean isSaveSuccessful =  data.addToStore(product);
                    if(isSaveSuccessful)
                    {
                        Toast.makeText(getApplicationContext(), "Data added successfully", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e)
                {

                }
            }
        });

        //return rootView;
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        static Bundle bundle;
        private static final String SAVED_BUNDLE_TAG = "saved_bundle";

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            //bundle = savedInstanceState.getBundle(SAVED_BUNDLE_TAG);
            bundle = getArguments();
            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            Log.w("DatePicker", "Date = " + year);


            fromDay = day;
            fromMonth = month;
            fromYear = year;
            ((EditText) getActivity().findViewById(R.id.editDate)).setText(fromDay + "/" + fromMonth + "/" + fromYear);

        }

    }
}
