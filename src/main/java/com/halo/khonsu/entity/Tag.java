package com.halo.khonsu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author chen
 * @since 2022-05-07
 */
@Getter
@Setter
  @TableName("t_tag")
@ApiModel(value = "Tag对象", description = "")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
      @TableId(value = "id", type = IdType.AUTO)
        private Integer id;

      @ApiModelProperty("标签名称")
      private String name;

      @ApiModelProperty("是否启用")
      private Boolean state;

      @ApiModelProperty("文章id")
      private Integer articleId;


}
