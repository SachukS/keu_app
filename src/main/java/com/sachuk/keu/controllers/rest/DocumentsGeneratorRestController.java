package com.sachuk.keu.controllers.rest;

import com.sachuk.keu.database.service.DocumentGeneratorService;
import com.sachuk.keu.database.service.MilitaryManService;
import com.sachuk.keu.entities.MilitaryMan;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class DocumentsGeneratorRestController {

    public final MilitaryManService militaryManService;

    public final DocumentGeneratorService generatorService;

    @RequestMapping(path = "/downloadExhaustFromKEU/{id}", method = RequestMethod.GET)
    public ResponseEntity<ByteArrayResource> download(@PathVariable("id") Long milManId){
        MilitaryMan militaryMan;
        if (militaryManService.findById(milManId).isPresent()) {
            militaryMan = militaryManService.findById(milManId).get();

            String pathToGeneratedFile = generatorService.generateExhaustFromKEU(militaryManService.getExhaustInfo(militaryMan), militaryMan.getSurname());
            File file = new File(pathToGeneratedFile);

            HttpHeaders header = new HttpHeaders();
            header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Витяг_з_КЕУ_" + militaryMan.getSurname() + ".docx");
            header.add("Cache-Control", "no-cache, no-store, must-revalidate");
            header.add("Pragma", "no-cache");
            header.add("Expires", "0");

            Path path = Paths.get(file.getAbsolutePath());
            ByteArrayResource resource = null;
            try {
                resource = new ByteArrayResource(Files.readAllBytes(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);
        }
        return null;
    }
}
