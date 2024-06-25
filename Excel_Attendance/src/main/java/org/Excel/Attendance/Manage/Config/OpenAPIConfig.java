package org.Excel.Attendance.Manage.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

  @Value("${ExcelAttendanceManage.openapi.dev-url}")
  private String devUrl;

//  @Value("${bezkoder.openapi.prod-url}")
//  private String prodUrl;

  @Bean
  public OpenAPI myOpenAPI() {
    Server devServer = new Server();
    devServer.setUrl(devUrl);
    devServer.setDescription("Server URL in Development environment");

//    Server prodServer = new Server();
//    prodServer.setUrl(devUrl);
//    prodServer.setDescription("Server URL in Production environment");


//    License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

    Info info = new Info()
        .title("Excel Attendance Management  API")
        .version("1.0");
//        .license(mitLicense);

    return new OpenAPI().info(info).servers(List.of(devServer));
  }
}
