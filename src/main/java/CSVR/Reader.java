package CSVR;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class Reader {
	//initializations
	BufferedReader br = null;
	Writer write = new Writer();
	
	
public void readFile(File file) {
	
	String line = "";
	int Lines = 0;
	int bad = 0;
	int good = 0;
	//creation of bad data file.
	write.createFile();
	
	try {
		//reads file.
		br = new BufferedReader(new FileReader(file));
		SQLManager manager = new SQLManager();
		//reads every line for errors.
		while((line = br.readLine())!=null){
			//may have to change per formats and per optimization
			String[] row = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			//if boolean is ever false, add to bad CSV and add to bad in log
			boolean correct = true;
			if(row.length > 10) 
				//if row doesn't meet requirements, false
				correct = false;
			else
			{
				//should change per length of wanted csv row
				for (int i = 0; i< row.length; i++) {
					//if a row has an empty slot,  error row, if slot is null, error row, false positive
					//could be removed for optimization but not without giving up bad check
					if(row[i].equals("")||row[i] ==null) 
						correct = false;
						}
			}
			//database validation libraries or account for potential double & Single quote inserts in strings
						if(correct) {
							//insert good row into sqlite
								manager.insertRow(row);
								good++;
							}
						else {
							//insert bad row into bad CSV
							bad++;
							write.Write(row);
						}
						Lines++;
					}
				//end SQL
					manager.connect.close();
			
				try {
					//finish log
					Writer.writeLog(good, bad, Lines);
				}catch(IOException e) {
				e.printStackTrace();
				}
			
		}catch(IOException | SQLException e) {
		e.printStackTrace();
		}finally {
			if(br != null) {
				try {
					//end buffered writer
					br.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
