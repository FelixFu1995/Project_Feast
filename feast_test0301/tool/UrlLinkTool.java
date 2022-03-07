package tw.com.feast_test0301.tool;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UrlLinkTool extends Thread {
    private String url = null;

    public NetEventListener OnServerAnswerListener;

    public UrlLinkTool(String url){
        this.url =url;
    }

    //Listener_Method
    public interface NetEventListener {
        public void linkStart();
        public void linkEnd(byte[] dataByte);
    }

    /**
     * Implement
     */
    @Override
    public void run() {
        OnServerAnswerListener.linkStart();
        urlLink(url);
        return;
    }

    /**
     * Connect httpURL to get_httpURL_content
     * @param url URL
     */
    public void urlLink(String url){
        HttpURLConnection conn = null;
        InputStream is = null;
        URL Server;
        //url = "http://10.0.2.2:8080/ProjectDemo/DemoTest?"+url;
        System.out.println(url);
        try {
            Server = new URL(url);
            conn = (HttpURLConnection) Server.openConnection();
            conn.connect();
            is = conn.getInputStream();

            byte[] inDateChar = new byte[1024];
            ArrayList<Byte> pool = new ArrayList<Byte>();
            int loadSize=is.read(inDateChar);
            while (loadSize!=-1){
                for (int i=0;i<loadSize;i++){
                    pool.add(inDateChar[i]);
                }
                loadSize = is.read(inDateChar);
            }
            byte[] allDate =new byte[pool.size()];
            for(int i=0;i<pool.size();i++){
                allDate[i]= pool.get(i);
            }
            OnServerAnswerListener.linkEnd(allDate);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return;
    }
}
