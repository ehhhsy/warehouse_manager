package com.hsy.warehouse_manager2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
//接收/role/auth-grant 并传入DTO
public class AssignAuthDto {

    //接收角色id
    private Integer roleId;

    //接收角色分配的所有菜单权限id
    private List<Integer> authIds;
}
