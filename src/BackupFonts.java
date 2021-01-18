import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.nio.file.Files;

public class BackupFonts {
    public static void main(String[] args) throws IOException, FontFormatException {
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

        for (File x : fontList) {
            System.out.print(x.getName() + " ");
            Font f = Font.createFont(Font.TRUETYPE_FONT, x); //What is a fontFormatException and how do I account for it?
            System.out.println(f.getName());
            progress++;

            File fBackup = new File("./fonts/" + f.getName() + ".otf"); //create output path with proper name and filetype
            if (fBackup.isFile())
                fBackup.delete(); //replace existing file with the same name

            Files.copy(x.toPath(), fBackup.toPath()); //copy original file to the output path
            progress++;
        }

        //System.out.println("Total progress: " + progress + "/" + progressEndPoint);
    }
}
