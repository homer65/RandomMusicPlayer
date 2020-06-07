package org.myoggradio.rmp;
import javafx.scene.media.AudioClip;
public class MyThread extends Thread
{
	private AudioClip audioClip = null;
	public MyThread(AudioClip audioClip)
	{
		this.setDaemon(true);
		this.audioClip = audioClip;
	}
	public void run()
	{
		while (true)
		{
			try {
				Thread.sleep(1000);
				if (!audioClip.isPlaying())
				{
					if (MP3Player.shouldPlay)
					{
						System.out.println("MyThread:run:startPlaying");
						audioClip = MP3Player.createNextClip();
						audioClip.play();
					}
				}
				else
				{
					if (!MP3Player.shouldPlay)
					{
						System.out.println("MyThread:run:stopPlaying");
						audioClip.stop();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
