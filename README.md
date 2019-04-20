# springboot-demo
1.工具

①eclipse + Spring Tool Suite (STS) for Eclipse插件

②spring-tool-suite-4-4.0.2.RELEASE（搜sts4官网下载）

③idea

2.new project ，选择spring -> spring starter project

![Image text](https://raw.githubusercontent.com/CoviShieh/img-folder/master/q.png)
![Image text](https://raw.githubusercontent.com/CoviShieh/img-folder/master/2.png)

          （根据自己需要的项目需求课改）

![Image text](https://raw.githubusercontent.com/CoviShieh/img-folder/master/3.png)

    可根据实际需求更改版本号和需要的依赖包，这里简单入门就只选web就好了
    
![Image text](https://raw.githubusercontent.com/CoviShieh/img-folder/master/4.png)
    
              可以看到，项目结构已经生成完毕


         
          
3.写一个的Controller测试
@RestController

public class TestController {

@RequestMapping("/hello")

    public String test(){

    return "hello,this is my fitst boot!";

    }

}

4.运行spring-boot的入口文件DemoApplication.java

![Image text](https://raw.githubusercontent.com/CoviShieh/img-folder/master/5.png)

          （看到这个端口号即为启动成功）

5.启动浏览器访问（ip:端口号/controller定义的路径）

![Image text](https://raw.githubusercontent.com/CoviShieh/img-folder/master/6.png)


完！
