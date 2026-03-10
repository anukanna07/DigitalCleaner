package utils;

import java.io.File;

public class FileInfo {

    private File file;
    private boolean duplicate;
    private boolean oldFile;
    private boolean blurry;

    public FileInfo(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }

    public boolean isOldFile() {
        return oldFile;
    }

    public void setOldFile(boolean oldFile) {
        this.oldFile = oldFile;
    }

    public boolean isBlurry() {
        return blurry;
    }

    public void setBlurry(boolean blurry) {
        this.blurry = blurry;
    }
}