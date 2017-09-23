package com.fighter.ace.app.resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fighter.ace.app.vo.MenuVO;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanebert on 17/5/25.
 */
@Component
@Path("/user")
public class UserResource {

    @GET
    @Path("menu")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenu(@Context HttpServletRequest request) {

        MenuVO mainMenu = new MenuVO();
        mainMenu.setMenuTitle("用户管理");
        mainMenu.setMenuUrl("#userList.html");

        List<MenuVO> subMenus = new ArrayList<>();
        MenuVO a = new MenuVO();
        a.setMenuUrl("#userma.html");
        a.setMenuTitle("abced");
        a.setIsLeaf(true);
        subMenus.add(a);

        mainMenu.setSubMenus(subMenus);


        return Response.ok(mainMenu).build();
    }

}
