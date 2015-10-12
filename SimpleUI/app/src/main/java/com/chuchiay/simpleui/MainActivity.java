package com.chuchiay.simpleui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText inputText;
    private CheckBox hide;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    private ListView listView;
    private Spinner storeInfoSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("settings", Context.MODE_PRIVATE);
        editor = sp.edit();


        inputText = (EditText)findViewById(R.id.inputText);
        inputText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                String text = inputText.getText().toString();
                editor.putString("inputText", text);
                editor.commit();

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        submit(view);
                        return true;
                    }

                }

                return false;
            }
        });

        hide = (CheckBox) findViewById(R.id.hideCheckbox);
        hide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("hideCheckBox", isChecked);
                editor.commit();
            }
        });
        inputText.setText(sp.getString("inputText", ""));
        hide.setChecked(sp.getBoolean("hideCheckBox", false));

        listView = (ListView) findViewById(R.id.testListView);
        storeInfoSpinner = (Spinner) findViewById(R.id.storeInfoSpinner);

        setList();
        setStoreInfo();
    }
    private void setStoreInfo(){
        String[] data = getResources().getStringArray(R.array.storeInfo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,data);
        storeInfoSpinner.setAdapter(adapter);
    }
    private void setList(){
        if(Utils.readFile(this,"test.txt") != null) {
            String[] data = Utils.readFile(this, "test.txt").split("\n");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);

            listView.setAdapter(adapter);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void submit(View view){
        String text = inputText.getText().toString();

        if(hide.isChecked()){
            inputText.setText("********");
        }

        Utils.writeFile(this,"test.txt",text + "\n");
        text = Utils.readFile(this,"test.txt");
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
        inputText.setText("");
        setList();
    }
}
