package org.myoggradio.rmp;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
 
public class MP3Player extends Application
{
    private AudioClip audioClip;
    private static ArrayList<File> lieder = new ArrayList<File>();
    public static File musik_home = new File("Z:\\Musik");
    public static boolean shouldPlay = true;
     
    public static void main(String[] args) 
    {
        if (args.length > 0)
        {
        	musik_home = new File(args[0]);
        }
        Application.launch(args);
    }
     
    @Override
    public void init() throws MalformedURLException 
    {
     	JFileChooser chooser = new JFileChooser(); 
        chooser.setCurrentDirectory(musik_home);
        chooser.setDialogTitle("Musik Home");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
        {
        	musik_home = chooser.getSelectedFile();
        }
    	getLieder(musik_home);
    	System.out.println("Anzahl MP3 eingelesen: " + lieder.size());
    	audioClip = createNextClip();
    	audioClip.play();
     	MyThread mt = new MyThread(audioClip);
    	mt.start();
    }
    public static AudioClip createNextClip() throws MalformedURLException
    {
    	int n = lieder.size();
    	double r = Math.random();
    	int x = (int) (r * (double) n);
    	File lied = lieder.get(x);
    	System.out.println(lied.getName());
        final URL resource = lied.toURI().toURL();
        AudioClip erg = new AudioClip(resource.toExternalForm());
        return erg;
    }
    private void getLieder(File dir)
    {
    	File[] files = dir.listFiles();
    	for (int i=0;i<files.length;i++)
    	{
    		File file = files[i];
    		if (file.isDirectory()) getLieder(file);
    		else setLied(file);
    	}
    }
    private void setLied(File file)
    {
    	if (istMP3(file))
    	{
    		lieder.add(file);
    	}
    }
    private boolean istMP3(File file)
    {
    	boolean erg = false;
    	String pfad = file.getAbsolutePath();
    	int l = pfad.length();
    	if (l > 3)
    	{
    		String suffix = pfad.substring(l-3,l).toLowerCase();
    		if (suffix.equals("mp3")) erg = true;
    	}
    	return erg;
    }
     
    @Override
    public void start(Stage stage) 
    {
        Button playButton = new Button("Play");
        Button stopButton = new Button("Stop");
        playButton.setOnAction(new EventHandler <ActionEvent>() 
        {
            public void handle(ActionEvent event) 
            {
                shouldPlay = true;
            }
        });     
        stopButton.setOnAction(new EventHandler <ActionEvent>() 
        {
            public void handle(ActionEvent event) 
            {
                shouldPlay = false;
            }
        });     
        HBox buttonBox = new HBox(5, playButton, stopButton);
        VBox root = new VBox(5, buttonBox);
        root.setPrefWidth(300);
        root.setPrefHeight(350);
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("MyMP3Player");
        stage.show();       
    }   
}