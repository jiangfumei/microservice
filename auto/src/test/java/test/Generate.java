package test;

import com.github.microwww.generate.GenerateBuilder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class Generate {

    @Test
    public void create() throws IOException {
        /*String root = new File(this.getClass().getResource("/").getFile()).getCanonicalPath();
        File src = FileSystems.getDefault().getPath(root, "..", "..", "..", "src", "main", "java").toFile();*/
        File src = new File("D:\\mydb");
        GenerateBuilder.config("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://127.0.0.1:3306/microservice?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai",
                "root", "root")
                .writeEntity(src, "com.cloud.sysadmin.entity")
                .writeEntityIdGeneratedValue().writerEntitySetToList()
                .and().repository("com.cloud.sysadmin.repository").writeRepositoryFile()
                .and().dto("com.cloud.sysadmin.vo").writeAbstractBaseClassFile().writeDTOFile()
                .and().service("com.cloud.sysadmin.service").writeServiceFile();
    }

}
