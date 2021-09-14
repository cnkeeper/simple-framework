/*
 * *****************************************************
 * Copyright (C) 2021 bytedance.com. All Rights Reserved
 * This file is part of bytedance EA project.
 * Unauthorized copy of this file, via any medium is strictly prohibited.
 * Proprietary and Confidential.
 * ****************************************************
 */

package com.github.cnkeep.mockito;

/**
 * Server
 *
 * @author LeiLi.Zhang <mailto:zhangleili@bytedance.com>
 * @date 2021/9/14
 */
public class Server {
    private EchoService echoService;

    public void run(String message){
        System.out.println(echoService.echo(message));
    }
}
