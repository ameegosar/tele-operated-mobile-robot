/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javaapplication26.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 *
 * 
 */
public class WinBotClienApplet extends Applet implements ActionListener {

    Button fbutton;
    Button bbutton;
    Button abutton;
    Button cbutton;
    Button sbutton;
    Button connect;
   // Button clearmap;
    TextField _batVal;
    //String temp;
    //Font bigfont;
   
    
    public boolean isThreadToRun;
    boolean isForcedThreadStop;
    boolean isSocketConnected;
    Vector<Globals.RoboCommand> commandQ;
    OutputStream output;
    InputStream input/*,inputvideo*/;
    Thread commandThread, videoThread;
    
    
    Socket commandSocket;
    //double fvel, bvel, arotate, crotate;
    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    @Override
    public void init() {
        // TODO start asynchronous download of heavy resources
        // Now we will use the FlowLayout
                GUILayout();
				
		isThreadToRun = false;
                isForcedThreadStop = false;
                isSocketConnected = false;
                commandQ = new Vector();
                commandThread = null;
                commandSocket = null;
                videoThread = null;
               
                //fvel = bvel = arotate = crotate = 0.0;
    }
    
    public void GUILayout()
    {
       setLayout(null);
        this.setBackground(new Color(225,225,225));
        //bigfont = new Font("Arial",Font.BOLD,14);
        fbutton = new Button("Move Forward");
        fbutton.setBounds(315, 310, 150, 30);
        fbutton.setBackground(Color.yellow);
        fbutton.setForeground(Color.red);
        abutton = new Button("Rotate Anticlockwise");
       abutton.setBounds(160, 345, 150, 30);
       sbutton = new Button("Stop");
       sbutton.setBounds(315, 345, 150, 30);
       cbutton = new Button("Rotate Clockwise");
       cbutton.setBounds(470, 345, 150, 30);
       bbutton = new Button("Move Backward");
       bbutton.setBounds(315, 380, 150, 30);
        connect = new Button("Connect RoboServer");
        connect.setBounds(230, 250, 320, 35);
        _batVal = new TextField("RoboServer is not connected");
        _batVal.setEditable(false);
        _batVal.setBounds(270, 288, 240, 20);
        setEnableControls(false);
        add(fbutton);
        add(abutton);
        add(sbutton);
        add(cbutton);
        add(bbutton);
        add(connect);
        add(_batVal);
       fbutton.addActionListener(this);
        abutton.addActionListener(this);
        sbutton.addActionListener(this);
        cbutton.addActionListener(this);
        bbutton.addActionListener(this);
        connect.addActionListener(this);
        //temp= null;
        
       
    }
    public void setEnableControls(boolean _status)
    {
       fbutton.setEnabled(_status);
       bbutton.setEnabled(_status);
       abutton.setEnabled(_status);
       sbutton.setEnabled(_status);
       cbutton.setEnabled(_status);

    }
     /*public void temp(String a)
     {
         //temp = a;
         _batVal.setText(a);
         repaint();
     }*/
    @Override
    public void paint(Graphics g)
	{
            if(isForcedThreadStop == true)
                {
                    DisconnectHost();
                    isForcedThreadStop = false;
                }
            if(isSocketConnected == false)
            {
                //g.setColor(new Color(175,175,175));
                g.setColor(Color.black);
                g.fillRect(230, 5, 320, 240);
            }
		g.setColor(Color.pink);
                g.drawRect(0, 0, getWidth()-1, getHeight()-1);
                //g.setFont(bigfont);
                //g.drawString(temp, 475, 410);
                
	}//public void paint(Graphics g)
    
    @Override
    public void stop()
    {
       DisconnectHost(); 
    }
    
    public void ConnectHost()
        {
            if(connect.getLabel().equals(/*new String(*/"Connect RoboServer"/*)*/) == true)
            {
                try{
        
                    InetAddress host = InetAddress.getByName("31.28.1.7");
                    int port = 50000;
                    
                   commandSocket = new Socket(host,port) ;
                    output = commandSocket.getOutputStream();
                     input = commandSocket.getInputStream();
                     connect.setLabel("Disconnect RoboServer");
                     //temp("Server is connected!");
                     _batVal.setText("Server is connected");
                      isSocketConnected = true;                   
                    }
                catch(Exception e)
                    {
                        DisconnectHost();
                        //temp("Error in connecting server");
                        _batVal.setText("Error in connecting server");
                    }//catch block
                if(isSocketConnected == true )
                {
                    try{
                    commandThread = new Thread(new CommandSendThread(this,output,commandQ));
                    videoThread = new Thread(new VideoTransmissionThread(this,input));
                    isThreadToRun = true;
                    commandThread.start();
                    videoThread.start();
                    setEnableControls(true);
                    }//try block
                    catch(Exception e)
                    {
                    DisconnectHost();
                    //temp("Error in inotoating some processes!");
                    _batVal.setText("Error in inotoating some processes!");
                    }
                }// if(isSocketConnected == true)
            }//if
            else
            {
                DisconnectHost();
                repaint();
            }
        }//public void connectHost()
    
    public void DisconnectHost()
    {
            isSocketConnected = false;
            isThreadToRun = false;
            try
            {   
                if(commandSocket != null)
                {
                commandSocket.shutdownOutput();
                commandSocket.shutdownInput(); 
                }
                
               
                if( commandThread != null)
                    while(commandThread.isAlive()){}
               
                if(videoThread != null)
                    while(videoThread.isAlive()){}
                
                if(commandSocket != null)
                    commandSocket.close();
                
               
               
                commandThread = null;
                
                videoThread = null;
               commandSocket = null;
               connect.setLabel("Connect RoboServer");
                _batVal.setText("Server is disconnected!");
               
            }
            catch(IOException e)
            {
               // temp("Error in disconnecting Server!");
                _batVal.setText("Error in disconnecting Server!");
            }
    }//public void DisconnectHost()
    
    public void StopThreads()
    {
        isForcedThreadStop = true;
        repaint();
    }
    
    /*public void BatteryLevel(double batval)
    {
        String x = String.valueOf(batval);
        _batVal.setText("  Value received:" + x);
        //repaint();
    }*/
     
    @Override
    public void actionPerformed(ActionEvent evt) 
	{
            // Actions of the connectButton
		if (evt.getSource() == connect) 
		{
                    ConnectHost();
                    repaint();               
		}
        	// Actions of the commandSendButton
		
                else if (evt.getSource() == fbutton) 
                {
                    if(isSocketConnected && isThreadToRun)
                    {
                        Globals.RoboCommand rc = new Globals.RoboCommand();
                        /*bvel = arotate = crotate = 0.0;
                        fvel = fvel + 0.1;
                        if(fvel < 0.1) fvel = 0.1;
                        if(fvel > 1.0) fvel = 1.0;*/
                        rc._command = Globals.MOVE_FORWARD;
                        rc._p1 = 0.0; //fvel;
                        commandQ.add(rc);
                    }
                }
                else if (evt.getSource() == bbutton) 
                {
                    if(isSocketConnected && isThreadToRun)
                    {
                        Globals.RoboCommand rc = new Globals.RoboCommand();
                        /*fvel = arotate = crotate = 0.0;
                        bvel = bvel - 0.05;
                        if(bvel < -0.4) bvel = -0.4;
                        if(bvel > -0.1) bvel = -0.1;*/
                        rc._command = Globals.MOVE_BACKWARD;
                        rc._p1 = 0.0; //bvel;
                        commandQ.add(rc);
                    }
                }
                else if (evt.getSource() == sbutton) 
                {
                    if(isSocketConnected && isThreadToRun)
                    {
                        Globals.RoboCommand rc = new Globals.RoboCommand();
                        //fvel = bvel = arotate = crotate = 0.0;
                        rc._command = Globals.STOP_MOTION;
                        rc._p1 = rc._p2 = 0.0;
                        commandQ.add(rc);
                    }
                }
                else if (evt.getSource() == abutton) 
                {
                    if(isSocketConnected && isThreadToRun)
                    {
                        Globals.RoboCommand rc = new Globals.RoboCommand();
                        /*bvel = fvel = crotate = 0.0;
                        arotate = arotate + Globals._PI/18.0;
                        if(arotate < (Globals._PI/9.0)) arotate = Globals._PI/9.0;
                        if(arotate > (Globals._PI/2.0)) arotate = Globals._PI/2.0;*/
                        rc._command = Globals.ROTATE_ANTICLOCKWISE;
                        rc._p1 = 0.0; //arotate;
                        commandQ.add(rc);
                    }
                }
                else if (evt.getSource() == cbutton) 
                {
                    if(isSocketConnected && isThreadToRun)
                    {
                        Globals.RoboCommand rc = new Globals.RoboCommand();
                        /*bvel = fvel = arotate = 0.0;
                        crotate = crotate - Globals._PI/18.0;
                        if(crotate < -(Globals._PI/2.0)) crotate = -Globals._PI/2.0;
                        if(crotate > -(Globals._PI/9.0)) crotate = -Globals._PI/9.0;*/
                        rc._command = Globals.ROTATE_CLOCKWISE;
                        rc._p1 = 0.0; //crotate;
                        commandQ.add(rc);
                    }
                }
	}//public void actionPerformed(ActionEvent evt) 

    // TODO overwrite start(), stop() and destroy() methods
}

class CommandSendThread implements Runnable
{
    WinBotClienApplet applet;
    Vector<Globals.RoboCommand> commandQ;
    OutputStream output;
    Globals.RoboCommand rc;    
    Globals.RoboCommand dummy;
    public CommandSendThread(WinBotClienApplet applet, OutputStream output, Vector<Globals.RoboCommand> commandQ)
    {
        this.applet = applet;
        this.output = output;
        this.commandQ = commandQ;
        dummy = new Globals.RoboCommand();
        dummy._command = Globals.DUMMY_COMMAND;
    }
    
    @Override
    public void run()
    {
        byte [] sent = new byte[56];
        
        int mycount = 0;
        while(applet.isThreadToRun == true)
        {
            
                if(commandQ.isEmpty()==false)
                {
                    rc = commandQ.remove(0);
                    mycount = 0;
                }
                else
                    ++mycount;
                try
                {
                    if(mycount == 0 || mycount == 15)
                    {
                        if(mycount == 0)
                            Globals.RoboCommandtoBArray(rc, sent);
                        else
                            Globals.RoboCommandtoBArray(dummy, sent);
                        output.write(sent, 0, 56);
                        mycount = 0;
                    }
                 }//try
                 catch(IOException e)
                 {
                    applet.isThreadToRun = false;
                    applet.StopThreads();
                 }
                
            try
            {
                Thread.sleep(100);
            }
            catch(InterruptedException e)
            {
                
            }
        }//while(applet.isThreadToRun == true)
    }//public void run()
}
    
class VideoTransmissionThread implements Runnable
{
    WinBotClienApplet applet;
    InputStream input;
    BufferedImage vidimg;
    
    

public VideoTransmissionThread (WinBotClienApplet applet,InputStream input)
{
    this.applet = applet;
    this.input = input;
}
        public void Display()
        {
            Graphics g = applet.getGraphics();
            g.drawImage(vidimg, 230, 5, 320, 240, null);
        }
        
@Override
    public void run()
    {
        byte [] headbyte = new byte[8];
        byte [] vidbyte = new byte[16384];
        int [] header = new int[2];
    
        while(applet.isThreadToRun == true)
            { 
            try
                {    
                Globals.ReadData(input,headbyte,8);
                Globals.BArraytoHeader(headbyte, header);
                if(header[0] == 200)
                    {
                    Globals.ReadData(input,vidbyte,header[1]);
                    vidimg = ImageIO.read(new ByteArrayInputStream(vidbyte));
                    Display();
                    }
                }
   
    
        catch(IOException e)
            {
                applet.isThreadToRun = false;
                applet.StopThreads();
            }
          catch(Exception ex)
            {
                applet.isThreadToRun = false;
                applet.StopThreads();
            }
         }
    }
}
    