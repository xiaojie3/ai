import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;

import java.nio.file.Paths;
import java.sql.Types;

public class CodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("url", "username", "password")
                .globalConfig(builder -> {
                    builder.author("XieJie") // 设置作者
                            .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java")
                            .commentDate("yyyy-MM-dd");
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("com.example.demo") // 设置父包名
                                .moduleName("system") // 设置父包模块名
                                .entity("entity")
                                .mapper("mapper")
                                .service("service")
                                .serviceImpl("service.impl")
                                .xml("mapper.xml")
                )
                .strategyConfig(builder ->
                        builder.addTablePrefix("sys_") // 设置过滤表前缀
                ).execute();
    }
}
