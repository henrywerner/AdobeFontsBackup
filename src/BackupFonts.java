import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.nio.file.Files;

public class BackupFonts {
    public static void main(String[] args) throws Exception {
        int progress = 0;
        int progressEndPoint = 0;

        //establish output directory
        File outputDir = new File("./fonts");

        //make sure font directory exists
        String appdataLocation = System.getenv("APPDATA");
        File fontDir = new File( appdataLocation + "/Adobe/CoreSync/plugins/livetype/r");
        System.out.println("Locating " + fontDir);
        if (!fontDir.exists()) {
            System.out.println("File path does not exist.");
            return;
        }
        else {
            System.out.println("File path located. \nCopying files...");
        }

        //create list of all files in Adobe Fonts local directory
        File[] fontList = fontDir.listFiles();
        if (fontList == null) { //catch nullPtr exceptions
            System.out.println("No files in file path.");
            return;
        }
        progressEndPoint = fontList.length * 2;
        System.out.println("Files found: " + fontList.length);

        for (File x : fontList) {
            System.out.print(x.getName() + " ");
            Font f = Font.createFont(Font.TRUETYPE_FONT, x); //What is a fontFormatException and how do I account for it?
            progress++;
            //System.out.println(f.getName());
            updateProgressBar(f.getName(), progress, progressEndPoint);
            

            File fBackup = new File("./fonts/" + f.getName() + ".otf"); //create output path with proper name and filetype
            if (fBackup.isFile())
                fBackup.delete(); //replace existing file with the same name

            Files.copy(x.toPath(), fBackup.toPath()); //copy original file to the output path
            progress++;
            updateProgressBar("Saved.", progress, progressEndPoint);
        }
    }

    private static void updateProgressBar(String txt, int progCurrent, int progEnd) throws Exception {
        char tickChar = '█', emptyChar = '_', startChar = '│', endChar = '│';
        int barLength = 75;
        float progressState = (progCurrent / (float)progEnd) * 100; 
        String printedStateValue = String.format("%02d", (int)progressState);
        printedStateValue = progCurrent + "/" + progEnd + " " + printedStateValue; // debug math
        printedStateValue = " " + printedStateValue + '%';

        String progressBar = "" + startChar;
        for (int x = 0; x < barLength; x++) {
            if (x <= progressState / 100 * barLength) 
                progressBar += tickChar;
            else
                progressBar += emptyChar;
        }
        progressBar += endChar + printedStateValue + "\r";

        while (txt.length() < barLength + 2 + printedStateValue.length()) { // the +6 is the chars for beginning, end, and printedStateVlaue
            txt += " ";
        }

        System.out.print(txt + "\n" + progressBar);

        if (progCurrent == progEnd)
            System.out.println("\nDone.");

        Thread.sleep(10);
    }
}
