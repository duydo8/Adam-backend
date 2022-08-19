package com.example.adambackend.controller.general;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.adambackend.payload.response.IGenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
public class FileUploadController {


    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dyzq75un4",
            "api_key", "113684863585229",
            "api_secret", "sboa3R1ujWeluS03XPMHh7sjSsc"));
    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam(value = "file",required = false) MultipartFile file) {
        try {

        File file1=convertToFile(file);


            Map uploadResult = cloudinary.uploader().upload(file1, ObjectUtils.emptyMap());
            return ResponseEntity.ok().body(new IGenericResponse<>(uploadResult.get("url"),200,"upload thành công"));

        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }
    public File convertToFile(MultipartFile multipartFile) {
        File file = new File("src/main/resources/targetFile.tmp");

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(multipartFile.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
    @PostMapping("/uploadMultipleFiles")
    public ResponseEntity<?> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        List<MultipartFile> multipartFiles= Arrays.asList(files);
        try {
        List<File> fileList= multipartFiles.stream().map(e->convertToFile(e)).collect(Collectors.toList());
        List<Object> listUrl= new ArrayList<>();
        for (File f: fileList
             ) {

                ObjectUtils objectUtils= new ObjectUtils();
                Map map= objectUtils.emptyMap();
                Map uploadResult = cloudinary.uploader().upload(f,new HashMap());
                listUrl.add(uploadResult.get("url"));
        }
        return ResponseEntity.ok().body(new IGenericResponse<>(listUrl,200,"upload thành công"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
