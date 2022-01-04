package cn.aisencode.springaop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"cn.aisencode.*"})
@MapperScan("cn.aisencode.mapper")
public class SpringaopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringaopApplication.class, args);
    }

}
