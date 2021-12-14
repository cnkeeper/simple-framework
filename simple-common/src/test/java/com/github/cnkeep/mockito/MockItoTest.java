/*
 * *****************************************************
 * Copyright (C) 2021 bytedance.com. All Rights Reserved
 * This file is part of bytedance EA project.
 * Unauthorized copy of this file, via any medium is strictly prohibited.
 * Proprietary and Confidential.
 * ****************************************************
 */

package com.github.cnkeep.mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * MockItoTest
 *
 * @author LeiLi.Zhang <mailto:zhangleili924@gmail.com>
 * @date 2021/9/14
 */
@ExtendWith(MockitoExtension.class)
public class MockItoTest {
    @InjectMocks
    private Server server;
    @Mock
    private EchoService echoService;
    @Spy
    private EchoService service;

    @BeforeEach
    public void setup(){
        when(echoService.echo(anyString())).thenReturn("mock"+anyString());
    }

    @Test
    public void test(){
        String s = echoService.echo("s");
        System.out.println(s);
        server.run("hhh");
        System.out.println(service.echo("heeel"));
    }
}
