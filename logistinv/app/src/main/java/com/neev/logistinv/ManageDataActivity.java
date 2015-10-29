package com.neev.logistinv;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
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

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

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
    private String ItemType;


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,R.layout.simplerow);
        String strSelected = parent.getSelectedItem().toString();
        adapter1 = PopulateList(strSelected);

        listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(adapter1);
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                inputSearch.setText(selectedItem);
            }

        });
        Spinner spnMoveFrom = (Spinner)findViewById(R.id.spnMoveFrom);
        if(strSelected.equals(MainActivity.RAW_MATERIAL)) {
            spnMoveFrom.setSelection(0);
            spnMoveFrom.setEnabled(false);
        }
        else
            spnMoveFrom.setEnabled(true);

        ResetFields();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    //return rootView;
    public ArrayAdapter<String> PopulateList(String ItemType)

    {
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this,R.layout.simplerow);
        Intent intent = getIntent();
        NeevDataLayer data = new NeevDataLayer();
        List rmList = null;
        if(ItemType.equalsIgnoreCase(MainActivity.RAW_MATERIAL))
        {
            rmList = data.retrieveAllRawMaterialFromLocalStore();
        }
        else
        {
            rmList = data.retrieveAllFinishedProductFromLocalStore();

        }

        for(int i=0;i< rmList.size();i++)
        {
            ParseObject po = (ParseObject)rmList.get(i);
            String name = (String) po.get("Name");
            listAdapter.add(name);

        }

        return listAdapter;
    }

    protected void onCreate(Bundle savedInstanceState)
    {
        final Calendar c = Calendar.getInstance();
        fromYear = c.get(Calendar.YEAR);
        fromMonth = c.get(Calendar.MONTH)+1;
        fromDay = c.get(Calendar.DAY_OF_MONTH);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.managedata);

        Intent intent = getIntent();
        ItemType = intent.getStringExtra("item_type");
        NeevDataLayer data = new NeevDataLayer();

        Spinner spnItemType = (Spinner) findViewById(R.id.itemtype_spinner);
        spnItemType.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> itemType_adapter = ArrayAdapter.createFromResource(this, R.array.dashboardItemArray, android.R.layout.simple_spinner_item);
        itemType_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Apply the adapter to the spinner
        spnItemType.setAdapter(itemType_adapter);
        spnItemType.setSelection(getIndex(spnItemType, ItemType));

        final Spinner spnMoveFrom = (Spinner) findViewById(R.id.spnMoveFrom);
       // spnItemType.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> movefromstage = ArrayAdapter.createFromResource(this, R.array.movefromstage, android.R.layout.simple_spinner_item);
        movefromstage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Apply the adapter to the spinner
//        movefromstage.insert("From Stage:",0);
        spnMoveFrom.setAdapter(movefromstage);

        spnMoveFrom.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
                int item = spnMoveFrom.getSelectedItemPosition();
                if (item == 0)
                    ((Button) findViewById(R.id.btnSave)).setText("Add");
                else
                    ((Button) findViewById(R.id.btnSave)).setText("Move");
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        if(spnItemType.getSelectedItem().toString().equals(MainActivity.RAW_MATERIAL)) {
            spnMoveFrom.setSelection(0);
            spnMoveFrom.setEnabled(false);
        }

        adapter1 = PopulateList(ItemType);
        listView = (ListView) findViewById(R.id.listView);

        listView.setAdapter(adapter1);
        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                inputSearch.setText(selectedItem);
            }

        });


        EditText editText = (EditText) findViewById(R.id.editDate);
        editText.setText(fromDay + "/" + fromMonth + "/" + fromYear);
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
                ResetFields();
            }
        });


        final Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!ValidateData())
                    return;
                NeevDataLayer data = new NeevDataLayer();
                NeevRawMaterialItem trans=new NeevRawMaterialItem();
                NeevProductItem prodtrans=new NeevProductItem();
                boolean isSaveSuccessful;

                try {
                    double price =Double.parseDouble(inputPrice.getText().toString());
                    int qty =Integer.parseInt(inputQty.getText().toString());

                    double dblTotal = price * qty;
                    //Date creationDate = dateformat.parse(inputDate.getText().toString());
                    //Date creationDate = new Date(fromYear,fromMonth,fromDay);
                    //String creationDate=inputDate.getText().toString();
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(fromYear, fromMonth-1, fromDay,10,0,0);
                    Date creationDate = calendar.getTime();
                    String name=inputSearch.getText().toString();
                    Spinner stage=(Spinner)findViewById(R.id.itemtype_spinner);
                    String strStage=stage.getSelectedItem().toString();
                    if(strStage.equalsIgnoreCase(MainActivity.RAW_MATERIAL))
                    {
                        if(data.checkNewRawMaterial(name))
                        {
                            NeevRawMaterialMaster newRawMaterial = new NeevRawMaterialMaster();
                            newRawMaterial.setName(name);
                            newRawMaterial.setID(0);
                            newRawMaterial.setUnit("TBD");
                            data.addToRawMaterialMaster(newRawMaterial);
                        }
                        trans.setName(name);
                        trans.setQty(qty);
                        trans.setUnitPrice(price);
                        trans.setDate(creationDate);
                        trans.setTotal(Double.parseDouble(String.format("%.2f", dblTotal)));
                        isSaveSuccessful = data.addToRMStore(trans);
                    }
                    else
                    {
                       if(data.checkNewProduct(name))
                        {
                            FinishedProductMaster newProd = new FinishedProductMaster();
                            newProd.setName(name);
                            newProd.setID(0);
                            data.addToProductMaster(newProd);
                        }
                        prodtrans.setPName(name);
                        prodtrans.setPQty(qty);
                        prodtrans.setPPrice(price);
                        prodtrans.setPDate(creationDate);
                        prodtrans.setPType(strStage);
                        prodtrans.setPTotal(Double.parseDouble(String.format("%.2f", dblTotal)));

                        String strFromStage = spnMoveFrom.getSelectedItem().toString();
                        isSaveSuccessful = data.addToProdStore(prodtrans);
                        if(isSaveSuccessful && !strFromStage.equalsIgnoreCase("from stage:"))
                        {
                            NeevProductItem Item = new NeevProductItem();
                            Item.setPName(name);
                            Item.setPType(strFromStage);
                            Item.setPPrice(price);
                            Item.setPDate(creationDate);
                            Item.setPQty(-qty);
                            Item.setPTotal(-Double.parseDouble(String.format("%.2f", dblTotal)));
                            isSaveSuccessful = data.addToProdStore(Item);
                        }
                    }

                    if(isSaveSuccessful)
                    {
                        Toast.makeText(getApplicationContext(), "Data added successfully", Toast.LENGTH_LONG).show();
                        ResetFields();
                        adapter1 = PopulateList(strStage);
            listView = (ListView) findViewById(R.id.listView);

            listView.setAdapter(adapter1);
            listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        final int position, long id) {
                    String selectedItem = (String) parent.getItemAtPosition(position);
                    inputSearch.setText(selectedItem);
                }
            });
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    //public boolean

    private int getIndex(Spinner spinner,String string){



        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++){

            if (spinner.getItemAtPosition(i).equals(string)){
                index = i;
            }

        }

        return index;

    }

        public void ResetFields() {
            inputQty.setText("");
            inputPrice.setText("");
            inputSearch.setText("");
            inputDate.setText(fromDay + "/" + fromMonth + "/" + fromYear);
            Spinner spn = (Spinner)findViewById(R.id.spnMoveFrom);
            spn.setSelection(0);
        }

        public boolean ValidateData()
        {
            if(inputQty.getText().toString().trim().equals("") ||inputPrice.getText().toString().trim().equals("")
                    || inputSearch.getText().toString().trim().equals("") || inputDate.getText().toString().trim().equals(""))
                return false;
            else
                return  true;

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

            Log.w("DatePicker", "Date = " + day +"/"+(month+1)+"/"+year);


            fromDay = day;
            fromMonth = month+1;
            fromYear = year;
            ((EditText) getActivity().findViewById(R.id.editDate)).setText(fromDay + "/" + fromMonth + "/" + fromYear);

        }

    }

}
