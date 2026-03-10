package utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.*;

public class ImageAnalyzer {

    public static List<FileInfo> analyze(List<File> files) {

        List<FileInfo> result = new ArrayList<>();
        Map<String, FileInfo> hashMap = new HashMap<>();

        long currentTime = System.currentTimeMillis();

        for(File file : files) {

            FileInfo info = new FileInfo(file);

            // 🔴 Duplicate detection using MD5
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                FileInputStream fis = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int bytesRead;

                while((bytesRead = fis.read(buffer)) != -1) {
                    md.update(buffer, 0, bytesRead);
                }
                fis.close();

                byte[] hashBytes = md.digest();
                StringBuilder sb = new StringBuilder();
                for(byte b : hashBytes) {
                    sb.append(String.format("%02x", b));
                }
                String hash = sb.toString();

                if(hashMap.containsKey(hash)) {
                    info.setDuplicate(true);
                } else {
                    hashMap.put(hash, info);
                }

            } catch(Exception e) {
                e.printStackTrace();
            }

            // 🟡 Old file detection (>30 days)
            long days = (currentTime - file.lastModified()) / (1000*60*60*24);
            if(days > 30) {
                info.setOldFile(true);
            }

            // 🟣 Blurry detection (low resolution)
            try {
                BufferedImage img = ImageIO.read(file);
                if(img != null) {
                    if(img.getWidth() < 800 || img.getHeight() < 600) {
                        info.setBlurry(true);
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }

            result.add(info);
        }

        return result;
    }
}