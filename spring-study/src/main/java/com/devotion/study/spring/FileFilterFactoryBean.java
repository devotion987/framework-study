package com.devotion.study.spring;

import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.filefilter.NameFileFilter;
import org.springframework.beans.factory.FactoryBean;

public class FileFilterFactoryBean implements FactoryBean<Collection<FileFilter>> {

	private final List<FileFilter> filters = new ArrayList<>();

	@Override
	public Collection<FileFilter> getObject() throws Exception {
		return filters;
	}

	@Override
	public Class<?> getObjectType() {
		return Collection.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public void setFilters(Collection<Object> filterList) {
		for (Object o : filterList) {
			if (o instanceof String) {
				filters.add(new NameFileFilter(o.toString()));
			} else if (o instanceof FileFilter) {
				filters.add((FileFilter) o);
			}
		}
	}

}
