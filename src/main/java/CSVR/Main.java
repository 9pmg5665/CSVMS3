package CSVR;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
/* Author Connor Berry
 * Date 8/14
 * 
 */
public class Main {

	public static void main(String[] args) {
		//to allow picking of file
		JFileChooser fc = new JFileChooser();
		//filter to only CSV Files
		fc.setFileFilter(new FileNameExtensionFilter("CSV FILES", "CSV"));
		//not used but could easily be for another function.
		int ret = fc.showOpenDialog(null);
		//contains fil
		File data = fc.getSelectedFile();
		//brings sql to life
		SQLManager databaseMan = new SQLManager();
		//drop default table, necessary for reducing spam.
		databaseMan.dropTable();
		//makes table
		databaseMan.makeTable();
		//initializes function that reads and performs goal
		Reader read = new Reader();
		read.readFile(data);
	}

}
