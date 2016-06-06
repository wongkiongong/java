package com.huangqg.ano;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
public @interface NoDBColumn {
	
    public String name() default "fieldName";
    public String setFuncName() default "setField";
    public String getFuncName() default "getField"; 
    public boolean defaultDBValue() default false;
}
