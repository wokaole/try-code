package com.cold.tutorial.maven;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;


public class MavenUtil {

	private static final String FILE_SUFFIX = "lastUpdated"; 
	private static final Logger log = LoggerFactory.getLogger(MavenUtil.class);
	
	public static void cleanJarCache(String repositoryPath) throws FileNotFoundException {
		File mavenRep = new File(repositoryPath);
		if (!mavenRep.exists()) {
			throw new FileNotFoundException();
		}
		File[] listFiles = mavenRep.listFiles((FilenameFilter)FileFilterUtils.directoryFileFilter());
		delCacheFile(listFiles, null);
		log.info("maven repository delete end...");
	}

	private static void delCacheFile(File[] dirs, File[] files) {
		if (dirs != null && dirs.length > 0 ) {
			for (File dir : dirs) {
				File[] childDirs = dir.listFiles((FilenameFilter)FileFilterUtils.directoryFileFilter());
				File[] childFiles = dir.listFiles((FilenameFilter)FileFilterUtils.suffixFileFilter(FILE_SUFFIX));
				delCacheFile(childDirs, childFiles);
			}
		}
		
		if (files != null && files.length > 0) {
			for (File file : files) {
				if(file.delete()) {
					log.info("File {} has been deleted!", file.getName());
				}
			}
		}
	}
}
