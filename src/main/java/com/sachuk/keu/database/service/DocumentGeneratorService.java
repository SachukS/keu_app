package com.sachuk.keu.database.service;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DocumentGeneratorService {
    private final static String SAVED_FILE_DIRECTORY_PATH = "src/main/resources/static/documents/generated_files/";
    private final static String LOAD_FILE_DIRECTORY_PATH = "src/main/resources/static/documents/documents_templates/Витяг_з_КЕУ.docx";

    public String generateExhaustFromKEU(String[] val, String fileName){
        Document document = new Document();
        document.loadFromFile(LOAD_FILE_DIRECTORY_PATH);
        Map<String, String> replacements = new HashMap<>();
        String[] keys = {"dateNow", "rank", "surname", "name", "fatherName", "garnison", "dateWhen", "count", "place",
                "familyMembers", "JKName", "quoteName", "quoteDate", "quoteType", "numberInGarnisonQueue"
                , "numberInQueueType", "docNumber"};

        for (int i = 0; i < val.length; i++) {
            replacements.put(keys[i], val[i]);
        }

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            document.replace("{" + entry.getKey() + "}", entry.getValue(), true, true);
        }

        String savePath = SAVED_FILE_DIRECTORY_PATH + "Витяг_з_КЕУ_" + fileName + ".docx";
        document.saveToFile(savePath, FileFormat.Docx_2013);
        return savePath;
    }
}
