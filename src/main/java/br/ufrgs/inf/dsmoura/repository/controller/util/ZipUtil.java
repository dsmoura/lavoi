package br.ufrgs.inf.dsmoura.repository.controller.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.ufrgs.inf.dsmoura.repository.model.entity.ArtifactDTO;
import br.ufrgs.inf.dsmoura.repository.model.entity.Artifactable;

public class ZipUtil {
	
	public final static int BUFFER_SIZE = 4096;
	final static Log logger = LogFactory.getLog(ZipUtil.class);
	
	public static void fromFilesToZipFile(String filename, List<File> files) {
		try {
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
			out.setMethod(ZipOutputStream.DEFLATED);
			
			byte data[] = new byte[BUFFER_SIZE];
			for (File file : files) {
				logger.info("Adding: " + file.getName());
				FileInputStream fi = new FileInputStream(file);
				BufferedInputStream origin = new BufferedInputStream(fi, BUFFER_SIZE);
				ZipEntry entry = new ZipEntry(file.getName());
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Deprecated
	public static ZipOutputStream fromArtifactsToZipFile(String filename, List<ArtifactDTO> files) {
		try {
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
			out.setMethod(ZipOutputStream.DEFLATED);
			
			byte data[] = new byte[BUFFER_SIZE];
			for (ArtifactDTO file : files) {
				logger.info("Adding: " + file.getName());
				InputStream bais = new ByteArrayInputStream(file.getFile());
				BufferedInputStream origin = new BufferedInputStream(bais, BUFFER_SIZE);
				ZipEntry entry = new ZipEntry(file.getName());
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
//			out.close();
			return out;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void fromArtifactsToZipFile(ServletOutputStream outstream, InputStream rassetXMLInputStream, List<Artifactable> files) {
		try {
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(outstream));
			out.setMethod(ZipOutputStream.DEFLATED);
			
			byte data[] = new byte[BUFFER_SIZE];
			
			logger.info("Adding: " + "rasset.xml");
			BufferedInputStream originXML = new BufferedInputStream(rassetXMLInputStream, BUFFER_SIZE);
			ZipEntry entry = new ZipEntry("rasset.xml");
			out.putNextEntry(entry);
			int count;
			while ((count = originXML.read(data, 0, BUFFER_SIZE)) != -1) {
				out.write(data, 0, count);
			}
			originXML.close();
			
			for (Artifactable file : files) {
				if (file.getFile() != null) {
					logger.info("Adding: " + file.getName());
					InputStream bais = new ByteArrayInputStream(file.getFile());
					BufferedInputStream origin = new BufferedInputStream(bais, BUFFER_SIZE);
					String pathEntry = "";
					if (file.getLocation() != null) {
						pathEntry = file.getLocation() + "\\";
					}
					pathEntry += file.getName();
					entry = new ZipEntry(pathEntry);
					out.putNextEntry(entry);
					while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
						out.write(data, 0, count);
					}
					origin.close();
				}
			}
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void fromZipFileToFiles(String zipFilename) {
      try {
      	BufferedOutputStream dest = null;
      	FileInputStream fis;
			fis = new FileInputStream(zipFilename);
         ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
         ZipEntry entry;
         while((entry = zis.getNextEntry()) != null) {
            System.out.println("Extracting: " + entry);
            int count;
            byte data[] = new byte[BUFFER_SIZE];
            // write the files to the disk
            FileOutputStream fos = new FileOutputStream(entry.getName());
            dest = new BufferedOutputStream(fos, BUFFER_SIZE);
            while ((count = zis.read(data, 0, BUFFER_SIZE)) != -1) {
               dest.write(data, 0, count);
            }
            dest.flush();
            dest.close();
         }
         zis.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
