####扩展spring自定义标签需要以下步骤：
1. 创建一个需要扩展的组件；
2. 定义XSD文件描述组件内容；
3. 实现BeanDefinitionParser接口，用来解析XSD文件的定义和组件定义；
4. 创建Handler文件，扩展自NamespaceHandlerSupport，目的是将组件注册到psring容器；
5. 编写Spring.handlers和Spring.schemas文件。