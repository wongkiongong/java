package com.huangqg.ano;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;;

@Target(ElementType.TYPE)
public @interface Table {
    /**
     * 数据表名称注解，默认值为类名称
     * @return
     */
    public String tableName() default "className";
}
