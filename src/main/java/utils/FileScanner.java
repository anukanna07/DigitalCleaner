package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileScanner {

    public static List<File> getScreenshotFiles(String folderPath)
    {
        List<File> screenshotList = new ArrayList<>();

        File folder = new File(folderPath);

        if(folder.exists() && folder.isDirectory())
        {
            File[] files = folder.listFiles();

            for(File file : files)
            {
                String name = file.getName().toLowerCase();

                if(name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg"))
                {
                    screenshotList.add(file);
                }
            }
        }

        return screenshotList;
    }
}