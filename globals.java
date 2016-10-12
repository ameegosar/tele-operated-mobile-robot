/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication26;


import java.io.InputStream;


public class Globals {
    
    public static final long NO_COMMAND = 1;
    public static final long SETENCODERSPOS = 800;
    public static final long ROBODRIVE = 900;
    public static final long SETLKpKdKi = 100;
    public static final long SETRKpKdKi = 200;
    public static final long EMERGENCYSTOP = 1100;
    public static final long SETROBOPOSE = 1200;
    public static final long DUMMY_COMMAND = 1500; 
    public static final long MOVE_FORWARD = 1111;
    public static final long MOVE_BACKWARD = -1111;
    public static final long ROTATE_CLOCKWISE = 2222;
    public static final long ROTATE_ANTICLOCKWISE = -2222;
    public static final long STOP_MOTION = 3333;
    public static final long OBSTACLE_AVOID_ON = 4444;
    public static final long OBSTACLE_AVOID_OFF = -4444;
    public static final long IG_POINT = 10000;
    public static final long LOCALIZE_ON = 6666;
    public static final long LOCALIZE_OFF = -6666;
    public static final long CLEAR_Q_IGPOINT = 7777;
    public static final long NEW_IGPOINTS = 8888;
    public static final long PLAN_PATH_ON = 9999;
    public static final long PLAN_PATH_OFF = -9999;
    public static final long SLAM_ON = 5050;
    public static final long SLAM_OFF = -5050;
    public static final long CLEAR_MAP = 2233;
    public static final long END_APPL = 9090;
    public static final double _PI = 3.1415927;
    
    public static void DoubletoBArray(double d, byte[] barray) {
     
        if(barray != null && barray.length>=8)
        {
            long l = Double.doubleToLongBits(d);
            barray[0] = (byte)(l/*(l >> 0)*/ & 0xff);
            barray[1] = (byte)((l >> 8) & 0xff);
            barray[2] =(byte)((l >> 16) & 0xff);
            barray[3] =(byte)((l >> 24) & 0xff);
            barray[4] =(byte)((l >> 32) & 0xff);
            barray[5] =(byte)((l >> 40) & 0xff);
            barray[6] =(byte)((l >> 48) & 0xff);
            barray[7] =(byte)((l >> 56) & 0xff);
        }
   }
        // TODO code application logic here
    public static double BArraytoDouble(byte[] tmp)
    {
	long accum = 0;
        if(tmp != null && tmp.length>=8)
        {
            for ( int i = 0; i < 8; i++ ) {
                accum |= (((long)tmp[i] & 0xff)  << (8*i));
            }
        }

        return Double.longBitsToDouble(accum);
    }
    
    public static void LongtoBArray(long l, byte[] barray) {
     
        if(barray != null && barray.length>=8)
        {
            barray[0] = (byte)(l/*(l >> 0)*/ & 0xff);
            barray[1] = (byte)((l >> 8) & 0xff);
            barray[2] =(byte)((l >> 16) & 0xff);
            barray[3] =(byte)((l >> 24) & 0xff);
            barray[4] =(byte)((l >> 32) & 0xff);
            barray[5] =(byte)((l >> 40) & 0xff);
            barray[6] =(byte)((l >> 48) & 0xff);
            barray[7] =(byte)((l >> 56) & 0xff);
        }
   }
        // TODO code application logic here
    public static long BArraytoLong(byte[] tmp)
    {
	long accum = 0;
        if(tmp != null && tmp.length>=8)
        {
            for ( int i = 0; i < 8; i++ ) {
                accum |= (((long)tmp[i] & 0xff)  << (8*i));
            }
        }

        return accum;
    }
    
    public static void InttoBArray(int l, byte[] barray) {
     
        if(barray != null && barray.length>=4)
        {
            barray[0] = (byte)(l & 0xff);
            barray[1] = (byte)((l >> 8) & 0xff);
            barray[2] =(byte)((l >> 16) & 0xff);
            barray[3] =(byte)((l >> 24) & 0xff);
        }
   }
        // TODO code application logic here
    public static int BArraytoInt(byte[] tmp)
    {
	int accum = 0;
        if(tmp != null && tmp.length>=4)
        {
            for ( int i = 0; i < 4; i++ ) {
                accum |= (((int)tmp[i] & 0xff)  << (8*i));
            }
        }

        return accum;
    }
    
    public static class RoboCommand
    {
        public long _command = 0;
        public double _p1 =0.0, _p2 = 0.0, _p3 = 0.0;
        public double _p4 =0.0, _p5 = 0.0, _p6 = 0.0;
    }
    
   /* public static class RoboState
    {
        public double _leftEncoder = 0.0, _rightEncoder = 0.0;
        public double _roboX = 0.0, _roboY = 0.0, _roboTht = 0.0;
        public double _encCountPerMeter = 0.0, _wheelbase = 0.0;
        public double _roboradious = 0.0;
        public double _transVel = 0.0, _rotVel = 0.0;
        public double _batteryVoltage = 0.0;
    }
    
    public static class LaserInfo
    {
        public double startangle = 0.0, scanresolution = 0.0;
        public double posx = 0.0, posy = 0.0, postht = 0.0;
        public double dxx,dxy,dxt,dyy,dyt,dtt;
    }*/
    
    public static void RoboCommandtoBArray(Globals.RoboCommand rc, byte[] barray)
    {
        if(barray != null && barray.length>=56)
        {
            int index = 0,i;
            byte[] tarray = new byte[8];
            LongtoBArray(rc._command,tarray);
            for(i=0;i<8;i++,index++)
                barray[index] = tarray[i];
            DoubletoBArray(rc._p1,tarray);
            for(i=0;i<8;i++,index++)
                barray[index] = tarray[i];
            DoubletoBArray(rc._p2,tarray);
            for(i=0;i<8;i++,index++)
                barray[index] = tarray[i];
            DoubletoBArray(rc._p3,tarray);
            for(i=0;i<8;i++,index++)
                barray[index] = tarray[i];
            DoubletoBArray(rc._p4,tarray);
            for(i=0;i<8;i++,index++)
                barray[index] = tarray[i];
            DoubletoBArray(rc._p5,tarray);
            for(i=0;i<8;i++,index++)
                barray[index] = tarray[i];
            DoubletoBArray(rc._p6,tarray);
            for(i=0;i<8;i++,index++)
                barray[index] = tarray[i];
        }
    }
    
    public static void BArraytoHeader(byte[] barray, int[] header)
    {
        if(barray != null && header != null && barray.length>=8 && header.length>=2)
        {
           int index = 0,i;
           byte[] tarray = new byte[4];
           for(i=0;i<4;i++,index++)
               tarray[i] = barray[index];
           header[0] = BArraytoInt(tarray);
           for(i=0;i<4;i++,index++)
               tarray[i] = barray[index];
           header[1] = BArraytoInt(tarray); 
        }
    }
    
    /*public static void BArraytoRoboState(byte[] barray, Globals.RoboState rs)
    {
        if(barray != null && barray.length >= 88)
        {
            int index = 0,i;
            byte[] tarray = new byte[8];
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            rs._leftEncoder = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            rs._rightEncoder = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            rs._roboX = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            rs._roboY = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            rs._roboTht = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            rs._encCountPerMeter = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            rs._wheelbase = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            rs._roboradious = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            rs._transVel = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            rs._rotVel = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            rs._batteryVoltage = BArraytoDouble(tarray);
        }
    }
    
     public static void BArraytoLaserInfo(byte[] barray, Globals.LaserInfo li)
    {
        if(barray != null && barray.length >= 88)
        {
            int index = 0,i;
            byte[] tarray = new byte[8];
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            li.startangle = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            li.scanresolution = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            li.posx = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            li.posy = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            li.postht = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            li.dxx = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            li.dxy = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            li.dxt = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            li.dyy = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            li.dyt = BArraytoDouble(tarray);
            for(i=0;i<8;i++,index++)
                tarray[i] = barray[index];
            li.dtt = BArraytoDouble(tarray);
        }
    }*/
     public static void ReadData(InputStream inputvideo, byte[] buff, int len)throws Exception
    {
        int offset = 0,n;
               
            do
            {
                
                n = inputvideo.read(buff,offset,len);
                len = len - n;
                offset += n;  
                if(n == -1)
                    throw new Exception();
            }while(len>0);
         
    }
    
}
