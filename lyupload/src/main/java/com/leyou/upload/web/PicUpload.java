package com.leyou.upload.web;

import com.leyou.upload.service.PicUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * @date 2019/1/4-22:52
 */
@RestController
@RequestMapping("upload")
public class PicUpload {
	@Autowired
	PicUploadService picUploadService;

	@PostMapping("image")
	public ResponseEntity<String> addImage(@RequestParam("file") MultipartFile file){
		return ResponseEntity.ok(picUploadService.addImage(file));
	}
}
