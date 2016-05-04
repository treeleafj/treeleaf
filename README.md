# ❦ treeleaf 树叶-java框架
## treeleaf-cache 缓存框架
    提供基于redis和本地内存两种缓存操作封装(集成连接池)
```java
    CacheConfig cacheConfig = new CacheConfig();
    cacheConfig.setType(0);//设置缓存类型,0为redis,1为本地缓存
    cacheConfig.setHost("192.168.0.1");//设置redis远程地址
    cacheConfig.setPassword("treeleaf");//设置redis密码,没密码可不设置
    cacheConfig.setPort(6379);//redis端口
    cacheConfig.setMaxTotal(30);//连接池最大大小
    cacheConfig.init();//初始化
    
    User user = new User();
    user.setId("123");
    user.setUsername("张三");
    
    Cache cache = CacheFactory.getCache();
    cache.set("user@123", user, 60);//以key为"user@123"存在redis中,设置60秒超时
    
    cache.pushQueue("myQueue", user);//将user插入名为"myQueue"的列尾
    
    List list = new ArrayList();
    list.add(user);
    cache.setList("myList", list, 60);//将一个list存到redis,设置60秒超时
    
    Map map = new HashMap()
    map.put("123", "张三");
    cache.setMap("myMap", map, 60);//以key为"myMap"设置一个map,60秒超时
    cache.setMapValue("myMap", "456", "李四");//增加一个key为456,value为李四的数据到myMap中
    
    user = cache.popQueue("myQueue", User.class);//从list的头部取出数据
    user = cache.get("user@123", User.class);
    List<User> users = cache.getList("myList", User.class);
    String value = cache.getMapValue("myMap", "456");
    
    cache.del("user@123");//删除key为user@123的数据
```
## treeleaf-common 通用工具类
    提供分页标准,查询结果集标准,bean操作,http访问,断言,数据加解密,唯一id生成,浮点数精确计算,模版生成,手机邮箱格式验证

```java
    //bean操作
    Map map = new HashMap();
    map.put("id", "123");
    map.put("username", "张三");
    User user = FastBeanUtils.fastPopulate(User.class, map);
    
    String json = Jsoner.toJson(user);
    user = Jsoner.toObj(user);
    
    //http访问
    Map<String, String> param = new HashMap<>();
    param.put("a", "1");
    param.put("b", "2");
    String r = new Get("https://xxx.com").param("c", "3").params(param).get();
    
    String r2 = new Post("https://xxx.com")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body("a=1&b=2&c=3")
                    .post(new FileOutputStream(new File("xxx")));
    
    //断言(所以断言不成功的都会抛出AssertException,附带有失败的提示信息,客户端可以统一拦截并转义)
    Assert.notNull(user, "用户对象不能为空");
    Assert.hasText(user.getUsername(), "用户名不能为空");
    
    AssertEx.hasText(user.getUsername())
            .setRetCode("999999")
            .throwEx("用户{}的用户名不能为空", user.getId());
    
    //数据加密
    byte [] byteKey = Des3.build3DesKey();
    String byte2Base64Key = Des3.build3DesKeyToBase64();
    byte [] result = Des3.encrypt("123".getByte("utf-8"), byteKey);//无论在什么时候调用getByte(),都要记得指定编码
    String result2 = Des3.encryptToBase64("123", byte2Base64Key);
    
    //另还有RSA, sha-1, sha-2
    
    //唯一ID生成
    ID.get();//参考了mongodb的ID生成策略,有序,全局唯一,但如果忽略了大小写则有可能会重复
    Uuid.buildBase64UUID();//改进了uuid,由原来36位缩减到了22位长度
    
    //浮点数精确计算
    //因为java的浮点数计算中存在着失真的问题
    //所有涉及到浮点数运算的都最好使用BigDecimal,且构造BigDecimal时必须是字符串的入参,否则还是会失真)
    double a = Maths.add(0.2, 0.1, 0.1);
    double b = Maths.subtract(0.2, 0.1, 0.1, 0.1);
    double c = Maths.multiply(0.2, 0.1, 0.1);
    double d = Maths.divide(0.2, 0.1, 0.1);
    
    //手机邮箱格式验证
    new MobileNumValidator().validate("15912345678");
    new EmailValidator().validate("123@123.com");
 
```

## treeleaf-db 数据库操作扩展
    提供简易的数据库操作,无需编写SQL
```java
    //treeleaf-db是建立于基于Model的,所以必须最好要有一个Model:
    
   /**
    * 每个凡是要映射到数据库字段的属性都需用@Column标识, 
    * 如果是主键,则需要加上primaryKey=true,
    * autoIncremen,表示是数据库是否自增长,但只支持mysql,不建议使用,因为对
    * 数据库扩展(分库分表,换数据库等)比较限制,默认为false
    * 如果跟数据库字段名不一致, 可以设置value="数据库实际字段名", 
    * requrie用于标识属性在保存或修改的时候是否必须不能为空, 可不设置该值,默认为false
    */
    @Table("t_user")//标注实际的数据库表名
    public class User extends Model {
        
        /**
         * 用户id
         */
        //使用
        @Column(primaryKey = true)
        private String id;
    
        /**
         * 用户名
         */
        @Column(requrie=true)
        private String username;
        
        /**
         * 密码
         */
        @Column
        private String password;
        
        //get set...
    }
    
    User user = new User();
    user.setId("123");
    user.setUsername("张三");
    
    //设置数据源
    DBConnectionFactory factory = new DefaultDBConnectionFactoryImpl();
    factory.setDataSource(dataSource);
    
    //构建数据模型操作对象
    DBModelOperator operator = new MySqlDBModelOperator();
    operator.save(user);//保存
    
    user.setName("张二");
    operator.update(user);//更新
    
    user = operator.findById(user.getId(), User.class);//根据id查找
    
    //如果多条件查询或者统计总数据怎么.? 可以使用Example!
    //先定义一个Example,一个Example最好对应一个模型
    public class UserExample extends Example<UserExample.UserCriteria> {
    
        @Override
        protected UserCriteria createCriteriaInternal() {
            return new UserCriteria();
        }
    
        public static class UserCriteria extends Criteria {

            public UserCriteria andMobileEqualTo(String v) {
                return addCriterion("a.mobile =", v, "mobile");
            }
            
            public PeriodCriteria andUsernameIsNotNull() {
                return addCriterion("a.username is not null");
            }
                    
            public UserCriteria andUsernameLike(String v) {
                return addCriterion("a.username like", '%' + v + '%', "username");
            }
        }
    }
    
    UserExample example = new UserExample();
    //加上查询条件
    example.createCriteria().andMobileEqualTo("159123467").andUsernameLike("张");
    example.or().andUsernameIsNotNull();
    
    //分页
    example.setPageable(new Pageable(1, 10));//从第一页开始,每页10条
    //排序
    example.setOrderByClause("id desc");
    
    List<UserModel> list = operator.findByExample(example, UserModel.class);//查询
    long total = operator.countByExample(example, UserModel.class);//统计总数
    
    //推荐使用PageResult对象返回
    PageResult pageResult = new PageResult<>(example.getPageable(), list, total);
```
## treeleaf-web web框架
    封装Spring MVC,提供明朗的返回类型,日志打印,异常捕获转义,客户端信息获取
```xml
    <!--配置spring mvc-->
    <mvc:annotation-driven>
        <mvc:argument-resolvers>
            <bean class="org.treeleaf.web.spring.handler.ParamHandlerMethodArgumentResolver"/>
            <bean class="org.treeleaf.web.spring.handler.ClientInfoHandlerMethodArgumentResolver"/>
        </mvc:argument-resolvers>
        <mvc:return-value-handlers>
            <bean class="org.treeleaf.web.spring.handler.TextHandlerMethodReturnValueHandler"/>
            <bean class="org.treeleaf.web.spring.handler.HtmlHandlerMethodReturnValueHandler"/>
            <bean class="org.treeleaf.web.spring.handler.RedirectHandlerMethodReturnValueHandler"/>
        </mvc:return-value-handlers>
    </mvc:annotation-driven>
    
    <!-- 拦截器 -->
    <mvc:interceptors>

        <mvc:interceptor>
            <mvc:mapping path="/**"/>
            <mvc:exclude-mapping path="/**/*.js"/>
            <mvc:exclude-mapping path="/**/*.css"/>
            <mvc:exclude-mapping path="/**/*.jpg"/>
            <mvc:exclude-mapping path="/**/*.gif"/>
            <mvc:exclude-mapping path="/**/*.png"/>
            <mvc:exclude-mapping path="/file/**"/>
            <bean class="org.treeleaf.web.spring.interceptor.MultipleHandlerInerceptor">
                <property name="handlers">
                    <list>
                        <!--增加接口访问参数, 耗时, 异常打印-->
                        <bean class="org.treeleaf.web.spring.interceptor.PrintLogHandlerInerceptor"/>
                        <!--使用treeleaf-db配合spring-jdbc时可以配置该拦截器进行数据库连接资源回收-->
                        <bean class="org.treeleaf.web.spring.interceptor.DBConnectionHandlerInterceptor"/>
                    </list>
                </property>
            </bean>
        </mvc:interceptor>

    </mvc:interceptors>


    <!--异常处理,对于请求网页的会返回错误页面,对于请求json格式的会返回json格式的信息-->
    <bean class="org.treeleaf.web.spring.resovler.ExHandlerExceptionResolver">
        <!--默认响应码-->
        <property name="status" value="200"/>
        <property name="exExceptionHanlder">
            <bean class="com.syyg.mobile.ext.MobileExceptionHandler">
                <!--配置错误跳转页面-->
                <property name="errorView" value="/error"/>
                <!--是否采用redirect跳转-->
                <property name="redirect" value="false"/>
                <!--默认提示语-->
                <property name="tip" value="网络繁忙,请稍后尝试"/>
            </bean>
        </property>
    </bean>
```

```java
    @Controller
    @RequestMapping("user")
    public class UserController {

        //返回Html页面
        @RequestMapping("index.html")
        public Html index() {
            User user = new User();
            user.setId("123");
            return new Html(user);//默认跳到spring mvc配置的网页根路径下的user/index.jsp, 同时会放置一个名为model的对象到require的attribute里面
            //return new Html("/user/index", user);//强制指定跳转到哪个jsp
        }
    
        //前端跳转的方式前往user/index.html页面
        @RequestMapping("home.html")
        public Html home() {
            return new Redirect("/user/index.html");
        }
    
        //返回json数据
        @RequestMapping("basicinfo.json")
        public Json basicinfo(String userId) {
            Assert.notNull(userId, "用户id不能为空");
            User user = new User();
            return new Json(user);
        }
        
        //提供jsonp的方式供其它域名下的js调用
        @RequestMapping("basicinfo.jsonp")
        public Jsonp basicinfoJsonp(ClientInfo clientInfo) {
            Assert.notNull(userId, "用户id不能为空");
            log.info("客户端ip:{},是否移动端:{}", clientInfo.getIp(), clientInfo.isMobile());
            User user = new User();
            return new Jsonp(user);
        }
    
        //返回纯文本
        @RequestMapping("status")
        public Text status(Param param) {
            log.info("传入参数:{}", param.asMap());
            return new Text(Text.CONTENT_TYPE_XML, "<xml>\n" +
                           "<return_code><![CDATA[SUCCESS]]></return_code>\n" +
                           "<return_msg><![CDATA[OK]]></return_msg>\n" +
                           "</xml>");
        }
        
        //返回json数据,专供Extjs调用
        @RequestMapping("basicinfo.json")
        public ExtJson basicinfoExt(String userId) {
            Assert.notNull(userId, "用户id不能为空");
            User user = new User();
            return new ExtJson(user);
        }
        
    }
```
    
## treeleaf-webchat 微信模块
    提供了一些微信js sdk上需要用到的微信接口操作, 以及微信支付,微信红包功能
```java
    //微信Jssdk类接口
    Jssdk jssdk = new Jssdk();
    jssdk.setAppid("appid");
    jssdk.setSecret("secret");
    String at = jssdk.access_token();
    SnsUserInfo userInfo = jssdk.snsuserinfo(at, "opendid");
    //具体其它接口见Jssdk
    
    //微信支付
    //统一下单接口, 获取微信支付链接
    UnifiedOrder unifiedOrder = new UnifiedOrder();

    //设置入参
    unifiedOrder.setDevice_info(WechatPay.DEVICE_INFO);
    unifiedOrder.setBody("产品名称");
    unifiedOrder.setOut_trade_no("订单号");
    unifiedOrder.setFee_type("CNY");
    unifiedOrder.setTotal_fee(String.valueOf(Maths.multiply(1, 100D).intValue()));//单位是分
    unifiedOrder.setSpbill_create_ip("127.0.0.1");
    unifiedOrder.setNotify_url("自己的通知地址");
    unifiedOrder.setTrade_type("交易类型");//取值如下：JSAPI，NATIVE，APP，WAP
    unifiedOrder.setOpenid("openid");

    WechatPay wechatPay = new WechatPay();
    wechatPay.setAppid("appid");
    wechatPay.setMerchantNo("merchantNo");
    wechatPay.setKey("key");
    UnifiedOrderResult orderResult = wechatPay.unifiedorder(unifiedOrder);
    
    //另wechatPay提供js sdk支付和支付结果通知验签
    //wechatPay.jsapi(xxx)
    //wechatPay.notice(xml)
    

    //微信红包(有普通也有裂变)
    WechatRedpack wechatRedpack = new WechatRedpack();
    wechatRedpack.setAppid("appid");
    wechatRedpack.setKey("key");
    wechatRedpack.setMerchantNo("merchantNo");
    wechatRedpack.setCertPath("apiclient_cert.p12");

    DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    String billNo = "RB" + format.format(new Date()) + new Random().nextInt(10);

    Redpack redpack = new Redpack();
    redpack.setMch_billno(billNo);
    redpack.setSend_name("sendName");
    redpack.setRe_openid("openId");
    redpack.setTotal_amount("10");
    redpack.setTotal_num("1");
    redpack.setWishing("wishing");
    redpack.setClient_ip("127.0.0.1");
    redpack.setAct_name("actName");
    redpack.setRemark("remarke");

    RedpackResult result = wechatRedpack.send(redpack);
```
    
## treeleaf-queue 队列客户端框架
    暂时提供了kafka的操作封装
    
## treeleaf-config 配置管理
    暂时提供了基于zookeeper的配置管理
