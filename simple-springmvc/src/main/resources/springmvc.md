====================RequestMapping===============================
  faviconHandlerMapping = SimpleUrlHandlerMapping
  endpointHandlerMapping = EndpointHandlerMapping
* requestMappingHandlerMapping = RequestMappingHandlerMapping
  welcomePageHandlerMapping = WebMvcAutoConfiguration$WelcomePageHandlerMapping
  beanNameHandlerMapping = BeanNameUrlHandlerMapping
* resourceHandlerMapping = SimpleUrlHandlerMapping
  viewControllerHandlerMapping = WebMvcConfigurationSupport$EmptyHandlerMapping
  defaultServletHandlerMapping = WebMvcConfigurationSupport$EmptyHandlerMapping

=====================HandlerAdapter==============================
* requestMappingHandlerAdapter = RequestMappingHandlerAdapter
* httpRequestHandlerAdapter = HttpRequestHandlerAdapter
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