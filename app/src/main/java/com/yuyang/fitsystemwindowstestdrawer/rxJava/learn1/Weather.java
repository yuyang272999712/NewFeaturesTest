package com.yuyang.fitsystemwindowstestdrawer.rxJava.learn1;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;

/**
 * 天气类
 */
public class Weather {
    /**
     * 城市
     */
    String city;
    /**
     * 日期
     */
    String date;
    /**
     * 温度
     */
    String temperature;
    /**
     * 风向
     */
    String direction;
    /**
     * 风力
     */
    String power;
    /**
     * 天气状况
     */
    String status;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("城市:" + city + "\r\n");
        builder.append("日期:" + date + "\r\n");
        builder.append("天气状况:" + status + "\r\n");
        builder.append("温度:" + temperature + "\r\n");
        builder.append("风向:" + direction + "\r\n");
        builder.append("风力:" + power + "\r\n");
        return builder.toString();
    }

    /**
     * 解析xml获取天气情况
     * @param weatherXml
     * @return
     */
    public static Weather parseWeather(String weatherXml){
        //采用Pull方式解析xml
        StringReader reader = new StringReader(weatherXml);
        XmlPullParser xmlParser = Xml.newPullParser();
        Weather weather = null;
        try {
            xmlParser.setInput(reader);
            int eventType = xmlParser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        weather = new Weather();
                        break;
                    case XmlPullParser.START_TAG:
                        String nodeName = xmlParser.getName();
                        if("city".equals(nodeName)){
                            weather.city = xmlParser.nextText();
                        } else if("savedate_weather".equals(nodeName)){
                            weather.date = xmlParser.nextText();
                        } else if("temperature1".equals(nodeName)) {
                            weather.temperature = xmlParser.nextText();
                        } else if("temperature2".equals(nodeName)){
                            weather.temperature += "-" + xmlParser.nextText();
                        } else if("direction1".equals(nodeName)){
                            weather.direction = xmlParser.nextText();
                        } else if("power1".equals(nodeName)){
                            weather.power = xmlParser.nextText();
                        } else if("status1".equals(nodeName)){
                            weather.status = xmlParser.nextText();
                        }
                        break;
                }
                eventType = xmlParser.next();
            }
            return weather;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            reader.close();
        }
    }
}
