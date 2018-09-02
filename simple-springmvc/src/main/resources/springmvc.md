====================RequestMapping===============================
  faviconHandlerMapping = SimpleUrlHandlerMapping
  endpointHandlerMapping = EndpointHandlerMapping
* requestMappingHandlerMapping = RequestMappingHandlerMapping #处理RequestMapping的映射请求
  welcomePageHandlerMapping = WebMvcAutoConfiguration$WelcomePageHandlerMapping
  beanNameHandlerMapping = BeanNameUrlHandlerMapping
* resourceHandlerMapping = SimpleUrlHandlerMapping #处理url静态资源的请求
  viewControllerHandlerMapping = WebMvcConfigurationSupport$EmptyHandlerMapping
  defaultServletHandlerMapping = WebMvcConfigurationSupport$EmptyHandlerMapping

=====================HandlerAdapter==============================
* requestMappingHandlerAdapter = RequestMappingHandlerAdapter #执行RequestMapping的映射请求
* httpRequestHandlerAdapter = HttpRequestHandlerAdapter #执行url静态资源的请求
simpleControllerHandlerAdapter = SimpleControllerHandlerAdapter

=====================ViewResolver================================
  viewResolver = ContentNegotiatingViewResolver
  beanNameViewResolver = BeanNameViewResolver
* mvcViewResolver = ViewResolverComposite
* defaultViewResolver = InternalResourceViewResolver


=====================Handler======================================
HandlerMethod：映射方法
ResourceHttpRequestHandler: 静态资源


AbstractHandlerMethodMapping$MappingRegistry 

PathResourceResolver

HandlerMethodReturnValueHandler: 返回值处理

资源位置：
/
resources/
META-INF/resources
static/
public/

RequestMappingHandlerMapping初始化时afterPropertiesSet()扫描注册了所有的@RequestMapping方法

//获取所有的对象名称
ApplicationContext.getBeanNamesForType(Object.class));