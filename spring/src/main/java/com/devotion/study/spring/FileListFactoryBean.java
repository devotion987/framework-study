package com.devotion.study.spring;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FileListFactoryBean implements FactoryBean<Collection<File>> {

	private String directory;
	private Collection<FileFilter> filters;
	private Collection<File> nestedFiles;

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public void setFilters(Collection<FileFilter> filters) {
		this.filters = filters;
	}

	public void setNestedFiles(Collection<Collection<File>> nestedFiles) {
		this.nestedFiles = new HashSet<>();
		for (Collection<File> nested : nestedFiles) {
			this.nestedFiles.addAll(nested);
		}
	}

	@Override
	public Collection<File> getObject() throws Exception {
		Collection<File> files = new ArrayList<>();
		Collection<File> results = new ArrayList<>(0);

		if (!StringUtils.isEmpty(directory)) {
			File dir = new File(directory);
			File[] dirFiles = dir.listFiles();
			if (null != dirFiles && dirFiles.length > 0) {
				files = Arrays.asList(dirFiles);
			}
		}

		if (!CollectionUtils.isEmpty(nestedFiles)) {
			files.addAll(nestedFiles);
		}
		if (!CollectionUtils.isEmpty(filters)) {
			boolean add = true;
			for (File file : files) {
				for (FileFilter fileFilter : filters) {
					if (!fileFilter.accept(file)) {
						add = false;
						break;
					}
				}
				if (add) {
					results.add(file);
				}
			}
			return results;
		}
		return files;
	}

	@Override
	public Class<?> getObjectType() {
		return Collection.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
