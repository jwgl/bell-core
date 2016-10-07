package cn.edu.bnuz.bell.menu.module;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation全能模块菜单配置
 * Created by yanglin on 2016/10/5.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ModuleMenuConfiguration.class)
public @interface EnableModuleMenu {
}
