package com.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


import com.jiadoctor.common.util.DateHelper;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class CommonUpload {

	private final long phothMaxSize = 0x400000L; // 4M
	private final long videoMaxSize = 0x6400000L; // 100M
	private final String allowTypePhoth = "image/bmp,image/x-png,image/png,image/gif,image/pjpeg,image/jpeg";
	private final String allowTypeVideo = "";

	private Integer id;
	private File upload;
	private String uploadFileName;
	private String oldUploadFileName;
	private String uploadContentType;
	private String allowTypes;
	private String savePath;
	private String handleType;
	private long maxSize;
	private int zipWidth;
	private int zipHeight;

	/**
	 * 压缩文件路径
	 */
	private String zipSavePath;

	public String getAllowTypePhoto() {
		return allowTypePhoth;
	}

	public String getAllowTypeVideo() {
		return allowTypeVideo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHandleType() {
		return handleType;
	}

	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}

	public long getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getOldUploadFileName() {
		return oldUploadFileName;
	}

	public void setOldUploadFileName(String oldUploadFileName) {
		this.oldUploadFileName = oldUploadFileName;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getAllowTypes() {
		return allowTypes;
	}

	public void setAllowTypes(String allowTypes) {
		this.allowTypes = allowTypes;
	}

	public void setSavePath(String path) throws Exception {
		this.savePath = path;
	}

	@SuppressWarnings("deprecation")
	public String getSavePath() throws Exception {
//		String realPath = ServletActionContext.getRequest().getRealPath(savePath);
		String realPath="";
		return realPath;
	}
    
	
	
	public boolean uploadFile() throws Exception {

		/**
		 * 添加压缩文件
		 */

		if (!checkUpload())
			return false;

		FileOutputStream fos = null;
		FileInputStream fis = null;
		String newFileName = null;
		if (uploadFileName != null && !uploadFileName.equals("")) {
			try {
				String inputFileExt = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);
				newFileName = (new StringBuffer(String.valueOf((new DateHelper()).getRandomNum()))).append(".")
					.append(inputFileExt).toString();
				String outputFilePath = (new StringBuffer(String.valueOf(getSavePath()))).append(Constants.FILE_SEPARATOR).append(
					newFileName).toString();
				System.out.println(outputFilePath);
				fos = new FileOutputStream(outputFilePath);
				fis = new FileInputStream(getUpload());
				byte buffer[] = new byte[1024];
				for (int len = 0; (len = fis.read(buffer)) > 0;) {
					fos.write(buffer, 0, len);
				}
				if ("update".equals(getHandleType())) {
					if (!delectFile())
						return false;
				}

				uploadFileName = newFileName;

			} catch (Exception e) {
				return false;
			} finally {
				fos.close();
				fis.close();
			}
			if (zipSavePath != null && !"".equals(this.zipSavePath))
				zipTool(upload, newFileName, this.zipWidth, this.zipHeight);
		}
		return true;
	}

	public boolean delectFile() throws Exception {
		File delFile = new File((new StringBuffer(String.valueOf(getSavePath()))).append(Constants.FILE_SEPARATOR).append(
			oldUploadFileName).toString());
		if (delFile.exists()) {
			try {
				delFile.delete();
			} catch (Exception e) {
				return false;
			}
		}

		/**
		 * 删除压缩文件
		 */
		File delZipFile = new File((new StringBuffer(String.valueOf(getZipSavePath()))).append(Constants.FILE_SEPARATOR).append(
			"zip_" + oldUploadFileName).toString());
		if (delZipFile.exists()) {
			try {
				delZipFile.delete();
			} catch (Exception e) {
				return false;
			}
		}

		return true;
	}

	public boolean checkUpload() throws Exception {
		String filterResult = filterType(allowTypes.split(","));
		if (filterResult != null) {
			return false;
		} else {
			return checkUploadSize();
		}
	}

	public String filterType(String types[]) {
		if (getUploadContentType() == null)
			return null;
		String fileType = uploadContentType;
		String as[];
		int j = (as = types).length;
		for (int i = 0; i < j; i++) {
			String type = as[i];
			if (type.equals(fileType))
				return null;
		}
		return "FilterError";
	}

	public boolean checkUploadSize() throws Exception {
		if (upload != null && upload.length() > maxSize) {
			return false;
		} else {
			return true;
		}
	}

	public long getPhotoMaxSize() {
		return phothMaxSize;
	}

	public long getVideoMaxSize() {
		return videoMaxSize;
	}

	@SuppressWarnings("deprecation")
	public String getZipSavePath() {
//		String realPath = ServletActionContext.getRequest().getRealPath(zipSavePath);
		String realPath="";
		return realPath;
	}

	public void setZipSavePath(String zipSavePath) {
		this.zipSavePath = zipSavePath;
	}

	/**
	 * 压缩图片信息
	 * 
	 * @param upload
	 * @param uploadFileName
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public void zipTool(File upload, String uploadFileName, int width, int height) throws IOException {
		FileOutputStream fosTemp = null;
		FileInputStream fisTemp = null;
		try {
			String newFileName = "zip_" + uploadFileName;

			String outputFilePath = (new StringBuffer(getZipSavePath())).append(Constants.FILE_SEPARATOR).append(newFileName)
				.toString();

			// 压缩
			Image src = javax.imageio.ImageIO.read(upload);
			src = src.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = tag.createGraphics();
			g2.drawImage(src, 0, 0, width, height, Color.white, null);
			g2.dispose();

			float[] kernelData2 = { -0.125f, -0.125f, -0.125f, -0.125f, 2, -0.125f, -0.125f, -0.125f, -0.125f };
			Kernel kernel = new Kernel(3, 3, kernelData2);
			ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
			tag = cOp.filter(tag, null);
			fosTemp = new FileOutputStream(outputFilePath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fosTemp);
			encoder.encode(tag);

		} catch (Exception e) {

			System.out.println("failure to zip the image.");
			e.printStackTrace();
		} finally {
			if (fosTemp != null)
				fosTemp.close();
			if (fisTemp != null)
				fisTemp.close();
		}
	}

	public int getZipWidth() {
		return zipWidth;
	}

	public void setZipWidth(int zipWidth) {
		this.zipWidth = zipWidth;
	}

	public int getZipHeight() {
		return zipHeight;
	}

	public void setZipHeight(int zipHeight) {
		this.zipHeight = zipHeight;
	}
}
