package com.hsy.warehouse_manager2.dto;
//接收分配角色请求json数据的dto类

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO(Data Transfer Object) 即 数据传输对象,是一种设计模式,主要用于在进程间传递数据。
 * DTO类的主要作用有:
 * 封装数据:DTO可以用来封装多种数据类型成一个模型,简化数据传输。
 * 属性和请求体传入的参数最好一样
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignRoleDto {
    private Integer userId;
    private List<String> roleCheckList;
}
