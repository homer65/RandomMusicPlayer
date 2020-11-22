package org.myoggradio.rmp;

import java.io.File;

import javafx.application.Application;

public class Main 
{
	public static File musik_home = new File("Z:\\Musik");
    public static void main(String[] args) 
    {
        if (args.length > 0)
        {
        	musik_home = new File(args[0]);
        }
        Application.launch(MP3Player.class,args);
    }
}
