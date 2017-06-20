package com.devotion.study.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class FileFilterDefinitionParser extends AbstractSingleBeanDefinitionParser {

	@Override
	protected Class<?> getBeanClass(Element element) {
		return FileFilterFactoryBean.class;
	}

	@Override
	protected void doParse(Element element, ParserContext ctx, BeanDefinitionBuilder builder) {
		String scope = element.getAttribute("scope");
		builder.setScope(scope);
		try {
			ManagedList<Object> filters = new ManagedList<Object>();

			NodeList nl = element.getChildNodes();
			for (int i = 0, len = nl.getLength(); i < len; i++) {
				DefinitionParserUtil.parseLimitedList(filters, nl.item(i), ctx, builder.getBeanDefinition(), scope);
			}
			builder.addPropertyValue("filters", filters);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
