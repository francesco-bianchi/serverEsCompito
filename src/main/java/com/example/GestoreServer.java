package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class GestoreServer extends Thread{
    Socket socket;
    Username user;
    public GestoreServer(Socket socket, Username user) {
        this.socket = socket;
        this.user = user;
    }

    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            boolean presente = false;
            boolean trovatoCanc = false;
            int pos=0;
            String fraseRic="";
            String username ="";
            ArrayList arrNote = new ArrayList<String>();

            do {
                username = in.readLine();
                presente = user.addUsername(username);
                if(presente){
                    out.writeBytes("n" + "\n");
                }
                else{
                    out.writeBytes("s" +"\n");
                }
                
            } while (presente);
            

            do {
                
                fraseRic = in.readLine();
                if(fraseRic.equals("!")){
                    System.out.println("Client disconnesso");
                }
                else if(fraseRic.equals("?")){
                    if(arrNote.isEmpty()){
                        out.writeBytes("Non sono state inserite note" + "\n");
                    }
                    else{
                        for(int i=0; i<arrNote.size(); i++){
                            out.writeBytes(arrNote.get(i) + "\n");
                        }
                        out.writeBytes("@" + "\n");
                    }
                }
                else if(fraseRic.equals("$")){
                    if(arrNote.isEmpty()){
                        out.writeBytes("Non sono state inserite note" + "\n");
                    }
                    else{
                        for(int i=0; i<arrNote.size(); i++){
                            out.writeBytes(arrNote.get(i) + "\n");
                        }
                        out.writeBytes("@" + "\n");

                        String strCancella =  in.readLine();
                        for(int i=0; i<arrNote.size(); i++){
                            if(strCancella.equals(arrNote.get(i))){
                                trovatoCanc = true;
                                pos = i;
                            }
                        }
                        if(!trovatoCanc){
                            out.writeBytes("n" + "\n");
                        }
                        else{
                            arrNote.remove(pos);
                            out.writeBytes("s" + "\n");
                        }


                    }
                }
                else{
                    arrNote.add(fraseRic);
                    out.writeBytes("OK" + "\n");
                }
            } while (!fraseRic.equals("!"));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
