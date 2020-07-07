package test;

import com.github.microwww.generate.GenerateBuilder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class Generate {

    public void create() throws IOException {
        String root = new File(this.getClass().getResource("/").getFile()).getCanonicalPath();
        File src = FileSystems.getDefault().getPath(root, "..", "..", "..", "src", "main", "java").toFile();

        GenerateBuilder.config("com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://127.0.0.1:3306/microservice?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai",
                "root", "root")
                .writeEntity(src, "cn.book.domain")
                .writeEntityIdGeneratedValue().writerEntitySetToList()
                .and().repository("cn.book.repository").writeRepositoryFile()
                .and().service("cn.book.service").writeServiceFile()
                .and().dto("cn.book.vo").writeAbstractBaseClassFile().writeDTOFile()
                .and().controller("cn.book.controller").writeControllerFile();
    }



}
