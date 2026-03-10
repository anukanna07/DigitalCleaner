package utils;

public class RiskIndicator {

    public static String getRiskLevel(String fileName){

        fileName = fileName.toLowerCase();

        if(fileName.endsWith(".exe") || 
           fileName.endsWith(".dll") || 
           fileName.endsWith(".sys")){
            return "HIGH";
        }

        if(fileName.endsWith(".doc") || 
           fileName.endsWith(".docx") ||
           fileName.endsWith(".pdf")){
            return "MEDIUM";
        }

        return "LOW";
    }
}