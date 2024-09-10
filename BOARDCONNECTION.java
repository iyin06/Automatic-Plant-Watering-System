
import org.firmata4j.firmata.*;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import java.io.IOException;
import org.firmata4j.ssd1306.SSD1306;
import org.firmata4j.I2CDevice;

import java.util.Timer;

public class BOARDCONNECTION {

    public static void main(String[] args)
            throws InterruptedException, IOException{
        String myPort = "/dev/cu.usbserial-0001";
        IODevice myGroveBoard = new FirmataDevice(myPort);

        try{
            myGroveBoard.start();
            myGroveBoard.ensureInitializationIsDone();

            I2CDevice i2CDevice = myGroveBoard.getI2CDevice((byte)0x3C);
            SSD1306 OLEDDISPLAY = new SSD1306(i2CDevice, SSD1306.Size.SSD1306_128_64);
            OLEDDISPLAY.init();

            var ArduinoPump = myGroveBoard.getPin(7);
            ArduinoPump.setMode(Pin.Mode.OUTPUT);

            var GroveSensor = myGroveBoard.getPin(15);
            GroveSensor.setMode(Pin.Mode.ANALOG);

            Timer timerTask = new Timer();

            var Taskformoisture = new MoistureSensor(OLEDDISPLAY,GroveSensor, ArduinoPump);

            timerTask.schedule(Taskformoisture,0,100);



        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

