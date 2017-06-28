package top.wugy.study.spring;

import java.util.List;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class FileListDefinitionParser extends AbstractSingleBeanDefinitionParser {

	@Override
	protected Class<?> getBeanClass(Element element) {
		return FileListFactoryBean.class;
	}

	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		builder.addPropertyValue("directory", element.getAttribute("directory"));
		String scope = element.getAttribute("scope");
		builder.setScope(scope);

		AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
		ParserContext nestedCtx = new ParserContext(parserContext.getReaderContext(), parserContext.getDelegate(),
				beanDefinition);
		Element exclusionElement = DomUtils.getChildElementByTagName(element, "fileFilter");
		if (null != exclusionElement) {
			FileFilterDefinitionParser fileFilterParser = new FileFilterDefinitionParser();
			builder.addPropertyValue("filters", fileFilterParser.parse(exclusionElement, nestedCtx));
		}

		List<Element> fileLists = DomUtils.getChildElementsByTagName(element, "fileList");
		ManagedList<Object> nestedFiles = new ManagedList<>();
		FileListDefinitionParser parser = null;
		if (!CollectionUtils.isEmpty(fileLists)) {
			parser = new FileListDefinitionParser();
			for (Element fileElement : fileLists) {
				nestedFiles.add(parser.parse(fileElement, nestedCtx));
			}
		}

		NodeList nodeList = element.getChildNodes();
		for (int i = 0, len = nodeList.getLength(); i < len; i++) {
			DefinitionParserUtil.parseLimitedList(nestedFiles, nodeList.item(i), parserContext, beanDefinition, scope,
					false);
		}

		builder.addPropertyValue("nestedFiles", nestedFiles);
	}

}
