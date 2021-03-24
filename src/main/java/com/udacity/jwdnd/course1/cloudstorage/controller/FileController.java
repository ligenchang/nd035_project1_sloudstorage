package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController {
    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes, Authentication authentication, Model model) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        String contentType = file.getContentType();
        Long fileSize = file.getSize();
        byte[] fileData = file.getBytes();

        File myfile = new File();
        myfile.setFileName(fileName);
        myfile.setContentType(contentType);
        myfile.setFileSize(fileSize.toString());
        myfile.setFileData(fileData);
        myfile.setUserId(userService.getUser(authentication.getName()).getUserId());
        Integer fileId = fileService.addFile(myfile);

        List<File> f = this.fileService.getFiles(authentication.getName());
        model.addAttribute("files", f);
        model.addAttribute("resultSuccess", true);

        return "result";
    }


    @GetMapping("/delete/{id}")
    public String deleteFile(@PathVariable("id") Integer id, Model model, Authentication authentication){
        System.out.println(id);
        fileService.deleteFile(id);
        List<File> f = this.fileService.getFiles(authentication.getName());
        model.addAttribute("files", f);
        model.addAttribute("resultSuccess", true);
        return "result";
    }


    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> viewFile(@PathVariable("id") Integer id, Model model, Authentication authentication) throws IOException {
        File file = this.fileService.getFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + file.getFileName() + "\"").body(new
                        ByteArrayResource(file.getFileData()));
    }
}
