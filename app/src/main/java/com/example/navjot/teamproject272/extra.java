package com.example.navjot.teamproject272;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import android.content.Intent;
//import com.sun.mail.util.MailSSLSocketFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
//import javax.sql.DataSource;


public class extra extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);
     System.out.print("after content view");

        final EditText user1 = (EditText) findViewById(R.id.username);
        final EditText pass = (EditText) findViewById(R.id.password);

        Button button = (Button) findViewById(R.id.enterbutton);
        Button aboutButton = (Button) findViewById(R.id.button_about);
        Button buttonRegister = (Button) findViewById(R.id.button5);

       /* SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
          SharedPreferences.Editor editor = pref.edit();

        editor.putString("username", "string value");
        editor.putString("password", "string value");
        editor.commit();        // commit changes

        pref.getString("username", null); // getting String
        pref.getString("password", null); // getting String

        editor.remove("name"); // will delete key name
        editor.remove("email"); // will delete key email
        editor.commit(); // commit changes
        */

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //----------------------------------------------------
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                //----------------------------------------------------
                AsyncTaskRunner runner = new AsyncTaskRunner();

                String password=pass.getText().toString();
                String username=user1.getText().toString();
                //------------------------------------------------------
                editor.putString("username", username);
                editor.putString("password", password);
                editor.commit();

                //------------------------------------------------------
                runner.user = username;
                runner.pass = password;
                System.out.println("user:"+username+"password:"+password);
                runner.context = getApplicationContext();
                runner.execute();

                //-------------------------------------------------------
/*

                    */
                // -----------------------------------------------
                String host = "pop.gmail.com";
                String port = "995";
                String userName = "navjotwarade@gmail.com";
                String password1 = "navjot0724";

                String saveDirectory = "D:/Attachment";


                //  receiver.execute();
                //------------------------------------------------

            } //onclick
        }); //button listener

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), about.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(i);
            }
        });

//------------------------------------------------------------------------------
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), register.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(i);
            }
        });


        //----------------------------------------------------------------------

    }    //oncreate
    //------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_extra, menu);
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

    //------------------------------------------------------------
   /* private class AsyncTaskRunner extends AsyncTask<String, String, String>
    {

        private String user;
        private String pass;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("user:"+user+"password:"+pass);
        }

        protected String doInBackground(String...params){

            //String user=params[0];
            //String pass=params[1];

            String filename="";

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            class GMailAuthenticator extends Authenticator {
                String user;
                String pw;
                public GMailAuthenticator (String username, String password)
                {
                    super();
                    this.user = username;
                    this.pw =password;
                }
                public PasswordAuthentication getPasswordAuthentication()
                {
                    // char[] password = pw.toCharArray();
                    return new PasswordAuthentication(user,pw);
                }
            }   //Gmailauthenticator
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imaps");

            Session session = Session.getInstance(props, new GMailAuthenticator(user, pass));
            IMAPStore imapStore = null;

            try {
                imapStore = (IMAPStore) session.getStore("imaps");
                /// Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                System.out.println("user:"+user+"password:"+pass);

                imapStore.connect("imap.gmail.com", user, pass);

                final IMAPFolder folder = (IMAPFolder) imapStore.getFolder("Inbox");
                folder.open(IMAPFolder.READ_WRITE);
                Message msg = folder.getMessage(folder.getMessageCount());
                Address[] in = msg.getFrom();
                String from="";
                for (Address address : in) {
                    System.out.println("FROM:" + address.toString());
                    from=address.toString();
                }
                System.out.println("BELOW FROM:" + from);
                //if(from=)
                //BodyPart bp = mp.getBodyPart(0);
                System.out.println("SENT DATE:" + msg.getSentDate());
                System.out.println("SUBJECT:" + msg.getSubject());
                System.out.println(msg.getContent().toString());
                Multipart mp = (Multipart) msg.getContent();
                BodyPart bp = mp.getBodyPart(0);
                System.out.println("CONTENT:" + bp.getContent());
                //---------------------------------------------------------------------------------------
                //  String folder1 = getAttachmentFolderForMessage(getMessageUID(msg));
                if(msg.isMimeType("multipart*//*"))
                {	//It's a a multipart so go through the parts and find what looks like the                 attachments
                    //  Multipart mp = (Multipart)msg.getContent();
                    int partCount = mp.getCount();
                    Part curtPart;
                    String curPartDisposition;
                    //String filename;
                    File file;
                    String LogTag="hi hw r u";
                    String saveDirectory = "D:/Attachment";
                    for (int i = 0; i < partCount; i++)
                    {
                        curtPart = mp.getBodyPart(1);
                        curPartDisposition = curtPart.getDisposition();
                        // many mailers don't include a Content-Disposition
                        if (curPartDisposition == null || curPartDisposition.equalsIgnoreCase
                                (Part.ATTACHMENT))
                        {
                            filename = curtPart.getFileName();
                            if (filename == null || filename.length() == 0)
                                filename = "Attachment" + i;
                            //filename = "" + filename;
                            try
                            {

                                //-------------------------------------

                                FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory()+"/"+filename);
                                // FileOutputStream output = new FileOutputStream(myFile,false);
                                InputStream input = curtPart.getInputStream();

                                byte[] buffer = new byte[4096];

                                int byteRead;

                                while ((byteRead = input.read(buffer)) != -1) {
                                    output.write(buffer, 0, byteRead);
                                }
                                output.close();

//-----------------------------------------------------------------
                                //((MimeBodyPart)curtPart).saveFile(filename);
                                Log.d(LogTag,"Saved the attachment "+i+" to the following filename ["+filename+"].");
                                //return filename;
                            }
                            catch (IOException ex)
                            {
                                Log.e(LogTag, "Caught an exception trying to save an attachment to the filename["+filename+"].",ex);
                                //throw new IMAPException("Unable to save the attachment to a file. "+ex.getMessage());
                            }
                            //return filename;
                        }
                    }

                }
                //---------------------------------------------------------------------------------------
                // Message m[] = folder.getMessages();
          *//*  FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message messages[]=folder.search(ft);
           // System.out.println(m.length);
            // int size=m.length;
            int i=0;
            for (Message n : messages) {
               System.out.println(n.getSubject());
                System.out.println("CONTENT:" + n.getFrom());
                            }
*//* //------------------------------------------------------------------------------------------------

            } catch (Exception e) {
                e.printStackTrace();
            }


            return filename;
        }  //doInbackground

        @Override
        protected void onPostExecute(String filename) {
            String val1="";
            String val2="";
            String val3="";
            try {
                System.out.println("file is: "+ filename);
                FileInputStream output = new FileInputStream(Environment.getExternalStorageDirectory() + "/" + filename);
                Scanner scnr = new Scanner(output);
                int lineNumber = 1;
                while(scnr.hasNextLine()){
                    String line = scnr.nextLine();
                    System.out.println("line " + lineNumber + " :" + line);
                    Scanner s = new Scanner(line);
                    if(s.findInLine(Pattern.compile(".UNL"))!=null){
                        val1=String.valueOf(s.nextFloat());
                        System.out.println("val1:"+val1);
                    }
                    if(s.findInLine(Pattern.compile(".PNL"))!=null){
                        val2=String.valueOf(s.nextFloat());
                        System.out.println("val2:"+val2);
                    }
                    if(s.findInLine(Pattern.compile(".DSL"))!=null){
                        val3=String.valueOf(s.nextFloat());
                        System.out.println("val3:"+val3);
                    }

                    lineNumber++;
                    //---------------------------------------------------------------------

                   *//* i.putExtra("var1",val1);
                    i.putExtra("var2",val2);
                    i.putExtra("var3",val3); *//*
                    //--------------------------------------------------------------------
                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("var1",val1 );
                    editor.putString("var2",val2);
                    editor.putString("var3",val3);
                    editor.commit();        // commit changes

                    //--------------------------------------------------------------------
                    Intent i = new Intent(getApplicationContext(), tableActivity.class);
                    startActivity(i);
                    finish();
                    //---------------------------------------------------------------------

                }

            }catch(Exception e){
                e.printStackTrace();
            } //try 1
         System.out.println("all values" + val1 + val2 + val3); // accesible

        }//on postexecute


    }*///Asynctask runner
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++




}   //extra activity


