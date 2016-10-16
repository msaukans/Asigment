package com.example.maris.asigment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.app.Dialog;
import android.widget.EditText;
import android.widget.ListView;
import android.graphics.Color;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> arrayList;
    private ArrayList<String> arrayList2;
    private ArrayAdapter<String> adapter1;
    private ArrayAdapter<String> adapter2;
    private EditText textInput;
    private ListView lv1,lv2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        lv1 = (ListView) findViewById(R.id.list1); //************************* settin up first list view
        String[] items1 = {"item1", "item2", "item3"};
        arrayList = new ArrayList<>(Arrays.asList(items1));
        adapter1 = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textItem, arrayList);
        lv1.setAdapter(adapter1);//*********** first listview finished set up
        lv2 = (ListView) findViewById(R.id.list2);
        final String[] items2 = {"thing1", "thing2", "thing3"};
        arrayList2 = new ArrayList<>(Arrays.asList(items2));
        adapter2 = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textItem, arrayList2);
        lv2.setAdapter(adapter2);
        /////////////////////////////////////////////////////////////////// 2nd listview setup finished
        registerForContextMenu(lv1); // popUp menu for first list

        textInput = (EditText) findViewById(R.id.textInput);
        Button addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {//adds items to first adapter
            public void onClick(View v) {
                String newItem = textInput.getText().toString();
                arrayList.add(newItem);
                adapter1.notifyDataSetChanged();
            }

        });//end addButton



        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {//to edit item in first adapter

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Show input box
                showEditBox(arrayList.get(position), position);
            }
        });//end start editItem

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {//to delete item in second adapter

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Show input box
                arrayList2.get(position);
                arrayList2.remove(position);
                adapter2.notifyDataSetChanged();
            }
        });//end delete item

       /* lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {//to copy item to 2nd adapter

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Show input box
                String itemm = "null";
                itemm = arrayList.get(position);
                arrayList2.add(itemm);
                adapter2.notifyDataSetChanged();
            }
        });//end copy item*/
    }//end onCreate

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popup, menu);
    }//end createContextMenu ->popUp menu

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete_id:
                arrayList.remove(info.position);
                adapter1.notifyDataSetChanged();
                return true;
            case R.id.copy_id:
                String itemm = "null-0";
                itemm = arrayList.get(info.position);
                arrayList2.add(itemm);
                adapter2.notifyDataSetChanged();
                return true;
            case R.id.edit_id:
                this.showEditBox(arrayList.get(info.position), info.position);
                return true;

        }
        return super.onContextItemSelected(item);
    }//popup menu finalised

    public void showEditBox(String oldItem, final int index) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Edit Box");
        dialog.setContentView(R.layout.edit_box);
        TextView txtMessage = (TextView) dialog.findViewById(R.id.editMessage);
        txtMessage.setText("Edit Item");
        txtMessage.setTextColor(Color.parseColor("RED"));
        final EditText editText = (EditText) dialog.findViewById(R.id.textInputB);
        editText.setText(oldItem);
        Button bt = (Button) dialog.findViewById(R.id.btDone);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.set(index, editText.getText().toString());
                adapter1.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }//end editItem


}//end Main Activity