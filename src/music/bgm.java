package music;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URI;
import java.net.URL;

public class bgm extends Thread{
  private File f;
  private URI uri;
  private URL url;

  public void run(){
      try{
          f = new File("src/bgm.wav");
          uri = f.toURI();
          url =uri.toURL();
          AudioClip aau;
          aau = Applet.newAudioClip(url);
          aau.loop();
          System.out.println("music start");

      } catch (Exception e) {
          e.printStackTrace();
      }
  }
}
