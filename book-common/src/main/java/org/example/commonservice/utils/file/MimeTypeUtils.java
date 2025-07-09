package org.example.commonservice.utils.file;


public class MimeTypeUtils {

    public static final String IMAGE_PNG = "image/png";
    public static final String IMAGE_JPG = "image/jpg";
    public static final String IMAGE_JPEG = "image/jpeg";
    public static final String IMAGE_GIF = "image/gif";
    public static final String[] IMAGE_EXTENSION = {"gif", "jpg", "jpeg", "png"};
    public static final String[] FLASH_EXTENSION = {"swf", "flv"};
    public static final String[] MEDIA_EXTENSION = {"swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg",
        "asf", "rm", "rmvb"};
    public static final String[] VIDEO_EXTENSION = {"mp4", "avi", "rmvb"};
    public static final String[] DEFAULT_ALLOWED_EXTENSION = {
        // image
        "gif", "jpg", "jpeg", "png",
        // word excel powerpoint
        "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
        // compressedFiles
        "rar", "zip", "gz", "bz2",
        // videoFormat
        "mp4", "avi", "rmvb",
        // pdf
        "pdf"};

    public static String getExtension(String prefix) {
        return switch (prefix) {
            case IMAGE_PNG -> "png";
            case IMAGE_JPG -> "jpg";
            case IMAGE_JPEG -> "jpeg";
            case IMAGE_GIF -> "gif";
            default -> "";
        };
    }
}
