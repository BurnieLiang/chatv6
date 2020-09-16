package com.github.BurnieLiang.chatv6.model.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

/**
 * @author Boning Liang
 * @date 2020-09-16 11:45:31
 */
@Data
public class Route {

    /**
     * 路由序号
     * 从0开始增加
     */
    private Integer id;

    /**
     * 地址
     */
    private String addr;

    /**
     * 下一个路由
     */
    private Route next;

    public static Route add(Route route, String addr) {
        Route newRoute = new Route();
        newRoute.setId(route.getId()+1);
        newRoute.setAddr(addr);
        newRoute.setNext(route);
        return newRoute;
    }

    public static void main(String[] args) throws JsonProcessingException {

        Route route = new Route();
        route.setId(0);
        route.setAddr("192.168.1.1:8080");

        int i = 0;
        while (i < 5) {
            route = Route.add(route,"test");
            i++;
        }

//        Route route1 = new Route();
//        route1.setId(1);
//        route1.setAddr("192.168.1.2:8080");
//        route1.setNext(route0);
//
//        Route route2 = new Route();
//        route2.setId(2);
//        route2.setAddr("192.168.1.3:8080");
//        route2.setNext(route1);

        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(objectMapper.writeValueAsString(route));

    }

}
