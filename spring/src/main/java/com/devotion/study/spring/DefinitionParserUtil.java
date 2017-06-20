package com.devotion.study.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DefinitionParserUtil {

	public static void parseLimitedList(ManagedList<Object> filters, Node item, ParserContext ctx,
			AbstractBeanDefinition beanDefinition, String attribute) {
		parseLimitedList(filters, item, ctx, beanDefinition, attribute, true);
	}

	public static void parseLimitedList(ManagedList<Object> nestedFiles, Node item, ParserContext parserContext,
			AbstractBeanDefinition beanDefinition, String scope, boolean supportCustomTags) {

		if (item.getNodeType() == Node.ELEMENT_NODE) {
			Element elem = (Element) item;
			String tagName = item.getLocalName();
			if (tagName.equals("value")) {
				String val = item.getTextContent();
				if (scope.equals("step") && (val.startsWith("#{") && val.endsWith("}"))
						&& (!val.startsWith("#{jobParameters"))) {
					ExpressionParser parser = new SpelExpressionParser();
					Expression exp = parser.parseExpression(val.substring(2, val.length() - 1));
					nestedFiles.add(exp.getValue());
				} else {
					nestedFiles.add(val);
				}
			} else if (tagName.equals("ref") || tagName.equals("idref")) {
				nestedFiles.add(parserContext.getRegistry().getBeanDefinition(item.getTextContent()));
			} else if (tagName.equals("bean")) {
				DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
				BeanDefinitionParserDelegate pd = new BeanDefinitionParserDelegate(parserContext.getReaderContext());
				BeanDefinitionHolder bh = pd.parseBeanDefinitionElement(elem, beanDefinition);
				BeanDefinitionReaderUtils.registerBeanDefinition(bh, beanFactory);
				Object bean = beanFactory.getBean(bh.getBeanName());
				nestedFiles.add(bean);
			} else if (tagName.equals("property")) {
				BeanDefinitionParserDelegate pd = new BeanDefinitionParserDelegate(parserContext.getReaderContext());
				pd.parsePropertyElement(elem, beanDefinition);
			} else if (supportCustomTags) {
				BeanDefinitionParserDelegate pd = new BeanDefinitionParserDelegate(parserContext.getReaderContext());
				BeanDefinition bd = pd.parseCustomElement(elem, beanDefinition);
				nestedFiles.add(bd);
			}
		}
	}

}
