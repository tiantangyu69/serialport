package org.sagacity.jfinal.config;

import org.sagacity.jfinal.controller.IndexController;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.plugin.tablebind.AutoTableBindPlugin;
import com.jfinal.ext.route.AutoBindRoutes;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.tx.TxByActionMethods;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

/**
 * 项目API引导式配置
 */
public class WebConfig extends JFinalConfig {

	/**
	 * 配置常量
	 */
	public void configConstant(Constants constants) {
		loadPropertyFile("config.properties"); // 加载少量必要配置，随后可用getProperty(...)获取值
		constants.setViewType(ViewType.JSP); // 设置视图类型为Jsp，否则默认为FreeMarker
		constants.setBaseViewPath("/WEB-INF/jsp/");// 配置视图映射路径
		
		constants.setDevMode(getPropertyToBoolean("devMode", false));// 设置开发模式
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes routes) {
		routes.add("/", IndexController.class, "/index"); // 第三个参数为该Controller的视图存放路径
		routes.add(new AutoBindRoutes());// 使用jFinal-ext配置controller自动扫描
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins plugins) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"),
				getProperty("user"), getProperty("password").trim());
		c3p0Plugin.setInitialPoolSize(5);
		c3p0Plugin.setMinPoolSize(5);
		c3p0Plugin.setMaxPoolSize(15);
		plugins.add(c3p0Plugin);

		// 使用jfinal-ext配置AutoTableBindPlugin插件，自动扫描model
		AutoTableBindPlugin atbp = new AutoTableBindPlugin(c3p0Plugin);
		atbp.setContainerFactory(new CaseInsensitiveContainerFactory(true));// 配置数据库不区分大小写
		atbp.setShowSql(true);
		atbp.setDialect(new MysqlDialect());
		plugins.add(atbp);
	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors interceptors) {
		interceptors.add(new TxByActionMethods("save*", "update*", "delete*"));// 配置事务
	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers handlers) {

	}

	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目 运行此 main
	 * 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebContent", 80, "/", 5);
	}
}
