package com.guozhong.his.modules.market.log.annotation;

import java.lang.annotation.*;  

@Target({ElementType.PARAMETER, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
public  @interface SystemLog {  
  
    String module()  default "";  //模块名称 系统管理-用户管理－列表页面
    String methods()  default "";  //新增用户
    String description()  default "";  //
  
  
}  