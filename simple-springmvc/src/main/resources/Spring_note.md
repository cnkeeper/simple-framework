ConfigurationClassBeanDefinitionReader

### AnnotationConfigUtils默认添加的RootBeanDefinition
ConfigurationClassPostProcessor
会为Configuration对象生成代理类 org.springframework.context.annotation.ConfigurationClassEnhancer#newEnhancer

Spring最核心的技术之一--万物皆是BeanPostProcess
AutowiredAnnotationBeanPostProcessor(支持@Autowire,@Value,@Inject注解)
CommonAnnotationBeanPostProcessor（支持@Resource,n@PostConstruct，@PreDestroy注解）
ConfigurationClassPostProcessor(支持@Configuration注解)
ApplicationContextAwareProcessor(支持ApplicationContextAware接口)

refresh
	BeanDefinitionRegistryPostProcessor.postBeanDefinition: 完成针对@Configuration 完成bean的扫描
	beanFactoryPostProcess.postProcessBeanFactory: @Configuration类生成代理

生命周期
(@PostConstruct > afterPropertiesSet)


createBean
	if null != InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation（可能生成代理对象, 实例化）:
		return proxyBean
后置处理器：
	else:
		new Object

pupulate
	InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation
	autowireByName/ByType(没搞懂要干啥)
	InstantiationAwareBeanPostProcessor.postProcessProperties(@Autowire, @Value, @Resource等属性/方法的注入)

initializeBean
	BeanPostProcessor.postProcessBeforeInitialization
	InitializingBean.afterPropertiesSet(初始化)
	BeanPostProcessor.postProcessAfterInitialization



0 = {ApplicationContextAwareProcessor@6322} 
1 = {ConfigurationClassPostProcessor$ImportAwareBeanPostProcessor@6323} 
2 = {PostProcessorRegistrationDelegate$BeanPostProcessorChecker@6324} 
3 = {ConfigurationPropertiesBindingPostProcessor@6325} 
4 = {AnnotationAwareAspectJAutoProxyCreator@6326} 
5 = {PersistenceExceptionTranslationPostProcessor@6327}
6 = {MethodValidationPostProcessor@6328}
7 = {MeterRegistryPostProcessor@6329} 
8 = {CommonAnnotationBeanPostProcessor@5583} 
9 = {AutowiredAnnotationBeanPostProcessor@4843} 
10 = {ApplicationListenerDetector@6330} 


priorityOrderedPostProcessors = {ArrayList@7318}  size = 3
 0 = {ConfigurationPropertiesBindingPostProcessor@7352} 
 1 = {CommonAnnotationBeanPostProcessor@7353} 
 2 = {AutowiredAnnotationBeanPostProcessor@7334} 

 orderedPostProcessors = {ArrayList@7356}  size = 3
 0 = {AnnotationAwareAspectJAutoProxyCreator@7396} 
 1 = {PersistenceExceptionTranslationPostProcessor@7487} 
 2 = {MethodValidationPostProcessor@7598}

 internalPostProcessors = {ArrayList@7321}  size = 2
 0 = {CommonAnnotationBeanPostProcessor@7353} 
 1 = {AutowiredAnnotationBeanPostProcessor@7334} 

result = {CopyOnWriteArrayList@4747}  size = 11
 0 = {ApplicationContextAwareProcessor@5189} 
 1 = {ConfigurationClassPostProcessor$ImportAwareBeanPostProcessor@5191} 
 2 = {PostProcessorRegistrationDelegate$BeanPostProcessorChecker@7518} 
 3 = {ConfigurationPropertiesBindingPostProcessor@7352} 
 4 = {AnnotationAwareAspectJAutoProxyCreator@7396}
 5 = {PersistenceExceptionTranslationPostProcessor@7487}
 6 = {MethodValidationPostProcessor@7598}
 7 = {MeterRegistryPostProcessor@7684} 
 8 = {CommonAnnotationBeanPostProcessor@7353} 
 8 = {CommonAnnotationBeanPostProcessor@7353} 
 9 = {AutowiredAnnotationBeanPostProcessor@7334} 
 10 = {ApplicationListenerDetector@7709} 



* spring通过asm的ClassVisitor实现递归解析类，注解的元信息MetaData.【SimpleMetadataReader, AnnotationMetadataReadingVisitor】

ApplicationContext:
AbstractAutowireCapableBeanFactory

#### @Configuation实现:
ConfigurationClassPostProcessor
ConfigurationClassParser#doProcessConfigurationClass 
	标注@Componenet的扫描内部类，会递归扫描类注解，内部类，含有注解的方法列表
	@PropertySource,@ComponentScan，@Import, @ImportSource, @Bean

#### @Autowire实现：
AutowiredAnnotationBeanPostProcessor(@Autowire, @Value, @Inject的注入实现)
+org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#populateBean

#### placeholder实现：
PropertyPlaceholderAutoConfiguration
PropertySourcesPlaceholderConfigurer(BeanFactoryPostProcess)解析文件生成K-V真实的数据注入由AutowiredAnnotationBeanPostProcessor完成

#### aop实现:
AbstractAutoProxyCreator#postProcessAfterInitialization + wrapIfNecessnary
org.springframework.aop.config.AopConfigUtils
org.springframework.aop.aspectj.annotation.BeanFactoryAspectJAdvisorsBuilder#buildAspectJAdvisors扫描@Aspect注解的类
DefaultAopProxyFactory代理生成方式选择


#### @Conditional实现:
https://blog.csdn.net/kangsa998/article/details/97940187
ConditionEvaluator.shouldSkip(...)
在 解析 ConfigurationClass 阶段，phase 参数是 PARSE_CONFIGURATION
在 加载 BeanDefinition 阶段，phase 参数是 REGISTER_BEAN

ConfigurationParse解析所有的@Configuration类，获取其中的BeanDefinition, 这一步PARSE_CONFIGURATION生效
然后将所有的@Configuration类包装成BeanDefinition
在invokeBeanFactoryPostProcessors时触发, 注册所有的BeanDefinition


#### spring.handlers文件:
DefaultNamespaceHandlerResolver

#### @Component扫描:
ComponentScanAnnotationParser

#### ApplicationContextAware:
ApplicationContextAwareProcessor

#### BeanDefinitionLoader扫描器, 解析xml/annotation

#### spring.factories文件扫描
EnumAutoConfigurationImportSelector会调用SpringFactoriesLoader加载所有的spring.factories文件，从而读入configuration配置类名

#### @Transactional
@EnableTransactionManagement
ProxyTransactionManagementConfiguration
本质上是代码生成了一个切面


生成AbstractAutowireCapableBeanFactory；
生成ClassPathBeanDefinitionScanner扫描器；
默认注册RootBeanDefinitation;
doscan
解析默认的RootBeanDefinitation扫描class,org.springframework.context.annotation.ConfigurationClassParser#doProcessConfigurationClass




@Import的实现：https://www.cnblogs.com/hermanlife/p/10019473.html

@EnableAutoConfiguration会扫描所有的spring.factories

#### SpringBoot自动加载的核心
ConfigurationBeanPostProcess(BeanFactoryPostProcess)后置处理器处理启动类上的@Configuration注解
> 内部类
> @PropertySource
> 解析@ComponentScan注解，扫描所有需要spring接管的类
> @Import, @ImportSource(Enable注解的实现，就是每个注解上有@Import导入了Selector回调类)
> 扫描包含@Bean注解的方法
在容器初始化时，执行到invokeBeanFactoryPostProcessors, 会处理@Configuration类，为其生成代理类.


> Spring中大量的扩展点都是依托于spring内部的处理流程，各种后置处理器，事件监听器


### BeanFactoryPostProcess
#### 方法列表
- *postProcessBeanDefinitionRegistry*
 spring已经完成beanDefinition扫描后执行，可添加自定义的definition.
 `ConfigurationClassPostProcessor`正是在这一步完成所有@Configuration类扫描，@Component扫描.
 `MapperScannerConfigurer`完成mapper扫描注册
 
- *postProcessBeanFactory*
  `PropertySourcesPlaceholderConfigurer`完成配置文件解析成KV对.

### BeanPostProcess
#### 方法列表
##### 实例化阶段
- postProcessBeforeInstantiation
  对象实例化前生效，可以解决循环依赖中的代理对象问题.
- postProcessAfterInstantiation
  对象实例化后执行，暂未使用.

##### 初始化阶段
- postProcessPropertyValues
  在spring填充依赖到对象前执行。
  `AutowiredAnnotationBeanPostProcessor`(@Autowire, @Value, @Inject的注入实现).
  `CommonAnnotationBeanPostProcessor`（@Resource,@PostConstruct，@PreDestroy实现）.
  

##### 初始化方法回调
- postProcessBeforeInitialization
  `BeanValidationPostProcessor`在这里完成了限制校验.
  `ConfigurationPropertiesBindingPostProcessor`完成对@ConfigurationProperties的绑定.
  `ApplicationContextAwareProcessor`ApplicationContextAware的注入实现.
- postProcessAfterInitialization
  `AbstractAutoProxyCreator`aop代理对象生成逻辑.

