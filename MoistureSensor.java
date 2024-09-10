
import edu.princeton.cs.introcs.StdDraw;
import org.firmata4j.Pin;
import org.firmata4j.ssd1306.SSD1306;

import java.util.HashMap;
import java.util.TimerTask;

public class MoistureSensor extends TimerTask {


   private  final SSD1306 OLEDDISPLAY;
    int m;
    int counter = 1;

     Pin GroveSensor;

     Pin ArduinoPump;

    MoistureSensor(SSD1306 OLEDDISPLAY, Pin GroveSensor, Pin ArduinoPump) {

        this.OLEDDISPLAY = OLEDDISPLAY;
        this.GroveSensor = GroveSensor;
        this.ArduinoPump = ArduinoPump;


    }
    @Override
    public void run(){
        HashMap<Integer,Integer> StoredValues = new HashMap<>();

        while(true){
            if (GroveSensor.getValue()>=700){

                try{
                    String currentmoisture = String.valueOf(GroveSensor.getValue());
                    OLEDDISPLAY.getCanvas().clear();
                    OLEDDISPLAY.getCanvas().setTextsize(1);
                    OLEDDISPLAY.getCanvas().drawString(0,0,currentmoisture+" "+"moisture is to low");
                    OLEDDISPLAY.display,,mmj();
                    ArduinoPump.setValue(1); //ON
                }catch (Exception ex){

                }
            }
            else if (650<= GroveSensor.getValue() && GroveSensor.getValue()<= 700){
                try{
                    String currentmoisture = String.valueOf(GroveSensor.getValue());
                    OLEDDISPLAY.getCanvas().clear();
                    OLEDDISPLAY.getCanvas().setTextsize(1);
                    OLEDDISPLAY.getCanvas().drawString(0,0,currentmoisture+ " "+ "moisture level not high enough");
                    OLEDDISPLAY.display();
                    ArduinoPump.setValue(1); //ON

                }catch (Exception ex){

                }
            }
            else {
                try{
                    String currentmoisture = String.valueOf(GroveSensor.getValue());
                    OLEDDISPLAY.getCanvas().clear();
                    OLEDDISPLAY.getCanvas().setTextsize(1);
                    OLEDDISPLAY.getCanvas().drawString(0,0,currentmoisture+ " "+ "moisture level good");
                    OLEDDISPLAY.display();
                    ArduinoPump.setValue(0); //OFF
                } catch (Exception ex){

                }
            }
            HashMap<Integer, Double> finalgraph = new HashMap<>();
         finalgraph.put(counter, (double) GroveSensor.getValue()*5/1023);

          StdDraw.setXscale(0,100);
          StdDraw.setYscale(0,5);

          StdDraw.setPenRadius(0.009);
          StdDraw.setPenColor(StdDraw.RED);

            StdDraw.line(0,0,0,5);
            StdDraw.line(0,0,80,0);
            StdDraw.text(-1.55,4.5,"[V]");
            StdDraw.text(30,-0.20,"Time[sec]");
            StdDraw.text(30,5,"Moisture  Vs. Time ");
            StdDraw.text(0,-0.20,"0");
            StdDraw.text(-1.55,0,"0");
            StdDraw.text(60,-0.20,"100");
            StdDraw.text(-1.55,5,"5");
            finalgraph.forEach((xValue,yValue) -> StdDraw.text(xValue,yValue,"*"));
          counter++;

        }
    }
}
