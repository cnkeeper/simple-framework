package com.github.cnkeep.dubbo.common.util;

import org.junit.Test;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-6 22:4
 * @version:
 **/
public class FileLockTest {
    @Test
    public void fileLock() throws IOException, InterruptedException {
        Path path = Paths.get("./lock.lock");
        if (!Files.exists(path)) {
            path.toFile().createNewFile();
        }

        FileChannel channel = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE);
        System.out.println(channel.tryLock());
        System.out.println(channel.tryLock());
        new CountDownLatch(1).await();
    }
}
