package com.example.navjot.teamproject272;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class tableActivity extends Activity {

    private static int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        count++;
        super.onCreate(savedInstanceState);
        //Toast.makeText(getApplicationContext(),"the count is: "+count,Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_table);
//-----------------------------------------------------------------------------------------------------
        TextView t1=(TextView) findViewById(R.id.textView3);
        TextView t2=(TextView) findViewById(R.id.TextView3);
        TextView t3=(TextView) findViewById(R.id.View3);

        TextView t4=(TextView) findViewById(R.id.textView4);
        TextView t5=(TextView) findViewById(R.id.TextView4);
        TextView t6=(TextView) findViewById(R.id.View4);

        TextView t7=(TextView) findViewById(R.id.textView5);
        TextView t8=(TextView) findViewById(R.id.TextView5);
        TextView t9=(TextView) findViewById(R.id.View5);
//-----------------------------------------------------------------------------------------------------
        String i1="0.00"; // getting String
        String i2="0.00"; // getting String
        String i3="0.00"; // getting String

        String s1="0.00"; // getting String
        String s2="0.00"; // getting String
        String s3="0.00"; // getting String

        String n1="0.00"; // getting String
        String n2="0.00"; // getting String
        String n3="0.00"; // getting String
//-----------------------------------------------------------------------------------------------------
        Button button = (Button) findViewById(R.id.comparebutton);
        Button closeButton = (Button) findViewById(R.id.button3);
        Button refreshButton=(Button)findViewById(R.id.button4);
        Button buyButton=(Button)findViewById(R.id.button2);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
         i1=pref.getString("i1", null); // getting String
         i2=pref.getString("i2", null); // getting String
         i3=pref.getString("i3", null); // getting String

         s1=pref.getString("s1", null); // getting String
         s2=pref.getString("s2", null); // getting String
         s3=pref.getString("s3", null); // getting String

         n1=pref.getString("n1", null); // getting String
         n2=pref.getString("n2", null); // getting String
         n3=pref.getString("n3", null); // getting String
//------------------------------------------------------------------------------------------------------

        if(i1==null|| i1==""){
            i1="0.00";
            i2="0.00";
            i3="0.00";
        }
        if(s1==null || s1==""){
            s1="0.00";
            s2="0.00";
            s3="0.00";
        }
        if(n1==null || n1==""){
            n1="0.00";
            n2="0.00";
            n3="0.00";

        }

//-------------------------------------------------------------------------------------------------------
      final double c1 = Double.parseDouble(i1);
       final  double c2 = Double.parseDouble(i2);
        final double c3 = Double.parseDouble(i3);

        final double c4 = Double.parseDouble(s1);
        final double c5 = Double.parseDouble(s2);
        final double c6 = Double.parseDouble(s3);

        final double c7 = Double.parseDouble(n1);
        final double c8 = Double.parseDouble(n2);
        final double c9 = Double.parseDouble(n3);


        System.out.println("the double values:"+c1+c4+c7);

//-------------------------------------------------------------------------------------------------------
        String v4=pref.getString("username", null); // getting String
        System.out.println("the username in table acitivity is:"+v4);

        t1.setText(i1);
        t2.setText(i2);
        t3.setText(i3);

        t4.setText(s1);
        t5.setText(s2);
        t6.setText(s3);

        t7.setText(n1);
        t8.setText(n2);
        t9.setText(n3);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                editor.remove("username"); // will delete key name
                editor.remove("password"); // will delete key email
                editor.commit(); // commit changes
                finish();
            }
        });


        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //------------------------------------------------------
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                AsyncTaskRunner runner = new AsyncTaskRunner();

                String username=pref.getString("username", null); // getting String
                String password=pref.getString("password", null); // getting String
                runner.user = username;
                runner.pass = password;
                System.out.println("user:"+username+"password:"+password);
                runner.context = getApplicationContext();
                runner.execute();

                //-------------------------------------------------------
            }
        });

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //--------------------------------------------------------------------------
                double smallest = c1;
                if (smallest > c4) smallest = c4;
                if (smallest > c7) smallest = c7;

                double smallest1 = c2;
                if (smallest1 > c5) smallest1 = c5;
                if (smallest1 > c8) smallest1 = c8;


                double smallest2 = c3;
                if (smallest2 > c6) smallest2 = c6;
                if (smallest2 > c9) smallest2 = c9;


//---------------------------------------------------------------------------------------

                AlertDialog.Builder builder1 = new AlertDialog.Builder(tableActivity.this);
                if(smallest==c1){
                    builder1.setMessage("Company with lowest price for UNLEADED Fuel is : 'IPC USA'");
                }
                if(smallest==c4){
                    builder1.setMessage("Company with lowest price for UNLEADED Fuel is : 'SC FUELS'");
                }
                if(smallest==c7){
                    builder1.setMessage("Company with lowest price for UNLEADED Fuel is : 'NATIONAL'");
                }

                //builder1.setMessage("Company with lowest price for UNLEADED Fuel is : IPC USA");
                builder1.setCancelable(true);
                builder1.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

//-----------------------------------------------------------------------------------------------
                AlertDialog.Builder builder2 = new AlertDialog.Builder(tableActivity.this);
                if(smallest1==c2){
                    builder2.setMessage("Company with lowest price for PREMIUM Fuel is : 'IPC USA'");
                }
                if(smallest1==c5){
                    builder2.setMessage("Company with lowest price for PREMIUM Fuel is : 'SC FUELS'");
                }
                if(smallest1==c8){
                    builder2.setMessage("Company with lowest price for PREMIUM Fuel is : 'NATIONAL'");
                }

//                builder2.setMessage("Company with lowest price for PREMIUM Fuel is : IPC USA");
                builder2.setCancelable(true);
                builder2.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert12 = builder2.create();
                alert12.show();

//-----------------------------------------------------------------------------------------------

                AlertDialog.Builder builder3 = new AlertDialog.Builder(tableActivity.this);
                if(smallest2==c3){
                    builder3.setMessage("Company with lowest price for DIESEL Fuel is : 'IPC USA'");
                }
                if(smallest2==c6){
                    builder3.setMessage("Company with lowest price for DIESEL Fuel is : 'SC FUELS'");
                }
                if(smallest2==c9){
                    builder3.setMessage("Company with lowest price for DIESEL Fuel is : 'NATIONAL'");
                }
               // builder3.setMessage("Company with lowest price for DIESEL Fuel is : IPC USA");
                builder3.setCancelable(true);
                builder3.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert13 = builder3.create();
                alert13.show();


//-----------------------------------------------------------------------------------------------
            }
        });
        //--------------------------------------------------------------
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder4 = new AlertDialog.Builder(tableActivity.this);
                //if()
                builder4.setMessage("This Feature is included in Future Implementation ");
                builder4.setCancelable(true);
                builder4.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert1 = builder4.create();
                alert1.show();
            }
        });


        //--------------------------------------------------------------
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_table, menu);
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

}
