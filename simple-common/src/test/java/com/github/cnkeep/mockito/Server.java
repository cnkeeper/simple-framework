 

package com.github.cnkeep.mockito;

/**
 * Server
 *
 * @author LeiLi.Zhang <mailto:zhangleili924@gmail.com>
 * @date 2021/9/14
 */
public class Server {
    private EchoService echoService;

    public void run(String message){
        System.out.println(echoService.echo(message));
    }
}
