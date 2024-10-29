package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class GestoreServer extends Thread{
    Socket socket;
    ArrayList user;
    ArrayList <String> arrCond;
    public GestoreServer(Socket socket, ArrayList <String> user, ArrayList <String> arrCond) {
        this.socket = socket;
        this.user = user;
        this.arrCond = arrCond;
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
                presente = false;
                username = in.readLine();
                
                if(user.contains(username)){
                    out.writeBytes("n" + "\n");
                    presente = true;
                }
                else{
                    user.add(username);
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
                        out.writeBytes("@" + "\n");
                    }
                    else{
                        for(int i=0; i<arrNote.size(); i++){
                            out.writeBytes(arrNote.get(i) + "\n");
                        }
                        out.writeBytes("@" + "\n");
                    }
                }
                else if(fraseRic.equals("&")){
                    if(arrCond.isEmpty()){
                        out.writeBytes("@" + "\n");
                    }
                    else{
                        for(int i=0; i<arrCond.size(); i++){
                            out.writeBytes(arrCond.get(i) + "\n");
                        }
                        out.writeBytes("@" + "\n");
                    }
                }
                else if(fraseRic.equals("$")){ //cancella
                    if(arrNote.isEmpty()){
                        out.writeBytes("@" + "\n");
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
                        if(trovatoCanc){
                            arrNote.remove(pos);
                            out.writeBytes("s" + "\n");
                        }
                        else{
                            out.writeBytes("n" + "\n");
                        }
                        


                    }
                }
                else if(fraseRic.charAt(0) == '&'){
                    arrCond.add(username + ": " +fraseRic);
                    out.writeBytes("OK" + "\n");
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
