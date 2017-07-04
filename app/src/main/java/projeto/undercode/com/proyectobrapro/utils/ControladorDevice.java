package projeto.undercode.com.proyectobrapro.utils;

import android.content.Context;
import android.location.Location;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

/**
 * Created by Level on 16/09/2016.
 */
public class ControladorDevice {
    private static ControladorGPS objControladorGPS = null;
    private static ControladorGPS2 objControladorGPS2 = null;
    private static ControladorDevice objControladorDevice = null;

    protected ControladorDevice(){
    }

    public synchronized static ControladorDevice getInstance() {
        if(objControladorDevice == null) {
            objControladorDevice = new ControladorDevice();
        }
        return objControladorDevice;
    }

    public String getPIM(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String spim=telephonyManager.getSimSerialNumber();
        if(spim==null){
            spim="Ausente";
        }
        return spim;
    }

    public String getIMEI(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String simei="";
        simei = telephonyManager.getDeviceId();
        if(simei==null){
            try {
                Class<?> c = Class.forName("android.os.SystemProperties");
                Method get = c.getMethod("get", String.class, String.class );
                simei = (String)(   get.invoke(c, "ro.serialno", "unknown" )  );
            }
            catch (Exception ignored)
            {
            }
            if(simei==null){
                simei="Ausente";
            }
        }
        return simei;
    }

    public void iniciarGPS1(Context context){
        if(objControladorGPS == null){
            objControladorGPS = ControladorGPS.getInstance(context);
        }
        objControladorGPS.iniciarGPS();
    }

    public void iniciarGPS2(Context context){
        if(objControladorGPS2 == null){
            objControladorGPS2 = ControladorGPS2.getInstance(context);
        }
        objControladorGPS2.iniciarGPS();
    }

    public Location getLocation1(){
        return ControladorGPS.getLocation();
    }

    public Location getLocation2(){
        return ControladorGPS2.getLocation();
    }

    public void finalizarGPS1(){
        if(objControladorGPS != null){
            objControladorGPS.finalizarGPS();
        }
    }

    public void finalizarGPS2(){
        if(objControladorGPS2 != null){
            objControladorGPS2.finalizarGPS();
        }
    }
}
