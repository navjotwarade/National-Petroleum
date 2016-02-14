package com.example.navjot.teamproject272;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 * Created by Navjot on 5/9/2015.
 */
public class AsyncTaskRunner extends AsyncTask<String, String, String>
{

    public String user;
    public String pass;
    public Context context;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("user:"+user+"password:"+pass);
    }

    protected String doInBackground(String...params){

        //------------------------------------------------------------------------------------
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        //------------------------------------------------------------------------------------

        //String user=params[0];
        //String pass=params[1];
        String c1="nirmit09@gmail.com";
        String c2="nirmit.patel@sjsu.edu";
        String c3="nirmit@petronational.com";
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

            if(user.contains("gmail.com")) {
                System.out.println("inside imap gmail");
                imapStore.connect("imap.gmail.com",user, pass);
            }
            if(user.contains("yahoo.com")){
                System.out.println("inside imap yahoo");
                imapStore.connect("imap.mail.yahoo.com",user, pass);
            }
            System.out.println("Below imap protocol");

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
      //--------------------------------------------------------------------------------------
            editor.putString("from",from );
            editor.commit();        // commit changes
      //----------------------------------------------------------------------------------------
            String frm="";
            if(from.contains(c1)||from.contains(c2)|| from.contains(c3))
            {

              System.out.println("in side of if condition");
            }else{
                int i=folder.getMessageCount();

                Message mn=folder.getMessage(folder.getMessageCount());
                for(int j=1;j<10;j++) {

                    Message msgnth = folder.getMessage(i - j);
                    mn=msgnth;
                    Address[] n = mn.getFrom();
                    //String frm="";
                    for (Address address : n) {
                         frm = address.toString();
                        System.out.println("FROM:" + address.toString());
                       }
                    if (frm.contains(c1) || frm.contains(c2) || frm.contains(c3)) {
                        int az = 0;

                    } else {
                        continue;
                    };
                    break;



                }

                msg=mn;
            }
            //---------------------------------------------------------------------------------
            //BodyPart bp = mp.getBodyPart(0);
            System.out.println("SENT DATE:" + msg.getSentDate());
            System.out.println("SUBJECT:" + msg.getSubject());
            System.out.println(msg.getContent().toString());

            if(frm.contains(c1)||frm.contains(c2)|| frm.contains(c3)||from.contains(c1)||from.contains(c2)|| from.contains(c3)){
            Multipart mp = (Multipart) msg.getContent();
            BodyPart bp = mp.getBodyPart(0);
            System.out.println("CONTENT:" + bp.getContent());
            System.out.println(from+"="+c1);
            //---------------------------------------------------------------------------------------
            // if(from.contains(c1)||from.contains(c2)|| from.contains(c3)){
                 System.out.println("from fffffff"+c1);
            if(msg.isMimeType("multipart/*"))
            {	//It's a a multipart so go through the parts and find what looks like the                 attachments
                //  Multipart mp = (Multipart)msg.getContent();
                System.out.println("In multipart mime");
                int partCount = mp.getCount();
                Part curtPart;
                String curPartDisposition;
                //String filename;
                File file;
                String LogTag="find name";
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
                            Log.d(LogTag, "Saved the attachment " + i + " to the following filename [" + filename + "].");
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
              } else{
                 Handler handler =  new Handler(context.getMainLooper());
                 handler.post( new Runnable(){
                 public void run(){
                 Toast.makeText(context, "No new emails from the registered Suppliers", Toast.LENGTH_LONG).show();
                     Intent i = new Intent(context, tableActivity.class);
                     i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
                     context.startActivity(i);
                     }
                 });
                                                          //of the if statement from suppliers
               }    //else of if of suppliers




        }  catch(Exception m1){
            Handler handler =  new Handler(context.getMainLooper());
            handler.post( new Runnable(){
                public void run(){
                    Toast.makeText(context, "Please check your credentials and also make sure you are connected to network connection ", Toast.LENGTH_LONG).show();

                }
            });
            m1.printStackTrace();

        }




        return filename;
    }  //doInbackground

    @Override
    protected void onPostExecute(String filename) {
        //------------------------------------------------------------------------------------
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        //------------------------------------------------------------------------------------
        String from=pref.getString("from", null); // getting String

        String val1="";
        String val2="";
        String val3="";

        String c1="nirmit09@gmail.com";
        String c2="nirmit.patel@sjsu.edu";
        String c3="nirmit@petronational.com";

      if(from.contains(c1)||from.contains(c2)|| from.contains(c3)){
        try {

            //-----------------------------------------------------------------------------------

            //-----------------------------------------------------------------------------------
            System.out.println("file is: "+ filename);
            FileInputStream output = new FileInputStream(Environment.getExternalStorageDirectory() + "/" + filename);
            Scanner scnr = new Scanner(output);
            int lineNumber = 1;
            while(scnr.hasNextLine()){
                String line = scnr.nextLine();
                System.out.println("line " + lineNumber + " :" + line);
                Scanner s = new Scanner(line);
                if(s.findInLine(Pattern.compile(".UNL"))!=null){
                   //if(from==""){}
                    val1=String.valueOf(s.nextFloat());
                    System.out.println("val1:"+val1);
                }
                if(s.findInLine(Pattern.compile(".PNL"))!=null){
                    //if(from==""){}
                    val2=String.valueOf(s.nextFloat());
                    System.out.println("val2:"+val2);
                }
                if(s.findInLine(Pattern.compile(".DSL"))!=null){
                    //if(from==""){}
                    val3=String.valueOf(s.nextFloat());
                    System.out.println("val3:"+val3);
                }
                /**/

                lineNumber++;
                //---------------------------------------------------------------------
                          if(from.contains(c1)){
                              System.out.println("in c1 from contains");
                            //  if(val1==" ")

                editor.putString("i1",val1 );
                editor.putString("i2",val2);
                editor.putString("i3",val3);
                editor.commit();        // commit changes

                             }else if(from.contains(c2)){
                              System.out.println("in c2 from contains");
                editor.putString("s1",val1 );
                editor.putString("s2",val2);
                editor.putString("s3",val3);
                editor.commit();        // commit changes

                             }else if(from.contains(c3)){
                              System.out.println("in c3 from contains");

                editor.putString("n1",val1 );
                editor.putString("n2",val2);
                editor.putString("n3",val3);
                editor.commit();        // commit changes


                             }


                Intent i = new Intent(context, tableActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                //finish();
                //---------------------------------------------------------------------

            }
            if(val1==null||val1=="")
            {
                Handler handler =  new Handler(context.getMainLooper());
                handler.post( new Runnable(){
                    public void run(){
                        Toast.makeText(context, "The attachment does not contain any fuel prices", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(context, tableActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                });
            }

        }catch(Exception e){
            e.printStackTrace();
        } //try 1

          }            //check if emails are from registered suppliers
        System.out.println("all values" + val1 + val2 + val3); // accesible

    }//on postexecute


}