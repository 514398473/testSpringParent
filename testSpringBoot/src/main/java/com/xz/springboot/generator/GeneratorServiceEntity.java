package com.xz.springboot.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * <p>
 * 测试生成代码
 * </p>
 *
 * @author K神
 * @date 2017/12/18
 */
public class GeneratorServiceEntity {

//	public static void main(String[] args) {
//		String packageName = "com.xz.springboot.test";
//		boolean serviceNameStartWithI = true;// user -> UserService, 设置成true: user -> IUserService
//		generateByTables(serviceNameStartWithI, packageName, "t_user");
//	}

	private static void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
		GlobalConfig config = new GlobalConfig();
		String dbUrl = "jdbc:mysql://localhost:3306/test";
		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDbType(DbType.MYSQL).setUrl(dbUrl).setUsername("root").setPassword("123456")
				.setDriverName("com.mysql.jdbc.Driver");
		StrategyConfig strategyConfig = new StrategyConfig();
		strategyConfig.setCapitalMode(true).setEntityLombokModel(false).setDbColumnUnderline(true)
				.setNaming(NamingStrategy.underline_to_camel).setInclude(tableNames);// 修改替换成你需要的表名，多个表名传数组
		config.setActiveRecord(true).setAuthor("").setOutputDir("e:\\").setFileOverride(true);
		if (!serviceNameStartWithI) {
			config.setServiceName("%sService");
		}
		new AutoGenerator().setGlobalConfig(config).setDataSource(dataSourceConfig).setStrategy(strategyConfig)
				.setPackageInfo(
						new PackageConfig().setParent(packageName).setController("controller").setEntity("model"))
				.execute();
	}

	private void generateByTables(String packageName, String... tableNames) {
		generateByTables(true, packageName, tableNames);
	}
}
