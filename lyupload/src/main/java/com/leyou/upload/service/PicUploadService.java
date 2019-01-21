package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.upload.config.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @date 2019/1/4-22:59
 */
@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class PicUploadService {
	@Autowired
	private FastFileStorageClient fastFileStorageClient;
	@Autowired
	private UploadProperties prop;
	public String addImage(MultipartFile file) {
		try {
			//校验文件类型
			if (!prop.getAllowTypes().contains(file.getContentType())){
				throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
			}
			//校验文件内容
			BufferedImage read = ImageIO.read(file.getInputStream());
			if (read==null){
				throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
			}
			//获取文件后缀
			String extension= StringUtils.substringAfterLast(file.getOriginalFilename(),"." );
			//保存目标文件到FastDFS
			StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
			//返回文件地址
			return prop.getBaseUrl()+storePath.getFullPath();
		} catch (IOException e) {
			log.error("上传失败", e);
			throw  new LyException(ExceptionEnum.UPLOAD_DEFAULT);
		}

	}
}
