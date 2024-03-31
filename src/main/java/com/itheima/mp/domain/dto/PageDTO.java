package com.itheima.mp.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 86178
 */
@Data
@ApiModel(description = "分页结果")
public class PageDTO<T> {
    @ApiModelProperty("总页数")
    private Long pages;
    @ApiModelProperty("总记录数")
    private Long total;
    @ApiModelProperty("反回集合")
    private List<T> list;

}
