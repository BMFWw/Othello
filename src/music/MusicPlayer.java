package music;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MusicPlayer {

  // wav文件的路径
  private File file;
  // 是否循环播放
  private boolean isLoop = false;
  // 是否正在播放
  private boolean isPlaying;

  private PlayThread playThread;

  public MusicPlayer(String srcPath) {
    file = new File(srcPath);
  }

  public void play() {
    playThread = new PlayThread();
    playThread.start();
  }

  public void over() {
    isPlaying = false;
    if (playThread != null) {
      playThread = null;
    }
  }

  public MusicPlayer setLoop(boolean isLoop) {
    this.isLoop = isLoop;
    return this;
  }

  /**
   * 异步播放线程
   */
  private class PlayThread extends Thread {

    @Override
    public void run() {
      isPlaying = true;
      do {

        SourceDataLine sourceDataLine = null;
        BufferedInputStream bufIn = null;
        AudioInputStream audioIn = null;
        try {
          bufIn = new BufferedInputStream(new FileInputStream(file));
          audioIn = AudioSystem.getAudioInputStream(bufIn); // 可直接传入file

          AudioFormat format = audioIn.getFormat();
          sourceDataLine = AudioSystem.getSourceDataLine(format);
          sourceDataLine.open();
          // 必须open之后
          sourceDataLine.start();
          byte[] buf = new byte[1024];

          int len = -1;
          while (isPlaying && (len = audioIn.read(buf)) != -1) {
            sourceDataLine.write(buf, 0, len);
          }

        } catch (Exception e) {
          e.printStackTrace();
        } finally {

          if (sourceDataLine != null) {
            sourceDataLine.drain();
            sourceDataLine.close();
          }
          try {
            if (bufIn != null) {
              bufIn.close();
            }
            if (audioIn != null) {
              audioIn.close();
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      } while (isPlaying && isLoop);
    }
  }


}