package CSVR;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Writer {
	//dedicated to writing bad data
    private PrintWriter writer;

    public void createFile() {

        try {
        	//for recording bad data
            this.writer = new PrintWriter(new File("bad-data.csv"));
            //for what would be the ordinary format in the bad file.
            StringBuilder sb = new StringBuilder();
            sb.append("A,"+
            "B,"+
            "C,"+
            "D,"+
            "E,"+
            "F,"+
            "G,"+
            "H,"+
            "I,"+
            "J");
            //writing to file from stringbuilder for ease, could also use a buffered writer 
            //but SB is fine for this.
            writer.write(sb.toString());
            //for debug/confirmation purposes
            System.out.println("Bad data csv file created!");

        } catch (FileNotFoundException e) {
        	//in case file is deleted/moved/otherwise missing mid usage.
            System.out.println(e.getMessage());
        }

    }
    public void Write(String[] args) {

        StringBuilder sb = new StringBuilder();
        sb.append('\n');
        //for each string, append the builder with string & a comma
        for (String s : args) {
            sb.append(s);
            sb.append(",");
        }
        //delete the extra space
        sb.deleteCharAt(sb.length() - 1);
        //write to file
        this.writer.write(sb.toString());
        //devug/confirmation purposes
        System.out.println("Line inserted in bad data csv file!");

    }
    
    public static void writeLog(int good, int bad, int total) throws IOException{
		//dedicated to writing log file of total lines
		Logger log = Logger.getLogger(Writer.class.getName());
		//creates an app.log file
		FileHandler filer = new FileHandler("app.log", true);
		log.addHandler(filer);
		//is the log that was just made is accessible, add the info of however many entrys exist
		//per catagory. For example file should be around 253 per testing
		if(log.isLoggable(Level.INFO)) {
			//writing entries to log
			log.info("%"+good + " successful entries /n" +
					"%" + bad + " unsuccessful entries /n" +
					"%" + total + " total entries");
		}
		//without closing, throws severe error due to ongoing.
		log.removeHandler(filer);
		filer.close();
	}
}

